import Exception from "@/framework/exception/Exception";
import Message from "@/framework/message";

const GlobalExceptionHandler = {
  handle(error) {
    let ex = Exception.ofErrorMsg(error)
    console.log('GlobalExceptionHandler', ex)
    if (ex.code != null && ex.code != undefined) {
      let code = ex.code
      if (code == Exception.CODE_ERROR()) {
        Message.noticeError(ex.msg)
      } else if (code == Exception.CODE_WARN()) {
        Message.noticeWarning(ex.msg)
      } else if (code == Exception.CODE_INFO()) {
        Message.noticeInfo(ex.msg)
      } else if (code == Exception.CODE_SUCCESS()) {
        Message.noticeSuccess(ex.msg)
      } else {
        Message.noticeError(ex.msg)
      }
    } else {
      Message.noticeError(ex)
    }
  }
}

export default GlobalExceptionHandler
