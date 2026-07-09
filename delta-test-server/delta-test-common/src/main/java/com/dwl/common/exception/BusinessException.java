package com.dwl.common.exception;

import com.dwl.common.enums.ErrorCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 业务异常
 * Business Exception
 * <p>
 * 业务逻辑中抛出的受检异常，携带ErrorCode枚举以标识具体错误类型。
 * Runtime exception thrown during business logic, carrying an ErrorCode enum
 * to identify the specific error type.
 * </p>
 *
 * @author ByDWL
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误码枚举
     * Error code enum
     */
    private final ErrorCode errorCode;

    /**
     * 使用ErrorCode枚举构造业务异常
     * Construct business exception with ErrorCode enum
     *
     * @param errorCode 错误码枚举 / Error code enum
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 使用ErrorCode枚举和自定义消息构造业务异常
     * Construct business exception with ErrorCode enum and custom message
     *
     * @param errorCode 错误码枚举 / Error code enum
     * @param message   自定义错误消息 / Custom error message
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 使用ErrorCode枚举和原因异常构造业务异常
     * Construct business exception with ErrorCode enum and cause
     *
     * @param errorCode 错误码枚举 / Error code enum
     * @param cause     原因异常 / Cause exception
     */
    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    /**
     * 使用ErrorCode枚举、自定义消息和原因异常构造业务异常
     * Construct business exception with ErrorCode enum, custom message, and cause
     *
     * @param errorCode 错误码枚举 / Error code enum
     * @param message   自定义错误消息 / Custom error message
     * @param cause     原因异常 / Cause exception
     */
    public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * 获取错误码数值
     * Get the numeric error code
     *
     * @return 错误码数值 / Numeric error code
     */
    public int getCode() {
        return errorCode.getCode();
    }
}
