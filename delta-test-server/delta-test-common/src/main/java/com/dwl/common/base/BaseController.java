package com.dwl.common.base;

import com.dwl.common.result.ErrorCode;
import com.dwl.common.result.PageResult;
import com.dwl.common.result.R;
import com.dwl.common.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 基础控制器
 * Base Controller
 * <p>
 * 所有控制器的公共父类，提供统一的响应构建方法和当前登录用户信息获取。
 * Common parent class for all controllers, providing unified response building
 * methods and current login user information retrieval.
 * </p>
 *
 * @author DeltaTest
 */
@Schema(description = "基础控制器 / Base Controller")
public abstract class BaseController {

    // ==================== 成功响应构建方法 / Success Response Builder Methods ====================

    /**
     * 返回成功响应（无数据）
     * Return success response without data
     *
     * @param <T> 数据泛型 / Data generic type
     * @return 成功响应体 / Success response body
     */
    protected <T> R<T> success() {
        return R.ok();
    }

    /**
     * 返回成功响应（带数据）
     * Return success response with data
     *
     * @param data 响应数据 / Response data
     * @param <T>  数据泛型 / Data generic type
     * @return 成功响应体 / Success response body
     */
    protected <T> R<T> success(T data) {
        return R.ok(data);
    }

    /**
     * 返回成功响应（分页数据）
     * Return success response with paginated data
     *
     * @param page 分页结果 / Paginated result
     * @param <T>  数据泛型 / Data generic type
     * @return 成功响应体 / Success response body
     */
    protected <T> R<PageResult<T>> success(PageResult<T> page) {
        return R.ok(page);
    }

    // ==================== 失败响应构建方法 / Failure Response Builder Methods ====================

    /**
     * 返回失败响应（使用ErrorCode枚举）
     * Return failure response using ErrorCode enum
     *
     * @param errorCode 错误码枚举 / Error code enum
     * @param <T>       数据泛型 / Data generic type
     * @return 失败响应体 / Failure response body
     */
    protected <T> R<T> fail(ErrorCode errorCode) {
        return R.fail(errorCode);
    }

    /**
     * 返回失败响应（自定义消息）
     * Return failure response with custom message
     *
     * @param message 错误消息 / Error message
     * @param <T>     数据泛型 / Data generic type
     * @return 失败响应体 / Failure response body
     */
    protected <T> R<T> fail(String message) {
        return R.fail(ErrorCode.INTERNAL_ERROR.getCode(), message);
    }

    // ==================== 当前登录用户信息获取 / Current Login User Info ====================

    /**
     * 获取当前登录用户ID
     * Get current login user ID from SecurityContext
     *
     * @return 当前登录用户ID / Current login user ID
     */
    protected Long getCurrentUserId() {
        return SecurityUtil.getCurrentUserId();
    }

    /**
     * 获取当前登录用户名
     * Get current login username from SecurityContext
     *
     * @return 当前登录用户名 / Current login username
     */
    protected String getCurrentUsername() {
        return SecurityUtil.getCurrentUsername();
    }
}
