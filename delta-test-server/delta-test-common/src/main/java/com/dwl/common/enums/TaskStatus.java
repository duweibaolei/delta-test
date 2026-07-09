package com.dwl.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务状态枚举
 * Task Status Enumeration
 * <p>
 * 定义测试任务的生命周期状态。
 * Defines the lifecycle states of a test task.
 * </p>
 *
 * @author ByDWL
 */
@Getter
@AllArgsConstructor
@Schema(description = "任务状态枚举 / Task Status Enumeration")
public enum TaskStatus {

    /**
     * 待执行
     * Pending execution
     */
    PENDING("pending", "待执行"),

    /**
     * 执行中
     * Running
     */
    RUNNING("running", "执行中"),

    /**
     * 已暂停
     * Paused
     */
    PAUSED("paused", "已暂停"),

    /**
     * 已完成
     * Completed
     */
    COMPLETED("completed", "已完成"),

    /**
     * 执行失败
     * Failed
     */
    FAILED("failed", "执行失败"),

    /**
     * 已取消
     * Cancelled
     */
    CANCELLED("cancelled", "已取消");

    /**
     * 状态编码
     * Status code
     */
    @Schema(description = "状态编码 / Status code", example = "pending")
    private final String code;

    /**
     * 状态描述
     * Status description
     */
    @Schema(description = "状态描述 / Status description", example = "待执行")
    private final String description;
}
