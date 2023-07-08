import {message} from 'ant-design-vue';

const Message = {
  noticeError(str) {
    console.log("[ERROR] ", str)
    if (typeof str !== "string") {
      str = JSON.stringify(str)
    }
    message.error(str)
  },
  noticeWarning(str) {
    console.log("[WARN] ", str)
    if (typeof str !== "string") {
      str = JSON.stringify(str)
    }
    message.warning(str)
  },
  noticeInfo(str) {
    console.log("[INFO] ", str)
    if (typeof str !== "string") {
      str = JSON.stringify(str)
    }
    message.info(str)
  }
}
export default Message
