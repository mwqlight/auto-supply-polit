# Auto Supply Frontend

供应链数字孪生可视化前端应用，基于 Vue 3 + TypeScript + Vite 构建。

## 功能特性

- 供应链网络可视化展示
- 实时数据监控面板
- 数字孪生体管理
- 用户认证和权限控制
- 响应式设计，支持多设备访问

## 技术栈

- Vue 3 (Composition API)
- TypeScript
- Vite
- Pinia (状态管理)
- Vue Router (路由管理)
- Axios (HTTP客户端)
- Element Plus (UI组件库)
- ECharts (数据可视化)

## 环境要求

- Node.js 16+
- npm 8+ (推荐使用pnpm)

## 快速开始

### 1. 安装依赖

```bash
npm install
# 或者使用 pnpm
pnpm install
```

### 2. 启动开发服务器

```bash
npm run dev
# 或者使用 pnpm
pnpm dev
```

开发服务器默认运行在 `http://localhost:5173`

### 3. 构建生产版本

```bash
npm run build
# 或者使用 pnpm
pnpm build
```

构建产物将输出到 `dist` 目录。

### 4. 本地预览生产构建

```bash
npm run preview
# 或者使用 pnpm
pnpm preview
```

## 项目结构

```
src/
├── api/            # API接口封装
│   ├── modules/    # 按模块拆分
│   └── index.ts    # 统一出口
├── assets/         # 静态资源
├── components/     # 公共组件
│   ├── base/       # 基础组件
│   └── layout/     # 布局组件
├── composables/    # 组合式API
├── router/         # 路由配置
├── store/          # 状态管理
├── styles/         # 全局样式
├── types/          # TypeScript类型定义
├── utils/          # 工具函数
├── views/          # 页面视图
│   ├── dashboard/  # 控制台
│   ├── digital-twin/ # 数字孪生
│   ├── auth/       # 认证相关
│   └── layout/     # 布局页面
├── App.vue         # 根组件
└── main.ts         # 入口文件
```

## 开发指南

### 环境变量配置

创建 `.env.local` 文件来配置本地环境变量：

```env
VITE_API_BASE_URL=http://localhost:8080
VITE_APP_TITLE=Supply Chain Digital Twin
```

### 组件开发规范

1. 使用 `<script setup lang="ts">` 语法
2. 组件名称使用 PascalCase
3. Props 必须定义类型和默认值
4. 使用 Composition API 组织逻辑

示例组件结构：

```vue
<script setup lang="ts">
// 1. 类型定义
interface Props {
  title?: string
}

// 2. Props 定义
const props = withDefaults(defineProps<Props>(), {
  title: 'Default Title'
})

// 3. 响应式数据
const count = ref(0)

// 4. 计算属性
const doubleCount = computed(() => count.value * 2)

// 5. 方法定义
function increment() {
  count.value++
}

// 6. 生命周期钩子
onMounted(() => {
  console.log('Component mounted')
})
</script>

<template>
  <div>
    <h1>{{ title }}</h1>
    <p>Count: {{ count }}</p>
    <p>Double Count: {{ doubleCount }}</p>
    <button @click="increment">Increment</button>
  </div>
</template>
```

### API 调用封装

API 调用统一通过 `src/api` 目录管理：

```typescript
// src/api/modules/digitalTwin.ts
import request from '@/utils/request'
import type { 
  DigitalTwinData, 
  SupplyChainNode, 
  SupplyChainLink 
} from '@/types/digitalTwin'

// 获取数字孪生数据
export function getDigitalTwinData() {
  return request<DigitalTwinData>({
    url: '/api/v1/supplychain/digital-twin/data',
    method: 'get'
  })
}

// 获取所有节点
export function getAllNodes() {
  return request<SupplyChainNode[]>({
    url: '/api/v1/supplychain/digital-twin/nodes',
    method: 'get'
  })
}
```

### 路由配置

路由配置位于 `src/router/index.ts`：

```typescript
import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/dashboard/index.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/Login.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
```

### 状态管理

使用 Pinia 进行状态管理，定义在 `src/store` 目录：

```typescript
// src/store/modules/user.ts
import { defineStore } from 'pinia'
import type { UserInfo } from '@/types/user'

interface UserState {
  userInfo: UserInfo | null
  token: string | null
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    userInfo: null,
    token: localStorage.getItem('token')
  }),
  
  getters: {
    isLoggedIn: (state) => !!state.token
  },
  
  actions: {
    setToken(token: string) {
      this.token = token
      localStorage.setItem('token', token)
    },
    
    logout() {
      this.token = null
      this.userInfo = null
      localStorage.removeItem('token')
    }
  }
})
```

## 测试

### 单元测试

使用 Vitest 进行单元测试：

```bash
npm run test:unit
# 或者使用 pnpm
pnpm test:unit
```

### 组件测试

使用 Vue Test Utils 进行组件测试：

```typescript
import { mount } from '@vue/test-utils'
import HelloWorld from './HelloWorld.vue'

test('displays message', () => {
  const wrapper = mount(HelloWorld, {
    props: {
      msg: 'Hello World'
    }
  })

  expect(wrapper.text()).toContain('Hello World')
})
```

## 构建和部署

### 环境配置

根据不同环境创建对应的环境文件：

- `.env.development` - 开发环境
- `.env.production` - 生产环境
- `.env.staging` - 预发布环境

### 构建命令

```bash
# 开发环境构建
npm run build

# 生产环境构建
npm run build:prod

# 预发布环境构建
npm run build:stage
```

### Docker 部署

构建 Docker 镜像：

```bash
docker build -t auto-supply-frontend:latest .
```

运行容器：

```bash
docker run -d \
  --name auto-supply-frontend \
  -p 80:80 \
  auto-supply-frontend:latest
```

## 代码质量

### 代码检查

使用 ESLint 和 Prettier 保证代码风格一致：

```bash
# 检查代码
npm run lint

# 自动修复
npm run lint:fix
```

### 类型检查

使用 TypeScript 进行类型检查：

```bash
npm run type-check
```

## 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 许可证

MIT License