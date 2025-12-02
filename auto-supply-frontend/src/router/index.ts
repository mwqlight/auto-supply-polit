import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

// Layouts
import DefaultLayout from '@/components/layout/DefaultLayout.vue'

// Views
import LoginView from '@/views/LoginView.vue'
import DashboardView from '@/views/dashboard/DashboardView.vue'
import DigitalTwinView from '@/views/digital-twin/DigitalTwinView.vue'
import ResourceView from '@/views/resource/ResourceView.vue'
import ProcessView from '@/views/process/ProcessView.vue'
import RiskView from '@/views/risk/RiskView.vue'
import ValueView from '@/views/value/ValueView.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
    meta: {
      title: '用户登录'
    }
  },
  {
    path: '/',
    component: DefaultLayout,
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: DashboardView,
        meta: {
          title: '驾驶舱主页'
        }
      },
      {
        path: '/digital-twin',
        name: 'DigitalTwin',
        component: DigitalTwinView,
        meta: {
          title: '数字孪生'
        }
      },
      {
        path: '/resource',
        name: 'Resource',
        component: ResourceView,
        meta: {
          title: '资源管理'
        }
      },
      {
        path: '/process',
        name: 'Process',
        component: ProcessView,
        meta: {
          title: '流程协同'
        }
      },
      {
        path: '/risk',
        name: 'Risk',
        component: RiskView,
        meta: {
          title: '风险防控'
        }
      },
      {
        path: '/value',
        name: 'Value',
        component: ValueView,
        meta: {
          title: '价值创造'
        }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router