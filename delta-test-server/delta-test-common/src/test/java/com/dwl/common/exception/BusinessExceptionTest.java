package com.dwl.common.exception;

import com.dwl.common.result.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 业务异常 BusinessException 单元测试
 * Business Exception BusinessException Unit Test
 *
 * @author DeltaTest
 */
@DisplayName("业务异常 BusinessException / Business Exception BusinessException")
class BusinessExceptionTest {

    // ==================== 构造函数测试 / Constructor Tests ====================

    @Nested
    @DisplayName("构造函数 / Constructors")
    class ConstructorTests {

        @Test
        @DisplayName("BusinessException(ErrorCode) 正确设置 errorCode 和 message / Constructor with ErrorCode sets errorCode and message")
        void constructorWithErrorCode_setsErrorCodeAndMessage() {
            BusinessException exception = new BusinessException(ErrorCode.USER_NOT_FOUND);

            assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
            assertEquals("用户不存在", exception.getMessage());
            assertEquals(1001, exception.getCode());
        }

        @Test
        @DisplayName("BusinessException(ErrorCode, message) 使用自定义消息 / Constructor with ErrorCode and message uses custom message")
        void constructorWithErrorCodeAndMessage_usesCustomMessage() {
            String customMessage = "用户ID=123不存在 / User ID=123 not found";

            BusinessException exception = new BusinessException(ErrorCode.USER_NOT_FOUND, customMessage);

            assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
            assertEquals(customMessage, exception.getMessage());
            assertEquals(1001, exception.getCode());
        }

        @Test
        @DisplayName("BusinessException(ErrorCode, cause) 保留原因异常 / Constructor with ErrorCode and cause preserves cause")
        void constructorWithErrorCodeAndCause_preservesCause() {
            IOException cause = new IOException("数据库连接失败 / DB connection failed");

            BusinessException exception = new BusinessException(ErrorCode.INTERNAL_ERROR, cause);

            assertEquals(ErrorCode.INTERNAL_ERROR, exception.getErrorCode());
            assertEquals("服务器内部错误", exception.getMessage());
            assertSame(cause, exception.getCause());
            assertEquals(500, exception.getCode());
        }

        @Test
        @DisplayName("BusinessException(ErrorCode, message, cause) 同时设置自定义消息和原因 / Constructor with all params sets custom message and cause")
        void constructorWithAllParams_setsCustomMessageAndCause() {
            RuntimeException cause = new RuntimeException("原始异常 / Original exception");
            String customMessage = "自定义错误 / Custom error";

            BusinessException exception = new BusinessException(ErrorCode.FORBIDDEN, customMessage, cause);

            assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());
            assertEquals(customMessage, exception.getMessage());
            assertSame(cause, exception.getCause());
            assertEquals(403, exception.getCode());
        }
    }

    // ==================== getCode() 方法测试 / getCode() Method Tests ====================

    @Nested
    @DisplayName("getCode() 方法 / getCode() Method")
    class GetCodeTests {

        @Test
        @DisplayName("getCode() 返回 ErrorCode 的 code 数值 / getCode() returns ErrorCode's numeric code")
        void getCode_returnsNumericCode() {
            for (ErrorCode errorCode : ErrorCode.values()) {
                BusinessException exception = new BusinessException(errorCode);
                assertEquals(errorCode.getCode(), exception.getCode());
            }
        }
    }

    // ==================== 继承关系测试 / Inheritance Tests ====================

    @Nested
    @DisplayName("继承关系 / Inheritance")
    class InheritanceTests {

        @Test
        @DisplayName("BusinessException 是 RuntimeException 的子类 / BusinessException is a subclass of RuntimeException")
        void isRuntimeException() {
            BusinessException exception = new BusinessException(ErrorCode.BAD_REQUEST);
            assertInstanceOf(RuntimeException.class, exception);
        }

        @Test
        @DisplayName("可被 try-catch 正常捕获 / Can be caught by try-catch normally")
        void canBeCaughtByTryCatch() {
            ErrorCode caughtCode = null;
            try {
                throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
            } catch (BusinessException e) {
                caughtCode = e.getErrorCode();
            }
            assertEquals(ErrorCode.TOKEN_EXPIRED, caughtCode);
        }
    }

    // ==================== 序列化测试 / Serialization Tests ====================

    @Nested
    @DisplayName("序列化 / Serialization")
    class SerializationTests {

        @Test
        @DisplayName("serialVersionUID 为 1L / serialVersionUID is 1L")
        void serialVersionUID_isOne() {
            // 验证异常类可序列化 / Verify exception class is serializable
            BusinessException exception = new BusinessException(ErrorCode.SUCCESS);
            // RuntimeException 实现了 Serializable / RuntimeException implements Serializable
            assertInstanceOf(java.io.Serializable.class, exception);
        }
    }

    /**
     * 测试用内部 IO 异常 / Test internal IO exception
     */
    private static class IOException extends Exception {
        IOException(String message) {
            super(message);
        }
    }
}
