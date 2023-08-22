/**
 * 禁用开发者模式
 */
import Devtools from './Devtools'

const InstallDevtoolsProtection = function (interval = 4000) {
  Devtools.install(interval)
  window.addEventListener('devtoolsopen', function (event) {
    if (event.detail.isOpen) {
      alert('请关闭调试模式')
      window.location.href = 'about:blank'
    }
  })
}

const DevtoolsProtection = {
  install: InstallDevtoolsProtection,
  devtools: Devtools
}

export default DevtoolsProtection
