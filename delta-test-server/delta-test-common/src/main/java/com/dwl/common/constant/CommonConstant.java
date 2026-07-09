package com.dwl.common.constant;

/**
 * 通用常量
 * Common Constants
 * <p>
 * 定义系统中通用的常量值，如是否标识、分页默认值、逻辑删除标识等。
 * Defines common constant values in the system, such as yes/no flags,
 * pagination defaults, logical delete markers, etc.
 * </p>
 *
 * @author ByDWL
 */
public final class CommonConstant {

    /**
     * 私有构造函数，防止实例化
     * Private constructor to prevent instantiation
     */
    private CommonConstant() {
        throw new UnsupportedOperationException("常量类不允许实例化 / Constants class cannot be instantiated");
    }

    // ==================== 是否标识 / Yes-No Flags ====================

    /**
     * 是 / Yes
     */
    public static final int YES = 1;

    /**
     * 否 / No
     */
    public static final int NO = 0;

    // ==================== 分页默认值 / Pagination Defaults ====================

    /**
     * 默认页码（从1开始）
     * Default page number (starts from 1)
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认每页大小
     * Default page size
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大每页大小
     * Maximum page size
     */
    public static final int MAX_PAGE_SIZE = 100;

    // ==================== 逻辑删除标识 / Logical Delete Flags ====================

    /**
     * 已删除
     * Deleted
     */
    public static final int LOGICAL_DELETE_DELETED = 1;

    /**
     * 未删除（正常）
     * Not deleted (normal)
     */
    public static final int LOGICAL_DELETE_NORMAL = 0;
}
