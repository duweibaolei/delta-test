package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用例与链路关联实体
 * Case-Link Association Entity
 * <p>
 * 对应表 case_link_relation，用例与业务链路的多对多关联。
 * Maps to table case_link_relation, many-to-many association between cases and business links.
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
@TableName("case_link_relation")
@Schema(description = "用例与链路关联 / Case-Link Association")
public class CaseLinkRelation extends BaseEntity {

    /**
     * 用例ID
     * Case ID
     */
    @Schema(description = "用例ID / Case ID", example = "1")
    private Long caseId;

    /**
     * 链路ID
     * Link ID
     */
    @Schema(description = "链路ID / Link ID", example = "1")
    private Long linkId;
}
