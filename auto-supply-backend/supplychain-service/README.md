# SupplyChain Service

供应链数字孪生服务，提供实时的供应链网络可视化和监控功能。

## 功能特性

- 数字孪生体管理
- 供应链节点和链接监控
- 实时事件跟踪
- 历史数据分析
- RESTful API接口

## 技术栈

- Spring Boot 3.x
- MySQL 8.x
- Redis
- JWT 认证
- Maven

## 环境要求

- JDK 17+
- MySQL 8.0+
- Redis 6.x+
- Maven 3.8+

## 快速开始

### 1. 数据库配置

创建数据库：
```sql
CREATE DATABASE supplychain_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 配置文件

修改 `src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/supplychain_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: root
```

### 3. 启动服务

```bash
# 编译项目
mvn clean compile

# 运行服务
mvn spring-boot:run

# 或者打包运行
mvn clean package
java -jar target/supplychain-service-1.0.0.jar
```

服务默认运行在 `http://localhost:8080`

## API 文档

服务集成 SpringDoc OpenAPI，启动后访问以下地址查看 API 文档：

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## 认证接口

### 用户登录

```
POST /api/v1/auth/login

请求参数:
{
  "username": "admin",
  "password": "admin123"
}

响应:
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "expiresIn": 86400000
  },
  "timestamp": 1650000000000
}
```

### 用户登出

```
POST /api/v1/auth/logout

Headers:
Authorization: Bearer <access_token>

响应:
{
  "code": 200,
  "message": "登出成功",
  "data": null,
  "timestamp": 1650000000000
}
```

## 数字孪生接口

### 获取数字孪生数据

```
GET /api/v1/supplychain/digital-twin/data

Headers:
Authorization: Bearer <access_token>

响应:
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "nodes": [...],
    "links": [...],
    "metrics": {...},
    "events": [...]
  },
  "timestamp": 1650000000000
}
```

### 获取所有节点

```
GET /api/v1/supplychain/digital-twin/nodes

Headers:
Authorization: Bearer <access_token>

响应:
{
  "code": 200,
  "message": "操作成功",
  "data": [...],
  "timestamp": 1650000000000
}
```

### 获取所有链接

```
GET /api/v1/supplychain/digital-twin/links

Headers:
Authorization: Bearer <access_token>

响应:
{
  "code": 200,
  "message": "操作成功",
  "data": [...],
  "timestamp": 1650000000000
}
```

### 获取实时事件

```
GET /api/v1/supplychain/digital-twin/events/realtime

Headers:
Authorization: Bearer <access_token>

响应:
{
  "code": 200,
  "message": "操作成功",
  "data": [...],
  "timestamp": 1650000000000
}
```

## 测试

### 运行单元测试

```bash
mvn test
```

### 运行集成测试

```bash
mvn verify
```

## 部署

### 构建 Docker 镜像

```bash
docker build -t supplychain-service:latest .
```

### 运行容器

```bash
docker run -d \
  --name supplychain-service \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/supplychain_db \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=root \
  supplychain-service:latest
```

## 目录结构

```
src/main/java/com/supplychain/supplychain/
├── config/         # 配置类
├── controller/     # 控制层
├── dto/            # 数据传输对象
│   ├── request/    # 请求DTO
│   └── response/   # 响应DTO
├── entity/         # 实体类
├── exception/      # 异常处理
├── filter/         # 过滤器
├── repository/     # 数据访问层
├── service/        # 业务逻辑
│   └── impl/       # 实现类
└── util/           # 工具类
```

## 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 许可证

MIT License