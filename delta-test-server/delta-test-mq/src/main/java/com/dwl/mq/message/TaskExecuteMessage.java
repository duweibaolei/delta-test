package com.dwl.mq.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 任务执行消息 DTO
 * Task Execute Message DTO
 *
 * @author DeltaTest
 */
@Data
@Schema(description = "任务执行消息 / Task Execute Message")
public class TaskExecuteMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 任务ID / Task ID */
    @Schema(description = "任务ID / Task ID", example = "1")
    private Long taskId;

    /** 用例ID列表 / Case ID list */
    @Schema(description = "用例ID列表 / Case ID list", example = "[1, 2, 3]")
    private List<Long> caseIds;

    /** 环境ID / Environment ID */
    @Schema(description = "环境ID / Environment ID", example = "1")
    private Long envId;

    /** 浏览器类型 / Browser type */
    @Schema(description = "浏览器类型 / Browser type", example = "chrome")
    private String browserType;

    /** 并发数 / Concurrency */
    @Schema(description = "并发数 / Concurrency", example = "3")
    private Integer concurrency;

    /** 重试次数 / Retry count */
    @Schema(description = "重试次数 / Retry count", example = "1")
    private Integer retryCount;
}
