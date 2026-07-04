package com.dwl.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用例状态枚举
 * Case Status Enumeration
 * <p>
 * 定义测试用例的当前状态，用于标识用例在生命周期中的阶段。
 * Defines the current status of a test case, indicating its phase in the lifecycle.
 * </p>
 *
 * @author DeltaTest
 */
@Getter
@AllArgsConstructor
@Schema(description = "用例状态枚举 / Case Status Enumeration")
public enum CaseStatus {

    /**
     * 活跃（正常可用）
     * Active (normally usable)
     */
    ACTIVE("active", "活跃"),

    /**
     * 不稳定（偶尔失败）
     * Unstable (occasionally fails)
     */
    UNSTABLE("unstable", "不稳定"),

    /**
     * 已禁用
     * Disabled
     */
    DISABLED("disabled", "已禁用"),

    /**
     * 草稿
     * Draft
     */
    DRAFT("draft", "草稿");

    /**
     * 状态编码
     * Status code
     */
    @Schema(description = "状态编码 / Status code", example = "active")
    private final String code;

    /**
     * 状态描述
     * Status description
     */
    @Schema(description = "状态描述 / Status description", example = "活跃")
    private final String description;
}
