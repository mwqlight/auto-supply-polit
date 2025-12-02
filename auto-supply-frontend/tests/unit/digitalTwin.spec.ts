import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import * as DigitalTwinAPI from '@/api/modules/digitalTwin'
import DigitalTwinView from '@/views/digital-twin/DigitalTwinView.vue'
import { nextTick } from 'vue'

// Mock the API module
vi.mock('@/api/modules/digitalTwin', () => ({
  getDigitalTwinDataApi: vi.fn(),
  getNodeDetailsApi: vi.fn(),
  getLinkDetailsApi: vi.fn(),
  updateNodeStatusApi: vi.fn(),
  updateLinkStatusApi: vi.fn(),
  getRealTimeEventsApi: vi.fn(),
  getHistoricalDataApi: vi.fn()
}))

describe('DigitalTwinView', () => {
  beforeEach(() => {
    vi.resetAllMocks()
  })

  it('renders properly', async () => {
    // Mock API response
    const mockData = {
      nodes: [
        {
          id: '1',
          name: 'Supplier A',
          type: 'supplier',
          status: 'normal',
          location: { lat: 0, lng: 0, address: 'Address A' },
          metrics: {
            throughput: 100,
            latency: 10,
            availability: 99.9,
            quality: 95
          },
          capacity: { max: 1000, current: 500, unit: 'units' },
          lastUpdated: '2023-01-01T00:00:00Z'
        }
      ],
      links: [
        {
          id: '1',
          sourceNodeId: '1',
          targetNodeId: '2',
          status: 'normal',
          metrics: {
            bandwidth: 1000,
            latency: 5,
            packetLoss: 0.1,
            utilization: 50
          },
          capacity: { max: 2000, current: 1000, unit: 'Mbps' },
          lastUpdated: '2023-01-01T00:00:00Z'
        }
      ],
      metrics: {
        overallHealth: 95,
        riskLevel: 'low',
        totalNodes: 10,
        activeNodes: 9,
        totalLinks: 15,
        activeLinks: 14,
        avgLatency: 15,
        uptime: 99.5
      },
      events: []
    }

    vi.mocked(DigitalTwinAPI.getDigitalTwinDataApi).mockResolvedValue({
      code: 200,
      message: 'success',
      data: mockData,
      timestamp: Date.now()
    })

    const wrapper = mount(DigitalTwinView)
    await nextTick()

    expect(wrapper.exists()).toBe(true)
    expect(DigitalTwinAPI.getDigitalTwinDataApi).toHaveBeenCalled()
  })

  it('handles API error gracefully', async () => {
    // Mock API error
    vi.mocked(DigitalTwinAPI.getDigitalTwinDataApi).mockRejectedValue(
      new Error('Network error')
    )

    const wrapper = mount(DigitalTwinView)
    await nextTick()

    // Check that error handling works
    expect(wrapper.exists()).toBe(true)
    expect(DigitalTwinAPI.getDigitalTwinDataApi).toHaveBeenCalled()
  })

  it('updates node status correctly', async () => {
    const mockResponse = {
      code: 200,
      message: 'success',
      data: true,
      timestamp: Date.now()
    }

    vi.mocked(DigitalTwinAPI.updateNodeStatusApi).mockResolvedValue(mockResponse)

    const nodeId = '1'
    const status = 'warning'

    const wrapper = mount(DigitalTwinView)
    await nextTick()

    // Simulate calling the updateNodeStatus function
    // This would typically be triggered by user interaction in the actual component
    // For testing purposes, we're directly testing the API call
    const result = await wrapper.vm.updateNodeStatus(nodeId, { status })

    expect(DigitalTwinAPI.updateNodeStatusApi).toHaveBeenCalledWith(nodeId, { status })
    expect(result).toEqual(mockResponse)
  })

  it('fetches node details correctly', async () => {
    const mockNodeDetails = {
      id: '1',
      name: 'Supplier A',
      type: 'supplier',
      status: 'normal',
      location: { lat: 0, lng: 0, address: 'Address A' },
      metrics: {
        throughput: 100,
        latency: 10,
        availability: 99.9,
        quality: 95
      },
      capacity: { max: 1000, current: 500, unit: 'units' },      
      lastUpdated: '2023-01-01T00:00:00Z'
    }

    const mockResponse = {
      code: 200,
      message: 'success',
      data: mockNodeDetails,
      timestamp: Date.now()
    }

    vi.mocked(DigitalTwinAPI.getNodeDetailsApi).mockResolvedValue(mockResponse)

    const nodeId = '1'
    const wrapper = mount(DigitalTwinView)
    await nextTick()

    const result = await wrapper.vm.getNodeDetails(nodeId)

    expect(DigitalTwinAPI.getNodeDetailsApi).toHaveBeenCalledWith(nodeId)
    expect(result).toEqual(mockResponse)
  })
})