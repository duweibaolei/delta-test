package com.dwl.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典类型视图对象 VO
 * Dictionary Type View Object
 *
 * @author ByDWL
 */
@Data
@Schema(description = "字典类型视图对象 / Dictionary Type View Object")
public class DictTypeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 字典类型ID / Dictionary type ID */
    @Schema(description = "字典类型ID / Dictionary type ID", example = "1")
    private Long id;

    /** 字典类型编码 / Dictionary type code */
    @Schema(description = "字典类型编码 / Dictionary type code", example = "browser_type")
    private String dictType;

    /** 字典类型名称 / Dictionary type name */
    @Schema(description = "字典类型名称 / Dictionary type name", example = "浏览器类型")
    private String dictName;

    /** 是否系统内置 / Is system built-in */
    @Schema(description = "是否系统内置 / Is system built-in", example = "0")
    private Integer isSystem;

    /** 备注 / Remark */
    @Schema(description = "备注 / Remark")
    private String remark;

    /** 创建时间 / Creation time */
    @Schema(description = "创建时间 / Creation time")
    private LocalDateTime createTime;
}
