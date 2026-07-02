package com.dwl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 更新用户 DTO
 * Update User DTO
 *
 * @author DeltaTest
 */
@Data
@Schema(description = "更新用户请求 / Update User Request")
public class UserUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 昵称 / Nickname */
    @Schema(description = "昵称 / Nickname", example = "测试用户")
    private String nickname;

    /** 邮箱 / Email */
    @Schema(description = "邮箱 / Email", example = "test@deltatest.com")
    private String email;

    /** 手机号 / Phone number */
    @Schema(description = "手机号 / Phone number", example = "13800138000")
    private String phone;

    /** 头像URL / Avatar URL */
    @Schema(description = "头像URL / Avatar URL")
    private String avatar;

    /** 状态（0-正常，1-禁用）/ Status (0-normal, 1-disabled) */
    @Schema(description = "状态 / Status", example = "0")
    private Integer status;

    /** 角色ID列表 / Role ID list */
    @Schema(description = "角色ID列表 / Role ID list", example = "[1, 2]")
    private List<Long> roleIds;
}
