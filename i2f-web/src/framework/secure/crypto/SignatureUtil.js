import SecureProvider from './SecureProvider'
import B64 from '../util/base64'

const SignatureUtil = {
  sign (text) {
    return SecureProvider.messageDigester.sign(B64.encrypt(text))
  }
}
export default SignatureUtil
