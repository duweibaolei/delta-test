package com.dwl.web.controller;

import com.dwl.common.result.R;
import com.dwl.model.vo.PermissionVO;
import com.dwl.service.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限管理 控制器
 * Permission Management Controller
 *
 * @author DeltaTest
 */
@RestController
@RequestMapping("/api/system/permissions")
@RequiredArgsConstructor
@Tag(name = "权限管理", description = "权限管理 / Permission Management")
public class SysPermissionController {

    /** 权限服务 / Permission service */
    private final SysPermissionService permissionService;

    /**
     * 查询权限树
     * Query permission tree
     *
     * @return 权限树 / Permission tree
     */
    @GetMapping("/tree")
    @Operation(summary = "查询权限树", description = "查询权限树 / Query permission tree")
    public R<List<PermissionVO>> listPermissionTree() {
        return R.ok(permissionService.listPermissions());
    }
}
