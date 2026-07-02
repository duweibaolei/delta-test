package com.dwl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 创建仓库 DTO
 * Create Repository DTO
 *
 * @author DeltaTest
 */
@Data
@Schema(description = "创建仓库请求 / Create Repository Request")
public class RepositoryCreateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 仓库名称 / Repository name */
    @Schema(description = "仓库名称 / Repository name", example = "DeltaTest", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "仓库名称不能为空 / Repository name cannot be empty")
    private String repoName;

    /** 仓库URL / Repository URL */
    @Schema(description = "仓库URL / Repository URL", example = "https://github.com/org/DeltaTest.git", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "仓库URL不能为空 / Repository URL cannot be empty")
    private String repoUrl;

    /** 默认分支 / Default branch */
    @Schema(description = "默认分支 / Default branch", example = "main")
    private String defaultBranch;

    /** 凭证类型 / Credential type */
    @Schema(description = "凭证类型 / Credential type", example = "http_token")
    private String credentialType;

    /** 凭证密钥 / Credential key */
    @Schema(description = "凭证密钥 / Credential key")
    private String credentialKey;
}
