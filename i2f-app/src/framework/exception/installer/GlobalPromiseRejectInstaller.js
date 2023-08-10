import GlobalExceptionHandler from "@/framework/exception/handler/GlobalExceptionHandler";
import Exception from "@/framework/exception/Exception";

/**
 * 全局promise发生未捕获的reject的异常安裝器
 * 需要参数为window实例，即全局window对象
 */
const GlobalPromiseRejectInstaller = {
  install(windowInstance) {
    // 处理全局的promise.reject异常
    windowInstance.addEventListener('unhandledrejection', (event) => {
      let msg = event.reason
      if (msg.message != null && msg.message != undefined) {
        msg = msg.message
      }
      let code = Exception.CODE_ERROR()
      let ex = {}
      try {
        ex = JSON.parse(msg)
      } catch (e) {
      }
      msg = ex.msg || msg
      code = ex.code || code
      GlobalExceptionHandler.handle(Exception.newError(code, msg, 'PromiseReject', {
        event: event,
        reason: event.reason
      }))
    })
  }
}

export default GlobalPromiseRejectInstaller
