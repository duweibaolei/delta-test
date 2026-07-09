package com.dwl;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DeltaTest 应用启动类
 * DeltaTest Application Main Class
 * <p>
 * 双模式驱动的Web自动化测试平台服务端入口。
 * Entry point for the DeltaTest dual-mode Web automation testing platform server.
 * </p>
 *
 * @author ByDWL
 */
@SpringBootApplication
@MapperScan("com.dwl.dao.mapper")
@OpenAPIDefinition(info = @Info(
        title = "DeltaTest API",
        version = "1.0.0",
        description = "双模式驱动的Web自动化测试平台 API / DeltaTest Dual-mode Web Automation Testing Platform API"
))
public class DeltaTestApplication {

    /**
     * 应用入口方法
     * Application entry point
     *
     * @param args 命令行参数 / Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DeltaTestApplication.class, args);
    }
}
