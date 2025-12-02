import { computed } from 'vue'
import { useUserStore } from '@/store/modules/user'

/**
 * Permission composable
 * @returns Permission checking methods
 */
export function usePermission() {
  const userStore = useUserStore()

  /**
   * Check if user has specific permission
   * @param permission Permission code
   * @returns Has permission
   */
  const hasPermission = computed(() => (permission: string): boolean => {
    return userStore.permissions.includes(permission)
  })

  /**
   * Check if user has specific role
   * @param role Role name
   * @returns Has role
   */
  const hasRole = computed(() => (role: string): boolean => {
    return userStore.roles.includes(role)
  })

  /**
   * Check if user has any of the specified permissions
   * @param permissions Permission codes
   * @returns Has any permission
   */
  const hasAnyPermission = computed(() => (permissions: string[]): boolean => {
    return permissions.some(permission => userStore.permissions.includes(permission))
  })

  /**
   * Check if user has all of the specified permissions
   * @param permissions Permission codes
   * @returns Has all permissions
   */
  const hasAllPermissions = computed(() => (permissions: string[]): boolean => {
    return permissions.every(permission => userStore.permissions.includes(permission))
  })

  /**
   * Check if user is admin
   * @returns Is admin
   */
  const isAdmin = computed((): boolean => {
    return userStore.roles.includes('admin')
  })

  return {
    hasPermission,
    hasRole,
    hasAnyPermission,
    hasAllPermissions,
    isAdmin
  }
}