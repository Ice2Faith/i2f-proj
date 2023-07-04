import { Base64 } from 'js-base64'
import JsonUtil from '@/framework/utils/json-util'

const Base64Util={
    encode(data){
        return Base64.encode(data)
    },
    decode(data){
        return Base64.decode(data)
    },
    encodeObj(data){
        return this.encode(JsonUtil.toJson(data))
    },
    decodeObj(data){
        return JsonUtil.parseJson(this.decode(data))
    }
}

export default Base64Util
