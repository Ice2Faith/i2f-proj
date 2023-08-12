/**
 * 核心处理逻辑类
 */
import Base64Obfuscator from '../util/base64-obfuscator'
import SecureConsts from '../consts/secure-consts'
import StringUtils from '../util/string-utils'
import SignatureUtil from '../crypto/SignatureUtil'
import AsymmetricUtil from '../crypto/AsymmetricUtil'
import SymmetricUtil from '../crypto/SymmetricUtil'

const SecureTransfer = {
  // 获取安全请求头，参数：是否启用安全参数，是否编码URL
  getSecureHeader (openSecureParams, openSecureUrl) {
    const ret = {}

    return this.getSecureHeaderInto(ret, openSecureParams, openSecureUrl)
  },
  // 添加安全请求头到headers中，openSecureParams表示是否启用安全参数，openSecureUrl表示是否启用安全URL编码
  getSecureHeaderInto (headers, openSecureParams, openSecureUrl) {
    headers[SecureConsts.SECURE_DATA_HEADER()] = SecureConsts.FLAG_ENABLE()

    if (openSecureParams) {
      headers[SecureConsts.SECURE_PARAMS_HEADER()] = SecureConsts.FLAG_ENABLE()
    }
    if (openSecureUrl) {
      headers[SecureConsts.SECURE_URL_HEADER()] = SecureConsts.FLAG_ENABLE()
    }

    return headers
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
  // 存储asym公钥的键名
  ASYM_PUBKEY_NAME () {
    return 'SECURE_PUB'
  },
  // 保存asym公钥
  saveAsymPubKey (pubKey) {
    return this.setStorageItem(this.ASYM_PUBKEY_NAME(), pubKey)
  },
  // 加载asym公钥
  loadAsymPubKey () {
    const pubKey = this.getStorageItem(this.ASYM_PUBKEY_NAME())
    return Base64Obfuscator.decode(pubKey)
  },
  ASYM_PRIKEY_NAME () {
    return 'SECURE_PRI'
  },
  // 保存asym公钥
  saveAsymPriKey (priKey) {
    return this.setStorageItem(this.ASYM_PRIKEY_NAME(), priKey)
  },
  // 加载asym公钥
  loadAsymPriKey () {
    const priKey = this.getStorageItem(this.ASYM_PRIKEY_NAME())
    return Base64Obfuscator.decode(priKey)
  },
  existsAsymPriKey () {
    const priKey = this.getStorageItem(this.ASYM_PRIKEY_NAME())
    return !StringUtils.isEmpty(priKey)
  },
  // 随机生成symm秘钥
  symmKeyGen (size) {
    return SymmetricUtil.genKey(size)
  },
  // 随机生成16位symm秘钥
  symmKeyGen16 () {
    return this.symmKeyGen(16)
  },
  // symm加密给定对象
  encrypt (obj, symmKey) {
    return SymmetricUtil.encryptObj(obj, symmKey)
  },
  // symm解密给定串为对象
  decrypt (bs64, symmKey) {
    return SymmetricUtil.decryptObj(bs64, symmKey)
  },
  // 获取asym公钥签名
  getAsymSign () {
    const b464 = this.loadAsymPubKey()
    const asymSign = SignatureUtil.sign(b464)
    return asymSign
  },
  // 获取asym公钥签名
  getAsymPriSign () {
    const b464 = this.loadAsymPriKey()
    const asymSign = SignatureUtil.sign(b464)
    return asymSign
  },
  // 获取安全请求头的值
  getRequestSecureHeader (symmKey) {
    if (StringUtils.isEmpty(symmKey)) {
      return 'null'
    }
    const pubKey = this.loadAsymPubKey()
    let symmKeyTransfer = AsymmetricUtil.publicKeyEncrypt(pubKey, symmKey)
    symmKeyTransfer = Base64Obfuscator.encode(symmKeyTransfer, true)
    return symmKeyTransfer
  },
  // 获取安全请求头的值
  getRequestDigitalHeader (symmKey) {
    if (StringUtils.isEmpty(symmKey)) {
      return 'null'
    }
    const priKey = this.loadAsymPriKey()
    let symmKeyTransfer = AsymmetricUtil.privateKeyEncrypt(priKey, symmKey)
    symmKeyTransfer = Base64Obfuscator.encode(symmKeyTransfer, true)
    return symmKeyTransfer
  },
  // 获取安全响应头中的值
  getResponseSecureHeader (symmKeyTransfer) {
    if (StringUtils.isEmpty(symmKeyTransfer)) {
      return null
    }
    const priKey = this.loadAsymPriKey()
    symmKeyTransfer = symmKeyTransfer.trim()
    // 解除模糊之后使用asym进行解密得到symm秘钥
    let symmKey = Base64Obfuscator.decode(symmKeyTransfer)
    symmKey = AsymmetricUtil.privateKeyDecrypt(priKey, symmKey)
    return symmKey
  },
  // 获取安全响应头中的值
  getResponseDigitalHeader (symmKeyTransfer) {
    if (StringUtils.isEmpty(symmKeyTransfer)) {
      return null
    }
    const pubKey = this.loadAsymPubKey()
    symmKeyTransfer = symmKeyTransfer.trim()
    // 解除模糊之后使用asym进行解密得到symm秘钥
    let symmKey = Base64Obfuscator.decode(symmKeyTransfer)
    symmKey = AsymmetricUtil.publicKeyDecrypt(pubKey, symmKey)
    return symmKey
  }

}

export default SecureTransfer
