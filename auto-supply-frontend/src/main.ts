import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import { setupRouterGuard } from './router/guard'

// Import global styles
import './styles/index.scss'

// Import Element Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

// Setup router guard
setupRouterGuard(router)

app.mount('#app')