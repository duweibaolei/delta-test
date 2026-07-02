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
 * 影响范围实体
 * Affected Scope Entity
 * <p>
 * 对应表 affected_scope，记录变更分析识别出的影响范围。
 * Maps to table affected_scope, recording affected scopes identified by change analysis.
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
@TableName("affected_scope")
@Schema(description = "影响范围 / Affected Scope")
public class AffectedScope implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 影响范围ID
     * Affected scope ID
     */
    @Schema(description = "影响范围ID / Affected scope ID", example = "1")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属分析ID
     * Analysis ID
     */
    @Schema(description = "所属分析ID / Analysis ID", example = "1")
    private Long analysisId;

    /**
     * 范围类型: frontend_page/frontend_component/backend_api/backend_service/database_table
     * Scope type: frontend_page/frontend_component/backend_api/backend_service/database_table
     */
    @Schema(description = "范围类型 / Scope type", example = "backend_api")
    private String scopeType;

    /**
     * 范围名称
     * Scope name
     */
    @Schema(description = "范围名称 / Scope name", example = "用户登录接口")
    private String scopeName;

    /**
     * 范围路径/标识
     * Scope path/identifier
     */
    @Schema(description = "范围路径/标识 / Scope path or identifier", example = "/api/auth/login")
    private String scopePath;

    /**
     * 是否选入回归范围: 1-是 0-否
     * Selected for regression: 1-yes, 0-no
     */
    @Schema(description = "是否选入回归范围: 1-是 0-否 / Selected for regression", example = "1")
    private Integer selectedForRegression;

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
