package com.dwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dwl.dao.mapper.SysPermissionMapper;
import com.dwl.dao.mapper.SysRolePermissionMapper;
import com.dwl.model.entity.SysPermission;
import com.dwl.model.entity.SysRolePermission;
import com.dwl.model.vo.PermissionVO;
import com.dwl.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统权限 服务实现类
 * System Permission Service Implementation
 *
 * @author ByDWL
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl implements SysPermissionService {

    /** 权限Mapper / Permission Mapper */
    private final SysPermissionMapper permissionMapper;

    /** 角色权限关联Mapper / Role-Permission Mapper */
    private final SysRolePermissionMapper rolePermissionMapper;

    /**
     * 查询权限树
     * List permissions as tree structure
     *
     * @return 权限树 / Permission tree
     */
    @Override
    public List<PermissionVO> listPermissions() {
        // 查询所有权限 / Query all permissions
        List<SysPermission> allPermissions = permissionMapper.selectList(
                new LambdaQueryWrapper<SysPermission>()
                        .eq(SysPermission::getStatus, 0)
                        .orderByAsc(SysPermission::getSortOrder)
        );

        // 转换为VO列表 / Convert to VO list
        List<PermissionVO> voList = allPermissions.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        // 构建树形结构 / Build tree structure
        return buildTree(voList, 0L);
    }

    /**
     * 根据角色ID列表查询权限
     * Get permissions by role IDs
     *
     * @param roleIds 角色ID列表 / Role ID list
     * @return 权限实体列表 / Permission entity list
     */
    @Override
    public List<SysPermission> getPermissionsByRoleIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return List.of();
        }

        // 查询角色关联的权限ID / Query permission IDs associated with roles
        List<SysRolePermission> rolePermissions = rolePermissionMapper.selectList(
                new LambdaQueryWrapper<SysRolePermission>().in(SysRolePermission::getRoleId, roleIds)
        );

        List<Long> permIds = rolePermissions.stream()
                .map(SysRolePermission::getPermissionId)
                .distinct()
                .collect(Collectors.toList());

        if (permIds.isEmpty()) {
            return List.of();
        }

        return permissionMapper.selectBatchIds(permIds);
    }

    // ==================== 私有方法 / Private Methods ====================

    /**
     * 构建权限树
     * Build permission tree
     *
     * @param allPermissions 所有权限VO列表 / All permission VO list
     * @param parentId       父级ID / Parent ID
     * @return 树形权限列表 / Tree permission list
     */
    private List<PermissionVO> buildTree(List<PermissionVO> allPermissions, Long parentId) {
        List<PermissionVO> tree = new ArrayList<>();
        for (PermissionVO perm : allPermissions) {
            if (parentId.equals(perm.getParentId())) {
                perm.setChildren(buildTree(allPermissions, perm.getId()));
                tree.add(perm);
            }
        }
        return tree;
    }

    /**
     * 实体转视图对象
     * Convert entity to view object
     *
     * @param permission 系统权限实体 / System permission entity
     * @return 权限视图对象 / Permission view object
     */
    private PermissionVO convertToVO(SysPermission permission) {
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(permission, vo);
        return vo;
    }
}
