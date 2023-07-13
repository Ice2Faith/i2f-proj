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
        label="菜单键"
        name="menuKey"
      >
        <a-input v-model:value="form.menuKey" allow-clear/>
      </a-form-item>

      <a-form-item
        label="类型"
        name="type"
      >
        <a-select
          v-model:value="form.type"
          show-search
          allow-clear
          placeholder="请选择"
          :options="metas.menuTypeList"
          :filter-option="filterOption"
        >
        </a-select>
      </a-form-item>

      <a-form-item
        label="状态"
        name="status"
      >
        <a-select
          v-model:value="form.status"
          show-search
          allow-clear
          placeholder="请选择"
          :options="metas.statusList"
          :filter-option="filterOption"
        >
        </a-select>
      </a-form-item>

      <a-form-item
        label="URL"
        name="url"
      >
        <a-input v-model:value="form.url" allow-clear/>
      </a-form-item>

      <a-form-item
        label="权限键"
        name="permKey"
      >
        <a-input v-model:value="form.permKey" allow-clear/>
      </a-form-item>

      <a-form-item
        label="备注"
        name="remark"
      >
        <a-input v-model:value="form.remark" allow-clear/>
      </a-form-item>

      <a-form-item
        label="ICON"
        name="icon"
      >
        <a-input v-model:value="form.icon" allow-clear/>
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
      moduleBaseUrl: '/api/sys/resources',

      form: {
        name: '',
        menuKey: '',
        type: null,
        status: null,
        url: '',
        permKey: '',
        remark: '',
        icon: '',
      },
      rules: {

      },
      dialogs: {

      },
      metas:{
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
        menuTypeList:[{
          value: 0,
          label: '菜单',
        }, {
          value: 1,
          label: '权限',
        }],
      },
      tableColumns: [
          {
            title: '名称',
            dataIndex: 'name',
          },
          {
            title: '菜单键',
            dataIndex: 'menuKey',
          },
          {
            title: '类型',
            dataIndex: 'typeDesc',
          },
          {
            title: 'URL',
            dataIndex: 'url',
          },
          {
            title: '备注',
            dataIndex: 'remark',
          },
          {
            title: '状态',
            dataIndex: 'statusDesc',
          },
          {
            title: '排序字段',
            dataIndex: 'orderIndex',
          },
          {
            title: 'ICON',
            dataIndex: 'icon',
          },
          {
            title: '更新日期',
            dataIndex: 'updateTime',
          },
          {
            title: '更新人',
            dataIndex: 'updateUser',
          },
          {
            title: '创建日期',
            dataIndex: 'createTime',
          },
          {
            title: '创建人',
            dataIndex: 'createUser',
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
