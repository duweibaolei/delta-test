# 八、已知待改进项

> 本文件记录 DeltaTest 项目当前已知的待改进项及计划阶段。
>
> **关联文件**：
> - 安全方案 → [security.md](./security.md)（Token 存储/黑名单等安全相关待办）
> - 技术栈 → [tech-stack.md](./tech-stack.md)（当前技术选型与版本）

---

| 项目 | 现状 | 建议 | 阶段 |
|------|------|------|------|
| **DTO/VO 字段映射** | `UserCreateDTO.nickname` → `SysUser.realName` 手动映射 | 引入 MapStruct 编译期类型安全转换 | Phase 1 |
| **WebSocket 认证** | `/ws/**` 当前 permitAll | 生产环境需握手拦截器验证 Token | Phase 2 |
| **Token 黑名单** | logout 为空操作 | Redis 存储已注销 Token 至过期 | Phase 2 |
| **Token 存储方式** | 前端 Token 存储在 localStorage，存在 XSS 风险 | 迁移到 HttpOnly Cookie + 双 Token 机制 | Phase 2+ |
| **国际化简版** | 登录页使用函数式 i18n，非 vue-i18n | Phase 3 迁移到 vue-i18n | Phase 3 |
| **占位页面** | 4个功能页面为空壳占位 | Phase 1-2 按模块逐步开发 | Phase 1-2 |
| **前端缺少 Mock** | 无 MSW Mock 服务 | 引入 Mock Service Worker | Phase 1 |
| **Python LLM 真实调用** | LLMService.generate() 返回 mock 占位 | Phase 1 接入 OpenAI SDK 真实调用 | Phase 1 |
| **Python Playwright 执行节点** | 骨架阶段无 Playwright 集成 | Phase 1 搭建 Playwright 执行框架 | Phase 1 |
| **Python 语义检索** | EmbeddingService 返回 mock 数据 | Phase 3 接入 sentence-transformers + Milvus | Phase 3 |
| **Agent 架构** | Python AI 5 个独立 Service，无编排能力 | 引入 Master-Slave Agent 架构，支持意图路由和并行调度 | Phase 1 |
| **Agent 记忆** | 无会话上下文保持 | 短期记忆（Redis）+ 长期记忆（MySQL/Milvus） | Phase 3 |
| **Agent 对话** | 仅支持单次请求-响应 | 新增对话式 Agent API，支持多轮交互 | Phase 3 |
| **Tool 注册** | 无工具抽象 | 统一 Tool 接口 + 装饰器注册 | Phase 1 |
| **C 引擎 TLS 实现** | gRPC Server TLS 模式降级为明文，打印 WARN | 集成 OpenSSL/Schannel 实现 TLS 服务端凭据 | Phase 1 |
| **C 引擎 Diff 真实计算** | `diff_engine_compute()` 返回占位数据 | 集成 libgit2 实现真实 diff 计算 | Phase 1 |
| **C 引擎影响分析真实实现** | `impact_analyzer_analyze()` 返回占位数据 | 集成 tree-sitter 依赖链分析 | Phase 1 |
| **C 引擎依赖图构建** | `dependency_graph_build()` 返回 0 | 集成 tree-sitter AST 解析 + 依赖图构建 | Phase 1 |
| **C 引擎日志集成** | spdlog 已声明但未使用 | 替换 `std::cout/cerr` 为 spdlog 异步日志 | Phase 1 |
| **C 引擎 Docker 非 root** | Dockerfile 未指定非 root 用户 | 添加 `USER nonroot` 指令 | Phase 1 |
| **C 引擎 Protobuf 版本对齐** | Conan 解析 protobuf/5.29.6，Java 侧 4.29.3 | 验证序列化兼容性，必要时 `override=True` | Phase 1 |
| **C 引擎 Windows gRPC 构建** | Conan 在 Windows 下安装 gRPC 存在兼容性问题 | 推荐使用 Docker/Linux 构建 gRPC Server | Phase 2 |
