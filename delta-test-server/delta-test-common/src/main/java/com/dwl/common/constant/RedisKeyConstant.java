package com.dwl.common.constant;

/**
 * Redis键常量
 * Redis Key Constants
 * <p>
 * 定义系统中所有Redis缓存键的前缀，确保键命名规范统一。
 * Defines all Redis cache key prefixes in the system, ensuring
 * consistent and standardized key naming conventions.
 * </p>
 *
 * @author ByDWL
 */
public final class RedisKeyConstant {

    /**
     * 私有构造函数，防止实例化
     * Private constructor to prevent instantiation
     */
    private RedisKeyConstant() {
        throw new UnsupportedOperationException("常量类不允许实例化 / Constants class cannot be instantiated");
    }

    // ==================== 登录认证 / Login Authentication ====================

    /**
     * 登录Token缓存键前缀
     * Login token cache key prefix
     * <p>完整键格式：delta:test:login:token:{token值}</p>
     * <p>Full key format: delta:test:login:token:{tokenValue}</p>
     */
    public static final String LOGIN_TOKEN_KEY = "delta:test:login:token:";

    // ==================== 字典管理 / Dictionary Management ====================

    /**
     * 字典类型缓存键前缀
     * Dictionary type cache key prefix
     * <p>完整键格式：delta:test:dict:type:{类型编码}</p>
     * <p>Full key format: delta:test:dict:type:{typeCode}</p>
     */
    public static final String DICT_TYPE_KEY = "delta:test:dict:type:";

    /**
     * 字典数据缓存键前缀
     * Dictionary data cache key prefix
     * <p>完整键格式：delta:test:dict:data:{类型编码}</p>
     * <p>Full key format: delta:test:dict:data:{typeCode}</p>
     */
    public static final String DICT_DATA_KEY = "delta:test:dict:data:";

    // ==================== 任务状态 / Task Status ====================

    /**
     * 任务状态缓存键前缀
     * Task status cache key prefix
     * <p>完整键格式：delta:test:task:status:{任务ID}</p>
     * <p>Full key format: delta:test:task:status:{taskId}</p>
     */
    public static final String TASK_STATUS_KEY = "delta:test:task:status:";
}
