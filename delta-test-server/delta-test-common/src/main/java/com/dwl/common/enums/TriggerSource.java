package com.dwl.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 触发来源枚举
 * Trigger Source Enumeration
 * <p>
 * 定义测试任务的触发方式，包括Webhook自动触发、手动触发和定时触发。
 * Defines the trigger method of test tasks, including Webhook auto-trigger,
 * manual trigger, and scheduled trigger.
 * </p>
 *
 * @author DeltaTest
 */
@Getter
@AllArgsConstructor
@Schema(description = "触发来源枚举 / Trigger Source Enumeration")
public enum TriggerSource {

    /**
     * Webhook自动触发
     * Webhook auto-triggered
     */
    AUTO("auto", "Webhook自动触发"),

    /**
     * 手动触发
     * Manually triggered
     */
    MANUAL("manual", "手动触发"),

    /**
     * 定时触发
     * Scheduled trigger
     */
    SCHEDULED("scheduled", "定时触发");

    /**
     * 触发来源编码
     * Trigger source code
     */
    @Schema(description = "触发来源编码 / Trigger source code", example = "auto")
    private final String code;

    /**
     * 触发来源描述
     * Trigger source description
     */
    @Schema(description = "触发来源描述 / Trigger source description", example = "Webhook自动触发")
    private final String description;
}
