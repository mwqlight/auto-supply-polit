<template>
  <div class="process-view">
    <div class="view-header">
      <h2 class="page-title">流程维管控</h2>
      <div class="controls">
        <el-button type="primary" class="cyber-button" @click="optimizeProcess">
          <i class="el-icon-magic-stick"></i> 流程优化
        </el-button>
        <el-button class="cyber-button" @click="refreshData">
          <i class="el-icon-refresh"></i> 刷新
        </el-button>
      </div>
    </div>

    <div class="process-content">
      <!-- 流程概览卡片 -->
      <div class="overview-cards">
        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(0, 224, 255, 0.1);">
            <i class="el-icon-document-checked"></i>
          </div>
          <div class="card-content">
            <div class="card-title">订单履约率</div>
            <div class="card-value">{{ orderFulfillment.rate }}%</div>
            <div class="card-change" :class="orderFulfillment.change > 0 ? 'positive' : 'negative'">
              {{ orderFulfillment.change > 0 ? '+' : '' }}{{ orderFulfillment.change }}%
              <i :class="orderFulfillment.change > 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(163, 103, 255, 0.1);">
            <i class="el-icon-timer"></i>
          </div>
          <div class="card-content">
            <div class="card-title">平均交付周期</div>
            <div class="card-value">{{ deliveryCycle.average }}天</div>
            <div class="card-change" :class="deliveryCycle.change < 0 ? 'positive' : 'negative'">
              {{ deliveryCycle.change > 0 ? '+' : '' }}{{ deliveryCycle.change }}天
              <i :class="deliveryCycle.change < 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(0, 245, 168, 0.1);">
            <i class="el-icon-data-analysis"></i>
          </div>
          <div class="card-content">
            <div class="card-title">流程效率</div>
            <div class="card-value">{{ processEfficiency.rate }}%</div>
            <div class="card-change" :class="processEfficiency.change > 0 ? 'positive' : 'negative'">
              {{ processEfficiency.change > 0 ? '+' : '' }}{{ processEfficiency.change }}%
              <i :class="processEfficiency.change > 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(255, 170, 51, 0.1);">
            <i class="el-icon-warning-outline"></i>
          </div>
          <div class="card-content">
            <div class="card-title">瓶颈数量</div>
            <div class="card-value">{{ bottleneckCount.current }}</div>
            <div class="card-change" :class="bottleneckCount.change < 0 ? 'positive' : 'negative'">
              {{ bottleneckCount.change > 0 ? '+' : '' }}{{ bottleneckCount.change }}
              <i :class="bottleneckCount.change < 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>
      </div>

      <!-- 流程管理区域 -->
      <div class="management-section">
        <!-- 流程可视化 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>流程可视化</h3>
            <div class="section-controls">
              <el-select v-model="selectedProcess" placeholder="选择流程">
                <el-option label="采购流程" value="procurement" />
                <el-option label="生产流程" value="production" />
                <el-option label="仓储流程" value="warehousing" />
                <el-option label="配送流程" value="distribution" />
              </el-select>
              <el-button type="text" @click="viewProcessDetails">查看详情</el-button>
            </div>
          </div>
          <div class="section-content">
            <div ref="processFlowChart" class="flow-chart"></div>
          </div>
        </div>

        <!-- 关键流程指标 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>关键流程指标</h3>
            <div class="section-controls">
              <el-date-picker
                v-model="kpiDateRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                class="date-range-picker"
              />
              <el-button type="text" @click="exportKPIReport">导出报告</el-button>
            </div>
          </div>
          <div class="section-content">
            <el-table :data="processKPIs" style="width: 100%" stripe>
              <el-table-column prop="name" label="流程环节" width="200" />
              <el-table-column prop="duration" label="平均耗时" width="120" />
              <el-table-column prop="efficiency" label="效率(%)" width="120">
                <template #default="scope">
                  <el-progress 
                    :percentage="scope.row.efficiency" 
                    :stroke-width="10"
                    :color="getEfficiencyColor(scope.row.efficiency)"
                  />
                </template>
              </el-table-column>
              <el-table-column prop="cost" label="单位成本" width="120" />
              <el-table-column prop="quality" label="质量合格率" width="120">
                <template #default="scope">
                  <el-tag :type="getQualityTagType(scope.row.quality)">
                    {{ scope.row.quality }}%
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="bottleneck" label="瓶颈状态" width="120">
                <template #default="scope">
                  <el-tag :type="scope.row.bottleneck ? 'danger' : 'success'">
                    {{ scope.row.bottleneck ? '是' : '否' }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button type="text" size="small" @click="optimizeStep(scope.row)">优化</el-button>
                  <el-button type="text" size="small" @click="viewStepDetails(scope.row)">详情</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>

        <!-- 流程异常监控 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>流程异常监控</h3>
            <div class="section-controls">
              <el-select v-model="anomalyFilter" placeholder="异常类型">
                <el-option label="全部" value="" />
                <el-option label="超时" value="timeout" />
                <el-option label="质量异常" value="quality" />
                <el-option label="成本超支" value="cost" />
              </el-select>
              <el-button type="text" @click="viewAnomalyAnalysis">异常分析</el-button>
            </div>
          </div>
          <div class="section-content">
            <el-table :data="filteredAnomalies" style="width: 100%" stripe>
              <el-table-column prop="id" label="异常编号" width="120" />
              <el-table-column prop="process" label="流程环节" width="150" />
              <el-table-column prop="type" label="异常类型" width="120">
                <template #default="scope">
                  <el-tag :type="getAnomalyType(scope.row.type)">
                    {{ getAnomalyTypeName(scope.row.type) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="description" label="异常描述" />
              <el-table-column prop="time" label="发生时间" width="180" />
              <el-table-column prop="status" label="处理状态" width="120">
                <template #default="scope">
                  <el-tag :type="getAnomalyStatusType(scope.row.status)">
                    {{ getAnomalyStatusName(scope.row.status) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button type="text" size="small" @click="handleAnomaly(scope.row)">处理</el-button>
                  <el-button type="text" size="small" @click="viewAnomalyDetails(scope.row)">详情</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </div>

    <!-- 流程优化对话框 -->
    <el-dialog v-model="optimizationDialogVisible" title="流程优化建议" width="800px">
      <div class="optimization-content">
        <div class="optimization-summary">
          <h4>优化概要</h4>
          <p>{{ optimizationSummary.recommendation }}</p>
        </div>
        
        <div class="optimization-details">
          <h4>详细建议</h4>
          <ul>
            <li v-for="(suggestion, index) in optimizationDetails.suggestions" :key="index">
              {{ suggestion }}
            </li>
          </ul>
        </div>
        
        <div class="optimization-impact">
          <h4>预期影响</h4>
          <div class="impact-metrics">
            <div class="impact-item">
              <div class="impact-label">效率提升</div>
              <div class="impact-value positive">+{{ optimizationImpact.efficiency }}%</div>
            </div>
            <div class="impact-item">
              <div class="impact-label">成本节约</div>
              <div class="impact-value positive">-{{ optimizationImpact.cost }}%</div>
            </div>
            <div class="impact-item">
              <div class="impact-label">周期缩短</div>
              <div class="impact-value positive">-{{ optimizationImpact.cycle }}天</div>
            </div>
          </div>
        </div>
      </div>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="optimizationDialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="applyOptimization">应用优化</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'

// Reactive data
const selectedProcess = ref('production')
const kpiDateRange = ref([])
const anomalyFilter = ref('')
const optimizationDialogVisible = ref(false)

// Mock data
const orderFulfillment = ref({
  rate: 94.2,
  change: 2.1
})

const deliveryCycle = ref({
  average: 5.7,
  change: -0.3
})

const processEfficiency = ref({
  rate: 87.5,
  change: 3.2
})

const bottleneckCount = ref({
  current: 3,
  change: -1
})

const processKPIs = ref([
  { name: '原料采购', duration: '2.5天', efficiency: 92, cost: '¥1200', quality: 98.5, bottleneck: false },
  { name: '生产加工', duration: '3.2天', efficiency: 85, cost: '¥2800', quality: 96.2, bottleneck: true },
  { name: '质量检验', duration: '0.8天', efficiency: 95, cost: '¥500', quality: 99.1, bottleneck: false },
  { name: '仓储管理', duration: '1.5天', efficiency: 88, cost: '¥800', quality: 97.8, bottleneck: false },
  { name: '物流配送', duration: '2.8天', efficiency: 82, cost: '¥1500', quality: 95.6, bottleneck: true }
])

const anomalies = ref([
  { id: 'ANM20230401001', process: '生产加工', type: 'timeout', description: '加工时间超过标准20%', time: '2023-04-01 14:32:15', status: 'pending' },
  { id: 'ANM20230401002', process: '质量检验', type: 'quality', description: '产品合格率低于标准3%', time: '2023-04-01 16:45:22', status: 'resolved' },
  { id: 'ANM20230402001', process: '物流配送', type: 'timeout', description: '配送延误超过24小时', time: '2023-04-02 09:15:30', status: 'processing' },
  { id: 'ANM20230402002', process: '原料采购', type: 'cost', description: '采购成本超出预算5%', time: '2023-04-02 11:22:45', status: 'pending' }
])

const optimizationSummary = ref({
  recommendation: '建议优化生产加工环节，通过引入自动化设备提升效率'
})

const optimizationDetails = ref({
  suggestions: [
    '引入自动化装配线减少人工操作时间',
    '优化生产排程算法提高设备利用率',
    '加强员工技能培训提升操作熟练度',
    '实施精益生产减少浪费'
  ]
})

const optimizationImpact = ref({
  efficiency: 12.5,
  cost: 8.3,
  cycle: 1.2
})

// Computed properties
const filteredAnomalies = computed(() => {
  if (!anomalyFilter.value) return anomalies.value
  return anomalies.value.filter(anomaly => anomaly.type === anomalyFilter.value)
})

// Chart references
const processFlowChart = ref<HTMLElement | null>(null)
let flowChartInstance: echarts.ECharts | null = null

// Lifecycle
onMounted(() => {
  initCharts()
})

// Methods
function optimizeProcess() {
  // Show optimization dialog with mock recommendations
  optimizationDialogVisible.value = true
}

function refreshData() {
  // In a real app, this would fetch fresh data from the backend
  console.log('Refreshing process data...')
}

function viewProcessDetails() {
  console.log('View process details for:', selectedProcess.value)
}

function exportKPIReport() {
  console.log('Export KPI report')
}

function optimizeStep(step: any) {
  console.log('Optimize step:', step)
  optimizeProcess()
}

function viewStepDetails(step: any) {
  console.log('View step details:', step)
}

function viewAnomalyAnalysis() {
  console.log('View anomaly analysis')
}

function handleAnomaly(anomaly: any) {
  console.log('Handle anomaly:', anomaly)
  // Update status to processing
  const index = anomalies.value.findIndex(a => a.id === anomaly.id)
  if (index !== -1) {
    anomalies.value[index].status = 'processing'
  }
}

function viewAnomalyDetails(anomaly: any) {
  console.log('View anomaly details:', anomaly)
}

function applyOptimization() {
  console.log('Apply optimization')
  optimizationDialogVisible.value = false
  // In a real app, this would send the optimization request to the backend
}

function getEfficiencyColor(efficiency: number) {
  if (efficiency >= 90) return '#00f5a8'
  if (efficiency >= 70) return '#00e0ff'
  if (efficiency >= 50) return '#ffaa33'
  return '#ff4d4f'
}

function getQualityTagType(quality: number) {
  if (quality >= 95) return 'success'
  if (quality >= 90) return 'warning'
  return 'danger'
}

function getAnomalyType(type: string) {
  switch (type) {
    case 'timeout': return 'warning'
    case 'quality': return 'danger'
    case 'cost': return 'info'
    default: return 'info'
  }
}

function getAnomalyTypeName(type: string) {
  switch (type) {
    case 'timeout': return '超时'
    case 'quality': return '质量异常'
    case 'cost': return '成本超支'
    default: return '其他'
  }
}

function getAnomalyStatusType(status: string) {
  switch (status) {
    case 'pending': return 'warning'
    case 'processing': return 'primary'
    case 'resolved': return 'success'
    default: return 'info'
  }
}

function getAnomalyStatusName(status: string) {
  switch (status) {
    case 'pending': return '待处理'
    case 'processing': return '处理中'
    case 'resolved': return '已解决'
    default: return '未知'
  }
}

function initCharts() {
  nextTick(() => {
    if (processFlowChart.value) {
      flowChartInstance = echarts.init(processFlowChart.value)
      flowChartInstance.setOption(getFlowChartOption())
      
      // Handle window resize
      window.addEventListener('resize', () => {
        if (flowChartInstance) {
          flowChartInstance.resize()
        }
      })
    }
  })
}

function getFlowChartOption() {
  return {
    title: {
      text: '生产流程可视化',
      textStyle: {
        color: 'rgba(255, 255, 255, 0.8)',
        fontSize: 14
      }
    },
    tooltip: {
      trigger: 'item',
      triggerOn: 'mousemove'
    },
    series: [
      {
        type: 'tree',
        data: [{
          name: '订单接收',
          children: [
            {
              name: '原料采购',
              children: [
                { name: '入库检验' },
                { name: '仓储管理' }
              ]
            },
            {
              name: '生产加工',
              children: [
                { name: '加工准备' },
                { name: '装配生产' },
                { name: '质量检验' }
              ]
            },
            {
              name: '物流配送',
              children: [
                { name: '打包出库' },
                { name: '运输配送' },
                { name: '客户签收' }
              ]
            }
          ]
        }],
        top: '1%',
        left: '7%',
        bottom: '1%',
        right: '20%',
        symbolSize: 12,
        symbol: 'circle',
        orient: 'vertical',
        expandAndCollapse: true,
        label: {
          position: 'top',
          rotate: -90,
          verticalAlign: 'middle',
          align: 'right',
          fontSize: 12,
          color: 'rgba(255, 255, 255, 0.8)'
        },
        leaves: {
          label: {
            position: 'bottom',
            rotate: -90,
            verticalAlign: 'middle',
            align: 'left'
          }
        },
        emphasis: {
          focus: 'descendant'
        },
        animationDurationUpdate: 750,
        lineStyle: {
          color: '#00e0ff',
          width: 2
        },
        itemStyle: {
          color: '#00e0ff',
          borderColor: '#a367ff'
        }
      }
    ]
  }
}
</script>

<style scoped lang="scss">
.process-view {
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
        
        .date-range-picker {
          width: 300px;
        }
      }
    }
    
    .section-content {
      flex: 1;
      overflow: hidden;
      
      .flow-chart {
        width: 100%;
        height: 300px;
      }
      
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

.optimization-content {
  .optimization-summary {
    margin-bottom: var(--spacing-lg);
    
    h4 {
      margin-bottom: var(--spacing-md);
      color: var(--color-primary);
    }
    
    p {
      line-height: 1.6;
    }
  }
  
  .optimization-details {
    margin-bottom: var(--spacing-lg);
    
    h4 {
      margin-bottom: var(--spacing-md);
      color: var(--color-primary);
    }
    
    ul {
      padding-left: var(--spacing-lg);
      
      li {
        margin-bottom: var(--spacing-sm);
        line-height: 1.6;
      }
    }
  }
  
  .optimization-impact {
    h4 {
      margin-bottom: var(--spacing-md);
      color: var(--color-primary);
    }
    
    .impact-metrics {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
      gap: var(--spacing-md);
      
      .impact-item {
        padding: var(--spacing-md);
        border-radius: var(--radius-md);
        background: rgba(0, 224, 255, 0.05);
        text-align: center;
        
        .impact-label {
          font-size: 0.9rem;
          color: var(--text-secondary);
          margin-bottom: var(--spacing-sm);
        }
        
        .impact-value {
          font-size: 1.2rem;
          font-weight: 700;
          
          &.positive {
            color: var(--color-success);
          }
        }
      }
    }
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
          
          .date-range-picker {
            width: 100%;
          }
        }
      }
    }
  }
}
</style>