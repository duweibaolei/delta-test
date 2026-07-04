/**
 * Axios HTTP 客户端配置
 * Axios HTTP Client Configuration
 * <p>
 * 统一配置请求/响应拦截器，自动携带 Token、处理错误码。
 * Unified request/response interceptor configuration, automatically carries Token, handles error codes.
 * </p>
 *
 * @author DeltaTest
 */
import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, InternalAxiosRequestConfig, AxiosResponse } from 'axios'
import { message } from 'ant-design-vue'
import { useUserStore } from '@/stores/user'
import router from '@/router'

/**
 * 统一响应体结构
 * Unified response body structure
 */
export interface ApiResponse<T = unknown> {
  /** 响应状态码 / Response status code */
  code: number
  /** 响应消息 / Response message */
  message: string
  /** 响应数据 / Response data */
  data: T
  /** 响应时间戳 / Response timestamp */
  timestamp: number
}

/**
 * 创建 Axios 实例
 * Create Axios instance
 */
const http: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '',
  timeout: 30000,
  withCredentials: true, // 为后续 Cookie 认证预留 / Reserved for future Cookie auth
  headers: {
    'Content-Type': 'application/json',
  },
})

/**
 * 请求拦截器
 * Request interceptor
 * <p>
 * 自动在请求头中注入 Authorization: Bearer {token}。
 * Automatically injects Authorization: Bearer {token} in request headers.
 * </p>
 */
http.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const userStore = useUserStore()
    if (userStore.token && config.headers) {
      config.headers.Authorization = `Bearer ${userStore.token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

/**
 * 响应拦截器
 * Response interceptor
 * <p>
 * 统一处理业务错误码、Token 过期、网络错误。
 * Unified handling of business error codes, token expiration, network errors.
 * </p>
 */
http.interceptors.response.use(
  (response: AxiosResponse<ApiResponse>) => {
    const { code, message: msg, data } = response.data

    // 成功（200）/ Success (200)
    if (code === 200) {
      return data as unknown as AxiosResponse
    }

    // Token 过期（1004）/ Token expired (1004)
    if (code === 1004) {
      const userStore = useUserStore()
      userStore.logout()
      router.push({ name: 'Login' })
      message.error('登录已过期，请重新登录 / Session expired, please login again')
      return Promise.reject(new Error(msg))
    }

    // Token 无效（1005）/ Token invalid (1005)
    if (code === 1005) {
      const userStore = useUserStore()
      userStore.logout()
      router.push({ name: 'Login' })
      message.error('认证失败，请重新登录 / Authentication failed, please login again')
      return Promise.reject(new Error(msg))
    }

    // 其他业务错误 / Other business errors
    message.error(msg || '操作失败 / Operation failed')
    return Promise.reject(new Error(msg))
  },
  (error) => {
    // HTTP 网络错误 / HTTP network error
    const status = error.response?.status
    const errorMessages: Record<number, string> = {
      400: '请求参数错误 / Bad request',
      401: '未认证 / Unauthorized',
      403: '无权限 / Forbidden',
      404: '资源不存在 / Not found',
      500: '服务器内部错误 / Internal server error',
      502: '上游服务不可用 / Upstream unavailable',
      503: '服务暂不可用 / Service unavailable',
    }
    const msg = errorMessages[status!] || `网络错误 / Network error: ${error.message}`
    message.error(msg)
    return Promise.reject(error)
  },
)

/**
 * GET 请求
 * GET request
 */
export function get<T = unknown>(url: string, config?: AxiosRequestConfig): Promise<T> {
  return http.get<unknown, T>(url, config)
}

/**
 * POST 请求
 * POST request
 */
export function post<T = unknown>(url: string, data?: unknown, config?: AxiosRequestConfig): Promise<T> {
  return http.post<unknown, T>(url, data, config)
}

/**
 * PUT 请求
 * PUT request
 */
export function put<T = unknown>(url: string, data?: unknown, config?: AxiosRequestConfig): Promise<T> {
  return http.put<unknown, T>(url, data, config)
}

/**
 * DELETE 请求
 * DELETE request
 */
export function del<T = unknown>(url: string, config?: AxiosRequestConfig): Promise<T> {
  return http.delete<unknown, T>(url, config)
}

export default http
