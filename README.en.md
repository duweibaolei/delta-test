# DeltaTest - Dual-Mode Driven Intelligent Web Automation Testing Platform

An enterprise-level automation testing platform integrating Java + Vue + Python + C multi-tech stack, supporting both "change-driven automatic closed-loop" and "manual trigger" modes, covering code change analysis, test case management, task scheduling, execution control, and quality reporting.

Both a practical test efficiency tool and an engineering learning reference for multi-language collaborative architecture.

---

## 📌 Table of Contents

- [Project Positioning](#project-positioning)
- [Core Features](#core-features)
- [Technology Stack Overview](#technology-stack-overview)
- [Overall Architecture](#overall-architecture)
- [Quick Start](#quick-start)
- [Module Description](#module-description)
- [Development Roadmap](#development-roadmap)
- [Documentation](#documentation)

---

## 📌 Project Positioning

DeltaTest is a web automation testing platform for R&D teams, positioned as a dual-mode driven full-link closed-loop testing system:

- **Change-Driven Mode**: Automatically感知 code changes through Git Webhook, completing differential analysis, risk assessment, test case matching, and task execution全流程 automation, achieving test left-shifting
- **Manual Trigger Mode**: Supports visual test case management, manual task creation, and quality report generation, suitable for conventional regression testing scenarios

The project adopts a layered decoupled multi-language architecture, balancing production practicality and architectural learning value. It can be directly used for small to medium team test efficiency improvements or serve as an enterprise-level full-stack project learning reference.

---

## ✨ Core Features

### 1. Dual-Mode Drive, Unified Base

Automatic closed-loop and manual triggering reuse the same test case, task, execution, and reporting system. One base covers two business scenarios, avoiding duplicate construction.

### 2. Full-Link Test Closed-Loop

Covers the complete test lifecycle: "code change analysis → risk assessment → test case matching → task scheduling → execution control → quality report → root cause analysis".

### 3. Multi-Language Collaborative Architecture

- Java carries business scheduling and data consistency assurance
- C language handles high-performance code difference calculation
- Python carries AI intelligent analysis capabilities
- Vue provides modern frontend interaction experience

Different languages each play their role, communicating through standard protocols with no technology stack binding.

### 4. AI Intelligent Testing Empowerment

Built-in large model access capabilities, supporting:

- Automatic code change risk level determination
- Intelligent test case generation
- Execution failure root cause analysis
- Intelligent test report summarization

### 5. Enterprise-Level Engineering Standards

Strict unidirectional layered dependencies, unified response body and error code system, full-link bilingual comments, standardized naming conventions, compliant with team collaboration development standards.

### 6. Real-Time Execution Visualization

Based on WebSocket, real-time push of task execution progress, logs, and results, with second-level front-end display of execution status.

---

## 🛠️ Technology Stack Overview

### Java Backend

| Category | Technology | Version |
|----------|------------|---------|
| Core Framework | Spring Boot | 3.4.1 |
| Security Framework | Spring Security + JWT | 6.x |
| ORM Framework | MyBatis-Plus | 3.5.9 |
| Cross-Language Communication | gRPC + Protobuf | 1.69.0 |
| Message Queue | Spring AMQP (RabbitMQ) | - |
| Cache | Spring Data Redis (Lettuce) | - |
| Object Mapping | MapStruct | 1.6.3 |
| Tool Enhancement | Lombok + Hutool | 1.18.36 / 5.8.34 |
| API Documentation | SpringDoc OpenAPI | 2.8.3 |

### Vue Frontend

| Category | Technology | Version |
|----------|------------|---------|
| Core Framework | Vue 3 (Composition API) | 3.5.x |
| Build Tool | Vite | 6.x |
| Type System | TypeScript | 5.8.x |
| UI Component Library | Ant Design Vue | 4.x |
| State Management | Pinia | 2.x |
| Routing Management | Vue Router | 4.x |
| HTTP Client | Axios | 1.x |
| Code Standards | ESLint + Prettier | 9.x / 3.x |

### Python AI Service

| Category | Technology | Version |
|----------|------------|---------|
| Web Framework | FastAPI | 0.115+ |
| ASGI Server | Uvicorn | 0.34+ |
| Data Validation | Pydantic | 2.10+ |
| Configuration Management | pydantic-settings | 2.7+ |
| Package Management | uv | 0.11+ |
| Code Standards | Ruff | 0.9+ |
| Type Checking | mypy (strict mode) | 1.14+ |
| Testing Framework | pytest | 8.3+ |

### Infrastructure

- **Database**: MySQL 8.x
- **Cache**: Redis 7.x
- **Message Queue**: RabbitMQ 3.x
- **Object Storage**: MinIO
- **Vector Database**: Milvus (planned)

---

## 🏗️ Overall Architecture

### Module Dependencies

Strictly follows the unidirectional downward dependency principle, no circular dependencies, each module can be independently compiled and extended:

```
delta-test-admin (startup entry)
  → delta-test-web (Web presentation layer)
  → delta-test-service (business logic layer)
  → delta-test-dao (data access layer)
  → delta-test-model (data model layer)
  → delta-test-common (common infrastructure)

delta-test-grpc-client (cross-language communication)
delta-test-mq (message queue module)
```

### Core Data Flow

**Change-Driven Automatic Link**

```
Git Webhook → Java Webhook Interface → gRPC Call C Engine for Difference Calculation
→ Python AI Risk Assessment → Automatic Test Case Matching → Generate Execution Task
→ RabbitMQ Distribute Task → Playwright Execution Node Consumption
→ Results/Logs Return via MQ → WebSocket Real-Time Push to Frontend
```

**Manual Trigger Link**

```
Vue Frontend → HTTP REST → Java Backend CRUD Management
→ Manual Task Creation → RabbitMQ Distribution → Execution Node Execution
→ Results Return → Generate Quality Report → Frontend Visualization
```

---

## 🚀 Quick Start

### Environment Requirements

- JDK 17+
- Node.js 18+ / pnpm 10.x
- Python 3.12+ / uv
- Docker & Docker Compose (recommended, one-click dependency startup)

### 1. One-Click Infrastructure Startup

Use `docker-compose.yml` in the project root directory to one-click start MySQL, Redis, RabbitMQ and other dependencies:

```bash
docker-compose up -d
```

### 2. Start Java Backend

```bash
# Compile entire project
mvn clean compile

# Start application
cd delta-test-admin
mvn spring-boot:run
```

- Backend address: http://localhost:8080
- API documentation: http://localhost:8080/swagger-ui.html

### 3. Start Vue Frontend

```bash
cd delta-test-web
pnpm install
pnpm dev
```

- Frontend address: http://localhost:5173
- Default account: admin / admin123

### 4. Start Python AI Service

```bash
cd delta-test-ai
uv sync --extra dev
uv run uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
```

- AI service address: http://localhost:8000
- API documentation: http://localhost:8000/docs

---

## 📂 Module Description

### 1. Java Backend (delta-test-server)

8 Maven sub-module layered monolith architecture:

- `delta-test-common`: Common base classes, exceptions, result wrappers, utility classes, constant enums
- `delta-test-model`: Database entities, DTOs, VO pure POJO definitions
- `delta-test-dao`: MyBatis-Plus Mapper data access layer
- `delta-test-service`: Business interfaces and implementations, transaction control
- `delta-test-web`: REST controllers, security configuration, WebSocket, API documentation
- `delta-test-grpc-client`: C language computation engine gRPC client
- `delta-test-mq`: RabbitMQ producers, consumers, message body definitions
- `delta-test-admin`: Spring Boot startup entry, configuration files

### 2. Vue Frontend (delta-test-web)

Standard frontend-backend separation engineering architecture:

- `views/`: Page components, covering workspace, change analysis, test case management, task center, quality reports
- `api/`: API call layer corresponding one-to-one with backend interfaces
- `stores/`: Pinia state management, user authentication and token persistence
- `router/`: Routing configuration and global permission guards
- `utils/`: Axios wrapper, common utility functions

### 3. Python AI Service (delta-test-ai)

Independently decoupled AI capability service:

- `app/api/`: FastAPI routes, risk assessment, test case generation, root cause analysis interfaces
- `app/services/`: AI business logic implementation
- `app/prompts/`: Large model Prompt templates
- `app/core/`: LLM wrapper, configuration management
- `app/models/`: Request/Response DTOs, enum definitions

---

## 🗺️ Development Roadmap

### Phase 1 Core Closed-Loop (in Progress)

- Manual test full-link closed-loop: test case management → task creation → Playwright execution → report display
- LLM real integration, complete risk assessment and test case generation basic capabilities
- Frontend core page functionality implementation, improve interaction experience
- Deployment documentation and one-click startup script completion

### Phase 2 Intelligent Enhancement

- Connect Git Webhook change-driven automatic closed-loop
- Launch test failure root cause analysis and conversational test assistant
- Complete security system: WebSocket authentication, token blacklist, refined permissions
- Add execution node distributed scheduling capabilities

### Phase 3 Ecosystem Expansion

- Integrate vector search for test case semantic matching and knowledge base retention
- Plugin-based expansion supporting more test engines and large model vendors
- Multi-tenant and enterprise-level permission system
- Complete internationalization solution and community ecosystem construction

---

## 📚 Documentation

Complete project documentation is located in the `docs/` directory at the repository root:

- [Architecture Design](./.trae/skills/dev-knowledge-base/architecture.md): Module division, dependencies, data flow design
- [Code Standards](./.trae/skills/dev-knowledge-base/code-standings.md): Multi-language naming conventions, formatting rules, comment standards
- [Technology Stack](./.trae/skills/dev-knowledge-base/tech-stack.md): Framework versions, middleware configuration, core integration solutions
- [Development Workflow](./.trae/skills/dev-knowledge-base/dev-workflow.md): Build commands, layered best practices, configuration management
- [Performance Optimization](./.trae/skills/dev-knowledge-base/performance.md): Layered performance optimization strategies and implementations
- [Security Solutions](./.trae/skills/dev-knowledge-base/security.md): Authentication system, authorization rules, data security
- [Improvements](./.trae/skills/dev-knowledge-base/improvements.md): Known plans and evolution directions
