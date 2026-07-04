DeltaTest - 双模式驱动的智能 Web 自动化测试平台
一款融合 Java + Vue + Python + C 多技术栈的企业级自动化测试平台，支持「变更驱动自动闭环」与「手动录入触发」双模式，覆盖代码变更分析、用例管理、任务调度、执行管控、质量报告全链路。
既是可落地的测试提效工具，也是多语言协同架构的工程化学习范本。

---
📌 目录
- [项目定位](#项目定位)
- [核心特性](#核心特性)
- [技术栈全景](#技术栈全景)
- [整体架构](#整体架构)
- [快速开始](#快速开始)
- [模块说明](#模块说明)
- [开发路线图](#开发路线图)
- [文档体系](#文档体系)

---
📌 项目定位
DeltaTest 是一款面向研发团队的 Web 自动化测试平台，核心定位为双模式驱动的测试全链路闭环系统：
- 变更驱动模式：通过 Git Webhook 自动感知代码变更，完成差异分析、风险评估、用例匹配、任务执行全流程自动化，实现测试左移
- 手动触发模式：支持可视化用例管理、手动创建执行任务、生成质量报告，适配常规回归测试场景
  项目采用分层解耦的多语言架构，兼顾生产落地实用性与架构学习价值，既可以直接用于中小团队测试提效，也可以作为企业级全栈项目的学习参考。

---
✨ 核心特性
1. 双模式驱动，统一底座
   自动闭环与手动触发复用同一套用例、任务、执行、报告体系，一套底座覆盖两种业务场景，避免重复建设。
2. 全链路测试闭环
   覆盖「代码变更分析 → 风险评估 → 用例匹配 → 任务调度 → 执行管控 → 质量报告 → 根因分析」完整测试生命周期。
3. 多语言协同架构
- Java 承载业务调度与数据一致性保障
- C 语言负责高性能代码差异计算
- Python 承载 AI 智能分析能力
- Vue 提供现代化前端交互体验
  不同语言各司其职，通过标准协议通信，无技术栈绑定。
4. AI 智能测试赋能
   内置大模型接入能力，支持：
- 代码变更风险自动定级
- 测试用例智能生成
- 执行失败根因分析
- 测试报告智能总结
5. 企业级工程规范
   严格的单向分层依赖、统一响应体与错误码体系、全链路中英双语注释、标准化命名约定，符合团队协作开发标准。
6. 实时执行可视化
   基于 WebSocket 实现任务执行进度、日志、结果的实时推送，前端秒级展示执行状态。

---
🛠️ 技术栈全景
Java 后端
分类
技术选型
版本
核心框架
Spring Boot
3.4.1
安全框架
Spring Security + JWT
6.x
ORM 框架
MyBatis-Plus
3.5.9
跨语言通信
gRPC + Protobuf
1.69.0
消息队列
Spring AMQP (RabbitMQ)
-
缓存
Spring Data Redis (Lettuce)
-
对象映射
MapStruct
1.6.3
工具增强
Lombok + Hutool
1.18.36 / 5.8.34
API 文档
SpringDoc OpenAPI
2.8.3
Vue 前端
分类
技术选型
版本
核心框架
Vue 3 (Composition API)
3.5.x
构建工具
Vite
6.x
类型系统
TypeScript
5.8.x
UI 组件库
Ant Design Vue
4.x
状态管理
Pinia
2.x
路由管理
Vue Router
4.x
HTTP 客户端
Axios
1.x
代码规范
ESLint + Prettier
9.x / 3.x
Python AI 服务
分类
技术选型
版本
Web 框架
FastAPI
0.115+
ASGI 服务器
Uvicorn
0.34+
数据校验
Pydantic
2.10+
配置管理
pydantic-settings
2.7+
包管理
uv
0.11+
代码规范
Ruff
0.9+
类型检查
mypy (strict 模式)
1.14+
测试框架
pytest
8.3+
基础设施
- 数据库：MySQL 8.x
- 缓存：Redis 7.x
- 消息队列：RabbitMQ 3.x
- 对象存储：MinIO
- 向量数据库：Milvus（规划中）

---
🏗️ 整体架构
模块依赖关系
严格遵循单向向下依赖原则，无循环依赖，各模块可独立编译与扩展：
delta-test-admin (启动入口)
→ delta-test-web (Web表现层)
→ delta-test-service (业务逻辑层)
→ delta-test-dao (数据访问层)
→ delta-test-model (数据模型层)
→ delta-test-common (通用基础设施)

    delta-test-grpc-client (跨语言通信)
    delta-test-mq (消息队列模块)
核心数据流
变更驱动自动链路
Git Webhook → Java Webhook接口 → gRPC调用C引擎计算差异
→ Python AI风险评估 → 自动匹配用例 → 生成执行任务
→ RabbitMQ分发任务 → Playwright执行节点消费
→ 结果/日志回传MQ → WebSocket实时推送前端
手动触发链路
Vue前端 → HTTP REST → Java后端CRUD管理
→ 手动创建任务 → RabbitMQ分发 → 执行节点执行
→ 结果回传 → 生成质量报告 → 前端可视化展示

---
🚀 快速开始
环境要求
- JDK 17+
- Node.js 18+ / pnpm 10.x
- Python 3.12+ / uv
- Docker & Docker Compose（推荐，一键启动依赖）
1. 一键启动基础设施
   使用项目根目录 docker-compose.yml 一键启动 MySQL、Redis、RabbitMQ 等依赖：
   docker-compose up -d
2. 启动 Java 后端
# 编译全项目
mvn clean compile

# 启动应用
cd delta-test-admin
mvn spring-boot:run
- 后端地址：http://localhost:8080
- API 文档：http://localhost:8080/swagger-ui.html
3. 启动 Vue 前端
   cd delta-test-web
   pnpm install
   pnpm dev
- 前端地址：http://localhost:5173
- 默认账号：admin / admin123
4. 启动 Python AI 服务
   cd delta-test-ai
   uv sync --extra dev
   uv run uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
- AI 服务地址：http://localhost:8000
- 接口文档：http://localhost:8000/docs

---
📂 模块说明
1. Java 后端（delta-test-server）
   8 个 Maven 子模块的分层单体架构：
- delta-test-common：通用基类、异常、结果封装、工具类、常量枚举
- delta-test-model：数据库实体、DTO、VO 纯 POJO 定义
- delta-test-dao：MyBatis-Plus Mapper 数据访问层
- delta-test-service：业务接口与实现、事务控制
- delta-test-web：REST 控制器、安全配置、WebSocket、接口文档
- delta-test-grpc-client：C 语言计算引擎 gRPC 客户端
- delta-test-mq：RabbitMQ 生产者、消费者、消息体定义
- delta-test-admin：Spring Boot 启动入口、配置文件
2. Vue 前端（delta-test-web）
   标准前后端分离工程化架构：
- views/：页面组件，覆盖工作台、变更分析、用例管理、任务中心、质量报告
- api/：与后端接口一一对应的 API 调用层
- stores/：Pinia 状态管理，用户认证与 Token 持久化
- router/：路由配置与全局权限守卫
- utils/：Axios 封装、通用工具函数
3. Python AI 服务（delta-test-ai）
   独立解耦的 AI 能力服务：
- app/api/：FastAPI 路由，风险评估、用例生成、根因分析等接口
- app/services/：AI 业务逻辑实现
- app/prompts/：大模型 Prompt 模板
- app/core/：LLM 封装、配置管理
- app/models/：请求 / 响应 DTO、枚举定义

---
🗺️ 开发路线图
Phase 1 核心闭环（进行中）
- 手动测试全链路闭环：用例管理 → 任务创建 → Playwright 执行 → 报告展示
- LLM 真实接入，完成风险评估、用例生成基础能力
- 前端核心页面功能落地，完善交互体验
- 部署文档与一键启动脚本完善
  Phase 2 智能增强
- 打通 Git Webhook 变更驱动自动闭环
- 上线测试失败根因分析、对话式测试助手
- 完善安全体系：WebSocket 认证、Token 黑名单、权限细化
- 新增执行节点分布式调度能力
  Phase 3 生态扩展
- 接入向量检索，实现用例语义匹配与知识库沉淀
- 插件化扩展，支持更多测试引擎与大模型厂商
- 多租户与企业级权限体系
- 国际化完整方案与社区生态建设

---
📚 文档体系
完整项目文档位于仓库根目录 docs/ 目录：
- [架构设计](./.trae/skills/dev-knowledge-base/architecture.md)：模块划分、依赖关系、数据流设计
- [代码规范](./.trae/skills/dev-knowledge-base/code-standards.md)：多语言命名约定、格式化规则、注释规范
- [技术栈说明](./.trae/skills/dev-knowledge-base/tech-stack.md)：框架版本、中间件配置、核心集成方案
- [开发流程](./.trae/skills/dev-knowledge-base/dev-workflow.md)：构建命令、分层最佳实践、配置管理
- [性能优化](./.trae/skills/dev-knowledge-base/performance.md)：各层级性能优化策略与实现
- [安全方案](./.trae/skills/dev-knowledge-base/security.md)：认证体系、授权规则、数据安全
- [待改进项](./.trae/skills/dev-knowledge-base/improvements.md)：已知规划与演进方向