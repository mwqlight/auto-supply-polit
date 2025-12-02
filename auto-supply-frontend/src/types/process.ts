export interface Process {
  id: number
  name: string
  code: string
  description: string
  version: string
  status: 'active' | 'inactive' | 'draft'
  owner: string
  department: string
  startDate?: string
  endDate?: string
  expectedDuration: number
  actualDuration?: number
  milestones: Array<{
    id: number
    name: string
    dueDate: string
    completedDate?: string
    status: 'pending' | 'in_progress' | 'completed' | 'delayed'
  }>
  kpis: Array<{
    id: number
    name: string
    target: number
    actual: number
    unit: string
  }>
  createdAt: string
  updatedAt: string
}

export interface ProcessInstance {
  id: number
  processId: number
  name: string
  status: 'pending' | 'in_progress' | 'completed' | 'cancelled' | 'failed'
  priority: 'low' | 'medium' | 'high' | 'critical'
  startedAt?: string
  completedAt?: string
  assignee?: string
  progress: number
  variables: Record<string, any>
  logs: Array<{
    id: number
    timestamp: string
    message: string
    level: 'info' | 'warning' | 'error'
  }>
}

export interface ProcessMetric {
  id: number
  processId: number
  metricName: string
  currentValue: number
  targetValue: number
  unit: string
  trend: 'improving' | 'declining' | 'stable'
  lastUpdated: string
}

export interface ProcessQueryParams {
  page?: number
  size?: number
  keyword?: string
  status?: string
  department?: string
}

export interface ProcessInstanceQueryParams {
  page?: number
  size?: number
  processId?: number
  status?: string
  priority?: string
  assignee?: string
}