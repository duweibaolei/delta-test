package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI根因分析实体
 * AI Root Cause Analysis Entity
 * <p>
 * 对应表 ai_root_cause，记录AI对失败用例的根因分析结果。
 * Maps to table ai_root_cause, recording AI root cause analysis results for failed cases.
 * 此表无 updated_at 字段，不继承 BaseEntity。
 * This table has no updated_at column and does not extend BaseEntity.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ai_root_cause")
@Schema(description = "AI根因分析 / AI Root Cause Analysis")
public class AiRootCause implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     * Primary key ID
     */
    @Schema(description = "主键ID / Primary key ID", example = "1")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 执行记录ID
     * Execution record ID
     */
    @Schema(description = "执行记录ID / Execution record ID", example = "1")
    private Long executionId;

    /**
     * 报告ID
     * Report ID
     */
    @Schema(description = "报告ID / Report ID")
    private Long reportId;

    /**
     * AI分析可能原因
     * AI analyzed possible cause
     */
    @Schema(description = "AI分析可能原因 / AI analyzed possible cause")
    private String possibleCause;

    /**
     * 置信度(0-100)
     * Confidence level (0-100)
     */
    @Schema(description = "置信度(0-100) / Confidence level (0-100)", example = "85")
    private Integer confidence;

    /**
     * 修复建议
     * Fix suggestion
     */
    @Schema(description = "修复建议 / Fix suggestion")
    private String fixSuggestion;

    /**
     * AI模型版本
     * AI model version
     */
    @Schema(description = "AI模型版本 / AI model version", example = "gpt-4o")
    private String modelVersion;

    /**
     * 逻辑删除标志: 0-未删除 1-已删除
     * Logical delete flag: 0-not deleted, 1-deleted
     */
    @Schema(description = "逻辑删除标志: 0-未删除 1-已删除 / Logical delete flag: 0-not deleted, 1-deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 分析时间
     * Analyzed timestamp
     */
    @Schema(description = "分析时间 / Analyzed timestamp")
    private LocalDateTime analyzedAt;
}
