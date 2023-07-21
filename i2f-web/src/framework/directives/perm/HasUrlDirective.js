import Auth from "@/framework/auth";

/**
 * 元素聚集焦点
 * v-has-url="admin"
 */
const HasUrlDirective = {
  name: 'hasUrl',
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
      let arr = user.tag.urls
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

export default HasUrlDirective
