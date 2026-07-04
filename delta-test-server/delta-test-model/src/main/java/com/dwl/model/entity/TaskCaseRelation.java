package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 任务与用例关联实体
 * Task-Case Association Entity
 * <p>
 * 对应表 task_case_relation，测试任务与用例的多对多关联。
 * Maps to table task_case_relation, many-to-many association between test tasks and cases.
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
@TableName("task_case_relation")
@Schema(description = "任务与用例关联 / Task-Case Association")
public class TaskCaseRelation extends BaseEntity {

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
}
