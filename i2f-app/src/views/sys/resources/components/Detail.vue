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
      moduleBaseUrl: '/api/sys/resources',
      form: {
        name: '',
        menuKey: '',
        type: 0,
        url: '',
        permKey: '',
        remark: '',
        parentId: null,
        status: 1,
        orderIndex: 1,
        icon: '',
        style: '',
        updateTime: '',
        updateUser: '',
        createTime: '',
        createUser: ''
      },
      controls: {
        loading: false
      },
      rules: {
        name: [{ required: true, message: '请输入名称!' }],
        type: [{ required: true, message: '请输入类型!' }]
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
        menuTypeList: [{
          value: 0,
          label: '菜单'
        }, {
          value: 1,
          label: '权限'
        }],
        resourceTreeData: [],
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
          prop: 'name',
          label: '名称'
        },
        {
          prop: 'menuKey',
          label: '菜单键'
        },
        {
          prop: 'type',
          label: '类型',
          type: 'radio',
          options: 'menuTypeList',
          optionsFields: 'metasFields'
        },
        {
          prop: 'url',
          label: 'URL'
        },
        {
          prop: 'permKey',
          label: '权限键'
        },
        {
          prop: 'remark',
          label: '备注'
        },
        {
          prop: 'parentId',
          label: '父资源',
          type: 'tree',
          options: 'resourceTreeData',
          optionsFields: 'resourcesTreeFields'
        },
        {
          prop: 'status',
          label: '状态',
          type: 'radio',
          options: 'statusList',
          optionsFields: 'metasFields'
        },
        {
          prop: 'orderIndex',
          label: '排序字段',
          type: 'number'
        },
        {
          prop: 'icon',
          label: 'ICON'
        },
        {
          prop: 'style',
          label: '样式'
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
      this.loadResourcesTreeData()
    },
    loadResourcesTreeData () {
      this.$axios({
        url: `${this.moduleBaseUrl}/tree`,
        method: 'get'
      }).then(({ data }) => {
        this.metas.resourceTreeData = [{
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
