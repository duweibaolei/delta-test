package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Git仓库配置实体
 * Git Repository Configuration Entity
 * <p>
 * 对应表 sys_repository，管理Git仓库的连接、认证和Webhook配置。
 * Maps to table sys_repository, managing Git repository connections, authentication, and webhook configuration.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_repository")
@Schema(description = "Git仓库配置 / Git Repository Configuration")
public class SysRepository extends BaseEntity {

    /**
     * 仓库名称
     * Repository name
     */
    @Schema(description = "仓库名称 / Repository name", example = "DeltaTest-Server")
    private String repoName;

    /**
     * 仓库地址
     * Repository URL
     */
    @Schema(description = "仓库地址 / Repository URL", example = "git@github.com:example/DeltaTest-Server.git")
    private String repoUrl;

    /**
     * 默认分支
     * Default branch
     */
    @Schema(description = "默认分支 / Default branch", example = "main")
    private String branchDefault;

    /**
     * 认证方式: ssh/token/password
     * Credential type: ssh/token/password
     */
    @Schema(description = "认证方式: ssh/token/password / Credential type", example = "ssh")
    private String credentialType;

    /**
     * 认证密钥(加密存储)
     * Credential key (encrypted)
     */
    @Schema(description = "认证密钥(加密存储) / Credential key (encrypted)")
    private String credentialKey;

    /**
     * Webhook回调地址
     * Webhook callback URL
     */
    @Schema(description = "Webhook回调地址 / Webhook callback URL")
    private String webhookUrl;

    /**
     * Webhook签名密钥
     * Webhook secret key
     */
    @Schema(description = "Webhook签名密钥 / Webhook secret key")
    private String webhookSecret;

    /**
     * 状态: 1-启用 0-禁用
     * Status: 1-enabled, 0-disabled
     */
    @Schema(description = "状态: 1-启用 0-禁用 / Status: 1-enabled, 0-disabled", example = "1")
    private Integer status;
}
