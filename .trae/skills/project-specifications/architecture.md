# 一、项目整体架构设计

> 本文件定义 DeltaTest 项目的整体架构、模块划分和数据流设计。
>
> **关联文件**：
> - 代码规范 → [code-standards.md](./code-standards.md)
> - 技术栈 → [tech-stack.md](./tech-stack.md)
> - 安全方案 → [security.md](./security.md)

---

## 1.1 系统定位

**双模式驱动的 Web 自动化测试平台**——支持「变更驱动自动闭环」（Git Webhook 触发）和「手动录入触发」两种模式，覆盖代码变更分析 → 用例匹配 → 任务执行 → 质量报告的全链路。

## 1.2 模块划分

### 1.2.1 Java 后端（8 个 Maven 子模块）

```
delta-test-server (父POM)
│
├── delta-test-common     ← 通用基础设施层
│   ├── base/             BaseEntity, BaseController
│   ├── config/           MyBatisPlusMetaObjectHandler
│   ├── constant/         CommonConstant, RedisKeyConstant
│   ├── enums/            RiskLevel, CaseStatus, ExecutionStatus, SourceType, TaskStatus, TriggerSource
│   ├── exception/        BusinessException, GlobalExceptionHandler
│   ├── result/           R<T>, ErrorCode, PageResult<T>
│   └── utils/            JwtUtil, SecurityUtil
│
├── delta-test-model      ← 数据模型层（纯 POJO，零业务依赖）
│   ├── entity/           36 个实体类（全部继承 com.dwl.common.base.BaseEntity）
│   ├── dto/              8 个请求传输对象
│   └── vo/               8 个视图响应对象
│
├── delta-test-dao        ← 数据访问层
│   └── mapper/           36 个 MyBatis-Plus Mapper 接口
│
├── delta-test-service    ← 业务逻辑层
│   ├── 接口/             17 个 Service 接口
│   └── impl/             8 个 ServiceImpl 实现类
│
├── delta-test-web        ← Web 表现层
│   ├── config/           SecurityConfig, JwtAuthenticationFilter, OpenApiConfig
│   ├── controller/       7 个 REST Controller
│   └── websocket/        WebSocketConfig, TaskProgressWebSocketHandler
│
├── delta-test-grpc-client ← gRPC 客户端模块
│   ├── proto/            code_analysis.proto (3 个 RPC 方法)
│   └── config/           GrpcClientConfig (ManagedChannel + TLS开关)
│
├── delta-test-mq         ← 消息队列模块
│   ├── config/           RabbitMqConfig (1 Exchange + 3 Queue + 3 Binding)
│   ├── producer/         TaskMessageProducer
│   ├── consumer/         TaskResultConsumer, TaskLogConsumer
│   └── message/          TaskExecuteMessage, TaskResultMessage, TaskLogMessage
│
└── delta-test-admin      ← 应用启动模块
    ├── DeltaTestApplication.java (main 方法)
    └── resources/         application.yml / -dev.yml / -prod.yml
```

### 1.2.2 Vue 前端（delta-test-web）

```
delta-test-web/
├── public/                  ← 静态资源
│   └── vite.svg             Favicon
├── src/
│   ├── api/                 ← API 调用层（与 Java 后端接口一一对应）
│   │   └── auth.ts          认证 API（loginApi, refreshTokenApi, logoutApi）
│   ├── assets/
│   │   └── styles/
│   │       └── global.less  全局样式 + CSS 变量 + 滚动条美化
│   ├── layouts/
│   │   └── MainLayout.vue   主布局（Sider + Header + Content）
│   ├── router/
│   │   └── index.ts         Vue Router 配置 + 路由守卫
│   ├── stores/
│   │   └── user.ts          Pinia 用户 Store（Token + 用户信息）
│   ├── utils/
│   │   └── http.ts          Axios 统一 HTTP 客户端（拦截器 + 错误码处理）
│   ├── views/
│   │   ├── login/           登录页
│   │   ├── dashboard/       工作台（统计卡片 + 快速入口）
│   │   ├── analysis/        变更分析（占位，Phase 1 开发）
│   │   ├── testcase/        用例管理（占位，Phase 2 开发）
│   │   ├── task/            任务中心（占位，Phase 1 开发）
│   │   └── report/          质量报告（占位，Phase 1 开发）
│   ├── App.vue              根组件（ConfigProvider + zhCN + dayjs locale）
│   └── main.ts              应用入口（Pinia + Router + Antd）
├── .env / .env.development / .env.production  ← 环境变量
├── index.html               HTML 入口
├── vite.config.ts           Vite 构建配置（插件 + 代理 + 分包）
├── tsconfig.json / tsconfig.app.json           ← TypeScript 配置
├── env.d.ts                 类型声明
└── package.json             项目清单（pnpm 10.x）
```

**前端核心分层**：

```
Views（页面组件）→ API（接口调用）→ Utils/HTTP（Axios 拦截器）→ Java 后端
     ↓                   ↓
  Layouts（布局）    Stores（Pinia 状态管理）
     ↓                   ↓
  Router（路由守卫）  localStorage（Token 持久化）
```

### 1.2.3 Python AI 服务（delta-test-ai）

```
delta-test-ai/
├── pyproject.toml              ← uv 包管理 + Ruff + pytest + mypy 配置
├── .python-version             ← Python 3.12
├── uv.lock                     ← 依赖锁定（可复现构建）
├── .env.example                ← 环境变量示例
├── Dockerfile                  ← 多阶段构建（uv + slim）
├── docker-compose.yml          ← 本地开发
├── README.md                   ← 项目说明
├── app/
│   ├── main.py                 ← FastAPI 入口（生命周期 + 路由注册）
│   ├── core/
│   │   ├── config.py           ← pydantic-settings 配置管理
│   │   └── llm.py              ← LLM 调用封装（骨架）
│   ├── agents/                 ← Agent 编排层（Phase 1 引入）
│   │   ├── base.py             ← BaseAgent 抽象基类
│   │   ├── master.py           ← MasterAgent（意图路由 + 编排 + 聚合）
│   │   ├── risk_agent.py       ← RiskAnalysisAgent
│   │   ├── summary_agent.py    ← ChangeSummaryAgent
│   │   ├── root_cause_agent.py ← RootCauseAgent
│   │   ├── case_gen_agent.py   ← CaseGenerationAgent
│   │   ├── semantic_agent.py   ← SemanticMatchAgent
│   │   └── tools/              ← Agent 可用工具
│   │       ├── base.py         ← BaseTool + @tool 装饰器 + ToolRegistry
│   │       ├── git_diff_tool.py
│   │       ├── scope_query_tool.py
│   │       ├── execution_log_tool.py
│   │       ├── failure_mark_tool.py
│   │       ├── page_element_tool.py
│   │       ├── business_link_tool.py
│   │       ├── embedding_tool.py
│   │       └── milvus_search_tool.py
│   ├── api/
│   │   ├── health.py           ← GET  /api/health
│   │   ├── risk_assessment.py  ← POST /api/risk-assessment
│   │   ├── summary.py          ← POST /api/summary
│   │   ├── root_cause.py       ← POST /api/root-cause
│   │   ├── case_generation.py  ← POST /api/case-generation
│   │   └── agent.py            ← POST /api/agent/chat (Phase 3)
│   ├── models/
│   │   ├── response.py         ← 统一响应体 R[T]（与 Java 对齐）
│   │   ├── requests.py         ← 4 个请求 DTO
│   │   ├── enums.py            ← RiskLevel + ErrorCode(5xxx)
│   │   └── agent.py            ← Agent 相关 DTO (Phase 2+)
│   ├── memory/                 ← 记忆管理（Phase 3）
│   │   ├── short_term.py       ← Redis 短期记忆
│   │   └── long_term.py        ← MySQL 长期记忆
│   ├── services/               ← 5 个服务（Service 调用 Agent）
│   │   ├── risk_service.py
│   │   ├── summary_service.py
│   │   ├── root_cause_service.py
│   │   ├── case_gen_service.py
│   │   └── embedding_service.py
│   ├── prompts/                ← 4 个 Prompt 模板
│   │   ├── risk_assessment.py
│   │   ├── summary.py
│   │   ├── root_cause.py
│   │   └── case_generation.py
│   └── utils/
└── tests/
    ├── conftest.py             ← pytest fixtures
    ├── test_health.py          ← 健康检查测试
    └── test_risk_assessment.py ← 风险评估测试
```

**Python AI 核心分层**：

```
API（FastAPI 路由）→ Service（业务逻辑）→ Agent（编排调度）→ LLM/Prompt（AI 调用）
     ↓                   ↓                    ↓
Models（请求/响应）  Core/Config（配置管理）  Tools（工具调用）
     ↓                                      ↓
Java 后端（HTTP REST 调用 Python → Python 返回 R<T>）  Memory（记忆管理，Phase 3）
```

**通信协议**：Java → Python 使用 HTTP REST（JSON），连接超时 5s / 读取超时 60s（AI 耗时较长）

## 1.3 模块依赖关系

```
admin → web → service → dao → model → common
              ↓            ↓
         grpc-client    mq → model → common
```

依赖规则：**单向向下**，上层可依赖下层，下层不可反向依赖。`common` 是最底层零业务依赖模块，`model` 仅依赖 `common`，`dao` 仅依赖 `model`，以此类推。

## 1.4 数据流设计

```
┌──────────────────────────────────────────────────────────────────────┐
│                         变更驱动自动模式                               │
│  Git Webhook → Java(/webhook) → gRPC → C引擎(ComputeDiff) → MySQL  │
│       → HTTP → Python AI(风险评估) → MySQL → 自动匹配用例 → 生成任务  │
│       → RabbitMQ → Playwright执行 → 结果回传MQ → WebSocket → 前端     │
├──────────────────────────────────────────────────────────────────────┤
│                         手动触发模式                                   │
│  Vue前端 → HTTP REST → Java CRUD → MySQL                            │
│  手动创建任务 → RabbitMQ → Playwright执行 → 结果回传 → 报告            │
├──────────────────────────────────────────────────────────────────────┤
│                         实时推送                                       │
│  MQ Consumer → TaskProgressWebSocketHandler → ConcurrentHashMap      │
│              → broadcast() / sendToSession() → Vue前端               │
├──────────────────────────────────────────────────────────────────────┤
│                    Agent 编排数据流（Phase 1 引入）                     │
│  Java → HTTP → Python AI MasterAgent                                │
│       → 并行调度 RiskAgent + SummaryAgent                             │
│       → 条件调度 SemanticMatchAgent（risk >= medium）                  │
│       → 结果聚合 → 回写 Java → MySQL                                  │
│  MQ TaskResultConsumer(FAILED)                                       │
│       → HTTP → Python AI RootCauseAgent                              │
│       → 结果回写 → Java → ai_root_cause 表                            │
│       → WebSocket → 前端通知                                          │
└──────────────────────────────────────────────────────────────────────┘
```
