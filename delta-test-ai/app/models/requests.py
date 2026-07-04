"""
请求 DTO 模型
Request DTO Models
<p>
定义各 AI 接口的请求参数模型，使用 Pydantic 进行类型校验。
Defines request parameter models for each AI interface, using Pydantic for type validation.
</p>

@author DeltaTest
"""

from pydantic import BaseModel, Field


class RiskAssessmentRequest(BaseModel):
    """
    风险评估请求
    Risk Assessment Request
    <p>
    包含变更文件列表和变更描述，用于评估代码变更的风险等级。
    Contains changed file list and change description for assessing risk level of code changes.
    </p>
    """

    """变更文件路径列表 / List of changed file paths"""
    changed_files: list[str] = Field(
        ...,
        description="变更文件路径列表 / List of changed file paths",
        examples=[["src/main/java/com/dwl/service/UserService.java"]],
    )

    """变更描述 / Change description"""
    change_description: str = Field(
        default="",
        description="变更描述 / Change description",
        examples=["修改了用户登录逻辑"],
    )

    """提交ID / Commit ID"""
    commit_id: str = Field(
        default="",
        description="提交ID / Commit ID",
        examples=["abc1234"],
    )


class SummaryRequest(BaseModel):
    """
    变更摘要请求
    Change Summary Request
    <p>
    包含变更详情，用于生成变更说明与测试建议。
    Contains change details for generating change description and test recommendations.
    </p>
    """

    """变更文件路径列表 / List of changed file paths"""
    changed_files: list[str] = Field(
        ...,
        description="变更文件路径列表 / List of changed file paths",
    )

    """变更差异内容 / Change diff content"""
    diff_content: str = Field(
        default="",
        description="变更差异内容 / Change diff content",
    )

    """提交ID / Commit ID"""
    commit_id: str = Field(
        default="",
        description="提交ID / Commit ID",
    )


class RootCauseRequest(BaseModel):
    """
    根因分析请求
    Root Cause Analysis Request
    <p>
    包含失败用例和执行日志，用于分析失败原因与修复建议。
    Contains failed test case and execution logs for analyzing failure causes and fix suggestions.
    </p>
    """

    """用例名称 / Test case name"""
    case_name: str = Field(
        ...,
        description="用例名称 / Test case name",
        examples=["用户登录验证"],
    )

    """错误信息 / Error message"""
    error_message: str = Field(
        ...,
        description="错误信息 / Error message",
    )

    """执行日志 / Execution logs"""
    execution_log: str = Field(
        default="",
        description="执行日志 / Execution logs",
    )

    """关联任务ID / Associated task ID"""
    task_id: str = Field(
        default="",
        description="关联任务ID / Associated task ID",
    )


class CaseGenerationRequest(BaseModel):
    """
    用例生成请求
    Test Case Generation Request
    <p>
    包含页面描述和需求信息，用于自动生成测试步骤与断言。
    Contains page description and requirements for auto-generating test steps and assertions.
    </p>
    """

    """页面URL / Page URL"""
    page_url: str = Field(
        ...,
        description="页面URL / Page URL",
        examples=["https://example.com/login"],
    )

    """页面描述 / Page description"""
    page_description: str = Field(
        ...,
        description="页面描述 / Page description",
        examples=["用户登录页面，包含用户名、密码输入框和登录按钮"],
    )

    """测试需求描述 / Test requirement description"""
    requirement: str = Field(
        default="",
        description="测试需求描述 / Test requirement description",
        examples=["验证用户登录功能的各种场景"],
    )
