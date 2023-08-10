import {showNotify} from 'vant';

const Message = {
  noticeError(str) {
    console.log("[ERROR] ", str)
    if (typeof str !== "string") {
      str = JSON.stringify(str)
    }
    showNotify({type: 'danger', message: str});
  },
  noticeWarning(str) {
    console.log("[WARN] ", str)
    if (typeof str !== "string") {
      str = JSON.stringify(str)
    }
    showNotify({type: 'warning', message: str});
  },
  noticeInfo(str) {
    console.log("[INFO] ", str)
    if (typeof str !== "string") {
      str = JSON.stringify(str)
    }
    showNotify({type: 'primary', message: str});
  },
  noticeSuccess(str) {
    console.log("[SUCCESS] ", str)
    if (typeof str !== "string") {
      str = JSON.stringify(str)
    }
    showNotify({type: 'success', message: str});
  }
}
export default Message
