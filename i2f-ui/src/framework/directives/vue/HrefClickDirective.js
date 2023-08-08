/**
 * 点击跳转路由
 * v-href-click="'http://xxxx'"
 */
const HrefClickDirective = {
  name: 'hrefClick',
  // vue3 写法
  mounted(el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  // vue2写法
  inserted(el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  action(el, binding, vnode) {
    el.addEventListener('click', () => {
      window.location.href = binding.value
    })
  }
}

export default HrefClickDirective
