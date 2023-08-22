/**
 * 重写console输出函数为空函数
 * 避免控制台输出
 */
const consoleProtectionFunc = function () {
  function overrideFunc (name) {
    if (!console['_' + name]) {
      console['_' + name] = console[name]
    }
    console[name] = function (...any) {}
  }
  overrideFunc('log')
  overrideFunc('debug')
  overrideFunc('dir')
  overrideFunc('error')
  overrideFunc('info')
  overrideFunc('trace')
  overrideFunc('warn')
  console.clear()
}

const InstallConsoleProtection = function (interval = 4000) {
  setInterval(function () {
    consoleProtectionFunc()
  }, interval)
  consoleProtectionFunc()
}

const ConsoleProtection = {
  install: InstallConsoleProtection,
  protection: consoleProtectionFunc
}

export default ConsoleProtection
