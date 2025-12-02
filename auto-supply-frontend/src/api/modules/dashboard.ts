import request from '@/api/index'
import type { 
  DashboardMetrics, 
  ResourceUtilization, 
  ProcessEfficiency,
  RiskIndicators,
  ValueMetrics,
  AlertItem
} from '@/types/dashboard'

/**
 * Get dashboard overview metrics
 */
export function getDashboardMetricsApi() {
  return request.get<DashboardMetrics>('/dashboard/metrics')
}

/**
 * Get resource utilization data
 */
export function getResourceUtilizationApi() {
  return request.get<ResourceUtilization[]>('/dashboard/resources')
}

/**
 * Get process efficiency data
 */
export function getProcessEfficiencyApi() {
  return request.get<ProcessEfficiency[]>('/dashboard/processes')
}

/**
 * Get risk indicators
 */
export function getRiskIndicatorsApi() {
  return request.get<RiskIndicators[]>('/dashboard/risks')
}

/**
 * Get value metrics
 */
export function getValueMetricsApi() {
  return request.get<ValueMetrics>('/dashboard/value')
}

/**
 * Get recent alerts
 */
export function getRecentAlertsApi() {
  return request.get<AlertItem[]>('/dashboard/alerts')
}

/**
 * Get predictive analytics
 */
export function getPredictiveAnalyticsApi() {
  return request.get<any>('/dashboard/predictive-analytics')
}