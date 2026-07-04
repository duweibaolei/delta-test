"""
用例生成接口
Test Case Generation API
<p>
根据页面描述和需求自动生成测试步骤与断言。
Auto-generates test steps and assertions based on page description and requirements.
</p>

@author DeltaTest
"""

from fastapi import APIRouter

from app.models.requests import CaseGenerationRequest
from app.models.response import R
from app.services.case_gen_service import get_case_gen_service

router = APIRouter(tags=["用例生成 / Case Generation"])


@router.post(
    "/api/case-generation",
    summary="用例生成 / Case Generation",
    description="根据页面描述自动生成测试步骤与断言 / Auto-generate test steps and assertions",
)
async def generate_case(request: CaseGenerationRequest) -> R[dict]:
    """
    生成测试用例
    Generate Test Case
    <p>
    接收页面描述和测试需求，返回自动生成的测试步骤与断言。
    Receives page description and test requirements, returns auto-generated test steps and assertions.
    </p>

    Args:
        request: 用例生成请求 / Case generation request

    Returns:
        R[dict]: 用例生成结果 / Case generation result
    """
    service = get_case_gen_service()
    result = await service.generate(request.page_url, request.page_description, request.requirement)
    return R.success(data=result)
