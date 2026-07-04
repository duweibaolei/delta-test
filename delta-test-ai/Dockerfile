# ============================================================
# DeltaTest AI 服务 Dockerfile
# DeltaTest AI Service Dockerfile
# ============================================================
# 多阶段构建：使用 uv 安装依赖，slim 镜像运行
# Multi-stage build: use uv for dependency installation, slim image for runtime

# ==================== 构建阶段 / Build Stage ====================
FROM ghcr.io/astral-sh/uv:python3.12-bookworm-slim AS builder

WORKDIR /app

# 复制依赖定义文件 / Copy dependency definition files
COPY pyproject.toml ./
COPY uv.lock ./
COPY .python-version ./

# 安装依赖到虚拟环境 / Install dependencies to virtual environment
RUN uv venv /app/.venv && \
    uv pip install --python /app/.venv/bin/python .

# ==================== 运行阶段 / Runtime Stage ====================
FROM python:3.12-slim AS runtime

WORKDIR /app

# 从构建阶段复制虚拟环境 / Copy virtual environment from build stage
COPY --from=builder /app/.venv /app/.venv

# 复制应用代码 / Copy application code
COPY app/ ./app/

# 设置环境变量 / Set environment variables
ENV PATH="/app/.venv/bin:$PATH" \
    PYTHONUNBUFFERED=1 \
    PYTHONDONTWRITEBYTECODE=1

# 暴露端口 / Expose port
EXPOSE 8000

# 启动命令 / Start command
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
