<template>
  <div>
      <span>用户名：</span><input type="text" v-model="form.username" placeholder="请输入用户名"/><br/>
      <span>密码：</span><input type="password" v-model="form.password" placeholder="请输入密码"/><br/>
      <button @click="doLogin()">登录</button>
  </div>
</template>

<script>
import Auth from "@/framework/auth";

export default {
  name: 'Login',
  props: {

  },
  data(){
    return {
      form:{
        username: '',
        password: ''
      }
    }
  },
  mounted() {
    Auth.removeToken()
    Auth.setRoutes([])
  },
  methods:{
    doLogin(){
      this.$axios({
        url: '/login',
        method: 'post',
        data: this.form
      }).then(data=>{
        let token=data.data
        Auth.setToken(token)
        this.$axios({
          url: 'sys/user/info',
          method: 'post'
        }).then(data=>{
          Auth.setUser(data)
          let user=Auth.getUser()
          Auth.setRoutes(['/','/hello','/home','/about'])
          Auth.resolveRedirect('/hello')
        })

      })

    }
  }
}
</script>

<style scoped>

</style>
