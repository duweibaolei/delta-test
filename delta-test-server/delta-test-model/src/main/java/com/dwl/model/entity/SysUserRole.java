package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户角色关联实体
 * User-Role Association Entity
 * <p>
 * 对应表 sys_user_role，用户与角色的多对多关联表。
 * Maps to table sys_user_role, many-to-many association between users and roles.
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
@TableName("sys_user_role")
@Schema(description = "用户角色关联 / User-Role Association")
public class SysUserRole extends BaseEntity {

    /**
     * 用户ID
     * User ID
     */
    @Schema(description = "用户ID / User ID", example = "1")
    private Long userId;

    /**
     * 角色ID
     * Role ID
     */
    @Schema(description = "角色ID / Role ID", example = "1")
    private Long roleId;
}
