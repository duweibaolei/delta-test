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
 * 用例与变更分析关联实体
 * Case-Analysis Association Entity
 * <p>
 * 对应表 case_analysis_relation，标记用例受变更分析影响的程度和处理状态。
 * Maps to table case_analysis_relation, marking the extent and resolution status of cases affected by change analysis.
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
@TableName("case_analysis_relation")
@Schema(description = "用例与变更分析关联 / Case-Analysis Association")
public class CaseAnalysisRelation implements Serializable {

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
     * 用例ID
     * Case ID
     */
    @Schema(description = "用例ID / Case ID", example = "1")
    private Long caseId;

    /**
     * 分析ID
     * Analysis ID
     */
    @Schema(description = "分析ID / Analysis ID", example = "1")
    private Long analysisId;

    /**
     * 影响类型: impacted-受影响 need_update-需更新断言 need_new-需新增
     * Affected type: impacted/need_update/need_new
     */
    @Schema(description = "影响类型: impacted/need_update/need_new / Affected type", example = "impacted")
    private String affectedType;

    /**
     * 是否已处理: 1-是 0-否
     * Whether resolved: 1-yes, 0-no
     */
    @Schema(description = "是否已处理: 1-是 0-否 / Whether resolved", example = "0")
    private Integer resolved;

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
