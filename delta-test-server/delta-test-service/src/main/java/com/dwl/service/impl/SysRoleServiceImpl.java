package com.dwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dwl.common.exception.BusinessException;
import com.dwl.common.result.ErrorCode;
import com.dwl.dao.mapper.SysRoleMapper;
import com.dwl.dao.mapper.SysRolePermissionMapper;
import com.dwl.dao.mapper.SysUserRoleMapper;
import com.dwl.model.dto.RoleCreateDTO;
import com.dwl.model.dto.RoleUpdateDTO;
import com.dwl.model.entity.SysRole;
import com.dwl.model.entity.SysRolePermission;
import com.dwl.model.entity.SysUserRole;
import com.dwl.model.vo.RoleVO;
import com.dwl.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色 服务实现类
 * System Role Service Implementation
 *
 * @author DeltaTest
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    /** 角色Mapper / Role Mapper */
    private final SysRoleMapper roleMapper;

    /** 角色权限关联Mapper / Role-Permission Mapper */
    private final SysRolePermissionMapper rolePermissionMapper;

    /** 用户角色关联Mapper / User-Role Mapper */
    private final SysUserRoleMapper userRoleMapper;

    /**
     * 创建角色
     * Create role
     *
     * @param dto 创建角色请求 / Create role request
     * @return 新角色ID / New role ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRole(RoleCreateDTO dto) {
        SysRole role = new SysRole();
        role.setRoleCode(dto.getRoleCode());
        role.setRoleName(dto.getRoleName());
        role.setDescription(dto.getRoleDesc());
        role.setStatus(0);

        roleMapper.insert(role);

        // 分配权限 / Assign permissions
        if (dto.getPermissionIds() != null && !dto.getPermissionIds().isEmpty()) {
            assignPermissions(role.getId(), dto.getPermissionIds());
        }

        log.info("创建角色成功，ID: {} / Role created successfully, ID: {}", role.getId());
        return role.getId();
    }

    /**
     * 更新角色
     * Update role
     *
     * @param id  角色ID / Role ID
     * @param dto 更新角色请求 / Update role request
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Long id, RoleUpdateDTO dto) {
        SysRole role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "角色不存在 / Role not found");
        }

        if (dto.getRoleName() != null) {
            role.setRoleName(dto.getRoleName());
        }
        if (dto.getRoleDesc() != null) {
            role.setDescription(dto.getRoleDesc());
        }
        if (dto.getStatus() != null) {
            role.setStatus(dto.getStatus());
        }

        roleMapper.updateById(role);

        // 更新权限 / Update permissions
        if (dto.getPermissionIds() != null) {
            assignPermissions(id, dto.getPermissionIds());
        }

        log.info("更新角色成功，ID: {} / Role updated successfully, ID: {}", id);
    }

    /**
     * 删除角色
     * Delete role
     *
     * @param id 角色ID / Role ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        SysRole role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "角色不存在 / Role not found");
        }

        // 检查是否有用户关联此角色 / Check if any user is associated with this role
        long userCount = userRoleMapper.selectCount(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id)
        );
        if (userCount > 0) {
            throw new BusinessException(ErrorCode.CONFLICT, "角色下存在用户，无法删除 / Role has users, cannot delete");
        }

        // 删除角色 / Delete role
        roleMapper.deleteById(id);

        // 删除角色权限关联 / Delete role-permission associations
        rolePermissionMapper.delete(
                new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, id)
        );

        log.info("删除角色成功，ID: {} / Role deleted successfully, ID: {}", id);
    }

    /**
     * 查询角色列表
     * List roles
     *
     * @return 角色视图对象列表 / Role view object list
     */
    @Override
    public List<RoleVO> listRoles() {
        List<SysRole> roles = roleMapper.selectList(
                new LambdaQueryWrapper<SysRole>().orderByAsc(SysRole::getId)
        );
        return roles.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 查询角色详情
     * Get role detail
     *
     * @param id 角色ID / Role ID
     * @return 角色视图对象 / Role view object
     */
    @Override
    public RoleVO getRoleDetail(Long id) {
        SysRole role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "角色不存在 / Role not found");
        }
        return convertToVO(role);
    }

    // ==================== 私有方法 / Private Methods ====================

    /**
     * 分配权限给角色
     * Assign permissions to role
     *
     * @param roleId        角色ID / Role ID
     * @param permissionIds 权限ID列表 / Permission ID list
     */
    private void assignPermissions(Long roleId, List<Long> permissionIds) {
        // 先删除原有权限关联 / Delete existing permission associations first
        rolePermissionMapper.delete(
                new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, roleId)
        );

        // 批量插入新权限关联 / Batch insert new permission associations
        for (Long permId : permissionIds) {
            SysRolePermission rp = new SysRolePermission();
            rp.setRoleId(roleId);
            rp.setPermissionId(permId);
            rolePermissionMapper.insert(rp);
        }
    }

    /**
     * 实体转视图对象
     * Convert entity to view object
     *
     * @param role 系统角色实体 / System role entity
     * @return 角色视图对象 / Role view object
     */
    private RoleVO convertToVO(SysRole role) {
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);
        return vo;
    }
}
