"""
变更摘要接口
Change Summary API
<p>
基于代码变更详情生成变更说明与测试建议。
Generates change description and test recommendations based on code change details.
</p>

@author ByDWL
"""

from fastapi import APIRouter

from app.models.requests import SummaryRequest
from app.models.response import R
from app.services.summary_service import get_summary_service

router = APIRouter(tags=["变更摘要 / Change Summary"])


@router.post(
    "/api/summary",
    summary="变更摘要 / Change Summary",
    description="基于代码变更生成变更说明与测试建议 / Generate change description and test recommendations",
)
async def generate_summary(request: SummaryRequest) -> R[dict]:
    """
    生成变更摘要
    Generate Change Summary
    <p>
    接收变更文件列表和 diff 内容，生成变更说明与测试建议。
    Receives changed file list and diff content, generates change description and test recommendations.
    </p>

    Args:
        request: 变更摘要请求 / Change summary request

    Returns:
        R[dict]: 变更摘要结果 / Change summary result
    """
    service = get_summary_service()
    result = await service.generate(request.changed_files, request.diff_content)
    return R.success(data=result)
