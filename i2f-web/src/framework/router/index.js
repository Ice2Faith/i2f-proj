// 路由配置
// 从routes中加载路由映射构建路由对象返回
import {createRouter, createWebHashHistory, createWebHistory} from 'vue-router'
import Routes from '@/framework/routes'
import Auth from "@/framework/auth"
import Config from "@/framework/config"
import Message from "@/framework/message"

// 创建路由
const Router = createRouter({
  history: Config.ROUTER_HASH_MODE ? createWebHashHistory(Config.ROUTER_BASE_PATH) : createWebHistory(Config.ROUTER_BASE_PATH),
  routes: Routes
})

// 配置路由守卫
Router.beforeEach((to, from, next) => {
  if (to.path == Config.LOGIN_ROUTE) {
    // 登录页直接放行
    next()
  }
  if (to.meta.permission == false) {
    // 有明确指定为false，则不需要判定权限
    next()
    return
  }
  // 未登录
  if (!Auth.isAuth()) {
    Message.noticeError("未登录，请先登录")
    next(Auth.getNextRedirect(to.path, to.query))
    return
  }
  let routes = Auth.getRoutes()
  // 没有资源权限
  if (routes.indexOf(to.path) < 0) {
    Message.noticeError("没有资源访问权限")
    // next(Auth.getNextRedirect(to.path,to.query))
    return
  }
  next()
})
export default Router
