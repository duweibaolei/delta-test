/// <reference types="vite/client" />

/**
 * 环境变量类型声明
 * Environment Variable Type Declarations
 */
declare module '*.vue' {
    import type {DefineComponent} from 'vue'
    const component: DefineComponent<Record<string, unknown>, Record<string, unknown>, unknown>
  export default component
}

interface ImportMetaEnv {
  /** API 基础路径 / API base URL */
  readonly VITE_API_BASE_URL: string
  /** 应用标题 / Application title */
  readonly VITE_APP_TITLE: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
