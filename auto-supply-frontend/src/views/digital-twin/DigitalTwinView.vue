<template>
  <div class="digital-twin-view">
    <div class="view-header">
      <h2 class="page-title">供应链数字孪生</h2>
      <div class="controls">
        <el-button type="primary" class="cyber-button" @click="refreshData">
          <i class="el-icon-refresh"></i> 刷新
        </el-button>
        <el-button class="cyber-button" @click="toggleSimulation">
          <i class="el-icon-video-play" v-if="!isSimulating"></i>
          <i class="el-icon-video-pause" v-else></i>
          {{ isSimulating ? '暂停' : '模拟' }}
        </el-button>
        <el-dropdown trigger="click" @command="handleScenarioChange">
          <el-button class="cyber-button">
            场景模式 <i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="normal">正常运营</el-dropdown-item>
              <el-dropdown-item command="disruption">供应中断</el-dropdown-item>
              <el-dropdown-item command="demand-surge">需求激增</el-dropdown-item>
              <el-dropdown-item command="quality-issue">质量问题</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <div class="twin-content">
      <!-- 3D可视化容器 -->
      <div class="visualization-panel cyber-card">
        <div class="panel-header">
          <h3>3D供应链网络</h3>
          <div class="header-controls">
            <el-tag type="success">实时</el-tag>
          </div>
        </div>
        <div class="panel-content">
          <div ref="threeContainer" class="three-container"></div>
        </div>
      </div>

      <!-- 实时数据面板 -->
      <div class="data-panel">
        <!-- 供应链健康度 -->
        <div class="health-card cyber-card">
          <div class="card-header">
            <h3>供应链健康度</h3>
            <el-tooltip content="基于AI算法计算的综合健康评分" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </div>
          <div class="card-content">
            <div class="health-score">
              <div class="score-value">{{ healthScore }}<span class="score-max">/100</span></div>
              <div class="score-label">综合评分</div>
            </div>
            <div class="health-indicators">
              <div class="indicator" v-for="indicator in healthIndicators" :key="indicator.name">
                <div class="indicator-name">{{ indicator.name }}</div>
                <div class="indicator-bar">
                  <el-progress 
                    :percentage="indicator.value" 
                    :stroke-width="6"
                    :color="getIndicatorColor(indicator.value)"
                  />
                </div>
                <div class="indicator-value">{{ indicator.value }}%</div>
              </div>
            </div>
          </div>
        </div>

        <!-- 实时事件流 -->
        <div class="events-card cyber-card">
          <div class="card-header">
            <h3>实时事件流</h3>
            <el-switch v-model="autoScroll" active-text="自动滚动" />
          </div>
          <div class="card-content">
            <div ref="eventList" class="event-list">
              <div 
                class="event-item" 
                v-for="event in events" 
                :key="event.id"
                :class="`event-${event.type}`"
              >
                <div class="event-icon">
                  <i :class="getEventIcon(event.type)"></i>
                </div>
                <div class="event-details">
                  <div class="event-title">{{ event.title }}</div>
                  <div class="event-description">{{ event.description }}</div>
                  <div class="event-meta">
                    <span class="event-time">{{ event.time }}</span>
                    <span class="event-location">{{ event.location }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 关键节点状态 -->
        <div class="nodes-card cyber-card">
          <div class="card-header">
            <h3>关键节点状态</h3>
            <el-button type="text" @click="viewAllNodes">查看全部</el-button>
          </div>
          <div class="card-content">
            <div class="node-grid">
              <div 
                class="node-item" 
                v-for="node in keyNodes" 
                :key="node.id"
                :class="`node-${node.status}`"
              >
                <div class="node-icon">
                  <i :class="getNodeIcon(node.type)"></i>
                </div>
                <div class="node-info">
                  <div class="node-name">{{ node.name }}</div>
                  <div class="node-status">{{ getNodeStatusText(node.status) }}</div>
                </div>
                <div class="node-metric">
                  <div class="metric-value">{{ node.metric.value }}</div>
                  <div class="metric-unit">{{ node.metric.unit }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick } from 'vue'
import * as THREE from 'three'

// Reactive data
const isSimulating = ref(false)
const healthScore = ref(87.5)
const autoScroll = ref(true)
const currentScenario = ref('normal')

// Health indicators
const healthIndicators = ref([
  { name: '供应商健康度', value: 92 },
  { name: '库存水位', value: 78 },
  { name: '产能利用率', value: 85 },
  { name: '物流准时率', value: 94 },
  { name: '质量合格率', value: 96 }
])

// Real-time events
const events = ref([
  { 
    id: 1, 
    type: 'info', 
    title: '订单确认', 
    description: '客户订单#ORD-2023-001245已确认', 
    time: '14:32:15', 
    location: '上海仓库' 
  },
  { 
    id: 2, 
    type: 'warning', 
    title: '库存预警', 
    description: '原材料A库存低于安全水位', 
    time: '14:28:42', 
    location: '深圳工厂' 
  },
  { 
    id: 3, 
    type: 'success', 
    title: '运输完成', 
    description: '货物已送达北京配送中心', 
    time: '14:15:30', 
    location: '北京DC' 
  },
  { 
    id: 4, 
    type: 'error', 
    title: '质量异常', 
    description: '批次#LOT-B20230401检测不合格', 
    time: '14:10:22', 
    location: '质检部门' 
  },
  { 
    id: 5, 
    type: 'info', 
    title: '生产计划', 
    description: '新产品线生产计划已下达', 
    time: '14:05:18', 
    location: '成都工厂' 
  }
])

// Key nodes
const keyNodes = ref([
  { 
    id: 1, 
    name: '上海总部', 
    type: 'headquarters', 
    status: 'normal',
    metric: { value: '98.5%', unit: '在线率' }
  },
  { 
    id: 2, 
    name: '深圳工厂', 
    type: 'factory', 
    status: 'warning',
    metric: { value: '87%', unit: '产能' }
  },
  { 
    id: 3, 
    name: '宁波港', 
    type: 'port', 
    status: 'normal',
    metric: { value: '12', unit: '船舶' }
  },
  { 
    id: 4, 
    name: '北京DC', 
    type: 'warehouse', 
    status: 'critical',
    metric: { value: '23%', unit: '库存' }
  }
])

// Three.js variables
const threeContainer = ref<HTMLElement | null>(null)
let scene: THREE.Scene
let camera: THREE.PerspectiveCamera
let renderer: THREE.WebGLRenderer
let animationId: number
let supplyChainGroup: THREE.Group

// Event list reference for scrolling
const eventList = ref<HTMLElement | null>(null)

// Lifecycle hooks
onMounted(() => {
  initThreeJS()
  animate()
  startEventSimulation()
})

onUnmounted(() => {
  if (animationId) {
    cancelAnimationFrame(animationId)
  }
  if (renderer) {
    renderer.dispose()
  }
})

// Methods
function refreshData() {
  // Simulate data refresh
  healthScore.value = Math.min(100, Math.max(0, healthScore.value + (Math.random() * 10 - 5)))
  
  // Update indicators
  healthIndicators.value = healthIndicators.value.map(indicator => ({
    ...indicator,
    value: Math.min(100, Math.max(0, indicator.value + (Math.random() * 6 - 3)))
  }))
  
  // Add new event
  const eventTypes = ['info', 'warning', 'success', 'error']
  const eventType = eventTypes[Math.floor(Math.random() * eventTypes.length)]
  
  events.value.unshift({
    id: Date.now(),
    type: eventType,
    title: `模拟事件 ${eventType}`,
    description: `这是一条${eventType === 'info' ? '信息' : eventType === 'warning' ? '警告' : eventType === 'success' ? '成功' : '错误'}类型的模拟事件`,
    time: new Date().toLocaleTimeString(),
    location: '模拟位置'
  })
  
  // Keep only last 20 events
  if (events.value.length > 20) {
    events.value.pop()
  }
  
  // Scroll to top if auto-scroll enabled
  if (autoScroll.value && eventList.value) {
    nextTick(() => {
      eventList.value!.scrollTop = 0
    })
  }
}

function toggleSimulation() {
  isSimulating.value = !isSimulating.value
}

function handleScenarioChange(scenario: string) {
  currentScenario.value = scenario
  // In a real app, this would trigger different simulation behaviors
}

function viewAllNodes() {
  // In a real app, this would navigate to a detailed nodes view
  console.log('View all nodes')
}

function getIndicatorColor(value: number) {
  if (value >= 90) return '#00f5a8'
  if (value >= 70) return '#00e0ff'
  if (value >= 50) return '#ffaa33'
  return '#ff4d4f'
}

function getEventIcon(type: string) {
  switch (type) {
    case 'info': return 'el-icon-info'
    case 'warning': return 'el-icon-warning'
    case 'success': return 'el-icon-check'
    case 'error': return 'el-icon-circle-close'
    default: return 'el-icon-info'
  }
}

function getNodeIcon(type: string) {
  switch (type) {
    case 'headquarters': return 'el-icon-office-building'
    case 'factory': return 'el-icon-guide'
    case 'port': return 'el-icon-ship'
    case 'warehouse': return 'el-icon-box'
    default: return 'el-icon-location'
  }
}

function getNodeStatusText(status: string) {
  switch (status) {
    case 'normal': return '正常'
    case 'warning': return '警告'
    case 'critical': return '危急'
    default: return '未知'
  }
}

// Three.js initialization
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
  camera.position.set(0, 5, 10)

  // Create renderer
  renderer = new THREE.WebGLRenderer({ antialias: true })
  renderer.setSize(
    threeContainer.value.clientWidth,
    threeContainer.value.clientHeight
  )
  threeContainer.value.appendChild(renderer.domElement)

  // Add lights
  const ambientLight = new THREE.AmbientLight(0xffffff, 0.6)
  scene.add(ambientLight)

  const directionalLight = new THREE.DirectionalLight(0x00e0ff, 1)
  directionalLight.position.set(10, 20, 15)
  scene.add(directionalLight)

  // Create supply chain network visualization
  createSupplyChainNetwork()

  // Add orbit controls
  // Note: In a real implementation, you would import and use OrbitControls
  // For this example, we'll simulate basic rotation

  // Handle resize
  window.addEventListener('resize', onWindowResize)
}

function createSupplyChainNetwork() {
  supplyChainGroup = new THREE.Group()
  scene.add(supplyChainGroup)

  // Create nodes (factories, warehouses, ports, etc.)
  const nodePositions = [
    { x: -5, y: 0, z: -3, color: 0x00e0ff, name: '供应商' },
    { x: -2, y: 0, z: 0, color: 0x00f5a8, name: '工厂A' },
    { x: 2, y: 0, z: 0, color: 0x00f5a8, name: '工厂B' },
    { x: 5, y: 0, z: -2, color: 0xffaa33, name: '港口' },
    { x: 0, y: 0, z: 3, color: 0xff4d4f, name: '仓库' },
    { x: 0, y: 0, z: 6, color: 0xa367ff, name: '配送中心' }
  ]

  const nodes = []
  for (const pos of nodePositions) {
    const geometry = new THREE.SphereGeometry(0.5, 32, 32)
    const material = new THREE.MeshPhongMaterial({ 
      color: pos.color,
      emissive: pos.color,
      emissiveIntensity: 0.2
    })
    const node = new THREE.Mesh(geometry, material)
    node.position.set(pos.x, pos.y, pos.z)
    supplyChainGroup.add(node)
    nodes.push(node)
  }

  // Create connections (supply chain flows)
  const connections = [
    { start: 0, end: 1 },
    { start: 0, end: 2 },
    { start: 1, end: 3 },
    { start: 2, end: 3 },
    { start: 3, end: 4 },
    { start: 4, end: 5 }
  ]

  for (const conn of connections) {
    const startNode = nodePositions[conn.start]
    const endNode = nodePositions[conn.end]
    
    const points = []
    points.push(new THREE.Vector3(startNode.x, startNode.y, startNode.z))
    points.push(new THREE.Vector3(endNode.x, endNode.y, endNode.z))
    
    const geometry = new THREE.BufferGeometry().setFromPoints(points)
    const material = new THREE.LineBasicMaterial({ 
      color: 0xffffff,
      transparent: true,
      opacity: 0.6
    })
    
    const line = new THREE.Line(geometry, material)
    supplyChainGroup.add(line)
  }
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
  
  // Rotate the supply chain group
  if (supplyChainGroup) {
    supplyChainGroup.rotation.y += 0.005
  }
  
  if (renderer && scene && camera) {
    renderer.render(scene, camera)
  }
}

function startEventSimulation() {
  setInterval(() => {
    if (isSimulating.value) {
      refreshData()
    }
  }, 5000)
}
</script>

<style scoped lang="scss">
.digital-twin-view {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.view-header {
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
  }
}

.twin-content {
  display: flex;
  flex: 1;
  gap: var(--spacing-lg);
  overflow: hidden;
}

.visualization-panel {
  flex: 2;
  display: flex;
  flex-direction: column;
  
  .panel-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: var(--spacing-md);
    
    h3 {
      font-size: 1.2rem;
      font-weight: 600;
    }
  }
  
  .panel-content {
    flex: 1;
    border-radius: var(--radius-md);
    overflow: hidden;
    background: rgba(0, 0, 0, 0.2);
    
    .three-container {
      width: 100%;
      height: 100%;
    }
  }
}

.data-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
  overflow-y: auto;
  
  .cyber-card {
    flex-shrink: 0;
  }
}

.health-card {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: var(--spacing-md);
    
    h3 {
      font-size: 1.2rem;
      font-weight: 600;
    }
    
    i {
      color: var(--text-secondary);
      cursor: help;
    }
  }
  
  .card-content {
    .health-score {
      text-align: center;
      margin-bottom: var(--spacing-lg);
      
      .score-value {
        font-size: 3rem;
        font-weight: 700;
        background: linear-gradient(45deg, var(--color-primary), var(--color-accent));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
        
        .score-max {
          font-size: 1.5rem;
          font-weight: 400;
        }
      }
      
      .score-label {
        font-size: 1rem;
        color: var(--text-secondary);
      }
    }
    
    .health-indicators {
      .indicator {
        display: flex;
        align-items: center;
        margin-bottom: var(--spacing-md);
        
        &:last-child {
          margin-bottom: 0;
        }
        
        .indicator-name {
          width: 100px;
          font-size: 0.9rem;
        }
        
        .indicator-bar {
          flex: 1;
          margin: 0 var(--spacing-md);
          
          :deep(.el-progress-bar__outer) {
            background: rgba(255, 255, 255, 0.1);
          }
        }
        
        .indicator-value {
          width: 40px;
          text-align: right;
          font-size: 0.9rem;
          font-weight: 600;
        }
      }
    }
  }
}

.events-card {
  flex: 1;
  
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
    
    .event-list {
      height: 100%;
      overflow-y: auto;
      
      .event-item {
        display: flex;
        padding: var(--spacing-md);
        border-radius: var(--radius-md);
        margin-bottom: var(--spacing-md);
        transition: all 0.3s ease;
        
        &:last-child {
          margin-bottom: 0;
        }
        
        &.event-info {
          background: rgba(0, 224, 255, 0.05);
          border-left: 3px solid var(--color-primary);
        }
        
        &.event-warning {
          background: rgba(255, 170, 51, 0.05);
          border-left: 3px solid var(--color-warning);
        }
        
        &.event-success {
          background: rgba(0, 245, 168, 0.05);
          border-left: 3px solid var(--color-success);
        }
        
        &.event-error {
          background: rgba(255, 77, 79, 0.05);
          border-left: 3px solid var(--color-danger);
        }
        
        .event-icon {
          margin-right: var(--spacing-md);
          font-size: 1.2rem;
          
          .event-info & {
            color: var(--color-primary);
          }
          
          .event-warning & {
            color: var(--color-warning);
          }
          
          .event-success & {
            color: var(--color-success);
          }
          
          .event-error & {
            color: var(--color-danger);
          }
        }
        
        .event-details {
          flex: 1;
          
          .event-title {
            font-weight: 600;
            margin-bottom: var(--spacing-sm);
          }
          
          .event-description {
            font-size: 0.9rem;
            color: var(--text-secondary);
            margin-bottom: var(--spacing-sm);
          }
          
          .event-meta {
            display: flex;
            font-size: 0.8rem;
            color: var(--text-tertiary);
            
            .event-time {
              margin-right: var(--spacing-md);
            }
          }
        }
      }
    }
  }
}

.nodes-card {
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
    .node-grid {
      display: grid;
      grid-template-columns: 1fr;
      gap: var(--spacing-md);
      
      .node-item {
        display: flex;
        align-items: center;
        padding: var(--spacing-md);
        border-radius: var(--radius-md);
        transition: all 0.3s ease;
        
        &.node-normal {
          background: rgba(0, 245, 168, 0.05);
        }
        
        &.node-warning {
          background: rgba(255, 170, 51, 0.05);
        }
        
        &.node-critical {
          background: rgba(255, 77, 79, 0.05);
        }
        
        .node-icon {
          margin-right: var(--spacing-md);
          font-size: 1.2rem;
          
          .node-normal & {
            color: var(--color-success);
          }
          
          .node-warning & {
            color: var(--color-warning);
          }
          
          .node-critical & {
            color: var(--color-danger);
          }
        }
        
        .node-info {
          flex: 1;
          
          .node-name {
            font-weight: 600;
            margin-bottom: var(--spacing-sm);
          }
          
          .node-status {
            font-size: 0.8rem;
            
            .node-normal & {
              color: var(--color-success);
            }
            
            .node-warning & {
              color: var(--color-warning);
            }
            
            .node-critical & {
              color: var(--color-danger);
            }
          }
        }
        
        .node-metric {
          text-align: right;
          
          .metric-value {
            font-weight: 700;
            font-size: 1.1rem;
          }
          
          .metric-unit {
            font-size: 0.8rem;
            color: var(--text-secondary);
          }
        }
      }
    }
  }
}

@media (max-width: 1200px) {
  .twin-content {
    flex-direction: column;
  }
  
  .visualization-panel {
    flex: 1;
    min-height: 400px;
  }
  
  .data-panel {
    flex: 1;
  }
}
</style>