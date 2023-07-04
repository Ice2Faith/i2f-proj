import CryptoJS from 'crypto-js'
import Base64Util from "@/framework/utils/base64-util"

const AesUtil={
    genKey(key){
        if(!key || key==''){
            return 'A1B2C3D4E5F67870'
        }
        let idx=0
        let adlen=0
        let klen=key.length
        while((klen+adlen)%16!=0){
            idx=((idx+3)*7)%klen
            key=key+key[idx]
            adlen++
        }
        return key
    },
    encrypt(data,key){
        let keys  = CryptoJS.enc.Utf8.parse(key) //Latin1 w8m31+Yy/Nw6thPsMpO5fg==
        let srcs = CryptoJS.enc.Utf8.parse(data)
        let encrypted = CryptoJS.AES.encrypt(srcs, keys, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Iso10126})
        return encrypted.toString()
    },
    decrypt(data,key){
        let keys  = CryptoJS.enc.Utf8.parse(key) //Latin1 w8m31+Yy/Nw6thPsMpO5fg==
        let decrypt = CryptoJS.AES.decrypt(data, keys, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Iso10126})
        return CryptoJS.enc.Utf8.stringify(decrypt).toString()
    },
    encryptObj (data,key){
        let srcs=Base64Util.encodeObj(data)
        return this.encrypt(srcs,key)
    },
    decryptObj(data,key){
        let srcs=this.decrypt(data,key)
        return Base64Util.decodeObj(srcs)
    }
}

export default AesUtil
