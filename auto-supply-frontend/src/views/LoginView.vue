<template>
  <div class="login-container">
    <div class="login-form cyber-card">
      <div class="form-header">
        <h2 class="form-title holographic-text">SupplyChain Cockpit</h2>
        <p class="form-subtitle">请输入您的账号和密码</p>
      </div>
      
      <el-form 
        ref="loginFormRef" 
        :model="loginForm" 
        :rules="loginRules" 
        class="form-fields"
        @submit.prevent="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="用户名"
            prefix-icon="User"
            size="large"
            class="cyber-input"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="密码"
            prefix-icon="Lock"
            size="large"
            class="cyber-input"
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <el-form-item>
          <el-checkbox v-model="rememberMe" class="remember-me">
            记住我
          </el-checkbox>
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            class="login-button"
            :loading="loading"
            @click="handleLogin"
          >
            {{ loading ? '登录中...' : '登录' }}
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="form-footer">
        <p class="footer-text">
          © 2025 SupplyChain Cockpit. All rights reserved.
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { useUserStore } from '@/store/modules/user'
import type { LoginParams } from '@/types/user'

const router = useRouter()
const userStore = useUserStore()

const loginFormRef = ref<FormInstance>()

const loginForm = reactive<LoginParams>({
  username: 'admin',
  password: 'password123'
})

const rememberMe = ref(false)
const loading = ref(false)

const loginRules = reactive<FormRules>({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少6位', trigger: 'blur' }
  ]
})

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      
      try {
        await userStore.login(loginForm)
        ElMessage.success('登录成功')
        
        // 跳转到首页
        router.push('/')
      } catch (error: any) {
        ElMessage.error(error.message || '登录失败')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped lang="scss">
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  width: 100vw;
  background: linear-gradient(135deg, var(--bg-secondary) 0%, var(--bg-primary) 100%);
  
  .login-form {
    width: 100%;
    max-width: 400px;
    padding: var(--spacing-xl);
    border-radius: var(--radius-xl);
    box-shadow: var(--shadow-glow);
    
    .form-header {
      text-align: center;
      margin-bottom: var(--spacing-xl);
      
      .form-title {
        font-size: 2rem;
        margin-bottom: var(--spacing-sm);
      }
      
      .form-subtitle {
        color: var(--text-secondary);
      }
    }
    
    .form-fields {
      :deep(.el-form-item) {
        margin-bottom: var(--spacing-lg);
      }
      
      :deep(.el-input__wrapper) {
        background: rgba(255, 255, 255, 0.05);
        border: 1px solid var(--border-primary);
        border-radius: var(--radius-md);
        box-shadow: none;
        
        &:hover,
        &:focus {
          border-color: var(--color-primary);
          box-shadow: 0 0 0 1px var(--color-primary);
        }
      }
    }
    
    .remember-me {
      color: var(--text-secondary);
      :deep(.el-checkbox__input.is-checked .el-checkbox__inner) {
        background-color: var(--color-primary);
        border-color: var(--color-primary);
      }
    }
    
    .login-button {
      width: 100%;
      background: linear-gradient(90deg, var(--color-primary), var(--color-accent));
      border: none;
      font-weight: bold;
      letter-spacing: 1px;
      
      &:hover {
        transform: translateY(-2px);
        box-shadow: var(--shadow-glow);
      }
    }
    
    .form-footer {
      margin-top: var(--spacing-xl);
      text-align: center;
      
      .footer-text {
        color: var(--text-muted);
        font-size: 0.8rem;
      }
    }
  }
}
</style>