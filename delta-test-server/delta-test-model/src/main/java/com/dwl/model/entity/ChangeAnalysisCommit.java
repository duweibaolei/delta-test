package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 变更分析与提交关联实体
 * Change Analysis-Commit Association Entity
 * <p>
 * 对应表 change_analysis_commit，变更分析与Git提交记录的多对多关联。
 * Maps to table change_analysis_commit, many-to-many association between change analyses and git commits.
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
@TableName("change_analysis_commit")
@Schema(description = "变更分析与提交关联 / Change Analysis-Commit Association")
public class ChangeAnalysisCommit extends BaseEntity {

    /**
     * 分析ID
     * Analysis ID
     */
    @Schema(description = "分析ID / Analysis ID", example = "1")
    private Long analysisId;

    /**
     * 提交记录ID
     * Commit record ID
     */
    @Schema(description = "提交记录ID / Commit record ID", example = "1")
    private Long commitId;
}
