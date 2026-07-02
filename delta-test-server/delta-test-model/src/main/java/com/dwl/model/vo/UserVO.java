package com.dwl.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户视图对象 VO
 * User View Object
 *
 * @author DeltaTest
 */
@Data
@Schema(description = "用户视图对象 / User View Object")
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 用户ID / User ID */
    @Schema(description = "用户ID / User ID", example = "1")
    private Long id;

    /** 用户名 / Username */
    @Schema(description = "用户名 / Username", example = "admin")
    private String username;

    /** 昵称 / Nickname */
    @Schema(description = "昵称 / Nickname", example = "管理员")
    private String nickname;

    /** 邮箱 / Email */
    @Schema(description = "邮箱 / Email", example = "admin@deltatest.com")
    private String email;

    /** 手机号 / Phone number */
    @Schema(description = "手机号 / Phone number", example = "13800138000")
    private String phone;

    /** 头像URL / Avatar URL */
    @Schema(description = "头像URL / Avatar URL")
    private String avatar;

    /** 状态 / Status */
    @Schema(description = "状态 / Status", example = "0")
    private Integer status;

    /** 最后登录时间 / Last login time */
    @Schema(description = "最后登录时间 / Last login time")
    private LocalDateTime lastLoginTime;

    /** 创建时间 / Creation time */
    @Schema(description = "创建时间 / Creation time")
    private LocalDateTime createTime;

    /** 角色列表 / Role list */
    @Schema(description = "角色列表 / Role list")
    private List<RoleVO> roles;
}
