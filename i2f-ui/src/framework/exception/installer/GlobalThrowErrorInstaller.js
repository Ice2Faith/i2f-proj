/**
 * 全局throw的异常安裝器
 * 需要参数为window实例，即全局window对象
 */
import GlobalExceptionHandler from '@/framework/exception/handler/GlobalExceptionHandler'
import Exception from '@/framework/exception/Exception'

const GlobalThrowErrorInstaller = {
  install (windowInstance) {
    windowInstance.onerror = function (message, url, lineNo, columnNo, error) {
      try {
        let msg = error.message
        let code = Exception.CODE_ERROR()
        let ex = {}
        try {
          ex = JSON.parse(msg)
        } catch (e) {
        }
        msg = ex.msg || msg
        code = ex.code || code

        GlobalExceptionHandler.handle(Exception.newError(code, msg, 'ThrowError', {
          message: message,
          url: url,
          lineNo: lineNo,
          columnNo: columnNo,
          error: error
        }))
      } catch (e) {

      }
    }
  }
}

export default GlobalThrowErrorInstaller
