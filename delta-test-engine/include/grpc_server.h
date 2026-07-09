/**
 * @file grpc_server.h
 * gRPC 服务头文件
 * gRPC Service Header
 * <p>
 * 声明 gRPC 服务器启动函数，供 main.cpp 调用。
 * Declares gRPC server startup function for main.cpp to call.
 * </p>
 *
 * @author ByDWL
 */

#ifndef DELTA_ENGINE_GRPC_SERVER_H
#define DELTA_ENGINE_GRPC_SERVER_H

#ifdef __cplusplus

#include <cstdint>

/**
 * 启动 gRPC 服务器 / Start gRPC server
 * <p>
 * 从环境变量读取配置：
 * Reads configuration from environment variables:
 * - ENGINE_PORT: 监听端口，默认 9090 / Listen port, default 9090
 * - ENGINE_USE_TLS: 是否启用 TLS，默认 false / Enable TLS, default false
 * - ENGINE_CERT_PATH: 服务端证书路径 / Server cert path
 * - ENGINE_KEY_PATH: 服务端私钥路径 / Server key path
 * </p>
 *
 * @param port 监听端口 / Listen port
 * @param use_tls 是否启用 TLS / Whether to enable TLS
 * @return 0 正常退出，非0 异常 / 0 normal exit, non-zero error
 */
int run_grpc_server(int port, bool use_tls);

#endif /* __cplusplus */

#endif /* DELTA_ENGINE_GRPC_SERVER_H */
