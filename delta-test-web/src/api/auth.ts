/**
 * 认证 API
 * Authentication API
 * <p>
 * 对接 Java 后端 /api/auth 接口，提供登录、刷新Token、退出登录。
 * Connects to Java backend /api/auth endpoints, providing login, refresh token, logout.
 * </p>
 *
 * @author DeltaTest
 */
import { post } from '@/utils/http'

/**
 * 登录请求 DTO
 * Login Request DTO
 */
export interface LoginDTO {
  /** 用户名 / Username */
  username: string
  /** 密码 / Password */
  password: string
}

/**
 * 登录响应 VO
 * Login Response VO
 */
export interface LoginVO {
  /** 访问令牌 / Access token */
  token: string
  /** 令牌类型 / Token type */
  tokenType: string
  /** 过期时间（秒）/ Expires in (seconds) */
  expiresIn: number
  /** 用户ID / User ID */
  userId: number
  /** 用户名 / Username */
  username: string
  /** 昵称 / Nickname */
  nickname: string
}

/**
 * 用户登录
 * User login
 *
 * @param data 登录请求 / Login request
 * @returns 登录响应 / Login response
 */
export function loginApi(data: LoginDTO): Promise<LoginVO> {
  return post<LoginVO>('/api/auth/login', data)
}

/**
 * 刷新Token
 * Refresh token
 *
 * @param token 旧Token / Old token
 * @returns 新Token / New token
 */
export function refreshTokenApi(token: string): Promise<string> {
  return post<string>('/api/auth/refresh', null, {
    headers: { Authorization: `Bearer ${token}` },
  })
}

/**
 * 退出登录
 * Logout
 */
export function logoutApi(): Promise<void> {
  return post<void>('/api/auth/logout')
}
