/**
 * 应用入口
 * Application Entry Point
 * <p>
 * DeltaTest 双模式驱动的Web自动化测试平台 · 前端入口
 * DeltaTest Dual-mode Web Automation Testing Platform · Frontend Entry
 * </p>
 *
 * @author ByDWL
 */
import {createApp} from 'vue'
import {createPinia} from 'pinia'
import App from './App.vue'
import router from './router'

// Ant Design Vue CSS Reset（全局样式，非组件样式）/ Ant Design Vue CSS Reset (global style, not component style)
import 'ant-design-vue/dist/reset.css'

// 全局样式 / Global styles
import './assets/styles/global.less'

const app = createApp(App)

// 状态管理 / State management
app.use(createPinia())

// 路由 / Router
app.use(router)

// UI 组件库由 unplugin-vue-components 按需自动注册，无需 app.use(Antd) 全量引入
// UI components are auto-registered on-demand by unplugin-vue-components, no need for app.use(Antd) full import

app.mount('#app')
