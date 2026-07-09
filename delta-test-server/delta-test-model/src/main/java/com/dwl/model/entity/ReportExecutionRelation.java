package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 报告与执行结果关联实体
 * Report-Execution Association Entity
 * <p>
 * 对应表 report_execution_relation，报告与执行记录的多对多关联。
 * Maps to table report_execution_relation, many-to-many association between reports and execution records.
 * 继承 BaseEntity，包含公共审计字段。
 * Extends BaseEntity, including common audit fields.
 * </p>
 *
 * @author ByDWL
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("report_execution_relation")
@Schema(description = "报告与执行结果关联 / Report-Execution Association")
public class ReportExecutionRelation extends BaseEntity {

    /**
     * 报告ID
     * Report ID
     */
    @Schema(description = "报告ID / Report ID", example = "1")
    private Long reportId;

    /**
     * 执行记录ID
     * Execution record ID
     */
    @Schema(description = "执行记录ID / Execution record ID", example = "1")
    private Long executionId;
}
