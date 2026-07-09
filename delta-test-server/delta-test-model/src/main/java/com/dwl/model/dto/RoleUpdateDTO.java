package com.dwl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 更新角色 DTO
 * Update Role DTO
 *
 * @author ByDWL
 */
@Data
@Schema(description = "更新角色请求 / Update Role Request")
public class RoleUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 角色名称 / Role name */
    @Schema(description = "角色名称 / Role name", example = "测试人员")
    private String roleName;

    /** 角色描述 / Role description */
    @Schema(description = "角色描述 / Role description", example = "测试人员角色")
    private String roleDesc;

    /** 排序号 / Sort order */
    @Schema(description = "排序号 / Sort order", example = "2")
    private Integer sortOrder;

    /** 状态 / Status */
    @Schema(description = "状态 / Status", example = "0")
    private Integer status;

    /** 权限ID列表 / Permission ID list */
    @Schema(description = "权限ID列表 / Permission ID list", example = "[1, 2, 3]")
    private List<Long> permissionIds;
}
