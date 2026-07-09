/**
 * 用户状态管理
 * User State Management (Pinia Store)
 * <p>
 * 管理用户登录状态、Token、用户信息。
 * Manages user login state, token, and user information.
 * </p>
 *
 * @author ByDWL
 */
import {defineStore} from 'pinia'
import {computed, ref} from 'vue'
import type {LoginDTO, LoginVO} from '@/api/auth'
import {loginApi, refreshTokenApi} from '@/api/auth'

/**
 * Token 存储键名
 * Token storage key name
 */
const TOKEN_KEY = 'delta_test_token'

/**
 * 用户 Store
 * User Store
 */
export const useUserStore = defineStore('user', () => {
  // ==================== 状态 / State ====================

  /** 访问令牌 / Access token */
  const token = ref<string>(localStorage.getItem(TOKEN_KEY) || '')

  /** 用户ID / User ID */
  const userId = ref<number | null>(null)

  /** 用户名 / Username */
  const username = ref<string>('')

  /** 昵称 / Nickname */
  const nickname = ref<string>('')

  /** 令牌过期时间（秒）/ Token expires in (seconds) */
  const expiresIn = ref<number>(0)

  // ==================== 计算属性 / Computed ====================

  /** 是否已登录 / Whether logged in */
  const isLoggedIn = computed(() => !!token.value)

  // ==================== 方法 / Actions ====================

  /**
   * 用户登录
   * User login
   *
   * @param dto 登录请求 / Login request DTO
   */
  async function login(dto: LoginDTO): Promise<void> {
    const data: LoginVO = await loginApi(dto)

    token.value = data.token
    userId.value = data.userId
    username.value = data.username
    nickname.value = data.nickname
    expiresIn.value = data.expiresIn

    // 持久化 Token / Persist token
    localStorage.setItem(TOKEN_KEY, data.token)
  }

  /**
   * 刷新令牌
   * Refresh token
   */
  async function refreshToken(): Promise<void> {
    if (!token.value) return

    const newToken = await refreshTokenApi(token.value)
    token.value = newToken
    localStorage.setItem(TOKEN_KEY, newToken)
  }

  /**
   * 退出登录
   * Logout
   */
  function logout(): void {
    token.value = ''
    userId.value = null
    username.value = ''
    nickname.value = ''
    expiresIn.value = 0
    localStorage.removeItem(TOKEN_KEY)
  }

  return {
    token,
    userId,
    username,
    nickname,
    expiresIn,
    isLoggedIn,
    login,
    refreshToken,
    logout,
  }
})
