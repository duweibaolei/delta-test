package com.dwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dwl.common.exception.BusinessException;
import com.dwl.common.enums.ErrorCode;
import com.dwl.dao.mapper.SysEnvironmentMapper;
import com.dwl.model.dto.EnvironmentCreateDTO;
import com.dwl.model.entity.SysEnvironment;
import com.dwl.model.vo.EnvironmentVO;
import com.dwl.service.SysEnvironmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统环境 服务实现类
 * System Environment Service Implementation
 *
 * @author ByDWL
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysEnvironmentServiceImpl implements SysEnvironmentService {

    /** 环境Mapper / Environment Mapper */
    private final SysEnvironmentMapper environmentMapper;

    @Override
    public List<EnvironmentVO> listEnvironments() {
        List<SysEnvironment> list = environmentMapper.selectList(
                new LambdaQueryWrapper<SysEnvironment>()
                        .eq(SysEnvironment::getStatus, 0)
                        .orderByAsc(SysEnvironment::getId)
        );
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public Long createEnvironment(EnvironmentCreateDTO dto) {
        SysEnvironment entity = new SysEnvironment();
        BeanUtils.copyProperties(dto, entity);
        entity.setStatus(0);
        environmentMapper.insert(entity);
        log.info("创建环境成功，ID: {} / Environment created, ID: {}", entity.getId());
        return entity.getId();
    }

    @Override
    public void updateEnvironment(Long id, EnvironmentCreateDTO dto) {
        SysEnvironment entity = environmentMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "环境不存在 / Environment not found");
        }
        BeanUtils.copyProperties(dto, entity);
        entity.setId(id);
        environmentMapper.updateById(entity);
        log.info("更新环境成功，ID: {} / Environment updated, ID: {}", id);
    }

    @Override
    public void deleteEnvironment(Long id) {
        SysEnvironment entity = environmentMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "环境不存在 / Environment not found");
        }
        environmentMapper.deleteById(id);
        log.info("删除环境成功，ID: {} / Environment deleted, ID: {}", id);
    }

    private EnvironmentVO convertToVO(SysEnvironment entity) {
        EnvironmentVO vo = new EnvironmentVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
