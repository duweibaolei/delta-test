# DeltaTest - Dual-Mode Web Automation Testing Platform

[English](#english-version) | 中文

---

## Project Overview

DeltaTest is a dual-mode web automation testing platform that supports traditional test case management while integrating AI capabilities to enhance testing efficiency. The platform adopts a microservices architecture, with a backend built on Spring Boot + MyBatis Plus, a frontend using Vue 3 + TypeScript, and an AI service built with Python FastAPI.

## Core Features

- **Dual-Mode Testing**: Supports traditional manual test case management and AI-assisted testing
- **Smart Case Generation**: Automatically generate test steps and assertions based on page descriptions
- **Change Impact Analysis**: Analyze the scope of code changes and assess risk levels
- **Root Cause Analysis**: Intelligent analysis of potential causes for test failures
- **Task Scheduling**: Supports scheduled and trigger-based test execution
- **Real-time WebSocket Push**: Real-time推送 of test progress and logs
- **Comprehensive Permission System**: User and role management based on RBAC

## Technical Architecture

### Backend Stack

| Component | Technology |
|----------|------------|
| Framework | Spring Boot 3.x |
| Database | MySQL + MyBatis Plus |
| Message Queue | RabbitMQ |
| Authentication | JWT + Spring Security |
| API Documentation | Swagger (OpenAPI 3.0) |
| Remote Invocation | gRPC |

### Frontend Stack

| Component | Technology |
|----------|------------|
| Framework | Vue 3 + Composition API |
| Language | TypeScript |
| Build Tool | Vite |
| State Management | Pinia |
| HTTP Client | Axios |
| UI Components | Ant Design Vue |

### AI Service Stack

| Component | Technology |
|----------|------------|
| Framework | FastAPI |
| Language | Python 3.12 |
| Package Manager | UV |
| Containerization | Docker |

## Project Structure

```
delta-test/
├── delta-test-ai/                 # AI Service (Python FastAPI)
│   ├── app/
│   │   ├── api/                   # API routes
│   │   ├── core/                  # Core configuration
│   │   ├── models/                # Data models
│   │   ├── prompts/               # AI prompts
│   │   ├── services/              # Business services
│   │   └── utils/                 # Utility classes
│   ├── tests/                     # Test cases
│   ├── Dockerfile
│   └── docker-compose.yml
│
├── delta-test-server/             # Backend Service (Java Spring Boot)
│   ├── delta-test-admin/          # Main application entry
│   ├── delta-test-common/         # Common components
│   ├── delta-test-dao/            # Data access layer
│   ├── delta-test-grpc-client/    # gRPC client
│   ├── delta-test-model/          # Data models
│   ├── delta-test-mq/             # Message queue
│   ├── delta-test-service/        # Business service layer
│   └── delta-test-web/            # Web controller layer
│
├── delta-test-web/                # Frontend Application (Vue 3)
│   ├── src/
│   │   ├── api/                   # API interfaces
│   │   ├── assets/                # Static assets
│   │   ├── components/            # Common components
│   │   ├── layouts/               # Layout components
│   │   ├── router/                # Routing configuration
│   │   ├── stores/                # State management
│   │   ├── utils/                 # Utility functions
│   │   └── views/                 # Page views
│   └── package.json
│
└── document/                      # Documentation
    ├── archetype/                 # UI prototypes
    └── sql/                       # Database scripts
```

## Quick Start

### Prerequisites

- JDK 17+
- Maven 3.8+
- Node.js 18+
- pnpm 8+
- MySQL 8.0+
- RabbitMQ 3.12+

### Backend Service Startup

1. Create the database and execute the SQL script:

```bash
mysql -u root -p < document/sql/delta_test_ddl.sql
```

2. Modify the configuration file `delta-test-server/delta-test-admin/src/main/resources/application-dev.yml` to configure database and Redis connection details.

3. Compile and start the backend service:

```bash
cd delta-test-server
mvn clean install -DskipTests
cd delta-test-admin
java -jar target/delta-test-admin.jar
```

Access the service at `http://localhost:8080`

### AI Service Startup

```bash
cd delta-test-ai

# Install dependencies
uv sync

# Start the service
uv run uvicorn app.main:app --reload
```

Access the AI service at `http://localhost:8000`; API documentation is available at `http://localhost:8000/docs`

### Frontend Startup

```bash
cd delta-test-web

# Install dependencies
pnpm install

# Start development server
pnpm dev
```

Access the frontend at `http://localhost:5173`

### Docker Compose Startup (Recommended)

```bash
# Start backend and AI services
cd delta-test-ai
docker-compose up -d

# Start frontend
cd delta-test-web
docker-compose up -d
```

## API Documentation

The platform provides complete RESTful API documentation accessible via Swagger UI:

- Backend API: `http://localhost:8080/swagger-ui.html`
- AI Service API: `http://localhost:8000/docs`

### Main API Modules

| Module | Path | Description |
|--------|------|-------------|
| Authentication | `/api/auth/*` | Login, token refresh, logout |
| User Management | `/api/system/users` | User CRUD operations |
| Role Management | `/api/system/roles` | Role and permission management |
| Environment Management | `/api/system/environments` | Test environment configuration |
| Repository Management | `/api/system/repositories` | Git repository configuration |
| Dictionary Management | `/api/system/dict-*` | System dictionaries |
| Case Management | `/api/case/*` | Test case management |
| Task Management | `/api/task/*` | Test task scheduling |
| Report Management | `/api/report/*` | Test reports |
| AI Services | `/api/*-generation`, `/api/risk-assessment`, `/api/root-cause`, `/api/summary` | AI capability endpoints |

## Core Data Models

### Test Case (TestCase)

```java
- id: Long              // Primary key
- caseName: String      // Case name
- caseDesc: String      // Case description
- caseStatus: CaseStatus    // Case status
- sourceType: SourceType    // Source type
- priority: Integer     // Priority
- tags: List<CaseTag>   // Tags
- steps: List<CaseStep> // Test steps
- createBy: Long        // Creator
- createTime: LocalDateTime  // Creation time
- updateTime: LocalDateTime  // Update time
- deleted: Integer      // Logical delete flag
```

### Test Task (TestTask)

```java
- id: Long              // Primary key
- taskName: String      // Task name
- taskDesc: String      // Task description
- taskStatus: TaskStatus   // Task status
- triggerSource: TriggerSource  // Trigger source
- envId: Long           // Environment ID
- execConfig: ExecConfig   // Execution configuration
- caseIds: List<Long>   // Associated case IDs
- createBy: Long        // Creator
- createTime: LocalDateTime  // Creation time
```

### Change Analysis (ChangeAnalysis)

```java
- id: Long              // Primary key
- repoId: Long          // Repository ID
- branch: String        // Branch
- commitHash: String    // Commit hash
- changeFiles: String   // List of changed files
- riskLevel: RiskLevel  // Risk level
- aiSummary: String     // AI analysis summary
- affectedScopes: List<AffectedScope>  // Affected scopes
- createTime: LocalDateTime  // Creation time
```

## AI Capabilities

### 1. Case Generation

Automatically generate test steps and assertions based on page URL and description.

```bash
POST /api/case-generation
{
  "pageUrl": "https://example.com/login",
  "pageDescription": "User login page with username and password input fields and a login button",
  "requirement": "Verify that the login functionality works correctly"
}
```

### 2. Risk Assessment

Perform risk assessment based on code change analysis results.

```bash
POST /api/risk-assessment
{
  "changedFiles": ["src/main/java/com/example/UserService.java"],
  "changeDescription": "Modified user password encryption logic"
}
```

### 3. Root Cause Analysis

Analyze potential causes of test failures.

```bash
POST /api/root-cause
{
  "caseName": "User Login Test",
  "errorMessage": "ElementClickInterceptedException",
  "executionLog": "..."
}
```

### 4. Change Summary

Generate change summaries and testing recommendations.

```bash
POST /api/summary
{
  "changedFiles": ["src/main/java/..."],
  "diffContent": "..."
}
```

## Message Queue

The platform uses RabbitMQ for asynchronous task processing. Main queues:

| Queue Name | Description |
|------------|-------------|
| task.execute.queue | Task execution queue |
| task.result.queue | Task result queue |
| task.log.queue | Task log queue |

## License

This project is open-sourced under the MIT License. See the [LICENSE](./LICENSE) file for details.

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