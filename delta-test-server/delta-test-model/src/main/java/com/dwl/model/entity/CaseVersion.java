package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 用例版本历史实体
 * Case Version History Entity
 * <p>
 * 对应表 case_version，保存测试用例每次修改的快照。
 * Maps to table case_version, saving snapshots of test case modifications.
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
@TableName("case_version")
@Schema(description = "用例版本历史 / Case Version History")
public class CaseVersion extends BaseEntity {

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
}
