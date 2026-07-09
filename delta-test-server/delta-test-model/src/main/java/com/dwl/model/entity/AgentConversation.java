package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Agent对话记录实体
 * Agent Conversation Entity
 * <p>
 * 对应表 agent_conversation，记录Agent与用户之间的对话历史。
 * Maps to table agent_conversation, recording conversation history between Agent and users.
 * 继承 BaseEntity，包含公共审计字段。
 * Extends BaseEntity, including common audit fields.
 * </p>
 *
 * @author ByDWL
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("agent_conversation")
@Schema(description = "Agent对话记录 / Agent Conversation")
public class AgentConversation extends BaseEntity {

    /**
     * 会话ID
     * Session ID
     */
    @Schema(description = "会话ID / Session ID", example = "CA-20260704-0001")
    private String sessionId;

    /**
     * 角色: user/agent/system
     * Role: user/agent/system
     */
    @Schema(description = "角色: user/agent/system / Role", example = "user")
    private String role;

    /**
     * 消息内容
     * Message content
     */
    @Schema(description = "消息内容 / Message content")
    private String content;

    /**
     * Agent类型
     * Agent type
     */
    @Schema(description = "Agent类型 / Agent type", example = "risk_analysis")
    private String agentType;

    /**
     * 子Agent结果(JSON)
     * Sub-agent results (JSON)
     */
    @Schema(description = "子Agent结果(JSON) / Sub-agent results (JSON)")
    private String subResults;

    /**
     * LLM模型版本
     * LLM model version
     */
    @Schema(description = "LLM模型版本 / LLM model version", example = "gpt-4o")
    private String modelVersion;

    /**
     * Token使用量(JSON)
     * Token usage (JSON)
     */
    @Schema(description = "Token使用量(JSON) / Token usage (JSON)")
    private String tokenUsage;
}
