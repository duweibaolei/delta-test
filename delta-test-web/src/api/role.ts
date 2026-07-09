/**
 * 角色管理 API
 * Role Management API
 * <p>
 * 对接 Java 后端 /api/system/roles 接口，提供角色列表查询能力。
 * Connects to Java backend /api/system/roles endpoints, providing role list query.
 * </p>
 *
 * @author ByDWL
 */
import { get } from '@/utils/http'

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

/**
 * 查询角色列表
 * List roles
 *
 * @returns 角色列表 / Role list
 */
export function listRolesApi(): Promise<RoleVO[]> {
  return get<RoleVO[]>('/api/system/roles')
}
