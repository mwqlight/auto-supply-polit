/**
 * Format currency
 * @param value Currency value
 * @param currency Currency code (default: 'CNY')
 * @param decimals Decimal places (default: 2)
 * @returns Formatted currency string
 */
export function formatCurrency(
  value: number,
  currency = 'CNY',
  decimals = 2
): string {
  return new Intl.NumberFormat('zh-CN', {
    style: 'currency',
    currency,
    minimumFractionDigits: decimals,
    maximumFractionDigits: decimals
  }).format(value)
}

/**
 * Format percentage
 * @param value Percentage value (0-100)
 * @param decimals Decimal places (default: 2)
 * @returns Formatted percentage string
 */
export function formatPercentage(value: number, decimals = 2): string {
  return `${value.toFixed(decimals)}%`
}

/**
 * Format number with thousand separator
 * @param value Number value
 * @param decimals Decimal places (default: 0)
 * @returns Formatted number string
 */
export function formatNumber(value: number, decimals = 0): string {
  return new Intl.NumberFormat('zh-CN', {
    minimumFractionDigits: decimals,
    maximumFractionDigits: decimals
  }).format(value)
}

/**
 * Format large number with unit (K, M, B)
 * @param value Number value
 * @param decimals Decimal places (default: 2)
 * @returns Formatted number string with unit
 */
export function formatLargeNumber(value: number, decimals = 2): string {
  if (value >= 1e9) {
    return `${(value / 1e9).toFixed(decimals)}B`
  }
  if (value >= 1e6) {
    return `${(value / 1e6).toFixed(decimals)}M`
  }
  if (value >= 1e3) {
    return `${(value / 1e3).toFixed(decimals)}K`
  }
  return value.toString()
}

/**
 * Format bytes to human readable format
 * @param bytes Bytes value
 * @param decimals Decimal places (default: 2)
 * @returns Formatted bytes string
 */
export function formatBytes(bytes: number, decimals = 2): string {
  if (bytes === 0) return '0 Bytes'
  
  const k = 1024
  const dm = decimals < 0 ? 0 : decimals
  const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB']
  
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  
  return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i]
}