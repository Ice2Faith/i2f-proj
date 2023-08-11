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
        label="部门键"
        name="deptKey"
      >
        <a-input v-model:value="form.deptKey" allow-clear/>
      </a-form-item>

      <a-form-item
        label="名称"
        name="name"
      >
        <a-input v-model:value="form.name" allow-clear/>
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
        label="备注"
        name="remark"
      >
        <a-input v-model:value="form.remark" allow-clear/>
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
                  <a-menu-item style="background-color: lightseagreen;color: white" @click="doAddChildren(record)">
                    <template #icon>
                      <plus-outlined/>
                    </template>
                    添加
                  </a-menu-item>
                  <a-menu-divider />
                  <a-menu-item style="background-color: deepskyblue;color: white" @click="doManage(record)">
                    <template #icon>
                      <safety-certificate-outlined />
                    </template>
                    管理
                  </a-menu-item>
                  <a-menu-divider/>
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

    <a-drawer
      v-if="dialogs.manage.show"
      v-model:visible="dialogs.manage.show"
      :title="dialogs.manage.title"
      width="100%"
      placement="right"
    >
      <template #extra>
        <a-button style="margin-right: 8px" @click="onManageCancel">取消</a-button>
      </template>
      <SysDeptManage :dept="dialogs.manage.record"></SysDeptManage>
    </a-drawer>
  </div>
</template>
<script>

import Detail from './components/Detail'

import ListManageMixin from '@/mixins/ListManageMixin'
import FormDetailMode from '@/framework/consts/FormDetailMode'
import SysDeptManage from '@/views/sys/dept/components/SysDeptManage'

export default {
  components: {
    SysDeptManage,
    Detail
  },
  mixins: [ListManageMixin],
  data () {
    return {
      moduleBaseUrl: '/api/sys/dept',

      form: {
        deptKey: '',
        name: '',
        status: '',
        remark: ''
      },
      rules: {},
      dialogs: {
        manage: {
          title: '部门管理',
          show: false,
          record: {}
        }
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
        }]
      },
      tableColumns: [
        {
          title: '部门键',
          dataIndex: 'deptKey'
        },
        {
          title: '名称',
          dataIndex: 'name'
        },
        {
          title: '状态',
          dataIndex: 'statusDesc'
        },
        {
          title: '备注',
          dataIndex: 'remark'
        },
        {
          title: '更新日期',
          dataIndex: 'updateTime'
        },
        {
          title: '更新人',
          dataIndex: 'updateUser'
        },
        {
          title: '创建日期',
          dataIndex: 'createTime'
        },
        {
          title: '创建人',
          dataIndex: 'createUser'
        },
        {
          title: '操作',
          key: 'action',
          fixed: 'right',
          width: '200px',
          align: 'center'
        }
      ]
    }
  },

  methods: {
    getData (reset) {
      if (reset) {
        this.tablePage.current = 1
      }
      this.queryLoading = true
      this.$axios({
        url: `${this.moduleBaseUrl}/tree`,
        method: 'get',
        params: this.form
      }).then(({ data }) => {
        this.tableData = data
      }).finally(() => {
        this.queryLoading = false
      })
    },
    doImport () {

    },
    doExport () {

    },
    doAddChildren (record) {
      this.dialogDetail.mode = FormDetailMode.ADD()
      this.dialogDetail.title = '新增'
      this.dialogDetail.record = {
        parentId: record.id
      }
      this.dialogDetail.show = true
    },
    doManage (record) {
      this.dialogs.manage.record = record
      this.dialogs.manage.show = true
    },
    onManageCancel () {
      this.dialogs.manage.show = false
    }
  }
}
</script>
<style>

</style>
