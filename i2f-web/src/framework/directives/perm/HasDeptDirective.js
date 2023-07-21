import Auth from "@/framework/auth";

/**
 * 是否具有某个部门
 * v-has-dept="admin"
 */
const HasDeptDirective = {
  name: 'hasDept',
  // vue3 写法
  mounted(el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  // vue2写法
  inserted(el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  action(el, binding, vnode) {
    if (binding.value && binding.value != '') {
      let user = Auth.getUser()
      if (!user) {
        return false
      }
      let arr = user.tag.depts
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

export default HasDeptDirective
