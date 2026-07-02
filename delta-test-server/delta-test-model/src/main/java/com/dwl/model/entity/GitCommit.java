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
 * Git提交记录实体
 * Git Commit Record Entity
 * <p>
 * 对应表 git_commit，记录Git仓库的提交变更信息。
 * Maps to table git_commit, recording Git repository commit change information.
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
@TableName("git_commit")
@Schema(description = "Git提交记录 / Git Commit Record")
public class GitCommit implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 提交记录ID
     * Commit record ID
     */
    @Schema(description = "提交记录ID / Commit record ID", example = "1")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属仓库ID
     * Repository ID
     */
    @Schema(description = "所属仓库ID / Repository ID", example = "1")
    private Long repoId;

    /**
     * Commit Hash(SHA-1)
     * Commit hash (SHA-1)
     */
    @Schema(description = "Commit Hash / Commit hash (SHA-1)", example = "a1b2c3d4e5f6")
    private String commitHash;

    /**
     * 分支名
     * Branch name
     */
    @Schema(description = "分支名 / Branch name", example = "main")
    private String branch;

    /**
     * 提交者姓名
     * Committer name
     */
    @Schema(description = "提交者姓名 / Committer name", example = "张三")
    private String authorName;

    /**
     * 提交者邮箱
     * Committer email
     */
    @Schema(description = "提交者邮箱 / Committer email", example = "zhangsan@example.com")
    private String authorEmail;

    /**
     * 提交信息
     * Commit message
     */
    @Schema(description = "提交信息 / Commit message", example = "feat: 新增用户管理模块")
    private String commitMessage;

    /**
     * 提交时间
     * Commit timestamp
     */
    @Schema(description = "提交时间 / Commit timestamp")
    private LocalDateTime commitTime;

    /**
     * 新增行数
     * Lines added
     */
    @Schema(description = "新增行数 / Lines added", example = "50")
    private Integer additions;

    /**
     * 删除行数
     * Lines deleted
     */
    @Schema(description = "删除行数 / Lines deleted", example = "10")
    private Integer deletions;

    /**
     * 变更文件数
     * Changed files count
     */
    @Schema(description = "变更文件数 / Changed files count", example = "5")
    private Integer changedFiles;

    /**
     * 触发来源: auto-Webhook自动 manual-手动触发
     * Trigger source: auto-Webhook automatic, manual-manual trigger
     */
    @Schema(description = "触发来源: auto-Webhook自动 manual-手动触发 / Trigger source", example = "auto")
    private String triggerSource;

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
