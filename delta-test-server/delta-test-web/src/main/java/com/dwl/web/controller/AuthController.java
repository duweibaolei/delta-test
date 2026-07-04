package com.dwl.web.controller;

import com.dwl.common.result.R;
import com.dwl.model.dto.LoginDTO;
import com.dwl.model.vo.LoginVO;
import com.dwl.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证管理 控制器
 * Authentication Management Controller
 *
 * @author DeltaTest
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "认证管理 / Authentication Management")
public class AuthController {

    /** 认证服务 / Authentication service */
    private final AuthService authService;

    /**
     * 用户登录
     * User login
     *
     * @param dto 登录请求 / Login request
     * @return 登录响应 / Login response
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录 / User login")
    public R<LoginVO> login(@Valid @RequestBody LoginDTO dto) {
        return R.ok(authService.login(dto));
    }

    /**
     * 刷新Token
     * Refresh token
     *
     * @param token 旧Token / Old token
     * @return 新Token / New token
     */
    @PostMapping("/refresh")
    @Operation(summary = "刷新Token", description = "刷新Token / Refresh token")
    public R<String> refreshToken(
            @Parameter(description = "旧Token / Old token", required = true)
            @RequestHeader("Authorization") String token) {
        // 去除Bearer前缀 / Remove Bearer prefix
        String rawToken = token.replace("Bearer ", "");
        return R.ok(authService.refreshToken(rawToken));
    }

    /**
     * 退出登录
     * Logout
     *
     * @return 操作结果 / Operation result
     */
    @PostMapping("/logout")
    @Operation(summary = "退出登录", description = "退出登录 / Logout")
    public R<Void> logout() {
        authService.logout();
        return R.ok();
    }
}
