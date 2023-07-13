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
        :rules="rules.username"
        label="登录用户名"
        name="username"
      >
        <a-input v-model:value="form.username"/>
      </a-form-item>

      <a-form-item
        v-if="hasAddMode()"
        :rules="rules.password"
        label="密码"
        name="password"
      >
        <a-input-password v-model:value="form.password"/>
      </a-form-item>

      <a-form-item
        :rules="rules.realname"
        label="用户名"
        name="realname"
      >
        <a-input v-model:value="form.realname"/>
      </a-form-item>

      <a-form-item
        :rules="rules.phone"
        label="电话号码"
        name="phone"
      >
        <a-input v-model:value="form.phone"/>
      </a-form-item>

      <a-form-item
        :rules="rules.email"
        label="电子邮箱"
        name="email"
      >
        <a-input v-model:value="form.email"/>
      </a-form-item>

      <a-form-item
        :rules="rules.status"
        label="状态"
        name="status"
      >
        <a-radio-group v-model:value="form.status"
                       :options="metas.statusList" />
      </a-form-item>

      <a-form-item
        :rules="rules.delFlag"
        label="是否可删除"
        name="delFlag"
      >
        <a-radio-group v-model:value="form.delFlag"
                       :options="metas.boolList" />
      </a-form-item>

      <a-form-item
        :rules="rules.sysFlag"
        label="是否系统"
        name="sysFlag"
      >
        <a-radio-group v-model:value="form.sysFlag"
                       :options="metas.boolList" />
      </a-form-item>

      <a-form-item
        :rules="rules.regDate"
        disabled
        label="注册时间"
        name="regDate"
      >
        <a-input disabled v-model:value="form.regDate"/>
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
        :rules="rules.updateUser"
        disabled
        label="更新人"
        name="updateUser"
      >
        <a-input disabled v-model:value="form.updateUser"/>
      </a-form-item>

      <a-form-item
        :rules="rules.createTime"
        label="创建日期"
        name="createTime"
      >
        <a-input disabled v-model:value="form.createTime"/>
      </a-form-item>

      <a-form-item
        :rules="rules.createUser"
        label="创建人"
        name="createUser"
      >
        <a-input disabled v-model:value="form.createUser"/>
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

import ListDetailMixin from "@/mixins/ListDetailMixin";
export default {
  components: {},
  mixins:[ListDetailMixin],
  data() {
    return {
      moduleBaseUrl: '/api/sys/user',
      form: {
        username: '',
        password: '',
        realname: '',
        phone: '',
        email: '',
        delFlag: 1,
        sysFlag: 0,
        status: 1,
        regDate: '',
        updateTime: '',
        updateUser: '',
        createTime: '',
        createUser: ''
      },
      rules: {
        username: [{required: true, message: '请输入登录用户名!'}],
        password: [{required: true, message: '请输入登录密码!'}],
      },
      metas: {
        statusList:[{
          value: 0,
          label: '禁用',
        }, {
          value: 1,
          label: '启用',
        }, {
          value: 99,
          label: '删除',
        }],
        boolList:[{
          value: 0,
          label: '否',
        }, {
          value: 1,
          label: '是',
        }],
      },
    }
  },
  methods: {

  }
}
</script>
<style>

</style>
