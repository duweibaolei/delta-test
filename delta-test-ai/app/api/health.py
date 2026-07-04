"""
健康检查接口
Health Check API
<p>
提供 AI 服务的健康检查端点，供 Java 后端和 K8s 探针调用。
Provides AI service health check endpoint for Java backend and K8s probes.
</p>

@author DeltaTest
"""

from fastapi import APIRouter

from app.models.response import R

router = APIRouter(tags=["健康检查 / Health Check"])


class HealthVO:
    """健康检查响应数据 / Health check response data"""

    status: str = "UP"


@router.get(
    "/api/health",
    summary="健康检查 / Health Check",
    description="检查 AI 服务是否正常运行 / Check if AI service is running normally",
)
async def health_check() -> R[dict]:
    """
    健康检查
    Health Check
    <p>
    返回 AI 服务的健康状态，供 K8s liveness/readiness 探针使用。
    Returns AI service health status, used by K8s liveness/readiness probes.
    </p>

    Returns:
        R[dict]: 健康状态 / Health status
    """
    return R.success(data={"status": "UP"})
