<template>
  <div>
    <div class="login-background">
      <div class="login-box login-mask">
      </div>
      <div class="login-box login-form">
        <div class="login-title">
          念
        </div>
        <van-form
          ref="form"
          @keyup.enter="doLogin"
        >
          <van-field
            v-model="form.username"
            :rules="rules.username"
            class="login-form-item"
            label="用户名"
            name="用户名"
            placeholder="用户名"
          />
          <van-field
            v-model="form.password"
            :rules="rules.password"
            class="login-form-item"
            label="密码"
            name="密码"
            placeholder="密码"
            type="password"
          />


          <div style="margin: 16px;">
            <van-button :loading="controls.loading" block round type="primary" @click="doLogin()">
              登录
            </van-button>
          </div>

          <van-row>
            <van-col :span="12">
              <van-row justify="start">
                <van-button class="link-button" hairline plain style="color: lightseagreen" @click="goResetPass">忘记密码？
                </van-button>
              </van-row>
            </van-col>
            <van-col :span="12">
              <van-row justify="end">
                <van-button class="link-button" hairline plain style="color: orangered" @click="goRegistry">去注册
                </van-button>
              </van-row>
            </van-col>
          </van-row>
        </van-form>
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
        username: [{required: true, message: '请填写用户名'}],
        password: [{required: true, message: '请填写密码'}]
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
      this.$axios({
        url: '/login',
        method: 'post',
        data: this.form
      }).then(({data}) => {
        let token = data
        Auth.setToken(token)
        this.$axios({
          url: 'sys/user/info',
          method: 'post'
        }).then(({data}) => {
          Auth.setUser(data)
          let user = Auth.getUser()
          Auth.setRoutes(user.tag.urls)
          Auth.resolveRedirect('/home')
        })
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
    goRegistry() {
      this.$router.push({path: '/registry'})
    },
    goResetPass() {
      this.$router.push({path: '/pass-reset'})
    }
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
  height: 100%;
  position: fixed;
}

.login-box {
  width: 80%;
  height: 240px;
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  padding: 36px;
  border-radius: 12px;
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
  font-size: 22px;
  color: dodgerblue;
  font-weight: bold;
}

.login-form-item {
  background: transparent !important;
  border: none !important;
  border-bottom: solid 1px #aaa !important;
}

.link-button {
  background: transparent !important;
  border: solid 1px transparent !important;
}
</style>
