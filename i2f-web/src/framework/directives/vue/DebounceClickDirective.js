/**
 * 防抖点击
 * 避免短时间内重复点击触发
 * v-debounce-click="1000"
 * 可以跟防抖的时长，单位毫秒
 */
const DebounceClickDirective = {
  name: 'debounceClick',
  // vue3 写法
  mounted (el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  // vue2写法
  inserted (el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  action (el, binding, vnode) {
    el.addEventListener('click', () => {
      if (!el.disabled) {
        el.disabled = true
        setTimeout(() => {
          el.disabled = false
        }, (binding.value || 1000))
      }
    })
  }
}

export default DebounceClickDirective
