import Auth from '@/framework/auth'

/**
 * 是否具有某个角色
 * v-has-role="admin"
 */
const HasRoleDirective = {
  name: 'hasRole',
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
      const arr = user.tag.roles
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

export default HasRoleDirective
