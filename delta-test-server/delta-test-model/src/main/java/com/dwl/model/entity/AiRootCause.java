package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * AI根因分析实体
 * AI Root Cause Analysis Entity
 * <p>
 * 对应表 ai_root_cause，记录AI对失败用例的根因分析结果。
 * Maps to table ai_root_cause, recording AI root cause analysis results for failed cases.
 * 继承 BaseEntity，包含公共审计字段。
 * Extends BaseEntity, including common audit fields.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("ai_root_cause")
@Schema(description = "AI根因分析 / AI Root Cause Analysis")
public class AiRootCause extends BaseEntity {

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
     * 分析时间
     * Analyzed timestamp
     */
    @Schema(description = "分析时间 / Analyzed timestamp")
    private LocalDateTime analyzedAt;
}
