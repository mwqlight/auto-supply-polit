# 部署文档

## 部署架构

```
┌─────────────────┐    ┌──────────────────┐    ┌──────────────────┐
│   Web Browser   │    │  Load Balancer   │    │   Application    │
│                 │───▶│    (Nginx)       │───▶│   Server(s)      │
└─────────────────┘    └──────────────────┘    └──────────────────┘
                                │                        │
                       ┌────────▼────────┐    ┌──────────▼──────────┐
                       │   MySQL 8.0+    │    │     Redis 6.x+      │
                       └─────────────────┘    └─────────────────────┘
```

## 环境准备

### 服务器要求
- CentOS 7.x / Ubuntu 18.04+ / Rocky Linux 8.x
- 4核CPU + 8GB内存 (最小配置)
- 100GB可用磁盘空间
- 公网IP地址

### 软件依赖
- OpenJDK 17+
- MySQL 8.0+
- Redis 6.x+
- Nginx 1.18+
- Docker 20.x+ (可选)

## 部署步骤

### 1. 数据库初始化

创建数据库：
```sql
CREATE DATABASE supplychain_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

执行数据库脚本：
```bash
mysql -u root -p supplychain_db < docs/database.sql
```

### 2. 应用配置

修改配置文件 `src/main/resources/application-prod.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/supplychain_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: ${DB_USERNAME:supplychain}
    password: ${DB_PASSWORD:supplychain123}
  
  redis:
    host: localhost
    port: 6379
    password: ${REDIS_PASSWORD:}
    database: 0

server:
  port: 8080

jwt:
  secret: ${JWT_SECRET:mySecretKey}
  expiration: ${JWT_EXPIRATION:86400000}
```

### 3. 构建应用

```bash
# 清理并编译
mvn clean compile

# 运行测试
mvn test

# 打包
mvn package -Pprod
```

### 4. 启动应用

```bash
# 方式一：直接运行
java -jar -Dspring.profiles.active=prod target/supplychain-service-1.0.0.jar

# 方式二：后台运行
nohup java -jar -Dspring.profiles.active=prod target/supplychain-service-1.0.0.jar > app.log 2>&1 &

# 方式三：使用 systemd (推荐)
sudo cp deploy/supplychain.service /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable supplychain
sudo systemctl start supplychain
```

### 5. Nginx 配置

创建 Nginx 配置文件 `/etc/nginx/conf.d/supplychain.conf`：
```nginx
upstream supplychain_backend {
    server 127.0.0.1:8080;
    # 可以添加更多服务器实现负载均衡
}

server {
    listen 80;
    server_name your-domain.com;
    
    # 重定向到 HTTPS
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl http2;
    server_name your-domain.com;
    
    # SSL 证书配置
    ssl_certificate /path/to/your/certificate.crt;
    ssl_certificate_key /path/to/your/private.key;
    
    # 安全头配置
    add_header X-Frame-Options DENY;
    add_header X-Content-Type-Options nosniff;
    add_header X-XSS-Protection "1; mode=block";
    
    location / {
        proxy_pass http://supplychain_backend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
    
    location /actuator/ {
        deny all;
        return 404;
    }
}
```

重新加载 Nginx 配置：
```bash
sudo nginx -t
sudo nginx -s reload
```

## Docker 部署

### 构建镜像

创建 Dockerfile：
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/supplychain-service-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

构建镜像：
```bash
docker build -t supplychain-service:1.0.0 .
```

### 运行容器

```bash
docker run -d \
  --name supplychain-service \
  --restart=unless-stopped \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_USERNAME=supplychain \
  -e DB_PASSWORD=supplychain123 \
  -e REDIS_PASSWORD= \
  -e JWT_SECRET=mySecretKey \
  supplychain-service:1.0.0
```

### Docker Compose 部署

创建 docker-compose.yml：
```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: supplychain-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root123
      MYSQL_DATABASE: supplychain_db
      MYSQL_USER: supplychain
      MYSQL_PASSWORD: supplychain123
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    restart: unless-stopped
    
  redis:
    image: redis:6-alpine
    container_name: supplychain-redis
    ports:
      - "6379:6379"
    restart: unless-stopped
    
  backend:
    build: .
    container_name: supplychain-backend
    depends_on:
      - mysql
      - redis
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: prod
      DB_HOST: mysql
      DB_PORT: 3306
      DB_NAME: supplychain_db
      DB_USERNAME: supplychain
      DB_PASSWORD: supplychain123
      REDIS_HOST: redis
      REDIS_PORT: 6379
    restart: unless-stopped

volumes:
  mysql_data:
```

启动服务：
```bash
docker-compose up -d
```

## 监控与日志

### 应用监控

启用 Spring Boot Actuator：
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
```

### 日志配置

配置日志文件 `src/main/resources/logback-spring.xml`：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <springProfile name="prod">
        <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>logs/supplychain.log</file>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>logs/supplychain.%d{yyyy-MM-dd}.%i.gz</fileNamePattern>
                <maxFileSize>100MB</maxFileSize>
                <maxHistory>30</maxHistory>
                <totalSizeCap>3GB</totalSizeCap>
            </rollingPolicy>
            <encoder>
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
            </encoder>
        </appender>
        
        <root level="INFO">
            <appender-ref ref="FILE" />
        </root>
    </springProfile>
</configuration>
```

## 性能调优

### JVM 参数调优

```bash
java -Xms2g -Xmx4g -XX:+UseG1GC -jar target/supplychain-service-1.0.0.jar
```

### 数据库连接池配置

```yaml
spring:
  datasource:
    hikari:
      minimum-idle: 10
      maximum-pool-size: 50
      idle-timeout: 300000
      max-lifetime: 600000
      connection-timeout: 30000
```

## 故障排除

### 常见问题

1. **数据库连接失败**
   - 检查数据库服务是否启动
   - 验证数据库连接配置
   - 检查防火墙设置

2. **Redis连接超时**
   - 检查Redis服务状态
   - 验证Redis连接配置
   - 查看网络连通性

3. **JWT认证失败**
   - 检查JWT密钥配置
   - 验证令牌有效期
   - 确认时钟同步

### 日志查看

```bash
# 查看应用日志
tail -f logs/supplychain.log

# 查看系统日志
journalctl -u supplychain -f

# Docker环境下查看日志
docker logs -f supplychain-service
```

## 备份与恢复

### 数据库备份

```bash
# 备份数据库
mysqldump -u root -p supplychain_db > backup/supplychain_db_$(date +%Y%m%d_%H%M%S).sql

# 恢复数据库
mysql -u root -p supplychain_db < backup/supplychain_db_20230101_120000.sql
```

### 应用配置备份

定期备份以下配置文件：
- `application-prod.yml`
- `logback-spring.xml`
- `deploy/supplychain.service`
- Nginx配置文件

## 安全加固

### 端口安全

只开放必要端口：
- 22 (SSH)
- 80/443 (HTTP/HTTPS)
- 3306 (MySQL - 仅内网访问)
- 6379 (Redis - 仅内网访问)

### 访问控制

配置防火墙规则：
```bash
# CentOS/Rocky Linux (firewalld)
sudo firewall-cmd --permanent --add-service=http
sudo firewall-cmd --permanent --add-service=https
sudo firewall-cmd --reload

# Ubuntu (ufw)
sudo ufw allow ssh
sudo ufw allow http
sudo ufw allow https
sudo ufw enable
```