package com.dwl.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色视图对象 VO
 * Role View Object
 *
 * @author ByDWL
 */
@Data
@Schema(description = "角色视图对象 / Role View Object")
public class RoleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 角色ID / Role ID */
    @Schema(description = "角色ID / Role ID", example = "1")
    private Long id;

    /** 角色编码 / Role code */
    @Schema(description = "角色编码 / Role code", example = "ADMIN")
    private String roleCode;

    /** 角色名称 / Role name */
    @Schema(description = "角色名称 / Role name", example = "管理员")
    private String roleName;

    /** 角色描述 / Role description */
    @Schema(description = "角色描述 / Role description")
    private String roleDesc;

    /** 排序号 / Sort order */
    @Schema(description = "排序号 / Sort order", example = "1")
    private Integer sortOrder;

    /** 状态 / Status */
    @Schema(description = "状态 / Status", example = "0")
    private Integer status;

    /** 创建时间 / Creation time */
    @Schema(description = "创建时间 / Creation time")
    private LocalDateTime createTime;

    /** 权限列表 / Permission list */
    @Schema(description = "权限列表 / Permission list")
    private List<PermissionVO> permissions;
}
