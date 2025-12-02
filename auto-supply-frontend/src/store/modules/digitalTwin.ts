import { defineStore } from 'pinia'
import { ref, reactive } from 'vue'
import type { DigitalTwinData, SupplyChainNode, SupplyChainLink } from '@/types/digitalTwin'
import { getDigitalTwinDataApi } from '@/api/modules/digitalTwin'

// Define the store
export const useDigitalTwinStore = defineStore('digitalTwin', () => {
  // State
  const digitalTwinData = reactive<DigitalTwinData>({
    nodes: [],
    links: [],
    metrics: {
      totalNodes: 0,
      totalLinks: 0,
      healthScore: 0,
      efficiency: 0,
      riskLevel: 'low'
    },
    events: [],
    timestamp: Date.now()
  })
  
  const isLoading = ref<boolean>(false)
  const error = ref<string>('')

  // Actions
  /**
   * Fetch digital twin data
   */
  const fetchDigitalTwinData = async () => {
    isLoading.value = true
    error.value = ''
    
    try {
      const res = await getDigitalTwinDataApi()
      if (res.code === 200) {
        // Update digital twin data
        digitalTwinData.nodes = res.data.nodes
        digitalTwinData.links = res.data.links
        digitalTwinData.metrics = res.data.metrics
        digitalTwinData.events = res.data.events
        digitalTwinData.timestamp = Date.now()
        return Promise.resolve(res)
      } else {
        error.value = res.message || '获取数字孪生数据失败'
        return Promise.reject(res)
      }
    } catch (err: any) {
      error.value = err.message || '网络错误，请稍后重试'
      return Promise.reject(err)
    } finally {
      isLoading.value = false
    }
  }

  /**
   * Update node status
   * @param nodeId Node ID
   * @param status New status
   */
  const updateNodeStatus = (nodeId: string, status: string) => {
    const node = digitalTwinData.nodes.find(n => n.id === nodeId)
    if (node) {
      node.status = status
      // Update timestamp
      digitalTwinData.timestamp = Date.now()
    }
  }

  /**
   * Update link status
   * @param linkId Link ID
   * @param status New status
   */
  const updateLinkStatus = (linkId: string, status: string) => {
    const link = digitalTwinData.links.find(l => l.id === linkId)
    if (link) {
      link.status = status
      // Update timestamp
      digitalTwinData.timestamp = Date.now()
    }
  }

  /**
   * Add event
   * @param event Event data
   */
  const addEvent = (event: any) => {
    digitalTwinData.events.unshift(event)
    // Keep only the latest 100 events
    if (digitalTwinData.events.length > 100) {
      digitalTwinData.events = digitalTwinData.events.slice(0, 100)
    }
    // Update timestamp
    digitalTwinData.timestamp = Date.now()
  }

  /**
   * Clear events
   */
  const clearEvents = () => {
    digitalTwinData.events = []
    // Update timestamp
    digitalTwinData.timestamp = Date.now()
  }

  /**
   * Reset state
   */
  const resetState = () => {
    digitalTwinData.nodes = []
    digitalTwinData.links = []
    digitalTwinData.metrics = {
      totalNodes: 0,
      totalLinks: 0,
      healthScore: 0,
      efficiency: 0,
      riskLevel: 'low'
    }
    digitalTwinData.events = []
    digitalTwinData.timestamp = Date.now()
    isLoading.value = false
    error.value = ''
  }

  return {
    // State
    digitalTwinData,
    isLoading: isLoading.value,
    error: error.value,

    // Actions
    fetchDigitalTwinData,
    updateNodeStatus,
    updateLinkStatus,
    addEvent,
    clearEvents,
    resetState
  }
})