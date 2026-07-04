"""
变更摘要服务
Change Summary Service
<p>
基于代码变更详情生成变更说明与测试建议，骨架阶段返回占位数据。
Generates change description and test recommendations, returns placeholder data in skeleton phase.
</p>

@author DeltaTest
"""


class SummaryService:
    """
    变更摘要服务
    Change Summary Service
    <p>
    骨架阶段返回 mock 数据，Phase 1 接入 LLM 生成真实摘要。
    Returns mock data in skeleton phase, integrates LLM for real summaries in Phase 1.
    </p>
    """

    async def generate(self, changed_files: list[str], diff_content: str = "") -> dict:
        """
        生成变更摘要
        Generate change summary

        Args:
            changed_files: 变更文件路径列表 / List of changed file paths
            diff_content: 变更差异内容 / Change diff content

        Returns:
            dict: 变更摘要结果 / Change summary result
        """
        return {
            "summary": (
                f"[MOCK] 本次提交变更了 {len(changed_files)} 个文件 / "
                f"This commit changed {len(changed_files)} file(s)"
            ),
            "test_suggestions": [
                "[MOCK] 建议对变更涉及的功能进行回归测试 / "
                "Recommend regression testing for affected features"
            ],
            "changed_files": changed_files,
        }


# 全局服务实例 / Global service instance
_summary_service: SummaryService | None = None


def get_summary_service() -> SummaryService:
    """获取变更摘要服务单例 / Get change summary service singleton"""
    global _summary_service
    if _summary_service is None:
        _summary_service = SummaryService()
    return _summary_service
