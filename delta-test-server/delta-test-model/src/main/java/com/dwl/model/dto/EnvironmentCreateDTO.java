package com.dwl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 创建环境 DTO
 * Create Environment DTO
 *
 * @author DeltaTest
 */
@Data
@Schema(description = "创建环境请求 / Create Environment Request")
public class EnvironmentCreateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 环境名称 / Environment name */
    @Schema(description = "环境名称 / Environment name", example = "开发环境", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "环境名称不能为空 / Environment name cannot be empty")
    private String envName;

    /** 环境编码 / Environment code */
    @Schema(description = "环境编码 / Environment code", example = "DEV", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "环境编码不能为空 / Environment code cannot be empty")
    private String envCode;

    /** 环境描述 / Environment description */
    @Schema(description = "环境描述 / Environment description", example = "开发测试环境")
    private String envDesc;

    /** 基础URL / Base URL */
    @Schema(description = "基础URL / Base URL", example = "https://dev.example.com")
    private String baseUrl;

    /** 排序号 / Sort order */
    @Schema(description = "排序号 / Sort order", example = "1")
    private Integer sortOrder;
}
