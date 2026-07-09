/**
 * Vitest 单元测试配置
 * Vitest Unit Test Configuration
 * <p>
 * 配置测试环境、覆盖率和路径别名。
 * Configures test environment, coverage, and path aliases.
 * </p>
 *
 * @author ByDWL
 */
import {defineConfig} from 'vitest/config'
import vue from '@vitejs/plugin-vue'
import {resolve} from 'path'

export default defineConfig({
  plugins: [vue()],

  // 路径别名 / Path aliases
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src'),
    },
  },

  // 测试配置 / Test configuration
  test: {
    // 启用全局 API（describe/it/expect）/ Enable global APIs
    globals: true,

    // 测试环境 / Test environment
    environment: 'jsdom',

    // 测试文件匹配模式 / Test file match pattern
    include: ['src/**/__tests__/**/*.{test,spec}.{ts,tsx}'],

    // 覆盖率配置 / Coverage configuration
    coverage: {
      provider: 'v8',
      reporter: ['text', 'json', 'html'],
    },
  },
})
