import request from '@/api/index'
import type { 
  ValueStream, 
  ValueStreamMetric, 
  CostBreakdown, 
  RevenueData,
  ValueStreamQueryParams
} from '@/types/value'

/**
 * Get value streams
 * @param params Query parameters
 */
export function getValueStreamsApi(params?: ValueStreamQueryParams) {
  return request.get<ValueStream[]>('/value/streams', { params })
}

/**
 * Get value stream details
 * @param id Value stream ID
 */
export function getValueStreamDetailsApi(id: number) {
  return request.get<ValueStream>(`/value/streams/${id}`)
}

/**
 * Create new value stream
 * @param data Value stream data
 */
export function createValueStreamApi(data: Partial<ValueStream>) {
  return request.post<ValueStream>('/value/streams', data)
}

/**
 * Update value stream
 * @param id Value stream ID
 * @param data Value stream data
 */
export function updateValueStreamApi(id: number, data: Partial<ValueStream>) {
  return request.put<ValueStream>(`/value/streams/${id}`, data)
}

/**
 * Delete value stream
 * @param id Value stream ID
 */
export function deleteValueStreamApi(id: number) {
  return request.delete(`/value/streams/${id}`)
}

/**
 * Get value stream metrics
 * @param streamId Value stream ID
 */
export function getValueStreamMetricsApi(streamId: number) {
  return request.get<ValueStreamMetric[]>(`/value/streams/${streamId}/metrics`)
}

/**
 * Get cost breakdown
 * @param streamId Value stream ID
 */
export function getCostBreakdownApi(streamId: number) {
  return request.get<CostBreakdown[]>(`/value/streams/${streamId}/costs`)
}

/**
 * Get revenue data
 * @param streamId Value stream ID
 */
export function getRevenueDataApi(streamId: number) {
  return request.get<RevenueData[]>(`/value/streams/${streamId}/revenue`)
}

/**
 * Get ROI analysis
 * @param streamId Value stream ID
 */
export function getROIAnalysisApi(streamId: number) {
  return request.get<any>(`/value/streams/${streamId}/roi`)
}