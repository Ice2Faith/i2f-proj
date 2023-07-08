// 这里定义了一个基本的App对象
// 直接在main.js中引入进行mount即可
// import VueApp from "@/framework/app"
// VueApp.mount("#app")

import {createApp} from 'vue'
import App from '@/framework/app/App.vue'
import Router from '@/framework/router'
import Store from "vuex"
import BaseRequest from "@/framework/request"
import Auth from "@/framework/auth"
import Config from "@/framework/config"
import Message from "@/framework/message"
import DownloadRequest from "@/framework/request/download"
import MultipartRequest from "@/framework/request/multipart"
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';

import * as AntIcons from '@ant-design/icons-vue'

// 使用路由创建App
const VueApp = createApp(App)
  .use(Router)
  .use(Store)
  .use(Antd)

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

// 在setup语法中，可以使用如下方式获取对象
// import { getCurrentInstance } from 'vue'
// const app=getCurrentInstance()
// app.$axios({...})

export default VueApp