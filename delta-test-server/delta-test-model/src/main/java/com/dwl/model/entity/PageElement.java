package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 页面元素对象库实体
 * Page Element Object Repository Entity
 * <p>
 * 对应表 page_element，管理被测系统的页面元素定位信息。
 * Maps to table page_element, managing page element locator information for the system under test.
 * </p>
 *
 * @author ByDWL
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("page_element")
@Schema(description = "页面元素对象库 / Page Element Object Repository")
public class PageElement extends BaseEntity {

    /**
     * 元素编码(别名)
     * Element code (alias)
     */
    @Schema(description = "元素编码(别名) / Element code (alias)", example = "btn_login")
    private String elementCode;

    /**
     * 元素名称
     * Element name
     */
    @Schema(description = "元素名称 / Element name", example = "登录按钮")
    private String elementName;

    /**
     * 所属页面
     * Page name
     */
    @Schema(description = "所属页面 / Page name", example = "登录页")
    private String pageName;

    /**
     * 主定位类型: css/xpath/id/data-testid/name
     * Primary locator type: css/xpath/id/data-testid/name
     */
    @Schema(description = "主定位类型: css/xpath/id/data-testid/name / Primary locator type", example = "css")
    private String locatorType;

    /**
     * 主定位值
     * Primary locator value
     */
    @Schema(description = "主定位值 / Primary locator value", example = "#login-btn")
    private String locatorValue;

    /**
     * 备份定位类型
     * Backup locator type
     */
    @Schema(description = "备份定位类型 / Backup locator type")
    private String backupLocatorType;

    /**
     * 备份定位值
     * Backup locator value
     */
    @Schema(description = "备份定位值 / Backup locator value")
    private String backupLocatorValue;

    /**
     * 描述
     * Description
     */
    @Schema(description = "描述 / Description")
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
}
