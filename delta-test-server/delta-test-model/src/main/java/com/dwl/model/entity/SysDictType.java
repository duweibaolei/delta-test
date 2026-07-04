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
 * 字典类型实体
 * Dictionary Type Entity
 * <p>
 * 对应表 sys_dict_type，管理系统中各类字典的分类定义。
 * Maps to table sys_dict_type, managing classification definitions for various system dictionaries.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dict_type")
@Schema(description = "字典类型 / Dictionary Type")
public class SysDictType extends BaseEntity {

    /**
     * 字典类型编码(唯一标识)
     * Dictionary type code (unique identifier)
     */
    @Schema(description = "字典类型编码 / Dictionary type code", example = "trigger_source")
    private String dictType;

    /**
     * 字典类型名称
     * Dictionary type name
     */
    @Schema(description = "字典类型名称 / Dictionary type name", example = "触发来源")
    private String dictName;

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
