package com.dwl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 重置密码 DTO
 * Reset Password DTO
 *
 * @author ByDWL
 */
@Data
@Schema(description = "重置密码请求 / Reset Password Request")
public class ResetPasswordDTO implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 新密码
     * New password
     */
    @Schema(description = "新密码 / New password", example = "******", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "新密码不能为空 / New password cannot be empty")
    private String newPassword;
}
