package com.dwl.service;

import com.dwl.model.entity.SysDictData;
import com.dwl.model.vo.DictDataVO;

import java.util.List;

/**
 * 字典数据 服务接口
 * Dictionary Data Service Interface
 *
 * @author ByDWL
 */
public interface SysDictDataService {

    /**
     * 根据字典类型查询字典数据
     * List dictionary data by type
     *
     * @param dictType 字典类型编码 / Dictionary type code
     * @return 字典数据列表 / Dictionary data list
     */
    List<DictDataVO> listDictDataByType(String dictType);

    /**
     * 创建字典数据
     * Create dictionary data
     *
     * @param entity 字典数据实体 / Dictionary data entity
     * @return 新ID / New ID
     */
    Long createDictData(SysDictData entity);

    /**
     * 更新字典数据
     * Update dictionary data
     *
     * @param entity 字典数据实体 / Dictionary data entity
     */
    void updateDictData(SysDictData entity);

    /**
     * 删除字典数据
     * Delete dictionary data
     *
     * @param id 字典数据ID / Dictionary data ID
     */
    void deleteDictData(Long id);
}
