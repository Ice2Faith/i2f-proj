import Auth from '@/framework/auth'

/**
 * 是否具有某个部门
 * v-has-dept-perm="{deptKey: 'admin',permKey: 'admin'}"
 */
const HasDeptPermDirective = {
  name: 'hasDeptPerm',
  // vue3 写法
  mounted (el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  // vue2写法
  inserted (el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  action (el, binding, vnode) {
    if (binding.value &&
      binding.value.deptKey &&
      binding.value.permKey &&
      binding.value.deptKey != '' &&
      binding.value.permKey != '') {
      const user = Auth.getUser()
      if (!user) {
        return false
      }
      const map = user.tag.deptPerms
      if (!map) {
        return false
      }
      if (!map[binding.value.deptKey]) {
        return false
      }
      const arr = map[binding.value.deptKey]
      let ok = false
      if (arr && arr.length > 0) {
        ok = arr.indexOf(binding.value.permKey) >= 0
      }
      if (!ok) {
        el.style.display = 'none'
      }
    }
  }
}

export default HasDeptPermDirective
