# 八、智能体架构设计

> 本文件定义 DeltaTest 项目智能体（Agent）模块的架构设计、能力矩阵、集成方案、API 契约与实施路线图。
>
> **关联文件**：
> - 项目架构 → [architecture.md](./architecture.md)（系统分层与数据流）
> - 代码规范 → [code-standards.md](./code-standards.md)（Python 命名/注释/FastAPI 注解）
> - 安全方案 → [security.md](./security.md)（LLM API Key 管理/数据脱敏）
> - 数据库规范 → [database-design.md](./database-design.md)（公共字段标准）

---

## 8.1 架构概述

### 8.1.1 架构选型：Master-Slave 协作模式

DeltaTest 智能体采用 **Master-Slave 协作模式**：一个 Master Agent（Orchestrator）作为唯一对外入口，负责意图理解、任务分解、Sub-Agent 调度与结果聚合；5 个 Sub-Agent 各自封装单一 AI 能力，彼此无依赖，只与 Master 通信。

```
Java Backend (HTTP REST, 5s/60s timeout)
    │
    ▼
┌───────────────────────────────────────────────┐
│         FastAPI (delta-test-ai)                │
│                                               │
│  ┌─────────────────────────────────────────┐  │
│  │       Master Agent (Orchestrator)       │  │
│  │  · intent_parsing()   意图理解          │  │
│  │  · task_decomposition() 任务分解        │  │
│  │  · sub_agent_dispatch()  Sub调度        │  │
│  │  · result_aggregation()  结果聚合       │  │
│  └──────┬────────┬────────┬────────────────┘  │
│         │        │        │                   │
│  ┌──────▼──┐ ┌───▼────┐ ┌─▼───────────┐     │
│  │ Risk    │ │Summary │ │RootCause    │     │
│  │ Agent   │ │Agent   │ │Agent        │     │
│  └─────────┘ └────────┘ └─────────────┘     │
│  ┌──────────┐ ┌──────────────────────────┐   │
│  │CaseGen   │ │SemanticMatch            │   │
│  │Agent     │ │Agent (EmbeddingService)  │   │
│  └──────────┘ └──────────────────────────┘   │
└───────────────────────────────────────────────┘
```

### 8.1.2 Agent 通信协议

| 维度 | 设计 | 说明 |
|------|------|------|
| Master ↔ Sub-Agent | **进程内 async function call** | 同一 FastAPI 进程，无额外 RPC 开销；未来可拆为独立 HTTP 服务 |
| 通信数据结构 | `AgentResult` 数据类 | 包含 `success: bool`、`data: dict`、`error: str | None`、`metadata: dict`（含 model_version、token_usage、latency_ms） |
| Sub-Agent 间通信 | **禁止直接通信** | 需要协作的场景由 Master 编排（例如风险分析后调用语义匹配查找关联用例） |
| Java ↔ Master Agent | HTTP REST（JSON） | 复用现有 5 个端点路径不变，Agent 层为内部增强 |

### 8.1.3 记忆系统

| 类型 | 存储 | 键模式 | 内容 | TTL | 阶段 |
|------|------|--------|------|-----|------|
| **短期记忆** | Redis | `delta:test:agent:session:{session_id}` | 当前对话上下文（最近 10 轮消息 + 业务上下文） | 30 分钟 | Phase 3 |
| **长期记忆** | MySQL `agent_memory` 表 | `pattern_key` | 历史分析模式（如某模块变更常导致哪些用例失败） | 永久 | Phase 3 |
| **向量记忆** | Milvus | embedding 向量 | 用例/代码语义索引，支持相似度检索 | 永久 | Phase 3 |

- **会话标识**：`session_id` 由 Java 后端生成（基于 `ChangeAnalysis.id` 或 `TaskExecution.id`），确保与业务实体关联
- **长期记忆迁移**：Phase 3 初期以 MySQL 结构化查询为主，Milvus 集成后迁移向量检索

---

## 8.2 智能体能力矩阵

### 8.2.1 Sub-Agent 能力定义

| 维度 | RiskAnalysisAgent | ChangeSummaryAgent | RootCauseAgent | CaseGenerationAgent | SemanticMatchAgent |
|------|---|---|---|---|---|
| **角色** | 评估代码变更风险等级 | 生成变更摘要与测试建议 | 分析失败根因 | 生成测试用例 | 语义匹配关联用例 |
| **输入** | `changed_files`, `change_description`, `diff_content` | `changed_files`, `diff_content` | `case_name`, `error_message`, `execution_log` | `page_url`, `page_description`, `requirement` | `query`, `top_k` |
| **输出** | `risk_level`, `risk_factors`, `recommendation`, `affected_areas` | `summary`, `test_suggestions` | `possible_cause`, `confidence`, `fix_suggestion` | `test_cases`（含 steps + assertions） | `matched_cases`（含 case_id, similarity） |
| **可用工具** | `GitDiffTool`, `AffectedScopeQueryTool` | `GitDiffTool`, `AffectedScopeQueryTool` | `ExecutionLogTool`, `ManualFailureMarkTool` | `PageElementTool`, `BusinessLinkTool` | `EmbeddingTool`, `MilvusSearchTool` |
| **Prompt 策略** | 结构化 JSON Schema 约束 + few-shot 示例 | Chain-of-Thought 推理 + 输出结构化 | 多轮分析 + 置信度自评 + 失败标记反馈学习 | 分步生成（先步骤后断言）+ 页面元素注入 | Embedding 向量检索 + 语义相似度排序 |
| **错误处理** | 降级为基于文件数量的规则引擎 | 降级为文件列表拼接摘要 | 降级为返回错误信息原文 | 降级为返回空步骤模板 | 降级为关键字匹配 |
| **对应现有代码** | `risk_service.py` + `prompts/risk_assessment.py` | `summary_service.py` + `prompts/summary.py` | `root_cause_service.py` + `prompts/root_cause.py` | `case_gen_service.py` + `prompts/case_generation.py` | `embedding_service.py` |

### 8.2.2 Master Agent 能力定义

| 维度 | MasterAgent |
|------|---|
| **角色** | 意图理解、任务分解、Sub-Agent 调度、结果聚合 |
| **输入** | `AgentRequest`（含 `intent: str`, `context: dict`, `session_id: str`） |
| **输出** | `AgentResponse`（含 `results: dict`, `summary: str`, `suggested_actions: list`） |
| **调度策略** | 基于 intent 分类路由到对应 Sub-Agent；支持并行调度多个 Sub-Agent |
| **聚合策略** | 合并多 Sub-Agent 结果，去重，生成统一摘要 |

---

## 8.3 与现有系统集成

### 8.3.1 与 Java 后端集成

| 集成点 | 方式 | 说明 |
|--------|------|------|
| 现有 5 个 AI 端点 | **不改路径** | `/api/risk-assessment` 等端点路径不变，内部由 Service → Agent 重构 |
| `ChangeAnalysisService` | **增强** | 新增 `triggerAiAnalysis(Long analysisId)` 方法，按序调用风险评估 + 变更摘要 |
| `TaskResultConsumer` | **增强** | 任务执行失败时自动触发 RootCauseAgent |
| 新增 Java 端点（Phase 3） | `POST /api/agent/chat`、`GET /api/agent/session/{sessionId}` | 对话式 Agent 交互入口 |
| 新增 DTO | `AgentChatDTO`、`AgentChatVO` | 对话请求/响应数据结构 |

### 8.3.2 与 Python AI 服务集成

扩展 FastAPI 目录结构，新增 `agents/`、`memory/`、`tools/` 目录：

```
delta-test-ai/app/
├── agents/                       ← 新增：Agent 编排层
│   ├── __init__.py
│   ├── base.py                  ← BaseAgent 抽象基类
│   ├── master.py                ← MasterAgent 实现
│   ├── risk_agent.py            ← RiskAnalysisAgent
│   ├── summary_agent.py         ← ChangeSummaryAgent
│   ├── root_cause_agent.py      ← RootCauseAgent
│   ├── case_gen_agent.py        ← CaseGenerationAgent
│   ├── semantic_agent.py        ← SemanticMatchAgent
│   └── tools/                   ← Agent 可用工具
│       ├── __init__.py          ← ToolRegistry 注册表
│       ├── base.py              ← BaseTool 抽象基类 + @tool 装饰器
│       ├── git_diff_tool.py     ← GitDiffTool
│       ├── scope_query_tool.py  ← AffectedScopeQueryTool
│       ├── execution_log_tool.py← ExecutionLogTool
│       ├── failure_mark_tool.py ← ManualFailureMarkTool
│       ├── page_element_tool.py ← PageElementTool
│       ├── business_link_tool.py← BusinessLinkTool
│       ├── embedding_tool.py    ← EmbeddingTool
│       └── milvus_search_tool.py← MilvusSearchTool
├── api/
│   ├── ... (现有 5 个不变)
│   └── agent.py                 ← 新增: Agent 对话式交互 API (Phase 3)
├── models/
│   ├── ... (现有不变)
│   └── agent.py                 ← 新增: Agent 相关 DTO
├── memory/                       ← 新增：记忆管理
│   ├── __init__.py
│   ├── short_term.py            ← Redis 短期记忆 (Phase 3)
│   └── long_term.py             ← MySQL 长期记忆 (Phase 3)
├── services/                     ← 保留，Service 调用 Agent
├── prompts/                      ← 保留，各 Agent 引用对应 Prompt
├── core/
│   ├── config.py                ← 扩展: Agent 相关配置项
│   └── llm.py                   ← 扩展: 流式输出 + 多模型选择
└── utils/
```

**分层原则**：保留 `services/` 层作为对外接口，`agents/` 层作为内部编排层。Service 调用 Agent，而非 Agent 替换 Service，确保 Java 后端的调用方式不变。

### 8.3.3 与 RabbitMQ 管道集成

| 场景 | 触发方式 | 说明 |
|------|----------|------|
| 任务执行失败 → RootCauseAgent | `TaskResultConsumer` 消费 `task.result` | 状态为 FAILED 时自动触发根因分析 |
| Agent 分析异步触发（Phase 2） | 新增队列 `delta.test.agent.trigger` | Java 投递 Agent 分析任务，Python 消费执行 |
| Agent 结果通知 | 现有 WebSocket 推送 | Agent 分析完成后通过 `TaskProgressWebSocketHandler` 通知前端 |

### 8.3.4 Agent 结果回写数据库

| Agent | 结果写入表 | 映射字段 |
|-------|-----------|----------|
| RiskAnalysisAgent | `change_analysis` | `risk_level` + `ai_summary` + `ai_test_suggestion` |
| ChangeSummaryAgent | `change_analysis` | `ai_summary` + `ai_test_suggestion` |
| RootCauseAgent | `ai_root_cause` | `possible_cause` + `confidence` + `fix_suggestion` + `model_version` + `analyzed_at` |
| CaseGenerationAgent | `test_case` + `case_step` | 用例主体 + 步骤明细 |
| SemanticMatchAgent | `case_analysis_relation` | `case_id` + `analysis_id` + `affected_type` |
| Agent 对话历史 | `agent_conversation` | session_id + role + content + sub_results |

---

## 8.4 智能体 API 契约

### 8.4.1 新增 HTTP 端点

| 方法 | 路径 | 说明 | 阶段 |
|------|------|------|------|
| `POST` | `/api/agent/chat` | 对话式 Agent 交互 | Phase 3 |
| `GET` | `/api/agent/session/{sessionId}` | 获取会话历史 | Phase 3 |
| `POST` | `/api/agent/trigger` | 触发 Agent 分析任务（异步） | Phase 2 |
| `GET` | `/api/agent/task/{taskId}/status` | 查询 Agent 任务状态 | Phase 2 |

### 8.4.2 Request/Response DTO

```python
class AgentChatRequest(BaseModel):
    """Agent 对话请求 / Agent Chat Request"""
    message: str = Field(..., description="用户消息 / User message")
    session_id: str = Field(default="", description="会话ID / Session ID")
    context: dict = Field(default_factory=dict, description="上下文信息 / Context info")

class AgentChatResponse(BaseModel):
    """Agent 对话响应 / Agent Chat Response"""
    reply: str = Field(..., description="Agent 回复 / Agent reply")
    intent: str = Field(..., description="识别意图 / Identified intent")
    sub_results: list[dict] = Field(default_factory=list, description="子Agent结果 / Sub-agent results")
    suggested_actions: list[str] = Field(default_factory=list, description="建议操作 / Suggested actions")
    session_id: str = Field(..., description="会话ID / Session ID")

class AgentTriggerRequest(BaseModel):
    """Agent 触发请求 / Agent Trigger Request"""
    analysis_id: int = Field(..., description="变更分析ID / Change analysis ID")
    agent_types: list[str] = Field(default_factory=list, description="Agent类型列表 / Agent types to trigger")
    priority: str = Field(default="normal", description="优先级: high/normal/low / Priority")

class AgentTaskStatusResponse(BaseModel):
    """Agent 任务状态响应 / Agent Task Status Response"""
    task_id: str = Field(..., description="任务ID / Task ID")
    status: str = Field(..., description="状态: pending/running/completed/failed / Status")
    progress: float = Field(default=0.0, description="进度 0-1 / Progress 0-1")
    results: dict = Field(default_factory=dict, description="已完成结果 / Completed results")
```

### 8.4.3 WebSocket 流式通道

复用现有 `/ws/task-progress` 端点，新增消息类型：

| 消息类型 | 方向 | 格式 |
|----------|------|------|
| `agent_progress` | Server → Client | `{"type": "agent_progress", "agent_type": "risk_analysis", "session_id": "xxx", "progress": 0.5, "partial_result": {...}}` |
| `agent_result` | Server → Client | `{"type": "agent_result", "agent_type": "risk_analysis", "session_id": "xxx", "result": {...}}` |

---

## 8.5 智能体工具定义

### 8.5.1 工具清单

| 工具名 | 类型 | 可用 Agent | 说明 | 实现方式 |
|--------|------|-----------|------|----------|
| `GitDiffTool` | 查询 | RiskAnalysis, ChangeSummary | 获取 Git 变更差异 | HTTP 调用 Java `/api/analysis/{id}/diff` |
| `AffectedScopeQueryTool` | 查询 | RiskAnalysis, ChangeSummary | 查询影响范围 | HTTP 调用 Java `/api/analysis/{id}/scopes` |
| `ExecutionLogTool` | 查询 | RootCause | 获取执行日志 | HTTP 调用 Java `/api/execution/{id}/logs` |
| `ManualFailureMarkTool` | 查询 | RootCause | 获取人工失败标记 | HTTP 调用 Java `/api/execution/{id}/failure-mark` |
| `PageElementTool` | 查询 | CaseGeneration | 查询页面元素 | HTTP 调用 Java `/api/page/{id}/elements` |
| `BusinessLinkTool` | 查询 | CaseGeneration | 查询业务链路 | HTTP 调用 Java `/api/link/{id}` |
| `EmbeddingTool` | 计算 | SemanticMatch | 文本向量化 | 本地 sentence-transformers |
| `MilvusSearchTool` | 检索 | SemanticMatch | 向量相似度搜索 | pymilvus 客户端 |

### 8.5.2 工具注册与发现机制

采用**装饰器注册模式**：

```python
# 工具注册示例 / Tool registration example
@tool(name="git_diff", agents=["risk", "summary"])
class GitDiffTool(BaseTool):
    async def execute(self, params: dict) -> ToolResult:
        ...
```

- 工具注册表 `ToolRegistry` 在应用启动时自动扫描 `app/agents/tools/` 目录
- 每个 Tool 必须实现 `BaseTool` 接口：`async execute(params: dict) -> ToolResult`
- Agent 通过 `self.available_tools` 列表发现可用工具
- Master Agent 拥有全部工具权限，Sub-Agent 只能使用声明时可用的 Tool 列表

---

## 8.6 智能体记忆与上下文

### 8.6.1 短期记忆结构（Redis）

```json
{
  "session_id": "CA-20260704-0001",
  "messages": [
    {"role": "user", "content": "这次变更影响有多大？", "timestamp": 1720076400000},
    {"role": "agent", "content": "变更涉及3个核心服务...", "sub_agent": "risk", "timestamp": 1720076401000}
  ],
  "context": {
    "analysis_id": 123456,
    "risk_level": "medium",
    "changed_files_count": 5
  },
  "ttl": 1800
}
```

### 8.6.2 历史模式学习

- 学习场景：某模块变更 → 常见受影响用例 → 自动提升匹配权重
- 反馈信号：`ManualFailureMark` 作为人类反馈，训练 Agent 调整分析策略
- 存储位置：`agent_memory` 表（Phase 3 实现）

### 8.6.3 跨会话知识持久化

| 持久化目标 | 存储位置 | 说明 |
|-----------|----------|------|
| 分析结果 | `change_analysis`, `ai_root_cause` 等 | 现有业务表 |
| 对话历史 | `agent_conversation` | 新增表 |
| 工具调用记录 | `agent_tool_call` | 新增表，用于审计和优化 |
| 历史模式 | `agent_memory` | 新增表，Phase 3 实现 |

---

## 8.7 智能体决策逻辑

### 8.7.1 意图路由规则

| 触发场景 | Intent | 调度的 Sub-Agent | 阶段 |
|----------|--------|-----------------|------|
| Webhook 收到代码变更 | `risk_assessment` | RiskAnalysis + ChangeSummary（并行） | Phase 1 |
| 变更分析完成，需匹配用例 | `case_matching` | SemanticMatch | Phase 1 |
| 任务执行失败 | `root_cause_analysis` | RootCause | Phase 1 |
| 用户输入页面描述 | `case_generation` | CaseGeneration | Phase 2 |
| 用户对话式提问 | `chat` | Master 自行处理或路由 | Phase 3 |

### 8.7.2 编排策略

| 策略 | 场景 | 示例 |
|------|------|------|
| **串行编排** | 后序 Agent 依赖前序结果 | RiskAnalysis → SemanticMatch（风险结果指导用例匹配范围） |
| **并行编排** | Agent 间无依赖 | RiskAnalysis + ChangeSummary（两者独立，可并行执行） |
| **条件编排** | 满足条件才触发 | 仅当 `risk_level >= medium` 时才触发 SemanticMatch |

### 8.7.3 降级与回退策略

| 异常场景 | 降级策略 |
|----------|----------|
| LLM 调用失败 | 降级到规则引擎（当前 Service 层的 mock 逻辑即为降级实现） |
| Python AI 服务不可用 | Java 后端返回降级结果（`ErrorCode.AI_UNAVAILABLE = 5002`） |
| 工具调用超时 | 跳过该工具，使用已有上下文继续推理 |
| 多次重试失败 | 标记任务为 `failed`，通知前端人工介入 |

### 8.7.4 Human-in-the-Loop 检查点

| 检查点 | 触发时机 | 用户操作 | 对应字段 |
|--------|----------|----------|----------|
| 风险等级调整 | AI 产出 risk_level 后 | 手动调整风险等级 | `ChangeAnalysis.riskLevelManual` |
| 用例确认 | CaseGenerationAgent 生成用例后 | 人工确认才入库 | 用例 status = draft → active |
| 失败标记 | RootCauseAgent 分析后 | 人工标记 ManualFailureMark | `ManualFailureMark.failureReason` |
| 结果推送 | 所有检查点 | WebSocket 实时推送到前端 | — |

---

## 8.8 智能体配置

### 8.8.1 LLM 模型选择

| Agent | 推荐模型 | 原因 |
|-------|---------|------|
| MasterAgent | `gpt-4o` | 需要强推理能力进行意图理解和任务分解 |
| RiskAnalysisAgent | `gpt-4o` | 需要综合判断能力 |
| ChangeSummaryAgent | `gpt-4o-mini` | 摘要生成对推理能力要求较低，降低成本 |
| RootCauseAgent | `gpt-4o` | 需要深度推理和代码理解 |
| CaseGenerationAgent | `gpt-4o` | 需要结构化输出生成 |
| SemanticMatchAgent | 不使用 LLM | 纯向量检索 |

### 8.8.2 参数配置

```python
# Agent 全局配置 / Agent Global Configuration (app/core/config.py)
AGENT_DEFAULT_MODEL: str = "gpt-4o"               # 默认模型
AGENT_DEFAULT_TEMPERATURE: float = 0.3             # 低温度保证输出稳定
AGENT_MAX_TOKENS: int = 4096                       # 最大输出 token
AGENT_RETRY_MAX: int = 2                           # 最大重试次数
AGENT_RETRY_INTERVAL: int = 5                      # 重试间隔（秒）
AGENT_SESSION_TTL: int = 1800                      # 短期记忆 TTL（秒）
AGENT_MEMORY_ENABLED: bool = False                  # 长期记忆开关（Phase 3）

# 各 Agent 独立配置（通过环境变量覆盖）
AGENT_RISK_MODEL: str = ""                         # 空=使用默认
AGENT_RISK_TEMPERATURE: float = 0.2                # 风险评估更低温度
AGENT_SUMMARY_MODEL: str = "gpt-4o-mini"           # 摘要用轻量模型
AGENT_ROOT_CAUSE_MODEL: str = ""
AGENT_CASE_GEN_MODEL: str = ""
```

### 8.8.3 超时与重试策略

| 配置项 | 值 | 说明 |
|--------|-----|------|
| 单次 LLM 调用超时 | 55s | 与现有 `LLM_TIMEOUT` 一致，在 Java 60s 读取超时内返回 |
| Agent 总执行超时 | 120s | Master 编排多 Sub-Agent 的总时长上限 |
| 重试策略 | 指数退避，最多 2 次（5s → 25s） | LLM 调用失败时自动重试 |
| 工具调用超时 | 10s | 单个 Tool 的 HTTP 调用超时 |

---

## 8.9 安全考量

### 8.9.1 Agent 权限边界

| 规则 | 说明 |
|------|------|
| Agent 只能通过 Tool 访问 Java 后端 API | Tool 内部使用服务间认证（API Key 或内部 Token） |
| Agent 不可直接操作数据库 | 所有数据读写通过 Java 后端 API |
| Sub-Agent 只能使用声明时可用的 Tool | 不可动态获取未授权 Tool |
| Master Agent 拥有全部工具权限 | 但不可绕过 Java 后端权限校验 |

### 8.9.2 敏感数据处理

| 措施 | 说明 |
|------|------|
| LLM 数据隐私 | 使用私有化部署的 LLM 或签署 DPA 的云服务 |
| API Key 管理 | `LLM_API_KEY` 通过环境变量注入，不写入日志 |
| 敏感文件脱敏 | 发送到 LLM 前可选脱敏文件路径 |
| 对话历史精简 | 不存储完整代码内容，仅保留摘要 |

### 8.9.3 审计追踪

所有 Agent 调用记录写入 `agent_tool_call` 表：

| 审计字段 | 说明 |
|----------|------|
| `agent_type` | Agent 类型 |
| `tool_name` | 工具名称 |
| `input_hash` | 输入哈希（不存原文） |
| `output_hash` | 输出哈希 |
| `latency_ms` | 耗时（毫秒） |
| `status` | 状态：success / failed / timeout |
| `model_version` | LLM 模型版本 |
| `token_usage` | Token 使用量（JSON） |

---

## 8.10 实施路线图

### Phase 1（变更驱动自动闭环）

| 任务 | 模块 | 交付物 |
|------|------|--------|
| 重构现有 Service 为 Sub-Agent 骨架 | `app/agents/` | 5 个 Sub-Agent 空实现 + BaseAgent 抽象基类 |
| 实现 MasterAgent 基本调度 | `app/agents/master.py` | 意图路由 + 串行/并行编排 |
| 实现 RiskAnalysisAgent（接入真实 LLM） | `app/agents/risk_agent.py` | 替换 mock 为 LLM 调用 |
| 实现 ChangeSummaryAgent（接入真实 LLM） | `app/agents/summary_agent.py` | 替换 mock 为 LLM 调用 |
| 实现 SemanticMatchAgent（接入 Embedding） | `app/agents/semantic_agent.py` | 替换 mock 为向量检索 |
| 实现 RootCauseAgent（接入真实 LLM） | `app/agents/root_cause_agent.py` | 替换 mock 为 LLM 调用 |
| 实现 CaseGenerationAgent（接入真实 LLM） | `app/agents/case_gen_agent.py` | 替换 mock 为 LLM 调用 |
| 实现 GitDiffTool + AffectedScopeQueryTool | `app/agents/tools/` | HTTP 调用 Java 后端 |

### Phase 2（手动录入触发）

| 任务 | 交付物 |
|------|--------|
| 实现 ExecutionLogTool + ManualFailureMarkTool | RootCause 可获取执行上下文 |
| 实现 PageElementTool + BusinessLinkTool | CaseGeneration 可获取页面元素 |
| 新增 `POST /api/agent/trigger` 端点 | Java 可异步触发 Agent 任务 |
| 实现 Agent 降级策略 | LLM 不可用时自动降级到规则引擎 |

### Phase 3（双模式深度协同）

| 任务 | 交付物 |
|------|--------|
| 实现对话式 Agent API | `POST /api/agent/chat` + `GET /api/agent/session/{sessionId}` |
| 实现短期记忆（Redis） | 会话上下文保持（最近 10 轮，TTL 30 分钟） |
| 实现长期记忆（agent_memory 表） | 历史模式学习 + 反馈训练 |
| 实现 Human-in-the-Loop 检查点 | 风险调整确认 + 用例确认 + 失败标记 |
| 实现流式 WebSocket 推送 | Agent 进度实时可见（agent_progress / agent_result 消息类型） |
| 接入 Milvus 向量检索 | 语义匹配从 MySQL 关键字升级为向量相似度 |
