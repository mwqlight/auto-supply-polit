import { ref, Ref } from 'vue'
import type { AxiosResponse } from 'axios'

/**
 * Generic data fetcher composable
 * @returns Data fetcher methods and reactive states
 */
export function useDataFetcher<T>() {
  const loading = ref(false)
  const data = ref<T | null>(null) as Ref<T | null>
  const error = ref<string | null>(null)

  /**
   * Fetch data from API
   * @param apiCall API call function
   */
  async function fetchData(apiCall: () => Promise<AxiosResponse<any>>) {
    loading.value = true
    error.value = null
    
    try {
      const response = await apiCall()
      data.value = response.data
    } catch (err: any) {
      error.value = err.message || 'Failed to fetch data'
      console.error('Fetch error:', err)
    } finally {
      loading.value = false
    }
  }

  /**
   * Reset states
   */
  function reset() {
    loading.value = false
    data.value = null
    error.value = null
  }

  return {
    loading,
    data,
    error,
    fetchData,
    reset
  }
}