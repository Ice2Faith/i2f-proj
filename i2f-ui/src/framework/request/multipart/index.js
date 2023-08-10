// 定义请求的统一封装
import axios from "axios"
import Config from "@/framework/config"
import Auth from "@/framework/auth"
import Message from "@/framework/message"
import AxiosExceptionHandler from "@/framework/request/exception";
import VueUtil from "@/framework/utils/vue-util";

// 定义默认的参数
axios.defaults.headers['Content-Type'] = Config.REQUEST_DEFAULT_CONTENT_TYPE
axios.defaults.timeout=Config.REQUEST_DEFAULT_TIMEOUT

// 定义默认请求
const MultipartRequest = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  timeout: Config.REQUEST_DEFAULT_TIMEOUT,
  method: 'post',
  responseType: "json",
  headers: {
    'Content-Type': 'multipart/form-data',
  }
})

// 定义请求拦截
MultipartRequest.interceptors.request.use(config => {
  // 如果已登录，则携带登录头
  if (!config.headers[Config.HEADER_TOKEN_NAME]) {
    if (Auth.isAuth()) {
      config.headers[Config.HEADER_TOKEN_NAME] = Auth.getToken()
    }
  }

  // 控制为post
  config.method = 'POST'

  // METHOD 转换为大写
  config.method = config.method.toUpperCase()

  // 如果是GET请求，却使用了data参数，则替换为params参数
  if (config.method == 'GET' && config.data) {
    config.params = Object.assign({}, config.params, config.data)
    config.data = undefined
  }

  // 组装为formData
  let formData = new FormData()
  Object.keys(config.data).forEach((key, index) => {
    formData.append(key, config.data[key])
  })

  config.data = formData

  return config
})

// 定义响应拦截
MultipartRequest.interceptors.response.use(res => {
  if (res.data) {
    let code = res.data.code
    let msg = res.data.msg
    // 判定业务响应码
    if (code == 401) {
      Message.noticeError("当前未登录，请登录后重试")
      Auth.removeToken()
      let route = VueUtil.route()
      let nextRedirect = Auth.getNextRedirect(route.path, route.query)
      VueUtil.router().push(nextRedirect)
    } else if (code == 403) {
      Message.noticeError("403，您没有权限查看资源")
    } else if (code == 404) {
      Message.noticeError("404，找不到目标资源")
    } else {
      Message.noticeError(`服务器响应失败：${code} | ${msg}`)
    }
  }
  return res.data
}, error => {
  AxiosExceptionHandler.handleResponseInterceptorError(error)
  return Promise.reject(error)
})

export default MultipartRequest
