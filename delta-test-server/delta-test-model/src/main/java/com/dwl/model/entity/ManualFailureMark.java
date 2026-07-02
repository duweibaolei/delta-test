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
 * 手动失败原因标记实体
 * Manual Failure Mark Entity
 * <p>
 * 对应表 manual_failure_mark，人工对失败用例标记原因分类。
 * Maps to table manual_failure_mark, manually marking failure reason categories for failed cases.
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
@TableName("manual_failure_mark")
@Schema(description = "手动失败原因标记 / Manual Failure Mark")
public class ManualFailureMark implements Serializable {

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
     * 失败原因: bug-业务缺陷 flaky-用例失效 env-环境问题
     * Failure reason: bug/flaky/env
     */
    @Schema(description = "失败原因: bug-业务缺陷 flaky-用例失效 env-环境问题 / Failure reason", example = "bug")
    private String failureReason;

    /**
     * 补充说明
     * Supplementary description
     */
    @Schema(description = "补充说明 / Supplementary description")
    private String description;

    /**
     * 标记人ID
     * Marker user ID
     */
    @Schema(description = "标记人ID / Marker user ID", example = "1")
    private Long markedBy;

    /**
     * 逻辑删除标志: 0-未删除 1-已删除
     * Logical delete flag: 0-not deleted, 1-deleted
     */
    @Schema(description = "逻辑删除标志: 0-未删除 1-已删除 / Logical delete flag: 0-not deleted, 1-deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 标记时间
     * Marked timestamp
     */
    @Schema(description = "标记时间 / Marked timestamp")
    private LocalDateTime markedAt;
}
