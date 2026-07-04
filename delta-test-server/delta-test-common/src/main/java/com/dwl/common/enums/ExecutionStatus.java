package com.dwl.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行状态枚举
 * Execution Status Enumeration
 * <p>
 * 定义测试用例执行结果的详细状态。
 * Defines the detailed status of test case execution results.
 * </p>
 *
 * @author DeltaTest
 */
@Getter
@AllArgsConstructor
@Schema(description = "执行状态枚举 / Execution Status Enumeration")
public enum ExecutionStatus {

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
     * 通过
     * Passed
     */
    PASSED("passed", "通过"),

    /**
     * 失败
     * Failed
     */
    FAILED("failed", "失败"),

    /**
     * 跳过
     * Skipped
     */
    SKIPPED("skipped", "跳过"),

    /**
     * 错误
     * Error
     */
    ERROR("error", "错误");

    /**
     * 状态编码
     * Status code
     */
    @Schema(description = "状态编码 / Status code", example = "passed")
    private final String code;

    /**
     * 状态描述
     * Status description
     */
    @Schema(description = "状态描述 / Status description", example = "通过")
    private final String description;
}
