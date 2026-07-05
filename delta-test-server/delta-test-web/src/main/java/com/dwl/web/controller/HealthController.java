package com.dwl.web.controller;

import com.dwl.common.result.R;
import com.dwl.model.vo.HealthVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查控制器
 * Health Check Controller
 * <p>
 * 提供服务健康检查端点，返回服务状态信息。
 * Provides service health check endpoint, returns service status information.
 * </p>
 *
 * @author DeltaTest
 */
@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@Tag(name = "健康检查 / Health Check", description = "服务健康检查端点 / Service health check endpoint")
public class HealthController {

    /** 应用版本号 / Application version */
    @Value("${spring.application.name:delta-test-server}")
    private String serviceName;

    /** 应用版本号 / Application version */
    @Value("${application.version:1.0.0-SNAPSHOT}")
    private String version;

    /**
     * 服务健康检查
     * Service health check
     * <p>
     * 返回服务当前状态、名称和版本号。无需认证即可访问。
     * Returns current service status, name, and version. No authentication required.
     * </p>
     *
     * @return 健康检查响应 / Health check response
     */
    @GetMapping
    @Operation(summary = "服务健康检查 / Service health check", description = "检查服务是否正常运行 / Check if the service is running normally")
    public R<HealthVO> health() {
        HealthVO healthVO = HealthVO.builder()
                .status("UP")
                .service(serviceName)
                .version(version)
                .build();
        return R.ok(healthVO);
    }
}
