<template>
  <div class="cyber-card" :class="[shadowClass, { 'is-hover': hover }]">
    <div v-if="$slots.header || header" class="cyber-card__header">
      <slot name="header">{{ header }}</slot>
    </div>
    <div class="cyber-card__body" :style="bodyStyle">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, defineProps } from 'vue'

// Define props
const props = withDefaults(defineProps<{
  header?: string
  bodyStyle?: Record<string, any>
  shadow?: 'always' | 'hover' | 'never'
  hover?: boolean
}>(), {
  header: '',
  bodyStyle: () => ({}),
  shadow: 'always',
  hover: false
})

// Computed properties
const shadowClass = computed(() => {
  return `cyber-card--${props.shadow}`
})
</script>

<style scoped lang="scss">
.cyber-card {
  position: relative;
  border-radius: var(--border-radius);
  background: rgba(10, 10, 10, 0.7);
  border: 1px solid rgba(255, 255, 255, 0.1);
  overflow: hidden;
  color: var(--text-primary);
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
  
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
    opacity: 0.05;
    z-index: 0;
  }
  
  // Glowing border effect
  &:after {
    content: '';
    position: absolute;
    top: -1px;
    left: -1px;
    right: -1px;
    bottom: -1px;
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
  
  &.is-hover:hover,
  &.cyber-card--always {
    &:after {
      opacity: 1;
    }
  }
  
  &.cyber-card--hover:hover {
    transform: translateY(-5px);
    box-shadow: 0 15px 30px rgba(0, 0, 0, 0.3);
  }
}

.cyber-card__header {
  padding: 18px 20px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  box-sizing: border-box;
  font-weight: 600;
  font-size: 16px;
  position: relative;
  z-index: 1;
  
  :slotted(*) {
    margin: 0;
  }
}

.cyber-card__body {
  padding: 20px;
  position: relative;
  z-index: 1;
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