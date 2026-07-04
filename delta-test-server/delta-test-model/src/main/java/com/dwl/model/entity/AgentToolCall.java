package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Agent工具调用记录实体
 * Agent Tool Call Entity
 * <p>
 * 对应表 agent_tool_call，记录Agent对工具的调用情况，用于审计和优化。
 * Maps to table agent_tool_call, recording Agent's tool invocation for auditing and optimization.
 * 继承 BaseEntity，包含公共审计字段。
 * Extends BaseEntity, including common audit fields.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("agent_tool_call")
@Schema(description = "Agent工具调用记录 / Agent Tool Call")
public class AgentToolCall extends BaseEntity {

    /**
     * 会话ID
     * Session ID
     */
    @Schema(description = "会话ID / Session ID", example = "CA-20260704-0001")
    private String sessionId;

    /**
     * Agent类型
     * Agent type
     */
    @Schema(description = "Agent类型 / Agent type", example = "risk_analysis")
    private String agentType;

    /**
     * 工具名称
     * Tool name
     */
    @Schema(description = "工具名称 / Tool name", example = "git_diff")
    private String toolName;

    /**
     * 输入哈希
     * Input hash
     */
    @Schema(description = "输入哈希 / Input hash")
    private String inputHash;

    /**
     * 输出哈希
     * Output hash
     */
    @Schema(description = "输出哈希 / Output hash")
    private String outputHash;

    /**
     * 耗时(毫秒)
     * Latency in milliseconds
     */
    @Schema(description = "耗时(毫秒) / Latency in ms", example = "1500")
    private Integer latencyMs;

    /**
     * 状态: success/failed/timeout
     * Status: success/failed/timeout
     */
    @Schema(description = "状态: success/failed/timeout / Status", example = "success")
    private String status;

    /**
     * 错误信息
     * Error message
     */
    @Schema(description = "错误信息 / Error message")
    private String errorMessage;
}
