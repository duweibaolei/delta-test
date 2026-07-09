package com.dwl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 创建角色 DTO
 * Create Role DTO
 *
 * @author ByDWL
 */
@Data
@Schema(description = "创建角色请求 / Create Role Request")
public class RoleCreateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 角色编码 / Role code */
    @Schema(description = "角色编码 / Role code", example = "TESTER", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色编码不能为空 / Role code cannot be empty")
    private String roleCode;

    /** 角色名称 / Role name */
    @Schema(description = "角色名称 / Role name", example = "测试人员", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色名称不能为空 / Role name cannot be empty")
    private String roleName;

    /** 角色描述 / Role description */
    @Schema(description = "角色描述 / Role description", example = "测试人员角色")
    private String roleDesc;

    /** 排序号 / Sort order */
    @Schema(description = "排序号 / Sort order", example = "2")
    private Integer sortOrder;

    /** 权限ID列表 / Permission ID list */
    @Schema(description = "权限ID列表 / Permission ID list", example = "[1, 2, 3]")
    private List<Long> permissionIds;
}
