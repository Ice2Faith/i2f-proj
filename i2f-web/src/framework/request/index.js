// 定义请求的统一封装
import axios from 'axios'
import Config from '@/framework/config'
import Auth from '@/framework/auth'
import Message from '@/framework/message'
import AxiosExceptionHandler from '@/framework/request/exception'
import VueUtil from '@/framework/utils/vue-util'
import SecureTransfer from '@/framework/secure/core/secure-transfer'
import SecureTransferFilter from '@/framework/secure/core/secure-transfer-filter'

// 定义默认的参数
axios.defaults.headers['Content-Type'] = Config.REQUEST_DEFAULT_CONTENT_TYPE
axios.defaults.timeout = Config.REQUEST_DEFAULT_TIMEOUT

// 定义默认请求
const BaseRequest = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  timeout: Config.REQUEST_DEFAULT_TIMEOUT,
  responseType: 'json'
})

// 定义请求拦截
BaseRequest.interceptors.request.use(config => {
  // 如果已登录，则携带登录头
  if (!config.headers[Config.HEADER_TOKEN_NAME]) {
    if (Auth.isAuth()) {
      config.headers[Config.HEADER_TOKEN_NAME] = Auth.getToken()
    }
  }

  SecureTransfer.getSecureHeaderInto(config.headers, true, true)
  SecureTransferFilter.requestFilter(config)

  return config
})

// 定义响应拦截
BaseRequest.interceptors.response.use(res => {
  SecureTransferFilter.responseFilter(res)

  if (res.data) {
    const code = res.data.code
    const msg = res.data.msg
    if (code == undefined || code == 200) {
      return res.data
    }

    // 判定业务响应码
    if (code == 401) {
      Message.noticeError(msg || '当前未登录，请登录后重试')
      Auth.removeToken()
      const route = VueUtil.route()
      const nextRedirect = Auth.getNextRedirect(route.path, route.query)
      VueUtil.router().push(nextRedirect)
      return Promise.reject(msg || '请先完成登录')
    } else if (code == 403) {
      Message.noticeError(msg || '403，您没有权限查看资源')
      return Promise.reject(msg || '您没有权限查看资源')
    } else if (code == 404) {
      Message.noticeError(msg || '404，找不到目标资源')
      return Promise.reject(msg || '找不到目标资源')
    } else {
      Message.noticeError(msg || `服务器响应失败：${code} | ${msg}`)
      return Promise.reject(msg || '服务器响应失败')
    }
  }
  return res.data
}, error => {
  SecureTransferFilter.responseFilter(error.response)
  AxiosExceptionHandler.handleResponseInterceptorError(error)
  return Promise.reject(error)
})

export default BaseRequest
