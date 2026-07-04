package com.dwl.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应体
 * Unified API Response Body
 * <p>
 * 所有API接口返回值的统一包装类，包含状态码、消息、数据和时间戳。
 * Unified wrapper for all API response values, containing status code,
 * message, data, and timestamp.
 * </p>
 *
 * @param <T> 响应数据泛型 / Response data generic type
 * @author DeltaTest
 */
@Data
@Schema(description = "统一响应体 / Unified API Response Body")
public class R<T> implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应状态码
     * Response status code (200=成功/success, 400/401/403/404/409/500/502/503=错误/error)
     */
    @Schema(description = "响应状态码 / Response status code", example = "200")
    private int code;

    /**
     * 响应消息
     * Response message
     */
    @Schema(description = "响应消息 / Response message", example = "操作成功")
    private String message;

    /**
     * 响应数据
     * Response data
     */
    @Schema(description = "响应数据 / Response data")
    private T data;

    /**
     * 响应时间戳
     * Response timestamp (milliseconds since epoch)
     */
    @Schema(description = "响应时间戳 / Response timestamp", example = "1704067200000")
    private long timestamp;

    /**
     * 默认构造函数
     * Default constructor - initializes timestamp to current time
     */
    public R() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 全参构造函数
     * Full argument constructor
     *
     * @param code    状态码 / Status code
     * @param message 消息 / Message
     * @param data    数据 / Data
     */
    public R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // ==================== 成功响应工厂方法 / Success Factory Methods ====================

    /**
     * 返回成功响应（无数据）
     * Return success response without data
     *
     * @param <T> 数据泛型 / Data generic type
     * @return 成功响应体 / Success response body
     */
    public static <T> R<T> ok() {
        return new R<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);
    }

    /**
     * 返回成功响应（带数据）
     * Return success response with data
     *
     * @param data 响应数据 / Response data
     * @param <T>  数据泛型 / Data generic type
     * @return 成功响应体 / Success response body
     */
    public static <T> R<T> ok(T data) {
        return new R<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    /**
     * 返回成功响应（带自定义消息和数据）
     * Return success response with custom message and data
     *
     * @param message 自定义消息 / Custom message
     * @param data    响应数据 / Response data
     * @param <T>     数据泛型 / Data generic type
     * @return 成功响应体 / Success response body
     */
    public static <T> R<T> ok(String message, T data) {
        return new R<>(ErrorCode.SUCCESS.getCode(), message, data);
    }

    // ==================== 失败响应工厂方法 / Failure Factory Methods ====================

    /**
     * 返回失败响应（默认内部错误）
     * Return failure response with default internal error
     *
     * @param <T> 数据泛型 / Data generic type
     * @return 失败响应体 / Failure response body
     */
    public static <T> R<T> fail() {
        return new R<>(ErrorCode.INTERNAL_ERROR.getCode(), ErrorCode.INTERNAL_ERROR.getMessage(), null);
    }

    /**
     * 返回失败响应（自定义状态码和消息）
     * Return failure response with custom code and message
     *
     * @param code    错误状态码 / Error status code
     * @param message 错误消息 / Error message
     * @param <T>     数据泛型 / Data generic type
     * @return 失败响应体 / Failure response body
     */
    public static <T> R<T> fail(int code, String message) {
        return new R<>(code, message, null);
    }

    /**
     * 返回失败响应（使用ErrorCode枚举）
     * Return failure response using ErrorCode enum
     *
     * @param errorCode 错误码枚举 / Error code enum
     * @param <T>       数据泛型 / Data generic type
     * @return 失败响应体 / Failure response body
     */
    public static <T> R<T> fail(ErrorCode errorCode) {
        return new R<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 返回失败响应（使用ErrorCode枚举和自定义消息）
     * Return failure response using ErrorCode enum with custom message
     *
     * @param errorCode 错误码枚举 / Error code enum
     * @param message   自定义错误消息 / Custom error message
     * @param <T>       数据泛型 / Data generic type
     * @return 失败响应体 / Failure response body
     */
    public static <T> R<T> fail(ErrorCode errorCode, String message) {
        return new R<>(errorCode.getCode(), message, null);
    }
}
