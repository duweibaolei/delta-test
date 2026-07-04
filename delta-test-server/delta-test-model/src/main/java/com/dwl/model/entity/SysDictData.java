package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典数据实体
 * Dictionary Data Entity
 * <p>
 * 对应表 sys_dict_data，存储字典类型下的具体键值对数据项。
 * Maps to table sys_dict_data, storing specific key-value data items under dictionary types.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dict_data")
@Schema(description = "字典数据 / Dictionary Data")
public class SysDictData extends BaseEntity {

    /**
     * 所属字典类型编码
     * Dictionary type code (foreign key to sys_dict_type)
     */
    @Schema(description = "所属字典类型编码 / Dictionary type code", example = "trigger_source")
    private String dictType;

    /**
     * 字典标签(显示值)
     * Dictionary label (display value)
     */
    @Schema(description = "字典标签(显示值) / Dictionary label (display value)", example = "手动触发")
    private String dictLabel;

    /**
     * 字典值(实际值)
     * Dictionary value (actual value)
     */
    @Schema(description = "字典值(实际值) / Dictionary value (actual value)", example = "manual")
    private String dictValue;

    /**
     * 排序(升序)
     * Sort order (ascending)
     */
    @Schema(description = "排序(升序) / Sort order (ascending)", example = "1")
    private Integer sortOrder;

    /**
     * 样式属性(如标签颜色)
     * CSS class (e.g., tag color)
     */
    @Schema(description = "样式属性 / CSS class", example = "tag-blue")
    private String cssClass;

    /**
     * 描述
     * Description
     */
    @Schema(description = "描述 / Description")
    private String description;

    /**
     * 状态: 1-启用 0-禁用
     * Status: 1-enabled, 0-disabled
     */
    @Schema(description = "状态: 1-启用 0-禁用 / Status: 1-enabled, 0-disabled", example = "1")
    private Integer status;

    /**
     * 创建人ID
     * Creator user ID
     */
    @Schema(description = "创建人ID / Creator user ID")
    private Long createdBy;
}
