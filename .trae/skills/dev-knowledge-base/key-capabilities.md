# 七、关键技术能力提炼

> 本文件总结 DeltaTest 项目展现的架构设计、工程实践、安全工程和数据建模能力。
>
> **关联文件**：
> - 项目架构 → [architecture.md](./architecture.md)
> - 代码规范 → [code-standards.md](./code-standards.md)
> - 技术栈 → [tech-stack.md](./tech-stack.md)
> - 安全方案 → [security.md](./security.md)

---

## 7.1 架构设计能力

| 能力 | 体现 |
|------|------|
| **多语言协作架构** | Java(业务调度) + C(计算引擎/gRPC) + Python(AI/HTTP) + Vue(前端) 四语言协同 |
| **双模式驱动** | 变更自动闭环 + 手动录入触发，同一底座不同入口 |
| **异步消息解耦** | RabbitMQ 分离任务分发/执行/结果/日志四个阶段 |
| **实时推送** | WebSocket + ConcurrentHashMap 会话管理 |
| **跨语言接口规范** | REST/WS/gRPC/AMQP 四种协议按场景选型 |
| **前后端分离** | Vue 前端 + JWT 无状态认证 + Axios 拦截器 + Vite 代理 |

## 7.2 工程实践能力

| 能力 | 体现 |
|------|------|
| **分层架构** | Java: common→model→dao→service→web→admin 严格单向依赖；Vue: views→api→utils/stores→后端 |
| **统一响应体** | Java `R<T>` + 前端 `ApiResponse<T>` 前后端一致 |
| **配置外化** | Java `${ENV:default}` + Vue `VITE_XXX` 环境变量，12-Factor App 合规 |
| **API 文档驱动** | SpringDoc OpenAPI 全量注解 + 前端 API 层一一对应 |
| **双语注释** | 中文 + English 双语 Javadoc/JSDoc，国际化团队协作友好 |
| **逻辑删除规范** | 全表 `is_deleted` + `idx_is_deleted`，DDL 层统一约束 |
| **字典数据驱动** | `sys_dict_type` + `sys_dict_data`，枚举字段前端下拉/后端校验统一数据源 |
| **前端工程化** | Vite + pnpm + TypeScript + ESLint + Prettier，全链路自动化 |

## 7.3 安全工程能力

| 能力 | 体现 |
|------|------|
| **JWT 无状态认证** | 密钥单点管理、Token 签发/解析/刷新完整闭环 |
| **BCrypt 密码哈希** | 自适应算法，抗暴力破解 |
| **Spring Security 6** | 声明式授权 + 过滤器链 + 方法级安全 |
| **CORS 安全** | 凭证模式 + 预检缓存 |
| **前端认证闭环** | 路由守卫 + Axios 拦截器 + Token 过期自动跳转登录页 |
| **Python 配置安全** | `.env` 不提交 Git + `pydantic-settings` 类型校验 + LLM_API_KEY 环境变量注入 |

## 7.4 数据建模能力

| 能力 | 体现 |
|------|------|
| **5 域 34 表** | 系统管理/代码分析/测试管理/执行管控/质量报告 |
| **24 种字典** | 90 条预置数据，覆盖所有枚举字段 |
| **审计字段** | BaseEntity 统一 `id/isDeleted/createdAt/updatedAt`（Java）/ `id/is_deleted/created_at/updated_at`（DB列） |
| **乐观锁** | `@Version` 保护高并发更新场景 |
| **雪花 ID** | `ASSIGN_ID` 分布式主键 |
