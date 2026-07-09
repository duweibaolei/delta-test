/**
 * 用户 Store 单元测试
 * User Store Unit Tests
 * <p>
 * 测试 useUserStore 的核心逻辑：初始状态、登录、登出、计算属性。
 * Tests core logic of useUserStore: initial state, login, logout, computed properties.
 * </p>
 *
 * @author ByDWL
 */
import {beforeEach, describe, expect, it, vi} from 'vitest'
import {createPinia, setActivePinia} from 'pinia'
import {useUserStore} from '../user'

// Mock localStorage / 模拟 localStorage
const localStorageMock = (() => {
  let store: Record<string, string> = {}
  return {
    getItem: vi.fn((key: string) => store[key] || null),
    setItem: vi.fn((key: string, value: string) => {
      store[key] = value
    }),
    removeItem: vi.fn((key: string) => {
      delete store[key]
    }),
    clear: vi.fn(() => {
      store = {}
    }),
  }
})()

Object.defineProperty(globalThis, 'localStorage', { value: localStorageMock })

describe('useUserStore / 用户 Store', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
    localStorageMock.clear()
    vi.clearAllMocks()
  })

  describe('初始状态 / Initial state', () => {
    it('token 应为空字符串 / token should be empty string', () => {
      const store = useUserStore()
      expect(store.token).toBe('')
    })

    it('userId 应为 null / userId should be null', () => {
      const store = useUserStore()
      expect(store.userId).toBeNull()
    })

    it('username 应为空字符串 / username should be empty string', () => {
      const store = useUserStore()
      expect(store.username).toBe('')
    })

    it('nickname 应为空字符串 / nickname should be empty string', () => {
      const store = useUserStore()
      expect(store.nickname).toBe('')
    })

    it('expiresIn 应为 0 / expiresIn should be 0', () => {
      const store = useUserStore()
      expect(store.expiresIn).toBe(0)
    })
  })

  describe('isLoggedIn 计算属性 / isLoggedIn computed', () => {
    it('token 为空时返回 false / should return false when token is empty', () => {
      const store = useUserStore()
      expect(store.isLoggedIn).toBe(false)
    })

    it('token 有值时返回 true / should return true when token has value', () => {
      const store = useUserStore()
      store.token = 'test-token'
      expect(store.isLoggedIn).toBe(true)
    })
  })

  describe('logout / 退出登录', () => {
    it('应清除所有状态 / should clear all state', () => {
      const store = useUserStore()
      store.token = 'test-token'
      store.userId = 1
      store.username = 'admin'
      store.nickname = '管理员'
      store.expiresIn = 3600

      store.logout()

      expect(store.token).toBe('')
      expect(store.userId).toBeNull()
      expect(store.username).toBe('')
      expect(store.nickname).toBe('')
      expect(store.expiresIn).toBe(0)
    })

    it('应从 localStorage 移除 Token / should remove token from localStorage', () => {
      const store = useUserStore()
      store.token = 'test-token'
      store.logout()

      expect(localStorageMock.removeItem).toHaveBeenCalledWith('delta_test_token')
    })
  })
})
