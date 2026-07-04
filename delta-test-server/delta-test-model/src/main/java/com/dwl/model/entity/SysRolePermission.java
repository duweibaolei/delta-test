package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 角色权限关联实体
 * Role-Permission Association Entity
 * <p>
 * 对应表 sys_role_permission，角色与权限的多对多关联表。
 * Maps to table sys_role_permission, many-to-many association between roles and permissions.
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
@TableName("sys_role_permission")
@Schema(description = "角色权限关联 / Role-Permission Association")
public class SysRolePermission extends BaseEntity {

    /**
     * 角色ID
     * Role ID
     */
    @Schema(description = "角色ID / Role ID", example = "1")
    private Long roleId;

    /**
     * 权限ID
     * Permission ID
     */
    @Schema(description = "权限ID / Permission ID", example = "1")
    private Long permissionId;
}
