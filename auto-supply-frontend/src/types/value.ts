export interface ValueStream {
  id: number
  name: string
  code: string
  description: string
  owner: string
  department: string
  startDate?: string
  endDate?: string
  status: 'active' | 'inactive' | 'planned'
  kpis: Array<{
    id: number
    name: string
    target: number
    actual: number
    unit: string
  }>
  stakeholders: Array<{
    id: number
    name: string
    role: string
    influence: 'high' | 'medium' | 'low'
  }>
  createdAt: string
  updatedAt: string
}

export interface ValueStreamMetric {
  id: number
  streamId: number
  metricName: string
  currentValue: number
  targetValue: number
  previousValue?: number
  unit: string
  trend: 'improving' | 'declining' | 'stable'
  lastUpdated: string
}

export interface CostBreakdown {
  id: number
  streamId: number
  category: string
  amount: number
  percentage: number
  trend: 'increasing' | 'decreasing' | 'stable'
}

export interface RevenueData {
  id: number
  streamId: number
  period: string
  revenue: number
  growthRate: number
  customers: number
}

export interface ValueStreamQueryParams {
  page?: number
  size?: number
  keyword?: string
  status?: string
  department?: string
}