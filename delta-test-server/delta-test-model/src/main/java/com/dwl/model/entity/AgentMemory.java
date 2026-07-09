package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

/**
 * Agent长期记忆实体
 * Agent Memory Entity
 * <p>
 * 对应表 agent_memory，存储Agent的历史分析模式与反馈学习数据。
 * Maps to table agent_memory, storing Agent's historical analysis patterns and feedback learning data.
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
@TableName("agent_memory")
@Schema(description = "Agent长期记忆 / Agent Memory")
public class AgentMemory extends BaseEntity {

    /**
     * 记忆类型: pattern/preference/feedback
     * Memory type: pattern/preference/feedback
     */
    @Schema(description = "记忆类型: pattern/preference/feedback / Memory type", example = "pattern")
    private String memoryType;

    /**
     * 来源类型: risk_analysis/root_cause/manual_mark
     * Source type: risk_analysis/root_cause/manual_mark
     */
    @Schema(description = "来源类型 / Source type", example = "risk_analysis")
    private String sourceType;

    /**
     * 来源业务ID
     * Source business ID
     */
    @Schema(description = "来源业务ID / Source business ID")
    private Long sourceId;

    /**
     * 模式键(如模块路径)
     * Pattern key (e.g. module path)
     */
    @Schema(description = "模式键(如模块路径) / Pattern key", example = "src/main/java/com/dwl/service")
    private String patternKey;

    /**
     * 模式值(JSON)
     * Pattern value (JSON)
     */
    @Schema(description = "模式值(JSON) / Pattern value (JSON)")
    private String patternValue;

    /**
     * 置信度
     * Confidence
     */
    @Schema(description = "置信度 / Confidence", example = "0.85")
    private BigDecimal confidence;

    /**
     * 命中次数
     * Hit count
     */
    @Schema(description = "命中次数 / Hit count", example = "5")
    private Integer hitCount;
}
