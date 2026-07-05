/**
 * 认证 API 单元测试
 * Authentication API Unit Tests
 * <p>
 * 测试登录、刷新Token、退出登录 API 函数的请求构造。
 * Tests login, refresh token, logout API function request construction.
 * </p>
 *
 * @author DeltaTest
 */
import { describe, expect, it, vi, beforeEach } from 'vitest'
import { loginApi, refreshTokenApi, logoutApi } from '../auth'
import type { LoginDTO, LoginVO } from '../auth'

// Mock http 工具 / Mock http utilities
vi.mock('@/utils/http', () => ({
  get: vi.fn(),
  post: vi.fn(),
  put: vi.fn(),
  del: vi.fn(),
}))

import { post } from '@/utils/http'

const mockPost = vi.mocked(post)

describe('认证 API / Authentication API', () => {
  beforeEach(() => {
    vi.resetAllMocks()
  })

  describe('loginApi', () => {
    it('应发送 POST 请求到 /api/auth/login / should send POST request to /api/auth/login', async () => {
      const dto: LoginDTO = { username: 'admin', password: '123456' }
      const mockVO: LoginVO = {
        token: 'test-token',
        tokenType: 'Bearer',
        expiresIn: 86400,
        userId: 1,
        username: 'admin',
        nickname: '管理员',
      }
      mockPost.mockResolvedValue(mockVO)

      const result = await loginApi(dto)

      expect(mockPost).toHaveBeenCalledWith('/api/auth/login', dto)
      expect(result).toEqual(mockVO)
    })

    it('应正确传递登录凭据 / should correctly pass login credentials', async () => {
      const dto: LoginDTO = { username: 'testuser', password: 'testpass' }
      mockPost.mockResolvedValue({} as LoginVO)

      await loginApi(dto)

      expect(mockPost).toHaveBeenCalledWith('/api/auth/login', {
        username: 'testuser',
        password: 'testpass',
      })
    })
  })

  describe('refreshTokenApi', () => {
    it('应发送 POST 请求到 /api/auth/refresh 并附带 Authorization header / should send POST with Authorization header', async () => {
      mockPost.mockResolvedValue('new-token')

      const result = await refreshTokenApi('old-token')

      expect(mockPost).toHaveBeenCalledWith('/api/auth/refresh', null, {
        headers: { Authorization: 'Bearer old-token' },
      })
      expect(result).toBe('new-token')
    })
  })

  describe('logoutApi', () => {
    it('应发送 POST 请求到 /api/auth/logout / should send POST request to /api/auth/logout', async () => {
      mockPost.mockResolvedValue(undefined)

      await logoutApi()

      expect(mockPost).toHaveBeenCalledWith('/api/auth/logout')
    })
  })
})
