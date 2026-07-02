package com.dwl.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 链路节点实体
 * Link Node Entity
 * <p>
 * 对应表 link_node，定义业务链路中的各个节点。
 * Maps to table link_node, defining individual nodes within a business link.
 * 此表无 updated_at 字段，不继承 BaseEntity，仅包含 createdAt。
 * This table has no updated_at column and does not extend BaseEntity, only contains createdAt.
 * </p>
 *
 * @author DeltaTest
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("link_node")
@Schema(description = "链路节点 / Link Node")
public class LinkNode implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     * Node ID
     */
    @Schema(description = "节点ID / Node ID", example = "1")
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /**
     * 逻辑删除标志: 0-未删除 1-已删除
     * Logical delete flag: 0-not deleted, 1-deleted
     */
    @Schema(description = "逻辑删除标志: 0-未删除 1-已删除 / Logical delete flag: 0-not deleted, 1-deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 创建时间
     * Created timestamp
     */
    @Schema(description = "创建时间 / Created timestamp")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
