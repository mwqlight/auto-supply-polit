import type { ECBasicOption } from 'echarts/types/dist/shared'

/**
 * Get common chart options
 * @returns Common chart options
 */
export function getCommonChartOptions(): ECBasicOption {
  return {
    textStyle: {
      fontFamily: 'Arial, sans-serif',
      fontSize: 12
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross',
        label: {
          backgroundColor: '#6a7985'
        }
      }
    },
    color: [
      '#00D4FF', // Primary cyan
      '#FF00C8', // Magenta
      '#00FF9D', // Green
      '#FFD700', // Yellow
      '#FF6B35', // Orange
      '#9B5DE5'  // Purple
    ]
  }
}

/**
 * Get cyberpunk style chart options
 * @returns Cyberpunk style chart options
 */
export function getCyberpunkChartOptions(): ECBasicOption {
  return {
    textStyle: {
      fontFamily: 'Courier New, monospace',
      fontSize: 12,
      color: '#00D4FF'
    },
    grid: {
      left: '5%',
      right: '5%',
      top: '10%',
      bottom: '15%',
      borderColor: '#00D4FF',
      borderWidth: 1
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross',
        label: {
          backgroundColor: 'rgba(0, 212, 255, 0.2)',
          color: '#00D4FF',
          borderColor: '#00D4FF'
        }
      },
      backgroundColor: 'rgba(0, 20, 40, 0.9)',
      borderColor: '#00D4FF',
      borderWidth: 1,
      textStyle: {
        color: '#00D4FF'
      }
    },
    xAxis: {
      axisLine: {
        lineStyle: {
          color: '#00D4FF'
        }
      },
      axisLabel: {
        color: '#00D4FF'
      },
      splitLine: {
        show: true,
        lineStyle: {
          color: 'rgba(0, 212, 255, 0.1)',
          type: 'dashed'
        }
      }
    },
    yAxis: {
      axisLine: {
        lineStyle: {
          color: '#00D4FF'
        }
      },
      axisLabel: {
        color: '#00D4FF'
      },
      splitLine: {
        show: true,
        lineStyle: {
          color: 'rgba(0, 212, 255, 0.1)',
          type: 'dashed'
        }
      }
    },
    color: [
      '#00D4FF', // Primary cyan
      '#FF00C8', // Magenta
      '#00FF9D', // Green
      '#FFD700', // Yellow
      '#FF6B35', // Orange
      '#9B5DE5'  // Purple
    ]
  }
}

/**
 * Get pie chart options with cyberpunk style
 * @returns Pie chart options
 */
export function getCyberpunkPieOptions(): ECBasicOption {
  return {
    textStyle: {
      fontFamily: 'Courier New, monospace',
      fontSize: 12,
      color: '#00D4FF'
    },
    tooltip: {
      trigger: 'item',
      backgroundColor: 'rgba(0, 20, 40, 0.9)',
      borderColor: '#00D4FF',
      borderWidth: 1,
      textStyle: {
        color: '#00D4FF'
      }
    },
    color: [
      '#00D4FF', // Primary cyan
      '#FF00C8', // Magenta
      '#00FF9D', // Green
      '#FFD700', // Yellow
      '#FF6B35', // Orange
      '#9B5DE5'  // Purple
    ]
  }
}