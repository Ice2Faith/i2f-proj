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
        :rules="rules.menuKey"
        label="菜单键"
        name="menuKey"
      >
        <a-input v-model:value="form.menuKey"/>
      </a-form-item>

      <a-form-item
        :rules="rules.type"
        label="类型"
        name="type"
      >
        <a-radio-group v-model:value="form.type"
                       :options="metas.menuTypeList" />
      </a-form-item>

      <a-form-item
        :rules="rules.url"
        label="URL"
        name="url"
      >
        <a-input v-model:value="form.url"/>
      </a-form-item>

      <a-form-item
        :rules="rules.permKey"
        label="权限键"
        name="permKey"
      >
        <a-input v-model:value="form.permKey"/>
      </a-form-item>

      <a-form-item
        :rules="rules.remark"
        label="备注"
        name="remark"
      >
        <a-input v-model:value="form.remark"/>
      </a-form-item>

      <a-form-item
        :rules="rules.parentId"
        label="父资源"
        name="parentId"
      >
        <a-tree-select
          v-model:value="form.parentId"
          show-search
          style="width: 100%"
          :fieldNames="{children:'children', label:'name', value: 'id' }"
          :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
          placeholder="请选择"
          allow-clear
          :tree-data="metas.resourceTreeData"
          tree-node-filter-prop="label"
        >
        </a-tree-select>
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
        :rules="rules.orderIndex"
        label="排序字段"
        name="orderIndex"
      >
        <a-input-number v-model:value="form.orderIndex"/>
      </a-form-item>

      <a-form-item
        :rules="rules.icon"
        label="ICON"
        name="icon"
      >
        <a-input v-model:value="form.icon"/>
      </a-form-item>

      <a-form-item
        :rules="rules.style"
        label="样式"
        name="style"
      >
        <a-textarea v-model:value="form.style"
                    :auto-size="{ minRows: 3, maxRows: 10 }"/>
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

import FormDetailMode from '@/framework/consts/FormDetailMode'
import ListDetailMixin from '@/mixins/ListDetailMixin'

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
        resourceTreeData: []
      }
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
