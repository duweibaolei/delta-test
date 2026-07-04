package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 用例标签实体
 * Case Tag Entity
 * <p>
 * 对应表 case_tag，定义测试用例的分类标签。
 * Maps to table case_tag, defining classification tags for test cases.
 * 继承 BaseEntity，包含公共审计字段。
 * Extends BaseEntity, including common audit fields.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("case_tag")
@Schema(description = "用例标签 / Case Tag")
public class CaseTag extends BaseEntity {

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
}
