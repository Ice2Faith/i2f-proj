// 定义请求的统一封装
import axios from 'axios'
import Config from '@/framework/config'
import Auth from '@/framework/auth'
import AxiosExceptionHandler from '@/framework/request/exception'

// 定义默认的参数
axios.defaults.headers['Content-Type'] = Config.REQUEST_DEFAULT_CONTENT_TYPE
axios.defaults.timeout = Config.REQUEST_DEFAULT_TIMEOUT

// 定义默认请求
const DownloadRequest = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  timeout: Config.REQUEST_DEFAULT_TIMEOUT,
  responseType: 'blob'
})

// 定义请求拦截
DownloadRequest.interceptors.request.use(config => {
  // 如果已登录，则携带登录头
  if (!config.headers[Config.HEADER_TOKEN_NAME]) {
    if (Auth.isAuth()) {
      config.headers[Config.HEADER_TOKEN_NAME] = Auth.getToken()
    }
  }

  // METHOD 转换为大写
  config.method = config.method.toUpperCase()

  // 如果是GET请求，却使用了data参数，则替换为params参数
  if (config.method == 'GET' && config.data) {
    config.params = Object.assign({}, config.params, config.data)
    config.data = undefined
  }

  return config
})

// 定义响应拦截
DownloadRequest.interceptors.response.use(res => {
  if (res.data) {
    const blob = new Blob([res.data])
    const downloadElement = document.createElement('a')
    const href = window.URL.createObjectURL(blob)
    let filename = ''
    let contentDisposition = '' // 从response的headers中获取filename, 后端response.setHeader("Content-disposition", "attachment; filename=xxxx.docx") 设置的文件名;
    Object.keys(res.headers).forEach((key, index) => {
      if (key.toLowerCase() == 'content-disposition') {
        contentDisposition = res.headers[key]
      }
    })
    if (contentDisposition && contentDisposition != '') {
      const patten = new RegExp('filename=([^;]+\\.[^\\.;]+);*')
      const result = patten.exec(contentDisposition)
      filename = decodeURI(escape(result[1])) // 处理文件名,解决中文乱码问题
    } else {
      let url = res.config.url + ''
      let idx = url.lastIndexOf('?')
      if (idx >= 0) {
        url = url.substring(0, idx)
      }
      idx = url.lastIndexOf('/')
      if (idx >= 0) {
        url = url.substring(idx + 1)
      }
      if (url != '') {
        filename = decodeURIComponent(url)
      }
    }
    if (!filename || filename == '') {
      filename = 'download.data'
    }
    downloadElement.style.display = 'none'
    downloadElement.href = href
    downloadElement.download = filename // 下载后文件名
    document.body.appendChild(downloadElement)
    downloadElement.click() // 点击下载
    document.body.removeChild(downloadElement) // 下载完成移除元素
    window.URL.revokeObjectURL(href) // 释放掉blob对象
  }
  return res.data
}, error => {
  AxiosExceptionHandler.handleResponseInterceptorError(error)
  return Promise.reject(error)
})

export default DownloadRequest
