<template>
  <div>

    <a-form
      ref="form"
      :label-col="{ span: 4 }"
      :model="form"
      :wrapper-col="{ span: 20 }"
      autocomplete="off"
      name="basic"
    >
      <a-form-item
        :rules="rules.name"
        label="名称"
        name="name"
      >
        <a-input v-model:value="form.name"/>
      </a-form-item>

      <a-form-item
        :rules="rules.platform"
        label="平台"
        name="platform"
      >
        <a-input v-model:value="form.platform"/>
      </a-form-item>


      <a-form-item
        :rules="rules.url"
        label="链接"
        name="url"
      >
        <a-input v-model:value="form.url"/>
      </a-form-item>

      <a-form-item
        :rules="rules.account"
        label="账号"
        name="account"
      >
        <a-input v-model:value="form.account"/>
      </a-form-item>

      <a-form-item
        :rules="rules.password"
        label="密码"
        name="password"
      >
        <a-input-password v-model:value="form.password"/>
      </a-form-item>

      <a-form-item
        :rules="rules.remark"
        label="备注"
        name="remark"
      >
        <a-input v-model:value="form.remark"/>
      </a-form-item>

      <a-form-item
        :rules="rules.updateTime"
        disabled
        label="更新日期"
        name="updateTime"
      >
        <a-input disabled v-model:value="form.updateTime"/>
      </a-form-item>

      <a-form-item
        :rules="rules.createTime"
        label="创建日期"
        name="createTime"
      >
        <a-input disabled v-model:value="form.createTime"/>
      </a-form-item>


    </a-form>

    <a-row :gutter="20" justify="end" type="flex" style="margin-top:12px">
      <a-col>
        <a-button @click="doCancel">取消</a-button>
      </a-col>
      <a-col v-if="hasSubmitButton()">
        <a-spin :spinning="submitLoading">
          <a-button type="primary" @click="doSubmit">提交</a-button>
        </a-spin>
      </a-col>
    </a-row>
  </div>
</template>
<script>

import FormDetailMode from "@/framework/consts/FormDetailMode";
import ListDetailMixin from "@/mixins/ListDetailMixin";

export default {
  props: {
    mode: {
      type: String,
      default: FormDetailMode.ADD()
    },
    record: {
      type: Object,
      default: {}
    }
  },
  mixins:[ListDetailMixin],
  data() {
    return {
      moduleBaseUrl: '/api/biz/passwordBook',

      form: {
        name: '',
        platform: '',
        url: '',
        account: '',
        password: '',
        remark: '',
        updateTime: '',
        createTime: '',
      },
      rules: {
        name: [{required: true, message: '请输入名称!'}],
        account: [{required: true, message: '请输入账号!'}]
      },
      metas: {

      },
    }
  },
  methods: {

  }
}
</script>
<style>

</style>
