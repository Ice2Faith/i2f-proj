import Auth from '@/framework/auth'

/**
 * 是否具有某个部门
 * v-has-dept-role="{deptKey: 'admin',roleKey: 'admin'}"
 */
const HasDeptRoleDirective = {
  name: 'hasDeptRole',
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
      binding.value.roleKey &&
      binding.value.deptKey != '' &&
      binding.value.roleKey != '') {
      const user = Auth.getUser()
      if (!user) {
        return false
      }
      const map = user.tag.deptRoles
      if (!map) {
        return false
      }
      if (!map[binding.value.deptKey]) {
        return false
      }
      const arr = map[binding.value.deptKey]
      let ok = false
      if (arr && arr.length > 0) {
        ok = arr.indexOf(binding.value.roleKey) >= 0
      }
      if (!ok) {
        el.style.display = 'none'
      }
    }
  }
}

export default HasDeptRoleDirective
