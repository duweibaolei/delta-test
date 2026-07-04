# 五、开发流程与最佳实践

> 本文件定义 DeltaTest 项目的构建流程、响应体规范、异常处理策略、分层架构和配置管理。
>
> **关联文件**：
> - 代码规范 → [code-standards.md](./code-standards.md)
> - 技术栈 → [tech-stack.md](./tech-stack.md)
> - 安全方案 → [security.md](./security.md)

---

## 5.1 构建流程

**Java 后端：**

```bash
# 完整编译（含 gRPC 代码生成）
mvn clean compile

# 单模块编译（-am 同时编译依赖模块）
mvn compile -pl delta-test-service -am

# 打包（admin 模块 spring-boot-maven-plugin）
mvn clean package -DskipTests

# 运行
java -jar delta-test-admin/target/delta-test-admin-1.0.0-SNAPSHOT.jar
```

**编译期代码生成**：
- `protobuf-maven-plugin:0.6.1` 自动从 `.proto` 生成 Java/gRPC 桩代码
- `mapstruct-processor` 编译期生成 DTO↔Entity 转换实现
- `lombok` 编译期生成 getter/setter/builder/log 等

**注解处理器顺序**（POM 配置）：
```xml
<annotationProcessorPaths>
    <path>lombok</path>          <!-- 先处理 Lombok -->
    <path>mapstruct-processor</path>  <!-- 再处理 MapStruct -->
</annotationProcessorPaths>
```

**Vue 前端：**

```bash
# 安装依赖（需在 delta-test-web/ 目录下）
pnpm install

# 开发模式（HMR 热更新，http://localhost:5173）
pnpm dev

# 类型检查
pnpm type-check

# 生产构建（输出到 dist/）
pnpm build

# 预览构建产物
pnpm preview

# 代码格式化
pnpm format

# 代码检查
pnpm lint
```

**构建配置要点**：
- `pnpm approve-builds` 需批准 `core-js`、`esbuild`、`vue-demi` 的构建脚本
- `unplugin-vue-components` 自动生成 `src/components.d.ts` 组件类型声明
- `unplugin-auto-import` 自动生成 `src/auto-imports.d.ts` API 类型声明

**Python AI 服务：**

```bash
# 安装依赖（需在 delta-test-ai/ 目录下）
uv sync --extra dev

# 开发模式（HMR 热更新，http://localhost:8000）
uv run uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload

# 运行测试
uv run pytest -v

# 代码检查
uv run ruff check .

# 代码格式化
uv run ruff format .

# 类型检查
uv run mypy app/
```

**构建配置要点**：
- `uv sync` 根据 `uv.lock` 锁定版本安装，确保可复现构建
- `pyproject.toml` 统一管理依赖、Ruff/pytest/mypy 配置
- Docker 多阶段构建：`uv pip install` → 复制 `.venv` → slim 运行镜像
- `hatchling` 构建后端，`[tool.hatch.build.targets.wheel] packages = ["app"]`

**C 计算引擎：**

```bash
# ===== 方式一：CMake Preset（推荐）=====

# 仅构建 C 业务逻辑库 + 测试（无需 Conan）
cmake --preset mingw-debug
cmake --build --preset mingw-debug
ctest --preset mingw-debug

# Release 构建
cmake --preset mingw-release
cmake --build --preset mingw-release

# ===== 方式二：手动 CMake 命令 =====

# 配置（指定 MinGW64 编译器，禁用 gRPC）
cmake -DCMAKE_BUILD_TYPE=Debug ^
      -DCMAKE_C_COMPILER=G:/software/Development/mingw64/bin/gcc.exe ^
      -DCMAKE_CXX_COMPILER=G:/software/Development/mingw64/bin/g++.exe ^
      -DCMAKE_MAKE_PROGRAM=G:/software/Development/mingw64/bin/mingw32-make.exe ^
      -DENABLE_GRPC=OFF ^
      -G "MinGW Makefiles" ^
      -B cmake-build-debug

# 编译
cmake --build cmake-build-debug

# 运行测试
cd cmake-build-debug && ctest --output-on-failure

# ===== 方式三：Conan + gRPC 完整构建 =====

# 检测编译器 Profile
conan profile detect

# 安装依赖
conan install . --build=missing -s build_type=Release

# CMake 配置 + 编译 + 测试
cmake --preset conan-release
cmake --build --preset conan-release
ctest --preset conan-release --output-on-failure

# ===== Docker 构建 =====
docker build -t delta-test-engine:1.0.0-skeleton .
docker run -d -p 9090:9090 delta-test-engine:1.0.0-skeleton
```

**构建配置要点**：
- Windows 环境下 CLion 自带 MinGW 路径含空格会导致 `ld` 链接失败，需使用独立 MinGW64
- `CMakePresets.json` 提供 3 个预设：`mingw-debug`、`mingw-release`、`conan-release`
- `ENABLE_GRPC=OFF` 时仅构建 C 业务逻辑库 + 2 个 CTest，无需 Conan
- Docker 多阶段构建：`conanio/gcc12`（Conan 安装依赖 + CMake 编译）→ `debian:bookworm-slim`（精简运行）
- `conanfile.py` 中 `cmake` 工具依赖已注释（使用系统 CMake），避免 Conan 缓存问题

## 5.2 统一响应体规范

所有 API 返回值包装为 `R<T>`：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": { ... },
  "timestamp": 1704067200000
}
```

错误码体系：

| 范围 | 领域 |
|------|------|
| 200 | 成功 |
| 400-503 | 通用 HTTP 错误 |
| 1xxx | 用户/认证模块 |
| 2xxx | 测试用例模块 |
| 3xxx | 测试任务模块 |
| 4xxx | 测试报告模块 |
| 5xxx | 引擎与 AI 服务 |

## 5.3 异常处理策略

```
Controller → Service 抛出 BusinessException(ErrorCode)
         → GlobalExceptionHandler 统一拦截
         → 转换为 R.fail(errorCode) 返回前端
```

| 异常类型 | HTTP 状态码 | 处理方式 |
|----------|-------------|----------|
| `BusinessException` | 200 | 业务错误码 + 消息 |
| `MethodArgumentNotValidException` | 400 | 拼接所有字段错误 |
| `ConstraintViolationException` | 400 | 拼接所有约束违反 |
| `HttpMessageNotReadableException` | 400 | 请求体格式错误 |
| `Exception` (兜底) | 500 | 内部错误 + 日志堆栈 |

## 5.4 分层架构最佳实践

```
Controller（入参校验 @Valid + 路由）
  ↓ DTO
Service（业务逻辑 + 事务 @Transactional）
  ↓ Entity
Mapper（MyBatis-Plus BaseMapper，无自定义 SQL）
  ↓
Database
```

- **Controller**：只做参数接收和响应封装，不包含业务逻辑
- **Service**：事务边界，业务校验，DTO→Entity 转换
- **Mapper**：纯数据访问，当前阶段全部继承 `BaseMapper<T>` 零自定义 SQL
- **Entity→VO 转换**：`BeanUtils.copyProperties()` + 手动补充关联数据

## 5.5 配置管理

```yaml
application.yml        ← 基础配置（环境变量占位符 ${VAR:default}）
application-dev.yml    ← 开发环境覆盖
application-prod.yml   ← 生产环境覆盖
```

关键配置外化：
- 数据源：`MYSQL_HOST/PORT/DB/USER/PASSWORD`
- Redis：`REDIS_HOST/PORT/PASSWORD/DB`
- RabbitMQ：`RABBITMQ_HOST/PORT/USER/PASSWORD/VHOST`
- JWT：`JWT_SECRET/EXPIRATION`
- gRPC：`grpc.client.engine.host/port`

**Vue 前端：**

```yaml
.env                    ← 基础环境变量（VITE_API_BASE_URL, VITE_APP_TITLE）
.env.development        ← 开发环境覆盖
.env.production         ← 生产环境覆盖
```

关键环境变量：
- `VITE_API_BASE_URL`：后端 API 地址（开发环境为空，走 Vite 代理）
- `VITE_APP_TITLE`：应用标题（`DeltaTest - 双模式驱动的Web自动化测试平台`）

前端配置约定：
- 所有自定义环境变量必须以 `VITE_` 前缀开头（Vite 要求）
- 通过 `import.meta.env.VITE_XXX` 读取（TypeScript 类型声明在 `env.d.ts`）
- 敏感信息（如密钥）不得放在前端环境变量中

**Python AI 服务：**

```python
# pydantic-settings 自动读取环境变量 + .env 文件
class Settings(BaseSettings):
    model_config = SettingsConfigDict(env_file=".env", case_sensitive=False)

    SERVICE_HOST: str = "0.0.0.0"         # 环境变量 SERVICE_HOST
    SERVICE_PORT: int = 8000              # 环境变量 SERVICE_PORT
    LOG_LEVEL: str = "DEBUG"              # 环境变量 LOG_LEVEL
    JAVA_SERVICE_URL: str = "http://localhost:8080"
    LLM_API_KEY: str = ""                 # 骨架阶段无需配置
    LLM_BASE_URL: str = "https://api.openai.com/v1"
    LLM_MODEL: str = "gpt-4o"
    LLM_TIMEOUT: int = 55                 # 秒，Java 读取超时 60s 内
```

关键配置外化：
- 服务端口：`SERVICE_HOST` / `SERVICE_PORT`
- 日志级别：`LOG_LEVEL`
- Java 回调：`JAVA_SERVICE_URL`
- LLM 配置：`LLM_API_KEY` / `LLM_BASE_URL` / `LLM_MODEL` / `LLM_TIMEOUT`
- `.env` 文件不提交 Git（`.gitignore` 中排除），`.env.example` 作为示例

**C 计算引擎：**

```bash
# 环境变量配置（main.cpp 读取）
ENGINE_PORT=9090           # gRPC 监听端口
ENGINE_USE_TLS=false       # 是否启用 TLS（true/false）
ENGINE_CERT_PATH=          # 服务端证书路径（TLS 启用时必填）
ENGINE_KEY_PATH=           # 服务端私钥路径（TLS 启用时必填）
```

```toml
# conanfile.py 依赖配置
[requirements]
grpc/1.69.0                 # gRPC Server 依赖
spdlog/1.15.1               # 高性能异步日志

[options]
shared=False                # 静态链接 gRPC/Protobuf
fPIC=True                   # 位置无关代码（Linux 共享库需要）
grpc/*:shared=False         # gRPC 静态链接
protobuf/*:shared=False     # Protobuf 静态链接
```

关键配置要点：
- 环境变量通过 `std::getenv()` 读取，无配置文件，与 Docker/K8s 环境变量注入一致
- Conan 依赖通过 `conan install` 生成 `CMakeToolchain` + `CMakeDeps`，CMake 通过 `--toolchain-file` 加载
- `CMakePresets.json` 硬编码编译器路径（Windows MinGW64），Linux 环境无需指定
- gRPC/Protobuf 静态链接，运行时无需额外 DLL/SO
