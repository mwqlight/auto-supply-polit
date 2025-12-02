import { Router } from 'vue-router'
import { useUserStore } from '@/store/modules/user'

// 白名单路由，无需登录即可访问
const WHITE_LIST = ['/login']
const LOGIN_PATH = '/login'

export function setupRouterGuard(router: Router) {
  router.beforeEach(async (to, from, next) => {
    // 获取用户状态
    const userStore = useUserStore()
    
    // 如果访问的是白名单路由，直接放行
    if (WHITE_LIST.includes(to.path)) {
      // 如果已登录且访问登录页，则重定向到首页
      if (to.path === LOGIN_PATH && userStore.isLogin) {
        next({ path: '/' })
        return
      }
      next()
      return
    }
    
    // 检查是否已登录
    if (userStore.isLogin) {
      next()
    } else {
      // 未登录则重定向到登录页
      next({ path: LOGIN_PATH, query: { redirect: to.fullPath } })
    }
  })
}