package com.dwl.service;

import com.dwl.common.result.PageResult;
import com.dwl.model.dto.UserCreateDTO;
import com.dwl.model.dto.UserUpdateDTO;
import com.dwl.model.entity.SysUser;
import com.dwl.model.vo.UserVO;

/**
 * 系统用户 服务接口
 * System User Service Interface
 *
 * @author ByDWL
 */
public interface SysUserService {

    /**
     * 根据用户名查询用户
     * Get user by username
     *
     * @param username 用户名 / Username
     * @return 系统用户实体 / System user entity
     */
    SysUser getByUsername(String username);

    /**
     * 创建用户
     * Create user
     *
     * @param dto 创建用户请求 / Create user request
     * @return 新用户ID / New user ID
     */
    Long createUser(UserCreateDTO dto);

    /**
     * 更新用户
     * Update user
     *
     * @param id  用户ID / User ID
     * @param dto 更新用户请求 / Update user request
     */
    void updateUser(Long id, UserUpdateDTO dto);

    /**
     * 删除用户
     * Delete user
     *
     * @param id 用户ID / User ID
     */
    void deleteUser(Long id);

    /**
     * 查询用户详情
     * Get user detail
     *
     * @param id 用户ID / User ID
     * @return 用户视图对象 / User view object
     */
    UserVO getUserDetail(Long id);

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
    PageResult<UserVO> pageUsers(String username, Integer status, int pageNum, int pageSize);

    /**
     * 重置密码
     * Reset password
     *
     * @param id          用户ID / User ID
     * @param newPassword 新密码 / New password
     */
    void resetPassword(Long id, String newPassword);

    /**
     * 更新最后登录时间
     * Update last login time
     *
     * @param id 用户ID / User ID
     */
    void updateLastLoginTime(Long id);

    /**
     * 更新用户状态
     * Update user status
     *
     * @param id     用户ID / User ID
     * @param status 状态（1-启用，0-禁用）/ Status (1-enabled, 0-disabled)
     */
    void updateStatus(Long id, Integer status);
}
