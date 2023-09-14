<template>
  <div>
    <Form ref="form"
          :fields="fields"
          v-model:form="form"
          :metas="metas"
          :rules="rules">
    </Form>
    <van-row :gutter="20" justify="center" style="margin-top:12px" type="flex">
      <van-col>
        <van-button @click="doCancel">取消</van-button>
      </van-col>
      <van-col v-if="hasSubmitButton()">
        <van-button :loading="controls.loading" type="primary" @click="doSubmit">提交</van-button>
      </van-col>
    </van-row>
  </div>
</template>
<script>

import FormDetailMode from '@/framework/consts/FormDetailMode'
import ListDetailMixin from '@/mixins/ListDetailMixin'
import Form from '@/components/Form'

export default {
  components: {
    Form
  },
  props: {
    mode: {
      type: String,
      default: FormDetailMode.ADD()
    },
    record: {
      type: Object,
      default: () => {
      }
    }
  },
  mixins: [ListDetailMixin],
  data () {
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
      controls: {
        loading: false
      },
      rules: {
        username: [{ required: true, message: '请输入登录用户名!' }],
        password: [{ required: true, message: '请输入登录密码!' }]
      },
      metas: {
        statusList: [{
          value: 0,
          label: '禁用'
        }, {
          value: 1,
          label: '启用'
        }, {
          value: 99,
          label: '删除'
        }],
        boolList: [{
          value: 0,
          label: '否'
        }, {
          value: 1,
          label: '是'
        }],
        metasFields: {
          text: 'label',
          value: 'value',
          children: 'children'
        }
      },
      fields: [
        {
          prop: 'username',
          label: '用户名'
        },
        {
          prop: 'password',
          label: '密码',
          type: 'password'
        },
        {
          prop: 'realname',
          label: '用户名'
        },
        {
          prop: 'phone',
          label: '电话号码',
          type: 'phone'
        },
        {
          prop: 'email',
          label: '电子邮箱',
          type: 'email'
        },
        {
          prop: 'status',
          label: '状态',
          type: 'radio',
          options: 'statusList',
          optionsFields: 'metasFields'
        },
        {
          prop: 'delFlag',
          label: '是否可删除',
          type: 'radio',
          options: 'boolList',
          optionsFields: 'metasFields'
        },
        {
          prop: 'sysFlag',
          label: '是否系统',
          type: 'radio',
          options: 'boolList',
          optionsFields: 'metasFields'
        },
        {
          prop: 'regDate',
          label: '注册时间',
          disabled: true,
          hasIf: () => this.mode != FormDetailMode.ADD()
        },
        {
          prop: 'updateTime',
          label: '更新日期',
          disabled: true,
          hasIf: (value) => this.mode != FormDetailMode.ADD() && value && value != ''
        },
        {
          prop: 'updateUser',
          label: '更新人',
          disabled: true,
          hasIf: (value) => this.mode != FormDetailMode.ADD() && value && value != ''
        },
        {
          prop: 'createTime',
          label: '创建日期',
          disabled: true,
          hasIf: (value) => this.mode != FormDetailMode.ADD() && value && value != ''
        },
        {
          prop: 'createUser',
          label: '创建人',
          disabled: true,
          hasIf: (value) => this.mode != FormDetailMode.ADD() && value && value != ''
        }
      ]
    }
  },
  methods: {}
}
</script>
<style>

</style>
