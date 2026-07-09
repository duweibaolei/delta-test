/**
 * 用户管理 API
 * User Management API
 * <p>
 * 对接 Java 后端 /api/system/users 接口，提供用户增删改查能力。
 * Connects to Java backend /api/system/users endpoints, providing user CRUD.
 * </p>
 *
 * @author ByDWL
 */
import { del, get, post, put } from '@/utils/http'

/** 用户创建请求 / User Create Request */
export interface UserCreateDTO {
  /** 用户名 / Username */
  username: string
  /** 密码 / Password */
  password: string
  /** 昵称 / Nickname */
  nickname?: string
  /** 邮箱 / Email */
  email?: string
  /** 角色ID列表 / Role ID list */
  roleIds?: number[]
}

/** 用户更新请求 / User Update Request */
export interface UserUpdateDTO {
  /** 昵称 / Nickname */
  nickname?: string
  /** 邮箱 / Email */
  email?: string
  /** 头像URL / Avatar URL */
  avatar?: string
  /** 状态: 1-启用 0-禁用 / Status: 1-enabled, 0-disabled */
  status?: number
  /** 角色ID列表 / Role ID list */
  roleIds?: number[]
}

/** 用户状态更新请求 / User Status Update Request */
export interface UserStatusDTO {
  /** 状态: 1-启用 0-禁用 / Status: 1-enabled, 0-disabled */
  status: number
}

/** 重置密码请求 / Reset Password Request */
export interface ResetPasswordDTO {
  /** 新密码 / New password */
  newPassword: string
}

/** 角色视图对象 / Role View Object */
export interface RoleVO {
  /** 角色ID / Role ID */
  id: number
  /** 角色编码 / Role code */
  roleCode: string
  /** 角色名称 / Role name */
  roleName: string
  /** 角色描述 / Role description */
  description?: string
  /** 状态: 1-启用 0-禁用 / Status: 1-enabled, 0-disabled */
  status?: number
}

/** 用户视图对象 / User View Object */
export interface UserVO {
  /** 用户ID / User ID */
  id: number
  /** 用户名 / Username */
  username: string
  /** 昵称 / Nickname */
  nickname?: string
  /** 邮箱 / Email */
  email?: string
  /** 头像URL / Avatar URL */
  avatar?: string
  /** 状态: 1-启用 0-禁用 / Status: 1-enabled, 0-disabled */
  status: number
  /** 最后登录时间 / Last login time */
  lastLoginTime?: string
  /** 创建时间 / Creation time */
  createTime?: string
  /** 角色列表 / Role list */
  roles: RoleVO[]
}

/** 分页结果 / Page Result */
export interface PageResult<T> {
  /** 数据列表 / Data list */
  list: T[]
  /** 总记录数 / Total record count */
  total: number
  /** 当前页码 / Current page number */
  pageNum: number
  /** 每页大小 / Page size */
  pageSize: number
  /** 总页数 / Total page count */
  pages: number
}

/** 分页查询参数 / Page query parameters */
export interface UserPageParams {
  /** 用户名（模糊查询）/ Username (fuzzy) */
  username?: string
  /** 状态 / Status */
  status?: number
  /** 页码 / Page number */
  pageNum?: number
  /** 每页大小 / Page size */
  pageSize?: number
}

/**
 * 分页查询用户
 * Page query users
 *
 * @param params 查询参数 / Query parameters
 * @returns 分页结果 / Paginated result
 */
export function pageUsersApi(params: UserPageParams): Promise<PageResult<UserVO>> {
  return get<PageResult<UserVO>>('/api/system/users/page', { params })
}

/**
 * 查询用户详情
 * Get user detail
 *
 * @param id 用户ID / User ID
 * @returns 用户视图对象 / User view object
 */
export function getUserDetailApi(id: number): Promise<UserVO> {
  return get<UserVO>(`/api/system/users/${id}`)
}

/**
 * 创建用户
 * Create user
 *
 * @param data 创建用户请求 / Create user request
 * @returns 新用户ID / New user ID
 */
export function createUserApi(data: UserCreateDTO): Promise<number> {
  return post<number>('/api/system/users', data)
}

/**
 * 更新用户
 * Update user
 *
 * @param id   用户ID / User ID
 * @param data 更新用户请求 / Update user request
 */
export function updateUserApi(id: number, data: UserUpdateDTO): Promise<void> {
  return put<void>(`/api/system/users/${id}`, data)
}

/**
 * 删除用户（逻辑删除）
 * Delete user (logical delete)
 *
 * @param id 用户ID / User ID
 */
export function deleteUserApi(id: number): Promise<void> {
  return del<void>(`/api/system/users/${id}`)
}

/**
 * 更新用户状态
 * Update user status
 *
 * @param id   用户ID / User ID
 * @param data 状态更新请求 / Status update request
 */
export function updateUserStatusApi(id: number, data: UserStatusDTO): Promise<void> {
  return put<void>(`/api/system/users/${id}/status`, data)
}

/**
 * 重置密码
 * Reset password
 *
 * @param id   用户ID / User ID
 * @param data 重置密码请求 / Reset password request
 */
export function resetPasswordApi(id: number, data: ResetPasswordDTO): Promise<void> {
  return put<void>(`/api/system/users/${id}/reset-password`, data)
}
