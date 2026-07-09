package com.dwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dwl.common.exception.BusinessException;
import com.dwl.common.enums.ErrorCode;
import com.dwl.dao.mapper.SysDictDataMapper;
import com.dwl.model.entity.SysDictData;
import com.dwl.model.vo.DictDataVO;
import com.dwl.service.SysDictDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典数据 服务实现类
 * Dictionary Data Service Implementation
 *
 * @author ByDWL
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl implements SysDictDataService {

    /** 字典数据Mapper / Dictionary data Mapper */
    private final SysDictDataMapper dictDataMapper;

    // TODO: 如需Redis缓存，可在此注入RedisTemplate / If Redis caching is needed, inject RedisTemplate here
    // private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<DictDataVO> listDictDataByType(String dictType) {
        // TODO: 可先从Redis缓存读取 / Can read from Redis cache first
        // String cacheKey = "dict:data:" + dictType;
        // List<DictDataVO> cached = (List<DictDataVO>) redisTemplate.opsForValue().get(cacheKey);
        // if (cached != null) return cached;

        List<SysDictData> list = dictDataMapper.selectList(
                new LambdaQueryWrapper<SysDictData>()
                        .eq(SysDictData::getDictType, dictType)
                        .eq(SysDictData::getStatus, 0)
                        .orderByAsc(SysDictData::getSortOrder)
        );

        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public Long createDictData(SysDictData entity) {
        dictDataMapper.insert(entity);
        // TODO: 清除Redis缓存 / Clear Redis cache
        // redisTemplate.delete("dict:data:" + entity.getDictType());
        log.info("创建字典数据成功，ID: {} / Dictionary data created, ID: {}", entity.getId());
        return entity.getId();
    }

    @Override
    public void updateDictData(SysDictData entity) {
        SysDictData existing = dictDataMapper.selectById(entity.getId());
        if (existing == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "字典数据不存在 / Dictionary data not found");
        }
        dictDataMapper.updateById(entity);
        // TODO: 清除Redis缓存 / Clear Redis cache
        // redisTemplate.delete("dict:data:" + existing.getDictType());
        log.info("更新字典数据成功，ID: {} / Dictionary data updated, ID: {}", entity.getId());
    }

    @Override
    public void deleteDictData(Long id) {
        SysDictData existing = dictDataMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "字典数据不存在 / Dictionary data not found");
        }
        dictDataMapper.deleteById(id);
        // TODO: 清除Redis缓存 / Clear Redis cache
        // redisTemplate.delete("dict:data:" + existing.getDictType());
        log.info("删除字典数据成功，ID: {} / Dictionary data deleted, ID: {}", id);
    }

    private DictDataVO convertToVO(SysDictData entity) {
        DictDataVO vo = new DictDataVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
