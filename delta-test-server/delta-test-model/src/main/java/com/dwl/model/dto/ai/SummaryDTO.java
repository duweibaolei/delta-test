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
 * 变更摘要请求 DTO
 * Change Summary Request DTO
 * <p>
 * 与 Python 端 SummaryRequest 对齐，用于向 AI 服务发起变更摘要请求。
 * Aligned with Python SummaryRequest, used to send change summary requests to the AI service.
 * </p>
 *
 * @author ByDWL
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "变更摘要请求 / Change Summary Request")
public class SummaryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 变更文件路径列表
     * List of changed file paths
     */
    @Schema(description = "变更文件路径列表 / List of changed file paths",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "变更文件列表不能为空 / Changed files list cannot be empty")
    private List<String> changedFiles;

    /**
     * 变更差异内容
     * Change diff content
     */
    @Schema(description = "变更差异内容 / Change diff content")
    private String diffContent;

    /**
     * 提交ID
     * Commit ID
     */
    @Schema(description = "提交ID / Commit ID")
    private String commitId;
}
