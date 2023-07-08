// 定义登录和权限相关的内容
import Config from "@/framework/config"
import VueUtil from "@/framework/utils/vue-util"
import Base64Util from "@/framework/utils/base64-util"
import SecureTransfer from "@/framework/secure/core/secure-transfer";
import SecureConfig from "@/framework/secure/secure-config";
import SecureProvider from "@/framework/secure/crypto/SecureProvider";
import Base64Obfuscator from "@/framework/secure/util/base64-obfuscator";

const Auth = {
  // 常量，表示存储token的键
  TOKEN_STORE_KEY() {
    return 'TOKEN'
  },
  // 是否已经登录
  isAuth() {
    return this.getToken() && this.getToken() != ''
  },
  // 获取登录令牌
  getToken() {
    return sessionStorage.getItem(this.TOKEN_STORE_KEY())
  },
  // 设置登录令牌
  setToken(token) {
    sessionStorage.setItem(this.TOKEN_STORE_KEY(), token)
  },
  // 移除登录令牌
  removeToken() {
    sessionStorage.removeItem(this.TOKEN_STORE_KEY())
  },
  // 常量，表示存储routes的键
  ROUTES_STORE_KEY() {
    return 'ROUTES'
  },
  // 设置账号所拥有的的路由
  setRoutes(arr) {
    let dt = Base64Util.encodeObj(arr)
    sessionStorage.setItem(this.ROUTES_STORE_KEY(), dt)
  },
  // 获取账号所拥有的路由
  getRoutes() {
    let dt = sessionStorage.getItem(this.ROUTES_STORE_KEY())
    if (!dt || dt == '') {
      return []
    }
    return Base64Util.decodeObj(dt)
  },
  // 常量，表示存储routes的键
  USER_STORE_KEY() {
    return 'USER'
  },
  localEncrypt(obj) {
    let symmKey = SecureTransfer.symmKeyGen(SecureConfig.symmKeySize / 8);
    let enc = SecureTransfer.encrypt(obj, symmKey);
    let encSymmKey = Base64Obfuscator.encode(Base64Util.encode(symmKey), false)
    let text = encSymmKey + ";" + enc
    let sign = SecureProvider.messageDigester.sign(text)
    let dt = sign + "#" + text
    dt = Base64Obfuscator.encode(Base64Util.encode(dt), true)
    return dt
  },
  localDecrypt(dt) {
    if (!dt || dt == '') {
      return null
    }
    dt = Base64Util.decode(Base64Obfuscator.decode(dt))
    let arr = dt.split('#', 2)
    if (arr.length != 2) {
      return null
    }
    let originSign = arr[0]
    let text = arr[1]
    let newSign = SecureProvider.messageDigester.sign(text)
    if (originSign != newSign) {
      return null
    }
    arr = text.split(';', 2)
    if (arr.length != 2) {
      return null
    }
    let encSymmKey = arr[0]
    let enc = arr[1]
    let symmKey = Base64Util.decode(Base64Obfuscator.decode(encSymmKey))
    let ret = SecureTransfer.decrypt(enc, symmKey)
    return ret
  },
  // 设置账号所拥有的的路由
  setUser(arr) {
    let dt = this.localEncrypt(arr)
    sessionStorage.setItem(this.USER_STORE_KEY(), dt)
  },
  // 获取账号所拥有的路由
  getUser() {
    let dt = sessionStorage.getItem(this.USER_STORE_KEY())
    let ret = this.localDecrypt(dt)
    return ret
  },
  goLogin() {
    VueUtil.router().replace({path: Config.LOGIN_ROUTE})
  },
  // 获取因为未登录到登录页登录成功之后的重定向参数
  // path和query就是登录成功之后重定向回来的路由参数
  getNextRedirect(path, query) {
    if (!query) {
      query = {}
    }
    return {
      path: Config.LOGIN_ROUTE,
      query: {
        redirect: path,
        query: encodeURIComponent(JSON.stringify(query))
      }
    }
  },
  // 处理登录页面登录成功之后要跳转的路由
  // 如果存在重定向的情况，按照重定向跳转
  // 如果没有重定向的，则按照入参nextPath金额nextQuery进行跳转
  resolveRedirect(nextPath = null, nextQuery = null) {
    if (!nextQuery) {
      nextQuery = {}
    }
    let route = VueUtil.route()
    let query = route.query
    if (!query) {
      query = {}
    }
    query = {...query}
    if (query.redirect && query.redirect != '') {
      let nextRoute = {
        path: query.redirect,
        query: {}
      }
      if (query.query && query.query != '') {
        nextRoute.query = JSON.parse(decodeURIComponent(query.query))
      }
      delete query['query']
      delete query['redirect']
      nextRoute.query = Object.assign({}, query, nextQuery, nextRoute.query)
      VueUtil.router().replace(nextRoute)
    } else if (nextPath && nextPath != '') {
      VueUtil.router().replace({path: nextPath, query: nextQuery})
    }
  }
}

export default Auth
