# ============================================================
# DeltaTest C Engine Dockerfile
# DeltaTest C Engine Dockerfile
# ============================================================
# 多阶段构建：Conan 安装依赖 → CMake 构建 → 精简运行镜像
# Multi-stage build: Conan install deps → CMake build → Slim runtime image

# ==================== 构建阶段 / Build Stage ====================
FROM conanio/gcc12:2.12.0 AS builder

WORKDIR /app

# 复制依赖声明文件（利用 Docker 缓存层）
# Copy dependency definition files (leverage Docker layer caching)
COPY conanfile.py ./

# 安装 Conan 依赖 / Install Conan dependencies
RUN conan install . --build=missing -s build_type=Release

# 复制源码 / Copy source code
COPY CMakeLists.txt ./
COPY proto/ ./proto/
COPY include/ ./include/
COPY src/ ./src/
COPY test/ ./test/

# CMake 构建 / CMake build
RUN cmake --preset conan-release \
    && cmake --build --preset conan-release -j$(nproc) \
    && ctest --preset conan-release --output-on-failure

# ==================== 运行阶段 / Runtime Stage ====================
FROM debian:bookworm-slim AS runtime

WORKDIR /app

# 安装运行时依赖 / Install runtime dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# 从构建阶段复制可执行文件 / Copy executable from build stage
COPY --from=builder /app/build/conan-release/delta-test-engine /app/delta-test-engine

# 环境变量默认值 / Environment variable defaults
ENV ENGINE_PORT=9090
ENV ENGINE_USE_TLS=false

# 暴露端口 / Expose port
EXPOSE 9090

# 启动命令 / Start command
CMD ["/app/delta-test-engine"]
