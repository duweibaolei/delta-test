"""
DeltaTest AI 服务配置管理
DeltaTest AI Service Configuration Management
<p>
基于 pydantic-settings 的类型安全配置管理，支持环境变量和 .env 文件。
Type-safe configuration management based on pydantic-settings, supporting env vars and .env files.
</p>

@author DeltaTest
"""

from functools import lru_cache

from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    """
    应用配置
    Application Settings
    <p>
    从环境变量和 .env 文件读取配置，所有字段均有默认值。
    Reads configuration from environment variables and .env files, all fields have default values.
    </p>
    """

    model_config = SettingsConfigDict(
        env_file=".env",
        env_file_encoding="utf-8",
        case_sensitive=False,
    )

    # ==================== 服务配置 / Service Configuration ====================

    """服务监听地址 / Service listen host"""
    SERVICE_HOST: str = "0.0.0.0"

    """服务监听端口 / Service listen port"""
    SERVICE_PORT: int = 8000

    """日志级别 / Log level"""
    LOG_LEVEL: str = "DEBUG"

    # ==================== Java 后端回调 / Java Backend Callback ====================

    """Java 后端服务地址 / Java backend service URL"""
    JAVA_SERVICE_URL: str = "http://localhost:8080"

    # ==================== LLM 配置 / LLM Configuration ====================

    """LLM API 密钥（可选，骨架阶段无需配置）/ LLM API key (optional, not needed in skeleton phase)"""
    LLM_API_KEY: str = ""

    """LLM API 基础地址 / LLM API base URL"""
    LLM_BASE_URL: str = "https://api.openai.com/v1"

    """LLM 模型名称 / LLM model name"""
    LLM_MODEL: str = "gpt-4o"

    """LLM 请求超时(秒) / LLM request timeout (seconds)"""
    LLM_TIMEOUT: int = 55


@lru_cache
def get_settings() -> Settings:
    """
    获取应用配置单例
    Get application settings singleton
    <p>
    使用 lru_cache 缓存，确保配置只加载一次。
    Uses lru_cache to ensure configuration is loaded only once.
    </p>

    Returns:
        Settings: 应用配置实例 / Application settings instance
    """
    return Settings()
