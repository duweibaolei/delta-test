package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 执行步骤结果实体
 * Execution Step Result Entity
 * <p>
 * 对应表 execution_step_result，记录每次执行中每个步骤的详细结果。
 * Maps to table execution_step_result, recording detailed results for each step in an execution.
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
@TableName("execution_step_result")
@Schema(description = "执行步骤结果 / Execution Step Result")
public class ExecutionStepResult extends BaseEntity {

    /**
     * 执行记录ID
     * Execution record ID
     */
    @Schema(description = "执行记录ID / Execution record ID", example = "1")
    private Long executionId;

    /**
     * 步骤序号
     * Step order number
     */
    @Schema(description = "步骤序号 / Step order number", example = "1")
    private Integer stepOrder;

    /**
     * 动作类型
     * Action type
     */
    @Schema(description = "动作类型 / Action type", example = "click")
    private String actionType;

    /**
     * 断言类型
     * Assert type
     */
    @Schema(description = "断言类型 / Assert type", example = "visible")
    private String assertType;

    /**
     * 断言结果: 1-通过 0-失败 NULL-无断言
     * Assert result: 1-passed, 0-failed, NULL-no assertion
     */
    @Schema(description = "断言结果: 1-通过 0-失败 NULL-无断言 / Assert result", example = "1")
    private Integer assertPassed;

    /**
     * 实际值
     * Actual value
     */
    @Schema(description = "实际值 / Actual value")
    private String actualValue;

    /**
     * 步骤耗时(毫秒)
     * Step duration (milliseconds)
     */
    @Schema(description = "步骤耗时(毫秒) / Step duration (ms)")
    private Long durationMs;

    /**
     * 错误信息
     * Error message
     */
    @Schema(description = "错误信息 / Error message")
    private String errorMessage;

    /**
     * 使用的定位策略: primary/backup
     * Locator strategy used: primary/backup
     */
    @Schema(description = "使用的定位策略: primary/backup / Locator strategy used", example = "primary")
    private String locatorUsed;
}
