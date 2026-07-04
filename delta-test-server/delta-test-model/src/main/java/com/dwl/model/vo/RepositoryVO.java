package com.dwl.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 仓库视图对象 VO
 * Repository View Object
 *
 * @author DeltaTest
 */
@Data
@Schema(description = "仓库视图对象 / Repository View Object")
public class RepositoryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 仓库ID / Repository ID */
    @Schema(description = "仓库ID / Repository ID", example = "1")
    private Long id;

    /** 仓库名称 / Repository name */
    @Schema(description = "仓库名称 / Repository name", example = "DeltaTest")
    private String repoName;

    /** 仓库URL / Repository URL */
    @Schema(description = "仓库URL / Repository URL", example = "https://github.com/org/DeltaTest.git")
    private String repoUrl;

    /** 默认分支 / Default branch */
    @Schema(description = "默认分支 / Default branch", example = "main")
    private String defaultBranch;

    /** 凭证类型 / Credential type */
    @Schema(description = "凭证类型 / Credential type", example = "http_token")
    private String credentialType;

    /** Webhook URL / Webhook URL */
    @Schema(description = "Webhook URL / Webhook URL")
    private String webhookUrl;

    /** 状态 / Status */
    @Schema(description = "状态 / Status", example = "0")
    private Integer status;

    /** 创建时间 / Creation time */
    @Schema(description = "创建时间 / Creation time")
    private LocalDateTime createTime;
}
