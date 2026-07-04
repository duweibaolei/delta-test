# 六、安全实现方案

> 本文件定义 DeltaTest 项目的认证体系、密码安全、接口授权、密钥管理和数据安全方案。
>
> **关联文件**：
> - 技术栈配置 → [tech-stack.md](./tech-stack.md)（Spring Security 6 安全链）
> - 代码规范 → [code-standards.md](./code-standards.md)（命名/注释规范）
> - 待改进项 → [improvements.md](./improvements.md)（Token 存储/黑名单等安全待办）

---

## 6.1 认证体系

```
登录 → AuthController.login(LoginDTO)
     → AuthServiceImpl 验证用户名+BCrypt密码
     → JwtUtil.generateToken(userId, username)
     → 返回 LoginVO(token, tokenType, expiresIn, userId, username, nickname)

请求 → JwtAuthenticationFilter.doFilterInternal()
     → extractToken() 从 Authorization: Bearer xxx 提取
     → jwtUtil.parseToken() 验证签名+有效期
     → 设置 SecurityContext(UsernamePasswordAuthenticationToken)
     → 继续过滤链

刷新 → AuthController.refreshToken()
     → jwtUtil.parseToken() 解析旧 Token
     → 验证用户仍然有效
     → jwtUtil.generateToken() 签发新 Token
```

**JWT Token 结构**：
```
Header:  { "alg": "HS256" }
Payload: { "sub": "username", "userId": 123, "iat": ..., "exp": ... }
Signature: HMAC-SHA256(base64(header) + "." + base64(payload), secret)
```

## 6.2 密码安全

- **加密算法**：BCrypt（自适应哈希，强度因子由 Spring Boot 默认 10）
- **存储**：只存哈希值，不存明文
- **验证**：`passwordEncoder.matches(plain, hashed)`

## 6.3 接口授权

| 路径 | 权限 |
|------|------|
| `/api/auth/**` | 公开（登录/刷新/登出） |
| `/swagger-ui/**`, `/v3/api-docs/**` | 公开（API 文档） |
| `/ws/**` | 公开（WebSocket） |
| `/actuator/health` | 公开（健康检查） |
| 其他 `/api/**` | 需认证 |

## 6.4 JWT 密钥管理

- **单一来源**：`JwtUtil` 是唯一密钥持有者，`AuthServiceImpl` 和 `JwtAuthenticationFilter` 通过注入复用
- **配置外化**：`${JWT_SECRET:默认值}`，生产环境必须通过环境变量注入
- **默认密钥**：≥256 bits 满足 HMAC-SHA256 最小密钥长度要求

## 6.5 数据安全

- **逻辑删除**：所有表 `is_deleted` 字段，数据不会物理消失
- **SQL注入**：MyBatis-Plus `LambdaQueryWrapper` 参数化查询，无字符串拼接
- **敏感数据**：密码字段 `@Schema` 不标记 example，API 文档不暴露

## 6.6 前端安全

**认证流程（前后端联动）：**

```
登录页 → useUserStore.login(LoginDTO)
       → loginApi → POST /api/auth/login
       → Java AuthController → AuthServiceImpl → JwtUtil.generateToken()
       → 返回 LoginVO(token, tokenType, expiresIn, userId, username, nickname)
       → 前端存入 Pinia + localStorage
       → 后续请求 Axios 拦截器自动注入 Authorization: Bearer {token}
```

**路由守卫安全：**

- 全局 `beforeEach` 拦截所有路由跳转
- `meta.requiresAuth !== false` 的路由必须认证
- 未登录自动重定向到 `/login?redirect={原路径}`
- 登录后自动跳转回原始请求页面

**Token 安全策略：**

| 策略 | 实现 |
|------|------|
| **存储位置** | localStorage（key: `delta_test_token`），页面刷新不丢失 |
| **请求注入** | Axios 请求拦截器自动添加 `Authorization: Bearer {token}` |
| **过期处理** | 响应码 `1004` → 清除登录态 + 跳转登录页 |
| **无效处理** | 响应码 `1005` → 清除登录态 + 跳转登录页 |
| **刷新机制** | `refreshTokenApi()` 调用 `/api/auth/refresh` 换发新 Token |
| **待改进** | localStorage 存在 XSS 风险，后续迁移到 HttpOnly Cookie |

**XSS 防护：**

- Vue 模板默认转义 `{{ }}`，不使用 `v-html` 渲染用户输入
- `localStorage` Token 存在 XSS 风险（待改进）

**CSRF 防护：**

- 后端 JWT 无状态认证，无需 CSRF Token
- 前端通过 `Authorization` 头传递 Token，非 Cookie 自动携带
