package com.dwl.mq.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 任务日志消息 DTO
 * Task Log Message DTO
 *
 * @author ByDWL
 */
@Data
@Schema(description = "任务日志消息 / Task Log Message")
public class TaskLogMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 任务ID / Task ID */
    @Schema(description = "任务ID / Task ID", example = "1")
    private Long taskId;

    /** 日志行 / Log line */
    @Schema(description = "日志行 / Log line", example = "[INFO] Starting test case: TC-001")
    private String logLine;

    /** 时间戳 / Timestamp */
    @Schema(description = "时间戳 / Timestamp", example = "1704067200000")
    private Long timestamp;
}
