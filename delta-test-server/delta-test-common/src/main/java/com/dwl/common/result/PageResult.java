package com.dwl.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页结果包装类
 * Paginated Result Wrapper
 * <p>
 * 封装分页查询的结果数据，包含数据列表和分页元信息。
 * Wraps paginated query result data, including the data list and pagination metadata.
 * </p>
 *
 * @param <T> 列表元素泛型 / List element generic type
 * @author DeltaTest
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页结果 / Paginated Result")
public class PageResult<T> implements Serializable {

    /**
     * 序列化版本号
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 数据列表
     * Data list
     */
    @Schema(description = "数据列表 / Data list")
    private List<T> list;

    /**
     * 总记录数
     * Total record count
     */
    @Schema(description = "总记录数 / Total record count", example = "100")
    private long total;

    /**
     * 当前页码（从1开始）
     * Current page number (starts from 1)
     */
    @Schema(description = "当前页码 / Current page number", example = "1")
    private long pageNum;

    /**
     * 每页大小
     * Page size
     */
    @Schema(description = "每页大小 / Page size", example = "10")
    private long pageSize;

    /**
     * 总页数
     * Total page count
     */
    @Schema(description = "总页数 / Total page count", example = "10")
    private long pages;

    /**
     * 根据分页参数构造分页结果
     * Construct paginated result from pagination parameters
     *
     * @param list     数据列表 / Data list
     * @param total    总记录数 / Total record count
     * @param pageNum  当前页码 / Current page number
     * @param pageSize 每页大小 / Page size
     */
    public PageResult(List<T> list, long total, long pageNum, long pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pageSize > 0 ? (total + pageSize - 1) / pageSize : 0;
    }
}
