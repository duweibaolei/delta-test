package com.dwl.model.dto.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用例生成请求 DTO
 * Test Case Generation Request DTO
 * <p>
 * 与 Python 端 CaseGenerationRequest 对齐，用于向 AI 服务发起用例生成请求。
 * Aligned with Python CaseGenerationRequest, used to send case generation requests to the AI service.
 * </p>
 *
 * @author ByDWL
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用例生成请求 / Case Generation Request")
public class CaseGenerationDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 页面URL
     * Page URL
     */
    @Schema(description = "页面URL / Page URL",
            example = "https://example.com/login",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "页面URL不能为空 / Page URL cannot be empty")
    private String pageUrl;

    /**
     * 页面描述
     * Page description
     */
    @Schema(description = "页面描述 / Page description",
            example = "用户登录页面，包含用户名、密码输入框和登录按钮",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "页面描述不能为空 / Page description cannot be empty")
    private String pageDescription;

    /**
     * 测试需求描述
     * Test requirement description
     */
    @Schema(description = "测试需求描述 / Test requirement description",
            example = "验证用户登录功能的各种场景")
    private String requirement;
}
