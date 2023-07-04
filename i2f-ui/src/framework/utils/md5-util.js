import CryptoJS from 'crypto-js'

const Md5Util={
    md5(str){
        return CryptoJS.MD5(str)
    }
}

export default Md5Util