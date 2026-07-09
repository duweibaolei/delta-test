package com.dwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dwl.common.exception.BusinessException;
import com.dwl.common.enums.ErrorCode;
import com.dwl.common.result.PageResult;
import com.dwl.dao.mapper.SysRepositoryMapper;
import com.dwl.model.dto.RepositoryCreateDTO;
import com.dwl.model.entity.SysRepository;
import com.dwl.model.vo.RepositoryVO;
import com.dwl.service.SysRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 系统代码仓库 服务实现类
 * System Repository Service Implementation
 *
 * @author ByDWL
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRepositoryServiceImpl implements SysRepositoryService {

    /** 仓库Mapper / Repository Mapper */
    private final SysRepositoryMapper repositoryMapper;

    @Override
    public PageResult<RepositoryVO> pageRepositories(String repoName, int pageNum, int pageSize) {
        LambdaQueryWrapper<SysRepository> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(repoName != null && !repoName.isEmpty(), SysRepository::getRepoName, repoName)
               .orderByDesc(SysRepository::getCreatedAt);

        Page<SysRepository> page = repositoryMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        List<RepositoryVO> voList = page.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, page.getTotal(), pageNum, pageSize);
    }

    @Override
    public Long createRepository(RepositoryCreateDTO dto) {
        SysRepository entity = new SysRepository();
        BeanUtils.copyProperties(dto, entity);
        entity.setStatus(0);
        repositoryMapper.insert(entity);
        log.info("创建仓库成功，ID: {} / Repository created, ID: {}", entity.getId());
        return entity.getId();
    }

    @Override
    public void updateRepository(Long id, RepositoryCreateDTO dto) {
        SysRepository entity = repositoryMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "仓库不存在 / Repository not found");
        }
        BeanUtils.copyProperties(dto, entity);
        entity.setId(id);
        repositoryMapper.updateById(entity);
        log.info("更新仓库成功，ID: {} / Repository updated, ID: {}", id);
    }

    @Override
    public void deleteRepository(Long id) {
        SysRepository entity = repositoryMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "仓库不存在 / Repository not found");
        }
        repositoryMapper.deleteById(id);
        log.info("删除仓库成功，ID: {} / Repository deleted, ID: {}", id);
    }

    @Override
    public String generateWebhookUrl(Long id) {
        SysRepository entity = repositoryMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "仓库不存在 / Repository not found");
        }

        // 生成Webhook URL（含UUID保证唯一性）/ Generate webhook URL with UUID for uniqueness
        String webhookToken = UUID.randomUUID().toString().replace("-", "");
        String webhookUrl = "/api/webhook/repo/" + webhookToken;

        entity.setWebhookUrl(webhookUrl);
        repositoryMapper.updateById(entity);

        log.info("生成Webhook URL成功，仓库ID: {} / Webhook URL generated, repository ID: {}", id);
        return webhookUrl;
    }

    private RepositoryVO convertToVO(SysRepository entity) {
        RepositoryVO vo = new RepositoryVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
