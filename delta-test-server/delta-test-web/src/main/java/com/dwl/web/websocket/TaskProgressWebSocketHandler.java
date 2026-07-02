package com.dwl.web.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务进度 WebSocket 处理器
 * Task Progress WebSocket Handler
 * <p>
 * 推送任务执行进度和日志信息给前端客户端。
 * Pushes task execution progress and log information to frontend clients.
 * </p>
 *
 * @author DeltaTest
 */
@Slf4j
@Component
public class TaskProgressWebSocketHandler extends TextWebSocketHandler {

    /** 已连接的会话 / Connected sessions */
    private static final ConcurrentHashMap<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    /**
     * 连接建立后调用
     * Called after connection established
     *
     * @param session WebSocket会话 / WebSocket session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        SESSIONS.put(session.getId(), session);
        log.info("WebSocket连接建立: {} / WebSocket connection established: {}", session.getId());
    }

    /**
     * 接收到消息后调用
     * Called after message received
     *
     * @param session WebSocket会话 / WebSocket session
     * @param message 文本消息 / Text message
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.debug("收到WebSocket消息: {} / Received WebSocket message: {}", message.getPayload());
    }

    /**
     * 连接关闭后调用
     * Called after connection closed
     *
     * @param session WebSocket会话 / WebSocket session
     * @param status  关闭状态 / Close status
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        SESSIONS.remove(session.getId());
        log.info("WebSocket连接关闭: {} / WebSocket connection closed: {}", session.getId());
    }

    /**
     * 发生错误时调用
     * Called on transport error
     *
     * @param session   WebSocket会话 / WebSocket session
     * @param exception 异常 / Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("WebSocket传输错误: {} / WebSocket transport error: {}", session.getId(), exception.getMessage());
        SESSIONS.remove(session.getId());
    }

    /**
     * 向所有连接的客户端广播消息
     * Broadcast message to all connected clients
     *
     * @param message 消息内容 / Message content
     */
    public void broadcast(String message) {
        TextMessage textMessage = new TextMessage(message);
        SESSIONS.forEach((id, session) -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(textMessage);
                }
            } catch (IOException e) {
                log.error("广播WebSocket消息失败: {} / Failed to broadcast WebSocket message: {}", id, e.getMessage());
            }
        });
    }

    /**
     * 向指定会话发送消息
     * Send message to specific session
     *
     * @param sessionId 会话ID / Session ID
     * @param message   消息内容 / Message content
     */
    public void sendToSession(String sessionId, String message) {
        WebSocketSession session = SESSIONS.get(sessionId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("发送WebSocket消息失败: {} / Failed to send WebSocket message: {}", sessionId, e.getMessage());
            }
        }
    }
}
