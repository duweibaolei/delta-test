"""
风险评估服务
Risk Assessment Service
<p>
基于变更分析结果进行风险评估，骨架阶段返回占位数据。
Performs risk assessment based on change analysis results, returns placeholder data in skeleton phase.
</p>

@author ByDWL
"""

from app.models.enums import RiskLevel


class RiskService:
    """
    风险评估服务
    Risk Assessment Service
    <p>
    骨架阶段返回 mock 数据，Phase 1 接入 LLM 进行真实风险评估。
    Returns mock data in skeleton phase, integrates LLM for real risk assessment in Phase 1.
    </p>
    """

    async def assess(self, changed_files: list[str], change_description: str = "") -> dict:
        """
        评估代码变更风险
        Assess risk of code changes

        Args:
            changed_files: 变更文件路径列表 / List of changed file paths
            change_description: 变更描述 / Change description

        Returns:
            dict: 风险评估结果 / Risk assessment result
        """
        # 骨架实现: 基于文件数量简单判断 / Skeleton: simple heuristic based on file count
        file_count = len(changed_files)
        if file_count >= 10:
            risk_level = RiskLevel.HIGH
        elif file_count >= 3:
            risk_level = RiskLevel.MEDIUM
        else:
            risk_level = RiskLevel.LOW

        return {
            "risk_level": risk_level,
            "risk_factors": [
                {"factor": "变更文件数量 / Changed file count", "value": str(file_count)},
                {"factor": "变更描述 / Change description", "value": change_description or "(无 / None)"},
            ],
            "recommendation": (
                f"[MOCK] 建议对 {file_count} 个变更文件进行回归测试 / "
                f"Recommend regression testing for {file_count} changed files"
            ),
            "affected_areas": ["待分析 / To be analyzed"],
        }


# 全局服务实例 / Global service instance
_risk_service: RiskService | None = None


def get_risk_service() -> RiskService:
    """获取风险评估服务单例 / Get risk assessment service singleton"""
    global _risk_service
    if _risk_service is None:
        _risk_service = RiskService()
    return _risk_service
