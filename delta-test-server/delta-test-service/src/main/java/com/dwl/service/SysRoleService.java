package com.dwl.service;

import com.dwl.model.dto.RoleCreateDTO;
import com.dwl.model.dto.RoleUpdateDTO;
import com.dwl.model.vo.RoleVO;

import java.util.List;

/**
 * 系统角色 服务接口
 * System Role Service Interface
 *
 * @author DeltaTest
 */
public interface SysRoleService {

    /**
     * 创建角色
     * Create role
     *
     * @param dto 创建角色请求 / Create role request
     * @return 新角色ID / New role ID
     */
    Long createRole(RoleCreateDTO dto);

    /**
     * 更新角色
     * Update role
     *
     * @param id  角色ID / Role ID
     * @param dto 更新角色请求 / Update role request
     */
    void updateRole(Long id, RoleUpdateDTO dto);

    /**
     * 删除角色
     * Delete role
     *
     * @param id 角色ID / Role ID
     */
    void deleteRole(Long id);

    /**
     * 查询角色列表
     * List roles
     *
     * @return 角色视图对象列表 / Role view object list
     */
    List<RoleVO> listRoles();

    /**
     * 查询角色详情
     * Get role detail
     *
     * @param id 角色ID / Role ID
     * @return 角色视图对象 / Role view object
     */
    RoleVO getRoleDetail(Long id);
}
