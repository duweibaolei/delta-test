/**
 * HTTP 客户端单元测试
 * HTTP Client Unit Tests
 * <p>
 * 测试 Axios 实例的基础配置：baseURL、timeout、headers、withCredentials。
 * Tests Axios instance base configuration: baseURL, timeout, headers, withCredentials.
 * </p>
 *
 * @author ByDWL
 */
import {describe, expect, it} from 'vitest'
import http from '../http'

describe('HTTP 客户端配置 / HTTP Client Configuration', () => {
  describe('基础配置 / Base configuration', () => {
    it('timeout 应为 30000ms / timeout should be 30000ms', () => {
      expect(http.defaults.timeout).toBe(30000)
    })

    it('Content-Type 应为 application/json / Content-Type should be application/json', () => {
      expect(http.defaults.headers['Content-Type']).toBe('application/json')
    })

    it('withCredentials 应为 true（为 Cookie 认证预留）/ withCredentials should be true', () => {
      expect(http.defaults.withCredentials).toBe(true)
    })
  })

  describe('请求拦截器 / Request interceptors', () => {
    it('应注册了请求拦截器 / should have request interceptors registered', () => {
      expect(http.interceptors.request.handlers?.length).toBeGreaterThan(0)
    })
  })

  describe('响应拦截器 / Response interceptors', () => {
    it('应注册了响应拦截器 / should have response interceptors registered', () => {
      expect(http.interceptors.response.handlers?.length).toBeGreaterThan(0)
    })
  })
})
