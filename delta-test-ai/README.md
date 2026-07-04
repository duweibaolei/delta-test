# DeltaTest AI Service

双模式驱动的Web自动化测试平台 AI服务

DeltaTest Dual-mode Web Automation Testing Platform AI Service

## 快速开始 / Quick Start

```bash
# 安装依赖 / Install dependencies
uv sync --extra dev

# 启动服务 / Start service
uv run uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload

# 运行测试 / Run tests
uv run pytest

# 代码检查 / Lint check
uv run ruff check .
```

## API 文档 / API Documentation

启动服务后访问 / After starting the service, visit:

- Swagger UI: http://localhost:8000/docs
- ReDoc: http://localhost:8000/redoc

## 技术栈 / Tech Stack

- Python 3.12 + FastAPI + Uvicorn
- Pydantic 2.x + pydantic-settings
- httpx + loguru
- Ruff + pytest + mypy
