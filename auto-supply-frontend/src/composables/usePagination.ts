import { ref, Ref, computed, ComputedRef } from 'vue'

/**
 * Pagination composable
 * @param defaultPageSize Default page size (default: 10)
 * @returns Pagination states and methods
 */
export function usePagination(defaultPageSize = 10) {
  const currentPage = ref(1)
  const pageSize = ref(defaultPageSize)
  const total = ref(0)

  /**
   * Set current page
   * @param page Page number
   */
  function setPage(page: number) {
    currentPage.value = page
  }

  /**
   * Set page size
   * @param size Page size
   */
  function setPageSize(size: number) {
    pageSize.value = size
    // Reset to first page when page size changes
    currentPage.value = 1
  }

  /**
   * Set total count
   * @param count Total count
   */
  function setTotal(count: number) {
    total.value = count
  }

  /**
   * Go to next page
   */
  function nextPage() {
    if (currentPage.value < totalPages.value) {
      currentPage.value++
    }
  }

  /**
   * Go to previous page
   */
  function prevPage() {
    if (currentPage.value > 1) {
      currentPage.value--
    }
  }

  /**
   * Total pages computed
   */
  const totalPages: ComputedRef<number> = computed(() => {
    return Math.ceil(total.value / pageSize.value)
  })

  /**
   * Offset computed (for database queries)
   */
  const offset: ComputedRef<number> = computed(() => {
    return (currentPage.value - 1) * pageSize.value
  })

  /**
   * Check if has previous page
   */
  const hasPrev: ComputedRef<boolean> = computed(() => {
    return currentPage.value > 1
  })

  /**
   * Check if has next page
   */
  const hasNext: ComputedRef<boolean> = computed(() => {
    return currentPage.value < totalPages.value
  })

  /**
   * Reset pagination
   */
  function reset() {
    currentPage.value = 1
    pageSize.value = defaultPageSize
    total.value = 0
  }

  return {
    currentPage,
    pageSize,
    total,
    totalPages,
    offset,
    hasPrev,
    hasNext,
    setPage,
    setPageSize,
    setTotal,
    nextPage,
    prevPage,
    reset
  }
}