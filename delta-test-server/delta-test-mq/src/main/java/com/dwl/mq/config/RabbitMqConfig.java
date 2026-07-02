package com.dwl.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 配置类
 * RabbitMQ Configuration
 * <p>
 * 定义交换机、队列、绑定关系和消息转换器。
 * Defines exchanges, queues, bindings, and message converter.
 * </p>
 *
 * @author DeltaTest
 */
@Configuration
public class RabbitMqConfig {

    /** 交换机名称 / Exchange name */
    public static final String EXCHANGE = "delta.test.exchange";

    /** 任务执行队列 / Task execution queue */
    public static final String TASK_EXECUTE_QUEUE = "delta.test.task.execute";

    /** 任务结果队列 / Task result queue */
    public static final String TASK_RESULT_QUEUE = "delta.test.task.result";

    /** 任务日志队列 / Task log queue */
    public static final String TASK_LOG_QUEUE = "delta.test.task.log";

    /** 任务执行路由键 / Task execute routing key */
    public static final String TASK_EXECUTE_KEY = "task.execute";

    /** 任务结果路由键 / Task result routing key */
    public static final String TASK_RESULT_KEY = "task.result";

    /** 任务日志路由键 / Task log routing key */
    public static final String TASK_LOG_KEY = "task.log";

    /**
     * 主题交换机
     * Topic exchange
     *
     * @return TopicExchange
     */
    @Bean
    public TopicExchange deltaTestExchange() {
        return new TopicExchange(EXCHANGE);
    }

    /**
     * 任务执行队列
     * Task execute queue
     *
     * @return Queue
     */
    @Bean
    public Queue taskExecuteQueue() {
        return new Queue(TASK_EXECUTE_QUEUE, true);
    }

    /**
     * 任务结果队列
     * Task result queue
     *
     * @return Queue
     */
    @Bean
    public Queue taskResultQueue() {
        return new Queue(TASK_RESULT_QUEUE, true);
    }

    /**
     * 任务日志队列
     * Task log queue
     *
     * @return Queue
     */
    @Bean
    public Queue taskLogQueue() {
        return new Queue(TASK_LOG_QUEUE, true);
    }

    /**
     * 任务执行队列绑定交换机
     * Bind task execute queue to exchange
     *
     * @return Binding
     */
    @Bean
    public Binding taskExecuteBinding() {
        return BindingBuilder.bind(taskExecuteQueue()).to(deltaTestExchange()).with(TASK_EXECUTE_KEY);
    }

    /**
     * 任务结果队列绑定交换机
     * Bind task result queue to exchange
     *
     * @return Binding
     */
    @Bean
    public Binding taskResultBinding() {
        return BindingBuilder.bind(taskResultQueue()).to(deltaTestExchange()).with(TASK_RESULT_KEY);
    }

    /**
     * 任务日志队列绑定交换机
     * Bind task log queue to exchange
     *
     * @return Binding
     */
    @Bean
    public Binding taskLogBinding() {
        return BindingBuilder.bind(taskLogQueue()).to(deltaTestExchange()).with(TASK_LOG_KEY);
    }

    /**
     * JSON消息转换器
     * JSON message converter
     *
     * @return MessageConverter
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
