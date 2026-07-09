<!-- 用户管理页面 / User Management Page -->
<template>
  <div class="user-management">
    <!-- 搜索区域 / Search area -->
    <a-form layout="inline" class="search-form">
      <a-form-item :label="'用户名 / Username'">
        <a-input
          v-model:value="searchParams.username"
          :placeholder="'请输入用户名 / Enter username'"
          allow-clear
          @press-enter="handleSearch"
        />
      </a-form-item>
      <a-form-item :label="'状态 / Status'">
        <a-select
          v-model:value="searchParams.status"
          :placeholder="'全部 / All'"
          allow-clear
          style="width: 140px"
        >
          <a-select-option :value="1">{{ '启用 / Enabled' }}</a-select-option>
          <a-select-option :value="0">{{ '禁用 / Disabled' }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="handleSearch">
          {{ '查询 / Search' }}
        </a-button>
        <a-button style="margin-left: 8px" @click="handleReset">
          {{ '重置 / Reset' }}
        </a-button>
      </a-form-item>
    </a-form>

    <!-- 操作按钮区域 / Action buttons area -->
    <div class="action-bar">
      <a-button type="primary" @click="openCreateModal">
        {{ '新建用户 / Create User' }}
      </a-button>
    </div>

    <!-- 数据表格 / Data table -->
    <a-table
      :columns="columns"
      :data-source="tableData"
      :loading="loading"
      :pagination="pagination"
      row-key="id"
      @change="handleTableChange"
    >
      <!-- 状态列 / Status column -->
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'status'">
          <a-tag :color="record.status === 1 ? 'green' : 'red'">
            {{ record.status === 1 ? '启用 / Enabled' : '禁用 / Disabled' }}
          </a-tag>
        </template>

        <!-- 角色列 / Roles column -->
        <template v-else-if="column.dataIndex === 'roles'">
          <a-tag
            v-for="role in record.roles"
            :key="role.id"
            color="blue"
          >
            {{ role.roleName }}
          </a-tag>
          <span v-if="!record.roles?.length" style="color: #999">
            {{ '未分配 / Unassigned' }}
          </span>
        </template>

        <!-- 时间列 / Time columns -->
        <template v-else-if="column.dataIndex === 'lastLoginTime'">
          {{ record.lastLoginTime ? formatTime(record.lastLoginTime) : '-' }}
        </template>
        <template v-else-if="column.dataIndex === 'createTime'">
          {{ record.createTime ? formatTime(record.createTime) : '-' }}
        </template>

        <!-- 操作列 / Action column -->
        <template v-else-if="column.dataIndex === 'action'">
          <a-space>
            <a-button type="link" size="small" @click="openEditModal(record)">
              {{ '编辑 / Edit' }}
            </a-button>
            <a-popconfirm
              :title="`确定要${record.status === 1 ? '禁用' : '启用'}该用户吗？ / Confirm to ${record.status === 1 ? 'disable' : 'enable'}?`"
              @confirm="handleToggleStatus(record)"
            >
              <a-button type="link" size="small">
                {{ record.status === 1 ? '禁用 / Disable' : '启用 / Enable' }}
              </a-button>
            </a-popconfirm>
            <a-button type="link" size="small" @click="openResetPasswordModal(record)">
              {{ '重置密码 / Reset Pwd' }}
            </a-button>
            <a-popconfirm
              :title="`确定要删除用户 ${record.username} 吗？此操作为逻辑删除。 / Confirm to delete user ${record.username}? This is a logical delete.`"
              @confirm="handleDelete(record)"
            >
              <a-button type="link" size="small" danger>
                {{ '删除 / Delete' }}
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <!-- 新建/编辑用户弹窗 / Create/Edit user modal -->
    <a-modal
      v-model:open="userModalVisible"
      :title="modalMode === 'create' ? '新建用户 / Create User' : '编辑用户 / Edit User'"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
      @cancel="handleModalCancel"
    >
      <a-form
        ref="userFormRef"
        :model="userFormData"
        :rules="userFormRules"
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 16 }"
      >
        <a-form-item :label="'用户名 / Username'" name="username">
          <a-input
            v-model:value="userFormData.username"
            :disabled="modalMode === 'edit'"
            :placeholder="'请输入用户名 / Enter username'"
          />
        </a-form-item>
        <a-form-item
          v-if="modalMode === 'create'"
          :label="'密码 / Password'"
          name="password"
        >
          <a-input-password
            v-model:value="userFormData.password"
            :placeholder="'请输入密码（至少6位）/ Enter password (min 6 chars)'"
          />
        </a-form-item>
        <a-form-item :label="'昵称 / Nickname'" name="nickname">
          <a-input
            v-model:value="userFormData.nickname"
            :placeholder="'请输入昵称 / Enter nickname'"
          />
        </a-form-item>
        <a-form-item :label="'邮箱 / Email'" name="email">
          <a-input
            v-model:value="userFormData.email"
            :placeholder="'请输入邮箱 / Enter email'"
          />
        </a-form-item>
        <a-form-item :label="'角色 / Roles'" name="roleIds">
          <a-select
            v-model:value="userFormData.roleIds"
            mode="multiple"
            :placeholder="'请选择角色 / Select roles'"
            :loading="rolesLoading"
          >
            <a-select-option
              v-for="role in roleOptions"
              :key="role.id"
              :value="role.id"
            >
              {{ role.roleName }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          v-if="modalMode === 'edit'"
          :label="'状态 / Status'"
          name="status"
        >
          <a-radio-group v-model:value="userFormData.status">
            <a-radio :value="1">{{ '启用 / Enabled' }}</a-radio>
            <a-radio :value="0">{{ '禁用 / Disabled' }}</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 重置密码弹窗 / Reset password modal -->
    <a-modal
      v-model:open="resetPwdModalVisible"
      :title="'重置密码 / Reset Password'"
      :confirm-loading="resetPwdLoading"
      @ok="handleResetPasswordOk"
      @cancel="resetPwdModalVisible = false"
    >
      <a-form
        ref="resetPwdFormRef"
        :model="resetPwdFormData"
        :rules="resetPwdFormRules"
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 16 }"
      >
        <a-form-item :label="'新密码 / New Password'" name="newPassword">
          <a-input-password
            v-model:value="resetPwdFormData.newPassword"
            :placeholder="'请输入新密码（至少6位）/ Enter new password (min 6 chars)'"
          />
        </a-form-item>
        <a-form-item :label="'确认密码 / Confirm'" name="confirmPassword">
          <a-input-password
            v-model:value="resetPwdFormData.confirmPassword"
            :placeholder="'请再次输入新密码 / Re-enter new password'"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
/**
 * 用户管理页面
 * User Management Page
 * <p>
 * 提供用户的增删改查、状态切换、重置密码等功能。
 * Provides user CRUD, status toggle, and password reset features.
 * </p>
 *
 * @author ByDWL
 */
import { onMounted, reactive, ref } from 'vue'
import { message, type FormInstance } from 'ant-design-vue'
import type { Rule } from 'ant-design-vue/es/form'
import dayjs from 'dayjs'
import {
  pageUsersApi,
  createUserApi,
  updateUserApi,
  deleteUserApi,
  updateUserStatusApi,
  resetPasswordApi,
} from '@/api/user'
import type { UserVO, UserCreateDTO, UserUpdateDTO } from '@/api/user'
import { listRolesApi } from '@/api/role'
import type { RoleVO } from '@/api/role'

// ==================== 搜索参数 / Search Parameters ====================

/** 搜索参数 / Search parameters */
const searchParams = reactive({
  username: undefined as string | undefined,
  status: undefined as number | undefined,
})

// ==================== 表格状态 / Table State ====================

/** 加载状态 / Loading state */
const loading = ref(false)

/** 表格数据 / Table data */
const tableData = ref<UserVO[]>([])

/** 分页配置 / Pagination config */
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
  showTotal: (total: number) => `共 ${total} 条 / Total ${total}`,
})

/** 表格列定义 / Table columns */
const columns = [
  { title: '用户名 / Username', dataIndex: 'username', width: 120 },
  { title: '昵称 / Nickname', dataIndex: 'nickname', width: 120 },
  { title: '邮箱 / Email', dataIndex: 'email', width: 180 },
  { title: '状态 / Status', dataIndex: 'status', width: 100 },
  { title: '角色 / Roles', dataIndex: 'roles', width: 200 },
  { title: '最后登录 / Last Login', dataIndex: 'lastLoginTime', width: 180 },
  { title: '创建时间 / Created', dataIndex: 'createTime', width: 180 },
  { title: '操作 / Action', dataIndex: 'action', width: 280, fixed: 'right' as const },
]

// ==================== 角色选项 / Role Options ====================

/** 角色选项列表 / Role options list */
const roleOptions = ref<RoleVO[]>([])

/** 角色加载状态 / Roles loading state */
const rolesLoading = ref(false)

/**
 * 加载角色列表
 * Load role list
 */
async function loadRoles(): Promise<void> {
  rolesLoading.value = true
  try {
    roleOptions.value = await listRolesApi()
  } catch {
    // 错误已由 HTTP 拦截器处理 / Errors handled by HTTP interceptor
  } finally {
    rolesLoading.value = false
  }
}

// ==================== 数据加载 / Data Loading ====================

/**
 * 加载用户列表
 * Load user list
 */
async function loadData(): Promise<void> {
  loading.value = true
  try {
    const result = await pageUsersApi({
      username: searchParams.username,
      status: searchParams.status,
      pageNum: pagination.current,
      pageSize: pagination.pageSize,
    })
    tableData.value = result.list
    pagination.total = result.total
  } catch {
    // 错误已由 HTTP 拦截器处理 / Errors handled by HTTP interceptor
  } finally {
    loading.value = false
  }
}

/**
 * 格式化时间
 * Format time
 *
 * @param time 时间字符串 / Time string
 * @returns 格式化后的时间 / Formatted time
 */
function formatTime(time: string): string {
  return dayjs(time).format('YYYY-MM-DD HH:mm:ss')
}

// ==================== 搜索与分页 / Search & Pagination ====================

/** 查询 / Search */
function handleSearch(): void {
  pagination.current = 1
  loadData()
}

/** 重置 / Reset */
function handleReset(): void {
  searchParams.username = undefined
  searchParams.status = undefined
  pagination.current = 1
  loadData()
}

/** 表格分页变化 / Table pagination change */
function handleTableChange(pag: { current?: number; pageSize?: number }): void {
  if (pag.current !== undefined) pagination.current = pag.current
  if (pag.pageSize !== undefined) pagination.pageSize = pag.pageSize
  loadData()
}

// ==================== 新建/编辑弹窗 / Create/Edit Modal ====================

/** 弹窗模式 / Modal mode */
type ModalMode = 'create' | 'edit'

/** 弹窗可见 / Modal visible */
const userModalVisible = ref(false)

/** 弹窗模式 / Modal mode */
const modalMode = ref<ModalMode>('create')

/** 弹窗加载 / Modal loading */
const modalLoading = ref(false)

/** 当前编辑的用户ID / Currently editing user ID */
const currentEditId = ref<number | null>(null)

/** 用户表单引用 / User form reference */
const userFormRef = ref<FormInstance>()

/** 用户表单数据 / User form data */
const userFormData = reactive({
  username: '',
  password: '',
  nickname: '',
  email: '',
  roleIds: [] as number[],
  status: 1,
})

/** 用户表单校验规则 / User form validation rules */
const userFormRules: Record<string, Rule[]> = {
  username: [
    { required: true, message: '请输入用户名 / Please enter username', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码 / Please enter password', trigger: 'blur' },
    { min: 6, message: '密码至少6位 / Password must be at least 6 characters', trigger: 'blur' },
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱格式 / Please enter valid email', trigger: 'blur' },
  ],
}

/**
 * 打开新建弹窗
 * Open create modal
 */
function openCreateModal(): void {
  modalMode.value = 'create'
  currentEditId.value = null
  Object.assign(userFormData, {
    username: '',
    password: '',
    nickname: '',
    email: '',
    roleIds: [],
    status: 1,
  })
  userModalVisible.value = true
  userFormRef.value?.clearValidate()
}

/**
 * 打开编辑弹窗
 * Open edit modal
 *
 * @param record 用户记录 / User record
 */
function openEditModal(record: UserVO): void {
  modalMode.value = 'edit'
  currentEditId.value = record.id
  Object.assign(userFormData, {
    username: record.username,
    password: '',
    nickname: record.nickname ?? '',
    email: record.email ?? '',
    roleIds: record.roles?.map((r) => r.id) ?? [],
    status: record.status,
  })
  userModalVisible.value = true
  userFormRef.value?.clearValidate()
}

/**
 * 弹窗确认
 * Modal confirm
 */
async function handleModalOk(): Promise<void> {
  try {
    await userFormRef.value?.validate()
  } catch {
    return
  }

  modalLoading.value = true
  try {
    if (modalMode.value === 'create') {
      const data: UserCreateDTO = {
        username: userFormData.username,
        password: userFormData.password,
        nickname: userFormData.nickname || undefined,
        email: userFormData.email || undefined,
        roleIds: userFormData.roleIds.length > 0 ? userFormData.roleIds : undefined,
      }
      await createUserApi(data)
      message.success('创建用户成功 / User created successfully')
    } else {
      const data: UserUpdateDTO = {
        nickname: userFormData.nickname || undefined,
        email: userFormData.email || undefined,
        roleIds: userFormData.roleIds,
        status: userFormData.status,
      }
      await updateUserApi(currentEditId.value!, data)
      message.success('更新用户成功 / User updated successfully')
    }
    userModalVisible.value = false
    loadData()
  } catch {
    // 错误已由 HTTP 拦截器处理 / Errors handled by HTTP interceptor
  } finally {
    modalLoading.value = false
  }
}

/**
 * 弹窗取消
 * Modal cancel
 */
function handleModalCancel(): void {
  userModalVisible.value = false
}

// ==================== 状态切换 / Status Toggle ====================

/**
 * 切换用户状态
 * Toggle user status
 *
 * @param record 用户记录 / User record
 */
async function handleToggleStatus(record: UserVO): Promise<void> {
  const newStatus = record.status === 1 ? 0 : 1
  try {
    await updateUserStatusApi(record.id, { status: newStatus })
    message.success(
      newStatus === 1
        ? '启用成功 / Enabled successfully'
        : '禁用成功 / Disabled successfully',
    )
    loadData()
  } catch {
    // 错误已由 HTTP 拦截器处理 / Errors handled by HTTP interceptor
  }
}

// ==================== 删除 / Delete ====================

/**
 * 删除用户
 * Delete user
 *
 * @param record 用户记录 / User record
 */
async function handleDelete(record: UserVO): Promise<void> {
  try {
    await deleteUserApi(record.id)
    message.success('删除成功 / Deleted successfully')
    loadData()
  } catch {
    // 错误已由 HTTP 拦截器处理 / Errors handled by HTTP interceptor
  }
}

// ==================== 重置密码 / Reset Password ====================

/** 重置密码弹窗可见 / Reset password modal visible */
const resetPwdModalVisible = ref(false)

/** 重置密码加载状态 / Reset password loading state */
const resetPwdLoading = ref(false)

/** 当前重置密码的用户ID / Current reset password user ID */
const resetPwdUserId = ref<number | null>(null)

/** 重置密码表单引用 / Reset password form reference */
const resetPwdFormRef = ref<FormInstance>()

/** 重置密码表单数据 / Reset password form data */
const resetPwdFormData = reactive({
  newPassword: '',
  confirmPassword: '',
})

/** 确认密码校验器 / Confirm password validator */
const confirmPasswordValidator = async (_rule: unknown, value: string): Promise<void> => {
  if (value && value !== resetPwdFormData.newPassword) {
    throw new Error('两次输入的密码不一致 / Passwords do not match')
  }
}

/** 重置密码表单校验规则 / Reset password form validation rules */
const resetPwdFormRules: Record<string, Rule[]> = {
  newPassword: [
    { required: true, message: '请输入新密码 / Please enter new password', trigger: 'blur' },
    { min: 6, message: '密码至少6位 / Password must be at least 6 characters', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请确认密码 / Please confirm password', trigger: 'blur' },
    { validator: confirmPasswordValidator, trigger: 'blur' },
  ],
}

/**
 * 打开重置密码弹窗
 * Open reset password modal
 *
 * @param record 用户记录 / User record
 */
function openResetPasswordModal(record: UserVO): void {
  resetPwdUserId.value = record.id
  resetPwdFormData.newPassword = ''
  resetPwdFormData.confirmPassword = ''
  resetPwdModalVisible.value = true
  resetPwdFormRef.value?.clearValidate()
}

/**
 * 重置密码确认
 * Reset password confirm
 */
async function handleResetPasswordOk(): Promise<void> {
  try {
    await resetPwdFormRef.value?.validate()
  } catch {
    return
  }

  resetPwdLoading.value = true
  try {
    await resetPasswordApi(resetPwdUserId.value!, {
      newPassword: resetPwdFormData.newPassword,
    })
    message.success('重置密码成功 / Password reset successfully')
    resetPwdModalVisible.value = false
  } catch {
    // 错误已由 HTTP 拦截器处理 / Errors handled by HTTP interceptor
  } finally {
    resetPwdLoading.value = false
  }
}

// ==================== 初始化 / Initialization ====================

onMounted(() => {
  loadData()
  loadRoles()
})
</script>

<style scoped lang="less">
.user-management {
  .search-form {
    margin-bottom: 16px;
  }

  .action-bar {
    margin-bottom: 16px;
  }
}
</style>
