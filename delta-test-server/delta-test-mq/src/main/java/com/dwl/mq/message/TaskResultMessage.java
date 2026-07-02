package com.dwl.mq.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 任务结果消息 DTO
 * Task Result Message DTO
 *
 * @author DeltaTest
 */
@Data
@Schema(description = "任务结果消息 / Task Result Message")
public class TaskResultMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 任务ID / Task ID */
    @Schema(description = "任务ID / Task ID", example = "1")
    private Long taskId;

    /** 执行记录ID / Execution record ID */
    @Schema(description = "执行记录ID / Execution record ID", example = "1")
    private Long executionId;

    /** 状态 / Status */
    @Schema(description = "状态（pass/fail/error）/ Status", example = "pass")
    private String status;

    /** 错误信息 / Error message */
    @Schema(description = "错误信息 / Error message")
    private String errorMessage;

    /** 截图URL / Screenshot URL */
    @Schema(description = "截图URL / Screenshot URL")
    private String screenshotUrl;

    /** 视频URL / Video URL */
    @Schema(description = "视频URL / Video URL")
    private String videoUrl;
}
