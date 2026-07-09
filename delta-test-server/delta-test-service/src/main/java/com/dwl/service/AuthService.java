package com.dwl.service;

import com.dwl.model.dto.LoginDTO;
import com.dwl.model.vo.LoginVO;

/**
 * 认证 服务接口
 * Authentication Service Interface
 *
 * @author ByDWL
 */
public interface AuthService {

    /**
     * 用户登录
     * User login
     *
     * @param dto 登录请求 / Login request
     * @return 登录响应 / Login response
     */
    LoginVO login(LoginDTO dto);

    /**
     * 刷新Token
     * Refresh token
     *
     * @param token 旧Token / Old token
     * @return 新Token / New token
     */
    String refreshToken(String token);

    /**
     * 退出登录
     * Logout
     */
    void logout();
}
