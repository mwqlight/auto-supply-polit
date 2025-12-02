<template>
  <div class="dashboard-view">
    <div class="dashboard-header">
      <h2 class="page-title">供应链全景驾驶舱</h2>
      <div class="controls">
        <el-button type="primary" class="cyber-button">刷新数据</el-button>
        <el-select v-model="timeRange" placeholder="选择时间范围" class="time-selector">
          <el-option label="实时" value="realtime"></el-option>
          <el-option label="近1小时" value="1h"></el-option>
          <el-option label="近24小时" value="24h"></el-option>
          <el-option label="近7天" value="7d"></el-option>
        </el-select>
      </div>
    </div>

    <div class="dashboard-grid">
      <!-- 3D供应链宇宙 -->
      <div class="grid-item full-width cyber-card">
        <div class="card-header">
          <h3>3D供应链宇宙</h3>
          <div class="header-controls">
            <el-tag type="success">实时</el-tag>
          </div>
        </div>
        <div class="card-content">
          <div ref="threeContainer" class="three-container"></div>
        </div>
      </div>

      <!-- 资源维面板 -->
      <div class="grid-item cyber-card">
        <div class="card-header">
          <h3>资源维</h3>
        </div>
        <div class="card-content">
          <div class="metric-grid">
            <div class="metric-item">
              <div class="metric-label">供应商健康度</div>
              <div class="metric-value">87.5%</div>
              <div class="metric-trend positive">↑ 2.3%</div>
            </div>
            <div class="metric-item">
              <div class="metric-label">库存水位</div>
              <div class="metric-value">78.3%</div>
              <div class="metric-trend negative">↓ 1.2%</div>
            </div>
            <div class="metric-item">
              <div class="metric-label">产能利用率</div>
              <div class="metric-value">92.7%</div>
              <div class="metric-trend warning">⚠ 高</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 流程维面板 -->
      <div class="grid-item cyber-card">
        <div class="card-header">
          <h3>流程维</h3>
        </div>
        <div class="card-content">
          <div class="metric-grid">
            <div class="metric-item">
              <div class="metric-label">订单履约率</div>
              <div class="metric-value">94.2%</div>
              <div class="metric-trend positive">↑ 0.8%</div>
            </div>
            <div class="metric-item">
              <div class="metric-label">交期达成率</div>
              <div class="metric-value">87.3%</div>
              <div class="metric-trend negative">↓ 3.1%</div>
            </div>
            <div class="metric-item">
              <div class="metric-label">流程瓶颈</div>
              <div class="metric-value">2</div>
              <div class="metric-trend warning">⚠ 注意</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 风险维面板 -->
      <div class="grid-item cyber-card">
        <div class="card-header">
          <h3>风险维</h3>
        </div>
        <div class="card-content">
          <div class="risk-chart">
            <div class="risk-item" v-for="risk in risks" :key="risk.id">
              <div class="risk-info">
                <div class="risk-name">{{ risk.name }}</div>
                <div class="risk-level" :class="risk.level.toLowerCase()">{{ risk.levelText }}</div>
              </div>
              <div class="risk-progress">
                <el-progress :percentage="risk.percentage" :status="risk.progressStatus" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 价值维面板 -->
      <div class="grid-item cyber-card">
        <div class="card-header">
          <h3>价值维</h3>
        </div>
        <div class="card-content">
          <div class="value-metrics">
            <div class="value-item">
              <div class="value-label">成本优化建议</div>
              <div class="value-value">¥1.2M/年</div>
            </div>
            <div class="value-item">
              <div class="value-label">可持续性指标</div>
              <div class="value-value">85.2%</div>
            </div>
            <div class="value-item">
              <div class="value-label">价值创造分析</div>
              <div class="value-value">↑ 15.3%</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 智能决策中心 -->
      <div class="grid-item cyber-card">
        <div class="card-header">
          <h3>智能决策中心</h3>
        </div>
        <div class="card-content">
          <div class="ai-insights">
            <div class="insight-item" v-for="insight in aiInsights" :key="insight.id">
              <div class="insight-title">{{ insight.title }}</div>
              <div class="insight-desc">{{ insight.description }}</div>
              <div class="insight-confidence">
                <el-progress :percentage="insight.confidence" :stroke-width="8" />
                <span>置信度: {{ insight.confidence }}%</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 实时数据流 -->
      <div class="grid-item cyber-card">
        <div class="card-header">
          <h3>实时数据流</h3>
        </div>
        <div class="card-content">
          <div class="data-stream">
            <div class="stream-item" v-for="item in dataStream" :key="item.id">
              <div class="stream-name">{{ item.name }}</div>
              <div class="stream-value">{{ item.value }}</div>
              <div class="stream-status" :class="item.status">{{ item.statusText }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import * as THREE from 'three'

const timeRange = ref('realtime')

// Mock data for risks
const risks = ref([
  { id: 1, name: '地缘政治风险', level: 'HIGH', levelText: '高风险', percentage: 82, progressStatus: 'exception' },
  { id: 2, name: '供应商财务风险', level: 'MEDIUM', levelText: '中风险', percentage: 65, progressStatus: 'warning' },
  { id: 3, name: '物流中断预警', level: 'LOW', levelText: '低风险', percentage: 35, progressStatus: 'success' }
])

// Mock data for AI insights
const aiInsights = ref([
  { id: 1, title: '上海工厂产能预警', description: '产能利用率已达92.7%，超出安全阈值', confidence: 89 },
  { id: 2, name: '港口拥堵预测', description: '洛杉矶港可能出现5-7天拥堵', confidence: 76 }
])

// Mock data for data stream
const dataStream = ref([
  { id: 1, name: '订单流', value: '1,245/秒', status: 'normal', statusText: '正常' },
  { id: 2, name: '库存变动', value: '+2.3%', status: 'positive', statusText: '增长' },
  { id: 3, name: '异常事件', value: '18个', status: 'warning', statusText: '警告' }
])

// Three.js container
const threeContainer = ref<HTMLElement | null>(null)
let scene: THREE.Scene
let camera: THREE.PerspectiveCamera
let renderer: THREE.WebGLRenderer
let animationId: number

onMounted(() => {
  initThreeJS()
  animate()
})

onUnmounted(() => {
  if (animationId) {
    cancelAnimationFrame(animationId)
  }
  if (renderer) {
    renderer.dispose()
  }
})

function initThreeJS() {
  if (!threeContainer.value) return

  // Create scene
  scene = new THREE.Scene()
  scene.background = new THREE.Color(0x0a0e17)

  // Create camera
  camera = new THREE.PerspectiveCamera(
    75,
    threeContainer.value.clientWidth / threeContainer.value.clientHeight,
    0.1,
    1000
  )
  camera.position.z = 5

  // Create renderer
  renderer = new THREE.WebGLRenderer({ antialias: true })
  renderer.setSize(
    threeContainer.value.clientWidth,
    threeContainer.value.clientHeight
  )
  threeContainer.value.appendChild(renderer.domElement)

  // Add some lights
  const ambientLight = new THREE.AmbientLight(0xffffff, 0.5)
  scene.add(ambientLight)

  const directionalLight = new THREE.DirectionalLight(0x00e0ff, 1)
  directionalLight.position.set(5, 5, 5)
  scene.add(directionalLight)

  // Add some sample objects
  const geometry = new THREE.SphereGeometry(1, 32, 32)
  const material = new THREE.MeshPhongMaterial({ 
    color: 0x00e0ff,
    emissive: 0x0088cc,
    shininess: 100
  })

  const sphere = new THREE.Mesh(geometry, material)
  scene.add(sphere)

  // Handle resize
  window.addEventListener('resize', onWindowResize)
}

function onWindowResize() {
  if (!threeContainer.value || !camera || !renderer) return

  camera.aspect = threeContainer.value.clientWidth / threeContainer.value.clientHeight
  camera.updateProjectionMatrix()
  renderer.setSize(
    threeContainer.value.clientWidth,
    threeContainer.value.clientHeight
  )
}

function animate() {
  animationId = requestAnimationFrame(animate)
  
  // Rotate the sphere
  if (scene) {
    scene.rotation.y += 0.01
  }
  
  if (renderer && scene && camera) {
    renderer.render(scene, camera)
  }
}
</script>

<style scoped lang="scss">
.dashboard-view {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--spacing-lg);
  
  .page-title {
    font-size: 1.8rem;
    font-weight: 700;
    background: linear-gradient(45deg, var(--color-primary), var(--color-accent));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }
  
  .controls {
    display: flex;
    gap: var(--spacing-md);
    
    .time-selector {
      width: 150px;
    }
  }
}

.dashboard-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-auto-rows: minmax(200px, auto);
  gap: var(--spacing-lg);
  flex: 1;
  overflow: auto;
  
  .grid-item {
    padding: var(--spacing-md);
    
    &.full-width {
      grid-column: span 4;
      height: 400px;
    }
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: var(--spacing-md);
      
      h3 {
        font-size: 1.2rem;
        font-weight: 600;
      }
    }
    
    .card-content {
      height: calc(100% - 40px);
      
      .three-container {
        width: 100%;
        height: 100%;
        border-radius: var(--radius-md);
        overflow: hidden;
      }
    }
  }
}

.metric-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: var(--spacing-md);
  
  .metric-item {
    padding: var(--spacing-md);
    border-radius: var(--radius-md);
    background: rgba(0, 224, 255, 0.05);
    
    .metric-label {
      font-size: 0.9rem;
      color: var(--text-secondary);
      margin-bottom: var(--spacing-sm);
    }
    
    .metric-value {
      font-size: 1.5rem;
      font-weight: 700;
      margin-bottom: var(--spacing-sm);
    }
    
    .metric-trend {
      font-size: 0.8rem;
      
      &.positive {
        color: var(--color-success);
      }
      
      &.negative {
        color: var(--color-danger);
      }
      
      &.warning {
        color: var(--color-warning);
      }
    }
  }
}

.risk-chart {
  .risk-item {
    margin-bottom: var(--spacing-md);
    
    .risk-info {
      display: flex;
      justify-content: space-between;
      margin-bottom: var(--spacing-sm);
      
      .risk-name {
        font-weight: 500;
      }
      
      .risk-level {
        padding: 2px 8px;
        border-radius: var(--radius-sm);
        font-size: 0.8rem;
        font-weight: 600;
        
        &.high {
          background: rgba(255, 77, 79, 0.2);
          color: var(--color-danger);
        }
        
        &.medium {
          background: rgba(255, 170, 51, 0.2);
          color: var(--color-warning);
        }
        
        &.low {
          background: rgba(0, 245, 168, 0.2);
          color: var(--color-success);
        }
      }
    }
    
    .risk-progress {
      :deep(.el-progress-bar__outer) {
        background: rgba(255, 255, 255, 0.1);
      }
    }
  }
}

.value-metrics {
  display: grid;
  grid-template-columns: 1fr;
  gap: var(--spacing-md);
  
  .value-item {
    padding: var(--spacing-md);
    border-radius: var(--radius-md);
    background: rgba(163, 103, 255, 0.05);
    
    .value-label {
      font-size: 0.9rem;
      color: var(--text-secondary);
      margin-bottom: var(--spacing-sm);
    }
    
    .value-value {
      font-size: 1.3rem;
      font-weight: 700;
    }
  }
}

.ai-insights {
  .insight-item {
    padding: var(--spacing-md);
    border-radius: var(--radius-md);
    background: rgba(0, 224, 255, 0.05);
    margin-bottom: var(--spacing-md);
    
    .insight-title {
      font-weight: 600;
      margin-bottom: var(--spacing-sm);
    }
    
    .insight-desc {
      font-size: 0.9rem;
      color: var(--text-secondary);
      margin-bottom: var(--spacing-md);
    }
    
    .insight-confidence {
      display: flex;
      align-items: center;
      gap: var(--spacing-md);
      
      :deep(.el-progress) {
        flex: 1;
      }
      
      span {
        font-size: 0.8rem;
        color: var(--text-secondary);
        white-space: nowrap;
      }
    }
  }
}

.data-stream {
  .stream-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: var(--spacing-sm) 0;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    
    &:last-child {
      border-bottom: none;
    }
    
    .stream-name {
      flex: 1;
    }
    
    .stream-value {
      flex: 1;
      text-align: center;
      font-weight: 600;
    }
    
    .stream-status {
      flex: 1;
      text-align: right;
      font-size: 0.8rem;
      
      &.normal {
        color: var(--color-success);
      }
      
      &.positive {
        color: var(--color-success);
      }
      
      &.warning {
        color: var(--color-warning);
      }
    }
  }
}

@media (max-width: 1200px) {
  .dashboard-grid {
    grid-template-columns: repeat(3, 1fr);
    
    .grid-item.full-width {
      grid-column: span 3;
    }
  }
}

@media (max-width: 992px) {
  .dashboard-grid {
    grid-template-columns: repeat(2, 1fr);
    
    .grid-item.full-width {
      grid-column: span 2;
    }
  }
}

@media (max-width: 768px) {
  .dashboard-grid {
    grid-template-columns: 1fr;
    
    .grid-item.full-width {
      grid-column: span 1;
    }
    
    .dashboard-header {
      flex-direction: column;
      align-items: flex-start;
      gap: var(--spacing-md);
    }
  }
}
</style>