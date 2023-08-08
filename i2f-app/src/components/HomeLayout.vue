<template>
  <div>
    <div class="main-content">
      <router-view/>
    </div>
    <van-tabbar v-model="activeTab" route>
      <van-tabbar-item icon="home-o" to="/home">主页</van-tabbar-item>
      <van-tabbar-item icon="apps-o" to="/menu">功能</van-tabbar-item>
      <van-tabbar-item v-if="needShowWorkspace()" :to="$router.currentRoute.value.fullPath" icon="add-o">工作台
      </van-tabbar-item>
      <van-tabbar-item :badge="noticeCount" icon="comment-o" to="/msg">消息</van-tabbar-item>
      <van-tabbar-item icon="user-o" to="/mine">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>
<script>
import Auth from "../framework/auth";

export default {
  name: 'HomeLayout',
  components: {},
  data() {
    return {
      user: {},
      noticeCount: 0,
      activeTab: 0,
      menus: [],

    }
  },
  created() {
    this.user = Auth.getUser();
  },
  methods: {
    needShowWorkspace() {
      let path = this.$router.currentRoute.value.fullPath
      if (['/home', '/menu', '/msg', '/mine'].indexOf(path) >= 0) {
        return false
      }
      return true
    },
    showNotice() {
      this.$message.noticeInfo('功能还未开发')
    },
    editUserInfo() {
      this.$message.noticeInfo('功能还未开发')
    },
    changePassword() {
      this.$message.noticeInfo('功能还未开发')
    },
    logout() {
      this.$axios({
        url: '/logout',
        method: 'get'
      }).then(data => {
        this.$router.replace({path: '/'})
        this.$message.noticeInfo(data.msg)
      })
    },
  }
}
</script>
<style scoped>
.main-content {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: var(--van-tabbar-height);
  overflow: auto;
}
</style>
