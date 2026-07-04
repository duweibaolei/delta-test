package com.dwl.mq.producer;

import com.dwl.model.dto.ExecConfig;
import com.dwl.mq.config.RabbitMqConfig;
import com.dwl.mq.message.TaskExecuteMessage;
import com.dwl.mq.message.TaskLogMessage;
import com.dwl.mq.message.TaskResultMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 任务消息生产者
 * Task Message Producer
 * <p>
 * 发送任务执行、结果和日志消息到RabbitMQ。
 * Sends task execution, result, and log messages to RabbitMQ.
 * </p>
 *
 * @author DeltaTest
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskMessageProducer {

    /** RabbitMQ模板 / RabbitMQ template */
    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送任务执行消息
     * Send task execute message
     *
     * @param taskId  任务ID / Task ID
     * @param caseIds 用例ID列表 / Case ID list
     * @param config  执行配置 / Execution configuration
     */
    public void sendTaskExecute(Long taskId, List<Long> caseIds, ExecConfig config) {
        TaskExecuteMessage message = new TaskExecuteMessage();
        message.setTaskId(taskId);
        message.setCaseIds(caseIds);
        message.setEnvId(config.getEnvId());
        message.setBrowserType(config.getBrowserType());
        message.setConcurrency(config.getConcurrency());
        message.setRetryCount(config.getRetryCount());

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE,
                RabbitMqConfig.TASK_EXECUTE_KEY,
                message
        );

        log.info("发送任务执行消息，任务ID: {} / Sent task execute message, task ID: {}", taskId);
    }

    /**
     * 发送任务结果消息
     * Send task result message
     *
     * @param taskId       任务ID / Task ID
     * @param executionId  执行记录ID / Execution record ID
     * @param status       状态 / Status
     * @param errorMessage 错误信息 / Error message
     */
    public void sendTaskResult(Long taskId, Long executionId, String status, String errorMessage) {
        TaskResultMessage message = new TaskResultMessage();
        message.setTaskId(taskId);
        message.setExecutionId(executionId);
        message.setStatus(status);
        message.setErrorMessage(errorMessage);

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE,
                RabbitMqConfig.TASK_RESULT_KEY,
                message
        );

        log.info("发送任务结果消息，任务ID: {} / Sent task result message, task ID: {}", taskId);
    }

    /**
     * 发送任务日志消息
     * Send task log message
     *
     * @param taskId  任务ID / Task ID
     * @param logLine 日志行 / Log line
     */
    public void sendTaskLog(Long taskId, String logLine) {
        TaskLogMessage message = new TaskLogMessage();
        message.setTaskId(taskId);
        message.setLogLine(logLine);
        message.setTimestamp(System.currentTimeMillis());

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE,
                RabbitMqConfig.TASK_LOG_KEY,
                message
        );

        log.debug("发送任务日志消息，任务ID: {} / Sent task log message, task ID: {}", taskId);
    }
}
