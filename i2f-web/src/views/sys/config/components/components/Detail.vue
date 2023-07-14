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
        :rules="rules.entryId"
        label="项ID"
        name="entryId"
      >
        <a-input v-model:value="form.entryId"/>
      </a-form-item>

      <a-form-item
        :rules="rules.entryKey"
        label="项键"
        name="entryKey"
      >
        <a-input v-model:value="form.entryKey"/>
      </a-form-item>

      <a-form-item
        :rules="rules.entryName"
        label="项名称"
        name="entryName"
      >
        <a-input v-model:value="form.entryName"/>
      </a-form-item>

      <a-form-item
        :rules="rules.entryDesc"
        label="项描述"
        name="entryDesc"
      >
        <a-input v-model:value="form.entryDesc"/>
      </a-form-item>

      <a-form-item
        :rules="rules.entryOrder"
        label="项排序"
        name="entryOrder"
      >
        <a-input v-model:value="form.entryOrder"/>
      </a-form-item>

      <a-form-item
        :rules="rules.parentEntryId"
        label="上级配置项"
        name="parentEntryId"
      >
        <a-tree-select
          v-model:value="form.parentEntryId"
          show-search
          style="width: 100%"
          :fieldNames="{children:'children', label:'entryName', value: 'entryId' }"
          :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
          placeholder="请选择"
          allow-clear
          :tree-data="metas.configItemsTreeData"
          tree-node-filter-prop="label"
        >
        </a-tree-select>
      </a-form-item>

      <a-form-item
        :rules="rules.paramDesc"
        label="参数描述"
        name="paramDesc"
      >
        <a-input v-model:value="form.paramDesc"/>
      </a-form-item>

      <a-form-item
        :rules="rules.param1"
        label="参数1"
        name="param1"
      >
        <a-input v-model:value="form.param1"/>
      </a-form-item>

      <a-form-item
        :rules="rules.param2"
        label="参数2"
        name="param2"
      >
        <a-input v-model:value="form.param2"/>
      </a-form-item>

      <a-form-item
        :rules="rules.param3"
        label="参数3"
        name="param3"
      >
        <a-input v-model:value="form.param3"/>
      </a-form-item>

      <a-form-item
        :rules="rules.param4"
        label="参数4"
        name="param4"
      >
        <a-input v-model:value="form.param4"/>
      </a-form-item>

      <a-form-item
        :rules="rules.param5"
        label="参数5"
        name="param5"
      >
        <a-input v-model:value="form.param5"/>
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
        :rules="rules.level"
        label="层级"
        name="level"
      >
        <a-input-number v-model:value="form.level"/>
      </a-form-item>

      <a-form-item
        :rules="rules.validTime"
        label="生效时间"
        name="validTime"
      >
        <a-date-picker v-model:value="form.validTime"
                       show-time
                       valueFormat="YYYY-MM-DD HH:mm:ss"
                       format="YYYY-MM-DD HH:mm:ss"
                       placeholder="请选择"   />

      </a-form-item>

      <a-form-item
        :rules="rules.invalidTime"
        label="失效时间"
        name="invalidTime"
      >
        <a-date-picker v-model:value="form.invalidTime"
                       show-time
                       valueFormat="YYYY-MM-DD HH:mm:ss"
                       format="YYYY-MM-DD HH:mm:ss"
                       placeholder="请选择"   />

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
        name="status"
      >
        <a-radio-group v-model:value="form.sysFlag"
                       :options="metas.boolList" />
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
        configId: '',
        entryId: '',
        entryKey: '',
        entryName: '',
        entryDesc: '',
        entryOrder: 1,
        parentEntryId: '',
        paramDesc: '',
        param1: '',
        param2: '',
        param3: '',
        param4: '',
        param5: '',
        status: 1,
        level: 1,
        validTime: '',
        invalidTime: '',
        modFlag: 1,
        delFlag: 1,
        sysFlag: 0,
        updateTime: '',
        updateUser: '',
        createTime: '',
        createUser: ''
      },
      rules: {
        entryId: [{required: true, message: '请输入键!'}],
        entryName: [{required: true, message: '请输入名称!'}],
      },
      metas: {
        configItemsTreeData:[],
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
    hookAfterMounted(){
      this.loadDeptTreeData()
    },
    loadDeptTreeData(){
      this.$axios({
        url: `${this.moduleBaseUrl}/items/tree/id/${this.record.id}`,
        method: 'get',
        params: this.form
      }).then(({data}) => {
        this.metas.configItemsTreeData=[{
          entryId: 0,
          entryName: '根节点',
          children: data
        }]
      })
    }
  }
}
</script>
<style>

</style>
