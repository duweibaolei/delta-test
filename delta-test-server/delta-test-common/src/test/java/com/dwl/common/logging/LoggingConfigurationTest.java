package com.dwl.common.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JSON 结构化日志配置验证测试
 * JSON Structured Logging Configuration Verification Test
 *
 * @author ByDWL
 */
@DisplayName("JSON结构化日志配置 / JSON Structured Logging Configuration")
class LoggingConfigurationTest {

    @Test
    @DisplayName("logstash-logback-encoder 可正常加载 / logstash-logback-encoder loads correctly")
    void logstashEncoder_loadsCorrectly() {
        // 验证 LogstashEncoder 类可实例化 / Verify LogstashEncoder class can be instantiated
        LogstashEncoder encoder = new LogstashEncoder();
        assertNotNull(encoder);
    }

    @Test
    @DisplayName("Logback 上下文可正常初始化 / Logback context initializes correctly")
    void logbackContext_initializesCorrectly() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        assertNotNull(context);

        // 验证 ROOT logger 存在 / Verify ROOT logger exists
        ch.qos.logback.classic.Logger rootLogger = context.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        assertNotNull(rootLogger);
    }

    @Test
    @DisplayName("LogstashEncoder 支持自定义字段 / LogstashEncoder supports custom fields")
    void logstashEncoder_supportsCustomFields() {
        LogstashEncoder encoder = new LogstashEncoder();
        encoder.setCustomFields("{\"service\":\"delta-test-server\"}");
        // 不抛出异常即通过 / Passes if no exception is thrown
        assertNotNull(encoder);
    }
}
