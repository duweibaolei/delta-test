/**
 * Vue Router 配置
 * Vue Router Configuration
 * <p>
 * 定义应用的路由规则，包含登录页和主布局的子路由。
 * Defines application routing rules, including login page and main layout sub-routes.
 * </p>
 *
 * @author DeltaTest
 */
import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { useUserStore } from '@/stores/user'

/**
 * 主布局子路由
 * Main layout sub-routes
 */
const mainRoutes: RouteRecordRaw[] = [
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('@/views/dashboard/index.vue'),
    meta: { title: '工作台 / Dashboard', icon: 'DashboardOutlined' },
  },
  {
    path: '/analysis',
    name: 'Analysis',
    component: () => import('@/views/analysis/index.vue'),
    meta: { title: '变更分析 / Change Analysis', icon: 'CodeOutlined' },
  },
  {
    path: '/testcase',
    name: 'TestCase',
    component: () => import('@/views/testcase/index.vue'),
    meta: { title: '用例管理 / Test Case', icon: 'FileSearchOutlined' },
  },
  {
    path: '/task',
    name: 'Task',
    component: () => import('@/views/task/index.vue'),
    meta: { title: '任务中心 / Task Center', icon: 'ThunderboltOutlined' },
  },
  {
    path: '/report',
    name: 'Report',
    component: () => import('@/views/report/index.vue'),
    meta: { title: '质量报告 / Quality Report', icon: 'BarChartOutlined' },
  },
]

/**
 * 全局路由
 * Global routes
 */
const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录 / Login', requiresAuth: false },
  },
  {
    path: '/',
    component: () => import('@/layouts/MainLayout.vue'),
    redirect: '/dashboard',
    children: mainRoutes,
    meta: { requiresAuth: true },
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard',
  },
]

/**
 * Router 实例
 * Router instance
 */
const router = createRouter({
  history: createWebHistory(),
  routes,
})

/**
 * 全局路由守卫
 * Global route guard
 * <p>
 * 检查目标路由是否需要认证，未认证时重定向到登录页。
 * Checks if the target route requires authentication, redirects to login page if not authenticated.
 * </p>
 */
router.beforeEach((to, _from, next) => {
  // 设置页面标题 / Set page title
  const appTitle = import.meta.env.VITE_APP_TITLE || 'DeltaTest'
  document.title = to.meta.title ? `${to.meta.title} - ${appTitle}` : appTitle

  // 检查认证 / Check authentication
  const requiresAuth = to.meta.requiresAuth !== false
  if (requiresAuth) {
    const userStore = useUserStore()
    if (!userStore.isLoggedIn) {
      next({ name: 'Login', query: { redirect: to.fullPath } })
      return
    }
  }

  next()
})

export default router
