import Router from "@/framework/router"

const VueUtil = {
  route() {
    return Router.currentRoute.value
  },
  router() {
    return Router
  }
}

export default VueUtil
