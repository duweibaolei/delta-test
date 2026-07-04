package com.dwl.web.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc OpenAPI 配置类
 * SpringDoc OpenAPI Configuration
 * <p>
 * 配置API文档信息和安全方案定义（Bearer JWT）。
 * Configures API documentation info and security scheme definition (Bearer JWT).
 * </p>
 *
 * @author DeltaTest
 */
@Configuration
public class OpenApiConfig {

    /**
     * OpenAPI 配置
     * OpenAPI configuration
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DeltaTest API")
                        .description("双模式驱动的Web自动化测试平台 API 文档 / DeltaTest - Dual-mode Web Automation Testing Platform API Documentation")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer JWT"))
                .components(new Components()
                        .addSecuritySchemes("Bearer JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("请输入JWT令牌 / Enter JWT token")));
    }
}
