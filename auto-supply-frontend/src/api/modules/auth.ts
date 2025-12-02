import request from '@/utils/request'
import type { LoginParams } from '@/types/user'

/**
 * 用户登录
 * @param data 登录参数
 */
export const loginApi = (data: LoginParams): Promise<any> => {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

/**
 * 用户登出
 */
export const logoutApi = (): Promise<any> => {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}