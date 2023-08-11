/**
 * 点击一次后移除元素
 * v-once-click
 */
const OnceClickDirective = {
  name: 'onceClick',
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
      if (el.parentNode) {
        el.parentNode.removeChild(el)
      }
    })
  }
}

export default OnceClickDirective
