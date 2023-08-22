/**
 * 控制台防止调试
 * 无限创建debugger影响调试工作
 */
const debuggerProtectionFunc = function () {
  function doDebuggerProtection (a) {
    // javascript-obfuscator:disable
    if (('' + a / a).length !== 1 || a % 20 === 0) {
      // javascript-obfuscator:disable
      (function () {
      }.constructor('debugger')())
    } else {
      // javascript-obfuscator:disable
      (function () {
      }.constructor('debugger')())
    }
    doDebuggerProtection(++a)
  }

  try {
    doDebuggerProtection(0)
  } catch (err) {
  }
}

const InstallDebuggerProtection = function (interval = 4000) {
  setInterval(function () {
    debuggerProtectionFunc()
  }, interval)
  debuggerProtectionFunc()
}

const DebuggerProtection = {
  install: InstallDebuggerProtection,
  protection: debuggerProtectionFunc
}

export default DebuggerProtection
