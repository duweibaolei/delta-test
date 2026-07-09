package com.dwl.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典数据视图对象 VO
 * Dictionary Data View Object
 *
 * @author ByDWL
 */
@Data
@Schema(description = "字典数据视图对象 / Dictionary Data View Object")
public class DictDataVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 字典数据ID / Dictionary data ID */
    @Schema(description = "字典数据ID / Dictionary data ID", example = "1")
    private Long id;

    /** 字典类型编码 / Dictionary type code */
    @Schema(description = "字典类型编码 / Dictionary type code", example = "browser_type")
    private String dictType;

    /** 字典标签 / Dictionary label */
    @Schema(description = "字典标签 / Dictionary label", example = "Chrome")
    private String dictLabel;

    /** 字典值 / Dictionary value */
    @Schema(description = "字典值 / Dictionary value", example = "chrome")
    private String dictValue;

    /** 排序号 / Sort order */
    @Schema(description = "排序号 / Sort order", example = "1")
    private Integer sortOrder;

    /** 状态 / Status */
    @Schema(description = "状态 / Status", example = "0")
    private Integer status;

    /** 备注 / Remark */
    @Schema(description = "备注 / Remark")
    private String remark;
}
