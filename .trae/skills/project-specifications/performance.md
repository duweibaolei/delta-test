# 五、性能优化措施

> 本文件定义 DeltaTest 项目各层的性能优化策略与实现方案。
>
> **关联文件**：
> - 技术栈配置 → [tech-stack.md](./tech-stack.md)
> - 安全方案 → [security.md](./security.md)

---

## 5.1 数据库层

| 措施 | 实现 |
|------|------|
| **雪花主键** | `@TableId(type = IdType.ASSIGN_ID)`，避免自增ID锁竞争 |
| **逻辑删除** | `@TableLogic`，DELETE 变 UPDATE，避免物理删除引发的索引重建 |
| **自动填充** | `MetaObjectHandler`，避免每条 SQL 手动设置审计字段 |
| **乐观锁** | `@Version`（TestCase），高并发更新场景避免丢失更新 |
| **分页查询** | MyBatis-Plus `Page<T>` + `LambdaQueryWrapper` 条件构造 |
| **索引规范** | 所有表 `idx_is_deleted`，业务查询字段按需建索引 |

## 5.2 缓存层

| 措施 | 实现 |
|------|------|
| **Redis 连接池** | Lettuce `max-active=20, max-idle=10, min-idle=5` |
| **键命名规范** | `delta:test:{domain}:{key}` 冒号分隔层级 |
| **字典缓存** | `delta:test:dict:type:` / `delta:test:dict:data:` |
| **Token缓存** | `delta:test:login:token:` |
| **任务状态缓存** | `delta:test:task:status:` |

## 5.3 消息层

| 措施 | 实现 |
|------|------|
| **异步解耦** | RabbitMQ 分离任务分发与执行，非阻塞 |
| **手动ACK** | `acknowledge-mode: manual`，确保消息不丢失 |
| **消费限流** | `prefetch: 10`，防止单消费者过载 |
| **失败重试** | 3 次 + 1s 间隔 + requeue 重新入队 |
| **JSON序列化** | `Jackson2JsonMessageConverter`，跨语言友好 |

## 5.4 Web 层

| 措施 | 实现 |
|------|------|
| **无状态认证** | JWT + `SessionCreationPolicy.STATELESS`，服务端不存 Session |
| **WebSocket会话池** | `ConcurrentHashMap`，线程安全 + O(1) 查找 |
| **CORS预检缓存** | `maxAge=3600L`，减少 OPTIONS 请求 |
| **Jackson空值过滤** | `default-property-inclusion: non_null`，减少传输体积 |
| **文件上传限制** | 单文件 50MB / 总请求 100MB |

## 5.5 gRPC 层

| 措施 | 实现 |
|------|------|
| **Protobuf 二进制** | 比 JSON 更小的序列化体积和更快的编解码 |
| **连接复用** | `ManagedChannel` 单例 Bean，复用 TCP 长连接 |
| **TLS 可切换** | 开发环境 `usePlaintext()`，生产环境 `${grpc.client.engine.use-tls}` 开关 + 自定义 CA 证书 |

## 5.6 前端层

| 措施 | 实现 |
|------|------|
| **组件按需加载** | `unplugin-vue-components` + `AntDesignVueResolver`，仅打包使用到的 Ant Design Vue 组件 |
| **API 自动导入** | `unplugin-auto-import`，省去 `import { ref, computed } from 'vue'` 等样板代码 |
| **路由懒加载** | 所有页面组件 `() => import()` 动态导入，首屏仅加载当前路由组件 |
| **分包策略** | `manualChunks`：`vue-vendor`（vue/router/pinia）+ `antd-vendor`（antd/icons），优化缓存命中 |
| **ES2022 目标** | `build.target: es2022`，跳过旧浏览器 polyfill，减小包体积 |
| **Sourcemap** | 生产环境 `sourcemap: false`，不暴露源码 |
| **Vite 代理** | 开发环境 `/api` → `localhost:8080`，避免跨域和重复部署 |
| **Less 预处理** | CSS 变量 + Less 函数，减少重复样式代码 |

## 5.7 Python AI 服务层

| 措施 | 实现 |
|------|------|
| **异步框架** | FastAPI + `async def` 全异步，单进程高并发 |
| **依赖锁定** | `uv.lock` 确保可复现构建，避免依赖漂移 |
| **配置缓存** | `@lru_cache` 缓存 Settings 单例，避免重复解析 |
| **服务单例** | `get_xxx_service()` 工厂函数 + 模块级全局变量，避免重复实例化 |
| **多阶段 Docker** | uv builder 阶段 + slim runtime 阶段，镜像体积最小化 |
| **超时意识** | `LLM_TIMEOUT=55s`，在 Java 60s 读取超时内返回 |
| **Ruff 集成** | Lint + Format 一体化工具，替代 flake8 + black + isort |
