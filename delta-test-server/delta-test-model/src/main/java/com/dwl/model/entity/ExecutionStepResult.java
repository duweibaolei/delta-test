package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 执行步骤结果实体
 * Execution Step Result Entity
 * <p>
 * 对应表 execution_step_result，记录每次执行中每个步骤的详细结果。
 * Maps to table execution_step_result, recording detailed results for each step in an execution.
 * 此表无 updated_at 字段，不继承 BaseEntity，仅包含 createdAt。
 * This table has no updated_at column and does not extend BaseEntity, only contains createdAt.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("execution_step_result")
@Schema(description = "执行步骤结果 / Execution Step Result")
public class ExecutionStepResult implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     * Primary key ID
     */
    @Schema(description = "主键ID / Primary key ID", example = "1")
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /**
     * 逻辑删除标志: 0-未删除 1-已删除
     * Logical delete flag: 0-not deleted, 1-deleted
     */
    @Schema(description = "逻辑删除标志: 0-未删除 1-已删除 / Logical delete flag: 0-not deleted, 1-deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 创建时间
     * Created timestamp
     */
    @Schema(description = "创建时间 / Created timestamp")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
