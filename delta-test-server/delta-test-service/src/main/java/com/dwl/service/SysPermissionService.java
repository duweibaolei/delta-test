package com.dwl.service;

import com.dwl.model.entity.SysPermission;
import com.dwl.model.vo.PermissionVO;

import java.util.List;

/**
 * 系统权限 服务接口
 * System Permission Service Interface
 *
 * @author ByDWL
 */
public interface SysPermissionService {

    /**
     * 查询权限树
     * List permissions as tree structure
     *
     * @return 权限树 / Permission tree
     */
    List<PermissionVO> listPermissions();

    /**
     * 根据角色ID列表查询权限
     * Get permissions by role IDs
     *
     * @param roleIds 角色ID列表 / Role ID list
     * @return 权限实体列表 / Permission entity list
     */
    List<SysPermission> getPermissionsByRoleIds(List<Long> roleIds);
}
