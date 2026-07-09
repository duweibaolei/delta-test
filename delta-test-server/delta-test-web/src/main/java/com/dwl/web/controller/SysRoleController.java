package com.dwl.web.controller;

import com.dwl.common.result.R;
import com.dwl.model.dto.RoleCreateDTO;
import com.dwl.model.dto.RoleUpdateDTO;
import com.dwl.model.vo.RoleVO;
import com.dwl.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理 控制器
 * Role Management Controller
 *
 * @author ByDWL
 */
@RestController
@RequestMapping("/api/system/roles")
@RequiredArgsConstructor
@Tag(name = "角色管理", description = "角色管理 / Role Management")
public class SysRoleController {

    private final SysRoleService roleService;

    @GetMapping
    @Operation(summary = "查询角色列表", description = "查询角色列表 / List roles")
    public R<List<RoleVO>> listRoles() {
        return R.ok(roleService.listRoles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询角色详情", description = "查询角色详情 / Get role detail")
    public R<RoleVO> getRoleDetail(
            @Parameter(description = "角色ID / Role ID", required = true) @PathVariable Long id) {
        return R.ok(roleService.getRoleDetail(id));
    }

    @PostMapping
    @Operation(summary = "创建角色", description = "创建角色 / Create role")
    public R<Long> createRole(@Valid @RequestBody RoleCreateDTO dto) {
        return R.ok(roleService.createRole(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新角色", description = "更新角色 / Update role")
    public R<Void> updateRole(
            @Parameter(description = "角色ID / Role ID", required = true) @PathVariable Long id,
            @Valid @RequestBody RoleUpdateDTO dto) {
        roleService.updateRole(id, dto);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色", description = "删除角色 / Delete role")
    public R<Void> deleteRole(
            @Parameter(description = "角色ID / Role ID", required = true) @PathVariable Long id) {
        roleService.deleteRole(id);
        return R.ok();
    }
}
