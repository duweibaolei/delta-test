package com.dwl.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 健康检查响应 VO
 * Health Check Response VO
 * <p>
 * 返回服务健康状态信息，供前端和监控系统调用。
 * Returns service health status information for frontend and monitoring systems.
 * </p>
 *
 * @author ByDWL
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "健康检查响应 / Health Check Response")
public class HealthVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 服务状态 / Service status */
    @Schema(description = "服务状态 / Service status", example = "UP")
    private String status;

    /** 服务名称 / Service name */
    @Schema(description = "服务名称 / Service name", example = "delta-test-server")
    private String service;

    /** 版本号 / Version */
    @Schema(description = "版本号 / Version", example = "1.0.0-SNAPSHOT")
    private String version;
}
