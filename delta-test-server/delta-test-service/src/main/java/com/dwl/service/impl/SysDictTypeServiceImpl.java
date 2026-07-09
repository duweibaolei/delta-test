package com.dwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dwl.common.exception.BusinessException;
import com.dwl.common.enums.ErrorCode;
import com.dwl.common.result.PageResult;
import com.dwl.dao.mapper.SysDictTypeMapper;
import com.dwl.model.entity.SysDictType;
import com.dwl.model.vo.DictTypeVO;
import com.dwl.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典类型 服务实现类
 * Dictionary Type Service Implementation
 *
 * @author ByDWL
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl implements SysDictTypeService {

    /** 字典类型Mapper / Dictionary type Mapper */
    private final SysDictTypeMapper dictTypeMapper;

    @Override
    public PageResult<DictTypeVO> pageDictTypes(String dictType, String dictName, int pageNum, int pageSize) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(dictType != null && !dictType.isEmpty(), SysDictType::getDictType, dictType)
               .like(dictName != null && !dictName.isEmpty(), SysDictType::getDictName, dictName)
               .orderByDesc(SysDictType::getCreatedAt);

        Page<SysDictType> page = dictTypeMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        List<DictTypeVO> voList = page.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, page.getTotal(), pageNum, pageSize);
    }

    @Override
    public DictTypeVO getDictType(Long id) {
        SysDictType entity = dictTypeMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "字典类型不存在 / Dictionary type not found");
        }
        return convertToVO(entity);
    }

    @Override
    public Long createDictType(SysDictType entity) {
        dictTypeMapper.insert(entity);
        log.info("创建字典类型成功，ID: {} / Dictionary type created, ID: {}", entity.getId());
        return entity.getId();
    }

    @Override
    public void updateDictType(SysDictType entity) {
        SysDictType existing = dictTypeMapper.selectById(entity.getId());
        if (existing == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "字典类型不存在 / Dictionary type not found");
        }
        dictTypeMapper.updateById(entity);
        log.info("更新字典类型成功，ID: {} / Dictionary type updated, ID: {}", entity.getId());
    }

    @Override
    public void deleteDictType(Long id) {
        SysDictType existing = dictTypeMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "字典类型不存在 / Dictionary type not found");
        }
        dictTypeMapper.deleteById(id);
        log.info("删除字典类型成功，ID: {} / Dictionary type deleted, ID: {}", id);
    }

    private DictTypeVO convertToVO(SysDictType entity) {
        DictTypeVO vo = new DictTypeVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
