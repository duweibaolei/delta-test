package com.dwl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录请求 DTO
 * Login Request DTO
 *
 * @author ByDWL
 */
@Data
@Schema(description = "登录请求 / Login Request")
public class LoginDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 用户名 / Username */
    @Schema(description = "用户名 / Username", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空 / Username cannot be empty")
    private String username;

    /** 密码 / Password */
    @Schema(description = "密码 / Password", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空 / Password cannot be empty")
    private String password;
}
