package com.dwl.ai.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AiServiceConfig 单元测试
 * AiServiceConfig Unit Tests
 *
 * @author ByDWL
 */
@DisplayName("AiServiceConfig 单元测试 / AiServiceConfig Unit Tests")
class AiServiceConfigTest {

    @Test
    @DisplayName("aiRestTemplate Bean — 使用默认配置创建RestTemplate / aiRestTemplate Bean - creates RestTemplate with default config")
    void aiRestTemplate_defaultConfig_createsRestTemplate() {
        // 使用系统默认属性值测试（@Value 在非Spring上下文中不会被注入）
        AiServiceConfig config = new AiServiceConfig();
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = config.aiRestTemplate(builder);

        assertThat(restTemplate).isNotNull();
    }

    @Test
    @DisplayName("aiRestTemplate Bean — RestTemplate非空 / aiRestTemplate Bean - RestTemplate is not null")
    void aiRestTemplate_returnsNonNull() {
        AiServiceConfig config = new AiServiceConfig();
        RestTemplateBuilder builder = new RestTemplateBuilder();

        assertThat(config.aiRestTemplate(builder)).isInstanceOf(RestTemplate.class);
    }
}
