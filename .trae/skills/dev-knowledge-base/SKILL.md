---
name: dev-knowledge-base
description: DeltaTest 开发知识库，涵盖架构设计、代码规范、技术栈、开发流程、性能优化、安全方案、智能体架构等
---

# DeltaTest 开发知识库

> 本文件为开发知识库的**主入口索引**，各专题详细内容已拆分至独立文件。

## 文件索引

| 序号 | 文件 | 主题 | 适用场景 |
|------|------|------|----------|
| 一 | [architecture.md](./architecture.md) | 项目整体架构设计 | 理解项目结构、模块划分、数据流 |
| 二 | [database-design.md](./database-design.md) | 数据库表设计规范 | 公共字段标准、建表规范、索引规范 |
| 三 | [tech-stack.md](./tech-stack.md) | 核心技术栈应用 | 框架版本、中间件配置、技术集成 |
| 四 | [code-standards.md](./code-standards.md) | 代码规范与风格指南 | 编码命名、格式化、注释、API注解 |
| 五 | [dev-workflow.md](./dev-workflow.md) | 开发流程与最佳实践 | 构建、响应体、异常处理、配置管理 |
| 六 | [security.md](./security.md) | 安全实现方案 | 认证、授权、密钥管理、数据安全 |
| 七 | [performance.md](./performance.md) | 性能优化措施 | 各层性能策略与实现方案 |
| 八 | [agent.md](./agent.md) | 智能体架构设计 | Agent 架构、能力矩阵、集成方案、API 契约 |
| 九 | [key-capabilities.md](./key-capabilities.md) | 关键技术能力提炼 | 架构/工程/安全/数据建模能力总结 |
| 十 | [improvements.md](./improvements.md) | 已知待改进项 | 当前技术债务与后续计划 |

## 快速导航

**理解项目全局时** → [architecture.md](./architecture.md)（架构/数据流）+ [key-capabilities.md](./key-capabilities.md)（能力总结）

**设计数据库表时** → [database-design.md](./database-design.md)（公共字段标准）+ [code-standards.md](./code-standards.md)（命名规范）

**配置中间件时** → [tech-stack.md](./tech-stack.md)（版本/配置）+ [performance.md](./performance.md)（优化措施）

**创建新类时** → [code-standards.md](./code-standards.md)（命名/后缀/注释）+ [dev-workflow.md](./dev-workflow.md)（分层架构/响应体）

**构建项目时** → [dev-workflow.md](./dev-workflow.md)（构建流程/配置管理）+ [improvements.md](./improvements.md)（已知问题/待改进项）

**实现安全功能时** → [security.md](./security.md)（认证/授权）+ [improvements.md](./improvements.md)（安全待办）

**开发 C 引擎时** → [architecture.md](./architecture.md)（C 引擎分层/桥接层设计）+ [code-standards.md](./code-standards.md)（C 编码规范/Windows 构建注意）+ [tech-stack.md](./tech-stack.md)（CMake Presets/gRPC 构建模式）

**构建 C 引擎时** → [dev-workflow.md](./dev-workflow.md)（CMake/Conan/Docker 构建流程）+ [improvements.md](./improvements.md)（C 引擎待改进项）

**设计 Agent 功能时** → [agent.md](./agent.md)（Agent 架构/能力矩阵）+ [architecture.md](./architecture.md)（系统架构/数据流）