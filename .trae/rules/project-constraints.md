---
项目硬约束规则，AI 助手在所有交互中必须遵守。
---

## Java 硬约束
- 包名必须以 com.dwl 开头
- 实体类必须继承 BaseEntity，添加 @EqualsAndHashCode(callSuper=true)
- 所有类/方法/字段必须有中英文双语 Javadoc 注释
- Controller/DTO/VO 必须有 @Schema/@Operation/@Tag 注解
- ID 策略使用 IdType.ASSIGN_ID（雪花算法），禁止 AUTO_INCREMENT

## 数据库硬约束
- 所有表必须包含 is_deleted (TINYINT NOT NULL DEFAULT 0) 并建立 idx_is_deleted 索引
- 所有表必须包含 created_at 和 updated_at 审计字段
- 枚举字段必须使用 sys_dict_type/sys_dict_data 字典数据
- 字段顺序：id → 业务字段 → is_deleted → created_at → updated_at

## 安全硬约束
- CORS 配置必须使用 ${cors.allowed-origins:*}，禁止硬编码
- Axios 必须启用 withCredentials: true
- JWT 密钥必须通过环境变量注入，禁止硬编码默认值到生产环境
- gRPC 客户端必须支持 TLS 开关

## 前端硬约束
- Ant Design Vue 必须按需导入（importStyle: 'less'）
- 单元测试使用 Vitest
