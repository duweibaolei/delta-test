package com.dwl.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 环境视图对象 VO
 * Environment View Object
 *
 * @author ByDWL
 */
@Data
@Schema(description = "环境视图对象 / Environment View Object")
public class EnvironmentVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 环境ID / Environment ID */
    @Schema(description = "环境ID / Environment ID", example = "1")
    private Long id;

    /** 环境名称 / Environment name */
    @Schema(description = "环境名称 / Environment name", example = "开发环境")
    private String envName;

    /** 环境编码 / Environment code */
    @Schema(description = "环境编码 / Environment code", example = "DEV")
    private String envCode;

    /** 环境描述 / Environment description */
    @Schema(description = "环境描述 / Environment description")
    private String envDesc;

    /** 基础URL / Base URL */
    @Schema(description = "基础URL / Base URL", example = "https://dev.example.com")
    private String baseUrl;

    /** 排序号 / Sort order */
    @Schema(description = "排序号 / Sort order", example = "1")
    private Integer sortOrder;

    /** 状态 / Status */
    @Schema(description = "状态 / Status", example = "0")
    private Integer status;
}
