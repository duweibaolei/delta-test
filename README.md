# DeltaTest - 双模式驱动的 Web 自动化测试平台

[English](#english-version) | 中文

---

## 项目简介

DeltaTest 是一个双模式驱动的 Web 自动化测试平台，支持传统测试用例管理的同时，还集成了 AI 能力来增强测试效率。平台采用微服务架构，后端基于 Spring Boot + MyBatis Plus，前端使用 Vue 3 + TypeScript，AI 服务基于 Python FastAPI 构建。

## 核心特性

- **双模式测试驱动**：支持传统手工测试用例管理和 AI 辅助测试
- **智能用例生成**：基于页面描述自动生成测试步骤与断言
- **变更影响分析**：分析代码变更的影响范围并评估风险
- **根因分析**：智能分析测试失败的可能原因
- **任务调度**：支持定时和触发式的测试任务执行
- **实时 WebSocket 推送**：测试进度和日志实时推送
- **完整的权限体系**：基于 RBAC 的用户和角色管理

## 技术架构

### 后端技术栈

| 组件 | 技术 |
|------|------|
| 框架 | Spring Boot 3.x |
| 数据库 | MySQL + MyBatis Plus |
| 消息队列 | RabbitMQ |
| 认证 | JWT + Spring Security |
| API 文档 | Swagger (OpenAPI 3.0) |
| 远程调用 | gRPC |

### 前端技术栈

| 组件 | 技术 |
|------|------|
| 框架 | Vue 3 + Composition API |
| 语言 | TypeScript |
| 构建工具 | Vite |
| 状态管理 | Pinia |
| HTTP 客户端 | Axios |
| UI 组件 | Ant Design Vue |

### AI 服务技术栈

| 组件 | 技术 |
|------|------|
| 框架 | FastAPI |
| 语言 | Python 3.12 |
| 包管理 | UV |
| 容器化 | Docker |

## 项目结构

```
delta-test/
├── delta-test-ai/                 # AI 服务 (Python FastAPI)
│   ├── app/
│   │   ├── api/                   # API 路由
│   │   ├── core/                  # 核心配置
│   │   ├── models/                # 数据模型
│   │   ├── prompts/               # AI 提示词
│   │   ├── services/              # 业务服务
│   │   └── utils/                 # 工具类
│   ├── tests/                     # 测试用例
│   ├── Dockerfile
│   └── docker-compose.yml
│
├── delta-test-server/             # 后端服务 (Java Spring Boot)
│   ├── delta-test-admin/          # 主应用入口
│   ├── delta-test-common/         # 公共组件
│   ├── delta-test-dao/            # 数据访问层
│   ├── delta-test-grpc-client/    # gRPC 客户端
│   ├── delta-test-model/          # 数据模型
│   ├── delta-test-mq/             # 消息队列
│   ├── delta-test-service/        # 业务服务层
│   └── delta-test-web/            # Web 控制层
│
├── delta-test-web/                # 前端应用 (Vue 3)
│   ├── src/
│   │   ├── api/                   # API 接口
│   │   ├── assets/                # 静态资源
│   │   ├── components/            # 公共组件
│   │   ├── layouts/               # 布局组件
│   │   ├── router/                # 路由配置
│   │   ├── stores/                # 状态管理
│   │   ├── utils/                 # 工具函数
│   │   └── views/                 # 页面视图
│   └── package.json
│
└── document/                      # 文档
    ├── archetype/                 # UI 原型
    └── sql/                       # 数据库脚本
```

## 快速开始

### 前置要求

- JDK 17+
- Maven 3.8+
- Node.js 18+
- pnpm 8+
- MySQL 8.0+
- RabbitMQ 3.12+

### 后端服务启动

1. 创建数据库并执行 SQL 脚本：

```bash
mysql -u root -p < document/sql/delta_test_ddl.sql
```

2. 修改配置文件 `delta-test-server/delta-test-admin/src/main/resources/application-dev.yml`，配置数据库和 Redis 连接信息。

3. 编译并启动后端服务：

```bash
cd delta-test-server
mvn clean install -DskipTests
cd delta-test-admin
java -jar target/delta-test-admin.jar
```

服务启动后访问 `http://localhost:8080`

### AI 服务启动

```bash
cd delta-test-ai

# 安装依赖
uv sync

# 启动服务
uv run uvicorn app.main:app --reload
```

AI 服务启动后访问 `http://localhost:8000`，API 文档位于 `http://localhost:8000/docs`

### 前端启动

```bash
cd delta-test-web

# 安装依赖
pnpm install

# 启动开发服务器
pnpm dev
```

前端启动后访问 `http://localhost:5173`

### Docker Compose 启动（推荐）

```bash
# 启动后端和 AI 服务
cd delta-test-ai
docker-compose up -d

# 启动前端
cd delta-test-web
docker-compose up -d
```

## API 文档

平台提供完整的 RESTful API 文档，通过 Swagger UI 访问：

- 后端 API：`http://localhost:8080/swagger-ui.html`
- AI 服务 API：`http://localhost:8000/docs`

### 主要 API 模块

| 模块 | 路径 | 说明 |
|------|------|------|
| 认证管理 | `/api/auth/*` | 登录、刷新 Token、登出 |
| 用户管理 | `/api/system/users` | 用户 CRUD |
| 角色管理 | `/api/system/roles` | 角色与权限管理 |
| 环境管理 | `/api/system/environments` | 测试环境配置 |
| 仓库管理 | `/api/system/repositories` | Git 仓库配置 |
| 字典管理 | `/api/system/dict-*` | 系统字典 |
| 用例管理 | `/api/case/*` | 测试用例管理 |
| 任务管理 | `/api/task/*` | 测试任务调度 |
| 报告管理 | `/api/report/*` | 测试报告 |
| AI 服务 | `/api/*-generation`, `/api/risk-assessment`, `/api/root-cause`, `/api/summary` | AI 能力接口 |

## 核心数据模型

### 测试用例 (TestCase)

```java
- id: Long              // 主键
- caseName: String      // 用例名称
- caseDesc: String      // 用例描述
- caseStatus: CaseStatus    // 用例状态
- sourceType: SourceType    // 来源类型
- priority: Integer     // 优先级
- tags: List<CaseTag>  // 标签
- steps: List<CaseStep>    // 测试步骤
- createBy: Long        // 创建人
- createTime: LocalDateTime  // 创建时间
- updateTime: LocalDateTime  // 更新时间
- deleted: Integer      // 逻辑删除
```

### 测试任务 (TestTask)

```java
- id: Long              // 主键
- taskName: String      // 任务名称
- taskDesc: String     // 任务描述
- taskStatus: TaskStatus   // 任务状态
- triggerSource: TriggerSource  // 触发来源
- envId: Long          // 环境 ID
- execConfig: ExecConfig    // 执行配置
- caseIds: List<Long>  // 关联用例
- createBy: Long       // 创建人
- createTime: LocalDateTime  // 创建时间
```

### 变更分析 (ChangeAnalysis)

```java
- id: Long              // 主键
- repoId: Long          // 仓库 ID
- branch: String        // 分支
- commitHash: String    // 提交哈希
- changeFiles: String   // 变更文件列表
- riskLevel: RiskLevel  // 风险等级
- aiSummary: String     // AI 分析摘要
- affectedScopes: List<AffectedScope>  // 影响范围
- createTime: LocalDateTime  // 创建时间
```

## AI 能力

### 1. 用例生成

根据页面 URL 和描述自动生成测试步骤和断言。

```bash
POST /api/case-generation
{
  "pageUrl": "https://example.com/login",
  "pageDescription": "用户登录页面，包含用户名、密码输入框和登录按钮",
  "requirement": "验证登录功能正常"
}
```

### 2. 风险评估

基于代码变更分析结果进行风险评估。

```bash
POST /api/risk-assessment
{
  "changedFiles": ["src/main/java/com/example/UserService.java"],
  "changeDescription": "修改了用户密码加密逻辑"
}
```

### 3. 根因分析

分析测试失败的可能原因。

```bash
POST /api/root-cause
{
  "caseName": "用户登录测试",
  "errorMessage": "ElementClickInterceptedException",
  "executionLog": "..."
}
```

### 4. 变更摘要

生成变更说明与测试建议。

```bash
POST /api/summary
{
  "changedFiles": ["src/main/java/..."],
  "diffContent": "..."
}
```

## 消息队列

平台使用 RabbitMQ 实现异步任务处理，主要队列：

| 队列名 | 说明 |
|--------|------|
| task.execute.queue | 任务执行队列 |
| task.result.queue | 任务结果队列 |
| task.log.queue | 任务日志队列 |

## 许可证

本项目基于 MIT 许可证开源，详见 [LICENSE](./LICENSE) 文件。

---

## English Version

# DeltaTest - Dual-Mode Web Automation Testing Platform

DeltaTest is a powerful dual-mode web automation testing platform that combines traditional test case management with AI-enhanced capabilities. Built with a microservices architecture, it features a Spring Boot backend, Vue 3 frontend, and Python AI service.

## Key Features

- **Dual-Mode Testing**: Traditional test case management with AI assistance
- **Smart Case Generation**: Auto-generate test steps and assertions from page descriptions
- **Change Impact Analysis**: Analyze code changes and assess risk levels
- **Root Cause Analysis**: Intelligent failure analysis
- **Task Scheduling**: Scheduled and triggered test execution
- **Real-time Updates**: WebSocket-based progress and log streaming
- **RBAC Security**: Complete user and role management

## Tech Stack

- **Backend**: Spring Boot 3.x, MyBatis Plus, MySQL, RabbitMQ
- **Frontend**: Vue 3, TypeScript, Vite, Ant Design Vue
- **AI Service**: Python 3.12, FastAPI, UV

## Quick Start

### Backend

```bash
# Create database and run SQL script
mysql -u root -p < document/sql/delta_test_ddl.sql

# Build and run
cd delta-test-server
mvn clean install -DskipTests
java -jar delta-test-admin/target/delta-test-admin.jar
```

### AI Service

```bash
cd delta-test-ai
uv sync
uv run uvicorn app.main:app --reload
```

### Frontend

```bash
cd delta-test-web
pnpm install
pnpm dev
```

## License

MIT License - see [LICENSE](./LICENSE) file.