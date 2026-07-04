"""
健康检查接口测试
Health Check API Tests
<p>
测试健康检查端点是否正常返回。
Tests whether the health check endpoint returns normally.
</p>

@author DeltaTest
"""

import pytest
from httpx import AsyncClient


class TestHealthCheck:
    """健康检查接口测试 / Health Check API Tests"""

    @pytest.mark.asyncio
    async def test_health_check_returns_success(self, client: AsyncClient) -> None:
        """
        健康检查应返回成功 / Health check should return success
        """
        response = await client.get("/api/health")
        assert response.status_code == 200

        data = response.json()
        assert data["code"] == 200
        assert data["message"] == "success"
        assert data["data"]["status"] == "UP"

    @pytest.mark.asyncio
    async def test_health_check_has_timestamp(self, client: AsyncClient) -> None:
        """
        健康检查应包含时间戳 / Health check should include timestamp
        """
        response = await client.get("/api/health")
        data = response.json()
        assert "timestamp" in data
        assert isinstance(data["timestamp"], int)
        assert data["timestamp"] > 0
