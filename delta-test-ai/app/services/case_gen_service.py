"""
用例生成服务
Test Case Generation Service
<p>
根据页面描述和需求自动生成测试步骤与断言，骨架阶段返回占位数据。
Auto-generates test steps and assertions, returns placeholder data in skeleton phase.
</p>

@author DeltaTest
"""


class CaseGenService:
    """
    用例生成服务
    Test Case Generation Service
    <p>
    骨架阶段返回 mock 数据，Phase 1 接入 LLM 生成真实用例。
    Returns mock data in skeleton phase, integrates LLM for real case generation in Phase 1.
    </p>
    """

    async def generate(self, page_url: str, page_description: str, requirement: str = "") -> dict:
        """
        生成测试用例
        Generate test case

        Args:
            page_url: 页面URL / Page URL
            page_description: 页面描述 / Page description
            requirement: 测试需求 / Test requirement

        Returns:
            dict: 用例生成结果 / Case generation result
        """
        return {
            "page_url": page_url,
            "test_cases": [
                {
                    "name": f"[MOCK] {page_description} - 基本功能验证 / Basic functionality test",
                    "steps": [
                        {"action": "navigate", "target": page_url, "value": ""},
                        {"action": "verify", "target": "page_title", "value": "[MOCK] 预期标题 / Expected title"},
                    ],
                    "assertions": ["[MOCK] 页面正常加载 / Page loads normally"],
                }
            ],
        }


# 全局服务实例 / Global service instance
_case_gen_service: CaseGenService | None = None


def get_case_gen_service() -> CaseGenService:
    """获取用例生成服务单例 / Get case generation service singleton"""
    global _case_gen_service
    if _case_gen_service is None:
        _case_gen_service = CaseGenService()
    return _case_gen_service
