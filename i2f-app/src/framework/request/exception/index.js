import Message from "@/framework/message";
import Auth from "@/framework/auth";
import VueUtil from "@/framework/utils/vue-util";

const AxiosExceptionHandler = {
  handleResponseInterceptorError(error) {
    if (error.code && error.message && error.request) {
      const {code, config, message, name, request, response} = error
      if (response) {
        const {data, status, statusText} = response
        if (status == 401) {
          Message.noticeError("当前未登录，请登录后重试")
          Auth.removeToken()
          let route = VueUtil.route()
          let nextRedirect = Auth.getNextRedirect(route.path, route.query)
          VueUtil.router().push(nextRedirect)
        } else if (status == 403) {
          Message.noticeError("403，您没有权限查看资源")
        } else if (status == 404) {
          Message.noticeError("404，找不到目标资源")
        } else {
          Message.noticeError(`响应失败：${status} | ${statusText} | ${code} | ${message}`)
        }
      } else if (name == 'AxiosError') {
        Message.noticeError(`请求发送异常：${code} | ${message}`)
      } else {
        Message.noticeError(`请求失败：${code} | ${message}`)
      }
    } else {
      Message.noticeError(`其他请求异常：${error}`)
    }
  }
}

export default AxiosExceptionHandler