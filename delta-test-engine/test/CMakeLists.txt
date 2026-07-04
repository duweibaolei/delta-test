# ============================================================
# DeltaTest C Engine - 测试配置
# DeltaTest C Engine - Test Configuration
# ============================================================

# ==================== C 业务逻辑测试 / C Business Logic Tests ====================

add_executable(test_diff_engine
    test_diff_engine.c
)
target_link_libraries(test_diff_engine PRIVATE delta_engine_core)
target_include_directories(test_diff_engine PRIVATE ${CMAKE_CURRENT_SOURCE_DIR}/../include)
add_test(NAME test_diff_engine COMMAND test_diff_engine)

add_executable(test_impact_analyzer
    test_impact_analyzer.c
)
target_link_libraries(test_impact_analyzer PRIVATE delta_engine_core)
target_include_directories(test_impact_analyzer PRIVATE ${CMAKE_CURRENT_SOURCE_DIR}/../include)
add_test(NAME test_impact_analyzer COMMAND test_impact_analyzer)

# ==================== gRPC 编译验证测试 / gRPC Compilation Verification Test ====================
# 仅在 gRPC 依赖可用时构建 / Only build when gRPC dependencies are available

if(GRPC_AVAILABLE)
    add_executable(test_grpc_server
        test_grpc_server.cpp
    )
    target_link_libraries(test_grpc_server PRIVATE
        delta_engine_proto
        delta_engine_core
        gRPC::grpc++
        protobuf::libprotobuf
    )
    target_include_directories(test_grpc_server PRIVATE
        ${CMAKE_CURRENT_SOURCE_DIR}/../include
        ${CMAKE_CURRENT_BINARY_DIR}/../generated
    )
    add_test(NAME test_grpc_server COMMAND test_grpc_server)
endif()
