package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 测试报告实体
 * Test Report Entity
 * <p>
 * 对应表 test_report，管理测试报告的统计、AI分析和结论信息。
 * Maps to table test_report, managing test report statistics, AI analysis, and conclusion information.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("test_report")
@Schema(description = "测试报告 / Test Report")
public class TestReport extends BaseEntity {

    /**
     * 报告编号: RPT-YYYYMMDD-NNNN
     * Report number: RPT-YYYYMMDD-NNNN
     */
    @Schema(description = "报告编号: RPT-YYYYMMDD-NNNN / Report number", example = "RPT-20260101-0001")
    private String reportNo;

    /**
     * 关联任务ID
     * Associated task ID
     */
    @Schema(description = "关联任务ID / Associated task ID", example = "1")
    private Long taskId;

    /**
     * 报告类型: task-任务报告 link-链路分析报告 change-变更质量报告
     * Report type: task/link/change
     */
    @Schema(description = "报告类型: task/link/change / Report type", example = "task")
    private String reportType;

    /**
     * 触发来源: auto/manual
     * Trigger source: auto/manual
     */
    @Schema(description = "触发来源: auto/manual / Trigger source", example = "auto")
    private String triggerSource;

    /**
     * 总用例数
     * Total case count
     */
    @Schema(description = "总用例数 / Total case count", example = "10")
    private Integer totalCount;

    /**
     * 通过数
     * Pass count
     */
    @Schema(description = "通过数 / Pass count", example = "8")
    private Integer passCount;

    /**
     * 失败数
     * Fail count
     */
    @Schema(description = "失败数 / Fail count", example = "1")
    private Integer failCount;

    /**
     * 跳过数
     * Skip count
     */
    @Schema(description = "跳过数 / Skip count", example = "1")
    private Integer skipCount;

    /**
     * 通过率(%)
     * Pass rate (%)
     */
    @Schema(description = "通过率(%) / Pass rate (%)", example = "80.00")
    private BigDecimal passRate;

    /**
     * 总执行耗时(毫秒)
     * Total execution duration (milliseconds)
     */
    @Schema(description = "总执行耗时(毫秒) / Total execution duration (ms)")
    private Long durationMs;

    /**
     * AI分析摘要
     * AI analysis summary
     */
    @Schema(description = "AI分析摘要 / AI analysis summary")
    private String aiSummary;

    /**
     * AI修复建议
     * AI fix suggestion
     */
    @Schema(description = "AI修复建议 / AI fix suggestion")
    private String aiSuggestion;

    /**
     * AI是否完成分析: 1-是 0-否
     * Whether AI has completed analysis: 1-yes, 0-no
     */
    @Schema(description = "AI是否完成分析: 1-是 0-否 / AI analysis completed", example = "0")
    private Integer aiAnalyzed;

    /**
     * 手动调整的报告结论
     * Manual adjusted report conclusion
     */
    @Schema(description = "手动调整的报告结论 / Manual adjusted report conclusion")
    private String manualConclusion;

    /**
     * 手动补充测试备注
     * Manual supplementary test remarks
     */
    @Schema(description = "手动补充测试备注 / Manual supplementary test remarks")
    private String manualRemark;

    /**
     * 状态: draft-草稿 published-已发布
     * Status: draft/published
     */
    @Schema(description = "状态: draft-草稿 published-已发布 / Status", example = "draft")
    private String status;
}
