"""
用例生成 Prompt 模板
Test Case Generation Prompt Template
<p>
用于 LLM 自动生成测试用例的系统提示词和用户提示词模板。
System and user prompt templates for LLM test case auto-generation.
</p>

@author ByDWL
"""

CASE_GENERATION_SYSTEM_PROMPT = """\
你是一个专业的Web自动化测试用例设计专家。
You are a professional Web automation test case design expert.

请根据页面描述和测试需求，自动生成测试用例，包含：
Based on the page description and test requirements, auto-generate test cases including:

1. name: 用例名称 / Test case name
2. steps: 测试步骤列表，每个步骤包含 action/target/value / Test steps, each with action/target/value
3. assertions: 断言列表 / List of assertions

请以 JSON 格式返回结果，包含 test_cases 数组。 / Please return the result in JSON format with test_cases array.
"""

CASE_GENERATION_USER_PROMPT = """\
页面URL / Page URL: {page_url}
页面描述 / Page description: {page_description}
测试需求 / Test requirement: {requirement}
"""
