package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用例步骤实体
 * Case Step Entity
 * <p>
 * 对应表 case_step，定义测试用例的每一步操作和断言。
 * Maps to table case_step, defining each operation and assertion step of a test case.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("case_step")
@Schema(description = "用例步骤 / Case Step")
public class CaseStep extends BaseEntity {

    /**
     * 用例ID
     * Case ID
     */
    @Schema(description = "用例ID / Case ID", example = "1")
    private Long caseId;

    /**
     * 步骤顺序(从1开始)
     * Step order (starts from 1)
     */
    @Schema(description = "步骤顺序(从1开始) / Step order (starts from 1)", example = "1")
    private Integer stepOrder;

    /**
     * 操作元素ID
     * Operation element ID
     */
    @Schema(description = "操作元素ID / Operation element ID")
    private Long elementId;

    /**
     * 动作类型: click/fill/select/waitFor/hover/scroll/navigate/assert
     * Action type: click/fill/select/waitFor/hover/scroll/navigate/assert
     */
    @Schema(description = "动作类型 / Action type", example = "click")
    private String actionType;

    /**
     * 输入值(动作参数)
     * Action value (action parameter)
     */
    @Schema(description = "输入值(动作参数) / Action value (action parameter)")
    private String actionValue;

    /**
     * 断言类型: url_contains/visible/text_match/value_contains/attribute
     * Assert type: url_contains/visible/text_match/value_contains/attribute
     */
    @Schema(description = "断言类型 / Assert type", example = "visible")
    private String assertType;

    /**
     * 断言期望值
     * Assert expected value
     */
    @Schema(description = "断言期望值 / Assert expected value")
    private String assertValue;

    /**
     * 等待超时(毫秒)
     * Wait timeout (milliseconds)
     */
    @Schema(description = "等待超时(毫秒) / Wait timeout (ms)", example = "5000")
    private Integer waitTimeout;

    /**
     * 步骤描述
     * Step description
     */
    @Schema(description = "步骤描述 / Step description")
    private String description;
}
