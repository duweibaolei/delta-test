"""
根因分析接口
Root Cause Analysis API
<p>
分析失败用例的可能原因与修复建议。
Analyzes possible causes of failed test cases and provides fix suggestions.
</p>

@author ByDWL
"""

from fastapi import APIRouter

from app.models.requests import RootCauseRequest
from app.models.response import R
from app.services.root_cause_service import get_root_cause_service

router = APIRouter(tags=["根因分析 / Root Cause Analysis"])


@router.post(
    "/api/root-cause",
    summary="根因分析 / Root Cause Analysis",
    description="分析失败用例的可能原因 / Analyze possible causes of failed test cases",
)
async def analyze_root_cause(request: RootCauseRequest) -> R[dict]:
    """
    根因分析
    Root Cause Analysis
    <p>
    接收失败用例信息和执行日志，返回可能原因、修复建议和置信度。
    Receives failed test case info and execution logs, returns possible causes, fix suggestions and confidence.
    </p>

    Args:
        request: 根因分析请求 / Root cause analysis request

    Returns:
        R[dict]: 根因分析结果 / Root cause analysis result
    """
    service = get_root_cause_service()
    result = await service.analyze(request.case_name, request.error_message, request.execution_log)
    return R.success(data=result)
