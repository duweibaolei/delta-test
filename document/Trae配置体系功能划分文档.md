# DeltaTest 项目 Trae 配置体系功能划分文档

> 本文档详细阐述 `.trae/rules` 与 `.trae/skills` 两种配置资源的定位差异、功能划分与使用指南，确保项目知识体系结构清晰、职责分明。

---

## 一、核心概念

### 1.1 Rules — 始终生效的静态规则片段

Rules 是**全局强制生效**的规则声明，位于 `.trae/rules/` 目录。无论用户当前执行什么任务，Rules 中的内容都会被自动注入到 AI 助手的上下文中，作为**不可绕过的基础约束**。

**核心特征**：

| 属性 | 说明 |
|------|------|
| 生效方式 | **始终生效** — 每次 AI 交互自动加载，无需手动触发 |
| 内容性质 | **静态约束** — 硬性规则、禁止项、必选项、格式要求 |
| 上下文占用 | **常驻** — 占用系统提示词空间，应保持精简 |
| 修改频率 | **低频** — 规则一旦确定极少变更 |
| 典型用途 | 编码硬约束（包名前缀、必须字段）、禁止操作（不允许的 API）、全局格式要求 |

### 1.2 Skills — 按需加载的技能包

Skills 是**条件触发**的知识包，位于 `.trae/skills/` 目录。每个 Skill 通过 YAML front matter 中的 `name` 和 `description` 声明元数据，AI 助手根据用户意图**按需匹配并加载**相关技能，不相关时不占用上下文。

**核心特征**：

| 属性 | 说明 |
|------|------|
| 生效方式 | **按需加载** — AI 根据用户意图匹配相关 Skill，仅加载需要的部分 |
| 内容性质 | **动态知识** — 详细规范、参考代码、设计决策、最佳实践 |
| 上下文占用 | **按需** — 仅匹配的 Skill 文件被注入，未匹配的不占空间 |
| 修改频率 | **中频** — 随项目演进持续更新和扩展 |
| 典型用途 | 架构设计参考、代码风格指南、技术栈配置、安全方案、API 契约 |

---

## 二、核心差异对比

### 2.1 三维度系统性对比

| 对比维度 | Rules | Skills |
|----------|-------|--------|
| **使用方式** | 自动注入，无需用户或 AI 主动选择 | AI 根据意图匹配，用户也可通过 `/<skill-name>` 手动调用 |
| **灵活性** | 零灵活性 — 要么全部生效，要么删除文件 | 高灵活性 — 可按文件粒度选择性加载，10 个专题文件独立可用 |
| **上下文管理** | 常驻占用，内容必须精简（建议 < 2000 字符） | 按需加载，单个文件可详尽（当前最大约 5000 字），总上下文由 AI 自动控制 |
| **内容深度** | 仅声明"必须/禁止"，不解释原因 | 完整阐述规范细节、代码示例、设计决策与原理 |
| **组织结构** | 扁平文件，一个主题一个 `.md` | 目录化组织，含 SKILL.md 索引 + 多个专题文件 |
| **扩展方式** | 新增 `.md` 文件即可，但每新增一条规则都会增加常驻上下文 | 新增专题文件 + 更新 SKILL.md 索引，不影响其他 Skill 的加载 |
| **适用场景** | 任何场景都必须遵守的底线规则 | 特定开发任务需要的深度参考知识 |

### 2.2 类比说明

| 类比维度 | Rules 类比 | Skills 类比 |
|----------|-----------|------------|
| 法律体系 | **宪法** — 一切行为的根本约束 | **专门法** — 特定领域的详细规定 |
| 公司制度 | **员工手册** — 每个人必须遵守 | **项目文档** — 参与特定项目时才需要 |
| 软件架构 | **编译器内置规则** — 语法检查 | **第三方库文档** — 按需查阅 API |
| 交通规则 | **红绿灯** — 任何时候都必须遵守 | **导航路线** — 出行时按需规划 |

---

## 三、目录结构说明

### 3.1 当前完整目录树

```
.trae/
├── settings.json                                    # Trae IDE 工作区配置
├── rules/                                           # ← Rules 目录
│   └── project-constraints.md                       # 项目硬约束规则（始终生效）
└── skills/                                          # ← Skills 目录
    └── dev-knowledge-base/                           # 开发知识库（按需加载）
        ├── SKILL.md                                 # 技能入口索引（front matter + 文件索引 + 快速导航）
        ├── architecture.md                          # 一、项目整体架构设计
        ├── code-standards.md                        # 二、代码规范与风格指南
        ├── tech-stack.md                            # 三、核心技术栈应用
        ├── dev-workflow.md                          # 四、开发流程与最佳实践
        ├── performance.md                           # 五、性能优化措施
        ├── security.md                              # 六、安全实现方案
        ├── key-capabilities.md                      # 七、关键技术能力提炼
        ├── improvements.md                          # 八、已知待改进项
        ├── database-design.md                       # 九、数据库表设计规范
        └── agent.md                                 # 十、智能体架构设计
```

### 3.2 命名规范

| 资源类型 | 文件命名 | 目录命名 | Front Matter |
|----------|----------|----------|-------------|
| Rules | `{主题}.md`（kebab-case） | 无子目录，扁平结构 | 无需 front matter |
| Skills | `{主题}.md`（kebab-case） | `{skill-name}/` 目录，含 SKILL.md 入口 | `name` + `description` 必填 |

---

## 四、功能模块划分

### 4.1 Rules 模块定义

Rules 承载的是**不可妥协的硬约束**，适合放置以下内容：

| 模块 | 规则内容 | 对应 Skill 来源 |
|------|----------|----------------|
| **包名约束** | 所有 Java 代码包名必须以 `com.dwl` 开头 | code-standards.md §2.1 |
| **逻辑删除约束** | 所有数据库表必须包含 `is_deleted` 字段（TINYINT NOT NULL DEFAULT 0）并建立 `idx_is_deleted` 索引 | database-design.md §9.1 |
| **公共字段约束** | 所有实体类必须继承 BaseEntity，包含 id/isDeleted/createdAt/updatedAt | database-design.md §9.1 |
| **双语注释约束** | 所有类、方法、字段必须包含中英文双语注释/Javadoc | code-standards.md §2.4 |
| **Swagger 注解约束** | 所有 Controller/DTO/VO 必须有完整的 @Schema/@Operation/@Tag 注解 | code-standards.md §2.5 |
| **CORS 配置约束** | CORS allowed origins 必须通过 `${cors.allowed-origins:*}` 配置，禁止硬编码 | security.md §6.6 |
| **字典驱动约束** | 枚举字段必须使用 `sys_dict_type`/`sys_dict_data` 字典数据，禁止硬编码枚举值 | code-standards.md §2.1 |
| **HTTP 客户端约束** | Axios 必须启用 `withCredentials: true` | security.md §6.6 |
| **ID 策略约束** | 所有表主键使用雪花算法（`IdType.ASSIGN_ID`），禁止 AUTO_INCREMENT | database-design.md §9.1 |
| **Lombok 约束** | 实体类必须添加 `@EqualsAndHashCode(callSuper = true)` | code-standards.md §2.2 |

### 4.2 Skills 模块定义

Skills 承载的是**深度参考知识**，按功能域组织为 10 个专题文件：

| 序号 | 文件 | 功能域 | 核心内容 | 典型触发场景 |
|------|------|--------|----------|-------------|
| 一 | architecture.md | **架构** | 系统定位、8模块划分、依赖关系、4种数据流 | 理解项目全局、新增模块、调试数据流 |
| 二 | code-standards.md | **编码** | 命名约定、类后缀、格式化、双语注释、Swagger/FastAPI 注解 | 创建新类、编写代码、Review 代码 |
| 三 | tech-stack.md | **技术栈** | 三端版本矩阵、Security/MyBatis-Plus/gRPC/RabbitMQ/WebSocket 配置 | 配置中间件、升级依赖、调试集成 |
| 四 | dev-workflow.md | **流程** | 构建流程、R\<T\> 响应体、异常处理、分层架构、配置管理 | 新增 API、处理异常、外化配置 |
| 五 | performance.md | **性能** | DB/缓存/MQ/Web/gRPC/前端/Python AI 七层优化 | 性能优化、容量规划、慢查询排查 |
| 六 | security.md | **安全** | JWT 认证、BCrypt、接口授权、密钥管理、数据安全、前端安全 | 实现认证、处理 CORS、Token 管理 |
| 七 | key-capabilities.md | **能力** | 架构/工程/安全/数据建模四维能力总结 | 技术评审、简历编写、能力盘点 |
| 八 | improvements.md | **债务** | 14 项待改进项的现状/建议/阶段 | 技术规划、Phase 准入、版本规划 |
| 九 | database-design.md | **数据库** | 公共字段、表分类、字段顺序、索引规范、建表规范 | 设计新表、DDL Review、字段规范化 |
| 十 | agent.md | **智能体** | Master-Slave 架构、5 Agent 能力矩阵、API 契约、8 工具、记忆、决策逻辑 | 设计 Agent、扩展 AI 能力、Agent 集成 |

---

## 五、使用场景分析

### 5.1 Rules 适用场景

| 场景 | Rule 作用 | 示例 |
|------|----------|------|
| **新建 Java 类** | 确保包名 `com.dwl`、继承 BaseEntity、双语注释、Swagger 注解 | AI 生成代码时自动遵守这些硬约束 |
| **新建数据库表** | 确保包含 is_deleted/created_at/updated_at、雪花 ID、idx_is_deleted | AI 生成 DDL 时自动遵守这些硬约束 |
| **配置安全** | 确保 CORS 不硬编码、Axios 启用 withCredentials | AI 修改配置时自动遵守这些硬约束 |
| **代码 Review** | 作为 Review 的底线检查清单 | 任何违反 Rule 的代码都不应合并 |

### 5.2 Skills 适用场景

| 场景 | 触发的 Skill | 获取的知识 |
|------|-------------|-----------|
| **新增业务 API** | dev-workflow + code-standards | R\<T\> 响应体封装、分层架构职责、DTO/Entity 转换、命名规范 |
| **配置 RabbitMQ** | tech-stack + performance | Exchange/Queue 拓扑、手动 ACK、限流重试、异步解耦 |
| **实现 JWT 认证** | security + dev-workflow | 完整认证流程、Token 结构、密钥管理、异常处理策略 |
| **设计新数据库表** | database-design + code-standards | 公共字段标准、字段顺序、索引规范、Entity 命名 |
| **设计 Agent 功能** | agent + architecture | Master-Slave 架构、意图路由、工具注册、Agent 编排数据流 |
| **排查性能问题** | performance + tech-stack | 各层优化措施、中间件配置参数、缓存策略 |
| **理解项目全局** | architecture + key-capabilities | 系统定位、模块依赖、数据流、能力总结 |
| **规划技术债务** | improvements | 14 项待改进项的现状、建议、所属阶段 |

### 5.3 Rules 与 Skills 协作场景

**场景：新建一个业务实体类**

1. **Rules 自动生效**：包名 `com.dwl`、继承 BaseEntity、`@EqualsAndHashCode(callSuper=true)`、双语注释、Swagger 注解 — 这些是**不可协商的底线**
2. **Skills 按需加载**：code-standards.md 提供命名约定和类后缀规范，database-design.md 提供公共字段顺序规范，dev-workflow.md 提供分层架构中 Entity 的职责定义 — 这些是**深度参考知识**

**结果**：AI 产出的代码既满足硬约束（Rules），又符合项目风格（Skills），且不相关的 Skill（如 agent.md）不会占用上下文空间。

---

## 六、Rules 待填充建议

当前 `rules/project-constraints.md` 已填充 12 条精简硬约束规则（控制在 2000 字符内）：

```markdown
---
项目硬约束规则，AI 助手在所有交互中必须遵守。
---

## Java 硬约束
- 包名必须以 com.dwl 开头
- 实体类必须继承 BaseEntity，添加 @EqualsAndHashCode(callSuper=true)
- 所有类/方法/字段必须有中英文双语 Javadoc 注释
- Controller/DTO/VO 必须有 @Schema/@Operation/@Tag 注解
- ID 策略使用 IdType.ASSIGN_ID（雪花算法），禁止 AUTO_INCREMENT

## 数据库硬约束
- 所有表必须包含 is_deleted (TINYINT NOT NULL DEFAULT 0) 并建立 idx_is_deleted 索引
- 所有表必须包含 created_at 和 updated_at 审计字段
- 枚举字段必须使用 sys_dict_type/sys_dict_data 字典数据
- 字段顺序：id → 业务字段 → is_deleted → created_at → updated_at

## 安全硬约束
- CORS 配置必须使用 ${cors.allowed-origins:*}，禁止硬编码
- Axios 必须启用 withCredentials: true
- JWT 密钥必须通过环境变量注入，禁止硬编码默认值到生产环境
- gRPC 客户端必须支持 TLS 开关

## 前端硬约束
- Ant Design Vue 必须按需导入（importStyle: 'less'）
- 单元测试使用 Vitest
```

---

## 七、维护指南

### 7.1 何时新增 Rule

- **条件**：某条规范在**任何场景**下都必须遵守，且违反会导致系统性问题
- **示例**：新增"所有 API 必须返回 R\<T\> 统一响应体"
- **注意**：Rule 常驻上下文，**每新增一条 Rule 都会增加 AI 的固定开销**，应严格控制数量（建议 ≤ 15 条）

### 7.2 何时新增 Skill 文件

- **条件**：某领域知识体量较大（> 500 字），且仅在**特定任务**时需要参考
- **示例**：新增 `testing.md` 专门描述测试策略与覆盖率要求
- **操作**：创建文件 + 更新 SKILL.md 索引 + 更新快速导航

### 7.3 何时将 Skill 内容提取为 Rule

- **条件**：Skill 中的某条规范被反复违反，且违反后果严重
- **示例**：code-standards.md 中的"包名 com.dwl"被多次遗漏 → 提取为 Rule
- **原则**：**Rule 是 Skill 的底线子集**，不是替代关系

### 7.4 版本演进建议

| 阶段 | Rules 变化 | Skills 变化 |
|------|-----------|------------|
| Phase 0-1 | 基本不变（约束已稳定） | 频繁更新（架构/代码规范/Agent 逐步细化） |
| Phase 2 | 可能新增安全/权限 Rule | 新增手动录入相关 Skill 内容 |
| Phase 3 | 基本冻结 | 新增 AI 优化/Semantic Match 相关内容 |
