<template>
  <div class="cyber-input-container" :class="{ 'is-focused': isFocused, 'is-disabled': disabled }">
    <input
      ref="inputRef"
      class="cyber-input"
      :class="[size, { 'has-prefix': $slots.prefix, 'has-suffix': $slots.suffix }]"
      :value="modelValue"
      :placeholder="placeholder"
      :disabled="disabled"
      :readonly="readonly"
      :type="type"
      @input="handleInput"
      @focus="handleFocus"
      @blur="handleBlur"
      @change="handleChange"
    />
    
    <div v-if="$slots.prefix" class="cyber-input__prefix">
      <slot name="prefix"></slot>
    </div>
    
    <div v-if="$slots.suffix" class="cyber-input__suffix">
      <slot name="suffix"></slot>
    </div>
    
    <div v-if="clearable && modelValue && !disabled" class="cyber-input__clear" @click="clear">
      <i class="el-icon-circle-close"></i>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, defineProps, defineEmits } from 'vue'

// Define props
const props = withDefaults(defineProps<{
  modelValue?: string | number
  placeholder?: string
  type?: string
  size?: 'small' | 'medium' | 'large'
  disabled?: boolean
  readonly?: boolean
  clearable?: boolean
}>(), {
  modelValue: '',
  placeholder: '',
  type: 'text',
  size: 'medium',
  disabled: false,
  readonly: false,
  clearable: false
})

// Define emits
const emit = defineEmits<{
  (e: 'update:modelValue', value: string | number): void
  (e: 'input', value: string | number): void
  (e: 'change', value: string | number): void
  (e: 'focus', event: FocusEvent): void
  (e: 'blur', event: FocusEvent): void
  (e: 'clear'): void
}>()

// Reactive data
const isFocused = ref(false)
const inputRef = ref<HTMLInputElement | null>(null)

// Methods
const handleInput = (event: Event) => {
  const value = (event.target as HTMLInputElement).value
  emit('update:modelValue', value)
  emit('input', value)
}

const handleChange = (event: Event) => {
  const value = (event.target as HTMLInputElement).value
  emit('change', value)
}

const handleFocus = (event: FocusEvent) => {
  isFocused.value = true
  emit('focus', event)
}

const handleBlur = (event: FocusEvent) => {
  isFocused.value = false
  emit('blur', event)
}

const clear = () => {
  emit('update:modelValue', '')
  emit('change', '')
  emit('clear')
  if (inputRef.value) {
    inputRef.value.focus()
  }
}

// Expose methods
defineExpose({
  focus: () => {
    if (inputRef.value) {
      inputRef.value.focus()
    }
  },
  blur: () => {
    if (inputRef.value) {
      inputRef.value.blur()
    }
  }
})
</script>

<style scoped lang="scss">
.cyber-input-container {
  position: relative;
  display: inline-block;
  width: 100%;
  vertical-align: middle;
  
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
    border-radius: var(--border-radius);
  }
  
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
  
  &.is-focused {
    &:after {
      opacity: 1;
    }
  }
  
  &.is-disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
}

.cyber-input {
  position: relative;
  display: block;
  width: 100%;
  height: 40px;
  padding: 0 15px;
  font-size: 14px;
  line-height: 40px;
  color: var(--text-primary);
  background: rgba(10, 10, 10, 0.7);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: var(--border-radius);
  outline: none;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
  box-sizing: border-box;
  z-index: 1;
  
  &::placeholder {
    color: var(--text-secondary);
  }
  
  &:focus {
    border-color: rgba(0, 224, 255, 0.5);
    box-shadow: 0 0 0 2px rgba(0, 224, 255, 0.2);
  }
  
  &.has-prefix {
    padding-left: 35px;
  }
  
  &.has-suffix {
    padding-right: 35px;
  }
  
  // Sizes
  &.small {
    height: 32px;
    line-height: 32px;
    padding: 0 12px;
    font-size: 13px;
  }
  
  &.medium {
    height: 40px;
    line-height: 40px;
    padding: 0 15px;
    font-size: 14px;
  }
  
  &.large {
    height: 48px;
    line-height: 48px;
    padding: 0 18px;
    font-size: 16px;
  }
  
  &:disabled {
    background: rgba(10, 10, 10, 0.4);
    cursor: not-allowed;
    color: var(--text-secondary);
  }
}

.cyber-input__prefix,
.cyber-input__suffix {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 2;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--text-secondary);
  
  i {
    font-size: 16px;
  }
}

.cyber-input__prefix {
  left: 12px;
}

.cyber-input__suffix {
  right: 12px;
}

.cyber-input__clear {
  position: absolute;
  top: 50%;
  right: 12px;
  transform: translateY(-50%);
  z-index: 2;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--text-secondary);
  cursor: pointer;
  transition: color 0.3s ease;
  
  i {
    font-size: 16px;
  }
  
  &:hover {
    color: var(--text-primary);
  }
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