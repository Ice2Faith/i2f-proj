const DirectiveInstaller = {
  install (vue, one) {
    vue.directive(one.name, one)
  },
  installAll (vue, obj) {
    Object.keys(obj).forEach(key => {
      const one = obj[key]
      if (one.name && one.name != '') {
        vue.directive(one.name, one)
      }
    })
  }
}

export default DirectiveInstaller
