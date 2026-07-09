package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 测试用例实体
 * Test Case Entity
 * <p>
 * 对应表 test_case，存储测试用例的基本信息和元数据。
 * Maps to table test_case, storing basic information and metadata for test cases.
 * </p>
 *
 * @author ByDWL
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("test_case")
@Schema(description = "测试用例 / Test Case")
public class TestCase extends BaseEntity {

    /**
     * 用例编号: TC-NNNN
     * Case number: TC-NNNN
     */
    @Schema(description = "用例编号: TC-NNNN / Case number", example = "TC-0001")
    private String caseNo;

    /**
     * 用例名称
     * Case name
     */
    @Schema(description = "用例名称 / Case name", example = "用户登录-正常流程")
    private String caseName;

    /**
     * 所属模块
     * Module name
     */
    @Schema(description = "所属模块 / Module name", example = "用户管理")
    private String moduleName;

    /**
     * 来源: auto/manual/hybrid
     * Source: auto/manual/hybrid
     */
    @Schema(description = "来源: auto/manual/hybrid / Source", example = "manual")
    private String source;

    /**
     * 状态: active-正常 unstable-不稳定 disabled-失效 draft-草稿
     * Status: active/unstable/disabled/draft
     */
    @Schema(description = "状态: active/unstable/disabled/draft / Status", example = "active")
    private String status;

    /**
     * 健康度评分(0-100)
     * Health score (0-100)
     */
    @Schema(description = "健康度评分(0-100) / Health score (0-100)", example = "100")
    private Integer healthScore;

    /**
     * 优先级: P0/P1/P2
     * Priority: P0/P1/P2
     */
    @Schema(description = "优先级: P0/P1/P2 / Priority", example = "P2")
    private String priority;

    /**
     * 版本号(乐观锁)
     * Version number (optimistic lock)
     */
    @Schema(description = "版本号(乐观锁) / Version number (optimistic lock)", example = "1")
    @Version
    private Integer version;

    /**
     * 用例描述
     * Case description
     */
    @Schema(description = "用例描述 / Case description")
    private String description;

    /**
     * 前置条件
     * Pre-condition
     */
    @Schema(description = "前置条件 / Pre-condition")
    private String preCondition;

    /**
     * 执行环境ID
     * Execution environment ID
     */
    @Schema(description = "执行环境ID / Execution environment ID")
    private Long envId;

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
