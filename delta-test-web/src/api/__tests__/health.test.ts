/**
 * 健康检查 API 单元测试
 * Health Check API Unit Tests
 * <p>
 * 测试健康检查 API 函数的请求构造和类型约束。
 * Tests health check API function request construction and type constraints.
 * </p>
 *
 * @author DeltaTest
 */
import { describe, expect, it, vi, beforeEach } from 'vitest'
import { healthCheckApi } from '../health'
import type { HealthVO } from '../health'

// Mock http 工具 / Mock http utilities
vi.mock('@/utils/http', () => ({
  get: vi.fn(),
  post: vi.fn(),
  put: vi.fn(),
  del: vi.fn(),
}))

import { get } from '@/utils/http'

const mockGet = vi.mocked(get)

describe('健康检查 API / Health Check API', () => {
  beforeEach(() => {
    vi.resetAllMocks()
  })

  describe('healthCheckApi', () => {
    it('应发送 GET 请求到 /api/health / should send GET request to /api/health', async () => {
      const mockResponse: HealthVO = {
        status: 'UP',
        service: 'delta-test-server',
        version: '1.0.0-SNAPSHOT',
      }
      mockGet.mockResolvedValue(mockResponse)

      const result = await healthCheckApi()

      expect(mockGet).toHaveBeenCalledWith('/api/health')
      expect(result).toEqual(mockResponse)
    })

    it('返回的 HealthVO 应包含必要字段 / returned HealthVO should contain required fields', async () => {
      const mockResponse: HealthVO = {
        status: 'UP',
        service: 'delta-test-server',
        version: '1.0.0-SNAPSHOT',
      }
      mockGet.mockResolvedValue(mockResponse)

      const result = await healthCheckApi()

      expect(result.status).toBe('UP')
      expect(result.service).toBe('delta-test-server')
      expect(result.version).toBe('1.0.0-SNAPSHOT')
    })

    it('请求失败时应抛出错误 / should throw error on request failure', async () => {
      mockGet.mockRejectedValue(new Error('Network error'))

      await expect(healthCheckApi()).rejects.toThrow('Network error')
    })
  })
})
