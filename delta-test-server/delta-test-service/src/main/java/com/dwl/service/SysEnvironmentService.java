package com.dwl.service;

import com.dwl.model.dto.EnvironmentCreateDTO;
import com.dwl.model.vo.EnvironmentVO;

import java.util.List;

/**
 * 系统环境 服务接口
 * System Environment Service Interface
 *
 * @author DeltaTest
 */
public interface SysEnvironmentService {

    /**
     * 查询环境列表
     * List environments
     *
     * @return 环境视图对象列表 / Environment view object list
     */
    List<EnvironmentVO> listEnvironments();

    /**
     * 创建环境
     * Create environment
     *
     * @param dto 创建环境请求 / Create environment request
     * @return 新环境ID / New environment ID
     */
    Long createEnvironment(EnvironmentCreateDTO dto);

    /**
     * 更新环境
     * Update environment
     *
     * @param id  环境ID / Environment ID
     * @param dto 更新环境请求 / Update environment request
     */
    void updateEnvironment(Long id, EnvironmentCreateDTO dto);

    /**
     * 删除环境
     * Delete environment
     *
     * @param id 环境ID / Environment ID
     */
    void deleteEnvironment(Long id);
}
