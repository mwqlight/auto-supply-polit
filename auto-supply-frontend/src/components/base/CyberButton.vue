<template>
  <button 
    class="cyber-button" 
    :class="[type, size, { 'is-disabled': disabled, 'is-loading': loading }]"
    :disabled="disabled || loading"
    @click="handleClick"
  >
    <span v-if="loading" class="loading-spinner"></span>
    <slot v-else></slot>
  </button>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'

// Define props
const props = withDefaults(defineProps<{
  type?: 'primary' | 'secondary' | 'success' | 'warning' | 'danger' | 'info'
  size?: 'small' | 'medium' | 'large'
  disabled?: boolean
  loading?: boolean
}>(), {
  type: 'primary',
  size: 'medium',
  disabled: false,
  loading: false
})

// Define emits
const emit = defineEmits<{
  (e: 'click', event: MouseEvent): void
}>()

// Methods
const handleClick = (event: MouseEvent) => {
  if (!props.disabled && !props.loading) {
    emit('click', event)
  }
}
</script>

<style scoped lang="scss">
.cyber-button {
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border: none;
  outline: none;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
  overflow: hidden;
  background: transparent;
  
  // Base styles
  &:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(45deg, 
      var(--color-primary), 
      var(--color-accent));
    opacity: 0.1;
    z-index: 0;
  }
  
  // Glowing border effect
  &:after {
    content: '';
    position: absolute;
    top: -2px;
    left: -2px;
    right: -2px;
    bottom: -2px;
    background: linear-gradient(45deg, 
      var(--color-primary), 
      var(--color-accent), 
      var(--color-primary));
    z-index: -1;
    border-radius: var(--border-radius);
    opacity: 0;
    transition: opacity 0.3s ease;
    animation: borderAnimation 3s linear infinite;
  }
  
  &:hover:not(.is-disabled):not(.is-loading) {
    transform: translateY(-2px);
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
    
    &:after {
      opacity: 1;
    }
  }
  
  &:active:not(.is-disabled):not(.is-loading) {
    transform: translateY(0);
  }
  
  // Button types
  &.primary {
    background: linear-gradient(45deg, 
      var(--color-primary), 
      var(--color-accent));
    color: white;
    box-shadow: 0 4px 15px rgba(0, 224, 255, 0.3);
    
    &:hover:not(.is-disabled):not(.is-loading) {
      box-shadow: 0 6px 20px rgba(0, 224, 255, 0.5);
    }
  }
  
  &.secondary {
    background: rgba(255, 255, 255, 0.1);
    color: var(--text-primary);
    border: 1px solid rgba(255, 255, 255, 0.2);
    
    &:hover:not(.is-disabled):not(.is-loading) {
      background: rgba(255, 255, 255, 0.15);
      border-color: rgba(255, 255, 255, 0.3);
    }
  }
  
  &.success {
    background: linear-gradient(45deg, 
      var(--color-success-start), 
      var(--color-success-end));
    color: white;
    box-shadow: 0 4px 15px rgba(0, 245, 168, 0.3);
    
    &:hover:not(.is-disabled):not(.is-loading) {
      box-shadow: 0 6px 20px rgba(0, 245, 168, 0.5);
    }
  }
  
  &.warning {
    background: linear-gradient(45deg, 
      var(--color-warning-start), 
      var(--color-warning-end));
    color: white;
    box-shadow: 0 4px 15px rgba(255, 170, 51, 0.3);
    
    &:hover:not(.is-disabled):not(.is-loading) {
      box-shadow: 0 6px 20px rgba(255, 170, 51, 0.5);
    }
  }
  
  &.danger {
    background: linear-gradient(45deg, 
      var(--color-danger-start), 
      var(--color-danger-end));
    color: white;
    box-shadow: 0 4px 15px rgba(255, 77, 79, 0.3);
    
    &:hover:not(.is-disabled):not(.is-loading) {
      box-shadow: 0 6px 20px rgba(255, 77, 79, 0.5);
    }
  }
  
  &.info {
    background: rgba(163, 103, 255, 0.2);
    color: var(--color-accent);
    border: 1px solid rgba(163, 103, 255, 0.3);
    
    &:hover:not(.is-disabled):not(.is-loading) {
      background: rgba(163, 103, 255, 0.3);
      border-color: rgba(163, 103, 255, 0.5);
    }
  }
  
  // Button sizes
  &.small {
    padding: 6px 12px;
    font-size: 0.8rem;
    border-radius: var(--border-radius-sm);
  }
  
  &.medium {
    padding: 10px 20px;
    font-size: 0.9rem;
    border-radius: var(--border-radius);
  }
  
  &.large {
    padding: 14px 28px;
    font-size: 1.1rem;
    border-radius: var(--border-radius-lg);
  }
  
  // Disabled state
  &.is-disabled {
    opacity: 0.5;
    cursor: not-allowed;
    transform: none !important;
    box-shadow: none !important;
    
    &:after {
      display: none;
    }
  }
  
  // Loading state
  &.is-loading {
    cursor: wait;
    
    .loading-spinner {
      width: 16px;
      height: 16px;
      border: 2px solid transparent;
      border-top-color: currentColor;
      border-radius: 50%;
      animation: spin 1s linear infinite;
    }
  }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@keyframes borderAnimation {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}
</style>