<template>
  <div>
      <span>用户名：</span><input type="text" v-model="form.username" placeholder="请输入用户名"/><br/>
      <span>密码：</span><input type="password" v-model="form.password" placeholder="请输入密码"/><br/>
      <span>验证码：</span><input type="text" v-model="form.result" placeholder="请输入验证码" @click.native="showVerifyCodeDialog"/><br/>
    <button @click="doLogin()">登录</button>

    <div
      v-if="dialogs.verifycode.show"
      style="width:800px"
    >
      <h4>{{dialogs.verifycode.title}}</h4>
      <hr/>
      <VerifyCode :request="verifycode"
                  @refresh="onRefreshVerifyCode"
                  @submit="onSubmitVerifyCode">
      </VerifyCode>
    </div>
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
    }
  }
}
</script>

<style scoped>

</style>
