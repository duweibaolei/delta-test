package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 系统权限实体
 * System Permission Entity
 * <p>
 * 对应表 sys_permission，定义菜单、按钮、接口等权限资源。
 * Maps to table sys_permission, defining permission resources such as menus, buttons, and APIs.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_permission")
@Schema(description = "系统权限 / System Permission")
public class SysPermission extends BaseEntity {

    /**
     * 权限编码
     * Permission code (unique identifier)
     */
    @Schema(description = "权限编码 / Permission code", example = "system:user:list")
    private String permissionCode;

    /**
     * 权限名称
     * Permission name
     */
    @Schema(description = "权限名称 / Permission name", example = "用户列表")
    private String permissionName;

    /**
     * 资源类型: menu-菜单 button-按钮 api-接口
     * Resource type: menu-menu, button-button, api-API endpoint
     */
    @Schema(description = "资源类型: menu-菜单 button-按钮 api-接口 / Resource type: menu/button/api", example = "menu")
    private String resourceType;

    /**
     * 父权限ID
     * Parent permission ID
     */
    @Schema(description = "父权限ID / Parent permission ID")
    private Long parentId;

    /**
     * 排序
     * Sort order
     */
    @Schema(description = "排序 / Sort order", example = "1")
    private Integer sortOrder;

    /**
     * 状态: 1-启用 0-禁用
     * Status: 1-enabled, 0-disabled
     */
    @Schema(description = "状态: 1-启用 0-禁用 / Status: 1-enabled, 0-disabled", example = "1")
    private Integer status;
}
