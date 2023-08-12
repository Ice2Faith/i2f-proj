const Message = {
  noticeError (str) {
    console.log('[ERROR] ', str)
    if (typeof str !== 'string') {
      str = JSON.stringify(str)
    }
  },
  noticeWarning (str) {
    console.log('[WARN] ', str)
    if (typeof str !== 'string') {
      str = JSON.stringify(str)
    }
  },
  noticeInfo (str) {
    console.log('[INFO] ', str)
    if (typeof str !== 'string') {
      str = JSON.stringify(str)
    }
  },
  noticeSuccess (str) {
    console.log('[SUCCESS] ', str)
    if (typeof str !== 'string') {
      str = JSON.stringify(str)
    }
  }
}
export default Message
