# 四、代码规范与风格指南

> 本文件定义 DeltaTest 项目所有语言的命名约定、格式化规则、注释规范和 API 注解规范。
>
> **关联文件**：
> - 项目架构 → [architecture.md](./architecture.md)
> - 技术栈版本 → [tech-stack.md](./tech-stack.md)
> - 开发流程 → [dev-workflow.md](./dev-workflow.md)

---

## 4.1 命名约定

| 类别 | 规范 | 示例 |
|------|------|------|
| 包名 | 全小写，模块前缀 `com.dwl` | `com.dwl.service.impl` |
| 前端目录 | kebab-case，领域前缀 | `api/`, `views/login/`, `stores/` |
| 前端视图 | `src/views/{module}/index.vue` | `views/login/index.vue`, `views/dashboard/index.vue` |
| 前端布局 | PascalCase | `MainLayout.vue` |
| 前端Store | kebab-case.ts | `stores/user.ts` → 导出 `useUserStore` |
| 前端API | kebab-case.ts | `api/auth.ts` → 导出 `loginApi` |
| 前端路由名 | PascalCase | `Dashboard`, `TestCase`, `Login` |
| 前端API函数 | camelCase + `Api` 后缀 | `loginApi()`, `refreshTokenApi()` |
| 类名 | UpperCamelCase + 领域后缀 | `SysUserServiceImpl`, `LoginDTO`, `UserVO` |
| 方法名 | lowerCamelCase，动词开头 | `getByUsername()`, `createUser()`, `pageUsers()` |
| 常量 | UPPER_SNAKE_CASE | `LOGICAL_DELETE_DELETED`, `TASK_EXECUTE_KEY` |
| 枚举 | UpperCamelCase 枚举值 | `RiskLevel.HIGH`, `ExecutionStatus.RUNNING` |
| 数据库表 | snake_case，领域前缀 | `sys_user`, `change_analysis`, `test_case` |
| 数据库列 | snake_case | `is_deleted`, `created_at`, `risk_level` |
| API路径 | kebab-case 复数资源 | `/api/system/users`, `/api/auth/login` |
| MQ队列 | 点分隔命名 | `delta.test.task.execute` |
| Redis键 | 冒号分隔 | `delta:test:login:token:` |
| Vue组件文件 | PascalCase 或 index.vue | `MainLayout.vue`, `login/index.vue` |
| Vue API文件 | camelCase + Api 后缀 | `auth.ts` → `loginApi()`, `refreshTokenApi()` |
| Vue Store文件 | camelCase | `user.ts` → `useUserStore()` |
| TypeScript接口 | PascalCase + 后缀 | `LoginDTO`, `LoginVO`, `ApiResponse<T>` |
| CSS类名 | kebab-case + BEM 可选 | `.login-container`, `.layout-sider` |
| Python包名 | snake_case，模块前缀 `app.` | `app.api.health`, `app.services.risk_service` |
| Python文件名 | snake_case | `risk_assessment.py`, `case_gen_service.py` |
| Python类名 | UpperCamelCase + 领域后缀 | `RiskService`, `RiskAssessmentRequest`, `R` |
| Python函数名 | snake_case | `assess_risk()`, `get_risk_service()` |
| Python常量 | UPPER_SNAKE_CASE | `RISK_ASSESSMENT_SYSTEM_PROMPT`, `ENGINE_UNAVAILABLE` |
| Python枚举 | UpperCamelCase 枚举值 | `RiskLevel.HIGH`, `ErrorCode.AI_UNAVAILABLE` |
| C头文件 | snake_case + `.h` 后缀 | `diff_engine.h`, `impact_analyzer.h`, `bridge.h` |
| C源文件 | snake_case + `.c` / `.cpp` | `diff_engine.c`, `grpc_server.cpp` |
| C函数名 | snake_case + 模块前缀 | `diff_engine_compute()`, `bridge_compute_diff()` |
| C结构体 | UpperCamelCase | `DiffResult`, `ImpactResult` |
| C宏/常量 | UPPER_SNAKE_CASE | `DELTA_ENGINE_DIFF_ENGINE_H` |
| C测试文件 | `test_` 前缀 + snake_case | `test_diff_engine.c`, `test_grpc_server.cpp` |
| CMake target | snake_case | `delta_engine_core`, `delta-test-engine` |

## 4.2 类后缀约定

| 后缀 | 含义 | 层级 |
|------|------|------|
| `Entity` / 无后缀 | 数据库实体 | model/entity |
| `DTO` | 请求传输对象 | model/dto |
| `VO` | 视图响应对象 | model/vo |
| `Mapper` | MyBatis-Plus 接口 | dao/mapper |
| `Service` | 业务接口 | service |
| `ServiceImpl` | 业务实现 | service/impl |
| `Controller` | REST 控制器 | web/controller |
| `Config` | 配置类 | */config |
| `Filter` | Servlet 过滤器 | web/config |
| `Handler` | WebSocket/异常处理器 | web/websocket |
| `Consumer` | MQ 消费者 | mq/consumer |
| `Producer` | MQ 生产者 | mq/producer |
| `Message` | MQ 消息体 | mq/message |
| `Store` | Pinia 状态管理 | stores/ |
| `Api` | 前端 API 调用函数 | api/ |
| `Service` | Python 业务服务 | services/ |
| `Request` | Python 请求 DTO | models/requests.py |
| `VO` | Python 响应数据结构 | 内嵌于各 API 返回 |
| `Result` | C 桥接层返回结构体 | include/bridge.h |
| `Impl` | C++ gRPC 服务实现类 | src/grpc_server.cpp |

## 4.3 代码格式化规则

**Java 后端：**

- **缩进**：4 空格
- **编码**：UTF-8（POM 显式声明 `project.build.sourceEncoding`）
- **行宽**：无硬限制，但 Swagger 注解单行描述保持简洁
- **import 顺序**：`java.*` → `jakarta.*` → 第三方 → `com.dwl.*`
- **BOM**：所有源文件必须无 BOM 头（编译时已批量清理）

**Vue 前端：**

- **缩进**：2 空格
- **编码**：UTF-8
- **引号**：单引号（Prettier 默认）
- **分号**：无分号（TypeScript/Vue 风格）
- **尾逗号**：保留尾逗号（`trailingComma: all`）
- **import 顺序**：Vue 生态 → 第三方库 → `@/` 别名路径 → 相对路径
- **组件结构**：`<template>` → `<script setup lang="ts">` → `<style scoped lang="less">`

**Python AI 服务：**

- **缩进**：4 空格
- **编码**：UTF-8
- **行宽**：120 字符（Ruff `line-length = 120`）
- **引号**：双引号（Ruff `quote-style = "double"`）
- **行尾**：LF（Ruff `line-ending = "lf"`）
- **import 顺序**：标准库 → 第三方 → `app.*`（Ruff `I` 规则自动排序）
- **类型注解**：所有公共函数必须有类型注解（mypy `strict = true`）
- **中文标点**：允许 docstring/comment 中使用中文全角标点（Ruff 忽略 RUF001/RUF002/RUF003）

**Vue 前端：**

- **缩进**：2 空格
- **编码**：UTF-8
- **TypeScript 严格模式**：strict: true + noUncheckedIndexedAccess
- **组件风格**：`<script setup lang="ts">` Composition API，禁止 Options API
- **样式**：`<style scoped lang="less">`，scoped + Less
- **Pinia**：Composition API 风格（defineStore('name', () => {...})），禁止 Options API
- **组件自动导入**：unplugin-vue-components + AntDesignVueResolver({ importStyle: 'less' })
- **API 自动导入**：unplugin-auto-import（Vue/VueRouter/Pinia API）
- **错误码约定**：1004 = Token 过期，1005 = Token 无效（http.ts 拦截器处理）
- **路由 meta 约定**：title（中英双语）/icon（Ant Design 图标名）/requiresAuth

**C 计算引擎：**

- **缩进**：4 空格
- **编码**：UTF-8
- **行宽**：无硬限制，但函数签名过长时换行对齐
- **C 标准**：C17（业务逻辑 `.c` 文件），C++17（gRPC Server `.cpp` 文件）
- **警告级别**：`-Wall -Wextra -Wpedantic`（GCC/Clang），`/W4`（MSVC）
- **未使用参数**：使用 `(void)param;` 显式抑制，不使用 `__attribute__((unused))`
- **头文件保护**：使用 `#ifndef DELTA_ENGINE_MODULE_H` / `#define` / `#endif` 宏保护，不使用 `#pragma once`
- **C/C++ 兼容**：头文件使用 `#ifdef __cplusplus extern "C" { #endif` 包裹
- **import 顺序**：项目头文件 → C 标准库 → 第三方库
- **CMake 格式**：大写命令（`add_library`），2 空格缩进，注释用 `# ====================`

## 4.4 注释规范（双语注释）

**核心原则**：所有类、接口、公共方法和字段必须有**中英双语注释**。前后端一致。

**Java 后端：**

```java
/**
 * JWT工具类
 * JWT Utility Class
 * <p>
 * 提供JWT令牌的生成、解析和验证功能。
 * Provides JWT token generation, parsing, and verification capabilities.
 * </p>
 *
 * @author DeltaTest
 */
```

- **类注释**：Javadoc + 双语描述 + `<p>` 段落分隔 + `@author DeltaTest`
- **方法注释**：Javadoc + 双语简述 + `@param` + `@return`
- **字段注释**：行内双语 `/** 中文名 / English name */`
- **行内注释**：`// 中文 / English` 双语，业务关键逻辑处必须添加
- **分隔注释**：`// ==================== 分区名 / Section Name ====================`

**Vue 前端：**

```typescript
/**
 * 用户状态管理
 * User State Management (Pinia Store)
 * <p>
 * 管理用户登录状态、Token、用户信息。
 * Manages user login state, token, and user information.
 * </p>
 *
 * @author DeltaTest
 */
```

- **文件注释**：JSDoc + 双语描述 + `<p>` 段落分隔 + `@author DeltaTest`（放在 `<script setup>` 顶部）
- **函数注释**：JSDoc + 双语简述 + `@param` + `@returns`
- **变量注释**：行内双语 `/** 中文名 / English name */`
- **行内注释**：`// 中文 / English` 双语，业务关键逻辑处必须添加
- **分区注释**：`// ==================== 分区名 / Section Name ====================`

**Python AI 服务：**

```python
"""
风险评估服务
Risk Assessment Service
<p>
基于变更分析结果进行风险评估，骨架阶段返回占位数据。
Performs risk assessment based on change analysis results, returns placeholder data in skeleton phase.
</p>

@author DeltaTest
"""
```

- **模块注释**：三引号 docstring + 双语描述 + `<p>` 段落分隔 + `@author DeltaTest`
- **类注释**：三引号 docstring + 双语描述 + `<p>` 段落分隔
- **方法注释**：三引号 docstring + 双语简述 + `Args:` + `Returns:`
- **字段注释**：行内双语 `"""中文名 / English name"""`
- **行内注释**：`# 中文 / English` 双语，业务关键逻辑处必须添加
- **分隔注释**：`# ==================== 分区名 / Section Name ====================`

**C 计算引擎：**

```c
/**
 * @file diff_engine.c
 * Diff 计算模块实现
 * Diff Computation Module Implementation
 * <p>
 * 接收 commit 范围，调用 libgit2 计算 diff，输出变更文件列表与变更行号。
 * Receives commit range, calls libgit2 to compute diff, outputs changed file list and line numbers.
 * </p>
 *
 * @author DeltaTest
 */
```

- **文件注释**：Doxygen + 双语描述 + `<p>` 段落分隔 + `@author DeltaTest`
- **函数注释**：Doxygen + 双语简述 + `@param` + `@return`
- **字段注释**：行内双语 `/**< 中文名 / English name */`
- **行内注释**：`/* 中文 / English */` 双语，业务关键逻辑处必须添加
- **分隔注释**：`/* ==================== 分区名 / Section Name ==================== */`
- **C++ 头文件保护**：`#ifdef __cplusplus` 内的函数同样需要 Doxygen 双语注释

## 4.5 Swagger 注解规范

```java
@Tag(name = "用户管理", description = "用户管理 / User Management")          // Controller级
@Operation(summary = "创建用户", description = "创建用户 / Create user")      // 方法级
@Schema(description = "用户名 / Username", example = "admin",               // 字段级
         requiredMode = Schema.RequiredMode.REQUIRED)                       // 必填标记
@Parameter(description = "用户ID / User ID", required = true)              // 参数级
```

## 4.6 FastAPI 注解规范

```python
# 路由级
@router.post(
    "/api/risk-assessment",
    summary="风险评估 / Risk Assessment",
    description="基于代码变更分析结果进行风险评估 / Perform risk assessment based on code change analysis",
    tags=["风险评估 / Risk Assessment"],
)
# 请求字段级
changed_files: list[str] = Field(
    ...,
    description="变更文件路径列表 / List of changed file paths",
    examples=[["src/main/java/com/dwl/service/UserService.java"]],
)
# 响应体
class R(BaseModel, Generic[T]):
    code: int = Field(default=200, description="响应状态码 / Response status code")
    message: str = Field(default="success", description="响应消息 / Response message")
    data: T | None = Field(default=None, description="响应数据 / Response data")
    timestamp: int = Field(description="响应时间戳(毫秒) / Response timestamp (ms)")
```

**注解风格**：`summary` 和 `description` 采用 `"中文 / English"` 格式，Pydantic `Field.description` 同样 `"中文名 / English name"`，与 Java `@Tag`/`@Operation`/`@Schema` 保持一致。

## 4.7 Vue 前端组件与 API 规范

**组件编写规范：**

| 规范 | 说明 |
|------|------|
| 组件风格 | `<script setup lang="ts">` + Composition API，禁止 Options API |
| 样式隔离 | 必须使用 `<style scoped lang="less">`，防止样式泄漏 |
| 路径别名 | 使用 `@/` 代替相对路径（`@/api/auth.ts` 而非 `../api/auth.ts`） |
| 按需引入 | Ant Design Vue 组件通过 `unplugin-vue-components` 自动按需注册 |
| API 自动导入 | Vue/Router/Pinia API 通过 `unplugin-auto-import` 免 import |
| 图标引入 | 按需从 `@ant-design/icons-vue` 导入，不使用全量注册 |

**API 层规范：**

| 规范 | 说明 |
|------|------|
| 一一对应 | 每个 Java Controller 对应一个前端 API 文件（如 `auth.ts` ↔ `AuthController`） |
| 函数命名 | camelCase + `Api` 后缀（`loginApi()`, `refreshTokenApi()`） |
| DTO/VO 类型 | 与 Java 后端 `LoginDTO`/`LoginVO` 同名定义 TypeScript interface |
| 请求方法 | 使用 `@/utils/http.ts` 导出的 `get/post/put/del` 函数 |
| 错误处理 | 由 Axios 拦截器统一处理，API 层不需 try-catch（除非需特殊逻辑） |

**路由规范：**

| 规范 | 说明 |
|------|------|
| 懒加载 | 所有页面组件使用 `() => import()` 动态导入 |
| 路由元信息 | `meta.title`（页面标题）、`meta.requiresAuth`（是否需认证） |
| 守卫逻辑 | 全局 `beforeEach` 检查 `useUserStore().isLoggedIn` |
| 命名路由 | 所有路由必须设置 `name` 属性，编程式导航使用 `name` 而非 `path` |

## 4.8 C 引擎编码规范

**模块分层规范：**

| 规范 | 说明 |
|------|------|
| gRPC Server 用 C++17 | `grpc_server.cpp` + `main.cpp`，可引用 gRPC/Protobuf 头文件 |
| 业务逻辑用 C17 | `diff_engine.c` / `impact_analyzer.c` / `dependency_graph.c`，仅使用 C 标准库 |
| 桥接层用 `extern "C"` | `bridge.h` / `bridge.c`，提供 C/C++ 兼容接口 |
| C 库零 C++ 依赖 | `delta_engine_core` 静态库不链接 gRPC/Protobuf，可独立编译 |

**桥接层设计规范：**

| 规范 | 说明 |
|------|------|
| 结构体定义 | 在 `bridge.h` 中定义，C/C++ 共享（`DiffResult`, `ImpactResult`） |
| 函数声明 | 在 `bridge.h` 中用 `extern "C"` 声明，C/C++ 均可调用 |
| 参数类型 | 仅使用 C 兼容类型（`int`, `long long`, `const char*`, `const char**`） |
| 返回类型 | 使用桥接层结构体（`DiffResult`, `ImpactResult`），不返回 Protobuf 类型 |
| 委托模式 | `bridge_compute_diff()` → `diff_engine_compute()`，桥接层只做转发 |

**CMake 构建规范：**

| 规范 | 说明 |
|------|------|
| gRPC 可选 | `option(ENABLE_GRPC ...)` + `find_package(... QUIET)` + `if(GRPC_AVAILABLE)` |
| C 库始终构建 | `delta_engine_core` 不依赖 gRPC，放在 `if(GRPC_AVAILABLE)` 之外 |
| 测试条件构建 | gRPC 测试仅在 `GRPC_AVAILABLE` 时构建 |
| Proto 代码生成 | `add_custom_command` 手动调用 `protoc`，不使用 `CMAKE_AUTOMOC` |
| 静态链接 | gRPC/Protobuf 静态链接（Conan `shared=False`），运行时无需额外 DLL |

**Windows 构建注意事项：**

| 问题 | 原因 | 解决方案 |
|------|------|----------|
| MinGW `ld` 路径空格报错 | CLion 自带 MinGW 安装路径含空格 | 使用无空格路径的独立 MinGW64 |
| Cygwin gcc 不兼容 | Cygwin 编译器与 MinGW Makefiles 生成器不兼容 | 显式指定 `-DCMAKE_C_COMPILER` 和 `-DCMAKE_CXX_COMPILER` |
| Conan 缓存损坏 | Windows 下 Conan 缓存权限/锁文件问题 | `conan cache clean` 或删除 `.conan2/` 重装 |
