"""
枚举定义
Enum Definitions
<p>
定义 AI 服务中使用的枚举类型，错误码与 Java 端 ErrorCode 5xxx 范围对齐。
Defines enum types used in the AI service, error codes aligned with Java ErrorCode 5xxx range.
</p>

@author DeltaTest
"""

from enum import IntEnum


class RiskLevel(str):
    """
    风险等级
    Risk Level
    <p>
    代码变更的风险等级评估结果。
    Risk level assessment result for code changes.
    </p>
    """

    """高风险 / High risk"""
    HIGH = "high"

    """中风险 / Medium risk"""
    MEDIUM = "medium"

    """低风险 / Low risk"""
    LOW = "low"


class ErrorCode(IntEnum):
    """
    AI 服务错误码
    AI Service Error Codes
    <p>
    与 Java 端 ErrorCode 5xxx 范围对齐。
    Aligned with Java ErrorCode 5xxx range.
    </p>
    """

    """C计算引擎不可用 / C calculation engine unavailable"""
    ENGINE_UNAVAILABLE = 5001

    """AI服务不可用 / AI service unavailable"""
    AI_UNAVAILABLE = 5002

    """LLM 调用超时 / LLM call timeout"""
    LLM_TIMEOUT = 5003

    """LLM 调用失败 / LLM call failed"""
    LLM_CALL_FAILED = 5004

    """风险评估参数错误 / Risk assessment parameter error"""
    RISK_ASSESSMENT_ERROR = 5101

    """变更摘要生成失败 / Change summary generation failed"""
    SUMMARY_GENERATION_ERROR = 5102

    """根因分析失败 / Root cause analysis failed"""
    ROOT_CAUSE_ERROR = 5103

    """用例生成失败 / Test case generation failed"""
    CASE_GENERATION_ERROR = 5104
