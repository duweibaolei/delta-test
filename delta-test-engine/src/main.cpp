/**
 * @file main.cpp
 * DeltaTest C 引擎入口
 * DeltaTest C Engine Entry Point
 * <p>
 * 启动 gRPC 服务器，监听 Java 后端的调用请求。
 * Starts gRPC server, listens for Java backend requests.
 * </p>
 *
 * @author ByDWL
 */

#include "grpc_server.h"

#include <cstdlib>
#include <iostream>
#include <string>

/**
 * 从环境变量读取配置 / Read config from environment variable
 *
 * @param key 环境变量键 / Environment variable key
 * @param default_val 默认值 / Default value
 * @return 配置值 / Configuration value
 */
static std::string get_env(const char* key, const std::string& default_val) {
    const char* val = std::getenv(key);
    return val ? std::string(val) : default_val;
}

/**
 * 程序入口 / Program entry point
 * <p>
 * 从环境变量读取 ENGINE_PORT 和 ENGINE_USE_TLS 配置，
 * 然后启动 gRPC 服务器。
 * Reads ENGINE_PORT and ENGINE_USE_TLS from environment variables,
 * then starts the gRPC server.
 * </p>
 *
 * @return 0 正常退出 / 0 normal exit
 */
int main() {
    // 从环境变量读取配置 / Read configuration from environment variables
    int port = std::stoi(get_env("ENGINE_PORT", "9090"));
    bool use_tls = get_env("ENGINE_USE_TLS", "false") == "true";

    std::cout << "========================================" << std::endl;
    std::cout << "DeltaTest Engine v1.0.0-skeleton" << std::endl;
    std::cout << "Port: " << port << std::endl;
    std::cout << "TLS:  " << (use_tls ? "enabled" : "disabled") << std::endl;
    std::cout << "========================================" << std::endl;

    return run_grpc_server(port, use_tls);
}
