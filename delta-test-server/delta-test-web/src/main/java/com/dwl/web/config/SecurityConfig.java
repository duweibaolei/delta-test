package com.dwl.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Security 安全配置类
 * Spring Security Configuration
 * <p>
 * 配置JWT过滤器、CORS、CSRF（REST API禁用）、会话管理等。
 * Configures JWT filter, CORS, CSRF (disabled for REST), session management, etc.
 * </p>
 *
 * @author ByDWL
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /** JWT认证过滤器 / JWT authentication filter */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /** JSON对象映射器 / JSON object mapper */
    private final ObjectMapper objectMapper;

    /** CORS允许的源（逗号分隔）/ CORS allowed origins (comma-separated) */
    @Value("${cors.allowed-origins:*}")
    private String corsAllowedOrigins;

    /**
     * 安全过滤器链
     * Security filter chain
     *
     * @param http HttpSecurity / HTTP security builder
     * @return SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF（REST API无需CSRF保护）/ Disable CSRF (not needed for REST API)
                .csrf(AbstractHttpConfigurer::disable)
                // 启用CORS / Enable CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 无状态会话管理 / Stateless session management
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 请求授权 / Request authorization
                .authorizeHttpRequests(auth -> auth
                        // 认证相关接口放行 / Permit auth endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        // 健康检查接口放行 / Permit health check endpoint
                        .requestMatchers("/api/health").permitAll()
                        // OpenAPI文档放行 / Permit OpenAPI docs
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                        // WebSocket放行 / Permit WebSocket
                        .requestMatchers("/ws/**").permitAll()
                        // Actuator健康检查放行 / Permit actuator health
                        .requestMatchers("/actuator/health").permitAll()
                        // 其他请求需要认证 / Other requests require authentication
                        .anyRequest().authenticated()
                )
                // 添加JWT过滤器 / Add JWT filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 密码编码器（BCrypt）
     * Password encoder (BCrypt)
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS配置
     * CORS configuration
     *
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> origins = Arrays.stream(corsAllowedOrigins.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        configuration.setAllowedOriginPatterns(origins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
