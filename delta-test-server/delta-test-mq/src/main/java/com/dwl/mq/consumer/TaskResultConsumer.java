package com.dwl.mq.consumer;

import com.dwl.mq.config.RabbitMqConfig;
import com.dwl.mq.message.TaskResultMessage;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 任务结果消息消费者
 * Task Result Message Consumer
 * <p>
 * 消费任务执行结果消息，更新执行状态和报告。
 * Consumes task execution result messages, updates execution status and reports.
 * </p>
 *
 * @author ByDWL
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskResultConsumer {

    /**
     * 监听任务结果队列
     * Listen to task result queue
     *
     * @param message 任务结果消息 / Task result message
     * @param channel RabbitMQ通道 / RabbitMQ channel
     * @param amqpMessage AMQP原始消息 / Raw AMQP message
     */
    @RabbitListener(queues = RabbitMqConfig.TASK_RESULT_QUEUE)
    public void onTaskResult(TaskResultMessage message, Channel channel, Message amqpMessage) {
        try {
            log.info("收到任务结果消息，任务ID: {}, 状态: {} / Received task result message, task ID: {}, status: {}",
                    message.getTaskId(), message.getStatus());

            // TODO: 处理任务结果 / Process task result
            // 1. 更新执行记录状态 / Update execution record status
            // 2. 更新任务状态 / Update task status
            // 3. 通知WebSocket推送 / Notify WebSocket push
            // 4. 触发报告生成 / Trigger report generation

            // 手动确认消息 / Manual acknowledge
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("处理任务结果消息失败: {} / Failed to process task result message: {}", e.getMessage());
            try {
                // 拒绝消息，重新入队 / Reject message, requeue
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
            } catch (Exception ex) {
                log.error("消息确认失败: {} / Message ack failed: {}", ex.getMessage());
            }
        }
    }
}
