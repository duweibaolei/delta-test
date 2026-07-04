package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 质量趋势日统计实体
 * Quality Daily Statistics Entity
 * <p>
 * 对应表 quality_daily_stats，按日聚合的质量度量统计数据。
 * Maps to table quality_daily_stats, daily aggregated quality metric statistics.
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
@TableName("quality_daily_stats")
@Schema(description = "质量趋势日统计 / Quality Daily Statistics")
public class QualityDailyStats extends BaseEntity {

    /**
     * 统计日期
     * Statistics date
     */
    @Schema(description = "统计日期 / Statistics date", example = "2026-01-01")
    private LocalDate statDate;

    /**
     * 总用例数
     * Total case count
     */
    @Schema(description = "总用例数 / Total case count", example = "100")
    private Integer totalCases;

    /**
     * 执行用例数
     * Total executed case count
     */
    @Schema(description = "执行用例数 / Total executed case count", example = "80")
    private Integer totalExecuted;

    /**
     * 通过数
     * Total passed count
     */
    @Schema(description = "通过数 / Total passed count", example = "72")
    private Integer totalPassed;

    /**
     * 失败数
     * Total failed count
     */
    @Schema(description = "失败数 / Total failed count", example = "8")
    private Integer totalFailed;

    /**
     * 通过率(%)
     * Pass rate (%)
     */
    @Schema(description = "通过率(%) / Pass rate (%)", example = "90.00")
    private BigDecimal passRate;

    /**
     * 新增缺陷数
     * New defect count
     */
    @Schema(description = "新增缺陷数 / New defect count", example = "3")
    private Integer newDefects;

    /**
     * 解决缺陷数
     * Resolved defect count
     */
    @Schema(description = "解决缺陷数 / Resolved defect count", example = "2")
    private Integer resolvedDefects;

    /**
     * 自动来源用例数
     * Auto-sourced case count
     */
    @Schema(description = "自动来源用例数 / Auto-sourced case count", example = "40")
    private Integer autoCases;

    /**
     * 手动来源用例数
     * Manual-sourced case count
     */
    @Schema(description = "手动来源用例数 / Manual-sourced case count", example = "50")
    private Integer manualCases;

    /**
     * 混合来源用例数
     * Hybrid-sourced case count
     */
    @Schema(description = "混合来源用例数 / Hybrid-sourced case count", example = "10")
    private Integer hybridCases;
}
