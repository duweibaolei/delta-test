-- ============================================================
-- DeltaTest 双模式驱动的Web自动化测试平台 · 数据库表结构
-- 基于文档《整体架构重梳理：双模式驱动的Web自动化测试平台》
-- 数据库: MySQL 8.0+
-- 字符集: utf8mb4
-- ============================================================

-- ============================================================
-- 一、系统管理域
-- ============================================================

-- 1.1 系统用户
CREATE TABLE sys_user (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username        VARCHAR(64)     NOT NULL COMMENT '用户名',
    password        VARCHAR(128)    NOT NULL COMMENT '密码(加密)',
    real_name       VARCHAR(64)     DEFAULT NULL COMMENT '真实姓名',
    email           VARCHAR(128)    DEFAULT NULL COMMENT '邮箱',
    avatar          VARCHAR(256)    DEFAULT NULL COMMENT '头像URL',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态: 1-启用 0-禁用',
    last_login_time DATETIME        DEFAULT NULL COMMENT '最后登录时间',
    is_deleted      TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_email (email),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- 1.2 系统角色
CREATE TABLE sys_role (
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    role_code   VARCHAR(64) NOT NULL COMMENT '角色编码',
    role_name   VARCHAR(64) NOT NULL COMMENT '角色名称',
    description VARCHAR(256) DEFAULT NULL COMMENT '描述',
    status      TINYINT     NOT NULL DEFAULT 1 COMMENT '状态: 1-启用 0-禁用',
    is_deleted  TINYINT     NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_code (role_code),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色';

-- 1.3 系统权限
CREATE TABLE sys_permission (
    id              BIGINT      NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    permission_code VARCHAR(128) NOT NULL COMMENT '权限编码',
    permission_name VARCHAR(64)  NOT NULL COMMENT '权限名称',
    resource_type   VARCHAR(32)  NOT NULL COMMENT '资源类型: menu-菜单 button-按钮 api-接口',
    parent_id       BIGINT      DEFAULT NULL COMMENT '父权限ID',
    sort_order      INT         NOT NULL DEFAULT 0 COMMENT '排序',
    status          TINYINT     NOT NULL DEFAULT 1 COMMENT '状态: 1-启用 0-禁用',
    is_deleted      TINYINT     NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_permission_code (permission_code),
    KEY idx_parent_id (parent_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统权限';

-- 1.4 用户角色关联
CREATE TABLE sys_user_role (
    id          BIGINT  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id     BIGINT  NOT NULL COMMENT '用户ID',
    role_id     BIGINT  NOT NULL COMMENT '角色ID',
    is_deleted  TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_role (user_id, role_id),
    KEY idx_role_id (role_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联';

-- 1.5 角色权限关联
CREATE TABLE sys_role_permission (
    id            BIGINT  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    role_id       BIGINT  NOT NULL COMMENT '角色ID',
    permission_id BIGINT  NOT NULL COMMENT '权限ID',
    is_deleted    TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_permission (role_id, permission_id),
    KEY idx_permission_id (permission_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联';

-- 1.6 环境配置
CREATE TABLE sys_environment (
    id              BIGINT      NOT NULL AUTO_INCREMENT COMMENT '环境ID',
    env_code        VARCHAR(64) NOT NULL COMMENT '环境编码: test/staging/production',
    env_name        VARCHAR(64) NOT NULL COMMENT '环境名称',
    base_url        VARCHAR(256) NOT NULL COMMENT '基础URL',
    description     VARCHAR(256) DEFAULT NULL COMMENT '描述',
    status          TINYINT     NOT NULL DEFAULT 1 COMMENT '状态: 1-启用 0-禁用',
    is_deleted      TINYINT     NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_env_code (env_code),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='环境配置';

-- 1.7 Git仓库配置
CREATE TABLE sys_repository (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
    repo_name       VARCHAR(128) NOT NULL COMMENT '仓库名称',
    repo_url        VARCHAR(512) NOT NULL COMMENT '仓库地址',
    branch_default  VARCHAR(128) NOT NULL DEFAULT 'main' COMMENT '默认分支',
    credential_type VARCHAR(32)  NOT NULL DEFAULT 'ssh' COMMENT '认证方式: ssh/token/password',
    credential_key  VARCHAR(512) NOT NULL COMMENT '认证密钥(加密存储)',
    webhook_url     VARCHAR(512) DEFAULT NULL COMMENT 'Webhook回调地址',
    webhook_secret  VARCHAR(128) DEFAULT NULL COMMENT 'Webhook签名密钥',
    status          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态: 1-启用 0-禁用',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_repo_url (repo_url(255)),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Git仓库配置';

-- 1.8 字典类型
CREATE TABLE sys_dict_type (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '字典类型ID',
    dict_type       VARCHAR(64)  NOT NULL COMMENT '字典类型编码(唯一标识)',
    dict_name       VARCHAR(128) NOT NULL COMMENT '字典类型名称',
    description     VARCHAR(256) DEFAULT NULL COMMENT '描述',
    status          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态: 1-启用 0-禁用',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_by      BIGINT       DEFAULT NULL COMMENT '创建人ID',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_dict_type (dict_type),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型';

-- 1.9 字典数据
CREATE TABLE sys_dict_data (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '字典数据ID',
    dict_type       VARCHAR(64)  NOT NULL COMMENT '所属字典类型编码',
    dict_label      VARCHAR(128) NOT NULL COMMENT '字典标签(显示值)',
    dict_value      VARCHAR(128) NOT NULL COMMENT '字典值(实际值)',
    sort_order      INT          NOT NULL DEFAULT 0 COMMENT '排序(升序)',
    css_class       VARCHAR(64)  DEFAULT NULL COMMENT '样式属性(如标签颜色)',
    description     VARCHAR(256) DEFAULT NULL COMMENT '描述',
    status          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态: 1-启用 0-禁用',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_by      BIGINT       DEFAULT NULL COMMENT '创建人ID',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_type_value (dict_type, dict_value),
    KEY idx_dict_type (dict_type),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据';


-- ============================================================
-- 二、代码分析域
-- ============================================================

-- 2.1 Git提交记录
CREATE TABLE git_commit (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '提交记录ID',
    repo_id         BIGINT       NOT NULL COMMENT '所属仓库ID',
    commit_hash     VARCHAR(40)  NOT NULL COMMENT 'Commit Hash(SHA-1)',
    branch          VARCHAR(128) NOT NULL COMMENT '分支名',
    author_name     VARCHAR(64)  NOT NULL COMMENT '提交者姓名',
    author_email    VARCHAR(128) DEFAULT NULL COMMENT '提交者邮箱',
    commit_message  TEXT         NOT NULL COMMENT '提交信息',
    commit_time     DATETIME     NOT NULL COMMENT '提交时间',
    additions       INT          NOT NULL DEFAULT 0 COMMENT '新增行数',
    deletions       INT          NOT NULL DEFAULT 0 COMMENT '删除行数',
    changed_files   INT          NOT NULL DEFAULT 0 COMMENT '变更文件数',
    trigger_source  VARCHAR(16)  NOT NULL DEFAULT 'auto' COMMENT '触发来源: auto-Webhook自动 manual-手动触发',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_repo_hash (repo_id, commit_hash),
    KEY idx_branch (repo_id, branch),
    KEY idx_commit_time (commit_time),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Git提交记录';

-- 2.2 变更分析
CREATE TABLE change_analysis (
    id                  BIGINT       NOT NULL AUTO_INCREMENT COMMENT '分析ID',
    analysis_no         VARCHAR(32)  NOT NULL COMMENT '分析编号: CA-YYYYMMDD-NNNN',
    repo_id             BIGINT       NOT NULL COMMENT '仓库ID',
    branch              VARCHAR(128) NOT NULL COMMENT '分支名',
    start_commit_hash   VARCHAR(40)  DEFAULT NULL COMMENT '起始Commit(手动分析时指定)',
    end_commit_hash     VARCHAR(40)  DEFAULT NULL COMMENT '截止Commit(手动分析时指定)',
    trigger_source      VARCHAR(16)  NOT NULL DEFAULT 'auto' COMMENT '触发来源: auto-Webhook manual-手动',
    trigger_user_id     BIGINT       DEFAULT NULL COMMENT '手动触发用户ID',
    risk_level          VARCHAR(16)  NOT NULL DEFAULT 'low' COMMENT 'AI风险等级: high/medium/low',
    risk_level_manual   VARCHAR(16)  DEFAULT NULL COMMENT '手动调整风险等级: high/medium/low',
    risk_adjust_reason  TEXT         DEFAULT NULL COMMENT '风险等级调整原因',
    ai_summary          TEXT         DEFAULT NULL COMMENT 'AI变更摘要',
    ai_test_suggestion  TEXT         DEFAULT NULL COMMENT 'AI测试建议',
    manual_description  TEXT         DEFAULT NULL COMMENT '手动补充变更说明',
    status              VARCHAR(16)  NOT NULL DEFAULT 'running' COMMENT '状态: running/completed/failed',
    is_deleted          TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_analysis_no (analysis_no),
    KEY idx_repo_branch (repo_id, branch),
    KEY idx_trigger_source (trigger_source),
    KEY idx_status (status),
    KEY idx_created_at (created_at),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='变更分析';

-- 2.3 变更分析与提交关联
CREATE TABLE change_analysis_commit (
    id              BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    analysis_id     BIGINT NOT NULL COMMENT '分析ID',
    commit_id       BIGINT NOT NULL COMMENT '提交记录ID',
    is_deleted      TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_analysis_commit (analysis_id, commit_id),
    KEY idx_commit_id (commit_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='变更分析与提交关联';

-- 2.4 影响范围
CREATE TABLE affected_scope (
    id              BIGINT      NOT NULL AUTO_INCREMENT COMMENT '影响范围ID',
    analysis_id     BIGINT      NOT NULL COMMENT '所属分析ID',
    scope_type      VARCHAR(32) NOT NULL COMMENT '范围类型: frontend_page/frontend_component/backend_api/backend_service/database_table',
    scope_name      VARCHAR(256) NOT NULL COMMENT '范围名称',
    scope_path      VARCHAR(512) DEFAULT NULL COMMENT '范围路径/标识',
    selected_for_regression TINYINT NOT NULL DEFAULT 1 COMMENT '是否选入回归范围: 1-是 0-否',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_analysis_id (analysis_id),
    KEY idx_scope_type (scope_type),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='影响范围';


-- ============================================================
-- 三、测试管理域
-- ============================================================

-- 3.1 页面元素对象库
CREATE TABLE page_element (
    id                  BIGINT       NOT NULL AUTO_INCREMENT COMMENT '元素ID',
    element_code        VARCHAR(128) NOT NULL COMMENT '元素编码(别名)',
    element_name        VARCHAR(128) NOT NULL COMMENT '元素名称',
    page_name           VARCHAR(128) DEFAULT NULL COMMENT '所属页面',
    locator_type        VARCHAR(32)  NOT NULL COMMENT '主定位类型: css/xpath/id/data-testid/name',
    locator_value       VARCHAR(512) NOT NULL COMMENT '主定位值',
    backup_locator_type VARCHAR(32)  DEFAULT NULL COMMENT '备份定位类型',
    backup_locator_value VARCHAR(512) DEFAULT NULL COMMENT '备份定位值',
    description         VARCHAR(256) DEFAULT NULL COMMENT '描述',
    source              VARCHAR(16)  NOT NULL DEFAULT 'manual' COMMENT '来源: auto/manual/hybrid',
    status              TINYINT      NOT NULL DEFAULT 1 COMMENT '状态: 1-启用 0-禁用',
    is_deleted          TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_element_code (element_code),
    KEY idx_page_name (page_name),
    KEY idx_source (source),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='页面元素对象库';

-- 3.2 测试用例
CREATE TABLE test_case (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用例ID',
    case_no         VARCHAR(32)  NOT NULL COMMENT '用例编号: TC-NNNN',
    case_name       VARCHAR(256) NOT NULL COMMENT '用例名称',
    module_name     VARCHAR(64)  DEFAULT NULL COMMENT '所属模块',
    source          VARCHAR(16)  NOT NULL DEFAULT 'manual' COMMENT '来源: auto/manual/hybrid',
    status          VARCHAR(16)  NOT NULL DEFAULT 'active' COMMENT '状态: active-正常 unstable-不稳定 disabled-失效 draft-草稿',
    health_score    INT          NOT NULL DEFAULT 100 COMMENT '健康度评分(0-100)',
    priority        VARCHAR(8)   NOT NULL DEFAULT 'P2' COMMENT '优先级: P0/P1/P2',
    version         INT          NOT NULL DEFAULT 1 COMMENT '版本号(乐观锁)',
    description     TEXT         DEFAULT NULL COMMENT '用例描述',
    pre_condition   TEXT         DEFAULT NULL COMMENT '前置条件',
    env_id          BIGINT       DEFAULT NULL COMMENT '执行环境ID',
    created_by      BIGINT       DEFAULT NULL COMMENT '创建人ID',
    last_modified_by BIGINT      DEFAULT NULL COMMENT '最后修改人ID',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_case_no (case_no),
    KEY idx_module (module_name),
    KEY idx_source (source),
    KEY idx_status (status),
    KEY idx_health (health_score),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试用例';

-- 3.3 用例步骤
CREATE TABLE case_step (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '步骤ID',
    case_id         BIGINT       NOT NULL COMMENT '用例ID',
    step_order      INT          NOT NULL COMMENT '步骤顺序(从1开始)',
    element_id      BIGINT       DEFAULT NULL COMMENT '操作元素ID',
    action_type     VARCHAR(32)  NOT NULL COMMENT '动作类型: click/fill/select/waitFor/hover/scroll/navigate/assert',
    action_value    VARCHAR(512) DEFAULT NULL COMMENT '输入值(动作参数)',
    assert_type     VARCHAR(32)  DEFAULT NULL COMMENT '断言类型: url_contains/visible/text_match/value_contains/attribute',
    assert_value    VARCHAR(512) DEFAULT NULL COMMENT '断言期望值',
    wait_timeout    INT          DEFAULT NULL COMMENT '等待超时(毫秒)',
    description     VARCHAR(256) DEFAULT NULL COMMENT '步骤描述',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_case_id_order (case_id, step_order),
    KEY idx_element_id (element_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用例步骤';

-- 3.4 用例版本历史
CREATE TABLE case_version (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '版本ID',
    case_id         BIGINT       NOT NULL COMMENT '用例ID',
    version         INT          NOT NULL COMMENT '版本号',
    snapshot_json   JSON         NOT NULL COMMENT '用例快照(含步骤完整数据)',
    change_summary  VARCHAR(256) DEFAULT NULL COMMENT '变更摘要',
    modified_by     BIGINT       DEFAULT NULL COMMENT '修改人ID',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_case_version (case_id, version),
    KEY idx_case_id (case_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用例版本历史';

-- 3.5 用例标签
CREATE TABLE case_tag (
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '标签ID',
    tag_name    VARCHAR(64) NOT NULL COMMENT '标签名称',
    tag_color   VARCHAR(16) DEFAULT NULL COMMENT '标签颜色',
    is_deleted  TINYINT     NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_tag_name (tag_name),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用例标签';

-- 3.6 用例标签关联
CREATE TABLE case_tag_relation (
    id      BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    case_id BIGINT NOT NULL COMMENT '用例ID',
    tag_id  BIGINT NOT NULL COMMENT '标签ID',
    is_deleted  TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_case_tag (case_id, tag_id),
    KEY idx_tag_id (tag_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用例标签关联';

-- 3.7 业务链路
CREATE TABLE business_link (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '链路ID',
    link_no         VARCHAR(32)  NOT NULL COMMENT '链路编号: BL-NNNN',
    link_name       VARCHAR(256) NOT NULL COMMENT '链路名称',
    description     TEXT         DEFAULT NULL COMMENT '链路描述',
    source          VARCHAR(16)  NOT NULL DEFAULT 'manual' COMMENT '来源: auto/manual/hybrid',
    status          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态: 1-启用 0-禁用',
    version         INT          NOT NULL DEFAULT 1 COMMENT '版本号(乐观锁)',
    created_by      BIGINT       DEFAULT NULL COMMENT '创建人ID',
    last_modified_by BIGINT      DEFAULT NULL COMMENT '最后修改人ID',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_link_no (link_no),
    KEY idx_source (source),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务链路';

-- 3.8 链路节点
CREATE TABLE link_node (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '节点ID',
    link_id         BIGINT       NOT NULL COMMENT '所属链路ID',
    node_order      INT          NOT NULL COMMENT '节点顺序',
    node_type       VARCHAR(32)  NOT NULL COMMENT '节点类型: frontend_page/backend_api/backend_service/database_table',
    node_name       VARCHAR(256) NOT NULL COMMENT '节点名称',
    node_identifier VARCHAR(512) DEFAULT NULL COMMENT '节点标识(如接口路径、表名)',
    assert_rule     VARCHAR(512) DEFAULT NULL COMMENT '断言规则',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_link_id_order (link_id, node_order),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='链路节点';

-- 3.9 用例与链路关联
CREATE TABLE case_link_relation (
    id          BIGINT  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    case_id     BIGINT  NOT NULL COMMENT '用例ID',
    link_id     BIGINT  NOT NULL COMMENT '链路ID',
    is_deleted  TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_case_link (case_id, link_id),
    KEY idx_link_id (link_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用例与链路关联';

-- 3.10 用例与变更分析关联(受影响标记)
CREATE TABLE case_analysis_relation (
    id              BIGINT  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    case_id         BIGINT  NOT NULL COMMENT '用例ID',
    analysis_id     BIGINT  NOT NULL COMMENT '分析ID',
    affected_type   VARCHAR(32) NOT NULL DEFAULT 'impacted' COMMENT '影响类型: impacted-受影响 need_update-需更新断言 need_new-需新增',
    resolved        TINYINT NOT NULL DEFAULT 0 COMMENT '是否已处理: 1-是 0-否',
    is_deleted      TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_case_analysis (case_id, analysis_id),
    KEY idx_analysis_id (analysis_id),
    KEY idx_resolved (resolved),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用例与变更分析关联';

-- 3.11 测试数据集
CREATE TABLE test_data_set (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '数据集ID',
    set_name        VARCHAR(128) NOT NULL COMMENT '数据集名称',
    description     VARCHAR(256) DEFAULT NULL COMMENT '描述',
    data_json       JSON         NOT NULL COMMENT '数据内容(键值对JSON)',
    source          VARCHAR(16)  NOT NULL DEFAULT 'manual' COMMENT '来源: auto/manual',
    created_by      BIGINT       DEFAULT NULL COMMENT '创建人ID',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_source (source),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试数据集';

-- 3.12 环境变量
CREATE TABLE env_variable (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '变量ID',
    env_id          BIGINT       DEFAULT NULL COMMENT '环境ID(NULL表示全局)',
    var_key         VARCHAR(128) NOT NULL COMMENT '变量键',
    var_value       VARCHAR(512) NOT NULL COMMENT '变量值',
    description     VARCHAR(256) DEFAULT NULL COMMENT '描述',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_env_key (env_id, var_key),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='环境变量';


-- ============================================================
-- 四、执行管控域
-- ============================================================

-- 4.1 执行节点
CREATE TABLE exec_node (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '节点ID',
    node_name       VARCHAR(64)  NOT NULL COMMENT '节点名称',
    node_host       VARCHAR(256) NOT NULL COMMENT '节点地址',
    node_port       INT          NOT NULL COMMENT '节点端口',
    browser_types   VARCHAR(256) NOT NULL DEFAULT 'chromium' COMMENT '支持浏览器类型(逗号分隔)',
    max_concurrent  INT          NOT NULL DEFAULT 2 COMMENT '最大并发数',
    status          VARCHAR(16)  NOT NULL DEFAULT 'healthy' COMMENT '状态: healthy/offline/busy',
    last_heartbeat  DATETIME     DEFAULT NULL COMMENT '最后心跳时间',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_status (status),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='执行节点';

-- 4.2 测试任务
CREATE TABLE test_task (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    task_no         VARCHAR(32)  NOT NULL COMMENT '任务编号: TK-NNNN',
    task_name       VARCHAR(256) NOT NULL COMMENT '任务名称',
    trigger_source  VARCHAR(16)  NOT NULL DEFAULT 'auto' COMMENT '触发来源: auto-变更自动 manual-手动 scheduled-定时',
    trigger_user_id BIGINT       DEFAULT NULL COMMENT '手动触发用户ID',
    analysis_id     BIGINT       DEFAULT NULL COMMENT '关联变更分析ID(自动触发时)',
    env_id          BIGINT       DEFAULT NULL COMMENT '执行环境ID',
    browser_type    VARCHAR(32)  NOT NULL DEFAULT 'chromium' COMMENT '浏览器类型',
    concurrency     INT          NOT NULL DEFAULT 1 COMMENT '并发数',
    retry_count     INT          NOT NULL DEFAULT 0 COMMENT '失败重试次数',
    schedule_cron   VARCHAR(64)  DEFAULT NULL COMMENT '定时CRON表达式(定时任务)',
    status          VARCHAR(16)  NOT NULL DEFAULT 'pending' COMMENT '状态: pending/running/paused/completed/failed/cancelled',
    progress        INT          NOT NULL DEFAULT 0 COMMENT '执行进度(百分比)',
    pass_count      INT          NOT NULL DEFAULT 0 COMMENT '通过数',
    fail_count      INT          NOT NULL DEFAULT 0 COMMENT '失败数',
    skip_count      INT          NOT NULL DEFAULT 0 COMMENT '跳过数',
    total_count     INT          NOT NULL DEFAULT 0 COMMENT '总用例数',
    start_time      DATETIME     DEFAULT NULL COMMENT '开始时间',
    end_time        DATETIME     DEFAULT NULL COMMENT '结束时间',
    duration_ms     BIGINT       DEFAULT NULL COMMENT '执行耗时(毫秒)',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_task_no (task_no),
    KEY idx_trigger_source (trigger_source),
    KEY idx_status (status),
    KEY idx_analysis_id (analysis_id),
    KEY idx_created_at (created_at),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试任务';

-- 4.3 任务与用例关联
CREATE TABLE task_case_relation (
    id              BIGINT  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    task_id         BIGINT  NOT NULL COMMENT '任务ID',
    case_id         BIGINT  NOT NULL COMMENT '用例ID',
    is_deleted      TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_task_case (task_id, case_id),
    KEY idx_case_id (case_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务与用例关联';

-- 4.4 任务执行记录(单条用例的执行实例)
CREATE TABLE task_execution (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '执行ID',
    task_id         BIGINT       NOT NULL COMMENT '任务ID',
    case_id         BIGINT       NOT NULL COMMENT '用例ID',
    node_id         BIGINT       DEFAULT NULL COMMENT '执行节点ID',
    retry_index     INT          NOT NULL DEFAULT 0 COMMENT '重试序号(0为首次)',
    status          VARCHAR(16)  NOT NULL DEFAULT 'pending' COMMENT '状态: pending/running/passed/failed/skipped/error',
    start_time      DATETIME     DEFAULT NULL COMMENT '开始时间',
    end_time        DATETIME     DEFAULT NULL COMMENT '结束时间',
    duration_ms     BIGINT       DEFAULT NULL COMMENT '执行耗时(毫秒)',
    failed_step     INT          DEFAULT NULL COMMENT '失败步骤序号',
    error_message   TEXT         DEFAULT NULL COMMENT '错误信息',
    screenshot_url  VARCHAR(512) DEFAULT NULL COMMENT '失败截图URL',
    video_url       VARCHAR(512) DEFAULT NULL COMMENT '执行录像URL',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_task_id (task_id),
    KEY idx_case_id (case_id),
    KEY idx_status (status),
    KEY idx_task_case (task_id, case_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务执行记录';

-- 4.5 执行步骤结果
CREATE TABLE execution_step_result (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    execution_id    BIGINT       NOT NULL COMMENT '执行记录ID',
    step_order      INT          NOT NULL COMMENT '步骤序号',
    action_type     VARCHAR(32)  NOT NULL COMMENT '动作类型',
    assert_type     VARCHAR(32)  DEFAULT NULL COMMENT '断言类型',
    assert_passed   TINYINT      DEFAULT NULL COMMENT '断言结果: 1-通过 0-失败 NULL-无断言',
    actual_value    VARCHAR(512) DEFAULT NULL COMMENT '实际值',
    duration_ms     BIGINT       DEFAULT NULL COMMENT '步骤耗时(毫秒)',
    error_message   VARCHAR(512) DEFAULT NULL COMMENT '错误信息',
    locator_used    VARCHAR(32)  DEFAULT NULL COMMENT '使用的定位策略: primary/backup',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_execution_id (execution_id),
    KEY idx_execution_step (execution_id, step_order),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='执行步骤结果';


-- ============================================================
-- 五、质量报表域
-- ============================================================

-- 5.1 测试报告
CREATE TABLE test_report (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '报告ID',
    report_no       VARCHAR(32)  NOT NULL COMMENT '报告编号: RPT-YYYYMMDD-NNNN',
    task_id         BIGINT       NOT NULL COMMENT '关联任务ID',
    report_type     VARCHAR(32)  NOT NULL DEFAULT 'task' COMMENT '报告类型: task-任务报告 link-链路分析报告 change-变更质量报告',
    trigger_source  VARCHAR(16)  NOT NULL DEFAULT 'auto' COMMENT '触发来源: auto/manual',
    total_count     INT          NOT NULL DEFAULT 0 COMMENT '总用例数',
    pass_count      INT          NOT NULL DEFAULT 0 COMMENT '通过数',
    fail_count      INT          NOT NULL DEFAULT 0 COMMENT '失败数',
    skip_count      INT          NOT NULL DEFAULT 0 COMMENT '跳过数',
    pass_rate       DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT '通过率(%)',
    duration_ms     BIGINT       DEFAULT NULL COMMENT '总执行耗时(毫秒)',
    ai_summary      TEXT         DEFAULT NULL COMMENT 'AI分析摘要',
    ai_suggestion   TEXT         DEFAULT NULL COMMENT 'AI修复建议',
    ai_analyzed     TINYINT      NOT NULL DEFAULT 0 COMMENT 'AI是否完成分析: 1-是 0-否',
    manual_conclusion TEXT       DEFAULT NULL COMMENT '手动调整的报告结论',
    manual_remark   TEXT         DEFAULT NULL COMMENT '手动补充测试备注',
    status          VARCHAR(16)  NOT NULL DEFAULT 'draft' COMMENT '状态: draft-草稿 published-已发布',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_report_no (report_no),
    KEY idx_task_id (task_id),
    KEY idx_report_type (report_type),
    KEY idx_created_at (created_at),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试报告';

-- 5.2 执行结果与报告关联
CREATE TABLE report_execution_relation (
    id              BIGINT  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    report_id       BIGINT  NOT NULL COMMENT '报告ID',
    execution_id    BIGINT  NOT NULL COMMENT '执行记录ID',
    is_deleted      TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_report_execution (report_id, execution_id),
    KEY idx_execution_id (execution_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报告与执行结果关联';

-- 5.3 AI根因分析
CREATE TABLE ai_root_cause (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    execution_id    BIGINT       NOT NULL COMMENT '执行记录ID',
    report_id       BIGINT       DEFAULT NULL COMMENT '报告ID',
    possible_cause  TEXT         NOT NULL COMMENT 'AI分析可能原因',
    confidence      INT          NOT NULL DEFAULT 0 COMMENT '置信度(0-100)',
    fix_suggestion  TEXT         DEFAULT NULL COMMENT '修复建议',
    model_version   VARCHAR(32)  DEFAULT NULL COMMENT 'AI模型版本',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    analyzed_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分析时间',
    PRIMARY KEY (id),
    KEY idx_execution_id (execution_id),
    KEY idx_report_id (report_id),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI根因分析';

-- 5.4 手动失败原因标记
CREATE TABLE manual_failure_mark (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    execution_id    BIGINT       NOT NULL COMMENT '执行记录ID',
    failure_reason  VARCHAR(32)  NOT NULL COMMENT '失败原因: bug-业务缺陷 flaky-用例失效 env-环境问题',
    description     TEXT         DEFAULT NULL COMMENT '补充说明',
    marked_by       BIGINT       NOT NULL COMMENT '标记人ID',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    marked_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '标记时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_execution (execution_id),
    KEY idx_failure_reason (failure_reason),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='手动失败原因标记';

-- 5.5 缺陷记录
CREATE TABLE defect_record (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '缺陷ID',
    defect_no       VARCHAR(32)  NOT NULL COMMENT '缺陷编号: BUG-NNNN',
    defect_title    VARCHAR(256) NOT NULL COMMENT '缺陷标题',
    severity        VARCHAR(16)  NOT NULL DEFAULT 'major' COMMENT '严重程度: critical/major/minor',
    description     TEXT         DEFAULT NULL COMMENT '缺陷描述',
    execution_id    BIGINT       DEFAULT NULL COMMENT '关联执行记录ID',
    case_id         BIGINT       DEFAULT NULL COMMENT '关联用例ID',
    commit_id       BIGINT       DEFAULT NULL COMMENT '关联提交ID',
    report_id       BIGINT       DEFAULT NULL COMMENT '关联报告ID',
    status          VARCHAR(16)  NOT NULL DEFAULT 'open' COMMENT '状态: open/resolved/closed',
    created_by      BIGINT       NOT NULL COMMENT '录入人ID',
    resolved_by     BIGINT       DEFAULT NULL COMMENT '解决人ID',
    resolved_at     DATETIME     DEFAULT NULL COMMENT '解决时间',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_defect_no (defect_no),
    KEY idx_severity (severity),
    KEY idx_case_id (case_id),
    KEY idx_execution_id (execution_id),
    KEY idx_status (status),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='缺陷记录';

-- 5.6 质量趋势统计(日聚合)
CREATE TABLE quality_daily_stats (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    stat_date       DATE         NOT NULL COMMENT '统计日期',
    total_cases     INT          NOT NULL DEFAULT 0 COMMENT '总用例数',
    total_executed  INT          NOT NULL DEFAULT 0 COMMENT '执行用例数',
    total_passed    INT          NOT NULL DEFAULT 0 COMMENT '通过数',
    total_failed    INT          NOT NULL DEFAULT 0 COMMENT '失败数',
    pass_rate       DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT '通过率(%)',
    new_defects     INT          NOT NULL DEFAULT 0 COMMENT '新增缺陷数',
    resolved_defects INT        NOT NULL DEFAULT 0 COMMENT '解决缺陷数',
    auto_cases      INT          NOT NULL DEFAULT 0 COMMENT '自动来源用例数',
    manual_cases    INT          NOT NULL DEFAULT 0 COMMENT '手动来源用例数',
    hybrid_cases    INT          NOT NULL DEFAULT 0 COMMENT '混合来源用例数',
    is_deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_stat_date (stat_date),
    KEY idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='质量趋势日统计';


-- ============================================================
-- 六、初始字典数据
-- ============================================================

-- ---- 字典类型 ----
INSERT INTO sys_dict_type (dict_type, dict_name, description) VALUES
('trigger_source',   '触发来源',     '数据来源方式: 自动触发/手动触发/定时触发'),
('source_type',      '资产来源',     '用例/元素/链路的来源: auto/manual/hybrid'),
('risk_level',       '风险等级',     'AI或手动评定的变更风险等级'),
('analysis_status',  '分析状态',     '变更分析的执行状态'),
('case_status',      '用例状态',     '测试用例的当前状态'),
('case_priority',    '用例优先级',   '测试用例的优先级'),
('action_type',      '步骤动作类型', '用例步骤中的操作动作'),
('assert_type',      '断言类型',     '用例步骤中的断言方式'),
('locator_type',     '元素定位类型', '页面元素的定位策略'),
('scope_type',       '影响范围类型', '变更影响范围的分类'),
('affected_type',    '受影响类型',   '用例受变更影响的程度'),
('task_status',      '任务状态',     '测试任务的执行状态'),
('execution_status', '执行状态',     '单条用例的执行结果'),
('browser_type',     '浏览器类型',   '执行环境使用的浏览器'),
('report_type',      '报告类型',     '测试报告的分类'),
('report_status',    '报告状态',     '测试报告的发布状态'),
('failure_reason',   '失败原因',     '手动标记的用例失败原因'),
('defect_severity',  '缺陷严重程度', '缺陷的严重程度分级'),
('defect_status',    '缺陷状态',     '缺陷的处理状态'),
('node_type',        '链路节点类型', '业务链路节点的分类'),
('node_status',      '执行节点状态', 'Playwright执行节点的状态'),
('credential_type',  '仓库认证方式', 'Git仓库的认证方式'),
('resource_type',    '权限资源类型', '系统权限的资源分类'),
('env_status',       '环境状态',     '测试环境的启用状态');

-- ---- 字典数据 ----
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, css_class) VALUES
-- 触发来源
('trigger_source',   'Webhook自动触发', 'auto',      1, 'tag-blue'),
('trigger_source',   '手动触发',         'manual',    2, 'tag-green'),
('trigger_source',   '定时触发',         'scheduled', 3, 'tag-orange'),
-- 资产来源
('source_type',      '自动生成',  'auto',    1, 'tag-blue'),
('source_type',      '手动录入',  'manual',  2, 'tag-green'),
('source_type',      '混合修改',  'hybrid',  3, 'tag-orange'),
-- 风险等级
('risk_level',       '高风险', 'high',   1, 'tag-red'),
('risk_level',       '中风险', 'medium', 2, 'tag-orange'),
('risk_level',       '低风险', 'low',    3, 'tag-green'),
-- 分析状态
('analysis_status',  '分析中', 'running',   1, 'tag-blue'),
('analysis_status',  '已完成', 'completed', 2, 'tag-green'),
('analysis_status',  '失败',   'failed',    3, 'tag-red'),
-- 用例状态
('case_status',      '正常',   'active',   1, 'tag-green'),
('case_status',      '不稳定', 'unstable', 2, 'tag-orange'),
('case_status',      '失效',   'disabled', 3, 'tag-red'),
('case_status',      '草稿',   'draft',    4, 'tag-gray'),
-- 用例优先级
('case_priority',    'P0-最高', 'P0', 1, 'tag-red'),
('case_priority',    'P1-较高', 'P1', 2, 'tag-orange'),
('case_priority',    'P2-普通', 'P2', 3, 'tag-gray'),
-- 步骤动作类型
('action_type',      '点击',   'click',    1, NULL),
('action_type',      '输入',   'fill',     2, NULL),
('action_type',      '选择',   'select',   3, NULL),
('action_type',      '等待',   'waitFor',  4, NULL),
('action_type',      '悬停',   'hover',    5, NULL),
('action_type',      '滚动',   'scroll',   6, NULL),
('action_type',      '导航',   'navigate', 7, NULL),
('action_type',      '断言',   'assert',   8, NULL),
-- 断言类型
('assert_type',      'URL包含',       'url_contains',    1, NULL),
('assert_type',      '元素可见',       'visible',         2, NULL),
('assert_type',      '文本匹配',       'text_match',      3, NULL),
('assert_type',      '值包含',         'value_contains',  4, NULL),
('assert_type',      '属性校验',       'attribute',       5, NULL),
-- 元素定位类型
('locator_type',     'CSS选择器',   'css',        1, NULL),
('locator_type',     'XPath',       'xpath',      2, NULL),
('locator_type',     'ID',          'id',         3, NULL),
('locator_type',     'data-testid', 'data-testid', 4, NULL),
('locator_type',     'name属性',    'name',       5, NULL),
-- 影响范围类型
('scope_type',       '前端页面',     'frontend_page',      1, NULL),
('scope_type',       '前端组件',     'frontend_component', 2, NULL),
('scope_type',       '后端接口',     'backend_api',        3, NULL),
('scope_type',       '后端服务',     'backend_service',    4, NULL),
('scope_type',       '数据库表',     'database_table',     5, NULL),
-- 受影响类型
('affected_type',    '受影响',     'impacted',     1, 'tag-orange'),
('affected_type',    '需更新断言', 'need_update',   2, 'tag-red'),
('affected_type',    '需新增',     'need_new',      3, 'tag-blue'),
-- 任务状态
('task_status',      '待执行', 'pending',   1, 'tag-gray'),
('task_status',      '执行中', 'running',   2, 'tag-blue'),
('task_status',      '已暂停', 'paused',    3, 'tag-orange'),
('task_status',      '已完成', 'completed', 4, 'tag-green'),
('task_status',      '失败',   'failed',    5, 'tag-red'),
('task_status',      '已取消', 'cancelled', 6, 'tag-gray'),
-- 执行状态
('execution_status', '待执行', 'pending',  1, 'tag-gray'),
('execution_status', '执行中', 'running',  2, 'tag-blue'),
('execution_status', '通过',   'passed',   3, 'tag-green'),
('execution_status', '失败',   'failed',   4, 'tag-red'),
('execution_status', '跳过',   'skipped',  5, 'tag-gray'),
('execution_status', '错误',   'error',    6, 'tag-red'),
-- 浏览器类型
('browser_type',     'Chromium', 'chromium', 1, NULL),
('browser_type',     'Chrome',   'chrome',   2, NULL),
('browser_type',     'Firefox',  'firefox',  3, NULL),
('browser_type',     'WebKit',   'webkit',   4, NULL),
('browser_type',     'Edge',     'edge',     5, NULL),
-- 报告类型
('report_type',      '任务报告',     'task',   1, NULL),
('report_type',      '链路分析报告', 'link',   2, NULL),
('report_type',      '变更质量报告', 'change', 3, NULL),
-- 报告状态
('report_status',    '草稿',     'draft',     1, 'tag-gray'),
('report_status',    '已发布',   'published', 2, 'tag-green'),
-- 失败原因
('failure_reason',   '业务缺陷', 'bug',    1, 'tag-red'),
('failure_reason',   '用例失效', 'flaky',  2, 'tag-orange'),
('failure_reason',   '环境问题', 'env',    3, 'tag-gray'),
-- 缺陷严重程度
('defect_severity',  '严重', 'critical', 1, 'tag-red'),
('defect_severity',  '主要', 'major',    2, 'tag-orange'),
('defect_severity',  '次要', 'minor',    3, 'tag-blue'),
-- 缺陷状态
('defect_status',    '待处理', 'open',     1, 'tag-red'),
('defect_status',    '已解决', 'resolved', 2, 'tag-orange'),
('defect_status',    '已关闭', 'closed',   3, 'tag-green'),
-- 链路节点类型
('node_type',        '前端页面',     'frontend_page',      1, NULL),
('node_type',        '后端接口',     'backend_api',        2, NULL),
('node_type',        '后端服务',     'backend_service',    3, NULL),
('node_type',        '数据库表',     'database_table',     4, NULL),
-- 执行节点状态
('node_status',      '健康', 'healthy', 1, 'tag-green'),
('node_status',      '离线', 'offline', 2, 'tag-red'),
('node_status',      '繁忙', 'busy',    3, 'tag-orange'),
-- 仓库认证方式
('credential_type',  'SSH密钥',   'ssh',      1, NULL),
('credential_type',  'Access Token', 'token',    2, NULL),
('credential_type',  '账号密码',   'password', 3, NULL),
-- 权限资源类型
('resource_type',    '菜单', 'menu',   1, NULL),
('resource_type',    '按钮', 'button', 2, NULL),
('resource_type',    '接口', 'api',    3, NULL);
