package com.dwl.model.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * Base Entity Class
 * <p>
 * 所有实体类的公共父类，包含主键、创建/更新信息、逻辑删除标识。
 * Common parent class for all entity classes, containing primary key,
 * creation/update info, and logical delete flag.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Schema(description = "基础实体类 / Base Entity Class")
public abstract class BaseEntity implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     * Primary key ID
     */
    @Schema(description = "主键ID / Primary key ID", example = "1")
    @TableId
    private Long id;

    /**
     * 创建人
     * Created by
     */
    @Schema(description = "创建人 / Created by", example = "admin")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     * Creation time
     */
    @Schema(description = "创建时间 / Creation time", example = "2024-01-01T00:00:00")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     * Updated by
     */
    @Schema(description = "更新人 / Updated by", example = "admin")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     * Update time
     */
    @Schema(description = "更新时间 / Update time", example = "2024-01-01T00:00:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标识（0-未删除，1-已删除）
     * Logical delete flag (0-not deleted, 1-deleted)
     */
    @Schema(description = "逻辑删除标识 / Logical delete flag", example = "0")
    @TableLogic
    private Integer deleted;
}
