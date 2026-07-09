package com.dwl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 执行配置 DTO
 * Execution Configuration DTO
 *
 * @author ByDWL
 */
@Data
@Schema(description = "执行配置 / Execution Configuration")
public class ExecConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 环境ID / Environment ID */
    @Schema(description = "环境ID / Environment ID", example = "1")
    private Long envId;

    /** 浏览器类型 / Browser type */
    @Schema(description = "浏览器类型 / Browser type", example = "chrome")
    private String browserType;

    /** 并发数 / Concurrency */
    @Schema(description = "并发数 / Concurrency", example = "3")
    private Integer concurrency;

    /** 重试次数 / Retry count */
    @Schema(description = "重试次数 / Retry count", example = "1")
    private Integer retryCount;
}
