const Message={
    noticeError(str){
        console.log("[ERROR] ",str)
        alert("[ERROR] "+str)
    },
    noticeWarning(str){
        console.log("[WARN] ",str)
    },
    noticeInfo(str){
        console.log("[INFO] ",str)
    }
}
export default Message