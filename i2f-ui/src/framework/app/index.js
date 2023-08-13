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

import DebuggerProtection from '@/framework/debugger/DebuggerProtection'

import '@/assets/css/scorll-bar.css'
import '@/assets/css/media-adapt.css'

/// ///////////////////////////////////////////////////////
import Directives from '@/framework/directives'
import DirectiveInstaller from '@/framework/directives/install'
/// ///////////////////////////////////////////////////////
import GlobalExceptionInstaller from '@/framework/exception/installer/GlobalExceptionInstaller'

if (process.env.NODE_ENV == 'prod') {
  DebuggerProtection.install()
}

// 使用路由创建App
const VueApp = createApp(App)
  .use(Router)
  .use(Store)

// 注册指令
DirectiveInstaller.installAll(VueApp, Directives)

// 注册全局异常处理器
GlobalExceptionInstaller.installJs()
GlobalExceptionInstaller.installVue(VueApp)

// 将内容挂载到原型上
VueApp.config.globalProperties.$axios = BaseRequest
VueApp.config.globalProperties.$download = DownloadRequest
VueApp.config.globalProperties.$multipart = MultipartRequest
VueApp.config.globalProperties.$auth = Auth
VueApp.config.globalProperties.$config = Config
VueApp.config.globalProperties.$message = Message

// 在setup语法中，可以使用如下方式获取对象
// import { getCurrentInstance } from 'vue'
// const app=getCurrentInstance()
// app.$axios({...})

export default VueApp
