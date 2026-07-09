# 三、核心技术栈应用

> 本文件定义 DeltaTest 项目的框架版本、中间件配置和核心技术集成方案。
>
> **关联文件**：
> - 项目架构 → [architecture.md](./architecture.md)
> - 代码规范 → [code-standards.md](./code-standards.md)
> - 性能优化 → [performance.md](./performance.md)
> - 安全方案 → [security.md](./security.md)

---

## 3.1 框架与库版本矩阵

| 层级 | 技术 | 版本 | 用途 |
|------|------|------|------|
| **运行时** | JDK | 17 | Java 运行时环境 |
| **框架** | Spring Boot | 3.4.1 | 应用框架（parent POM） |
| **安全** | Spring Security 6 | (Boot管理) | 认证授权 + BCrypt + JWT 无状态 |
| **ORM** | MyBatis-Plus (Boot3) | 3.5.9 | 数据访问增强，逻辑删除，分页插件，自动填充 |
| **API文档** | SpringDoc OpenAPI | 2.8.3 | Swagger UI + OpenAPI 3.0 规范 |
| **JWT** | jjwt | 0.12.6 | 令牌生成/解析（HMAC-SHA） |
| **RPC** | gRPC + Protobuf | 1.69.0 / 4.29.3 | Java↔C 引擎通信 |
| **消息** | Spring AMQP | (Boot管理) | RabbitMQ 异步任务分发 |
| **缓存** | Spring Data Redis | (Boot管理) | Lettuce 连接池 |
| **工具** | Hutool | 5.8.34 | 通用工具集 |
| **映射** | MapStruct | 1.6.3 | DTO↔Entity 编译期类型安全转换 |
| **简化** | Lombok | 1.18.36 | 样板代码消除 |
| **对象存储** | MinIO | 8.5.14 | 报告/截图文件存储 |
| **向量检索** | Milvus | 2.4.4 | AI 语义相似度搜索 |
| **测试** | Testcontainers | 1.20.4 | 集成测试容器化 |
| **日志编码** | logstash-logback-encoder | 8.0 | JSON 结构化日志（prod profile LogstashEncoder） |
| **数据库迁移** | Flyway | (Boot管理) | 版本化 DDL 迁移（flyway-core + flyway-mysql） |
| **AI HTTP** | RestTemplate | (Boot管理) | Java→Python AI 服务 HTTP 调用（@Qualifier 多 Bean 注入） |

**Vue 前端：**

| 层级 | 技术 | 版本 | 用途 |
|------|------|------|------|
| **框架** | Vue 3 | 3.5.x | Composition API + 响应式 |
| **构建** | Vite | 6.x | 极速 HMR，Vue 官方推荐 |
| **语言** | TypeScript | 5.8.x | 强类型约束 |
| **UI库** | Ant Design Vue | 4.x | 企业级组件 |
| **图标** | @ant-design/icons-vue | 7.x | 统一图标风格 |
| **路由** | Vue Router | 4.x | SPA 路由管理 + 守卫 |
| **状态** | Pinia | 2.x | 轻量 TypeScript 友好状态管理 |
| **HTTP** | Axios | 1.x | 拦截器机制统一处理 Token/错误 |
| **日期** | dayjs | 1.x | 轻量日期处理 + 中文 locale |
| **预处理** | Less | 4.x | CSS 增强 |
| **规范** | ESLint + Prettier | 9.x / 3.x | 代码风格统一 |
| **类型** | vue-tsc | 2.x | 编译期类型校验 |
| **测试** | Vitest | 3.2.x | 前端单元测试（jsdom + @vitest/coverage-v8） |
| **API类型** | openapi-typescript | ^7.13.0 | 从 OpenAPI 3.0 规范自动生成 TypeScript 类型定义 |
| **按需** | unplugin-vue-components | 28.x | Ant Design Vue 按需引入 |
| **自动** | unplugin-auto-import | 19.x | Vue/Router/Pinia API 自动导入 |

**Python AI 服务：**

| 层级 | 技术 | 版本 | 用途 |
|------|------|------|------|
| **运行时** | Python | 3.12 | 语言运行时 |
| **框架** | FastAPI | 0.115+ | 异步 Web 框架 + 自动 OpenAPI 文档 |
| **服务器** | Uvicorn | 0.34+ | ASGI 服务器（支持 WebSocket） |
| **数据校验** | Pydantic | 2.10+ | 数据模型 + 类型校验 + 序列化 |
| **配置管理** | pydantic-settings | 2.7+ | 环境变量 + .env 文件读取 |
| **HTTP客户端** | httpx | 0.28+ | 异步 HTTP 调用 Java 后端回调 |
| **日志** | loguru | 0.7+ | 结构化日志 + 自动异常捕获 |
| **包管理** | uv | 0.11+ | 极速依赖安装 + 虚拟环境管理 |
| **Lint** | Ruff | 0.9+ | 代码检查 + 格式化（替代 flake8 + black + isort） |
| **类型检查** | mypy | 1.14+ | 静态类型分析（strict 模式） |
| **测试** | pytest | 8.3+ | 异步测试 + pytest-asyncio |
| **Mock** | respx | 0.22+ | httpx 请求 Mock |

**C 计算引擎：**

| 层级 | 技术 | 版本 | 用途 |
|------|------|------|------|
| **语言标准** | C17 / C++17 | - | 业务逻辑用 C17，gRPC Server 用 C++17 |
| **构建系统** | CMake | 3.28+ | 跨平台构建配置 |
| **依赖管理** | Conan | 2.x | 第三方依赖安装 + CMakeDeps/CMakeToolchain 生成 |
| **RPC** | gRPC (grpc++) | 1.69.0 | C++ gRPC Server 实现 |
| **序列化** | Protobuf | 5.29.6 | Protobuf 消息定义 + 代码生成（Conan 解析版本，Java 侧为 4.29.3） |
| **日志** | spdlog | 1.15.1 | 高性能异步日志 |
| **容器** | Docker | - | 多阶段构建（conanio/gcc12 → debian:bookworm-slim） |
| **测试** | CTest | - | CMake 集成测试框架 |

**Phase 1 计划依赖：**

| 依赖 | 版本 | 用途 |
|------|------|------|
| libgit2 | 1.9.0 | Git 仓库操作（clone/pull/diff） |
| cjson | 1.7.18 | JSON 解析 |
| pcre2 | 10.44 | 正则表达式匹配 |
| tree-sitter | - | AST 解析 + 依赖图构建 |

## 3.2 Spring Security 6 安全链

```java
SecurityFilterChain:
  CsrfFilter          → DISABLED (REST API无状态)
  CorsFilter          → AllowedOriginPatterns(配置化 ${cors.allowed-origins}), Credentials=true
  SessionManagement   → STATELESS (无HttpSession)
  Authorization:
    /api/auth/**              → permitAll
    /swagger-ui/**            → permitAll
    /v3/api-docs/**           → permitAll
    /swagger-resources/**     → permitAll
    /ws/**                    → permitAll
    /actuator/health          → permitAll
    anyRequest                → authenticated
  JwtAuthenticationFilter → 解析 Bearer Token → 设置 SecurityContext(principal=userId, credentials=token)
```

## 3.3 MyBatis-Plus 增强配置

| 特性 | 配置 |
|------|------|
| 主键策略 | `ASSIGN_ID`（雪花算法，Long型） |
| 逻辑删除 | 字段 `isDeleted`（Java）/ `is_deleted`（DB列），0=正常/1=已删除 |
| 自动填充 | `createdAt`(INSERT), `updatedAt`(INSERT_UPDATE) |
| 驼峰映射 | `map-underscore-to-camel-case: true` |
| SQL日志 | `StdOutImpl`（开发环境） |
| 乐观锁 | `@Version` 注解（TestCase 等实体） |

## 3.4 gRPC 服务契约（Protobuf）

```protobuf
syntax = "proto3";
package delta.engine.v1;

service CodeAnalysisService {
  rpc ComputeDiff(DiffRequest) returns (DiffResponse);      // 代码差异计算
  rpc AnalyzeImpact(ImpactRequest) returns (ImpactResponse); // 影响范围分析
  rpc HealthCheck(HealthCheckRequest) returns (HealthCheckResponse);
}
```

消息设计要点：
- `java_multiple_files = true` → 每个 message 生成独立 Java 类
- `java_package = "com.dwl.grpc.engine"` → 统一包路径
- 响应含 `code + message` 标准化结构
- `repeated` 用于文件差异列表、影响范围列表
- `credential_type` + `credential_key` 用于 Git 仓库认证透传

**请求消息字段**：

| 消息 | 字段 | 类型 | 说明 |
|------|------|------|------|
| DiffRequest | repo_id | int64 | 仓库 ID |
| DiffRequest | repo_url | string | 仓库 URL |
| DiffRequest | branch | string | 分支名称 |
| DiffRequest | start_commit | string | 起始提交 |
| DiffRequest | end_commit | string | 结束提交 |
| DiffRequest | credential_type | string | 凭证类型（ssh/https/token） |
| DiffRequest | credential_key | string | 凭证密钥 |
| ImpactRequest | repo_id | int64 | 仓库 ID |
| ImpactRequest | repo_url | string | 仓库 URL |
| ImpactRequest | branch | string | 分支名称 |
| ImpactRequest | changed_files | repeated string | 变更文件列表 |
| ImpactRequest | credential_type | string | 凭证类型 |
| ImpactRequest | credential_key | string | 凭证密钥 |

**C 引擎 gRPC 构建**：

| 构建模式 | CMake 选项 | 依赖条件 | 构建产物 |
|----------|-----------|----------|----------|
| 仅 C 逻辑 | `-DENABLE_GRPC=OFF` | 无需 Conan | `libdelta_engine_core.a` + 2 CTest |
| 自动降级 | `-DENABLE_GRPC=ON`（默认） | 无 Protobuf | `libdelta_engine_core.a` + 2 CTest（WARNING） |
| 完整构建 | `-DENABLE_GRPC=ON` + Conan | Protobuf/gRPC 可用 | `libdelta_engine_core.a` + `delta-test-engine` 可执行文件 + 3 CTest |

**CMake Presets**：

| Preset | 用途 | gRPC | 工具链 |
|--------|------|------|--------|
| `mingw-debug` | Windows 日常开发 | OFF | MinGW64 (GCC 8.1.0) |
| `mingw-release` | Windows Release | OFF | MinGW64 (GCC 8.1.0) |
| `conan-release` | 完整 gRPC 构建 | ON | MinGW64 + Conan Toolchain |

## 3.5 RabbitMQ 拓扑

```
Exchange: delta.test.exchange (Topic)
  ├── delta.test.task.execute  ← task.execute   (Java→Playwright)
  ├── delta.test.task.result   ← task.result    (Playwright→Java)
  └── delta.test.task.log      ← task.log       (Playwright→Java)
```

- **消息序列化**：Jackson2JsonMessageConverter（JSON 格式）
- **确认模式**：手动 ACK（`acknowledge-mode: manual`）
- **预取数量**：10（`prefetch: 10`）
- **重试策略**：最多 3 次，间隔 1s
- **失败处理**：`basicNack` + requeue（重新入队）

## 3.6 WebSocket 实时推送

- **端点**：`/ws/task-progress`
- **会话管理**：`ConcurrentHashMap<String, WebSocketSession>`（线程安全）
- **推送模式**：`broadcast()` 全量广播 + `sendToSession()` 定向推送
- **跨域**：`setAllowedOrigins("*")`

## 3.7 Vue Router 路由体系

**路由表：**

| 路径 | 名称 | 组件 | 需认证 | 说明 |
|------|------|------|--------|------|
| `/login` | Login | `views/login/index.vue` | ❌ | 登录页面 |
| `/` | - | `layouts/MainLayout.vue` | ✅ | 主布局（重定向到 /dashboard） |
| `/dashboard` | Dashboard | `views/dashboard/index.vue` | ✅ | 工作台 |
| `/analysis` | Analysis | `views/analysis/index.vue` | ✅ | 变更分析 |
| `/testcase` | TestCase | `views/testcase/index.vue` | ✅ | 用例管理 |
| `/task` | Task | `views/task/index.vue` | ✅ | 任务中心 |
| `/report` | Report | `views/report/index.vue` | ✅ | 质量报告 |
| `/:pathMatch(.*)*` | - | - | - | 404 兜底重定向 |

**路由守卫流程：**

```
beforeEach → 设置页面标题（meta.title - VITE_APP_TITLE）
          → 检查 meta.requiresAuth（默认 true）
          → 需认证时检查 useUserStore().isLoggedIn
          → 未登录 → redirect /login?redirect={原路径}
```

## 3.8 Pinia 状态管理

**Store 定义规范：**

```typescript
// 使用 Setup Store 风格（Composition API）
export const useUserStore = defineStore('user', () => {
  // ==================== 状态 / State ====================
  const token = ref<string>(localStorage.getItem(TOKEN_KEY) || '')

  // ==================== 计算属性 / Computed ====================
  const isLoggedIn = computed(() => !!token.value)

  // ==================== 方法 / Actions ====================
  async function login(dto: LoginDTO): Promise<void> { ... }
  function logout(): void { ... }

  return { token, isLoggedIn, login, logout }
})
```

**已实现 Store：**

| Store | 文件 | 职责 |
|-------|------|------|
| `useUserStore` | `stores/user.ts` | Token 管理、用户信息、登录/登出/刷新 |

**Token 持久化：**

- 存储 key：`delta_test_token`
- 登录时写入 `localStorage`
- 退出时移除 `localStorage`
- 初始化时从 `localStorage` 恢复

## 3.9 Axios HTTP 客户端

**统一响应体结构**（与 Java `R<T>` 对应）：

```typescript
interface ApiResponse<T> {
  code: number      // 响应状态码（对应 Java R.code）
  message: string   // 响应消息（对应 Java R.message）
  data: T           // 响应数据（对应 Java R.data）
  timestamp: number // 响应时间戳（对应 Java R.timestamp）
}
```

**请求拦截器：**

```
每个请求 → 从 useUserStore() 获取 token
         → 注入 Authorization: Bearer {token}
```

**响应拦截器：**

| 错误码 | 处理方式 |
|--------|---------|
| `200` | 成功，解包返回 `data` 字段 |
| `1004` | Token 过期 → 清除登录态 → 跳转 `/login` |
| `1005` | Token 无效 → 清除登录态 → 跳转 `/login` |
| 其他业务码 | 弹出 `message.error()` 提示 |
| HTTP 400/401/403/404/500/502/503 | 状态码映射中文提示 + 弹窗 |

**导出函数：**

```typescript
export function get<T>(url: string, config?): Promise<T>
export function post<T>(url: string, data?, config?): Promise<T>
export function put<T>(url: string, data?, config?): Promise<T>
export function del<T>(url: string, config?): Promise<T>
```

## 3.10 Vite 构建配置

| 配置项 | 值 | 说明 |
|--------|-----|------|
| 路径别名 | `@` → `src/` | 简化导入路径 |
| API 代理 | `/api` → `http://localhost:8080` | 开发环境代理后端 |
| WebSocket 代理 | `/ws` → `ws://localhost:8080` | 实时通信代理 |
| 构建目标 | `es2022` | 现代浏览器特性 |
| 分包策略 | `vue-vendor` + `antd-vendor` | 优化缓存和加载 |
| CSS 预处理 | Less + `javascriptEnabled` | 支持内联 JS 表达式 |
| 组件按需 | `unplugin-vue-components` + `AntDesignVueResolver` | 按需生成组件导入 |
| API 自动导入 | `unplugin-auto-import` → `vue/vue-router/pinia` | 免写 `import { ref } from 'vue'` |
