package com.dwl.common.exception;

import com.dwl.common.result.ErrorCode;
import com.dwl.common.result.R;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * Global Exception Handler
 * <p>
 * 统一拦截并处理控制器层抛出的各类异常，将其转换为标准化的R响应体。
 * Uniformly intercepts and handles various exceptions thrown from the controller layer,
 * converting them into standardized R response bodies.
 * </p>
 *
 * @author DeltaTest
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * Handle business exception
     *
     * @param e 业务异常 / Business exception
     * @return 统一响应体 / Unified response body
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常 / Business exception: code={}, message={}", e.getCode(), e.getMessage(), e);
        return R.fail(e.getErrorCode());
    }

    /**
     * 处理请求参数校验异常（@Valid / @RequestBody）
     * Handle method argument validation exception (@Valid / @RequestBody)
     *
     * @param e 参数校验异常 / Method argument not valid exception
     * @return 统一响应体 / Unified response body
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 提取所有字段错误信息 / Extract all field error messages
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数校验失败 / Validation failed: {}", message);
        return R.fail(ErrorCode.BAD_REQUEST.getCode(), message);
    }

    /**
     * 处理约束违反异常（@Validated / 路径参数、查询参数校验）
     * Handle constraint violation exception (@Validated / path & query param validation)
     *
     * @param e 约束违反异常 / Constraint violation exception
     * @return 统一响应体 / Unified response body
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleConstraintViolationException(ConstraintViolationException e) {
        // 提取所有约束违反信息 / Extract all constraint violation messages
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
        log.warn("约束违反 / Constraint violation: {}", message);
        return R.fail(ErrorCode.BAD_REQUEST.getCode(), message);
    }

    /**
     * 处理请求体不可读异常（JSON解析错误等）
     * Handle HTTP message not readable exception (JSON parse error, etc.)
     *
     * @param e 请求体不可读异常 / HTTP message not readable exception
     * @return 统一响应体 / Unified response body
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("请求体解析失败 / Request body not readable: {}", e.getMessage());
        return R.fail(ErrorCode.BAD_REQUEST.getCode(), "请求体格式错误 / Invalid request body format");
    }

    /**
     * 兜底异常处理（捕获所有未处理的异常）
     * Fallback exception handler (catches all unhandled exceptions)
     *
     * @param e 未知异常 / Unknown exception
     * @return 统一响应体 / Unified response body
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> handleException(Exception e) {
        log.error("未处理异常 / Unhandled exception", e);
        return R.fail(ErrorCode.INTERNAL_ERROR);
    }
}
