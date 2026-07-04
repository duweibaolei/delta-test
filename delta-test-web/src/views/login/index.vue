<!-- 登录页面 / Login Page -->
<template>
  <div class="login-container">
    <div class="login-card">
      <!-- Logo 与标题 / Logo and title -->
      <div class="login-header">
        <div class="login-logo">
          <ThunderboltOutlined :style="{ fontSize: '36px', color: '#1890ff' }" />
        </div>
        <h1 class="login-title">DeltaTest</h1>
        <p class="login-subtitle">双模式驱动的Web自动化测试平台</p>
        <p class="login-subtitle-en">Dual-mode Web Automation Testing Platform</p>
      </div>

      <!-- 登录表单 / Login form -->
      <a-form
        :model="loginForm"
        :rules="loginRules"
        @finish="handleLogin"
        layout="vertical"
        class="login-form"
      >
        <a-form-item name="username" :label="t('username')">
          <a-input
            v-model:value="loginForm.username"
            :placeholder="t('usernamePlaceholder')"
            size="large"
            allow-clear
          >
            <template #prefix>
              <UserOutlined style="color: rgba(0, 0, 0, 0.25)" />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item name="password" :label="t('password')">
          <a-input-password
            v-model:value="loginForm.password"
            :placeholder="t('passwordPlaceholder')"
            size="large"
            allow-clear
          >
            <template #prefix>
              <LockOutlined style="color: rgba(0, 0, 0, 0.25)" />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item>
          <a-button
            type="primary"
            html-type="submit"
            size="large"
            :loading="loginLoading"
            block
          >
            {{ t('loginButton') }}
          </a-button>
        </a-form-item>
      </a-form>

      <!-- 底部信息 / Footer info -->
      <div class="login-footer">
        <span class="login-copyright">© 2026 DeltaTest</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { message } from 'ant-design-vue'
import { UserOutlined, LockOutlined, ThunderboltOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

/** 登录加载状态 / Login loading state */
const loginLoading = ref(false)

/** 登录表单数据 / Login form data */
const loginForm = reactive({
  username: '',
  password: '',
})

/** 表单校验规则 / Form validation rules */
const loginRules = {
  username: [
    { required: true, message: t('usernameRequired'), trigger: 'blur' },
  ],
  password: [
    { required: true, message: t('passwordRequired'), trigger: 'blur' },
    { min: 6, message: t('passwordMinLength'), trigger: 'blur' },
  ],
}

/**
 * 多语言文本（简化版，后期可替换为 vue-i18n）
 * i18n texts (simplified, can be replaced with vue-i18n later)
 */
function t(key: string): string {
  const texts: Record<string, string> = {
    username: '用户名 / Username',
    password: '密码 / Password',
    usernamePlaceholder: '请输入用户名 / Enter username',
    passwordPlaceholder: '请输入密码 / Enter password',
    loginButton: '登 录 / Login',
    usernameRequired: '请输入用户名 / Username is required',
    passwordRequired: '请输入密码 / Password is required',
    passwordMinLength: '密码至少6位 / Password must be at least 6 characters',
    loginSuccess: '登录成功 / Login successful',
    loginFailed: '登录失败 / Login failed',
  }
  return texts[key] || key
}

/**
 * 处理登录
 * Handle login
 */
async function handleLogin(): Promise<void> {
  loginLoading.value = true
  try {
    await userStore.login({
      username: loginForm.username,
      password: loginForm.password,
    })

    message.success(t('loginSuccess'))

    // 跳转到原始请求页或首页 / Redirect to original request page or home
    const redirect = (route.query.redirect as string) || '/dashboard'
    router.push(redirect)
  } catch {
    message.error(t('loginFailed'))
  } finally {
    loginLoading.value = false
  }
}
</script>

<style scoped lang="less">
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 420px;
  padding: 40px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.login-logo {
  margin-bottom: 16px;
}

.login-title {
  font-size: 28px;
  font-weight: 700;
  color: #1890ff;
  margin: 0 0 8px;
}

.login-subtitle {
  font-size: 14px;
  color: #666;
  margin: 0;
}

.login-subtitle-en {
  font-size: 12px;
  color: #999;
  margin: 4px 0 0;
}

.login-form {
  margin-top: 24px;
}

.login-footer {
  text-align: center;
  margin-top: 24px;
}

.login-copyright {
  font-size: 12px;
  color: #999;
}
</style>
