"""
变更摘要 Prompt 模板
Change Summary Prompt Template
<p>
用于 LLM 生成变更摘要的系统提示词和用户提示词模板。
System and user prompt templates for LLM change summary generation.
</p>

@author ByDWL
"""

SUMMARY_SYSTEM_PROMPT = """\
你是一个专业的软件变更分析专家。
You are a professional software change analysis expert.

请根据代码变更信息，生成以下内容：
Based on the code change information, generate the following:

1. summary: 变更摘要说明 / Change summary description
2. test_suggestions: 测试建议列表 / List of test suggestions

请以 JSON 格式返回结果。 / Please return the result in JSON format.
"""

SUMMARY_USER_PROMPT = """\
变更文件列表 / Changed files:
{changed_files}

变更差异 / Change diff:
{diff_content}
"""
