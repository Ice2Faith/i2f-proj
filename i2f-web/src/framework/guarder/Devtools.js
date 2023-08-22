/*!
devtools-detect
https://github.com/sindresorhus/devtools-detect
By Sindre Sorhus
MIT License
*/

const devtools = {
  isOpen: false,
  orientation: undefined
}

const threshold = 170

const emitDevtoolsChangeEvent = (isOpen, orientation) => {
  globalThis.dispatchEvent(new globalThis.CustomEvent('devtoolschange', {
    detail: {
      isOpen,
      orientation
    }
  }))
}

const emitDevtoolsOpenEvent = (isOpen, orientation) => {
  globalThis.dispatchEvent(new globalThis.CustomEvent('devtoolsopen', {
    detail: {
      isOpen,
      orientation
    }
  }))
}

const emitDevtoolsCloseEvent = (isOpen, orientation) => {
  globalThis.dispatchEvent(new globalThis.CustomEvent('devtoolsclose', {
    detail: {
      isOpen,
      orientation
    }
  }))
}

const devtoolsCheckFunc = ({ emitEvents = true } = {}) => {
  const widthThreshold = globalThis.outerWidth - globalThis.innerWidth > threshold
  const heightThreshold = globalThis.outerHeight - globalThis.innerHeight > threshold
  const orientation = widthThreshold ? 'vertical' : 'horizontal'

  if (
    !(heightThreshold && widthThreshold) &&
    ((globalThis.Firebug && globalThis.Firebug.chrome && globalThis.Firebug.chrome.isInitialized) || widthThreshold || heightThreshold)
  ) {
    if ((!devtools.isOpen || devtools.orientation !== orientation) && emitEvents) {
      emitDevtoolsChangeEvent(true, orientation)
    }

    devtools.isOpen = true
    devtools.orientation = orientation
    if (emitEvents) {
      emitDevtoolsOpenEvent(devtools.isOpen, devtools.orientation)
    }
  } else {
    if (devtools.isOpen && emitEvents) {
      emitDevtoolsChangeEvent(false, undefined)
    }

    devtools.isOpen = false
    devtools.orientation = undefined
    if (emitEvents) {
      emitDevtoolsCloseEvent(devtools.isOpen, devtools.orientation)
    }
  }
}

devtoolsCheckFunc()

const InstallDevtoolsCheck = function (interval = 4000) {
  setInterval(function () {
    devtoolsCheckFunc()
  }, interval)
  devtoolsCheckFunc()
}

const Devtools = {
  devtools: devtools,
  install: InstallDevtoolsCheck,
  check: devtoolsCheckFunc
}

export default Devtools
