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
        :rules="rules.roleKey"
        label="角色键"
        name="roleKey"
      >
        <a-input v-model:value="form.roleKey"/>
      </a-form-item>

      <a-form-item
        :rules="rules.roleName"
        label="角色名称"
        name="roleName"
      >
        <a-input v-model:value="form.roleName"/>
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
    },
    dept:{
      type: Object,
      default: {}
    }
  },
  mixins:[ListDetailMixin],
  data() {
    return {
      moduleBaseUrl: '/api/sys/dept/role',
      form: {
        deptId: '',
        roleKey: '',
        roleName: '',
        status: 1,
        updateTime: '',
        updateUser: '',
        createTime: '',
        createUser: ''
      },
      controls: {
        loading: false,
      },
      rules: {
        roleKey: [{required: true, message: '请输入角色键!'}],
        roleName: [{required: true, message: '请输入角色名称!'}],
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
    hookBeforeMounted(){
      this.form.deptId=this.dept.id
    },
  }
}
</script>
<style>

</style>
