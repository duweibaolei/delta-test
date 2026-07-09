package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dwl.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 链路节点实体
 * Link Node Entity
 * <p>
 * 对应表 link_node，定义业务链路中的各个节点。
 * Maps to table link_node, defining individual nodes within a business link.
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
@TableName("link_node")
@Schema(description = "链路节点 / Link Node")
public class LinkNode extends BaseEntity {

    /**
     * 所属链路ID
     * Link ID
     */
    @Schema(description = "所属链路ID / Link ID", example = "1")
    private Long linkId;

    /**
     * 节点顺序
     * Node order
     */
    @Schema(description = "节点顺序 / Node order", example = "1")
    private Integer nodeOrder;

    /**
     * 节点类型: frontend_page/backend_api/backend_service/database_table
     * Node type: frontend_page/backend_api/backend_service/database_table
     */
    @Schema(description = "节点类型 / Node type", example = "frontend_page")
    private String nodeType;

    /**
     * 节点名称
     * Node name
     */
    @Schema(description = "节点名称 / Node name", example = "登录页面")
    private String nodeName;

    /**
     * 节点标识(如接口路径、表名)
     * Node identifier (e.g., API path, table name)
     */
    @Schema(description = "节点标识 / Node identifier", example = "/api/auth/login")
    private String nodeIdentifier;

    /**
     * 断言规则
     * Assert rule
     */
    @Schema(description = "断言规则 / Assert rule")
    private String assertRule;
}
