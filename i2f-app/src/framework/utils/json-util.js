const JsonUtil = {
  toJson(obj) {
    return JSON.stringify(obj)
  },
  parseJson(str) {
    return JSON.parse(str)
  }
}
export default JsonUtil
