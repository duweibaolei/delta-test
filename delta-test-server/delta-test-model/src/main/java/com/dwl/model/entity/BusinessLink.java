package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 业务链路实体
 * Business Link Entity
 * <p>
 * 对应表 business_link，定义端到端的业务链路及其元信息。
 * Maps to table business_link, defining end-to-end business links and their metadata.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("business_link")
@Schema(description = "业务链路 / Business Link")
public class BusinessLink extends BaseEntity {

    /**
     * 链路编号: BL-NNNN
     * Link number: BL-NNNN
     */
    @Schema(description = "链路编号: BL-NNNN / Link number", example = "BL-0001")
    private String linkNo;

    /**
     * 链路名称
     * Link name
     */
    @Schema(description = "链路名称 / Link name", example = "用户注册到首次购买全流程")
    private String linkName;

    /**
     * 链路描述
     * Link description
     */
    @Schema(description = "链路描述 / Link description")
    private String description;

    /**
     * 来源: auto/manual/hybrid
     * Source: auto/manual/hybrid
     */
    @Schema(description = "来源: auto/manual/hybrid / Source", example = "manual")
    private String source;

    /**
     * 状态: 1-启用 0-禁用
     * Status: 1-enabled, 0-disabled
     */
    @Schema(description = "状态: 1-启用 0-禁用 / Status: 1-enabled, 0-disabled", example = "1")
    private Integer status;

    /**
     * 版本号(乐观锁)
     * Version number (optimistic lock)
     */
    @Schema(description = "版本号(乐观锁) / Version number (optimistic lock)", example = "1")
    @Version
    private Integer version;

    /**
     * 创建人ID
     * Creator user ID
     */
    @Schema(description = "创建人ID / Creator user ID")
    private Long createdBy;

    /**
     * 最后修改人ID
     * Last modifier user ID
     */
    @Schema(description = "最后修改人ID / Last modifier user ID")
    private Long lastModifiedBy;
}
