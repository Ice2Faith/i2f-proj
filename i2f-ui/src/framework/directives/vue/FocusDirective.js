/**
 * 元素聚集焦点
 * v-focus
 */
const FocusDirective = {
  name: 'focus',
  // vue3 写法
  mounted(el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  // vue2写法
  inserted(el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  action(el, binding, vnode) {
    el.focus()
    let input = el.querySelector('input')
    if (input) {
      input.focus()
    }
  }
}

export default FocusDirective
