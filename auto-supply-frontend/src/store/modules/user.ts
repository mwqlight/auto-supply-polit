import { defineStore } from 'pinia'
import { ref, reactive } from 'vue'
import type { UserInfo, LoginParams } from '@/types/user'
import { loginApi, logoutApi } from '@/api/modules/auth'

// Define the store
export const useUserStore = defineStore('user', () => {
  // State
  const token = ref<string>('')
  const userInfo = reactive<UserInfo>({
    id: '',
    username: '',
    nickname: '',
    avatar: '',
    roles: [],
    permissions: []
  })
  const isLogin = ref<boolean>(false)

  // Actions
  /**
   * User login
   * @param params Login parameters
   */
  const login = async (params: LoginParams) => {
    try {
      const res = await loginApi(params)
      if (res.code === 200) {
        token.value = res.data.accessToken
        isLogin.value = true
        // Save token to localStorage
        localStorage.setItem('access_token', token.value)
        return Promise.resolve(res)
      } else {
        return Promise.reject(new Error(res.message || '登录失败'))
      }
    } catch (error) {
      return Promise.reject(error)
    }
  }

  /**
   * User logout
   */
  const logout = async () => {
    try {
      await logoutApi()
      resetState()
      // Remove token from localStorage
      localStorage.removeItem('access_token')
      return Promise.resolve()
    } catch (error) {
      // Even if logout API fails, we still need to clear local state
      resetState()
      localStorage.removeItem('access_token')
      return Promise.reject(error)
    }
  }

  /**
   * Reset state
   */
  const resetState = () => {
    token.value = ''
    userInfo.id = ''
    userInfo.username = ''
    userInfo.nickname = ''
    userInfo.avatar = ''
    userInfo.roles = []
    userInfo.permissions = []
    isLogin.value = false
  }

  /**
   * Set token
   * @param newToken New token
   */
  const setToken = (newToken: string) => {
    token.value = newToken
    isLogin.value = !!newToken
    if (newToken) {
      localStorage.setItem('access_token', newToken)
    } else {
      localStorage.removeItem('access_token')
    }
  }

  /**
   * Check if user has permission
   * @param permission Permission to check
   */
  const hasPermission = (permission: string): boolean => {
    return userInfo.permissions.includes(permission)
  }

  /**
   * Check if user has role
   * @param role Role to check
   */
  const hasRole = (role: string): boolean => {
    return userInfo.roles.includes(role)
  }

  // Initialize token from localStorage
  const initToken = () => {
    const savedToken = localStorage.getItem('access_token')
    if (savedToken) {
      token.value = savedToken
      isLogin.value = true
    }
  }

  // Initialize store
  initToken()

  return {
    // State
    token,
    userInfo,
    isLogin,

    // Actions
    login,
    logout,
    resetState,
    setToken,
    hasPermission,
    hasRole
  }
})