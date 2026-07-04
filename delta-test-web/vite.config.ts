import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import Components from 'unplugin-vue-components/vite'
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers'
import AutoImport from 'unplugin-auto-import/vite'

/**
 * Vite 配置
 * Vite Configuration
 * <p>
 * DeltaTest 双模式驱动的Web自动化测试平台 · 前端构建配置
 * DeltaTest Dual-mode Web Automation Testing Platform · Frontend Build Configuration
 * </p>
 */
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd())

  return {
    plugins: [
      // Vue 3 单文件组件支持 / Vue 3 SFC support
      vue(),

      // Ant Design Vue 按需加载 / Ant Design Vue on-demand import
      Components({
        resolvers: [
          AntDesignVueResolver({
            importStyle: 'less',
          }),
        ],
        dts: 'src/components.d.ts',
      }),

      // API 自动导入（Vue/Router/Pinia） / Auto import for Vue/Router/Pinia
      AutoImport({
        imports: ['vue', 'vue-router', 'pinia'],
        dts: 'src/auto-imports.d.ts',
      }),
    ],

    // 路径别名 / Path aliases
    resolve: {
      alias: {
        '@': resolve(__dirname, 'src'),
      },
    },

    // 开发服务器 / Dev server
    server: {
      port: 5173,
      proxy: {
        '/api': {
          target: env.VITE_API_BASE_URL || 'http://localhost:8080',
          changeOrigin: true,
        },
        '/ws': {
          target: env.VITE_API_BASE_URL?.replace('http', 'ws') || 'ws://localhost:8080',
          ws: true,
        },
      },
    },

    // CSS 配置 / CSS configuration
    css: {
      preprocessorOptions: {
        less: {
          javascriptEnabled: true,
        },
      },
    },

    // 构建配置 / Build configuration
    build: {
      target: 'es2022',
      outDir: 'dist',
      sourcemap: false,
      chunkSizeWarningLimit: 1500,
      rollupOptions: {
        output: {
          manualChunks: {
            'vue-vendor': ['vue', 'vue-router', 'pinia'],
            'antd-vendor': ['ant-design-vue', '@ant-design/icons-vue'],
          },
        },
      },
    },
  }
})
