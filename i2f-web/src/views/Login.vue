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
          :label-col="{ span: 4 }"
          ref="form"
          :model="form"
          :wrapper-col="{ span: 20 }"
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

          <a-form-item
            :rules="rules.result"
            label="验证码"
            name="result"
          >
            <a-input v-model:value="form.result" @click.native="showVerifyCodeDialog"/>
          </a-form-item>

          <a-form-item :wrapper-col="{ offset: 0, span: 24 }">
            <a-row justify="center" type="flex">
              <a-spin :spinning="controls.loading">
                <a-button size="large" type="primary" @click="doLogin">登录</a-button>
              </a-spin>
            </a-row>
          </a-form-item>

          <a-row>
            <a-col :span="12">
              <a-row justify="start" type="flex">
                <a-button style="color: lightseagreen" type="link" @click="goResetPass">忘记密码？</a-button>
              </a-row>
            </a-col>
            <a-col :span="12">
              <a-row justify="end" type="flex">
                <a-button style="color: orangered" type="link" @click="goRegistry">去注册</a-button>
              </a-row>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <div class="login-footer">
        念 @Copyright 2023 created by Ice2Faith
      </div>
    </div>

    <a-modal
      v-if="dialogs.verifycode.show"
      v-model:visible="dialogs.verifycode.show"
      :title="dialogs.verifycode.title"
      :footer="null"
      width="1200px"
    >
      <VerifyCode :request="verifycode"
                  @refresh="onRefreshVerifyCode"
                  @submit="onSubmitVerifyCode">
      </VerifyCode>
    </a-modal>
  </div>
</template>

<script>
import Auth from '@/framework/auth'
import VerifyCode from '@/components/VerifyCode'

export default {
  name: 'Login',
  components: { VerifyCode },
  props: {},
  data () {
    return {
      verifycode: {
        question: '',
        base64: '',
        code: '',
        width: 320,
        height: 320
      },
      form: {
        username: '',
        password: ''
      },
      controls: {
        loading: false
      },
      rules: {
        username: [{ required: true, message: '请输入用户名!' }],
        password: [{ required: true, message: '请输入密码!' }],
        result: [{ required: true, message: '请输入验证码!' }]
      },
      dialogs: {
        verifycode: {
          show: false,
          title: '请输入验证码'
        }
      }
    }
  },
  mounted () {
    this.logout()
    Auth.removeToken()
    Auth.setRoutes([])
  },
  methods: {
    showVerifyCodeDialog () {
      this.dialogs.verifycode.show = true
    },
    onRefreshVerifyCode () {
      this.$axios({
        url: '/verifycode/refresh',
        method: 'get'
      }).then(({ data }) => {
        this.verifycode = data
      })
    },
    onSubmitVerifyCode (data) {
      Object.assign(this.form, data)
      this.dialogs.verifycode.show = false
    },
    doLogin () {
      this.$refs.form.validateFields().then(() => {
        this.controls.loading = true
        this.$axios({
          url: '/login',
          method: 'post',
          data: this.form
        }).then(({ data }) => {
          const token = data
          Auth.setToken(token)
          this.$axios({
            url: 'sys/user/info',
            method: 'post'
          }).then(({ data }) => {
            Auth.setUser(data)
            const user = Auth.getUser()
            Auth.setRoutes(user.tag.urls)
            Auth.resolveRedirect('/home')
          })
        }).finally(() => {
          this.controls.loading = false
        })
      })
    },
    logout () {
      this.$axios({
        url: '/logout',
        method: 'get'
      }).then(data => {
        this.$router.replace({ path: '/' })
        this.$message.noticeInfo(data.msg)
      })
    },
    goRegistry () {
      this.$router.push({ path: '/registry' })
    },
    goResetPass () {
      this.$router.push({ path: '/pass-reset' })
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
  min-width: 420px;
  width: 25%;
  height: 340px;
  position: fixed;
  right: 15%;
  top: 50%;
  transform: translateY(-50%);
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
  font-size: 18px;
  color: dodgerblue;
  font-weight: bold;
}
</style>
