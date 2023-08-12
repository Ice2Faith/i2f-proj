const plugins = []
// 判断环境
if (process.env.NODE_ENV === 'prod') {
  plugins.push('transform-remove-console')
  plugins.push('transform-remove-debugger')
}

module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],
  plugins: [...plugins]
}
