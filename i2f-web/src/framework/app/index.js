// 这里定义了一个基本的App对象
// 直接在main.js中引入进行mount即可
// import VueApp from "@/framework/app"
// VueApp.mount("#app")
import { createApp } from 'vue'
import App from '@/framework/app/App.vue'
import Router from '@/framework/router'
import Store from 'vuex'
import BaseRequest from '@/framework/request'
import Auth from '@/framework/auth'
import Config from '@/framework/config'
import Message from '@/framework/message'
import DownloadRequest from '@/framework/request/download'
import MultipartRequest from '@/framework/request/multipart'

import '@/assets/css/scorll-bar.css'
import '@/assets/css/media-adapt.css'
import '@/assets/css/ant-design-adapt.css'

// ant-design-vue
/// ///////////////////////////////////////////////////////
import Antd, { Modal } from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import * as AntIcons from '@ant-design/icons-vue'
/// ///////////////////////////////////////////////////////

// markdown editor
/// ///////////////////////////////////////////////////////
import VueMarkdownEditor from '@kangc/v-md-editor'
import '@kangc/v-md-editor/lib/style/base-editor.css'
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js'
import '@kangc/v-md-editor/lib/theme/style/vuepress.css'

// Prism
import Prism from 'prismjs'
// highlight code
import 'prismjs/components/prism-json'

import createTipPlugin from '@kangc/v-md-editor/lib/plugins/tip/index'
import '@kangc/v-md-editor/lib/plugins/tip/tip.css'

import createEmojiPlugin from '@kangc/v-md-editor/lib/plugins/emoji/index'
import '@kangc/v-md-editor/lib/plugins/emoji/emoji.css'

import createTodoListPlugin from '@kangc/v-md-editor/lib/plugins/todo-list/index'
import '@kangc/v-md-editor/lib/plugins/todo-list/todo-list.css'

import createLineNumberPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index'

import createHighlightLinesPlugin from '@kangc/v-md-editor/lib/plugins/highlight-lines/index'
import '@kangc/v-md-editor/lib/plugins/highlight-lines/highlight-lines.css'

import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index'
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css'

import createAlignPlugin from '@kangc/v-md-editor/lib/plugins/align'

import 'katex'
import 'katex/dist/katex.css'
import createKatexPlugin from '@kangc/v-md-editor/lib/plugins/katex/npm'

import 'mermaid'
import createMermaidPlugin from '@kangc/v-md-editor/lib/plugins/mermaid/npm'
import '@kangc/v-md-editor/lib/plugins/mermaid/mermaid.css'
/// ///////////////////////////////////////////////////////

import * as echarts from 'echarts'
import EchartsCustomTheme from '@/plugins/echarts/theme/custom'

/// ///////////////////////////////////////////////////////
import Directives from '@/framework/directives'
import DirectiveInstaller from '@/framework/directives/install'
/// ///////////////////////////////////////////////////////
import GlobalExceptionInstaller from '@/framework/exception/installer/GlobalExceptionInstaller'

VueMarkdownEditor.use(vuepressTheme, {
  Prism
})
VueMarkdownEditor.use(createTipPlugin())
VueMarkdownEditor.use(createEmojiPlugin())
VueMarkdownEditor.use(createTodoListPlugin())
VueMarkdownEditor.use(createLineNumberPlugin())
VueMarkdownEditor.use(createHighlightLinesPlugin())
VueMarkdownEditor.use(createCopyCodePlugin())
VueMarkdownEditor.use(createAlignPlugin())
VueMarkdownEditor.use(createKatexPlugin())

VueMarkdownEditor.use(createMermaidPlugin())

// 使用路由创建App
const VueApp = createApp(App)
  .use(Router)
  .use(Store)
  .use(Antd)
  .use(VueMarkdownEditor)

// 注册指令
DirectiveInstaller.installAll(VueApp, Directives)

// 注册全局异常处理器
GlobalExceptionInstaller.installJs()
GlobalExceptionInstaller.installVue(VueApp)

// 全局注册ant-icon
Object.keys(AntIcons).forEach(key => {
  VueApp.component(key, AntIcons[key])
})

// 将内容挂载到原型上
VueApp.config.globalProperties.$axios = BaseRequest
VueApp.config.globalProperties.$download = DownloadRequest
VueApp.config.globalProperties.$multipart = MultipartRequest
VueApp.config.globalProperties.$auth = Auth
VueApp.config.globalProperties.$config = Config
VueApp.config.globalProperties.$message = Message
VueApp.config.globalProperties.$modal = Modal
VueApp.config.globalProperties.$echarts = echarts
EchartsCustomTheme(echarts)

// 在setup语法中，可以使用如下方式获取对象
// import { getCurrentInstance } from 'vue'
// const app=getCurrentInstance()
// app.$axios({...})

export default VueApp
