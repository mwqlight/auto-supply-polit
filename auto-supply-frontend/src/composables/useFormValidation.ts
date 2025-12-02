import { ref, Ref, reactive } from 'vue'

/**
 * Form validation composable
 * @returns Form validation methods and states
 */
export function useFormValidation() {
  const errors = reactive<Record<string, string>>({})
  const isValid = ref(true)

  /**
   * Validate required field
   * @param fieldName Field name
   * @param value Field value
   * @param displayName Display name for error message
   * @returns Validation result
   */
  function validateRequired(fieldName: string, value: any, displayName?: string): boolean {
    if (value === null || value === undefined || value === '') {
      errors[fieldName] = `${displayName || fieldName} is required`
      return false
    }
    delete errors[fieldName]
    return true
  }

  /**
   * Validate email format
   * @param fieldName Field name
   * @param value Email value
   * @param displayName Display name for error message
   * @returns Validation result
   */
  function validateEmail(fieldName: string, value: string, displayName?: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (value && !emailRegex.test(value)) {
      errors[fieldName] = `${displayName || fieldName} is not a valid email`
      return false
    }
    delete errors[fieldName]
    return true
  }

  /**
   * Validate min length
   * @param fieldName Field name
   * @param value Field value
   * @param minLength Minimum length
   * @param displayName Display name for error message
   * @returns Validation result
   */
  function validateMinLength(fieldName: string, value: string, minLength: number, displayName?: string): boolean {
    if (value && value.length < minLength) {
      errors[fieldName] = `${displayName || fieldName} must be at least ${minLength} characters`
      return false
    }
    delete errors[fieldName]
    return true
  }

  /**
   * Validate max length
   * @param fieldName Field name
   * @param value Field value
   * @param maxLength Maximum length
   * @param displayName Display name for error message
   * @returns Validation result
   */
  function validateMaxLength(fieldName: string, value: string, maxLength: number, displayName?: string): boolean {
    if (value && value.length > maxLength) {
      errors[fieldName] = `${displayName || fieldName} must be no more than ${maxLength} characters`
      return false
    }
    delete errors[fieldName]
    return true
  }

  /**
   * Custom validation
   * @param fieldName Field name
   * @param validator Validator function
   * @param errorMessage Error message
   * @returns Validation result
   */
  function validateCustom(fieldName: string, validator: () => boolean, errorMessage: string): boolean {
    if (!validator()) {
      errors[fieldName] = errorMessage
      return false
    }
    delete errors[fieldName]
    return true
  }

  /**
   * Clear specific field error
   * @param fieldName Field name
   */
  function clearError(fieldName: string) {
    delete errors[fieldName]
  }

  /**
   * Clear all errors
   */
  function clearErrors() {
    Object.keys(errors).forEach(key => {
      delete errors[key]
    })
  }

  /**
   * Check if form is valid
   * @returns Validity status
   */
  function validateForm(): boolean {
    isValid.value = Object.keys(errors).length === 0
    return isValid.value
  }

  /**
   * Get error message for field
   * @param fieldName Field name
   * @returns Error message
   */
  function getError(fieldName: string): string | undefined {
    return errors[fieldName]
  }

  return {
    errors,
    isValid,
    validateRequired,
    validateEmail,
    validateMinLength,
    validateMaxLength,
    validateCustom,
    clearError,
    clearErrors,
    validateForm,
    getError
  }
}