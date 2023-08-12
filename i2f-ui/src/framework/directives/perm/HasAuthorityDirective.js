import Auth from '@/framework/auth'

/**
 * 是否具有某个权限
 * v-has-authority="admin"
 */
const HasAuthorityDirective = {
  name: 'hasAuthority',
  // vue3 写法
  mounted (el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  // vue2写法
  inserted (el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  action (el, binding, vnode) {
    if (binding.value && binding.value != '') {
      const user = Auth.getUser()
      if (!user) {
        return false
      }
      if (!user.authorities) {
        return false
      }
      const arr = user.authorities.map(item => item.authority)
      let ok = false
      if (arr && arr.length > 0) {
        ok = arr.indexOf(binding.value) >= 0
      }
      if (!ok) {
        el.style.display = 'none'
      }
    }
  }
}

export default HasAuthorityDirective
