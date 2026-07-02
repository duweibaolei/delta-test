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
 * 执行节点实体
 * Execution Node Entity
 * <p>
 * 对应表 exec_node，管理Playwright执行节点的注册和心跳状态。
 * Maps to table exec_node, managing Playwright execution node registration and heartbeat status.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("exec_node")
@Schema(description = "执行节点 / Execution Node")
public class ExecNode extends BaseEntity {

    /**
     * 节点名称
     * Node name
     */
    @Schema(description = "节点名称 / Node name", example = "node-01")
    private String nodeName;

    /**
     * 节点地址
     * Node host address
     */
    @Schema(description = "节点地址 / Node host address", example = "192.168.1.100")
    private String nodeHost;

    /**
     * 节点端口
     * Node port
     */
    @Schema(description = "节点端口 / Node port", example = "9321")
    private Integer nodePort;

    /**
     * 支持浏览器类型(逗号分隔)
     * Supported browser types (comma-separated)
     */
    @Schema(description = "支持浏览器类型(逗号分隔) / Supported browser types", example = "chromium,firefox")
    private String browserTypes;

    /**
     * 最大并发数
     * Maximum concurrent executions
     */
    @Schema(description = "最大并发数 / Maximum concurrent executions", example = "2")
    private Integer maxConcurrent;

    /**
     * 状态: healthy/offline/busy
     * Status: healthy/offline/busy
     */
    @Schema(description = "状态: healthy/offline/busy / Status", example = "healthy")
    private String status;

    /**
     * 最后心跳时间
     * Last heartbeat timestamp
     */
    @Schema(description = "最后心跳时间 / Last heartbeat timestamp")
    private LocalDateTime lastHeartbeat;
}
