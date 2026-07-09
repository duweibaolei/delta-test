package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 任务执行记录实体
 * Task Execution Record Entity
 * <p>
 * 对应表 task_execution，记录单条用例在任务中的执行实例。
 * Maps to table task_execution, recording the execution instance of a single case within a task.
 * </p>
 *
 * @author ByDWL
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("task_execution")
@Schema(description = "任务执行记录 / Task Execution Record")
public class TaskExecution extends BaseEntity {

    /**
     * 任务ID
     * Task ID
     */
    @Schema(description = "任务ID / Task ID", example = "1")
    private Long taskId;

    /**
     * 用例ID
     * Case ID
     */
    @Schema(description = "用例ID / Case ID", example = "1")
    private Long caseId;

    /**
     * 执行节点ID
     * Execution node ID
     */
    @Schema(description = "执行节点ID / Execution node ID")
    private Long nodeId;

    /**
     * 重试序号(0为首次)
     * Retry index (0 for first attempt)
     */
    @Schema(description = "重试序号(0为首次) / Retry index (0 for first)", example = "0")
    private Integer retryIndex;

    /**
     * 状态: pending/running/passed/failed/skipped/error
     * Status: pending/running/passed/failed/skipped/error
     */
    @Schema(description = "状态: pending/running/passed/failed/skipped/error / Status", example = "pending")
    private String status;

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

    /**
     * 失败步骤序号
     * Failed step order number
     */
    @Schema(description = "失败步骤序号 / Failed step order number")
    private Integer failedStep;

    /**
     * 错误信息
     * Error message
     */
    @Schema(description = "错误信息 / Error message")
    private String errorMessage;

    /**
     * 失败截图URL
     * Failure screenshot URL
     */
    @Schema(description = "失败截图URL / Failure screenshot URL")
    private String screenshotUrl;

    /**
     * 执行录像URL
     * Execution video URL
     */
    @Schema(description = "执行录像URL / Execution video URL")
    private String videoUrl;
}
