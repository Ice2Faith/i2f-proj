// 定义登录和权限相关的内容
import Config from '@/framework/config'
import VueUtil from '@/framework/utils/vue-util'
import Base64Util from '@/framework/utils/base64-util'
import SecureTransfer from '@/framework/secure/core/SecureTransfer'
import SecureConfig from '@/framework/secure/SecureConfig'
import SecureProvider from '@/framework/secure/crypto/SecureProvider'
import Base64Obfuscator from '@/framework/secure/util/Base64Obfuscator'

const Auth = {
  // 常量，表示存储token的键
  TOKEN_STORE_KEY () {
    return 'TOKEN'
  },
  // 是否已经登录
  isAuth () {
    return this.getToken() && this.getToken() != ''
  },
  getStorageItem (key) {
    let ret = sessionStorage.getItem(key)
    if (ret == null || ret == undefined) {
      ret = localStorage.getItem(key)
    }
    if (ret != null && ret != undefined) {
      sessionStorage.setItem(key, ret)
    }
    return ret
  },
  setStorageItem (key, value) {
    sessionStorage.setItem(key, value)
    localStorage.setItem(key, value)
  },
  removeStorageItem (key) {
    sessionStorage.removeItem(key)
    localStorage.removeItem(key)
  },
  // 获取登录令牌
  getToken () {
    return this.getStorageItem(this.TOKEN_STORE_KEY())
  },
  // 设置登录令牌
  setToken (token) {
    this.setStorageItem(this.TOKEN_STORE_KEY(), token)
  },
  // 移除登录令牌
  removeToken () {
    this.removeStorageItem(this.TOKEN_STORE_KEY())
  },
  // 常量，表示存储routes的键
  ROUTES_STORE_KEY () {
    return 'ROUTES'
  },
  // 设置账号所拥有的的路由
  setRoutes (arr) {
    const dt = Base64Util.encodeObj(arr)
    this.setStorageItem(this.ROUTES_STORE_KEY(), dt)
  },
  // 获取账号所拥有的路由
  getRoutes () {
    const dt = this.getStorageItem(this.ROUTES_STORE_KEY())
    if (!dt || dt == '') {
      return []
    }
    return Base64Util.decodeObj(dt)
  },
  // 常量，表示存储routes的键
  USER_STORE_KEY () {
    return 'USER'
  },
  localEncrypt (obj) {
    const symmKey = SecureTransfer.symmKeyGen(SecureConfig.symmKeySize / 8)
    const enc = SecureTransfer.encrypt(obj, symmKey)
    const encSymmKey = Base64Obfuscator.encode(Base64Util.encode(symmKey), false)
    const text = encSymmKey + ';' + enc
    const sign = SecureProvider.messageDigester.sign(text)
    let dt = sign + '#' + text
    dt = Base64Obfuscator.encode(Base64Util.encode(dt), true)
    return dt
  },
  localDecrypt (dt) {
    if (!dt || dt == '') {
      return null
    }
    dt = Base64Util.decode(Base64Obfuscator.decode(dt))
    let arr = dt.split('#', 2)
    if (arr.length != 2) {
      return null
    }
    const originSign = arr[0]
    const text = arr[1]
    const newSign = SecureProvider.messageDigester.sign(text)
    if (originSign != newSign) {
      return null
    }
    arr = text.split(';', 2)
    if (arr.length != 2) {
      return null
    }
    const encSymmKey = arr[0]
    const enc = arr[1]
    const symmKey = Base64Util.decode(Base64Obfuscator.decode(encSymmKey))
    const ret = SecureTransfer.decrypt(enc, symmKey)
    return ret
  },
  // 设置账号所拥有的的路由
  setUser (arr) {
    const dt = this.localEncrypt(arr)
    this.setStorageItem(this.USER_STORE_KEY(), dt)
  },
  // 获取账号所拥有的路由
  getUser () {
    const dt = this.getStorageItem(this.USER_STORE_KEY())
    const ret = this.localDecrypt(dt)
    return ret
  },
  goLogin () {
    VueUtil.router().replace({ path: Config.LOGIN_ROUTE })
  },
  // 获取因为未登录到登录页登录成功之后的重定向参数
  // path和query就是登录成功之后重定向回来的路由参数
  getNextRedirect (path, query) {
    if (!query) {
      query = {}
    }
    delete query.redirect
    query.redirect = path
    return {
      path: Config.LOGIN_ROUTE,
      query: query
    }
  },
  // 处理登录页面登录成功之后要跳转的路由
  // 如果存在重定向的情况，按照重定向跳转
  // 如果没有重定向的，则按照入参nextPath金额nextQuery进行跳转
  resolveRedirect (nextPath = null, nextQuery = null) {
    if (!nextQuery) {
      nextQuery = {}
    }
    const route = VueUtil.route()
    let query = route.query
    if (!query) {
      query = {}
    }
    if (query.redirect && query.redirect != '') {
      const redirectPath = query.redirect
      delete query.redirect
      const nextRoute = {
        path: redirectPath,
        query: Object.assign({}, query, nextQuery)
      }
      VueUtil.router().replace(nextRoute)
    } else if (nextPath && nextPath != '') {
      VueUtil.router().replace({ path: nextPath, query: nextQuery })
    }
  }
}

export default Auth
