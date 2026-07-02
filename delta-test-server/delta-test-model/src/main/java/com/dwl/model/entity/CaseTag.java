package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用例标签实体
 * Case Tag Entity
 * <p>
 * 对应表 case_tag，定义测试用例的分类标签。
 * Maps to table case_tag, defining classification tags for test cases.
 * 此表无 updated_at 字段，不继承 BaseEntity，仅包含 createdAt。
 * This table has no updated_at column and does not extend BaseEntity, only contains createdAt.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("case_tag")
@Schema(description = "用例标签 / Case Tag")
public class CaseTag implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签ID
     * Tag ID
     */
    @Schema(description = "标签ID / Tag ID", example = "1")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     * Tag name
     */
    @Schema(description = "标签名称 / Tag name", example = "冒烟测试")
    private String tagName;

    /**
     * 标签颜色
     * Tag color
     */
    @Schema(description = "标签颜色 / Tag color", example = "#FF5722")
    private String tagColor;

    /**
     * 逻辑删除标志: 0-未删除 1-已删除
     * Logical delete flag: 0-not deleted, 1-deleted
     */
    @Schema(description = "逻辑删除标志: 0-未删除 1-已删除 / Logical delete flag: 0-not deleted, 1-deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 创建时间
     * Created timestamp
     */
    @Schema(description = "创建时间 / Created timestamp")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
