package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 报告与执行结果关联实体
 * Report-Execution Association Entity
 * <p>
 * 对应表 report_execution_relation，报告与执行记录的多对多关联。
 * Maps to table report_execution_relation, many-to-many association between reports and execution records.
 * 此表无 updated_at 字段，不继承 BaseEntity。
 * This table has no updated_at column and does not extend BaseEntity.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("report_execution_relation")
@Schema(description = "报告与执行结果关联 / Report-Execution Association")
public class ReportExecutionRelation implements Serializable {

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

    /**
     * 逻辑删除标志: 0-未删除 1-已删除
     * Logical delete flag: 0-not deleted, 1-deleted
     */
    @Schema(description = "逻辑删除标志: 0-未删除 1-已删除 / Logical delete flag: 0-not deleted, 1-deleted")
    @TableLogic
    private Integer isDeleted;
}
