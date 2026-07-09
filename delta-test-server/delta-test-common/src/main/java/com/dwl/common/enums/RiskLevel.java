package com.dwl.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 风险等级枚举
 * Risk Level Enumeration
 * <p>
 * 定义测试用例或缺陷的风险等级，包括高风险、中风险和低风险。
 * Defines the risk level of test cases or defects, including high, medium, and low risk.
 * </p>
 *
 * @author ByDWL
 */
@Getter
@AllArgsConstructor
@Schema(description = "风险等级枚举 / Risk Level Enumeration")
public enum RiskLevel {

    /**
     * 高风险
     * High risk
     */
    HIGH("high", "高风险"),

    /**
     * 中风险
     * Medium risk
     */
    MEDIUM("medium", "中风险"),

    /**
     * 低风险
     * Low risk
     */
    LOW("low", "低风险");

    /**
     * 风险等级编码
     * Risk level code
     */
    @Schema(description = "风险等级编码 / Risk level code", example = "high")
    private final String code;

    /**
     * 风险等级描述
     * Risk level description
     */
    @Schema(description = "风险等级描述 / Risk level description", example = "高风险")
    private final String description;
}
