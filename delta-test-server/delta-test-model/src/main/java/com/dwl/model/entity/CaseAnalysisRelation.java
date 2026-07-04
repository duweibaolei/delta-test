package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 用例与变更分析关联实体
 * Case-Analysis Association Entity
 * <p>
 * 对应表 case_analysis_relation，标记用例受变更分析影响的程度和处理状态。
 * Maps to table case_analysis_relation, marking the extent and resolution status of cases affected by change analysis.
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
@TableName("case_analysis_relation")
@Schema(description = "用例与变更分析关联 / Case-Analysis Association")
public class CaseAnalysisRelation extends BaseEntity {

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
}
