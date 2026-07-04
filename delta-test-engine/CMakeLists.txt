# ============================================================
# DeltaTest C Engine - CMake 配置
# DeltaTest C Engine - CMake Configuration
# ============================================================

cmake_minimum_required(VERSION 3.28)
project(delta-test-engine
        VERSION 1.0.0
        DESCRIPTION "双模式驱动的Web自动化测试平台 - C计算引擎 / DeltaTest C Calculation Engine"
        LANGUAGES C CXX
)

# ==================== C/C++ 标准 / C/C++ Standards ====================
set(CMAKE_C_STANDARD 17)
set(CMAKE_C_STANDARD_REQUIRED ON)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# ==================== 编译选项 / Compiler Options ====================
# 警告级别 / Warning level
if (MSVC)
    add_compile_options(/W4)
else ()
    add_compile_options(-Wall -Wextra -Wpedantic)
endif ()

# ==================== gRPC 可选构建 / Optional gRPC Build ====================
# 默认启用 gRPC Server 构建，需要先执行 conan install 安装依赖
# Default: enable gRPC Server build, requires conan install first
# 如果依赖不可用，仅构建 C 业务逻辑库和测试
# If dependencies unavailable, only build C business logic library and tests
option(ENABLE_GRPC "Build gRPC Server (requires conan install) / 构建 gRPC 服务器（需要 conan install）" ON)

if (ENABLE_GRPC)
    # ==================== Conan 集成 / Conan Integration ====================
    # 通过 conan install 预先生成 CMakeToolchain + CMakeDeps
    # 构建时通过 --toolchain-file 或 preset 加载

    # 查找 Protobuf 和 gRPC / Find Protobuf and gRPC
    find_package(Protobuf CONFIG QUIET)
    find_package(gRPC CONFIG QUIET)

    if (Protobuf_FOUND AND gRPC_FOUND)
        message(STATUS "gRPC/Protobuf found - building full server / 找到 gRPC/Protobuf - 构建完整服务器")
        set(GRPC_AVAILABLE TRUE)
    else ()
        message(WARNING
                "gRPC/Protobuf not found - building C business logic only / 未找到 gRPC/Protobuf - 仅构建 C 业务逻辑\n"
                "To enable gRPC Server, run:\n"
                "  conan install . --build=missing -s build_type=Release\n"
                "  cmake --preset conan-release\n"
                "Or set -DENABLE_GRPC=OFF to suppress this warning"
        )
        set(GRPC_AVAILABLE FALSE)
    endif ()
else ()
    set(GRPC_AVAILABLE FALSE)
    message(STATUS "gRPC Server build disabled / gRPC 服务器构建已禁用")
endif ()

# ==================== C 业务逻辑库 / C Business Logic Library ====================
# 始终构建，不依赖 gRPC / Always built, no gRPC dependency
add_library(delta_engine_core STATIC
        src/diff_engine.c
        src/impact_analyzer.c
        src/dependency_graph.c
        src/bridge.c
)
target_include_directories(delta_engine_core PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR}/include
)
# C 库不直接链接 gRPC/protobuf，仅使用 C 标准库
# 后续 Phase 1 添加 libgit2, tree-sitter 等依赖时在此扩展
set_target_properties(delta_engine_core PROPERTIES
        C_STANDARD 17
        C_STANDARD_REQUIRED ON
        POSITION_INDEPENDENT_CODE ON
)

# ==================== gRPC Server 可执行文件 / gRPC Server Executable ====================
if (GRPC_AVAILABLE)
    # ==================== Proto 代码生成 / Proto Code Generation ====================
    set(PROTO_SRC_DIR "${CMAKE_CURRENT_SOURCE_DIR}/proto")
    set(PROTO_GENERATED_DIR "${CMAKE_CURRENT_BINARY_DIR}/generated")
    file(MAKE_DIRECTORY ${PROTO_GENERATED_DIR})

    # 查找 protoc 和 grpc_cpp_plugin / Find protoc and grpc_cpp_plugin
    find_program(_PROTOBUF_PROTOC protoc)
    find_program(_GRPC_CPP_PLUGIN grpc_cpp_plugin)

    # Proto 文件列表 / Proto file list
    set(PROTO_FILES "${PROTO_SRC_DIR}/code_analysis.proto")

    # Protobuf 消息代码生成 / Generate Protobuf message sources
    add_custom_command(
            OUTPUT "${PROTO_GENERATED_DIR}/code_analysis.pb.cc"
            "${PROTO_GENERATED_DIR}/code_analysis.pb.h"
            COMMAND ${_PROTOBUF_PROTOC}
            --proto_path=${PROTO_SRC_DIR}
            --cpp_out=${PROTO_GENERATED_DIR}
            ${PROTO_FILES}
            DEPENDS ${PROTO_FILES}
            COMMENT "Generating Protobuf C++ sources / 生成 Protobuf C++ 源码"
    )

    # gRPC Service 代码生成 / Generate gRPC service sources
    add_custom_command(
            OUTPUT "${PROTO_GENERATED_DIR}/code_analysis.grpc.pb.cc"
            "${PROTO_GENERATED_DIR}/code_analysis.grpc.pb.h"
            COMMAND ${_PROTOBUF_PROTOC}
            --proto_path=${PROTO_SRC_DIR}
            --grpc_out=${PROTO_GENERATED_DIR}
            --plugin=protoc-gen-grpc=${_GRPC_CPP_PLUGIN}
            ${PROTO_FILES}
            DEPENDS ${PROTO_FILES}
            "${PROTO_GENERATED_DIR}/code_analysis.pb.cc"
            COMMENT "Generating gRPC C++ sources / 生成 gRPC C++ 源码"
    )

    # Proto 生成代码库 / Proto generated code library
    add_library(delta_engine_proto STATIC
            "${PROTO_GENERATED_DIR}/code_analysis.pb.cc"
            "${PROTO_GENERATED_DIR}/code_analysis.grpc.pb.cc"
    )
    target_include_directories(delta_engine_proto PUBLIC ${PROTO_GENERATED_DIR})
    target_link_libraries(delta_engine_proto PUBLIC
            protobuf::libprotobuf
            gRPC::grpc++
    )

    # gRPC Server 可执行文件 / gRPC Server executable
    add_executable(delta-test-engine
            src/main.cpp
            src/grpc_server.cpp
    )
    target_include_directories(delta-test-engine PRIVATE
            ${CMAKE_CURRENT_SOURCE_DIR}/include
            ${PROTO_GENERATED_DIR}
    )
    target_link_libraries(delta-test-engine PRIVATE
            delta_engine_proto
            delta_engine_core
            gRPC::grpc++
            protobuf::libprotobuf
    )
endif ()

# ==================== 测试 / Tests ====================
option(BUILD_TESTS "Build unit tests / 构建单元测试" ON)

if (BUILD_TESTS)
    enable_testing()
    add_subdirectory(test)
endif ()
