package com.dwl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 创建用户 DTO
 * Create User DTO
 *
 * @author ByDWL
 */
@Data
@Schema(description = "创建用户请求 / Create User Request")
public class UserCreateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 用户名 / Username */
    @Schema(description = "用户名 / Username", example = "testuser", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空 / Username cannot be empty")
    private String username;

    /** 密码 / Password */
    @Schema(description = "密码 / Password", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空 / Password cannot be empty")
    private String password;

    /** 昵称 / Nickname */
    @Schema(description = "昵称 / Nickname", example = "测试用户")
    private String nickname;

    /** 邮箱 / Email */
    @Schema(description = "邮箱 / Email address", example = "test@deltatest.com")
    private String email;

    /** 角色ID列表 / Role ID list */
    @Schema(description = "角色ID列表 / Role ID list", example = "[1, 2]")
    private List<Long> roleIds;
}
