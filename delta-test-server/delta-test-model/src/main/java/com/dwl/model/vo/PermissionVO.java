package com.dwl.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 权限视图对象 VO（树形结构）
 * Permission View Object (Tree Structure)
 *
 * @author DeltaTest
 */
@Data
@Schema(description = "权限视图对象 / Permission View Object")
public class PermissionVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 权限ID / Permission ID */
    @Schema(description = "权限ID / Permission ID", example = "1")
    private Long id;

    /** 父级权限ID / Parent permission ID */
    @Schema(description = "父级权限ID / Parent permission ID", example = "0")
    private Long parentId;

    /** 权限名称 / Permission name */
    @Schema(description = "权限名称 / Permission name", example = "用户管理")
    private String permName;

    /** 权限标识 / Permission identifier */
    @Schema(description = "权限标识 / Permission identifier", example = "system:user:list")
    private String permCode;

    /** 权限类型（1-菜单，2-按钮）/ Permission type (1-menu, 2-button) */
    @Schema(description = "权限类型 / Permission type", example = "1")
    private Integer permType;

    /** 路由路径 / Route path */
    @Schema(description = "路由路径 / Route path", example = "/system/user")
    private String path;

    /** 图标 / Icon */
    @Schema(description = "图标 / Icon", example = "user")
    private String icon;

    /** 排序号 / Sort order */
    @Schema(description = "排序号 / Sort order", example = "1")
    private Integer sortOrder;

    /** 状态 / Status */
    @Schema(description = "状态 / Status", example = "0")
    private Integer status;

    /** 子权限列表 / Child permission list */
    @Schema(description = "子权限列表 / Child permission list")
    private List<PermissionVO> children;
}
