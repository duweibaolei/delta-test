---
alwaysApply: true
---
## Java 硬约束
- 包名必须以 com.dwl 开头
- 实体类必须继承 BaseEntity，添加 @EqualsAndHashCode(callSuper=true)
- 实体类统一使用 Lombok 五件套：@Data + @EqualsAndHashCode(callSuper=true) + @Builder + @NoArgsConstructor + @AllArgsConstructor
- 所有类/方法/字段必须有中英文双语 Javadoc 注释
- Controller/DTO/VO 必须有 @Schema/@Operation/@Tag 注解
- 枚举类也必须使用 @Schema 注解，每个枚举实例必须有中英文双语 Javadoc
- ID 策略使用 IdType.ASSIGN_ID（雪花算法），禁止 AUTO_INCREMENT
- Service 必须遵循接口+实现分离：XxxService（接口）+ XxxServiceImpl（实现类，在 impl 子包下）
- ServiceImpl 使用 @Slf4j + @RequiredArgsConstructor，Controller 使用 @RequiredArgsConstructor
- Mapper 接口统一继承 BaseMapper<Entity>，使用 @Mapper 注解
- DTO/VO/消息类统一声明 @Serial private static final long serialVersionUID = 1L
- DTO 类命名必须以 DTO 后缀结尾（如 UserCreateDTO），MQ 消息类以 Message 后缀结尾
- 统一响应体：Controller 方法返回 R<T> 或 R<PageResult<T>>
- 业务异常统一使用 BusinessException（携带 ErrorCode 枚举）

## 数据库硬约束
- 所有表必须包含 is_deleted (TINYINT NOT NULL DEFAULT 0) 并建立 idx_is_deleted 索引
- 所有表必须包含 created_at 和 updated_at 审计字段
- 枚举字段必须使用 sys_dict_type/sys_dict_data 字典数据
- 字段顺序：id → 业务字段 → is_deleted → created_at → updated_at
- 需要并发控制的实体使用 @Version 注解实现乐观锁

## 安全硬约束
- CORS 配置必须使用 ${cors.allowed-origins:*}，禁止硬编码
- Axios 必须启用 withCredentials: true
- JWT 密钥必须通过环境变量注入，禁止硬编码默认值到生产环境
- 生产环境 application-prod.yml 中 jwt.secret 必须为 ${JWT_SECRET}（无默认值）
- gRPC 客户端必须支持 TLS 开关

## 前端硬约束
- Ant Design Vue 必须按需导入（importStyle: 'less'），使用 unplugin-vue-components + AntDesignVueResolver 自动注册
- 使用 unplugin-auto-import 自动导入 Vue/VueRouter/Pinia API
- 单元测试使用 Vitest（globals: true, environment: jsdom, coverage: v8）
- TypeScript 必须启用严格模式（strict: true + noUncheckedIndexedAccess）
- CSS 预处理器使用 Less，组件样式必须 scoped（`<style scoped lang="less">`）
- Axios 必须启用 withCredentials: true，统一 HTTP 封装导出 get/post/put/del
- 前端统一响应体接口 ApiResponse<T>（code/message/data/timestamp），对应后端 R<T>
- API 函数命名必须使用 Api 后缀（如 loginApi、refreshTokenApi）
- Pinia 使用 Composition API 风格（defineStore('name', () => {...})），禁止 Options API
- 视图目录结构：src/views/{module}/index.vue，每个功能模块一个文件夹
- 路由命名使用 PascalCase（如 Dashboard、TestCase），meta 包含 title（中英双语）/icon/requiresAuth
- 测试文件组织：src/**/__tests__/*.test.ts，测试描述使用中英双语格式
- 环境变量必须以 VITE_ 前缀命名，在 env.d.ts 中声明 TypeScript 类型
- Token 存储 key 使用 delta_test_ 前缀（如 delta_test_token）
- Ant Design Vue 全局汉化：a-config-provider :locale="zhCN" + dayjs.locale('zh-cn')
- 生产环境 API 基础路径为 /，依赖 nginx 反向代理

## MQ 硬约束
- RabbitMQ 队列名称统一前缀 delta.test.，交换机名称 delta.test.exchange，使用 Topic Exchange
- MQ 消息类放在 mq.message 包下，实现 Serializable，使用 @Data + @Schema

## gRPC 硬约束
- Protobuf 生成代码的 java_package 统一为 com.dwl.grpc.engine，与项目包名前缀一致

## C 引擎硬约束
- C 业务逻辑使用 C17 标准，gRPC Server 层使用 C++17 标准
- 头文件使用 #ifndef 宏保护（DELTA_ENGINE_MODULE_H），不使用 #pragma once
- C/C++ 桥接层使用 extern "C" 包裹，仅使用 C 兼容类型
- C 业务逻辑库（delta_engine_core）零 C++ 依赖，可独立编译
- gRPC 构建可选（ENABLE_GRPC 选项），无 Conan 时仍可编译 C 逻辑
- 未使用参数使用 (void)param; 显式抑制，不使用 __attribute__((unused))
- 编译警告级别：GCC/Clang -Wall -Wextra -Wpedantic，MSVC /W4
