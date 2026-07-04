"""
风险评估接口
Risk Assessment API
<p>
基于代码变更分析结果进行风险评估，返回风险等级和建议。
Performs risk assessment based on code change analysis results, returning risk level and recommendations.
</p>

@author DeltaTest
"""

from fastapi import APIRouter

from app.models.requests import RiskAssessmentRequest
from app.models.response import R
from app.services.risk_service import get_risk_service

router = APIRouter(tags=["风险评估 / Risk Assessment"])


@router.post(
    "/api/risk-assessment",
    summary="风险评估 / Risk Assessment",
    description="基于代码变更分析结果进行风险评估 / Perform risk assessment based on code change analysis",
)
async def assess_risk(request: RiskAssessmentRequest) -> R[dict]:
    """
    风险评估
    Risk Assessment
    <p>
    接收变更文件列表和描述，返回风险等级、风险因素和建议。
    Receives changed file list and description, returns risk level, risk factors and recommendations.
    </p>

    Args:
        request: 风险评估请求 / Risk assessment request

    Returns:
        R[dict]: 风险评估结果 / Risk assessment result
    """
    service = get_risk_service()
    result = await service.assess(request.changed_files, request.change_description)
    return R.success(data=result)
