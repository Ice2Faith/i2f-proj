<template>
  <div>
    <Form ref="form"
          :fields="fields"
          v-model:form="form"
          :metas="metas"
          :rules="rules">
    </Form>

    <van-row :gutter="20" justify="center" type="flex" style="margin-top:12px">
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
      default: {}
    }
  },
  mixins: [ListDetailMixin],
  data () {
    return {
      moduleBaseUrl: '/api/sys/role',
      form: {
        roleKey: '',
        roleName: '',
        status: 1,
        delFlag: 1,
        sysFlag: 0,
        updateTime: '',
        updateUser: '',
        createTime: '',
        createUser: ''
      },
      controls: {
        loading: false
      },
      rules: {
        roleKey: [{ required: true, message: '请输入角色键!' }],
        roleName: [{ required: true, message: '请输入角色名称!' }]
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
          prop: 'roleKey',
          label: '角色键'
        },
        {
          prop: 'roleName',
          label: '角色名称'
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
