import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/modules/user'

// Create axios instance
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1', // API base_url
  timeout: 15000 // Request timeout
})

// Request interceptors
service.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    // Add token to headers if exists
    const userStore = useUserStore()
    if (userStore.token) {
      if (!config.headers) {
        config.headers = {}
      }
      config.headers['Authorization'] = `Bearer ${userStore.token}`
    }
    
    // Add timestamp to prevent caching
    if (config.params) {
      config.params['_t'] = Date.now()
    } else {
      config.params = { '_t': Date.now() }
    }
    
    return config
  },
  (error: any) => {
    // Handle request error
    console.error('Request error:', error)
    return Promise.reject(error)
  }
)

// Response interceptors
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data
    
    // Handle different response codes
    if (res.code !== 200) {
      // Handle specific error codes
      switch (res.code) {
        case 401:
          // Token expired or invalid
          ElMessage.error('登录已过期，请重新登录')
          // Clear token and redirect to login
          const userStore = useUserStore()
          userStore.logout()
          window.location.href = '/login'
          break
        case 403:
          ElMessage.error('权限不足，无法访问')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error(res.message || '请求失败')
          break
      }
      
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  (error: any) => {
    // Handle network errors
    console.error('Response error:', error)
    if (error.response) {
      switch (error.response.status) {
        case 401:
          ElMessage.error('登录已过期，请重新登录')
          const userStore = useUserStore()
          userStore.logout()
          window.location.href = '/login'
          break
        case 403:
          ElMessage.error('权限不足，无法访问')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error(`请求失败: ${error.response.statusText}`)
          break
      }
    } else {
      // Network error
      ElMessage.error('网络错误，请检查网络连接')
    }
    
    return Promise.reject(error)
  }
)

export default service