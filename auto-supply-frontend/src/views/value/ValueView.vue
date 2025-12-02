<template>
  <div class="value-view">
    <div class="view-header">
      <h2 class="page-title">价值维洞察</h2>
      <div class="controls">
        <el-button class="cyber-button" @click="refreshData">
          <i class="el-icon-refresh"></i> 刷新数据
        </el-button>
        <el-button type="primary" class="cyber-button" @click="generateReport">
          <i class="el-icon-document"></i> 生成报告
        </el-button>
      </div>
    </div>

    <div class="value-content">
      <!-- 价值概览卡片 -->
      <div class="overview-cards">
        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(0, 245, 168, 0.1);">
            <i class="el-icon-money"></i>
          </div>
          <div class="card-content">
            <div class="card-title">总价值创造</div>
            <div class="card-value">¥{{ totalValue.current.toLocaleString() }}</div>
            <div class="card-change" :class="totalValue.change >= 0 ? 'positive' : 'negative'">
              {{ totalValue.change >= 0 ? '+' : '' }}{{ totalValue.change }}%
              <i :class="totalValue.change >= 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(0, 224, 255, 0.1);">
            <i class="el-icon-s-data"></i>
          </div>
          <div class="card-content">
            <div class="card-title">ROI</div>
            <div class="card-value">{{ roi.current }}%</div>
            <div class="card-change" :class="roi.change >= 0 ? 'positive' : 'negative'">
              {{ roi.change >= 0 ? '+' : '' }}{{ roi.change }}%
              <i :class="roi.change >= 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(163, 103, 255, 0.1);">
            <i class="el-icon-trend-charts"></i>
          </div>
          <div class="card-content">
            <div class="card-title">成本节约</div>
            <div class="card-value">¥{{ costSavings.current.toLocaleString() }}</div>
            <div class="card-change" :class="costSavings.change >= 0 ? 'positive' : 'negative'">
              {{ costSavings.change >= 0 ? '+' : '' }}{{ costSavings.change }}%
              <i :class="costSavings.change >= 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>

        <div class="overview-card cyber-card">
          <div class="card-icon" style="background: rgba(255, 170, 51, 0.1);">
            <i class="el-icon-user"></i>
          </div>
          <div class="card-content">
            <div class="card-title">客户满意度</div>
            <div class="card-value">{{ customerSatisfaction.current }}%</div>
            <div class="card-change" :class="customerSatisfaction.change >= 0 ? 'positive' : 'negative'">
              {{ customerSatisfaction.change >= 0 ? '+' : '' }}{{ customerSatisfaction.change }}%
              <i :class="customerSatisfaction.change >= 0 ? 'el-icon-top' : 'el-icon-bottom'"></i>
            </div>
          </div>
        </div>
      </div>

      <!-- 价值分析区域 -->
      <div class="analysis-section">
        <!-- 价值链可视化 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>价值链可视化</h3>
            <div class="section-controls">
              <el-select v-model="valueChainPeriod" placeholder="时间周期">
                <el-option label="本月" value="month" />
                <el-option label="本季度" value="quarter" />
                <el-option label="本年" value="year" />
              </el-select>
              <el-button type="text" @click="viewValueChainDetails">查看详情</el-button>
            </div>
          </div>
          <div class="section-content">
            <div ref="valueChainChart" class="value-chain-chart"></div>
          </div>
        </div>

        <!-- 价值贡献分析 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>价值贡献分析</h3>
            <div class="section-controls">
              <el-select v-model="contributionDimension" placeholder="分析维度">
                <el-option label="按部门" value="department" />
                <el-option label="按产品线" value="productLine" />
                <el-option label="按地区" value="region" />
              </el-select>
              <el-button type="text" @click="exportContributionData">导出数据</el-button>
            </div>
          </div>
          <div class="section-content">
            <div ref="contributionChart" class="contribution-chart"></div>
          </div>
        </div>

        <!-- 价值驱动因素 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>价值驱动因素</h3>
            <div class="section-controls">
              <el-select v-model="driverPeriod" placeholder="时间周期">
                <el-option label="近7天" value="7d" />
                <el-option label="近30天" value="30d" />
                <el-option label="近90天" value="90d" />
              </el-select>
              <el-button type="text" @click="optimizeDrivers">优化建议</el-button>
            </div>
          </div>
          <div class="section-content">
            <el-table :data="valueDrivers" style="width: 100%" stripe>
              <el-table-column prop="factor" label="驱动因素" />
              <el-table-column prop="impact" label="影响程度" width="120">
                <template #default="scope">
                  <div class="impact-bar">
                    <div 
                      class="bar-fill" 
                      :style="{ width: scope.row.impact + '%', backgroundColor: getImpactColor(scope.row.impact) }"
                    ></div>
                    <span class="bar-text">{{ scope.row.impact }}%</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="trend" label="趋势" width="100">
                <template #default="scope">
                  <div class="trend-indicator" :class="scope.row.trend">
                    <i :class="scope.row.trend === 'up' ? 'el-icon-top' : 'el-icon-bottom'"></i>
                    {{ scope.row.trend === 'up' ? '上升' : '下降' }}
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="value" label="贡献值" width="120">
                <template #default="scope">
                  ¥{{ scope.row.value.toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button type="text" size="small" @click="viewDriverDetails(scope.row)">详情</el-button>
                  <el-button type="text" size="small" @click="optimizeDriver(scope.row)">优化</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>

        <!-- ROI分析 -->
        <div class="section-card cyber-card">
          <div class="section-header">
            <h3>ROI分析</h3>
            <div class="section-controls">
              <el-select v-model="roiAnalysisType" placeholder="分析类型">
                <el-option label="投资项目" value="investment" />
                <el-option label="流程优化" value="process" />
                <el-option label="技术创新" value="innovation" />
              </el-select>
              <el-button type="text" @click="viewROIAnalysis">详细分析</el-button>
            </div>
          </div>
          <div class="section-content">
            <div ref="roiChart" class="roi-chart"></div>
          </div>
        </div>
      </div>
    </div>

    <!-- 价值驱动因素详情对话框 -->
    <el-dialog v-model="driverDetailDialogVisible" title="驱动因素详情" width="700px">
      <div class="driver-detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="驱动因素">{{ currentDriver.factor }}</el-descriptions-item>
          <el-descriptions-item label="影响程度">{{ currentDriver.impact }}%</el-descriptions-item>
          <el-descriptions-item label="贡献值">¥{{ currentDriver.value.toLocaleString() }}</el-descriptions-item>
          <el-descriptions-item label="趋势">
            <div class="trend-indicator" :class="currentDriver.trend">
              <i :class="currentDriver.trend === 'up' ? 'el-icon-top' : 'el-icon-bottom'"></i>
              {{ currentDriver.trend === 'up' ? '上升' : '下降' }}
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="关联指标" :span="2">{{ currentDriver.relatedMetrics }}</el-descriptions-item>
          <el-descriptions-item label="影响分析" :span="2">{{ currentDriver.analysis }}</el-descriptions-item>
          <el-descriptions-item label="优化建议" :span="2">{{ currentDriver.suggestions }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="historical-trend">
          <h4>历史趋势</h4>
          <div ref="driverTrendChart" class="driver-trend-chart"></div>
        </div>
      </div>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="driverDetailDialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="implementOptimization">实施优化</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 优化建议对话框 -->
    <el-dialog v-model="optimizationDialogVisible" title="优化建议" width="600px">
      <el-form :model="optimizationForm" label-width="100px">
        <el-form-item label="优化目标">
          <el-input v-model="optimizationForm.target" placeholder="请输入优化目标" />
        </el-form-item>
        <el-form-item label="实施计划">
          <el-input 
            v-model="optimizationForm.plan" 
            type="textarea" 
            :rows="4" 
            placeholder="请输入实施计划"
          />
        </el-form-item>
        <el-form-item label="预期收益">
          <el-input v-model="optimizationForm.expectedBenefit" placeholder="请输入预期收益">
            <template #append>万元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="负责人">
          <el-select v-model="optimizationForm.assignee" placeholder="选择负责人" style="width: 100%">
            <el-option label="张三" value="zhangsan" />
            <el-option label="李四" value="lisi" />
            <el-option label="王五" value="wangwu" />
          </el-select>
        </el-form-item>
        <el-form-item label="截止时间">
          <el-date-picker 
            v-model="optimizationForm.deadline" 
            type="date" 
            placeholder="选择日期"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="optimizationDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitOptimization">提交</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'

// Reactive data
const valueChainPeriod = ref('quarter')
const contributionDimension = ref('department')
const driverPeriod = ref('30d')
const roiAnalysisType = ref('investment')
const driverDetailDialogVisible = ref(false)
const optimizationDialogVisible = ref(false)

// Mock data
const totalValue = ref({
  current: 12500000,
  change: 12.5
})

const roi = ref({
  current: 28.6,
  change: 3.2
})

const costSavings = ref({
  current: 2350000,
  change: 8.7
})

const customerSatisfaction = ref({
  current: 92.4,
  change: 2.1
})

const valueDrivers = ref([
  {
    factor: '数字化转型',
    impact: 35,
    trend: 'up',
    value: 4500000,
    relatedMetrics: '自动化率提升15%，人工成本降低8%',
    analysis: '通过引入AI和自动化技术，显著提升了运营效率，降低了人力成本',
    suggestions: '继续扩大自动化应用范围，优化算法提升准确率'
  },
  {
    factor: '供应链协同',
    impact: 28,
    trend: 'up',
    value: 3200000,
    relatedMetrics: '库存周转率提高20%，交付准时率提升至98%',
    analysis: '通过供应链上下游协同，实现了库存优化和交付效率提升',
    suggestions: '深化合作伙伴关系，建立更紧密的信息共享机制'
  },
  {
    factor: '产品质量提升',
    impact: 22,
    trend: 'up',
    value: 2800000,
    relatedMetrics: '产品合格率提升至99.5%，客户投诉减少40%',
    analysis: '通过质量管控体系升级，显著提升了产品质量和客户满意度',
    suggestions: '持续改进质量管理流程，引入更多检测手段'
  },
  {
    factor: '市场拓展',
    impact: 15,
    trend: 'down',
    value: 2000000,
    relatedMetrics: '新客户增长放缓，市场份额略有下降',
    analysis: '受市场竞争加剧影响，新客户获取难度增加，增长速度放缓',
    suggestions: '创新营销策略，加强品牌建设，开拓新兴市场'
  }
])

const currentDriver = ref({
  factor: '',
  impact: 0,
  trend: '',
  value: 0,
  relatedMetrics: '',
  analysis: '',
  suggestions: ''
})

const optimizationForm = ref({
  target: '',
  plan: '',
  expectedBenefit: '',
  assignee: '',
  deadline: ''
})

// Chart references
const valueChainChart = ref<HTMLElement | null>(null)
const contributionChart = ref<HTMLElement | null>(null)
const roiChart = ref<HTMLElement | null>(null)
const driverTrendChart = ref<HTMLElement | null>(null)
let valueChainInstance: echarts.ECharts | null = null
let contributionInstance: echarts.ECharts | null = null
let roiInstance: echarts.ECharts | null = null
let driverTrendInstance: echarts.ECharts | null = null

// Lifecycle
onMounted(() => {
  initCharts()
})

// Methods
function refreshData() {
  console.log('Refreshing value data...')
}

function generateReport() {
  console.log('Generating value report...')
}

function viewValueChainDetails() {
  console.log('View value chain details for period:', valueChainPeriod.value)
}

function exportContributionData() {
  console.log('Export contribution data by dimension:', contributionDimension.value)
}

function optimizeDrivers() {
  console.log('Optimizing drivers for period:', driverPeriod.value)
}

function viewROIAnalysis() {
  console.log('View ROI analysis for type:', roiAnalysisType.value)
}

function viewDriverDetails(driver: any) {
  currentDriver.value = { ...driver }
  driverDetailDialogVisible.value = true
  nextTick(() => {
    initDriverTrendChart()
  })
}

function optimizeDriver(driver: any) {
  optimizationForm.value = {
    target: `优化${driver.factor}`,
    plan: driver.suggestions,
    expectedBenefit: (driver.value * 0.1 / 10000).toFixed(2),
    assignee: 'zhangsan',
    deadline: ''
  }
  optimizationDialogVisible.value = true
}

function implementOptimization() {
  console.log('Implement optimization for driver:', currentDriver.value)
  driverDetailDialogVisible.value = false
}

function submitOptimization() {
  console.log('Submit optimization:', optimizationForm.value)
  optimizationDialogVisible.value = false
}

function getImpactColor(impact: number) {
  if (impact >= 30) return '#00f5a8'
  if (impact >= 20) return '#00e0ff'
  if (impact >= 10) return '#a367ff'
  return '#ffaa33'
}

function initCharts() {
  nextTick(() => {
    if (valueChainChart.value) {
      valueChainInstance = echarts.init(valueChainChart.value)
      valueChainInstance.setOption(getValueChainOption())
    }
    
    if (contributionChart.value) {
      contributionInstance = echarts.init(contributionChart.value)
      contributionInstance.setOption(getContributionOption())
    }
    
    if (roiChart.value) {
      roiInstance = echarts.init(roiChart.value)
      roiInstance.setOption(getROIOption())
    }
    
    // Handle window resize
    window.addEventListener('resize', () => {
      if (valueChainInstance) valueChainInstance.resize()
      if (contributionInstance) contributionInstance.resize()
      if (roiInstance) roiInstance.resize()
      if (driverTrendInstance) driverTrendInstance.resize()
    })
  })
}

function initDriverTrendChart() {
  if (driverTrendChart.value) {
    driverTrendInstance = echarts.init(driverTrendChart.value)
    driverTrendInstance.setOption(getDriverTrendOption())
  }
}

function getValueChainOption() {
  return {
    title: {
      text: '价值链可视化',
      textStyle: {
        color: 'rgba(255, 255, 255, 0.8)',
        fontSize: 14
      }
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: ['成本', '收入', '利润'],
      textStyle: { color: 'rgba(255, 255, 255, 0.6)' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: ['原材料采购', '生产制造', '仓储物流', '销售分销', '售后服务'],
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
        name: '成本',
        type: 'bar',
        stack: '总量',
        barWidth: '60%',
        itemStyle: { color: 'rgba(255, 77, 79, 0.7)' },
        data: [320, 450, 280, 380, 180]
      },
      {
        name: '收入',
        type: 'bar',
        stack: '总量',
        itemStyle: { color: 'rgba(0, 245, 168, 0.7)' },
        data: [420, 650, 480, 580, 380]
      },
      {
        name: '利润',
        type: 'bar',
        stack: '总量',
        itemStyle: { color: 'rgba(0, 224, 255, 0.7)' },
        data: [100, 200, 200, 200, 200]
      }
    ]
  }
}

function getContributionOption() {
  return {
    title: {
      text: '价值贡献分析',
      textStyle: {
        color: 'rgba(255, 255, 255, 0.8)',
        fontSize: 14
      }
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: 'bottom',
      textStyle: { color: 'rgba(255, 255, 255, 0.6)' }
    },
    series: [
      {
        name: '价值贡献',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#0a0a0a',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '16',
            fontWeight: 'bold',
            color: 'rgba(255, 255, 255, 0.8)'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 45, name: '研发部' },
          { value: 32, name: '生产部' },
          { value: 28, name: '销售部' },
          { value: 20, name: '采购部' },
          { value: 15, name: '质保部' }
        ]
      }
    ]
  }
}

function getROIOption() {
  return {
    title: {
      text: 'ROI分析',
      textStyle: {
        color: 'rgba(255, 255, 255, 0.8)',
        fontSize: 14
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['投资回报率'],
      textStyle: { color: 'rgba(255, 255, 255, 0.6)' }
    },
    xAxis: {
      type: 'category',
      data: ['ERP系统', '自动化设备', 'CRM系统', '数据分析平台', '云基础设施'],
      axisLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.2)' } },
      axisLabel: { 
        color: 'rgba(255, 255, 255, 0.6)',
        rotate: 45
      }
    },
    yAxis: {
      type: 'value',
      axisLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.2)' } },
      axisLabel: { color: 'rgba(255, 255, 255, 0.6)' },
      splitLine: { lineStyle: { color: 'rgba(255, 255, 255, 0.1)' } }
    },
    series: [{
      name: '投资回报率',
      data: [25, 32, 28, 40, 35],
      type: 'bar',
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: 'rgba(0, 245, 168, 0.8)' },
          { offset: 1, color: 'rgba(0, 224, 255, 0.8)' }
        ])
      }
    }]
  }
}

function getDriverTrendOption() {
  return {
    title: {
      text: `${currentDriver.value.factor} 历史趋势`,
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
    series: [{
      data: [15, 18, 22, 25, 28, currentDriver.value.impact],
      type: 'line',
      smooth: true,
      itemStyle: { color: '#00f5a8' },
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: 'rgba(0, 245, 168, 0.3)' },
          { offset: 1, color: 'rgba(0, 245, 168, 0)' }
        ])
      }
    }]
  }
}
</script>

<style scoped lang="scss">
.value-view {
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

.analysis-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
  gap: var(--spacing-lg);
  flex: 1;
  overflow: hidden;
  
  .section-card {
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
      }
    }
    
    .section-content {
      flex: 1;
      overflow: hidden;
      
      .value-chain-chart, 
      .contribution-chart, 
      .roi-chart,
      .driver-trend-chart {
        width: 100%;
        height: 300px;
      }
      
      :deep(.el-table) {
        .impact-bar {
          position: relative;
          width: 100%;
          height: 20px;
          background: rgba(255, 255, 255, 0.1);
          border-radius: 10px;
          overflow: hidden;
          
          .bar-fill {
            height: 100%;
            border-radius: 10px;
            transition: width 0.3s ease;
          }
          
          .bar-text {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 0.8rem;
            color: white;
            text-shadow: 0 0 2px rgba(0, 0, 0, 0.5);
          }
        }
        
        .trend-indicator {
          display: flex;
          align-items: center;
          font-size: 0.9rem;
          
          &.up {
            color: var(--color-success);
          }
          
          &.down {
            color: var(--color-danger);
          }
          
          i {
            margin-right: var(--spacing-sm);
          }
        }
      }
    }
  }
}

.driver-detail-content {
  .historical-trend {
    margin-top: var(--spacing-lg);
    
    h4 {
      margin-bottom: var(--spacing-md);
      font-size: 1.1rem;
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
  
  .analysis-section {
    grid-template-columns: 1fr;
    
    .section-card {
      .section-header {
        flex-direction: column;
        align-items: flex-start;
        gap: var(--spacing-md);
      }
    }
  }
}
</style>