package com.dwl.ai.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * AI服务统一响应体
 * AI Service Unified Response Body
 * <p>
 * 与 Python 端 R[T] 完全对齐的响应格式，用于反序列化 AI 服务的 HTTP 响应。
 * 包含 code、message、data、timestamp 四个字段，与 Java 端 R&lt;T&gt; 和 Python 端 R[T] 结构一致。
 * Unified response format fully aligned with Python R[T], used to deserialize AI service HTTP responses.
 * Contains code, message, data, timestamp fields, consistent with Java R&lt;T&gt; and Python R[T] structure.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI服务统一响应体 / AI Service Unified Response Body")
public class AiResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应状态码
     * Response status code
     */
    @Schema(description = "响应状态码 / Response status code", example = "200")
    private int code;

    /**
     * 响应消息
     * Response message
     */
    @Schema(description = "响应消息 / Response message", example = "success")
    private String message;

    /**
     * 响应数据
     * Response data
     */
    @Schema(description = "响应数据 / Response data")
    private Map<String, Object> data;

    /**
     * 响应时间戳（毫秒）
     * Response timestamp (milliseconds)
     */
    @Schema(description = "响应时间戳(毫秒) / Response timestamp (ms)", example = "1719936000000")
    private long timestamp;

    /**
     * 判断响应是否成功
     * Check if the response is successful
     *
     * @return true 如果状态码为200 / true if status code is 200
     */
    public boolean isSuccess() {
        return code == 200;
    }
}
