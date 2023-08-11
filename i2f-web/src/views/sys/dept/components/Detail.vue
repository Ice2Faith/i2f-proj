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
        :rules="rules.deptKey"
        label="部门键"
        name="deptKey"
      >
        <a-input v-model:value="form.deptKey"/>
      </a-form-item>

      <a-form-item
        :rules="rules.name"
        label="名称"
        name="name"
      >
        <a-input v-model:value="form.name"/>
      </a-form-item>

      <a-form-item
        :rules="rules.parentId"
        label="上级部门"
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
          :tree-data="metas.deptTreeData"
          tree-node-filter-prop="label"
        >
        </a-tree-select>
      </a-form-item>

      <a-form-item
        :rules="rules.level"
        label="层级"
        name="level"
      >
        <a-input-number v-model:value="form.level"/>
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

import ListDetailMixin from '@/mixins/ListDetailMixin'
export default {
  components: {},
  mixins: [ListDetailMixin],
  data () {
    return {
      moduleBaseUrl: '/api/sys/dept',
      form: {
        deptKey: '',
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
        }]
      }
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
