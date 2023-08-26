// 这里定义了一个基本的App对象
// 直接在main.js中引入进行mount即可
// import VueApp from "@/framework/app"
// VueApp.mount("#app")
import '@/framework/guarder'
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

import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

/// ///////////////////////////////////////////////////////
import Vant from 'vant'
import 'vant/lib/index.css'
import { showDialog, showConfirmDialog } from 'vant'
import * as AntIcons from '@ant-design/icons-vue'
/// ///////////////////////////////////////////////////////
import Directives from '@/framework/directives'
import DirectiveInstaller from '@/framework/directives/install'
/// ///////////////////////////////////////////////////////
import GlobalExceptionInstaller from '@/framework/exception/installer/GlobalExceptionInstaller'

// 全局进度条的配置
NProgress.configure({
  easing: 'ease', // 动画方式
  speed: 1000, // 递增进度条的速度
  showSpinner: false, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3, // 更改启动时使用的最小百分比
  parent: 'body' // 指定进度条的父容器
})

// 使用路由创建App
const VueApp = createApp(App)
  .use(Router)
  .use(Store)
  .use(Vant)

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
VueApp.config.globalProperties.$dialog = showDialog
VueApp.config.globalProperties.$confirm = showConfirmDialog

// 在setup语法中，可以使用如下方式获取对象
// import { getCurrentInstance } from 'vue'
// const app=getCurrentInstance()
// app.$axios({...})

export default VueApp
