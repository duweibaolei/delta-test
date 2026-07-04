package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 环境变量实体
 * Environment Variable Entity
 * <p>
 * 对应表 env_variable，管理各环境下的变量键值对配置。
 * Maps to table env_variable, managing variable key-value configurations for each environment.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("env_variable")
@Schema(description = "环境变量 / Environment Variable")
public class EnvVariable extends BaseEntity {

    /**
     * 环境ID(NULL表示全局)
     * Environment ID (NULL means global)
     */
    @Schema(description = "环境ID(NULL表示全局) / Environment ID (NULL for global)")
    private Long envId;

    /**
     * 变量键
     * Variable key
     */
    @Schema(description = "变量键 / Variable key", example = "BASE_URL")
    private String varKey;

    /**
     * 变量值
     * Variable value
     */
    @Schema(description = "变量值 / Variable value", example = "https://staging.example.com")
    private String varValue;

    /**
     * 描述
     * Description
     */
    @Schema(description = "描述 / Description")
    private String description;
}
