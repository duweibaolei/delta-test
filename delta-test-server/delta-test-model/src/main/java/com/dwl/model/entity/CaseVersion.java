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
 * 用例版本历史实体
 * Case Version History Entity
 * <p>
 * 对应表 case_version，保存测试用例每次修改的快照。
 * Maps to table case_version, saving snapshots of test case modifications.
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
@TableName("case_version")
@Schema(description = "用例版本历史 / Case Version History")
public class CaseVersion implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 版本ID
     * Version ID
     */
    @Schema(description = "版本ID / Version ID", example = "1")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用例ID
     * Case ID
     */
    @Schema(description = "用例ID / Case ID", example = "1")
    private Long caseId;

    /**
     * 版本号
     * Version number
     */
    @Schema(description = "版本号 / Version number", example = "1")
    private Integer version;

    /**
     * 用例快照(含步骤完整数据)，JSON格式内容以String存储
     * Case snapshot (including complete step data), stored as String for JSON content
     */
    @Schema(description = "用例快照(JSON格式) / Case snapshot (JSON format)")
    private String snapshotJson;

    /**
     * 变更摘要
     * Change summary
     */
    @Schema(description = "变更摘要 / Change summary")
    private String changeSummary;

    /**
     * 修改人ID
     * Modifier user ID
     */
    @Schema(description = "修改人ID / Modifier user ID")
    private Long modifiedBy;

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
