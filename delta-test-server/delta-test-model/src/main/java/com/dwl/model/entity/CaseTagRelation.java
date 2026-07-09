package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 用例标签关联实体
 * Case-Tag Association Entity
 * <p>
 * 对应表 case_tag_relation，用例与标签的多对多关联。
 * Maps to table case_tag_relation, many-to-many association between cases and tags.
 * 继承 BaseEntity，包含公共审计字段。
 * Extends BaseEntity, including common audit fields.
 * </p>
 *
 * @author ByDWL
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("case_tag_relation")
@Schema(description = "用例标签关联 / Case-Tag Association")
public class CaseTagRelation extends BaseEntity {

    /**
     * 用例ID
     * Case ID
     */
    @Schema(description = "用例ID / Case ID", example = "1")
    private Long caseId;

    /**
     * 标签ID
     * Tag ID
     */
    @Schema(description = "标签ID / Tag ID", example = "1")
    private Long tagId;
}
