package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 测试数据集实体
 * Test Data Set Entity
 * <p>
 * 对应表 test_data_set，管理参数化测试数据集。
 * Maps to table test_data_set, managing parameterized test data sets.
 * dataJson 字段以 String 类型存储 JSON 格式内容。
 * The dataJson field stores JSON format content as a String type.
 * </p>
 *
 * @author ByDWL
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("test_data_set")
@Schema(description = "测试数据集 / Test Data Set")
public class TestDataSet extends BaseEntity {

    /**
     * 数据集名称
     * Data set name
     */
    @Schema(description = "数据集名称 / Data set name", example = "用户登录测试数据")
    private String setName;

    /**
     * 描述
     * Description
     */
    @Schema(description = "描述 / Description")
    private String description;

    /**
     * 数据内容(键值对JSON)，JSON格式内容以String存储
     * Data content (key-value JSON), stored as String for JSON content
     */
    @Schema(description = "数据内容(JSON格式) / Data content (JSON format)")
    private String dataJson;

    /**
     * 来源: auto/manual
     * Source: auto/manual
     */
    @Schema(description = "来源: auto/manual / Source", example = "manual")
    private String source;

    /**
     * 创建人ID
     * Creator user ID
     */
    @Schema(description = "创建人ID / Creator user ID")
    private Long createdBy;
}
