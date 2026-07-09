package com.dwl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户状态更新 DTO
 * User Status Update DTO
 *
 * @author ByDWL
 */
@Data
@Schema(description = "用户状态更新请求 / User Status Update Request")
public class UserStatusDTO implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态: 1-启用 0-禁用
     * Status: 1-enabled, 0-disabled
     */
    @Schema(description = "状态: 1-启用 0-禁用 / Status: 1-enabled, 0-disabled", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态不能为空 / Status cannot be null")
    private Integer status;
}
