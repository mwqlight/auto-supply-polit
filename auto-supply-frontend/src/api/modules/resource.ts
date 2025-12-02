import request from '@/api/index'
import type { 
  Supplier, 
  InventoryItem, 
  CapacityData,
  SupplierQueryParams,
  InventoryQueryParams,
  CapacityQueryParams
} from '@/types/resource'

/**
 * Get supplier list
 * @param params Query parameters
 */
export function getSuppliersApi(params?: SupplierQueryParams) {
  return request.get<Supplier[]>('/resources/suppliers', { params })
}

/**
 * Get supplier details
 * @param id Supplier ID
 */
export function getSupplierDetailsApi(id: number) {
  return request.get<Supplier>(`/resources/suppliers/${id}`)
}

/**
 * Create new supplier
 * @param data Supplier data
 */
export function createSupplierApi(data: Partial<Supplier>) {
  return request.post<Supplier>('/resources/suppliers', data)
}

/**
 * Update supplier
 * @param id Supplier ID
 * @param data Supplier data
 */
export function updateSupplierApi(id: number, data: Partial<Supplier>) {
  return request.put<Supplier>(`/resources/suppliers/${id}`, data)
}

/**
 * Delete supplier
 * @param id Supplier ID
 */
export function deleteSupplierApi(id: number) {
  return request.delete(`/resources/suppliers/${id}`)
}

/**
 * Get inventory list
 * @param params Query parameters
 */
export function getInventoryApi(params?: InventoryQueryParams) {
  return request.get<InventoryItem[]>('/resources/inventory', { params })
}

/**
 * Get inventory item details
 * @param id Inventory item ID
 */
export function getInventoryDetailsApi(id: number) {
  return request.get<InventoryItem>(`/resources/inventory/${id}`)
}

/**
 * Update inventory item
 * @param id Inventory item ID
 * @param data Inventory data
 */
export function updateInventoryApi(id: number, data: Partial<InventoryItem>) {
  return request.put<InventoryItem>(`/resources/inventory/${id}`, data)
}

/**
 * Get capacity data
 * @param params Query parameters
 */
export function getCapacityApi(params?: CapacityQueryParams) {
  return request.get<CapacityData[]>('/resources/capacity', { params })
}

/**
 * Update capacity data
 * @param id Capacity record ID
 * @param data Capacity data
 */
export function updateCapacityApi(id: number, data: Partial<CapacityData>) {
  return request.put<CapacityData>(`/resources/capacity/${id}`, data)
}