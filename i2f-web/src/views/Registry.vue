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
          ref="form"
          :label-col="{ span: 4 }"
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
            <a-input v-model:value="form.username" placeholder="唯一的登录账号名"/>
          </a-form-item>

          <a-form-item
            :rules="rules.password"
            label="密码"
            name="password"
          >
            <a-input-password v-model:value="form.password" placeholder="登录密码"/>
          </a-form-item>

          <a-form-item
            :rules="rules.repeatPass"
            label="确认密码"
            name="repeatPass"
          >
            <a-input-password v-model:value="form.repeatPass" placeholder="确认登录密码"/>
          </a-form-item>

          <a-form-item
            :rules="rules.realname"
            label="昵称"
            name="realname"
          >
            <a-input v-model:value="form.realname" placeholder="自己喜欢的昵称"/>
          </a-form-item>

          <a-form-item
            :rules="rules.phone"
            label="电话号码"
            name="phone"
          >
            <a-input v-model:value="form.phone" placeholder="忘记密码时的验证方式之一"/>
          </a-form-item>

          <a-form-item
            :rules="rules.email"
            label="电子邮箱"
            name="email"
          >
            <a-input v-model:value="form.email" placeholder="忘记密码时的验证方式之一"/>
          </a-form-item>

          <a-form-item :wrapper-col="{ offset: 0, span: 24 }">
            <a-row justify="center" type="flex">
              <a-spin :spinning="controls.loading">
                <a-button size="large" style="background: orangered;border: solid 1px orangered" type="primary"
                          @click="doRegistry">注册
                </a-button>
              </a-spin>
            </a-row>
          </a-form-item>

          <a-row justify="end" type="flex">
            <a-button type="link" @click="goLogin">去登录</a-button>
          </a-row>
        </a-form>
      </div>
      <div class="login-footer">
        念 @Copyright 2023 created by Ice2Faith
      </div>
    </div>
  </div>
</template>

<script>

import Config from "@/framework/config";

export default {
  name: 'Registry',
  props: {},
  data() {
    return {
      form: {
        username: '',
        password: '',
        repeatPass: '',
        realname: '',
        phone: '',
        email: ''
      },
      controls: {
        loading: false,
      },
      rules: {
        username: [{required: true, message: '请输入用户名!'}],
        password: [{required: true, message: '请输入密码!'}],
        repeatPass: [{required: true, message: '请输入确认密码!'}],
        realname: [{required: true, message: '请输入昵称!'}],
      }
    }
  },
  mounted() {

  },
  methods: {
    doRegistry() {
      this.$refs.form.validateFields().then(() => {
        if (this.form.password != this.form.repeatPass) {
          this.$message.noticeError('确认密码和密码不一致，请重试')
          return
        }
        this.controls.loading = true
        this.$axios({
          url: '/sys/entrance/registry',
          method: 'post',
          data: this.form
        }).then(({data}) => {
          this.$router.push({path: Config.LOGIN_ROUTE})
        }).finally(() => {
          this.controls.loading = false
        })
      })
    },
    goLogin() {
      this.$router.push({path: Config.LOGIN_ROUTE})
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
  min-width: 500px;
  width: 25%;
  height: 520px;
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
  color: orangered;
  font-weight: bold;
}
</style>
