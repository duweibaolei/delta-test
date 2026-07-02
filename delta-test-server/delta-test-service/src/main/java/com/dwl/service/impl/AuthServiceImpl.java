package com.dwl.service.impl;

import com.dwl.common.exception.BusinessException;
import com.dwl.common.result.ErrorCode;
import com.dwl.common.utils.JwtUtil;
import com.dwl.model.dto.LoginDTO;
import com.dwl.model.entity.SysUser;
import com.dwl.model.vo.LoginVO;
import com.dwl.service.AuthService;
import com.dwl.service.SysUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 认证 服务实现类
 * Authentication Service Implementation
 * <p>
 * 复用 {@link JwtUtil} 进行令牌生成与解析，避免密钥配置重复。
 * Reuses {@link JwtUtil} for token generation and parsing to avoid duplicate key configuration.
 * </p>
 *
 * @author DeltaTest
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /** 用户服务 / User service */
    private final SysUserService userService;

    /** 密码编码器 / Password encoder */
    private final PasswordEncoder passwordEncoder;

    /** JWT工具类 / JWT utility */
    private final JwtUtil jwtUtil;

    /** JWT过期时间（毫秒）/ JWT expiration time (ms) */
    @Value("${jwt.expiration:86400000}")
    private Long jwtExpiration;

    /**
     * 用户登录
     * User login
     *
     * @param dto 登录请求 / Login request
     * @return 登录响应 / Login response
     */
    @Override
    public LoginVO login(LoginDTO dto) {
        // 查询用户 / Query user
        SysUser user = userService.getByUsername(dto.getUsername());
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户名或密码错误 / Invalid username or password");
        }

        // 验证密码 / Verify password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR, "用户名或密码错误 / Invalid username or password");
        }

        // 检查用户状态 / Check user status
        if (user.getStatus() != null && user.getStatus() == 1) {
            throw new BusinessException(ErrorCode.USER_DISABLED);
        }

        // 生成Token / Generate token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        // 更新最后登录时间 / Update last login time
        userService.updateLastLoginTime(user.getId());

        // 构建响应 / Build response
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setTokenType("Bearer");
        loginVO.setExpiresIn(jwtExpiration / 1000);
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setNickname(user.getRealName());

        log.info("用户登录成功: {} / User login successful: {}", user.getUsername());
        return loginVO;
    }

    /**
     * 刷新Token
     * Refresh token
     *
     * @param token 旧Token / Old token
     * @return 新Token / New token
     */
    @Override
    public String refreshToken(String token) {
        try {
            Claims claims = jwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);
            String username = claims.getSubject();

            // 检查用户是否仍然有效 / Check if user is still valid
            SysUser user = userService.getByUsername(username);
            if (user == null || user.getStatus() == 1) {
                throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户已禁用 / User is disabled");
            }

            return jwtUtil.generateToken(userId, username);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }
    }

    /**
     * 退出登录
     * Logout
     */
    @Override
    public void logout() {
        // JWT无状态，无需服务端操作 / JWT is stateless, no server-side action needed
        // 如需实现Token黑名单，可在此添加 / If token blacklist is needed, add here
        log.info("用户退出登录 / User logged out");
    }
}
