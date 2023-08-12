/**
 * 定义异常
 */
const Exception = {
  TYPE: () => 'Exception',

  CODE_ERROR: () => 500,
  CODE_WARN: () => 400,
  CODE_INFO: () => 300,
  CODE_SUCCESS: () => 200,

  newError (code, msg, type = null, data = null) {
    return new Error(this.toErrorMsg(this.newObj(code, msg, type, data)))
  },
  newObj (code, msg, type = null, data = null) {
    return {
      _type: Exception.concatType(type),
      code: code,
      msg: msg,
      data: data
    }
  },
  concatType (subType) {
    if (subType) {
      return subType + this.TYPE()
    }
    return this.TYPE()
  },
  toErrorMsg (obj) {
    return JSON.stringify(obj, null, '    ')
  },
  ofErrorMsg (error) {
    try {
      return JSON.parse(error.message)
    } catch (e) {
    }
    return error.message
  }
}

export default Exception
