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
 * 变更分析与提交关联实体
 * Change Analysis-Commit Association Entity
 * <p>
 * 对应表 change_analysis_commit，变更分析与Git提交记录的多对多关联。
 * Maps to table change_analysis_commit, many-to-many association between change analyses and git commits.
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
@TableName("change_analysis_commit")
@Schema(description = "变更分析与提交关联 / Change Analysis-Commit Association")
public class ChangeAnalysisCommit implements Serializable {

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

    /**
     * 逻辑删除标志: 0-未删除 1-已删除
     * Logical delete flag: 0-not deleted, 1-deleted
     */
    @Schema(description = "逻辑删除标志: 0-未删除 1-已删除 / Logical delete flag: 0-not deleted, 1-deleted")
    @TableLogic
    private Integer isDeleted;
}
