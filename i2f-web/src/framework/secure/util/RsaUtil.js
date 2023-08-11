/**
 * RSA工具
 */
import JSEncrypt from '../static/jsencrypt'

const RsaUtil = {
  publicKeyEncrypt (pubKey, text) {
    const chiper = new JSEncrypt()
    chiper.setKey(pubKey)
    return chiper.encrypt(text, false)
  },
  publicKeyDecrypt (pubKey, text) {
    const chiper = new JSEncrypt()
    chiper.setKey(pubKey)
    return chiper.decrypt(text, false)
  },
  privateKeyEncrypt (priKey, text) {
    const chiper = new JSEncrypt()
    chiper.setKey(priKey)
    return chiper.encrypt(text, true)
  },
  privateKeyDecrypt (priKey, text) {
    const chiper = new JSEncrypt()
    chiper.setKey(priKey)
    return chiper.decrypt(text, true)
  }
}

export default RsaUtil
