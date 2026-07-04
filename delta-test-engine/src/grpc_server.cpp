/**
 * @file grpc_server.cpp
 * gRPC 服务实现
 * gRPC Service Implementation
 * <p>
 * 实现 CodeAnalysisService 的三个 RPC 方法，
 * 通过桥接层调用 C 业务逻辑。
 * Implements three RPC methods of CodeAnalysisService,
 * calls C business logic through bridge layer.
 * </p>
 *
 * @author DeltaTest
 */

#include "grpc_server.h"
#include "bridge.h"
#include "code_analysis.grpc.pb.h"

#include <grpcpp/grpcpp.h>
#include <grpcpp/security/server_credentials.h>

#include <iostream>
#include <memory>
#include <string>
#include <vector>

namespace delta {
namespace engine {
namespace v1 {

/**
 * CodeAnalysisService 实现类
 * CodeAnalysisService Implementation Class
 */
class CodeAnalysisServiceImpl final
    : public CodeAnalysisService::Service {
public:
    /**
     * 计算代码差异 / Compute code diff
     * <p>
     * 骨架阶段：调用桥接层，返回占位响应。
     * Skeleton phase: calls bridge layer, returns placeholder response.
     * </p>
     *
     * @param context gRPC 上下文 / gRPC context
     * @param request 差异计算请求 / Diff computation request
     * @param response 差异计算响应 / Diff computation response
     * @return gRPC 状态 / gRPC status
     */
    grpc::Status ComputeDiff(
        grpc::ServerContext* /* context */,
        const DiffRequest* request,
        DiffResponse* response) override {

        // 调用 C 桥接层 / Call C bridge layer
        DiffResult result = bridge_compute_diff(
            request->repo_id(),
            request->repo_url().c_str(),
            request->branch().c_str(),
            request->start_commit().c_str(),
            request->end_commit().c_str(),
            request->credential_type().c_str(),
            request->credential_key().c_str()
        );

        // 填充响应 / Fill response
        response->set_code(result.code);
        response->set_message(result.message);
        // Phase 1: 转换 result.file_diffs 到 response FileDiff

        return grpc::Status::OK;
    }

    /**
     * 分析影响范围 / Analyze impact scope
     * <p>
     * 骨架阶段：调用桥接层，返回占位响应。
     * Skeleton phase: calls bridge layer, returns placeholder response.
     * </p>
     *
     * @param context gRPC 上下文 / gRPC context
     * @param request 影响分析请求 / Impact analysis request
     * @param response 影响分析响应 / Impact analysis response
     * @return gRPC 状态 / gRPC status
     */
    grpc::Status AnalyzeImpact(
        grpc::ServerContext* /* context */,
        const ImpactRequest* request,
        ImpactResponse* response) override {

        // 构造 C 风格字符串数组 / Build C-style string array
        std::vector<const char*> changed_files;
        changed_files.reserve(request->changed_files_size());
        for (const auto& f : request->changed_files()) {
            changed_files.push_back(f.c_str());
        }

        // 调用 C 桥接层 / Call C bridge layer
        ImpactResult result = bridge_analyze_impact(
            request->repo_id(),
            request->repo_url().c_str(),
            request->branch().c_str(),
            changed_files.data(),
            static_cast<int>(changed_files.size()),
            request->credential_type().c_str(),
            request->credential_key().c_str()
        );

        // 填充响应 / Fill response
        response->set_code(result.code);
        response->set_message(result.message);
        // Phase 1: 转换 result.impacts 到 response ScopeImpact

        return grpc::Status::OK;
    }

    /**
     * 健康检查 / Health check
     * <p>
     * 返回引擎版本与健康状态。
     * Returns engine version and health status.
     * </p>
     *
     * @param context gRPC 上下文 / gRPC context
     * @param request 健康检查请求 / Health check request
     * @param response 健康检查响应 / Health check response
     * @return gRPC 状态 / gRPC status
     */
    grpc::Status HealthCheck(
        grpc::ServerContext* /* context */,
        const HealthCheckRequest* /* request */,
        HealthCheckResponse* response) override {

        response->set_healthy(true);
        response->set_version("1.0.0-skeleton");

        return grpc::Status::OK;
    }
};

}  // namespace v1
}  // namespace engine
}  // namespace delta

/**
 * 启动 gRPC 服务器 / Start gRPC server
 * <p>
 * 创建 CodeAnalysisServiceImpl 实例，绑定监听地址和凭据，
 * 阻塞等待请求。
 * Creates CodeAnalysisServiceImpl instance, binds listen address and credentials,
 * blocks to wait for requests.
 * </p>
 *
 * @param port 监听端口 / Listen port
 * @param use_tls 是否启用 TLS / Whether to enable TLS
 * @return 0 正常退出，非0 异常 / 0 normal exit, non-zero error
 */
int run_grpc_server(int port, bool use_tls) {
    std::string server_address = "0.0.0.0:" + std::to_string(port);

    delta::engine::v1::CodeAnalysisServiceImpl service;

    grpc::ServerBuilder builder;

    if (use_tls) {
        // TLS 模式（Phase 1 完整实现）/ TLS mode (full implementation in Phase 1)
        std::cout << "[WARN] TLS mode not yet implemented in skeleton phase, "
                  << "falling back to plaintext" << std::endl;
        builder.AddListeningPort(server_address,
            grpc::InsecureServerCredentials());
    } else {
        // 明文模式 / Plaintext mode
        builder.AddListeningPort(server_address,
            grpc::InsecureServerCredentials());
    }

    builder.RegisterService(&service);

    std::unique_ptr<grpc::Server> server(builder.BuildAndStart());
    if (!server) {
        std::cerr << "[ERROR] Failed to start gRPC server on "
                  << server_address << std::endl;
        return 1;
    }

    std::cout << "DeltaTest Engine listening on " << server_address
              << " (TLS: " << (use_tls ? "on" : "off") << ")" << std::endl;

    server->Wait();
    return 0;
}
