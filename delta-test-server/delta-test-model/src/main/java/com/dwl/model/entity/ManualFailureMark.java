package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 手动失败原因标记实体
 * Manual Failure Mark Entity
 * <p>
 * 对应表 manual_failure_mark，人工对失败用例标记原因分类。
 * Maps to table manual_failure_mark, manually marking failure reason categories for failed cases.
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
@TableName("manual_failure_mark")
@Schema(description = "手动失败原因标记 / Manual Failure Mark")
public class ManualFailureMark extends BaseEntity {

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
     * 标记时间
     * Marked timestamp
     */
    @Schema(description = "标记时间 / Marked timestamp")
    private LocalDateTime markedAt;
}
