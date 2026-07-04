package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 影响范围实体
 * Affected Scope Entity
 * <p>
 * 对应表 affected_scope，记录变更分析识别出的影响范围。
 * Maps to table affected_scope, recording affected scopes identified by change analysis.
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
@TableName("affected_scope")
@Schema(description = "影响范围 / Affected Scope")
public class AffectedScope extends BaseEntity {

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
}
