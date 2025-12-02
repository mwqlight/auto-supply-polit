import request from '@/api/index'
import type { 
  DigitalTwinData, 
  SupplyChainNode, 
  SupplyChainLink,
  DigitalTwinMetrics,
  SupplyChainEvent
} from '@/types/digitalTwin'

/**
 * Get digital twin data
 */
export function getDigitalTwinDataApi() {
  return request.get<{ 
    nodes: SupplyChainNode[], 
    links: SupplyChainLink[],
    metrics: DigitalTwinMetrics,
    events: SupplyChainEvent[]
  }>('/digital-twin/data')
}

/**
 * Get node details
 * @param nodeId Node ID
 */
export function getNodeDetailsApi(nodeId: string) {
  return request.get<SupplyChainNode>(`/digital-twin/nodes/${nodeId}`)
}

/**
 * Get link details
 * @param linkId Link ID
 */
export function getLinkDetailsApi(linkId: string) {
  return request.get<SupplyChainLink>(`/digital-twin/links/${linkId}`)
}

/**
 * Update node status
 * @param nodeId Node ID
 * @param data Status update data
 */
export function updateNodeStatusApi(nodeId: string, data: { status: string }) {
  return request.put(`/digital-twin/nodes/${nodeId}/status`, data)
}

/**
 * Update link status
 * @param linkId Link ID
 * @param data Status update data
 */
export function updateLinkStatusApi(linkId: string, data: { status: string }) {
  return request.put(`/digital-twin/links/${linkId}/status`, data)
}

/**
 * Get real-time events
 */
export function getRealTimeEventsApi() {
  return request.get<SupplyChainEvent[]>('/digital-twin/events')
}

/**
 * Get historical data
 * @param params Query parameters
 */
export function getHistoricalDataApi(params: {
  startTime: string
  endTime: string
  nodeId?: string
}) {
  return request.get<any>('/digital-twin/historical', { params })
}