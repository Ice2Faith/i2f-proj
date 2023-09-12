<template>
  <div>

    <van-form
      van-form ref="form"
    >
      <van-cell-group inset>
        <van-field
          v-model="form.roleKey"
          clearable
          :rules="rules.roleKey"
          name="roleKey"
          label="角色键"
        />

        <van-field
          v-model="form.roleName"
          clearable
          :rules="rules.roleName"
          label="角色名称"
          name="roleName"
        />

        <van-field
          :rules="rules.status"
          label="状态"
          name="status">
          <template #input>
            <van-radio-group v-model="form.status" direction="horizontal">
              <van-radio v-for="(item,index) in metas.statusList"
                            :key="item.value"
                            :name="item.value">
                {{item.label}}
              </van-radio>
            </van-radio-group>
          </template>
        </van-field>

        <van-field
          :rules="rules.delFlag"
          label="是否可删除"
          name="delFlag">
          <template #input>
            <van-radio-group v-model="form.delFlag" direction="horizontal">
              <van-radio v-for="(item,index) in metas.boolList"
                            :key="item.value"
                            :name="item.value" shape="square">
                {{item.label}}
              </van-radio>
            </van-radio-group>
          </template>
        </van-field>

        <van-field
          :rules="rules.sysFlag"
          label="是否系统"
          name="sysFlag">
          <template #input>
            <van-radio-group v-model="form.sysFlag" direction="horizontal">
              <van-radio v-for="(item,index) in metas.boolList"
                            :key="item.value"
                            :name="item.value" shape="square">
                {{item.label}}
              </van-radio>
            </van-radio-group>
          </template>
        </van-field>

        <van-field
          v-model="form.updateTime"
          clearable
          :rules="rules.updateTime"
          disabled
          label="更新日期"
          name="updateTime"
        />

        <van-field
          v-model="form.updateUser"
          clearable
          :rules="rules.updateUser"
          disabled
          label="更新人"
          name="updateUser"
        />

        <van-field
          v-model="form.createTime"
          clearable
          :rules="rules.createTime"
          disabled
          label="创建日期"
          name="createTime"
        />

        <van-field
          v-model="form.createUser"
          clearable
          :rules="rules.createUser"
          disabled
          label="创建人"
          name="createUser"
        />

      </van-cell-group>
    </van-form>

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
        }]
      }
    }
  },
  methods: {}
}
</script>
<style>

</style>
