<template>
  <div class="resource-view">
    <div class="view-header">
      <h2 class="page-title">资源维管控</h2>
      <div class="controls">
        <el-button type="primary" class="cyber-button" @click="addResource">
          <i class="el-icon-plus"></i> 添加资源
        </el-button>
        <el-button class="cyber-button" @click="refreshData">
          <i class="el-icon-refresh"></i> 刷新
        </el-button>
      </div>
    </div>

    <div class="resource-content">
      <!-- 资源概览卡片 -->
      <div class="overview-cards">
        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(0, 224, 255, 0.1);">
            <i class="el-icon-user-solid"></i>
          </div>
          <div class="card-content">
            <div class="card-title">供应商总数</div>
            <div class="card-value">{{ supplierStats.total }}</div>
            <div class="card-change" :class="supplierStats.change > 0 ? 'positive' : 'negative'">
              {{ supplierStats.change > 0 ? '+' : '' }}{{ supplierStats.change }}%
              <i :class="supplierStats.change > 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(163, 103, 255, 0.1);">
            <i class="el-icon-box"></i>
          </div>
          <div class="card-content">
            <div class="card-title">原材料库存</div>
            <div class="card-value">{{ inventoryStats.total }}</div>
            <div class="card-change" :class="inventoryStats.change > 0 ? 'positive' : 'negative'">
              {{ inventoryStats.change > 0 ? '+' : '' }}{{ inventoryStats.change }}%
              <i :class="inventoryStats.change > 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(0, 245, 168, 0.1);">
            <i class="el-icon-guide"></i>
          </div>
          <div class="card-content">
            <div class="card-title">产能利用率</div>
            <div class="card-value">{{ capacityStats.utilization }}%</div>
            <div class="card-change" :class="capacityStats.change > 0 ? 'positive' : 'negative'">
              {{ capacityStats.change > 0 ? '+' : '' }}{{ capacityStats.change }}%
              <i :class="capacityStats.change > 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(255, 170, 51, 0.1);">
            <i class="el-icon-coin"></i>
          </div>
          <div class="card-content">
            <div class="card-title">资金周转率</div>
            <div class="card-value">{{ financialStats.turnoverRate }}</div>
            <div class="card-change" :class="financialStats.change > 0 ? 'positive' : 'negative'">
              {{ financialStats.change > 0 ? '+' : '' }}{{ financialStats.change }}%
              <i :class="financialStats.change > 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>
      </div>

      <!-- 资源管理区域 -->
      <div class="management-section">
        <!-- 供应商管理 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>供应商管理</h3>
            <div class="section-controls">
              <el-input
                v-model="supplierFilter"
                placeholder="搜索供应商..."
                prefix-icon="el-icon-search"
                class="search-input"
              />
              <el-button type="text" @click="viewSupplierAnalysis">分析报告</el-button>
            </div>
          </div>
          <div class="section-content">
            <el-table :data="filteredSuppliers" style="width: 100%" stripe>
              <el-table-column prop="name" label="供应商名称" width="200" />
              <el-table-column prop="category" label="类别" width="120" />
              <el-table-column prop="region" label="地区" width="120" />
              <el-table-column prop="reliability" label="可靠性" width="120">
                <template #default="scope">
                  <el-progress 
                    :percentage="scope.row.reliability" 
                    :stroke-width="10"
                    :color="getReliabilityColor(scope.row.reliability)"
                  />
                </template>
              </el-table-column>
              <el-table-column prop="deliveryTime" label="平均交付时间" width="150" />
              <el-table-column prop="cost" label="成本指数" width="120" />
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button type="text" size="small" @click="editSupplier(scope.row)">编辑</el-button>
                  <el-button type="text" size="small" @click="viewSupplierDetails(scope.row)">详情</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>

        <!-- 库存管理 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>库存管理</h3>
            <div class="section-controls">
              <el-input
                v-model="inventoryFilter"
                placeholder="搜索库存..."
                prefix-icon="el-icon-search"
                class="search-input"
              />
              <el-button type="text" @click="viewInventoryAnalysis">库存分析</el-button>
            </div>
          </div>
          <div class="section-content">
            <el-table :data="filteredInventory" style="width: 100%" stripe>
              <el-table-column prop="material" label="物料名称" width="200" />
              <el-table-column prop="sku" label="SKU" width="150" />
              <el-table-column prop="category" label="类别" width="120" />
              <el-table-column prop="currentStock" label="当前库存" width="120" />
              <el-table-column prop="safetyStock" label="安全库存" width="120" />
              <el-table-column prop="reorderPoint" label="补货点" width="120" />
              <el-table-column prop="status" label="状态" width="120">
                <template #default="scope">
                  <el-tag :type="getStockStatusType(scope.row.status)">
                    {{ scope.row.status }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button type="text" size="small" @click="adjustInventory(scope.row)">调整</el-button>
                  <el-button type="text" size="small" @click="viewInventoryDetails(scope.row)">详情</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>

        <!-- 产能管理 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>产能管理</h3>
            <div class="section-controls">
              <el-date-picker
                v-model="capacityDateRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                class="date-range-picker"
              />
              <el-button type="text" @click="viewCapacityAnalysis">产能分析</el-button>
            </div>
          </div>
          <div class="section-content">
            <div class="capacity-charts">
              <div class="chart-container">
                <div ref="utilizationChart" class="chart"></div>
              </div>
              <div class="chart-container">
                <div ref="efficiencyChart" class="chart"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 供应商对话框 -->
    <el-dialog v-model="supplierDialogVisible" title="供应商信息" width="600px">
      <el-form :model="currentSupplier" label-width="100px">
        <el-form-item label="供应商名称">
          <el-input v-model="currentSupplier.name" />
        </el-form-item>
        <el-form-item label="类别">
          <el-select v-model="currentSupplier.category" placeholder="请选择类别">
            <el-option label="原材料" value="raw-material" />
            <el-option label="零部件" value="component" />
            <el-option label="服务" value="service" />
          </el-select>
        </el-form-item>
        <el-form-item label="地区">
          <el-input v-model="currentSupplier.region" />
        </el-form-item>
        <el-form-item label="可靠性">
          <el-slider v-model="currentSupplier.reliability" :max="100" />
        </el-form-item>
        <el-form-item label="交付时间">
          <el-input v-model="currentSupplier.deliveryTime" />
        </el-form-item>
        <el-form-item label="成本指数">
          <el-input-number v-model="currentSupplier.cost" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="supplierDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveSupplier">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'

// Reactive data
const supplierFilter = ref('')
const inventoryFilter = ref('')
const capacityDateRange = ref([])
const supplierDialogVisible = ref(false)

// Mock data
const supplierStats = ref({
  total: 128,
  change: 5.2
})

const inventoryStats = ref({
  total: 24560,
  change: -2.1
})

const capacityStats = ref({
  utilization: 87.3,
  change: 3.7
})

const financialStats = ref({
  turnoverRate: '4.2x',
  change: 1.8
})

const suppliers = ref([
  { id: 1, name: '上海精密电子有限公司', category: '零部件', region: '华东', reliability: 92, deliveryTime: '3天', cost: 85 },
  { id: 2, name: '深圳创新材料公司', category: '原材料', region: '华南', reliability: 88, deliveryTime: '5天', cost: 72 },
  { id: 3, name: '北京高端制造厂', category: '零部件', region: '华北', reliability: 95, deliveryTime: '2天', cost: 92 },
  { id: 4, name: '广州化学工业集团', category: '原材料', region: '华南', reliability: 85, deliveryTime: '7天', cost: 68 },
  { id: 5, name: '成都智能科技公司', category: '服务', region: '西南', reliability: 90, deliveryTime: '1天', cost: 78 }
])

const inventory = ref([
  { id: 1, material: '硅晶圆', sku: 'SL-WF-001', category: '半导体', currentStock: 1250, safetyStock: 800, reorderPoint: 600, status: '充足' },
  { id: 2, material: '铜箔', sku: 'CU-FL-002', category: '电子', currentStock: 420, safetyStock: 500, reorderPoint: 300, status: '紧张' },
  { id: 3, material: '塑料颗粒', sku: 'PL-GR-003', category: '包装', currentStock: 3800, safetyStock: 3000, reorderPoint: 2000, status: '充足' },
  { id: 4, material: '锂电池', sku: 'LI-BAT-004', category: '能源', currentStock: 180, safetyStock: 300, reorderPoint: 150, status: '不足' },
  { id: 5, material: '传感器芯片', sku: 'SE-CH-005', category: '电子', currentStock: 2500, safetyStock: 2000, reorderPoint: 1200, status: '充足' }
])

const currentSupplier = ref({
  id: 0,
  name: '',
  category: '',
  region: '',
  reliability: 0,
  deliveryTime: '',
  cost: 0
})

// Computed properties
const filteredSuppliers = computed(() => {
  if (!supplierFilter.value) return suppliers.value
  return suppliers.value.filter(supplier => 
    supplier.name.toLowerCase().includes(supplierFilter.value.toLowerCase()) ||
    supplier.category.toLowerCase().includes(supplierFilter.value.toLowerCase()) ||
    supplier.region.toLowerCase().includes(supplierFilter.value.toLowerCase())
  )
})

const filteredInventory = computed(() => {
  if (!inventoryFilter.value) return inventory.value
  return inventory.value.filter(item => 
    item.material.toLowerCase().includes(inventoryFilter.value.toLowerCase()) ||
    item.sku.toLowerCase().includes(inventoryFilter.value.toLowerCase()) ||
    item.category.toLowerCase().includes(inventoryFilter.value.toLowerCase())
  )
})

// Chart references
const utilizationChart = ref<HTMLElement | null>(null)
const efficiencyChart = ref<HTMLElement | null>(null)
let utilizationChartInstance: echarts.ECharts | null = null
let efficiencyChartInstance: echarts.ECharts | null = null

// Lifecycle
onMounted(() => {
  initCharts()
})

// Methods
function addResource() {
  currentSupplier.value = {
    id: 0,
    name: '',
    category: '',
    region: '',
    reliability: 80,
    deliveryTime: '5天',
    cost: 75
  }
  supplierDialogVisible.value = true
}

function editSupplier(supplier: any) {
  currentSupplier.value = { ...supplier }
  supplierDialogVisible.value = true
}

function saveSupplier() {
  if (currentSupplier.value.id === 0) {
    // Add new supplier
    currentSupplier.value.id = suppliers.value.length + 1
    suppliers.value.push({ ...currentSupplier.value })
  } else {
    // Update existing supplier
    const index = suppliers.value.findIndex(s => s.id === currentSupplier.value.id)
    if (index !== -1) {
      suppliers.value[index] = { ...currentSupplier.value }
    }
  }
  supplierDialogVisible.value = false
}

function refreshData() {
  // In a real app, this would fetch fresh data from the backend
  console.log('Refreshing resource data...')
}

function viewSupplierAnalysis() {
  console.log('View supplier analysis')
}

function viewSupplierDetails(supplier: any) {
  console.log('View supplier details:', supplier)
}

function viewInventoryAnalysis() {
  console.log('View inventory analysis')
}

function adjustInventory(item: any) {
  console.log('Adjust inventory:', item)
}

function viewInventoryDetails(item: any) {
  console.log('View inventory details:', item)
}

function viewCapacityAnalysis() {
  console.log('View capacity analysis')
}

function getReliabilityColor(reliability: number) {
  if (reliability >= 90) return '#00f5a8'
  if (reliability >= 70) return '#00e0ff'
  if (reliability >= 50) return '#ffaa33'
  return '#ff4d4f'
}

function getStockStatusType(status: string) {
  switch (status) {
    case '充足': return 'success'
    case '紧张': return 'warning'
    case '不足': return 'danger'
    default: return 'info'
  }
}

function initCharts() {
  nextTick(() => {
    if (utilizationChart.value) {
      utilizationChartInstance = echarts.init(utilizationChart.value)
      utilizationChartInstance.setOption(getUtilizationChartOption())
    }
    
    if (efficiencyChart.value) {
      efficiencyChartInstance = echarts.init(efficiencyChart.value)
      efficiencyChartInstance.setOption(getEfficiencyChartOption())
    }
    
    // Handle window resize
    window.addEventListener('resize', () => {
      if (utilizationChartInstance) {
        utilizationChartInstance.resize()
      }
      if (efficiencyChartInstance) {
        efficiencyChartInstance.resize()
      }
    })
  })
}

function getUtilizationChartOption() {
  return {
    title: {
      text: '产能利用率趋势',
      textStyle: {
        color: 'rgba(255, 255, 255, 0.8)',
        fontSize: 14
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      axisLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.2)' } },
      axisLabel: { color: 'rgba(255, 255, 255, 0.6)' }
    },
    yAxis: {
      type: 'value',
      axisLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.2)' } },
      axisLabel: { color: 'rgba(255, 255, 255, 0.6)' },
      splitLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.1)' } }
    },
    series: [{
      data: [85, 87, 89, 86, 92, 90, 88],
      type: 'line',
      smooth: true,
      areaStyle: {
        color: {
          type: 'linear',
          x: 0, y: 0, x2: 0, y2: 1,
          colorStops: [{
            offset: 0, color: 'rgba(0, 224, 255, 0.3)'
          }, {
            offset: 1, color: 'rgba(0, 224, 255, 0)'
          }]
        }
      },
      lineStyle: { color: '#00e0ff' },
      itemStyle: { color: '#00e0ff' }
    }]
  }
}

function getEfficiencyChartOption() {
  return {
    title: {
      text: '生产效率对比',
      textStyle: {
        color: 'rgba(255, 255, 255, 0.8)',
        fontSize: 14
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['生产线A', '生产线B', '生产线C'],
      textStyle: { color: 'rgba(255, 255, 255, 0.6)' }
    },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五'],
      axisLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.2)' } },
      axisLabel: { color: 'rgba(255, 255, 255, 0.6)' }
    },
    yAxis: {
      type: 'value',
      axisLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.2)' } },
      axisLabel: { color: 'rgba(255, 255, 255, 0.6)' },
      splitLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.1)' } }
    },
    series: [
      {
        name: '生产线A',
        type: 'bar',
        data: [85, 88, 90, 87, 92],
        itemStyle: { color: '#00e0ff' }
      },
      {
        name: '生产线B',
        type: 'bar',
        data: [80, 82, 85, 83, 88],
        itemStyle: { color: '#a367ff' }
      },
      {
        name: '生产线C',
        type: 'bar',
        data: [78, 80, 83, 81, 86],
        itemStyle: { color: '#ffaa33' }
      }
    ]
  }
}
</script>

<style scoped lang="scss">
.resource-view {
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

.overview-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: var(--spacing-lg);
  margin-bottom: var(--spacing-lg);
  
  .overview-card {
    display: flex;
    padding: var(--spacing-md);
    
    .card-icon {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: var(--spacing-md);
      font-size: 1.5rem;
    }
    
    .card-content {
      flex: 1;
      
      .card-title {
        font-size: 0.9rem;
        color: var(--text-secondary);
        margin-bottom: var(--spacing-sm);
      }
      
      .card-value {
        font-size: 1.8rem;
        font-weight: 700;
        margin-bottom: var(--spacing-sm);
      }
      
      .card-change {
        font-size: 0.9rem;
        display: flex;
        align-items: center;
        
        &.positive {
          color: var(--color-success);
        }
        
        &.negative {
          color: var(--color-danger);
        }
        
        i {
          margin-left: var(--spacing-sm);
        }
      }
    }
  }
}

.management-section {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
  flex: 1;
  overflow: hidden;
  
  .section-card {
    flex: 1;
    display: flex;
    flex-direction: column;
    
    .section-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: var(--spacing-md);
      
      h3 {
        font-size: 1.2rem;
        font-weight: 600;
      }
      
      .section-controls {
        display: flex;
        gap: var(--spacing-md);
        align-items: center;
        
        .search-input {
          width: 250px;
        }
        
        .date-range-picker {
          width: 300px;
        }
      }
    }
    
    .section-content {
      flex: 1;
      overflow: hidden;
      
      :deep(.el-table) {
        height: 100%;
        
        .el-table__body-wrapper {
          height: calc(100% - 56px); // Subtract header height
          overflow-y: auto;
        }
      }
    }
  }
}

.capacity-charts {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: var(--spacing-lg);
  height: 100%;
  
  .chart-container {
    .chart {
      width: 100%;
      height: 300px;
    }
  }
}

@media (max-width: 1200px) {
  .capacity-charts {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .view-header {
    flex-direction: column;
    align-items: flex-start;
    gap: var(--spacing-md);
  }
  
  .overview-cards {
    grid-template-columns: 1fr;
  }
  
  .management-section {
    .section-card {
      .section-header {
        flex-direction: column;
        align-items: flex-start;
        gap: var(--spacing-md);
        
        .section-controls {
          width: 100%;
          
          .search-input {
            width: 100%;
          }
          
          .date-range-picker {
            width: 100%;
          }
        }
      }
    }
  }
}
</style>