# API 文档

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

### 获取历史数据

```
GET /api/v1/supplychain/digital-twin/history

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

### 获取节点详情

```
GET /api/v1/supplychain/digital-twin/nodes/{id}

Headers:
Authorization: Bearer <access_token>

响应:
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "name": "供应商A",
    "type": "supplier",
    "status": "active",
    "location": "上海",
    "createdAt": "2023-01-01T00:00:00",
    "updatedAt": "2023-01-01T00:00:00"
  },
  "timestamp": 1650000000000
}
```

### 获取链接详情

```
GET /api/v1/supplychain/digital-twin/links/{id}

Headers:
Authorization: Bearer <access_token>

响应:
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "sourceNodeId": 1,
    "targetNodeId": 2,
    "status": "active",
    "transportMode": "truck",
    "createdAt": "2023-01-01T00:00:00",
    "updatedAt": "2023-01-01T00:00:00"
  },
  "timestamp": 1650000000000
}
```

### 更新节点状态

```
PUT /api/v1/supplychain/digital-twin/nodes/{id}/status

Headers:
Authorization: Bearer <access_token>

请求参数:
{
  "status": "maintenance"
}

响应:
{
  "code": 200,
  "message": "操作成功",
  "data": null,
  "timestamp": 1650000000000
}
```

### 更新链接状态

```
PUT /api/v1/supplychain/digital-twin/links/{id}/status

Headers:
Authorization: Bearer <access_token>

请求参数:
{
  "status": "inactive"
}

响应:
{
  "code": 200,
  "message": "操作成功",
  "data": null,
  "timestamp": 1650000000000
}
```