export interface DashboardMetrics {
  kpiScore: number
  resourceUtilization: number
  processEfficiency: number
  riskIndex: number
  valueCreation: number
  lastUpdated: string
}

export interface ResourceUtilization {
  id: number
  resourceName: string
  type: 'supplier' | 'inventory' | 'capacity'
  currentUsage: number
  maxCapacity: number
  unit: string
  trend: 'up' | 'down' | 'stable'
  percentage: number
}

export interface ProcessEfficiency {
  id: number
  processName: string
  efficiency: number
  cycleTime: number
  throughput: number
  variance: number
}

export interface RiskIndicators {
  id: number
  riskName: string
  level: 'low' | 'medium' | 'high' | 'critical'
  impact: number
  probability: number
  lastAssessed: string
}

export interface ValueMetrics {
  totalValue: number
  valueGrowth: number
  costSavings: number
  roi: number
  valueStreams: Array<{
    name: string
    contribution: number
  }>
}

export interface AlertItem {
  id: number
  title: string
  type: 'info' | 'warning' | 'error' | 'success'
  severity: 'low' | 'medium' | 'high' | 'critical'
  timestamp: string
  resolved: boolean
}