package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 缺陷记录实体
 * Defect Record Entity
 * <p>
 * 对应表 defect_record，管理测试过程中发现的缺陷生命周期。
 * Maps to table defect_record, managing the lifecycle of defects found during testing.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("defect_record")
@Schema(description = "缺陷记录 / Defect Record")
public class DefectRecord extends BaseEntity {

    /**
     * 缺陷编号: BUG-NNNN
     * Defect number: BUG-NNNN
     */
    @Schema(description = "缺陷编号: BUG-NNNN / Defect number", example = "BUG-0001")
    private String defectNo;

    /**
     * 缺陷标题
     * Defect title
     */
    @Schema(description = "缺陷标题 / Defect title", example = "登录页面提交按钮无响应")
    private String defectTitle;

    /**
     * 严重程度: critical/major/minor
     * Severity: critical/major/minor
     */
    @Schema(description = "严重程度: critical/major/minor / Severity", example = "major")
    private String severity;

    /**
     * 缺陷描述
     * Defect description
     */
    @Schema(description = "缺陷描述 / Defect description")
    private String description;

    /**
     * 关联执行记录ID
     * Associated execution record ID
     */
    @Schema(description = "关联执行记录ID / Associated execution record ID")
    private Long executionId;

    /**
     * 关联用例ID
     * Associated case ID
     */
    @Schema(description = "关联用例ID / Associated case ID")
    private Long caseId;

    /**
     * 关联提交ID
     * Associated commit ID
     */
    @Schema(description = "关联提交ID / Associated commit ID")
    private Long commitId;

    /**
     * 关联报告ID
     * Associated report ID
     */
    @Schema(description = "关联报告ID / Associated report ID")
    private Long reportId;

    /**
     * 状态: open/resolved/closed
     * Status: open/resolved/closed
     */
    @Schema(description = "状态: open/resolved/closed / Status", example = "open")
    private String status;

    /**
     * 录入人ID
     * Creator user ID
     */
    @Schema(description = "录入人ID / Creator user ID", example = "1")
    private Long createdBy;

    /**
     * 解决人ID
     * Resolver user ID
     */
    @Schema(description = "解决人ID / Resolver user ID")
    private Long resolvedBy;

    /**
     * 解决时间
     * Resolved timestamp
     */
    @Schema(description = "解决时间 / Resolved timestamp")
    private LocalDateTime resolvedAt;
}
