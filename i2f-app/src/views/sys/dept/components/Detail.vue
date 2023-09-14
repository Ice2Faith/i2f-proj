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
      moduleBaseUrl: '/api/sys/dept',
      form: {
        eptKey: '',
        name: '',
        remark: '',
        parentId: null,
        level: 0,
        status: 1,
        updateTime: '',
        updateUser: '',
        createTime: '',
        createUser: ''
      },
      controls: {
        loading: false
      },
      rules: {
        deptKey: [{ required: true, message: '请输入部门键!' }],
        name: [{ required: true, message: '请输入名称!' }]
      },
      metas: {
        deptTreeData: [],
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
        metasFields: {
          text: 'label',
          value: 'value',
          children: 'children'
        },
        resourcesTreeFields: {
          text: 'name',
          key: 'id',
          children: 'children'
        }
      },
      fields: [
        {
          prop: 'deptKey',
          label: '部门键'
        },
        {
          prop: 'name',
          label: '名称'
        },
        {
          prop: 'parentId',
          label: '上级部门',
          type: 'tree',
          options: 'deptTreeData',
          optionsFields: 'resourcesTreeFields'
        },
        {
          prop: 'level',
          label: '层级',
          type: 'number'
        },
        {
          prop: 'status',
          label: '状态',
          type: 'radio',
          options: 'statusList',
          optionsFields: 'metasFields'
        },
        {
          prop: 'remark',
          label: '备注'
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
  methods: {
    hookAfterMounted () {
      this.loadDeptTreeData()
    },
    loadDeptTreeData () {
      this.$axios({
        url: `${this.moduleBaseUrl}/tree`,
        method: 'get'
      }).then(({ data }) => {
        this.metas.deptTreeData = [{
          id: 0,
          name: '根节点',
          children: data
        }]
      })
    }
  }
}
</script>
<style>

</style>
