package com.dwl.model.dto.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 风险评估请求 DTO
 * Risk Assessment Request DTO
 * <p>
 * 与 Python 端 RiskAssessmentRequest 对齐，用于向 AI 服务发起风险评估请求。
 * Aligned with Python RiskAssessmentRequest, used to send risk assessment requests to the AI service.
 * </p>
 *
 * @author ByDWL
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "风险评估请求 / Risk Assessment Request")
public class RiskAssessmentDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 变更文件路径列表
     * List of changed file paths
     */
    @Schema(description = "变更文件路径列表 / List of changed file paths",
            example = "[\"src/main/java/com/dwl/service/UserService.java\"]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "变更文件列表不能为空 / Changed files list cannot be empty")
    private List<String> changedFiles;

    /**
     * 变更描述
     * Change description
     */
    @Schema(description = "变更描述 / Change description",
            example = "修改了用户登录逻辑")
    private String changeDescription;

    /**
     * 提交ID
     * Commit ID
     */
    @Schema(description = "提交ID / Commit ID", example = "abc1234")
    private String commitId;
}
