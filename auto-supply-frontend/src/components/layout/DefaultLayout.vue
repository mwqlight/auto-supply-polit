<template>
  <div class="default-layout">
    <!-- Top Navigation Bar -->
    <header class="top-nav cyber-card">
      <div class="nav-left">
        <h1 class="logo holographic-text">SupplyChain Cockpit</h1>
      </div>
      <div class="nav-center">
        <nav class="nav-links">
          <router-link to="/dashboard" class="nav-link">驾驶舱</router-link>
          <router-link to="/digital-twin" class="nav-link">数字孪生</router-link>
          <router-link to="/resource" class="nav-link">资源</router-link>
          <router-link to="/process" class="nav-link">流程</router-link>
          <router-link to="/risk" class="nav-link">风险</router-link>
          <router-link to="/value" class="nav-link">价值</router-link>
        </nav>
      </div>
      <div class="nav-right">
        <div class="search-container">
          <input type="text" placeholder="智能搜索..." class="cyber-input" />
        </div>
        <div class="notifications">
          <el-badge :value="3" class="notification-badge">
            <el-icon><Bell /></el-icon>
          </el-badge>
        </div>
        <div class="user-profile">
          <el-dropdown>
            <span class="el-dropdown-link">
              <el-avatar :size="32" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人设置</el-dropdown-item>
                <el-dropdown-item>组织管理</el-dropdown-item>
                <el-dropdown-item divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </header>

    <!-- Main Content Area -->
    <main class="main-content">
      <router-view />
    </main>

    <!-- Bottom Status Bar -->
    <footer class="status-bar cyber-card">
      <div class="status-left">
        <span class="status-indicator online"></span>
        <span>系统运行正常</span>
      </div>
      <div class="status-center">
        <span>实时数据更新中...</span>
      </div>
      <div class="status-right">
        <span>{{ currentTime }}</span>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { Bell } from '@element-plus/icons-vue'

const currentTime = ref(new Date().toLocaleString())

let timer: number | null = null

onMounted(() => {
  timer = window.setInterval(() => {
    currentTime.value = new Date().toLocaleString()
  }, 1000)
})

onUnmounted(() => {
  if (timer) {
    clearInterval(timer)
  }
})
</script>

<style scoped lang="scss">
.default-layout {
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
}

.top-nav {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 var(--spacing-lg);
  height: 60px;
  margin: var(--spacing-md);
  z-index: 100;
  
  .nav-left {
    .logo {
      font-size: 1.5rem;
      font-weight: 700;
    }
  }
  
  .nav-center {
    .nav-links {
      display: flex;
      gap: var(--spacing-lg);
      
      .nav-link {
        color: var(--text-secondary);
        text-decoration: none;
        font-weight: 500;
        transition: all var(--transition-fast);
        padding: var(--spacing-sm) var(--spacing-md);
        border-radius: var(--radius-md);
        
        &.router-link-active,
        &:hover {
          color: var(--color-primary);
          background: rgba(0, 224, 255, 0.1);
        }
      }
    }
  }
  
  .nav-right {
    display: flex;
    align-items: center;
    gap: var(--spacing-lg);
    
    .search-container {
      width: 200px;
      
      input {
        width: 100%;
      }
    }
    
    .notifications {
      .notification-badge {
        :deep(.el-badge__content) {
          background-color: var(--color-danger);
        }
      }
    }
    
    .user-profile {
      cursor: pointer;
    }
  }
}

.main-content {
  flex: 1;
  overflow: auto;
  padding: 0 var(--spacing-md) var(--spacing-md);
}

.status-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 var(--spacing-lg);
  height: 40px;
  margin: 0 var(--spacing-md) var(--spacing-md);
  
  .status-left {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    
    .status-indicator {
      width: 10px;
      height: 10px;
      border-radius: 50%;
      
      &.online {
        background-color: var(--color-success);
        box-shadow: 0 0 8px var(--color-success);
      }
    }
  }
  
  .status-center {
    color: var(--text-secondary);
    font-size: 0.9rem;
  }
  
  .status-right {
    color: var(--text-secondary);
    font-size: 0.9rem;
  }
}
</style>