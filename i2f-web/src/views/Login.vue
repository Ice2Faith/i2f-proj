<template>
  <div>
    <div class="login-background">
      <div class="login-box login-mask">
      </div>
      <div class="login-box login-form">
        <div class="login-title">
          念
        </div>
        <a-form
          :label-col="{ span: 6 }"
          :model="form"
          :wrapper-col="{ span: 18 }"
          autocomplete
          name="basic"
          @keyup.enter="doLogin"
        >
          <a-form-item
            :rules="rules.username"
            label="用户名"
            name="username"
          >
            <a-input v-model:value="form.username"/>
          </a-form-item>

          <a-form-item
            :rules="rules.password"
            label="密码"
            name="password"
          >
            <a-input-password v-model:value="form.password"/>
          </a-form-item>


          <a-form-item :wrapper-col="{ offset: 0, span: 24 }">
            <a-row justify="center" type="flex">
              <a-spin :spinning="controls.loading">
                <a-button size="large" type="primary" @click="doLogin">登录</a-button>
              </a-spin>
            </a-row>
          </a-form-item>
        </a-form>
      </div>
      <div class="login-footer">
        念 @Copyright 2023 created by Ice2Faith
      </div>
    </div>
  </div>
</template>

<script>
import Auth from "@/framework/auth";

export default {
  name: 'Login',
  props: {},
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      controls: {
        loading: false,
      },
      rules: {
        username: [{required: true, message: '请输入用户名!'}],
        password: [{required: true, message: '请输入密码!'}],
      }
    }
  },
  mounted() {
    this.logout()
    Auth.removeToken()
    Auth.setRoutes([])
  },
  methods: {
    doLogin() {
      this.controls.loading = true
      this.$axios({
        url: '/login',
        method: 'post',
        data: this.form
      }).then(data => {
        let token = data.data
        Auth.setToken(token)
        this.$axios({
          url: 'sys/user/info',
          method: 'post'
        }).then(data => {
          Auth.setUser(data)
          let user = Auth.getUser()
          Auth.setRoutes(user.tag.urls)
          Auth.resolveRedirect('/home')
        })
      }).finally(() => {
        this.controls.loading = false
      })

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
.login-background {
  background-color: #444;
  background-image: url("~@/assets/login-bg.jpg");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  width: 100%;
  height: 100vh;
}

.login-box {
  min-width: 420px;
  width: 25%;
  height: 30vh;
  position: fixed;
  right: 15%;
  top: 25vh;
  padding: 8vh;
  border-radius: 2vh;
}

.login-mask {
  background-color: rgba(255, 255, 255, 0.8);
  filter: blur(8px);
  box-shadow: 8px 8px 0 #aaa;
}

.login-form {

}

.login-footer {
  position: fixed;
  bottom: 5vh;
  text-align: center;
  left: 0;
  right: 0;
  font-size: 18px;
  color: #aaa;
}

.login-title {
  text-align: center;
  font-size: 18px;
  color: dodgerblue;
  font-weight: bold;
}
</style>
