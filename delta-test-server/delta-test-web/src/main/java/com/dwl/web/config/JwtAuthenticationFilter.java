package com.dwl.web.config;

import com.dwl.common.enums.ErrorCode;
import com.dwl.common.result.R;
import com.dwl.common.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT认证过滤器
 * JWT Authentication Filter
 * <p>
 * 从请求头中提取并验证JWT令牌，设置Spring Security上下文。
 * 复用 {@link JwtUtil} 进行令牌解析，避免密钥配置重复。
 * Extracts and validates JWT token from request header, sets Spring Security context.
 * Reuses {@link JwtUtil} for token parsing to avoid duplicate key configuration.
 * </p>
 *
 * @author ByDWL
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** JWT工具类 / JWT utility */
    private final JwtUtil jwtUtil;

    /** JSON对象映射器 / JSON object mapper */
    private final ObjectMapper objectMapper;

    /**
     * 过滤请求，验证JWT令牌
     * Filter request, validate JWT token
     *
     * @param request     HTTP请求 / HTTP request
     * @param response    HTTP响应 / HTTP response
     * @param filterChain 过滤器链 / Filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取Token / Get token from request header
        String token = extractToken(request);

        if (token != null) {
            try {
                Claims claims = jwtUtil.parseToken(token);
                String username = claims.getSubject();
                Long userId = claims.get("userId", Long.class);

                // 设置认证信息 / Set authentication info
                // principal=userId(Long), credentials=token(String) 以便 SecurityUtil.getCurrentUserId() 正确获取
                // principal=userId(Long), credentials=token(String) so SecurityUtil.getCurrentUserId() works correctly
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, token, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                sendErrorResponse(response, ErrorCode.TOKEN_EXPIRED);
                return;
            } catch (Exception e) {
                sendErrorResponse(response, ErrorCode.TOKEN_INVALID);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 从请求头中提取Token
     * Extract token from request header
     *
     * @param request HTTP请求 / HTTP request
     * @return Token字符串 / Token string
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 发送错误响应
     * Send error response
     *
     * @param response  HTTP响应 / HTTP response
     * @param errorCode 错误码 / Error code
     */
    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        R<Void> result = R.fail(errorCode);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
