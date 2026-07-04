package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 测试任务实体
 * Test Task Entity
 * <p>
 * 对应表 test_task，管理测试任务的调度、执行进度和结果统计。
 * Maps to table test_task, managing test task scheduling, execution progress, and result statistics.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("test_task")
@Schema(description = "测试任务 / Test Task")
public class TestTask extends BaseEntity {

    /**
     * 任务编号: TK-NNNN
     * Task number: TK-NNNN
     */
    @Schema(description = "任务编号: TK-NNNN / Task number", example = "TK-0001")
    private String taskNo;

    /**
     * 任务名称
     * Task name
     */
    @Schema(description = "任务名称 / Task name", example = "登录模块回归测试")
    private String taskName;

    /**
     * 触发来源: auto-变更自动 manual-手动 scheduled-定时
     * Trigger source: auto/manual/scheduled
     */
    @Schema(description = "触发来源: auto/manual/scheduled / Trigger source", example = "auto")
    private String triggerSource;

    /**
     * 手动触发用户ID
     * Manual trigger user ID
     */
    @Schema(description = "手动触发用户ID / Manual trigger user ID")
    private Long triggerUserId;

    /**
     * 关联变更分析ID(自动触发时)
     * Associated change analysis ID (for auto-triggered tasks)
     */
    @Schema(description = "关联变更分析ID / Associated change analysis ID")
    private Long analysisId;

    /**
     * 执行环境ID
     * Execution environment ID
     */
    @Schema(description = "执行环境ID / Execution environment ID")
    private Long envId;

    /**
     * 浏览器类型
     * Browser type
     */
    @Schema(description = "浏览器类型 / Browser type", example = "chromium")
    private String browserType;

    /**
     * 并发数
     * Concurrency level
     */
    @Schema(description = "并发数 / Concurrency level", example = "1")
    private Integer concurrency;

    /**
     * 失败重试次数
     * Failure retry count
     */
    @Schema(description = "失败重试次数 / Failure retry count", example = "0")
    private Integer retryCount;

    /**
     * 定时CRON表达式(定时任务)
     * Schedule CRON expression (for scheduled tasks)
     */
    @Schema(description = "定时CRON表达式 / Schedule CRON expression")
    private String scheduleCron;

    /**
     * 状态: pending/running/paused/completed/failed/cancelled
     * Status: pending/running/paused/completed/failed/cancelled
     */
    @Schema(description = "状态: pending/running/paused/completed/failed/cancelled / Status", example = "pending")
    private String status;

    /**
     * 执行进度(百分比)
     * Execution progress (percentage)
     */
    @Schema(description = "执行进度(百分比) / Execution progress (%)", example = "0")
    private Integer progress;

    /**
     * 通过数
     * Pass count
     */
    @Schema(description = "通过数 / Pass count", example = "0")
    private Integer passCount;

    /**
     * 失败数
     * Fail count
     */
    @Schema(description = "失败数 / Fail count", example = "0")
    private Integer failCount;

    /**
     * 跳过数
     * Skip count
     */
    @Schema(description = "跳过数 / Skip count", example = "0")
    private Integer skipCount;

    /**
     * 总用例数
     * Total case count
     */
    @Schema(description = "总用例数 / Total case count", example = "0")
    private Integer totalCount;

    /**
     * 开始时间
     * Start time
     */
    @Schema(description = "开始时间 / Start time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     * End time
     */
    @Schema(description = "结束时间 / End time")
    private LocalDateTime endTime;

    /**
     * 执行耗时(毫秒)
     * Execution duration (milliseconds)
     */
    @Schema(description = "执行耗时(毫秒) / Execution duration (ms)")
    private Long durationMs;
}
