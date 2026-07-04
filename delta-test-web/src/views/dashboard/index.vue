<!-- 工作台页面 / Dashboard Page -->
<template>
  <div class="dashboard-page">
    <a-page-header title="工作台 / Dashboard" sub-title="欢迎使用 DeltaTest" />

    <a-row :gutter="[16, 16]">
      <a-col :span="6">
        <a-card>
          <a-statistic title="今日提交 / Today Commits" :value="0">
            <template #prefix><CodeOutlined /></template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <a-statistic title="高风险变更 / High Risk" :value="0" :value-style="{ color: '#cf1322' }">
            <template #prefix><WarningOutlined /></template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <a-statistic title="执行中任务 / Running Tasks" :value="0">
            <template #prefix><ThunderboltOutlined /></template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <a-statistic title="通过率 / Pass Rate" :value="0" suffix="%">
            <template #prefix><CheckCircleOutlined /></template>
          </a-statistic>
        </a-card>
      </a-col>
    </a-row>

    <a-card style="margin-top: 16px" title="快速入口 / Quick Access">
      <a-row :gutter="16">
        <a-col :span="8">
          <a-button type="primary" block size="large" @click="$router.push('/analysis')">
            <CodeOutlined /> 变更分析
          </a-button>
        </a-col>
        <a-col :span="8">
          <a-button block size="large" @click="$router.push('/testcase')">
            <FileSearchOutlined /> 用例管理
          </a-button>
        </a-col>
        <a-col :span="8">
          <a-button block size="large" @click="$router.push('/task')">
            <ThunderboltOutlined /> 任务中心
          </a-button>
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import {
  CheckCircleOutlined,
  CodeOutlined,
  FileSearchOutlined,
  ThunderboltOutlined,
  WarningOutlined,
} from '@ant-design/icons-vue'
</script>

<style scoped lang="less">
.dashboard-page {
  /* 占位，后续迭代填充 / Placeholder, will be filled in later iterations */
}
</style>
