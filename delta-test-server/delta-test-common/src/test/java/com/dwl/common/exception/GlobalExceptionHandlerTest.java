package com.dwl.common.exception;

import com.dwl.common.enums.ErrorCode;
import com.dwl.common.result.R;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 全局异常处理器 GlobalExceptionHandler 单元测试
 * Global Exception Handler GlobalExceptionHandler Unit Test
 *
 * @author ByDWL
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("全局异常处理器 GlobalExceptionHandler / Global Exception Handler GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    // ==================== BusinessException 处理测试 / BusinessException Handler Tests ====================

    @Nested
    @DisplayName("BusinessException 处理 / BusinessException Handling")
    class BusinessExceptionTests {

        @Test
        @DisplayName("处理 BusinessException 返回对应 ErrorCode 响应 / Handle BusinessException returns matching ErrorCode response")
        void handleBusinessException_returnsMatchingErrorCodeResponse() {
            BusinessException exception = new BusinessException(ErrorCode.USER_NOT_FOUND);

            R<Void> result = handler.handleBusinessException(exception);

            assertEquals(1001, result.getCode());
            assertEquals("用户不存在", result.getMessage());
            assertNull(result.getData());
        }

        @Test
        @DisplayName("处理带自定义消息的 BusinessException，返回 ErrorCode 原始消息 / Handle BusinessException with custom message returns ErrorCode original message")
        void handleBusinessException_withCustomMessage_returnsErrorCodeMessage() {
            BusinessException exception = new BusinessException(ErrorCode.BAD_REQUEST, "参数缺失 / Parameter missing");

            R<Void> result = handler.handleBusinessException(exception);

            // handler 使用 R.fail(e.getErrorCode())，返回 ErrorCode 原始消息而非自定义消息
            // Handler uses R.fail(e.getErrorCode()), returns ErrorCode original message, not custom message
            assertEquals(400, result.getCode());
            assertEquals(ErrorCode.BAD_REQUEST.getMessage(), result.getMessage());
        }

        @Test
        @DisplayName("各 ErrorCode 均可正确处理 / All ErrorCodes can be handled correctly")
        void handleBusinessException_allErrorCodes_handledCorrectly() {
            for (ErrorCode errorCode : ErrorCode.values()) {
                BusinessException exception = new BusinessException(errorCode);
                R<Void> result = handler.handleBusinessException(exception);
                assertEquals(errorCode.getCode(), result.getCode());
            }
        }
    }

    // ==================== MethodArgumentNotValidException 处理测试 / MethodArgumentNotValidException Handler Tests ====================

    @Nested
    @DisplayName("MethodArgumentNotValidException 处理 / MethodArgumentNotValidException Handling")
    class MethodArgumentNotValidExceptionTests {

        private MethodArgumentNotValidException createException(List<FieldError> fieldErrors) {
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            BindingResult bindingResult = mock(BindingResult.class);
            when(exception.getBindingResult()).thenReturn(bindingResult);
            when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
            return exception;
        }

        @Test
        @DisplayName("单个字段校验错误 / Single field validation error")
        void singleFieldError_returnsBadRequestWithMessage() {
            FieldError fieldError = new FieldError("object", "username", "用户名不能为空 / Username cannot be empty");
            MethodArgumentNotValidException exception = createException(List.of(fieldError));

            R<Void> result = handler.handleMethodArgumentNotValidException(exception);

            assertEquals(400, result.getCode());
            assertEquals("用户名不能为空 / Username cannot be empty", result.getMessage());
        }

        @Test
        @DisplayName("多个字段校验错误用分号连接 / Multiple field errors joined by semicolon")
        void multipleFieldErrors_joinedBySemicolon() {
            FieldError error1 = new FieldError("object", "username", "用户名不能为空 / Username required");
            FieldError error2 = new FieldError("object", "email", "邮箱格式不正确 / Invalid email format");
            MethodArgumentNotValidException exception = createException(List.of(error1, error2));

            R<Void> result = handler.handleMethodArgumentNotValidException(exception);

            assertEquals(400, result.getCode());
            assertTrue(result.getMessage().contains("用户名不能为空 / Username required"));
            assertTrue(result.getMessage().contains("邮箱格式不正确 / Invalid email format"));
            assertTrue(result.getMessage().contains("; "));
        }

        @Test
        @DisplayName("无字段错误时返回 400 / No field errors returns 400")
        void noFieldErrors_returnsBadRequest() {
            MethodArgumentNotValidException exception = createException(List.of());

            R<Void> result = handler.handleMethodArgumentNotValidException(exception);

            assertEquals(400, result.getCode());
        }
    }

    // ==================== ConstraintViolationException 处理测试 / ConstraintViolationException Handler Tests ====================

    @Nested
    @DisplayName("ConstraintViolationException 处理 / ConstraintViolationException Handling")
    class ConstraintViolationExceptionTests {

        @Test
        @DisplayName("单个约束违反 / Single constraint violation")
        @SuppressWarnings("unchecked")
        void singleViolation_returnsBadRequestWithMessage() {
            ConstraintViolation<?> violation = mock(ConstraintViolation.class);
            when(violation.getMessage()).thenReturn("必须为正数 / Must be positive");
            ConstraintViolationException exception = new ConstraintViolationException(Set.of(violation));

            R<Void> result = handler.handleConstraintViolationException(exception);

            assertEquals(400, result.getCode());
            assertEquals("必须为正数 / Must be positive", result.getMessage());
        }

        @Test
        @DisplayName("多个约束违反用分号连接 / Multiple violations joined by semicolon")
        @SuppressWarnings("unchecked")
        void multipleViolations_joinedBySemicolon() {
            ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
            when(violation1.getMessage()).thenReturn("必须为正数 / Must be positive");
            ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);
            when(violation2.getMessage()).thenReturn("不能为空 / Cannot be null");

            ConstraintViolationException exception = new ConstraintViolationException(
                    Set.of(violation1, violation2));

            R<Void> result = handler.handleConstraintViolationException(exception);

            assertEquals(400, result.getCode());
            assertTrue(result.getMessage().contains("必须为正数 / Must be positive"));
            assertTrue(result.getMessage().contains("不能为空 / Cannot be null"));
            assertTrue(result.getMessage().contains("; "));
        }
    }

    // ==================== HttpMessageNotReadableException 处理测试 / HttpMessageNotReadableException Handler Tests ====================

    @Nested
    @DisplayName("HttpMessageNotReadableException 处理 / HttpMessageNotReadableException Handling")
    class HttpMessageNotReadableExceptionTests {

        @Test
        @DisplayName("返回请求体格式错误响应 / Returns invalid request body format response")
        void returnsInvalidRequestBodyFormatResponse() {
            HttpMessageNotReadableException exception = new HttpMessageNotReadableException(
                    "JSON parse error / JSON解析错误", mock(org.springframework.http.HttpInputMessage.class));

            R<Void> result = handler.handleHttpMessageNotReadableException(exception);

            assertEquals(400, result.getCode());
            assertEquals("请求体格式错误 / Invalid request body format", result.getMessage());
            assertNull(result.getData());
        }
    }

    // ==================== 兜底 Exception 处理测试 / Fallback Exception Handler Tests ====================

    @Nested
    @DisplayName("兜底 Exception 处理 / Fallback Exception Handling")
    class FallbackExceptionTests {

        @Test
        @DisplayName("未知异常返回内部错误响应 / Unknown exception returns internal error response")
        void unknownException_returnsInternalErrorResponse() {
            Exception exception = new RuntimeException("未知错误 / Unknown error");

            R<Void> result = handler.handleException(exception);

            assertEquals(500, result.getCode());
            assertEquals("服务器内部错误", result.getMessage());
            assertNull(result.getData());
        }

        @Test
        @DisplayName("NullPointerException 返回内部错误响应 / NullPointerException returns internal error response")
        void nullPointerException_returnsInternalErrorResponse() {
            Exception exception = new NullPointerException("NPE / 空指针");

            R<Void> result = handler.handleException(exception);

            assertEquals(500, result.getCode());
            assertEquals("服务器内部错误", result.getMessage());
        }

        @Test
        @DisplayName("IllegalArgumentException 返回内部错误响应 / IllegalArgumentException returns internal error response")
        void illegalArgumentException_returnsInternalErrorResponse() {
            Exception exception = new IllegalArgumentException("非法参数 / Illegal argument");

            R<Void> result = handler.handleException(exception);

            assertEquals(500, result.getCode());
            assertEquals("服务器内部错误", result.getMessage());
        }
    }
}
