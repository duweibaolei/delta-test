package com.dwl.ai.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * AI服务HTTP客户端配置类
 * AI Service HTTP Client Configuration
 * <p>
 * 配置与Python AI服务的HTTP连接。
 * 连接超时5秒，读取超时60秒，遵循跨语言接口协议规范。
 * Configures HTTP connection to the Python AI service.
 * Connection timeout 5s, read timeout 60s, following cross-language interface protocol specification.
 * </p>
 *
 * @author DeltaTest
 */
@Slf4j
@Configuration
public class AiServiceConfig {

    /** AI服务基础URL / AI service base URL */
    @Value("${ai.service.url:http://localhost:8000}")
    private String aiServiceUrl;

    /** 连接超时（秒） / Connection timeout (seconds) */
    @Value("${ai.service.connect-timeout:5}")
    private int connectTimeout;

    /** 读取超时（秒） / Read timeout (seconds) */
    @Value("${ai.service.read-timeout:60}")
    private int readTimeout;

    /**
     * AI服务RestTemplate
     * AI Service RestTemplate
     * <p>
     * 创建用于调用Python AI服务的RestTemplate实例。
     * 连接超时5秒，读取超时60秒。
     * Creates a RestTemplate instance for calling the Python AI service.
     * Connection timeout 5s, read timeout 60s.
     * </p>
     *
     * @return RestTemplate 配置了AI服务超时的RestTemplate / RestTemplate configured with AI service timeouts
     */
    @Bean
    public RestTemplate aiRestTemplate(RestTemplateBuilder builder) {
        log.info("初始化AI服务RestTemplate: url={}, connectTimeout={}s, readTimeout={}s / " +
                "Initializing AI service RestTemplate: url={}, connectTimeout={}s, readTimeout={}s",
                aiServiceUrl, connectTimeout, readTimeout,
                aiServiceUrl, connectTimeout, readTimeout);

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(connectTimeout));
        factory.setReadTimeout(Duration.ofSeconds(readTimeout));

        return builder
                .rootUri(aiServiceUrl)
                .requestFactory(() -> factory)
                .build();
    }
}
