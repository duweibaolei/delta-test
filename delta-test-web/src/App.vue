<template>
  <a-config-provider :locale="zhCN">
    <router-view />
  </a-config-provider>
</template>

<script setup lang="ts">
/**
 * 根组件
 * Root Component
 * <p>
 * DeltaTest 应用根组件，包含路由视图。
 * DeltaTest application root component, containing router view.
 * </p>
 *
 * @author DeltaTest
 */
import zhCN from 'ant-design-vue/es/locale/zh_CN'
import dayjs from 'dayjs'
import 'dayjs/locale/zh-cn'

// 设置 dayjs 中文 locale / Set dayjs Chinese locale
dayjs.locale('zh-cn')
</script>

<style>
/* 全局重置 / Global reset */
#app {
  width: 100%;
  height: 100%;
}

html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto,
    'Helvetica Neue', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji',
    'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji';
}
</style>
