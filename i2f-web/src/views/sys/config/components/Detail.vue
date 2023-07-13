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
        :rules="rules.configKey"
        label="配置键"
        name="configKey"
      >
        <a-input v-model:value="form.configKey"/>
      </a-form-item>

      <a-form-item
        :rules="rules.name"
        label="名称"
        name="name"
      >
        <a-input v-model:value="form.name"/>
      </a-form-item>

      <a-form-item
        :rules="rules.modFlag"
        label="是否可修改"
        name="modFlag"
      >
        <a-radio-group v-model:value="form.modFlag"
                       :options="metas.boolList" />
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

import ListDetailMixin from "@/mixins/ListDetailMixin";
export default {
  components: {},
  mixins:[ListDetailMixin],
  data() {
    return {
      moduleBaseUrl: '/api/sys/config',
      form: {
        configKey: '',
        name: '',
        modFlag: 1,
        delFlag: 1,
        sysFlag: 0,
        remark: '',
        updateTime: '',
        updateUser: '',
        createTime: '',
        createUser: ''
      },
      rules: {
        configKey: [{required: true, message: '请输入配置键!'}],
        name: [{required: true, message: '请输入名称!'}],
      },
      metas: {
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
