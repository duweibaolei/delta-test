package com.dwl.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录响应 VO
 * Login Response VO
 *
 * @author DeltaTest
 */
@Data
@Schema(description = "登录响应 / Login Response")
public class LoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌 / Access token
     */
    @Schema(description = "访问令牌 / Access token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    /**
     * 令牌类型 / Token type
     */
    @Schema(description = "令牌类型 / Token type", example = "Bearer")
    private String tokenType = "Bearer";

    /**
     * 过期时间（秒）/ Expires in (seconds)
     */
    @Schema(description = "过期时间（秒）/ Expires in (seconds)", example = "86400")
    private Long expiresIn;

    /**
     * 用户ID / User ID
     */
    @Schema(description = "用户ID / User ID", example = "1")
    private Long userId;

    /**
     * 用户名 / Username
     */
    @Schema(description = "用户名 / Username", example = "admin")
    private String username;

    /**
     * 昵称 / Nickname
     */
    @Schema(description = "昵称 / Nickname", example = "管理员")
    private String nickname;
}
