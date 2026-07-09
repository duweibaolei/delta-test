package com.dwl.service;

import com.dwl.common.result.PageResult;
import com.dwl.model.dto.RepositoryCreateDTO;
import com.dwl.model.vo.RepositoryVO;

/**
 * 系统代码仓库 服务接口
 * System Repository Service Interface
 *
 * @author ByDWL
 */
public interface SysRepositoryService {

    /**
     * 分页查询仓库
     * Page query repositories
     *
     * @param repoName 仓库名称 / Repository name
     * @param pageNum  页码 / Page number
     * @param pageSize 每页大小 / Page size
     * @return 分页结果 / Paginated result
     */
    PageResult<RepositoryVO> pageRepositories(String repoName, int pageNum, int pageSize);

    /**
     * 创建仓库
     * Create repository
     *
     * @param dto 创建仓库请求 / Create repository request
     * @return 新仓库ID / New repository ID
     */
    Long createRepository(RepositoryCreateDTO dto);

    /**
     * 更新仓库
     * Update repository
     *
     * @param id  仓库ID / Repository ID
     * @param dto 更新仓库请求 / Update repository request
     */
    void updateRepository(Long id, RepositoryCreateDTO dto);

    /**
     * 删除仓库
     * Delete repository
     *
     * @param id 仓库ID / Repository ID
     */
    void deleteRepository(Long id);

    /**
     * 生成Webhook URL
     * Generate webhook URL
     *
     * @param id 仓库ID / Repository ID
     * @return Webhook URL
     */
    String generateWebhookUrl(Long id);
}
