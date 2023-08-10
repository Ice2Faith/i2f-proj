import GlobalThrowErrorInstaller from "@/framework/exception/installer/GlobalThrowErrorInstaller";
import GlobalPromiseRejectInstaller from "@/framework/exception/installer/GlobalPromiseRejectInstaller";
import GlobalVueErrorInstaller from "@/framework/exception/installer/GlobalVueErrorInstaller";
import GlobalVueWarnInstaller from "@/framework/exception/installer/GlobalVueWarnInstaller";

const GlobalExceptionInstaller = {
  installJs() {
    GlobalThrowErrorInstaller.install(window)
    GlobalPromiseRejectInstaller.install(window)
  },
  installVue(vueInstance) {
    GlobalVueErrorInstaller.install(vueInstance)
    GlobalVueWarnInstaller.install(vueInstance)
  },
}

export default GlobalExceptionInstaller
