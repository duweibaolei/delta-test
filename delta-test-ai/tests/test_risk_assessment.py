"""
风险评估接口测试
Risk Assessment API Tests
<p>
测试风险评估端点是否正常返回。
Tests whether the risk assessment endpoint returns normally.
</p>

@author ByDWL
"""

import pytest
from httpx import AsyncClient


class TestRiskAssessment:
    """风险评估接口测试 / Risk Assessment API Tests"""

    @pytest.mark.asyncio
    async def test_assess_risk_returns_success(self, client: AsyncClient) -> None:
        """
        风险评估应返回成功 / Risk assessment should return success
        """
        response = await client.post(
            "/api/risk-assessment",
            json={
                "changed_files": ["src/main/java/com/dwl/service/UserService.java"],
                "change_description": "修改了用户登录逻辑",
            },
        )
        assert response.status_code == 200

        data = response.json()
        assert data["code"] == 200
        assert data["message"] == "success"
        assert "risk_level" in data["data"]

    @pytest.mark.asyncio
    async def test_assess_risk_with_many_files(self, client: AsyncClient) -> None:
        """
        大量变更文件应返回高风险 / Many changed files should return high risk
        """
        files = [f"src/file_{i}.java" for i in range(15)]
        response = await client.post(
            "/api/risk-assessment",
            json={
                "changed_files": files,
            },
        )
        data = response.json()
        assert data["data"]["risk_level"] == "high"
