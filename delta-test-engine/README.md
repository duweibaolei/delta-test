# DeltaTest C Engine / DeltaTest C 计算引擎

> 双模式驱动的Web自动化测试平台 — C 高性能计算引擎
> Dual-mode Web Automation Testing Platform — C High-Performance Calculation Engine

## 概述 / Overview

DeltaTest C 引擎负责平台的核心计算能力：

- **Diff 计算**：接收 commit 范围，计算代码差异
- **依赖链分析**：基于 AST 构建依赖图，追踪影响范围
- **影响范围组装**：按前端页面/后端接口/数据库表分类输出

通过 gRPC 对外暴露服务，由 Java 后端调用。

## 架构分层 / Architecture Layers

```
┌──────────────────────────────────────────────────────┐
│  gRPC Server Layer (C++17 / grpc++)                  │
│  接收 RPC 请求、序列化/反序列化、TLS 配置             │
├──────────────────────────────────────────────────────┤
│  C Bridge Layer (extern "C" 接口)                    │
│  C++ → C 的类型转换和调用桥接                         │
├──────────────────────────────────────────────────────┤
│  Business Logic Layer (C17 / 纯 C)                   │
│  Diff 计算、依赖分析、影响范围组装                     │
└──────────────────────────────────────────────────────┘
```

## 技术栈 / Tech Stack

| 类别 | 技术 | 版本 |
|------|------|------|
| 语言标准 | C17 / C++17 | - |
| 构建工具 | CMake | 3.28+ |
| 依赖管理 | Conan | 2.x |
| gRPC | grpc++ | 1.69.0 |
| Protobuf | libprotobuf | 4.29.3 (传递依赖) |
| 日志 | spdlog | 1.15.1 |

## 构建指南 / Build Guide

### 前置条件 / Prerequisites

- CMake 3.28+
- Conan 2.x (`pip install conan==2.*`)
- GCC 12+ / MSVC 19.3+

### 构建步骤 / Build Steps

```bash
# 1. 检测编译器配置 / Detect compiler profile
conan profile detect

# 2. 安装依赖 / Install dependencies
conan install . --build=missing -s build_type=Release

# 3. CMake 配置 / CMake configure
cmake --preset conan-release

# 4. 编译 / Build
cmake --build --preset conan-release

# 5. 运行测试 / Run tests
ctest --preset conan-release --output-on-failure

# 6. 启动引擎 / Start engine
./build/Release/delta-test-engine
```

### Docker 构建 / Docker Build

```bash
docker build -t delta-test-engine:1.0.0-skeleton .
docker run -d -p 9090:9090 delta-test-engine:1.0.0-skeleton
```

## 环境变量 / Environment Variables

| 变量 | 默认值 | 说明 |
|------|--------|------|
| `ENGINE_PORT` | `9090` | gRPC 监听端口 |
| `ENGINE_USE_TLS` | `false` | 是否启用 TLS |
| `ENGINE_CERT_PATH` | - | 服务端证书路径（TLS 启用时） |
| `ENGINE_KEY_PATH` | - | 服务端私钥路径（TLS 启用时） |

## gRPC 接口 / gRPC API

```protobuf
service CodeAnalysisService {
  rpc ComputeDiff(DiffRequest) returns (DiffResponse);
  rpc AnalyzeImpact(ImpactRequest) returns (ImpactResponse);
  rpc HealthCheck(HealthCheckRequest) returns (HealthCheckResponse);
}
```

## 健康检查验证 / Health Check Verification

```bash
grpcurl -plaintext localhost:9090 delta.engine.v1.CodeAnalysisService/HealthCheck
# 预期响应: {"healthy": true, "version": "1.0.0-skeleton"}
```

## 项目结构 / Project Structure

```
delta-test-engine/
├── CMakeLists.txt          # 根 CMake 配置
├── conanfile.py            # Conan 2 依赖声明
├── Dockerfile              # 多阶段构建镜像
├── proto/
│   └── code_analysis.proto # gRPC 服务定义
├── include/
│   ├── bridge.h            # C/C++ 桥接层
│   ├── diff_engine.h       # Diff 计算模块
│   ├── impact_analyzer.h   # 影响分析模块
│   ├── dependency_graph.h  # 依赖图模块
│   └── grpc_server.h       # gRPC 服务头文件
├── src/
│   ├── main.cpp            # 程序入口
│   ├── grpc_server.cpp     # gRPC Service 实现
│   ├── bridge.c            # 桥接层实现
│   ├── diff_engine.c       # Diff 计算实现
│   ├── impact_analyzer.c   # 影响分析实现
│   └── dependency_graph.c  # 依赖图实现
└── test/
    ├── CMakeLists.txt      # 测试 CMake 配置
    ├── test_diff_engine.c  # Diff 引擎单元测试
    ├── test_impact_analyzer.c # 影响分析单元测试
    └── test_grpc_server.cpp   # gRPC 编译验证测试
```
