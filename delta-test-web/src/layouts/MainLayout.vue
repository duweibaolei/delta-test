<!-- 主布局 / Main Layout -->
<template>
  <a-layout class="main-layout">
    <!-- 侧边栏 / Sidebar -->
    <a-layout-sider
      v-model:collapsed="collapsed"
      :trigger="null"
      collapsible
      width="240"
      class="layout-sider"
    >
      <!-- Logo -->
      <div class="sider-logo">
        <ThunderboltOutlined :style="{ fontSize: '24px', color: '#1890ff' }" />
        <span v-if="!collapsed" class="sider-title">DeltaTest</span>
      </div>

      <!-- 菜单 / Menu -->
      <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="inline"
        @click="onMenuClick"
      >
        <a-menu-item key="/dashboard">
          <DashboardOutlined />
          <span>工作台</span>
        </a-menu-item>
        <a-menu-item key="/analysis">
          <CodeOutlined />
          <span>变更分析</span>
        </a-menu-item>
        <a-menu-item key="/testcase">
          <FileSearchOutlined />
          <span>用例管理</span>
        </a-menu-item>
        <a-menu-item key="/task">
          <ThunderboltOutlined />
          <span>任务中心</span>
        </a-menu-item>
        <a-menu-item key="/report">
          <BarChartOutlined />
          <span>质量报告</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>

    <!-- 右侧内容 / Right content -->
    <a-layout>
      <!-- 顶部栏 / Top header -->
      <a-layout-header class="layout-header">
        <div class="header-left">
          <MenuFoldOutlined
            v-if="!collapsed"
            class="trigger"
            @click="collapsed = true"
          />
          <MenuUnfoldOutlined
            v-else
            class="trigger"
            @click="collapsed = false"
          />
        </div>
        <div class="header-right">
          <a-dropdown>
            <span class="user-info">
              <UserOutlined />
              <span class="username">{{ userStore.nickname || userStore.username }}</span>
            </span>
            <template #overlay>
              <a-menu>
                <a-menu-item @click="handleLogout">
                  <LogoutOutlined />
                  <span>退出登录 / Logout</span>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </a-layout-header>

      <!-- 内容区域 / Content area -->
      <a-layout-content class="layout-content">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { message } from 'ant-design-vue'
import {
  DashboardOutlined,
  CodeOutlined,
  FileSearchOutlined,
  ThunderboltOutlined,
  BarChartOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UserOutlined,
  LogoutOutlined,
} from '@ant-design/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

/** 侧边栏折叠状态 / Sidebar collapsed state */
const collapsed = ref(false)

/** 当前选中菜单 / Current selected menu */
const selectedKeys = ref<string[]>([route.path])

// 路由变化时更新选中菜单 / Update selected menu on route change
watch(
  () => route.path,
  (path) => {
    selectedKeys.value = [path]
  },
)

/**
 * 菜单点击事件
 * Menu click event
 */
function onMenuClick({ key }: { key: string | number }): void {
  router.push(String(key))
}

/**
 * 退出登录
 * Logout
 */
function handleLogout(): void {
  userStore.logout()
  message.success('已退出登录 / Logged out')
  router.push({ name: 'Login' })
}
</script>

<style scoped lang="less">
.main-layout {
  min-height: 100vh;
}

.layout-sider {
  .sider-logo {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 64px;
    padding: 0 16px;
    background: rgba(255, 255, 255, 0.05);
    gap: 10px;
  }

  .sider-title {
    font-size: 18px;
    font-weight: 700;
    color: #fff;
    white-space: nowrap;
  }
}

.layout-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .trigger {
    font-size: 18px;
    cursor: pointer;
    transition: color 0.3s;

    &:hover {
      color: #1890ff;
    }
  }

  .user-info {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;

    .username {
      font-size: 14px;
      color: #333;
    }
  }
}

.layout-content {
  margin: 24px;
  padding: 24px;
  background: #fff;
  border-radius: 8px;
  min-height: calc(100vh - 64px - 48px - 48px);
}
</style>
