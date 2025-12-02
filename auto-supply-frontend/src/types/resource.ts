export interface Supplier {
  id: number
  name: string
  code: string
  category: string
  status: 'active' | 'inactive' | 'suspended'
  contact: {
    name: string
    email: string
    phone: string
  }
  address: {
    street: string
    city: string
    country: string
    zipCode: string
  }
  metrics: {
    qualityScore: number
    deliveryTime: number
    responsiveness: number
    costCompetitiveness: number
  }
  contracts: Array<{
    id: number
    name: string
    startDate: string
    endDate: string
    status: 'active' | 'expired' | 'pending'
  }>
  createdAt: string
  updatedAt: string
}

export interface InventoryItem {
  id: number
  sku: string
  name: string
  category: string
  description: string
  quantity: number
  reserved: number
  available: number
  unit: string
  location: string
  status: 'in_stock' | 'low_stock' | 'out_of_stock' | 'discontinued'
  reorderPoint: number
  maxStockLevel: number
  supplierId: number
  cost: number
  lastReceived: string
  lastShipped: string
  expiryDate?: string
  batchNumber?: string
}

export interface CapacityData {
  id: number
  resourceId: number
  resourceName: string
  date: string
  shift: 'morning' | 'afternoon' | 'night'
  plannedCapacity: number
  actualCapacity: number
  utilization: number
  downtime: number
  reason?: string
}

export interface SupplierQueryParams {
  page?: number
  size?: number
  keyword?: string
  category?: string
  status?: string
}

export interface InventoryQueryParams {
  page?: number
  size?: number
  keyword?: string
  category?: string
  status?: string
  location?: string
}

export interface CapacityQueryParams {
  page?: number
  size?: number
  resourceId?: number
  startDate?: string
  endDate?: string
}