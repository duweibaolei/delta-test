package com.dwl.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举
 * Error Code Enumeration
 * <p>
 * 定义系统中所有的错误码，包含通用HTTP错误码和业务领域错误码。
 * Defines all error codes in the system, including generic HTTP error codes
 * and domain-specific business error codes.
 * </p>
 *
 * @author ByDWL
 */
@Getter
@AllArgsConstructor
@Schema(description = "错误码枚举 / Error Code Enumeration")
public enum ErrorCode {

    // ==================== 通用错误码 / Generic HTTP Error Codes ====================

    /**
     * 操作成功
     * Operation successful
     */
    SUCCESS(200, "操作成功"),

    /**
     * 请求参数错误
     * Bad request - invalid parameters
     */
    BAD_REQUEST(400, "请求参数错误"),

    /**
     * 未认证
     * Unauthorized - not authenticated
     */
    UNAUTHORIZED(401, "未认证"),

    /**
     * 无权限
     * Forbidden - insufficient permissions
     */
    FORBIDDEN(403, "无权限"),

    /**
     * 资源不存在
     * Resource not found
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 数据冲突
     * Data conflict
     */
    CONFLICT(409, "数据冲突"),

    /**
     * 服务器内部错误
     * Internal server error
     */
    INTERNAL_ERROR(500, "服务器内部错误"),

    /**
     * 上游服务不可用
     * Upstream service unavailable
     */
    UPSTREAM_UNAVAILABLE(502, "上游服务不可用"),

    /**
     * 服务暂不可用
     * Service temporarily unavailable
     */
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),

    // ==================== 用户模块错误码 / User Module Error Codes (1xxx) ====================

    /**
     * 用户不存在
     * User not found
     */
    USER_NOT_FOUND(1001, "用户不存在"),

    /**
     * 密码错误
     * Password incorrect
     */
    USER_PASSWORD_ERROR(1002, "密码错误"),

    /**
     * 用户已禁用
     * User is disabled
     */
    USER_DISABLED(1003, "用户已禁用"),

    /**
     * Token已过期
     * Token has expired
     */
    TOKEN_EXPIRED(1004, "Token已过期"),

    /**
     * Token无效
     * Token is invalid
     */
    TOKEN_INVALID(1005, "Token无效"),

    // ==================== 测试用例模块错误码 / Test Case Module Error Codes (2xxx) ====================

    /**
     * 测试用例不存在
     * Test case not found
     */
    CASE_NOT_FOUND(2001, "测试用例不存在"),

    /**
     * 用例版本冲突
     * Case version conflict
     */
    CASE_VERSION_CONFLICT(2002, "用例版本冲突"),

    // ==================== 测试任务模块错误码 / Test Task Module Error Codes (3xxx) ====================

    /**
     * 测试任务不存在
     * Test task not found
     */
    TASK_NOT_FOUND(3001, "测试任务不存在"),

    /**
     * 任务已在执行中
     * Task is already running
     */
    TASK_ALREADY_RUNNING(3002, "任务已在执行中"),

    // ==================== 测试报告模块错误码 / Test Report Module Error Codes (4xxx) ====================

    /**
     * 测试报告不存在
     * Test report not found
     */
    REPORT_NOT_FOUND(4001, "测试报告不存在"),

    // ==================== 引擎与AI服务错误码 / Engine & AI Service Error Codes (5xxx) ====================

    /**
     * C计算引擎不可用
     * C calculation engine unavailable
     */
    ENGINE_UNAVAILABLE(5001, "C计算引擎不可用"),

    /**
     * AI服务不可用
     * AI service unavailable
     */
    AI_SERVICE_UNAVAILABLE(5002, "AI服务不可用");

    /**
     * 错误码
     * Error code
     */
    @Schema(description = "错误码 / Error code", example = "200")
    private final int code;

    /**
     * 错误消息
     * Error message
     */
    @Schema(description = "错误消息 / Error message", example = "操作成功")
    private final String message;
}
