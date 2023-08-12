import GlobalExceptionHandler from '@/framework/exception/handler/GlobalExceptionHandler'
import Exception from '@/framework/exception/Exception'

/**
 * 全局vue发生warn的异常安裝器
 * 需要参数为vue实例，即
 * vue2: let vueInstance=new Vue({...})
 * vue3: let vueInstance=createApp({...})
 * 此安装器需要在mount之前进行，否则不会生效
 * 以vue3为例，vue2是一样的
 * let vueInstance=createApp({...})
 * GlobalVueErrorInstaller.install(vueInstance)
 * vueInstance.mount('#app')
 */
const GlobalVueWarnInstaller = {
  install (vueInstance) {
    vueInstance.config.warnHandler = (message, instance, trace) => {
      try {
        console.log('[VueWarn]', message)
        console.log('[VueWarn]', instance)
        console.log('[VueWarn]', trace)
        let msg = message
        let code = Exception.CODE_WARN()
        let ex = {}
        try {
          ex = JSON.parse(msg)
        } catch (e) {
        }
        msg = ex.msg || msg
        code = ex.code || code

        GlobalExceptionHandler.handle(Exception.newError(code, msg, 'VueWarn', {
          message: message,
          trace: trace
        }))
      } catch (e) {
      }
    }
  }
}

export default GlobalVueWarnInstaller
