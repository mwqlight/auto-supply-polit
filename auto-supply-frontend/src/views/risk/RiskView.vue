<template>
  <div class="risk-view">
    <div class="view-header">
      <h2 class="page-title">风险维管控</h2>
      <div class="controls">
        <el-button type="primary" class="cyber-button" @click="assessRisks">
          <i class="el-icon-search"></i> 风险评估
        </el-button>
        <el-button class="cyber-button" @click="refreshData">
          <i class="el-icon-refresh"></i> 刷新
        </el-button>
      </div>
    </div>

    <div class="risk-content">
      <!-- 风险概览卡片 -->
      <div class="overview-cards">
        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(255, 77, 79, 0.1);">
            <i class="el-icon-warning"></i>
          </div>
          <div class="card-content">
            <div class="card-title">高风险项</div>
            <div class="card-value">{{ highRiskCount.current }}</div>
            <div class="card-change" :class="highRiskCount.change > 0 ? 'negative' : 'positive'">
              {{ highRiskCount.change > 0 ? '+' : '' }}{{ highRiskCount.change }}
              <i :class="highRiskCount.change > 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(255, 170, 51, 0.1);">
            <i class="el-icon-warning-outline"></i>
          </div>
          <div class="card-content">
            <div class="card-title">中风险项</div>
            <div class="card-value">{{ mediumRiskCount.current }}</div>
            <div class="card-change" :class="mediumRiskCount.change > 0 ? 'negative' : 'positive'">
              {{ mediumRiskCount.change > 0 ? '+' : '' }}{{ mediumRiskCount.change }}
              <i :class="mediumRiskCount.change > 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(0, 245, 168, 0.1);">
            <i class="el-icon-check"></i>
          </div>
          <div class="card-content">
            <div class="card-title">已缓解风险</div>
            <div class="card-value">{{ mitigatedRiskCount.current }}</div>
            <div class="card-change" :class="mitigatedRiskCount.change > 0 ? 'positive' : 'negative'">
              {{ mitigatedRiskCount.change > 0 ? '+' : '' }}{{ mitigatedRiskCount.change }}
              <i :class="mitigatedRiskCount.change > 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(0, 224, 255, 0.1);">
            <i class="el-icon-timer"></i>
          </div>
          <div class="card-content">
            <div class="card-title">平均响应时间</div>
            <div class="card-value">{{ avgResponseTime.current }}小时</div>
            <div class="card-change" :class="avgResponseTime.change < 0 ? 'positive' : 'negative'">
              {{ avgResponseTime.change > 0 ? '+' : '' }}{{ avgResponseTime.change }}小时
              <i :class="avgResponseTime.change < 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>
      </div>

      <!-- 风险管理区域 -->
      <div class="management-section">
        <!-- 风险热力图 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>风险热力图</h3>
            <div class="section-controls">
              <el-select v-model="heatmapPeriod" placeholder="时间周期">
                <el-option label="近7天" value="7d" />
                <el-option label="近30天" value="30d" />
                <el-option label="近90天" value="90d" />
              </el-select>
              <el-button type="text" @click="viewHeatmapDetails">查看详情</el-button>
            </div>
          </div>
          <div class="section-content">
            <div ref="riskHeatmap" class="heatmap-chart"></div>
          </div>
        </div>

        <!-- 风险清单 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>风险清单</h3>
            <div class="section-controls">
              <el-select v-model="riskCategoryFilter" placeholder="风险类别">
                <el-option label="全部" value="" />
                <el-option label="供应风险" value="supply" />
                <el-option label="需求风险" value="demand" />
                <el-option label="运营风险" value="operation" />
                <el-option label="财务风险" value="financial" />
                <el-option label="合规风险" value="compliance" />
              </el-select>
              <el-select v-model="riskLevelFilter" placeholder="风险等级">
                <el-option label="全部" value="" />
                <el-option label="高风险" value="high" />
                <el-option label="中风险" value="medium" />
                <el-option label="低风险" value="low" />
              </el-select>
              <el-button type="text" @click="exportRiskReport">导出报告</el-button>
            </div>
          </div>
          <div class="section-content">
            <el-table :data="filteredRisks" style="width: 100%" stripe>
              <el-table-column prop="id" label="风险编号" width="120" />
              <el-table-column prop="name" label="风险名称" />
              <el-table-column prop="category" label="风险类别" width="120" />
              <el-table-column prop="level" label="风险等级" width="100">
                <template #default="scope">
                  <el-tag :type="getRiskLevelType(scope.row.level)">
                    {{ getRiskLevelName(scope.row.level) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="probability" label="发生概率" width="100">
                <template #default="scope">
                  {{ scope.row.probability }}%
                </template>
              </el-table-column>
              <el-table-column prop="impact" label="影响程度" width="100">
                <template #default="scope">
                  {{ scope.row.impact }}%
                </template>
              </el-table-column>
              <el-table-column prop="owner" label="责任人" width="120" />
              <el-table-column prop="status" label="状态" width="120">
                <template #default="scope">
                  <el-tag :type="getRiskStatusType(scope.row.status)">
                    {{ getRiskStatusName(scope.row.status) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200">
                <template #default="scope">
                  <el-button type="text" size="small" @click="viewRiskDetails(scope.row)">详情</el-button>
                  <el-button type="text" size="small" @click="mitigateRisk(scope.row)">缓解</el-button>
                  <el-button type="text" size="small" @click="transferRisk(scope.row)">转移</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>

        <!-- 风险趋势分析 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>风险趋势分析</h3>
            <div class="section-controls">
              <el-date-picker
                v-model="trendDateRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                class="date-range-picker"
              />
              <el-button type="text" @click="viewTrendAnalysis">趋势分析</el-button>
            </div>
          </div>
          <div class="section-content">
            <div ref="riskTrendChart" class="trend-chart"></div>
          </div>
        </div>
      </div>
    </div>

    <!-- 风险详情对话框 -->
    <el-dialog v-model="riskDetailDialogVisible" title="风险详情" width="800px">
      <div class="risk-detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="风险编号">{{ currentRisk.id }}</el-descriptions-item>
          <el-descriptions-item label="风险名称">{{ currentRisk.name }}</el-descriptions-item>
          <el-descriptions-item label="风险类别">{{ getRiskCategoryName(currentRisk.category) }}</el-descriptions-item>
          <el-descriptions-item label="风险等级">
            <el-tag :type="getRiskLevelType(currentRisk.level)">
              {{ getRiskLevelName(currentRisk.level) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="发生概率">{{ currentRisk.probability }}%</el-descriptions-item>
          <el-descriptions-item label="影响程度">{{ currentRisk.impact }}%</el-descriptions-item>
          <el-descriptions-item label="风险值">{{ currentRisk.riskValue }}</el-descriptions-item>
          <el-descriptions-item label="责任人">{{ currentRisk.owner }}</el-descriptions-item>
          <el-descriptions-item label="识别时间">{{ currentRisk.identificationTime }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getRiskStatusType(currentRisk.status)">
              {{ getRiskStatusName(currentRisk.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="描述" :span="2">{{ currentRisk.description }}</el-descriptions-item>
          <el-descriptions-item label="潜在影响" :span="2">{{ currentRisk.potentialImpact }}</el-descriptions-item>
          <el-descriptions-item label="缓解措施" :span="2">{{ currentRisk.mitigationMeasures }}</el-descriptions-item>
        </el-descriptions>

        <div class="risk-history" v-if="currentRisk.history && currentRisk.history.length > 0">
          <h4>处理历史</h4>
          <el-timeline>
            <el-timeline-item
              v-for="(item, index) in currentRisk.history"
              :key="index"
              :timestamp="item.time"
              :color="getTimelineColor(item.action)"
            >
              <div class="history-item">
                <div class="action">{{ item.action }}</div>
                <div class="operator">{{ item.operator }}</div>
                <div class="comment">{{ item.comment }}</div>
              </div>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="riskDetailDialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="updateRisk">更新风险</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 风险缓解对话框 -->
    <el-dialog v-model="mitigationDialogVisible" title="风险缓解措施" width="600px">
      <el-form :model="mitigationForm" label-width="100px">
        <el-form-item label="缓解措施">
          <el-input 
            v-model="mitigationForm.measures" 
            type="textarea" 
            :rows="4" 
            placeholder="请输入风险缓解措施"
          />
        </el-form-item>
        <el-form-item label="预计完成时间">
          <el-date-picker 
            v-model="mitigationForm.dueDate" 
            type="date" 
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="责任人">
          <el-select v-model="mitigationForm.assignee" placeholder="选择责任人" style="width: 100%">
            <el-option label="张三" value="zhangsan" />
            <el-option label="李四" value="lisi" />
            <el-option label="王五" value="wangwu" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input 
            v-model="mitigationForm.remarks" 
            type="textarea" 
            :rows="2" 
            placeholder="请输入备注信息"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="mitigationDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitMitigation">提交</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'

// Reactive data
const heatmapPeriod = ref('30d')
const riskCategoryFilter = ref('')
const riskLevelFilter = ref('')
const trendDateRange = ref([])
const riskDetailDialogVisible = ref(false)
const mitigationDialogVisible = ref(false)

// Mock data
const highRiskCount = ref({
  current: 8,
  change: -2
})

const mediumRiskCount = ref({
  current: 15,
  change: 3
})

const mitigatedRiskCount = ref({
  current: 23,
  change: 5
})

const avgResponseTime = ref({
  current: 12.5,
  change: -1.8
})

const risks = ref([
  {
    id: 'RISK202304001',
    name: '关键供应商产能不足',
    category: 'supply',
    level: 'high',
    probability: 75,
    impact: 90,
    riskValue: 67.5,
    owner: '张三',
    status: 'identified',
    identificationTime: '2023-04-01',
    description: '主要原材料供应商产能接近极限，可能影响后续订单交付',
    potentialImpact: '可能导致订单延期15-20天，损失约¥500,000',
    mitigationMeasures: '正在寻找备选供应商，同时与现有供应商协商扩产',
    history: [
      { time: '2023-04-01 10:30:00', action: '风险识别', operator: '风险管理系统', comment: '通过AI监测识别到供应商产能风险' },
      { time: '2023-04-02 14:15:00', action: '初步评估', operator: '张三', comment: '确认风险等级为高风险' }
    ]
  },
  {
    id: 'RISK202304002',
    name: '市场需求波动',
    category: 'demand',
    level: 'medium',
    probability: 60,
    impact: 70,
    riskValue: 42,
    owner: '李四',
    status: 'mitigating',
    identificationTime: '2023-04-03',
    description: '根据市场分析，下一季度需求可能存在±15%的波动',
    potentialImpact: '库存积压或缺货风险，影响现金流约±¥200,000',
    mitigationMeasures: '建立动态库存调整机制，加强市场监测',
    history: [
      { time: '2023-04-03 09:45:00', action: '风险识别', operator: '市场部', comment: '通过季度市场分析报告识别' },
      { time: '2023-04-04 11:20:00', action: '制定缓解措施', operator: '李四', comment: '制定动态库存调整方案' }
    ]
  },
  {
    id: 'RISK202304003',
    name: '汇率波动风险',
    category: 'financial',
    level: 'medium',
    probability: 80,
    impact: 65,
    riskValue: 52,
    owner: '王五',
    status: 'transferred',
    identificationTime: '2023-04-05',
    description: '美元兑人民币汇率近期波动较大，影响进口成本',
    potentialImpact: '进口成本波动±3%，影响利润约¥150,000/月',
    mitigationMeasures: '已购买外汇期权进行风险对冲',
    history: [
      { time: '2023-04-05 13:10:00', action: '风险识别', operator: '财务部', comment: '通过汇率监测系统识别' },
      { time: '2023-04-06 15:30:00', action: '风险转移', operator: '王五', comment: '购买外汇期权完成风险转移' }
    ]
  }
])

const currentRisk = ref({
  id: '',
  name: '',
  category: '',
  level: '',
  probability: 0,
  impact: 0,
  riskValue: 0,
  owner: '',
  status: '',
  identificationTime: '',
  description: '',
  potentialImpact: '',
  mitigationMeasures: '',
  history: [] as any[]
})

const mitigationForm = ref({
  measures: '',
  dueDate: '',
  assignee: '',
  remarks: ''
})

// Computed properties
const filteredRisks = computed(() => {
  let result = risks.value
  if (riskCategoryFilter.value) {
    result = result.filter(risk => risk.category === riskCategoryFilter.value)
  }
  if (riskLevelFilter.value) {
    result = result.filter(risk => risk.level === riskLevelFilter.value)
  }
  return result
})

// Chart references
const riskHeatmap = ref<HTMLElement | null>(null)
const riskTrendChart = ref<HTMLElement | null>(null)
let heatmapInstance: echarts.ECharts | null = null
let trendChartInstance: echarts.ECharts | null = null

// Lifecycle
onMounted(() => {
  initCharts()
})

// Methods
function assessRisks() {
  // In a real app, this would trigger a risk assessment process
  console.log('Assessing risks...')
}

function refreshData() {
  // In a real app, this would fetch fresh data from the backend
  console.log('Refreshing risk data...')
}

function viewHeatmapDetails() {
  console.log('View heatmap details for period:', heatmapPeriod.value)
}

function exportRiskReport() {
  console.log('Export risk report')
}

function viewRiskDetails(risk: any) {
  currentRisk.value = { ...risk }
  riskDetailDialogVisible.value = true
}

function mitigateRisk(risk: any) {
  currentRisk.value = { ...risk }
  mitigationForm.value = {
    measures: risk.mitigationMeasures,
    dueDate: '',
    assignee: risk.owner,
    remarks: ''
  }
  mitigationDialogVisible.value = true
}

function transferRisk(risk: any) {
  console.log('Transfer risk:', risk)
  // Update status to transferred
  const index = risks.value.findIndex(r => r.id === risk.id)
  if (index !== -1) {
    risks.value[index].status = 'transferred'
  }
}

function viewTrendAnalysis() {
  console.log('View trend analysis')
}

function updateRisk() {
  console.log('Update risk:', currentRisk.value)
  riskDetailDialogVisible.value = false
}

function submitMitigation() {
  console.log('Submit mitigation:', mitigationForm.value)
  // Update risk status to mitigating
  const index = risks.value.findIndex(r => r.id === currentRisk.value.id)
  if (index !== -1) {
    risks.value[index].status = 'mitigating'
    risks.value[index].mitigationMeasures = mitigationForm.value.measures
  }
  mitigationDialogVisible.value = false
}

function getRiskLevelType(level: string) {
  switch (level) {
    case 'high': return 'danger'
    case 'medium': return 'warning'
    case 'low': return 'success'
    default: return 'info'
  }
}

function getRiskLevelName(level: string) {
  switch (level) {
    case 'high': return '高风险'
    case 'medium': return '中风险'
    case 'low': return '低风险'
    default: return '未知'
  }
}

function getRiskCategoryName(category: string) {
  switch (category) {
    case 'supply': return '供应风险'
    case 'demand': return '需求风险'
    case 'operation': return '运营风险'
    case 'financial': return '财务风险'
    case 'compliance': return '合规风险'
    default: return '其他风险'
  }
}

function getRiskStatusType(status: string) {
  switch (status) {
    case 'identified': return 'warning'
    case 'mitigating': return 'primary'
    case 'transferred': return 'info'
    case 'resolved': return 'success'
    default: return 'info'
  }
}

function getRiskStatusName(status: string) {
  switch (status) {
    case 'identified': return '已识别'
    case 'mitigating': return '缓解中'
    case 'transferred': return '已转移'
    case 'resolved': return '已解决'
    default: return '未知'
  }
}

function getTimelineColor(action: string) {
  switch (action) {
    case '风险识别': return '#ff4d4f'
    case '初步评估': return '#ffaa33'
    case '制定缓解措施': return '#00e0ff'
    case '风险转移': return '#a367ff'
    case '风险解决': return '#00f5a8'
    default: return '#00e0ff'
  }
}

function initCharts() {
  nextTick(() => {
    if (riskHeatmap.value) {
      heatmapInstance = echarts.init(riskHeatmap.value)
      heatmapInstance.setOption(getHeatmapOption())
    }
    
    if (riskTrendChart.value) {
      trendChartInstance = echarts.init(riskTrendChart.value)
      trendChartInstance.setOption(getTrendChartOption())
      
      // Handle window resize
      window.addEventListener('resize', () => {
        if (heatmapInstance) {
          heatmapInstance.resize()
        }
        if (trendChartInstance) {
          trendChartInstance.resize()
        }
      })
    }
  })
}

function getHeatmapOption() {
  return {
    title: {
      text: '风险热力图',
      textStyle: {
        color: 'rgba(255, 255, 255, 0.8)',
        fontSize: 14
      }
    },
    tooltip: {
      position: 'top'
    },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      axisLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.2)' } },
      axisLabel: { color: 'rgba(255, 255, 255, 0.6)' }
    },
    yAxis: {
      type: 'category',
      data: ['供应风险', '需求风险', '运营风险', '财务风险', '合规风险'],
      axisLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.2)' } },
      axisLabel: { color: 'rgba(255, 255, 255, 0.6)' }
    },
    visualMap: {
      min: 0,
      max: 10,
      calculable: true,
      orient: 'horizontal',
      left: 'center',
      bottom: '10%',
      inRange: {
        color: ['#00f5a8', '#00e0ff', '#a367ff', '#ffaa33', '#ff4d4f']
      }
    },
    series: [{
      name: '风险频率',
      type: 'heatmap',
      data: [
        [0, 0, 5], [1, 0, 7], [2, 0, 6], [3, 0, 4], [4, 0, 8], [5, 0, 3], [6, 0, 5],
        [0, 1, 3], [1, 1, 2], [2, 1, 4], [3, 1, 5], [4, 1, 3], [5, 1, 2], [6, 1, 4],
        [0, 2, 7], [1, 2, 8], [2, 2, 9], [3, 2, 6], [4, 2, 7], [5, 2, 5], [6, 2, 8],
        [0, 3, 4], [1, 3, 5], [2, 3, 3], [3, 3, 6], [4, 3, 4], [5, 3, 5], [6, 3, 3],
        [0, 4, 2], [1, 4, 3], [2, 4, 1], [3, 4, 2], [4, 4, 3], [5, 4, 2], [6, 4, 1]
      ],
      label: {
        show: true
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }]
  }
}

function getTrendChartOption() {
  return {
    title: {
      text: '风险趋势分析',
      textStyle: {
        color: 'rgba(255, 255, 255, 0.8)',
        fontSize: 14
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['高风险', '中风险', '低风险'],
      textStyle: { color: 'rgba(255, 255, 255, 0.6)' }
    },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月'],
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
        name: '高风险',
        type: 'line',
        stack: '风险数量',
        areaStyle: {
          color: {
            type: 'linear',
            x: 0, y: 0, x2: 0, y2: 1,
            colorStops: [{
              offset: 0, color: 'rgba(255, 77, 79, 0.3)'
            }, {
              offset: 1, color: 'rgba(255, 77, 79, 0)'
            }]
          }
        },
        lineStyle: { color: '#ff4d4f' },
        itemStyle: { color: '#ff4d4f' },
        data: [12, 10, 8, 9, 7, 8]
      },
      {
        name: '中风险',
        type: 'line',
        stack: '风险数量',
        areaStyle: {
          color: {
            type: 'linear',
            x: 0, y: 0, x2: 0, y2: 1,
            colorStops: [{
              offset: 0, color: 'rgba(255, 170, 51, 0.3)'
            }, {
              offset: 1, color: 'rgba(255, 170, 51, 0)'
            }]
          }
        },
        lineStyle: { color: '#ffaa33' },
        itemStyle: { color: '#ffaa33' },
        data: [18, 20, 15, 17, 16, 15]
      },
      {
        name: '低风险',
        type: 'line',
        stack: '风险数量',
        areaStyle: {
          color: {
            type: 'linear',
            x: 0, y: 0, x2: 0, y2: 1,
            colorStops: [{
              offset: 0, color: 'rgba(0, 245, 168, 0.3)'
            }, {
              offset: 1, color: 'rgba(0, 245, 168, 0)'
            }]
          }
        },
        lineStyle: { color: '#00f5a8' },
        itemStyle: { color: '#00f5a8' },
        data: [25, 22, 28, 24, 26, 23]
      }
    ]
  }
}
</script>

<style scoped lang="scss">
.risk-view {
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
      
      .heatmap-chart, .trend-chart {
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

.risk-detail-content {
  .history-item {
    .action {
      font-weight: 600;
      margin-bottom: var(--spacing-sm);
    }
    
    .operator {
      color: var(--text-secondary);
      font-size: 0.9rem;
      margin-bottom: var(--spacing-sm);
    }
    
    .comment {
      color: var(--text-secondary);
      font-size: 0.9rem;
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