package com.dwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dwl.common.exception.BusinessException;
import com.dwl.common.result.ErrorCode;
import com.dwl.common.result.PageResult;
import com.dwl.dao.mapper.SysRoleMapper;
import com.dwl.dao.mapper.SysUserMapper;
import com.dwl.dao.mapper.SysUserRoleMapper;
import com.dwl.model.dto.UserCreateDTO;
import com.dwl.model.dto.UserUpdateDTO;
import com.dwl.model.entity.SysRole;
import com.dwl.model.entity.SysUser;
import com.dwl.model.entity.SysUserRole;
import com.dwl.model.vo.RoleVO;
import com.dwl.model.vo.UserVO;
import com.dwl.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户 服务实现类
 * System User Service Implementation
 *
 * @author DeltaTest
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    /** 用户Mapper / User Mapper */
    private final SysUserMapper userMapper;

    /** 角色Mapper / Role Mapper */
    private final SysRoleMapper roleMapper;

    /** 用户角色关联Mapper / User-Role Mapper */
    private final SysUserRoleMapper userRoleMapper;

    /** 密码编码器 / Password encoder */
    private final PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查询用户
     * Get user by username
     *
     * @param username 用户名 / Username
     * @return 系统用户实体 / System user entity
     */
    @Override
    public SysUser getByUsername(String username) {
        return userMapper.selectOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username)
        );
    }

    /**
     * 创建用户
     * Create user
     *
     * @param dto 创建用户请求 / Create user request
     * @return 新用户ID / New user ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateDTO dto) {
        // 检查用户名是否已存在 / Check if username already exists
        SysUser existing = getByUsername(dto.getUsername());
        if (existing != null) {
            throw new BusinessException(ErrorCode.CONFLICT, "用户名已存在 / Username already exists");
        }

        // 创建用户实体 / Create user entity
        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRealName(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setStatus(0);

        userMapper.insert(user);

        // 分配角色 / Assign roles
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            assignRoles(user.getId(), dto.getRoleIds());
        }

        log.info("创建用户成功，ID: {} / User created successfully, ID: {}", user.getId());
        return user.getId();
    }

    /**
     * 更新用户
     * Update user
     *
     * @param id  用户ID / User ID
     * @param dto 更新用户请求 / Update user request
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(Long id, UserUpdateDTO dto) {
        SysUser user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        // 更新基本信息 / Update basic info
        if (dto.getNickname() != null) {
            user.setRealName(dto.getNickname());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getAvatar() != null) {
            user.setAvatar(dto.getAvatar());
        }
        if (dto.getStatus() != null) {
            user.setStatus(dto.getStatus());
        }

        userMapper.updateById(user);

        // 更新角色 / Update roles
        if (dto.getRoleIds() != null) {
            assignRoles(id, dto.getRoleIds());
        }

        log.info("更新用户成功，ID: {} / User updated successfully, ID: {}", id);
    }

    /**
     * 删除用户
     * Delete user
     *
     * @param id 用户ID / User ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        SysUser user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        // 删除用户 / Delete user
        userMapper.deleteById(id);

        // 删除用户角色关联 / Delete user-role associations
        userRoleMapper.delete(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id)
        );

        log.info("删除用户成功，ID: {} / User deleted successfully, ID: {}", id);
    }

    /**
     * 查询用户详情
     * Get user detail
     *
     * @param id 用户ID / User ID
     * @return 用户视图对象 / User view object
     */
    @Override
    public UserVO getUserDetail(Long id) {
        SysUser user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        return convertToVO(user);
    }

    /**
     * 分页查询用户
     * Page query users
     *
     * @param username 用户名（模糊查询）/ Username (fuzzy)
     * @param status   状态 / Status
     * @param pageNum  页码 / Page number
     * @param pageSize 每页大小 / Page size
     * @return 分页结果 / Paginated result
     */
    @Override
    public PageResult<UserVO> pageUsers(String username, Integer status, int pageNum, int pageSize) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(username != null && !username.isEmpty(), SysUser::getUsername, username)
               .eq(status != null, SysUser::getStatus, status)
               .orderByDesc(SysUser::getCreatedAt);

        Page<SysUser> page = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        List<UserVO> voList = page.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, page.getTotal(), pageNum, pageSize);
    }

    /**
     * 重置密码
     * Reset password
     *
     * @param id          用户ID / User ID
     * @param newPassword 新密码 / New password
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long id, String newPassword) {
        SysUser user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateById(user);

        log.info("重置密码成功，用户ID: {} / Password reset successfully, user ID: {}", id);
    }

    /**
     * 更新最后登录时间
     * Update last login time
     *
     * @param id 用户ID / User ID
     */
    @Override
    public void updateLastLoginTime(Long id) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    // ==================== 私有方法 / Private Methods ====================

    /**
     * 分配角色给用户
     * Assign roles to user
     *
     * @param userId  用户ID / User ID
     * @param roleIds 角色ID列表 / Role ID list
     */
    private void assignRoles(Long userId, List<Long> roleIds) {
        // 先删除原有角色关联 / Delete existing role associations first
        userRoleMapper.delete(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId)
        );

        // 批量插入新角色关联 / Batch insert new role associations
        for (Long roleId : roleIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }

    /**
     * 实体转视图对象
     * Convert entity to view object
     *
     * @param user 系统用户实体 / System user entity
     * @return 用户视图对象 / User view object
     */
    private UserVO convertToVO(SysUser user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);

        // 查询用户的角色 / Query user's roles
        List<SysUserRole> userRoles = userRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getId())
        );

        if (!userRoles.isEmpty()) {
            List<Long> roleIds = userRoles.stream()
                    .map(SysUserRole::getRoleId)
                    .collect(Collectors.toList());

            List<SysRole> roles = roleMapper.selectBatchIds(roleIds);
            List<RoleVO> roleVOs = roles.stream().map(role -> {
                RoleVO roleVO = new RoleVO();
                BeanUtils.copyProperties(role, roleVO);
                return roleVO;
            }).collect(Collectors.toList());

            vo.setRoles(roleVOs);
        } else {
            vo.setRoles(Collections.emptyList());
        }

        return vo;
    }
}
