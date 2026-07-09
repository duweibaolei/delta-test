package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 变更分析实体
 * Change Analysis Entity
 * <p>
 * 对应表 change_analysis，记录代码变更分析的结果和风险评定信息。
 * Maps to table change_analysis, recording code change analysis results and risk assessment information.
 * </p>
 *
 * @author ByDWL
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("change_analysis")
@Schema(description = "变更分析 / Change Analysis")
public class ChangeAnalysis extends BaseEntity {

    /**
     * 分析编号: CA-YYYYMMDD-NNNN
     * Analysis number: CA-YYYYMMDD-NNNN
     */
    @Schema(description = "分析编号: CA-YYYYMMDD-NNNN / Analysis number", example = "CA-20260101-0001")
    private String analysisNo;

    /**
     * 仓库ID
     * Repository ID
     */
    @Schema(description = "仓库ID / Repository ID", example = "1")
    private Long repoId;

    /**
     * 分支名
     * Branch name
     */
    @Schema(description = "分支名 / Branch name", example = "main")
    private String branch;

    /**
     * 起始Commit(手动分析时指定)
     * Start commit hash (specified in manual analysis)
     */
    @Schema(description = "起始Commit / Start commit hash")
    private String startCommitHash;

    /**
     * 截止Commit(手动分析时指定)
     * End commit hash (specified in manual analysis)
     */
    @Schema(description = "截止Commit / End commit hash")
    private String endCommitHash;

    /**
     * 触发来源: auto-Webhook manual-手动
     * Trigger source: auto-Webhook, manual-manual
     */
    @Schema(description = "触发来源: auto-Webhook manual-手动 / Trigger source", example = "auto")
    private String triggerSource;

    /**
     * 手动触发用户ID
     * Manual trigger user ID
     */
    @Schema(description = "手动触发用户ID / Manual trigger user ID")
    private Long triggerUserId;

    /**
     * AI风险等级: high/medium/low
     * AI risk level: high/medium/low
     */
    @Schema(description = "AI风险等级: high/medium/low / AI risk level", example = "low")
    private String riskLevel;

    /**
     * 手动调整风险等级: high/medium/low
     * Manual adjusted risk level: high/medium/low
     */
    @Schema(description = "手动调整风险等级 / Manual adjusted risk level")
    private String riskLevelManual;

    /**
     * 风险等级调整原因
     * Risk level adjustment reason
     */
    @Schema(description = "风险等级调整原因 / Risk level adjustment reason")
    private String riskAdjustReason;

    /**
     * AI变更摘要
     * AI change summary
     */
    @Schema(description = "AI变更摘要 / AI change summary")
    private String aiSummary;

    /**
     * AI测试建议
     * AI test suggestion
     */
    @Schema(description = "AI测试建议 / AI test suggestion")
    private String aiTestSuggestion;

    /**
     * 手动补充变更说明
     * Manual supplementary change description
     */
    @Schema(description = "手动补充变更说明 / Manual supplementary change description")
    private String manualDescription;

    /**
     * 状态: running/completed/failed
     * Status: running/completed/failed
     */
    @Schema(description = "状态: running/completed/failed / Status", example = "completed")
    private String status;
}
