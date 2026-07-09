package com.dwl.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT工具类
 * JWT Utility Class
 * <p>
 * 提供JWT令牌的生成、解析和验证功能。
 * Provides JWT token generation, parsing, and verification capabilities.
 * </p>
 *
 * @author ByDWL
 */
@Slf4j
@Component
public class JwtUtil {

    /**
     * JWT密钥（从配置文件读取）
     * JWT secret key (read from configuration file)
     */
    @Value("${jwt.secret:deltatest-default-secret-key-must-be-at-least-256-bits-long-for-hmac-sha}")
    private String secret;

    /**
     * JWT过期时间（毫秒，从配置文件读取，默认24小时）
     * JWT expiration time in milliseconds (read from configuration, default 24 hours)
     */
    @Value("${jwt.expiration:86400000}")
    private long expiration;

    /**
     * 获取签名密钥
     * Get signing key
     *
     * @return 签名密钥 / Signing key
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成JWT令牌
     * Generate JWT token
     *
     * @param userId   用户ID / User ID
     * @param username 用户名 / Username
     * @return JWT令牌 / JWT token
     */
    public String generateToken(Long userId, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        log.debug("生成JWT令牌 / Generating JWT token: userId={}, username={}", userId, username);

        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 解析JWT令牌
     * Parse JWT token
     *
     * @param token JWT令牌 / JWT token
     * @return Claims对象 / Claims object
     * @throws JwtException 令牌无效或已过期时抛出 / Thrown when token is invalid or expired
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 判断JWT令牌是否已过期
     * Check if JWT token is expired
     *
     * @param token JWT令牌 / JWT token
     * @return 是否已过期 / Whether the token is expired
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            // 令牌已过期 / Token has expired
            return true;
        } catch (JwtException e) {
            // 令牌无效，视为过期 / Token is invalid, treat as expired
            log.warn("JWT令牌无效 / Invalid JWT token: {}", e.getMessage());
            return true;
        }
    }

    /**
     * 从JWT令牌中获取用户ID
     * Get user ID from JWT token
     *
     * @param token JWT令牌 / JWT token
     * @return 用户ID / User ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 从JWT令牌中获取用户名
     * Get username from JWT token
     *
     * @param token JWT令牌 / JWT token
     * @return 用户名 / Username
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }
}
