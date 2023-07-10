<template>
  <div>
    <a-form
      ref="form"
      :label-col="{ span: 6 }"
      :model="form"
      :wrapper-col="{ span: 18 }"
      autocomplete="off"
      name="basic"
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
        <a-row :gutter="20" justify="end" type="flex">
          <a-col>
            <a-button @click="doCancel">取消</a-button>
          </a-col>
          <a-col>
            <a-spin :spinning="controls.loading">
              <a-button type="primary" @click="doSubmit">提交</a-button>
            </a-spin>
          </a-col>
        </a-row>
      </a-form-item>
    </a-form>

  </div>
</template>
<script>

export default {
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
      },

    }
  },
  mounted() {

  },
  methods: {
    doCancel() {
      this.$emit('cancel')
    },
    doSubmit() {
      let _this = this
      _this.controls.loading = true
      setTimeout(() => {
        _this.controls.loading = false
        _this.$emit('submit')
      }, 1000)

    }
  }
}
</script>
<style>

</style>
