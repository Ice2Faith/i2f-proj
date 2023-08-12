import Router from '@/framework/router'

/**
 * 点击跳转路由
 * v-route-click="{path: 'home',query:{id: 1}}"
 */
const RouteClickDirective = {
  name: 'routeClick',
  // vue3 写法
  mounted (el, binding, vnode) {
    this.action(el, binding, vnode)
  },
  // vue2写法
  // inserted(el, binding, vnode) {
  //   this.action(el, binding, vnode)
  // },
  action (el, binding, vnode) {
    el.addEventListener('click', () => {
      Router.push(binding.value)
    })
  }
}

export default RouteClickDirective
