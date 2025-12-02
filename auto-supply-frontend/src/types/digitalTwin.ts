export interface SupplyChainNode {
  id: string
  name: string
  type: 'supplier' | 'manufacturer' | 'distributor' | 'retailer' | 'customer'
  status: 'normal' | 'warning' | 'error' | 'offline'
  location: {
    lat: number
    lng: number
    address: string
  }
  metrics: {
    throughput: number
    latency: number
    availability: number
    quality: number
  }
  capacity: {
    max: number
    current: number
    unit: string
  }
  lastUpdated: string
}

export interface SupplyChainLink {
  id: string
  sourceNodeId: string
  targetNodeId: string
  status: 'normal' | 'warning' | 'error' | 'offline'
  metrics: {
    bandwidth: number
    latency: number
    packetLoss: number
    utilization: number
  }
  capacity: {
    max: number
    current: number
    unit: string
  }
  lastUpdated: string
}

export interface DigitalTwinMetrics {
  overallHealth: number
  riskLevel: 'low' | 'medium' | 'high'
  totalNodes: number
  activeNodes: number
  totalLinks: number
  activeLinks: number
  avgLatency: number
  uptime: number
}

export interface SupplyChainEvent {
  id: string
  type: 'disruption' | 'delay' | 'quality' | 'capacity' | 'maintenance'
  severity: 'low' | 'medium' | 'high' | 'critical'
  nodeId?: string
  linkId?: string
  title: string
  description: string
  timestamp: string
  resolved: boolean
}

export interface DigitalTwinData {
  nodes: SupplyChainNode[]
  links: SupplyChainLink[]
  metrics: DigitalTwinMetrics
  events: SupplyChainEvent[]
}