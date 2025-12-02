import { ref, Ref, computed, ComputedRef } from 'vue'
import type { SupplyChainNode, SupplyChainLink, DigitalTwinData } from '@/types/digitalTwin'

/**
 * Digital twin composable
 * @returns Digital twin methods and states
 */
export function useDigitalTwin() {
  const digitalTwinData = ref<DigitalTwinData | null>(null)
  const selectedNode = ref<SupplyChainNode | null>(null)
  const selectedLink = ref<SupplyChainLink | null>(null)
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  /**
   * Set digital twin data
   * @param data Digital twin data
   */
  function setDigitalTwinData(data: DigitalTwinData) {
    digitalTwinData.value = data
  }

  /**
   * Set selected node
   * @param node Selected node
   */
  function setSelectedNode(node: SupplyChainNode | null) {
    selectedNode.value = node
  }

  /**
   * Set selected link
   * @param link Selected link
   */
  function setSelectedLink(link: SupplyChainLink | null) {
    selectedLink.value = link
  }

  /**
   * Get node by ID
   * @param id Node ID
   * @returns Node object or undefined
   */
  function getNodeById(id: string): SupplyChainNode | undefined {
    return digitalTwinData.value?.nodes.find(node => node.id === id)
  }

  /**
   * Get link by ID
   * @param id Link ID
   * @returns Link object or undefined
   */
  function getLinkById(id: string): SupplyChainLink | undefined {
    return digitalTwinData.value?.links.find(link => link.id === id)
  }

  /**
   * Filter nodes by type
   * @param type Node type
   * @returns Filtered nodes
   */
  function getNodesByType(type: SupplyChainNode['type']): SupplyChainNode[] {
    return digitalTwinData.value?.nodes.filter(node => node.type === type) || []
  }

  /**
   * Get nodes with warnings or errors
   * @returns Nodes with issues
   */
  function getNodesWithIssues(): SupplyChainNode[] {
    return digitalTwinData.value?.nodes.filter(
      node => node.status === 'warning' || node.status === 'error'
    ) || []
  }

  /**
   * Get links with warnings or errors
   * @returns Links with issues
   */
  function getLinksWithIssues(): SupplyChainLink[] {
    return digitalTwinData.value?.links.filter(
      link => link.status === 'warning' || link.status === 'error'
    ) || []
  }

  /**
   * Calculate network health score
   * @returns Health score (0-100)
   */
  const networkHealthScore: ComputedRef<number> = computed(() => {
    if (!digitalTwinData.value) return 0
    
    const totalNodes = digitalTwinData.value.nodes.length
    const healthyNodes = digitalTwinData.value.nodes.filter(
      node => node.status === 'normal'
    ).length
    
    const totalLinks = digitalTwinData.value.links.length
    const healthyLinks = digitalTwinData.value.links.filter(
      link => link.status === 'normal'
    ).length
    
    if (totalNodes + totalLinks === 0) return 0
    
    return Math.round(
      ((healthyNodes + healthyLinks) / (totalNodes + totalLinks)) * 100
    )
  })

  /**
   * Get recent events
   * @param limit Number of events to return (default: 5)
   * @returns Recent events
   */
  function getRecentEvents(limit = 5): any[] {
    if (!digitalTwinData.value) return []
    
    return [...digitalTwinData.value.events]
      .sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())
      .slice(0, limit)
  }

  /**
   * Reset states
   */
  function reset() {
    digitalTwinData.value = null
    selectedNode.value = null
    selectedLink.value = null
    isLoading.value = false
    error.value = null
  }

  return {
    digitalTwinData,
    selectedNode,
    selectedLink,
    isLoading,
    error,
    networkHealthScore,
    setDigitalTwinData,
    setSelectedNode,
    setSelectedLink,
    getNodeById,
    getLinkById,
    getNodesByType,
    getNodesWithIssues,
    getLinksWithIssues,
    getRecentEvents,
    reset
  }
}