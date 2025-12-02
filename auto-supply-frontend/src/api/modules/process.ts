import request from '@/api/index'
import type { 
  Process, 
  ProcessInstance, 
  ProcessMetric,
  ProcessQueryParams,
  ProcessInstanceQueryParams
} from '@/types/process'

/**
 * Get process list
 * @param params Query parameters
 */
export function getProcessesApi(params?: ProcessQueryParams) {
  return request.get<Process[]>('/processes', { params })
}

/**
 * Get process details
 * @param id Process ID
 */
export function getProcessDetailsApi(id: number) {
  return request.get<Process>(`/processes/${id}`)
}

/**
 * Create new process
 * @param data Process data
 */
export function createProcessApi(data: Partial<Process>) {
  return request.post<Process>('/processes', data)
}

/**
 * Update process
 * @param id Process ID
 * @param data Process data
 */
export function updateProcessApi(id: number, data: Partial<Process>) {
  return request.put<Process>(`/processes/${id}`, data)
}

/**
 * Delete process
 * @param id Process ID
 */
export function deleteProcessApi(id: number) {
  return request.delete(`/processes/${id}`)
}

/**
 * Get process instances
 * @param params Query parameters
 */
export function getProcessInstancesApi(params?: ProcessInstanceQueryParams) {
  return request.get<ProcessInstance[]>('/processes/instances', { params })
}

/**
 * Get process instance details
 * @param id Instance ID
 */
export function getProcessInstanceDetailsApi(id: number) {
  return request.get<ProcessInstance>(`/processes/instances/${id}`)
}

/**
 * Start process instance
 * @param processId Process ID
 */
export function startProcessInstanceApi(processId: number) {
  return request.post<ProcessInstance>(`/processes/${processId}/start`)
}

/**
 * Stop process instance
 * @param instanceId Instance ID
 */
export function stopProcessInstanceApi(instanceId: number) {
  return request.post(`/processes/instances/${instanceId}/stop`)
}

/**
 * Get process metrics
 * @param processId Process ID
 */
export function getProcessMetricsApi(processId: number) {
  return request.get<ProcessMetric[]>(`/processes/${processId}/metrics`)
}