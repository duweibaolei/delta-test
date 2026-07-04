"""
DeltaTest AI 服务应用入口
DeltaTest AI Service Application Entry Point
<p>
双模式驱动的Web自动化测试平台 AI服务入口，基于 FastAPI 构建。
Entry point for the DeltaTest dual-mode Web automation testing platform AI service, built on FastAPI.
</p>

@author DeltaTest
"""

from contextlib import asynccontextmanager

from fastapi import FastAPI
from loguru import logger

from app.api import case_generation, health, risk_assessment, root_cause, summary
from app.core.config import get_settings


@asynccontextmanager
async def lifespan(_app: FastAPI):  # type: ignore[no-untyped-def]
    """
    应用生命周期管理
    Application lifecycle management
    <p>
    启动时加载配置并记录日志，关闭时清理资源。
    Loads configuration and logs on startup, cleans up resources on shutdown.
    </p>
    """
    settings = get_settings()
    logger.info(
        "DeltaTest AI 服务启动中 / DeltaTest AI Service starting | "
        "host={} port={} log_level={}",
        settings.SERVICE_HOST,
        settings.SERVICE_PORT,
        settings.LOG_LEVEL,
    )
    yield
    logger.info("DeltaTest AI 服务关闭 / DeltaTest AI Service shutting down")


# ==================== FastAPI 应用实例 / FastAPI Application Instance ====================
app = FastAPI(
    title="DeltaTest AI Service",
    version="1.0.0",
    description=(
        "双模式驱动的Web自动化测试平台 AI服务 / "
        "DeltaTest Dual-mode Web Automation Testing Platform AI Service"
    ),
    lifespan=lifespan,
)

# ==================== 路由注册 / Route Registration ====================
app.include_router(health.router)
app.include_router(risk_assessment.router)
app.include_router(summary.router)
app.include_router(root_cause.router)
app.include_router(case_generation.router)
