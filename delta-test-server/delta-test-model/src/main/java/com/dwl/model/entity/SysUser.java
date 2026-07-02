package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 系统用户实体
 * System User Entity
 * <p>
 * 对应表 sys_user，存储平台用户的基本信息和认证数据。
 * Maps to table sys_user, storing basic user information and authentication data.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
@Schema(description = "系统用户 / System User")
public class SysUser extends BaseEntity {

    /**
     * 用户名
     * Username (unique login identifier)
     */
    @Schema(description = "用户名 / Username", example = "admin")
    private String username;

    /**
     * 密码(加密存储)
     * Password (encrypted)
     */
    @Schema(description = "密码(加密存储) / Password (encrypted)")
    private String password;

    /**
     * 真实姓名
     * Real name
     */
    @Schema(description = "真实姓名 / Real name", example = "张三")
    private String realName;

    /**
     * 邮箱
     * Email address
     */
    @Schema(description = "邮箱 / Email address", example = "admin@deltatest.com")
    private String email;

    /**
     * 头像URL
     * Avatar URL
     */
    @Schema(description = "头像URL / Avatar URL")
    private String avatar;

    /**
     * 状态: 1-启用 0-禁用
     * Status: 1-enabled, 0-disabled
     */
    @Schema(description = "状态: 1-启用 0-禁用 / Status: 1-enabled, 0-disabled", example = "1")
    private Integer status;

    /**
     * 最后登录时间
     * Last login timestamp
     */
    @Schema(description = "最后登录时间 / Last login timestamp")
    private LocalDateTime lastLoginTime;
}
