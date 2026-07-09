package com.dwl.web.controller;

import com.dwl.common.result.PageResult;
import com.dwl.common.result.R;
import com.dwl.model.dto.ResetPasswordDTO;
import com.dwl.model.dto.UserCreateDTO;
import com.dwl.model.dto.UserStatusDTO;
import com.dwl.model.dto.UserUpdateDTO;
import com.dwl.model.vo.UserVO;
import com.dwl.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理 控制器
 * User Management Controller
 *
 * @author ByDWL
 */
@RestController
@RequestMapping("/api/system/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户管理 / User Management")
public class SysUserController {

    /** 用户服务 / User service */
    private final SysUserService userService;

    /**
     * 分页查询用户
     * Page query users
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户", description = "分页查询用户 / Page query users")
    public R<PageResult<UserVO>> pageUsers(
            @Parameter(description = "用户名 / Username") @RequestParam(required = false) String username,
            @Parameter(description = "状态 / Status") @RequestParam(required = false) Integer status,
            @Parameter(description = "页码 / Page number") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页大小 / Page size") @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(userService.pageUsers(username, status, pageNum, pageSize));
    }

    /**
     * 查询用户详情
     * Get user detail
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询用户详情", description = "查询用户详情 / Get user detail")
    public R<UserVO> getUserDetail(
            @Parameter(description = "用户ID / User ID", required = true) @PathVariable Long id) {
        return R.ok(userService.getUserDetail(id));
    }

    /**
     * 创建用户
     * Create user
     */
    @PostMapping
    @Operation(summary = "创建用户", description = "创建用户 / Create user")
    public R<Long> createUser(@Valid @RequestBody UserCreateDTO dto) {
        return R.ok(userService.createUser(dto));
    }

    /**
     * 更新用户
     * Update user
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新用户", description = "更新用户 / Update user")
    public R<Void> updateUser(
            @Parameter(description = "用户ID / User ID", required = true) @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto) {
        userService.updateUser(id, dto);
        return R.ok();
    }

    /**
     * 删除用户
     * Delete user
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "删除用户 / Delete user")
    public R<Void> deleteUser(
            @Parameter(description = "用户ID / User ID", required = true) @PathVariable Long id) {
        userService.deleteUser(id);
        return R.ok();
    }

    /**
     * 重置密码
     * Reset password
     */
    @PutMapping("/{id}/reset-password")
    @Operation(summary = "重置密码", description = "重置密码 / Reset password")
    public R<Void> resetPassword(
            @Parameter(description = "用户ID / User ID", required = true) @PathVariable Long id,
            @Valid @RequestBody ResetPasswordDTO dto) {
        userService.resetPassword(id, dto.getNewPassword());
        return R.ok();
    }

    /**
     * 更新用户状态
     * Update user status
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新用户状态", description = "更新用户状态 / Update user status")
    public R<Void> updateStatus(
            @Parameter(description = "用户ID / User ID", required = true) @PathVariable Long id,
            @Valid @RequestBody UserStatusDTO dto) {
        userService.updateStatus(id, dto.getStatus());
        return R.ok();
    }
}
