<template>
  <div>
    <a-form
      ref="form"
      :label-col="{ span: 4 }"
      :model="form"
      :wrapper-col="{ span: 20 }"
      autocomplete="off"
      layout="inline"
      name="basic"
      @keyup.enter="doSearch"
    >
      <a-form-item
        label="名称"
        name="name"
      >
        <a-input v-model:value="form.name" allow-clear/>
      </a-form-item>

      <a-form-item
        label="平台"
        name="platform"
      >
        <a-input v-model:value="form.platform" allow-clear/>
      </a-form-item>

      <a-form-item :wrapper-col="{ offset: 0, span: 24 }">
        <a-row :gutter="20" justify="center" type="flex">
          <a-col>
            <a-spin :spinning="queryLoading">
              <a-button type="primary" @click="doSearch">
                <template #icon>
                  <SearchOutlined/>
                </template>
                搜索
              </a-button>
            </a-spin>
          </a-col>
          <a-col>
            <a-button @click="doReset">
              <template #icon>
                <clear-outlined/>
              </template>
              重置
            </a-button>
          </a-col>
          <a-col>
            <a-popover title="说明" trigger="click">
              <template #content>
                <p>
                  关于存储安全？<br/>
                  账号、密码、备注三个属性，均加密存储在数据库中<br/>
                  因此，这三个属性不支持做查询条件<br/>
                </p>
              </template>
              <a-button shape="circle" type="link">
                <template #icon><question-circle-outlined /></template>
              </a-button>
            </a-popover>
          </a-col>
        </a-row>
      </a-form-item>
    </a-form>

    <a-row style="margin: 10px;">
      <a-col :span="12">
        <a-row :gutter="20">
          <a-col>
            <a-button type="primary" @click="doImport">
              <template #icon>
                <import-outlined/>
              </template>
              导入
            </a-button>
          </a-col>
          <a-col>
            <a-button type="primary" @click="doExport">
              <template #icon>
                <export-outlined/>
              </template>
              导出
            </a-button>
          </a-col>
        </a-row>
      </a-col>
      <a-col :span="12">
        <a-row :gutter="20" justify="end">
          <a-col>
            <a-button type="primary" @click="doAdd">
              <template #icon>
                <plus-outlined/>
              </template>
              新增
            </a-button>
          </a-col>
        </a-row>
      </a-col>
    </a-row>
    <a-table
      :columns="tableColumns"
      :data-source="tableData"
      :loading="queryLoading"
      :pagination="tablePage"
      :scroll="{ x: 1500 }"
      bordered
      :row-key="record => record[tableRowKey]"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'url'">
          <a href="#" @click="onClickUrl(record.url)">{{record.url}}</a>
        </template>
        <template v-if="column.key === 'account'">
          <a-input :value="record.account"/>
        </template>
        <template v-if="column.key === 'password'">
          <a-input-password :value="record.password"/>
        </template>
        <template v-if="column.key === 'action'">
          <a-row :gutter="5">
            <a-col>
              <a-button @click="doView(record)">
                <template #icon>
                  <eye-outlined/>
                </template>
                详情
              </a-button>
            </a-col>

            <a-dropdown>
              <a-button>
                更多
                <more-outlined/>
              </a-button>
              <template #overlay>
                <a-menu>
                  <a-menu-item style="background-color: dodgerblue;color:white" @click="doEdit(record)">
                    <template #icon>
                      <edit-outlined/>
                    </template>
                    编辑
                  </a-menu-item>
                  <a-menu-divider />
                  <a-menu-item style="background-color: orangered;color: white" @click="doDelete(record)">
                    <template #icon>
                      <delete-outlined/>
                    </template>
                    删除
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </a-row>
        </template>
      </template>
    </a-table>

    <a-modal
      v-if="dialogDetail.show"
      v-model:visible="dialogDetail.show"
      :title="dialogDetail.title"
      :footer="null"
      width="1200px"
    >
      <Detail :mode="dialogDetail.mode"
              :record="dialogDetail.record"
              @cancel="handleDetailCancel"
              @submit="handleDetailOk"></Detail>
    </a-modal>
  </div>
</template>
<script>

import FormDetailMode from "@/framework/consts/FormDetailMode";
import Detail from "./components/Detail";
import ListManageMixin from "@/mixins/ListManageMixin";

export default {
  components: {
    Detail
  },
  mixins:[ListManageMixin],
  data() {
    return {
      moduleBaseUrl: '/api/biz/passwordBook',

      form: {
        name: '',
        platform: null,
        account: '',
        remark: ''
      },
      rules: {

      },
      dialogs: {

      },
      metas:{
      },
      tableColumns: [
          {
            title: '名称',
            dataIndex: 'name',
          },
          {
            title: '平台',
            dataIndex: 'platform',
          },
          {
            title: '链接',
            dataIndex: 'url',
            key: 'url',
          },
          {
            title: '账号',
            dataIndex: 'account',
            key: 'account',
          },
          {
            title: '密码',
            dataIndex: 'password',
            key: 'password',
          },
          {
            title: '备注',
            dataIndex: 'remark',
          },
          {
            title: '更新日期',
            dataIndex: 'updateTime',
          },
          {
            title: '创建日期',
            dataIndex: 'createTime',
          },
          {
            title: '操作',
            key: 'action',
            fixed: 'right',
            width: '200px',
            align: 'center'
          },
        ]

    }
  },

  methods: {
    doImport() {

    },
    doExport() {

    },

  }
}
</script>
<style>

</style>
