/**
 * 避免F12打开开发者选项
 */

const devtoolsProtectionFunc = function () {
  document.onkeydown = document.onkeyup = document.onkeypress = function (event) {
    const e = event || window.event || arguments.callee.caller.arguments[0]

    if (e && e.keyCode == 123) {
      alert('禁止打开调试模式')
      e.returnValue = false
      return false
    }
  }
}

const InstallDevtoolsProtection = function (interval = 4000) {
  setInterval(function () {
    devtoolsProtectionFunc()
  }, interval)
  devtoolsProtectionFunc()
}

const F12Protection = {
  install: InstallDevtoolsProtection,
  protection: devtoolsProtectionFunc
}

export default F12Protection
