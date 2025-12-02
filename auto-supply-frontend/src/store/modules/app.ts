import { defineStore } from 'pinia'
import { ref, reactive } from 'vue'

// Define types
export interface AppState {
  sidebar: {
    opened: boolean
    withoutAnimation: boolean
  }
  device: string
  size: string
  language: string
  theme: string
}

// Define the store
export const useAppStore = defineStore('app', () => {
  // State
  const sidebar = reactive({
    opened: true,
    withoutAnimation: false
  })
  const device = ref<string>('desktop')
  const size = ref<string>('medium')
  const language = ref<string>('zh-cn')
  const theme = ref<string>('cyberpunk')

  // Actions
  /**
   * Toggle sidebar
   */
  const toggleSidebar = () => {
    sidebar.opened = !sidebar.opened
    sidebar.withoutAnimation = false
    if (sidebar.opened) {
      localStorage.setItem('sidebarStatus', '1')
    } else {
      localStorage.setItem('sidebarStatus', '0')
    }
  }

  /**
   * Close sidebar
   * @param withoutAnimation Whether to close without animation
   */
  const closeSidebar = (withoutAnimation: boolean) => {
    localStorage.setItem('sidebarStatus', '0')
    sidebar.opened = false
    sidebar.withoutAnimation = withoutAnimation
  }

  /**
   * Toggle device
   * @param device Device type
   */
  const toggleDevice = (deviceType: string) => {
    device.value = deviceType
  }

  /**
   * Set size
   * @param size Size
   */
  const setSize = (sizeValue: string) => {
    size.value = sizeValue
    localStorage.setItem('size', sizeValue)
  }

  /**
   * Set language
   * @param language Language
   */
  const setLanguage = (languageValue: string) => {
    language.value = languageValue
    localStorage.setItem('language', languageValue)
  }

  /**
   * Set theme
   * @param theme Theme
   */
  const setTheme = (themeValue: string) => {
    theme.value = themeValue
    localStorage.setItem('theme', themeValue)
    // Apply theme to document
    document.documentElement.setAttribute('data-theme', themeValue)
  }

  /**
   * Initialize app settings
   */
  const initAppSettings = () => {
    // Initialize sidebar status
    const sidebarStatus = localStorage.getItem('sidebarStatus')
    if (sidebarStatus !== null) {
      sidebar.opened = sidebarStatus === '1'
    }

    // Initialize size
    const savedSize = localStorage.getItem('size')
    if (savedSize) {
      size.value = savedSize
    }

    // Initialize language
    const savedLanguage = localStorage.getItem('language')
    if (savedLanguage) {
      language.value = savedLanguage
    }

    // Initialize theme
    const savedTheme = localStorage.getItem('theme')
    if (savedTheme) {
      theme.value = savedTheme
      document.documentElement.setAttribute('data-theme', savedTheme)
    } else {
      // Set default theme
      document.documentElement.setAttribute('data-theme', 'cyberpunk')
    }
  }

  return {
    // State
    sidebar,
    device: device.value,
    size: size.value,
    language: language.value,
    theme: theme.value,

    // Actions
    toggleSidebar,
    closeSidebar,
    toggleDevice,
    setSize,
    setLanguage,
    setTheme,
    initAppSettings
  }
})