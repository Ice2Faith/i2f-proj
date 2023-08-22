import ConsoleProtection from './ConsoleProtection'
import DebuggerProtection from './DebuggerProtection'
import DevtoolsProtection from './DevtoolsProtection'
import F12Protection from './F12Protection'

if (process.env.NODE_ENV == 'prod') {
  ConsoleProtection.install()
  DebuggerProtection.install()
  DevtoolsProtection.install()
  F12Protection.install()
}
