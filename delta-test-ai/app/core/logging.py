"""
DeltaTest AI 服务日志配置
DeltaTest AI Service Logging Configuration
<p>
基于 loguru 的 JSON 结构化日志配置。开发环境使用可读格式，生产环境使用 JSON 格式。
Loguru-based JSON structured logging configuration. Development uses readable format, production uses JSON format.
</p>

JSON 日志格式规范 / JSON Log Format Specification:
{
    "timestamp": "2026-07-05T19:00:00.000000+08:00",
    "level": "INFO",
    "name": "app.main",
    "message": "服务启动 / Service starting",
    "service": "delta-test-ai"
}

@author ByDWL
"""

import json
import sys
from typing import Any

from loguru import logger

from app.core.config import get_settings


def _json_sink(message: Any) -> None:
    """
    JSON 格式日志输出
    JSON format log output
    <p>
    将 loguru 的日志记录序列化为 JSON 格式输出到 stderr，便于日志采集系统（如 Filebeat/Fluentd）解析。
    Serializes loguru log records to JSON format output to stderr, for log collection systems (e.g., Filebeat/Fluentd) to parse.
    </p>

    Args:
        message: loguru 日志记录对象 / loguru log record object
    """
    record = message.record
    log_entry: dict[str, Any] = {
        "timestamp": record["time"].isoformat(),
        "level": record["level"].name,
        "name": record["name"],
        "message": record["message"],
        "service": "delta-test-ai",
    }
    # 附加异常信息 / Append exception info
    if record["exception"]:
        exc_type, exc_value, _ = record["exception"]
        log_entry["exception_type"] = str(exc_type) if exc_type else None
        log_entry["exception_message"] = str(exc_value) if exc_value else None

    # 附加额外上下文 / Append extra context
    extra = record.get("extra")
    if extra:
        log_entry["context"] = extra

    sys.stderr.write(json.dumps(log_entry, ensure_ascii=False, default=str) + "\n")
    sys.stderr.flush()


def setup_logging() -> None:
    """
    配置 loguru 日志
    Configure loguru logging
    <p>
    根据环境变量 LOG_FORMAT 切换日志格式：
    Switches log format based on LOG_FORMAT environment variable:
    - "json"（生产环境）: JSON 结构化日志，便于日志采集和分析
    - 其他值（开发环境）: 彩色可读格式，便于开发调试
    
    同时应用 LOG_LEVEL 配置的日志级别过滤。
    Also applies LOG_LEVEL configuration for log level filtering.
    </p>
    """
    settings = get_settings()

    # 移除 loguru 默认 handler / Remove loguru default handler
    logger.remove()

    log_format = getattr(settings, "LOG_FORMAT", "console").upper()

    if log_format == "JSON":
        # 生产环境: JSON 结构化日志 / Production: JSON structured logging
        logger.add(
            _json_sink,
            level=settings.LOG_LEVEL.upper(),
            filter=lambda record: True,
        )
    else:
        # 开发环境: 彩色可读格式 / Development: Colored readable format
        logger.add(
            sys.stderr,
            level=settings.LOG_LEVEL.upper(),
            format=(
                "<green>{time:YYYY-MM-DD HH:mm:ss.SSS}</green> | "
                "<level>{level: <8}</level> | "
                "<cyan>{name}</cyan>:<cyan>{function}</cyan>:<cyan>{line}</cyan> | "
                "<level>{message}</level>"
            ),
            colorize=True,
        )
