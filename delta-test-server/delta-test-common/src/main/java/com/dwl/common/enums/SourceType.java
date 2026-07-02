package com.dwl.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用例来源枚举
 * Source Type Enumeration
 * <p>
 * 定义测试用例的来源方式，包括自动生成、手动录入和混合修改。
 * Defines the source type of test cases, including auto-generated,
 * manually created, and hybrid modified.
 * </p>
 *
 * @author DeltaTest
 */
@Getter
@AllArgsConstructor
@Schema(description = "用例来源枚举 / Source Type Enumeration")
public enum SourceType {

    /**
     * 自动生成
     * Auto-generated
     */
    AUTO("auto", "自动生成"),

    /**
     * 手动录入
     * Manually created
     */
    MANUAL("manual", "手动录入"),

    /**
     * 混合修改
     * Hybrid modified
     */
    HYBRID("hybrid", "混合修改");

    /**
     * 来源编码
     * Source type code
     */
    @Schema(description = "来源编码 / Source type code", example = "auto")
    private final String code;

    /**
     * 来源描述
     * Source type description
     */
    @Schema(description = "来源描述 / Source type description", example = "自动生成")
    private final String description;
}
