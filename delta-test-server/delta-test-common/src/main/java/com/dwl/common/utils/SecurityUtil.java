package com.dwl.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全工具类
 * Security Utility Class
 * <p>
 * 提供从Spring Security上下文中获取当前登录用户信息的方法。
 * Provides methods to retrieve current login user information
 * from the Spring Security context.
 * </p>
 *
 * @author DeltaTest
 */
public final class SecurityUtil {

    /**
     * 私有构造函数，防止实例化
     * Private constructor to prevent instantiation
     */
    private SecurityUtil() {
        throw new UnsupportedOperationException("工具类不允许实例化 / Utility class cannot be instantiated");
    }

    /**
     * 获取当前登录用户ID
     * Get current login user ID from SecurityContext
     * <p>
     * 从Spring Security上下文中提取已认证用户的ID。
     * 如果用户未认证或上下文中无信息，返回null。
     * Extracts the authenticated user's ID from the Spring Security context.
     * Returns null if the user is not authenticated or context is empty.
     * </p>
     *
     * @return 当前登录用户ID，未认证时返回null / Current login user ID, or null if not authenticated
     */
    public static Long getCurrentUserId() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Long userId) {
            return userId;
        }
        return null;
    }

    /**
     * 获取当前登录用户ID（字符串形式）
     * Get current login user ID as String from SecurityContext
     * <p>
     * 从Spring Security上下文中提取已认证用户的ID字符串。
     * Extracts the authenticated user's ID as a string from the Spring Security context.
     * </p>
     *
     * @return 当前登录用户ID字符串，未认证时返回null / Current login user ID string, or null if not authenticated
     */
    public static String getCurrentUserIdAsString() {
        Long userId = getCurrentUserId();
        return userId != null ? userId.toString() : null;
    }

    /**
     * 获取当前登录用户名
     * Get current login username from SecurityContext
     * <p>
     * 从Spring Security上下文中提取已认证用户的用户名。
     * 如果用户未认证或上下文中无信息，返回null。
     * Extracts the authenticated user's username from the Spring Security context.
     * Returns null if the user is not authenticated or context is empty.
     * </p>
     *
     * @return 当前登录用户名，未认证时返回null / Current login username, or null if not authenticated
     */
    public static String getCurrentUsername() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * 判断当前用户是否已认证
     * Check if the current user is authenticated
     * <p>
     * 检查Spring Security上下文中是否存在已认证的Authentication对象。
     * Checks whether an authenticated Authentication object exists
     * in the Spring Security context.
     * </p>
     *
     * @return 是否已认证 / Whether authenticated
     */
    public static boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        return authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());
    }

    /**
     * 获取当前Authentication对象
     * Get current Authentication object from SecurityContext
     *
     * @return Authentication对象，可能为null / Authentication object, may be null
     */
    private static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context != null ? context.getAuthentication() : null;
    }
}
