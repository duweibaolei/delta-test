# 九、数据库表设计规范

> 本文件定义 DeltaTest 项目所有数据库表的公共字段标准、字段顺序、命名规则和约束规范。
>
> **关联文件**：
> - 代码规范 → [code-standards.md](./code-standards.md)（命名约定）
> - 技术栈 → [tech-stack.md](./tech-stack.md)（MyBatis-Plus 配置）

---

## 9.1 公共字段标准定义

### 所有表必须包含的公共字段

| 字段名 | 数据类型 | 约束 | 默认值 | 标准注释 |
|--------|---------|------|--------|---------|
| `id` | BIGINT | NOT NULL | （应用层雪花算法生成） | `'{业务语义}ID'` |
| `is_deleted` | TINYINT | NOT NULL DEFAULT 0 | 0 | `'逻辑删除: 0-未删除 1-已删除'` |
| `created_at` | DATETIME | NOT NULL DEFAULT CURRENT_TIMESTAMP | CURRENT_TIMESTAMP | `'创建时间'` |
| `updated_at` | DATETIME | NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | CURRENT_TIMESTAMP | `'更新时间'` |

### 字段说明

- **id**：主键，使用雪花算法（MyBatis-Plus `IdType.ASSIGN_ID`）由应用层生成，**不使用 AUTO_INCREMENT**
- **is_deleted**：逻辑删除标记，所有表必须包含且建立 `idx_is_deleted` 索引
- **created_at**：记录创建时间，由数据库 `DEFAULT CURRENT_TIMESTAMP` 自动填充，与 MyBatis-Plus `MetaObjectHandler` INSERT 填充对齐
- **updated_at**：记录更新时间，由数据库 `ON UPDATE CURRENT_TIMESTAMP` 自动更新，与 MyBatis-Plus `MetaObjectHandler` INSERT_UPDATE 填充对齐

## 9.2 表分类与公共字段要求

### 业务主表（必须包含全部 4 个公共字段）

所有非关联表均为业务主表，必须包含 `id` + `is_deleted` + `created_at` + `updated_at`。

**示例**：

```sql
CREATE TABLE sys_user (
    id              BIGINT      NOT NULL COMMENT '用户ID',
    -- 业务字段 --
    is_deleted      TINYINT     NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';
```

### 关联表（必须包含 id + is_deleted + created_at）

多对多关联表必须包含 `id` + `is_deleted` + `created_at`，`updated_at` 可选。

**示例**：

```sql
CREATE TABLE sys_user_role (
    id          BIGINT  NOT NULL COMMENT '用户角色关联ID',
    user_id     BIGINT  NOT NULL COMMENT '用户ID',
    role_id     BIGINT  NOT NULL COMMENT '角色ID',
    is_deleted  TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_role (user_id, role_id),
    KEY idx_role_id (role_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联';
```

### 不可变记录表（updated_at 可省略）

以下类型的表语义上记录创建后不再修改，可省略 `updated_at`：
- 提交记录（git_commit）
- 版本快照（case_version）
- 执行步骤结果（execution_step_result）
- 日统计快照（quality_daily_stats）

**判定规则**：如果表的业务字段在创建后不会被 UPDATE，则可省略 `updated_at`。

## 9.3 字段顺序规范

```
id → 业务字段 → is_deleted → created_at → updated_at
```

- `id` 永远是第一个字段
- `is_deleted` 放在最后一个业务字段之后、`created_at` 之前
- `created_at` 放在 `is_deleted` 之后
- `updated_at` 放在 `created_at` 之后（如果存在）

## 9.4 id 字段注释规范

| 表类型 | 注释格式 | 示例 |
|--------|---------|------|
| 业务主表 | `'{业务名称}ID'` | `'用户ID'`, `'用例ID'`, `'任务ID'` |
| 关联表 | `'{关联描述}ID'` | `'用户角色关联ID'`, `'用例标签关联ID'` |

**禁止**使用通用注释 `'ID'`，必须包含业务语义。

## 9.5 特殊业务时间字段

某些表可能需要额外的业务时间字段（如 `analyzed_at`、`marked_at`），这些字段**不能替代** `created_at`，应作为独立业务字段保留。

**规则**：
- `created_at` 记录数据行的创建时间（框架统一管理）
- 业务时间字段记录业务事件发生时间（业务逻辑控制）
- 两者语义不同，不可互换

**示例**：

```sql
CREATE TABLE ai_root_cause (
    id              BIGINT  NOT NULL COMMENT 'AI根因分析ID',
    execution_id    BIGINT  NOT NULL COMMENT '执行记录ID',
    -- 业务字段 --
    analyzed_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'AI分析完成时间',
    is_deleted      TINYINT  NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI根因分析';
```

## 9.6 索引规范

| 规则 | 说明 |
|------|------|
| 所有表必须有 `idx_is_deleted` 索引 | `KEY idx_is_deleted (is_deleted)` |
| 主键使用 `PRIMARY KEY (id)` | 不使用自增，雪花 ID 由应用层生成 |
| 唯一业务键使用 `uk_` 前缀 | 如 `UNIQUE KEY uk_username (username)` |
| 外键字段建立索引 | 如 `KEY idx_role_id (role_id)` |
| 不使用数据库外键约束 | 由应用层保证数据一致性 |

## 9.7 通用建表规范

| 规则 | 说明 |
|------|------|
| 引擎 | `ENGINE=InnoDB` |
| 字符集 | `DEFAULT CHARSET=utf8mb4` |
| 表注释 | 每张表必须有中文 COMMENT |
| 字段注释 | 每个字段必须有 COMMENT |
| 列命名 | snake_case |
| 枚举字段 | 使用字典数据（`sys_dict_type` + `sys_dict_data`）驱动 |
| 金额/比例 | 使用 `DECIMAL` 类型，不使用 `FLOAT`/`DOUBLE` |
| 大文本 | 使用 `TEXT` 类型 |
| JSON字段 | 使用 `JSON` 类型（MySQL 8.0+） |
