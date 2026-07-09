package com.dwl.web.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket 配置类
 * WebSocket Configuration
 * <p>
 * 注册WebSocket端点，配置允许的跨域来源。
 * Registers WebSocket endpoints and configures allowed origins.
 * </p>
 *
 * @author ByDWL
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    /** 任务进度WebSocket处理器 / Task progress WebSocket handler */
    private final TaskProgressWebSocketHandler taskProgressWebSocketHandler;

    /**
     * 注册WebSocket处理器
     * Register WebSocket handlers
     *
     * @param registry WebSocket处理器注册器 / WebSocket handler registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(taskProgressWebSocketHandler, "/ws/task-progress")
                .setAllowedOrigins("*");
    }
}
