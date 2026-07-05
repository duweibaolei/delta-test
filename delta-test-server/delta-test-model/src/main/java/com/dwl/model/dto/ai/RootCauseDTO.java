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
 * 根因分析请求 DTO
 * Root Cause Analysis Request DTO
 * <p>
 * 与 Python 端 RootCauseRequest 对齐，用于向 AI 服务发起根因分析请求。
 * Aligned with Python RootCauseRequest, used to send root cause analysis requests to the AI service.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "根因分析请求 / Root Cause Analysis Request")
public class RootCauseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用例名称
     * Test case name
     */
    @Schema(description = "用例名称 / Test case name",
            example = "用户登录验证",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用例名称不能为空 / Case name cannot be empty")
    private String caseName;

    /**
     * 错误信息
     * Error message
     */
    @Schema(description = "错误信息 / Error message",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "错误信息不能为空 / Error message cannot be empty")
    private String errorMessage;

    /**
     * 执行日志
     * Execution logs
     */
    @Schema(description = "执行日志 / Execution logs")
    private String executionLog;

    /**
     * 关联任务ID
     * Associated task ID
     */
    @Schema(description = "关联任务ID / Associated task ID")
    private String taskId;
}
