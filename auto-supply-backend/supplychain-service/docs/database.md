# 数据库设计文档

## 数据库表结构

### 用户表 (sc_user)

| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 用户ID |
| username | VARCHAR(50) | UNIQUE, NOT NULL | 用户名 |
| password | VARCHAR(100) | NOT NULL | 密码(BCrypt加密) |
| nick_name | VARCHAR(50) |  | 昵称 |
| email | VARCHAR(100) |  | 邮箱 |
| phone | VARCHAR(20) |  | 手机号 |
| avatar | VARCHAR(200) |  | 头像URL |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |
| last_login_at | TIMESTAMP |  | 最后登录时间 |

### 数字孪生体表 (sc_digital_twin)

| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 数字孪生体ID |
| twin_name | VARCHAR(100) | NOT NULL | 孪生体名称 |
| twin_type | VARCHAR(50) | NOT NULL | 孪生体类型 |
| status | VARCHAR(20) | NOT NULL | 状态(active/inactive) |
| location | VARCHAR(200) |  | 位置信息 |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 供应链节点表 (sc_supply_chain_node)

| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 节点ID |
| node_name | VARCHAR(100) | NOT NULL | 节点名称 |
| node_type | VARCHAR(50) | NOT NULL | 节点类型(supplier/manufacturer/distributor/retailer/consumer) |
| status | VARCHAR(20) | NOT NULL | 状态(active/maintenance/inactive) |
| location | VARCHAR(200) |  | 位置信息 |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 供应链链接表 (sc_supply_chain_link)

| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 链接ID |
| source_node_id | BIGINT | NOT NULL, FOREIGN KEY | 源节点ID |
| target_node_id | BIGINT | NOT NULL, FOREIGN KEY | 目标节点ID |
| transport_mode | VARCHAR(50) |  | 运输方式(truck/ship/plane/rail) |
| status | VARCHAR(20) | NOT NULL | 状态(active/maintenance/inactive) |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 供应链事件表 (sc_supply_chain_event)

| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 事件ID |
| event_type | VARCHAR(50) | NOT NULL | 事件类型(delay/disruption/risk/alert) |
| severity | VARCHAR(20) | NOT NULL | 严重程度(low/medium/high/critical) |
| source_node_id | BIGINT | FOREIGN KEY | 源节点ID |
| target_node_id | BIGINT | FOREIGN KEY | 目标节点ID |
| description | TEXT |  | 事件描述 |
| occurred_at | TIMESTAMP | NOT NULL | 发生时间 |
| resolved_at | TIMESTAMP |  | 解决时间 |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |

## 索引设计

### 用户表索引
- 主键索引: id
- 唯一索引: username

### 数字孪生体表索引
- 主键索引: id
- 普通索引: twin_type, status

### 供应链节点表索引
- 主键索引: id
- 普通索引: node_type, status

### 供应链链接表索引
- 主键索引: id
- 普通索引: source_node_id, target_node_id, transport_mode, status

### 供应链事件表索引
- 主键索引: id
- 普通索引: event_type, severity, source_node_id, target_node_id, occurred_at

## 初始化数据

### 默认管理员用户
```sql
INSERT INTO sc_user (username, password, nick_name, email) 
VALUES ('admin', '$2a$10$abcdefghijklmnopqrstuvwxABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', '管理员', 'admin@example.com');
```

### 示例数字孪生体
```sql
INSERT INTO sc_digital_twin (twin_name, twin_type, status, location) 
VALUES ('主供应链网络', 'supply_chain', 'active', '全球');
```

### 示例供应链节点
```sql
INSERT INTO sc_supply_chain_node (node_name, node_type, status, location) 
VALUES 
('原材料供应商A', 'supplier', 'active', '上海'),
('制造商B', 'manufacturer', 'active', '深圳'),
('分销商C', 'distributor', 'active', '北京'),
('零售商D', 'retailer', 'active', '广州');
```

### 示例供应链链接
```sql
INSERT INTO sc_supply_chain_link (source_node_id, target_node_id, transport_mode, status) 
VALUES 
(1, 2, 'truck', 'active'),
(2, 3, 'rail', 'active'),
(3, 4, 'truck', 'active');
```