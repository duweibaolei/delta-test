package com.dwl.mq.consumer;

import com.dwl.mq.config.RabbitMqConfig;
import com.dwl.mq.message.TaskLogMessage;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 任务日志消息消费者
 * Task Log Message Consumer
 * <p>
 * 消费任务执行日志消息，推送到WebSocket客户端。
 * Consumes task execution log messages, pushes to WebSocket clients.
 * </p>
 *
 * @author DeltaTest
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskLogConsumer {

    /**
     * 监听任务日志队列
     * Listen to task log queue
     *
     * @param message     任务日志消息 / Task log message
     * @param channel     RabbitMQ通道 / RabbitMQ channel
     * @param amqpMessage AMQP原始消息 / Raw AMQP message
     */
    @RabbitListener(queues = RabbitMqConfig.TASK_LOG_QUEUE)
    public void onTaskLog(TaskLogMessage message, Channel channel, Message amqpMessage) {
        try {
            log.debug("收到任务日志消息，任务ID: {} / Received task log message, task ID: {}", message.getTaskId());

            // TODO: 处理任务日志 / Process task log
            // 1. 存储日志到数据库 / Store log to database
            // 2. 通过WebSocket推送日志 / Push log via WebSocket

            // 手动确认消息 / Manual acknowledge
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("处理任务日志消息失败: {} / Failed to process task log message: {}", e.getMessage());
            try {
                // 拒绝消息，重新入队 / Reject message, requeue
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
            } catch (Exception ex) {
                log.error("消息确认失败: {} / Message ack failed: {}", ex.getMessage());
            }
        }
    }
}
