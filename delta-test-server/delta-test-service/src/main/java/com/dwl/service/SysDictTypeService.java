package com.dwl.service;

import com.dwl.common.result.PageResult;
import com.dwl.model.entity.SysDictType;
import com.dwl.model.vo.DictTypeVO;

/**
 * 字典类型 服务接口
 * Dictionary Type Service Interface
 *
 * @author DeltaTest
 */
public interface SysDictTypeService {

    /**
     * 分页查询字典类型
     * Page query dictionary types
     *
     * @param dictType 字典类型编码 / Dictionary type code
     * @param dictName 字典类型名称 / Dictionary type name
     * @param pageNum  页码 / Page number
     * @param pageSize 每页大小 / Page size
     * @return 分页结果 / Paginated result
     */
    PageResult<DictTypeVO> pageDictTypes(String dictType, String dictName, int pageNum, int pageSize);

    /**
     * 查询字典类型详情
     * Get dictionary type detail
     *
     * @param id 字典类型ID / Dictionary type ID
     * @return 字典类型视图对象 / Dictionary type view object
     */
    DictTypeVO getDictType(Long id);

    /**
     * 创建字典类型
     * Create dictionary type
     *
     * @param entity 字典类型实体 / Dictionary type entity
     * @return 新ID / New ID
     */
    Long createDictType(SysDictType entity);

    /**
     * 更新字典类型
     * Update dictionary type
     *
     * @param entity 字典类型实体 / Dictionary type entity
     */
    void updateDictType(SysDictType entity);

    /**
     * 删除字典类型
     * Delete dictionary type
     *
     * @param id 字典类型ID / Dictionary type ID
     */
    void deleteDictType(Long id);
}
