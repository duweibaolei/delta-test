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
 * 角色权限关联实体
 * Role-Permission Association Entity
 * <p>
 * 对应表 sys_role_permission，角色与权限的多对多关联表。
 * Maps to table sys_role_permission, many-to-many association between roles and permissions.
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
@TableName("sys_role_permission")
@Schema(description = "角色权限关联 / Role-Permission Association")
public class SysRolePermission implements Serializable {

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

    /**
     * 逻辑删除标志: 0-未删除 1-已删除
     * Logical delete flag: 0-not deleted, 1-deleted
     */
    @Schema(description = "逻辑删除标志: 0-未删除 1-已删除 / Logical delete flag: 0-not deleted, 1-deleted")
    @TableLogic
    private Integer isDeleted;
}
