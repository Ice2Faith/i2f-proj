<template>
  <div>
    <a-form
      ref="form"
      :label-col="{ span: 6 }"
      :model="form"
      :wrapper-col="{ span: 18 }"
      autocomplete="off"
      layout="inline"
      name="basic"
      @keyup.enter="doSearch"
    >
      <a-form-item
        :rules="rules.username"
        label="用户名"
        name="username"
      >
        <a-input v-model:value="form.username"/>
      </a-form-item>

      <a-form-item
        :rules="rules.password"
        label="密码"
        name="password"
      >
        <a-input-password v-model:value="form.password"/>
      </a-form-item>


      <a-form-item :wrapper-col="{ offset: 0, span: 24 }">
        <a-row :gutter="20" justify="center" type="flex">
          <a-col>
            <a-spin :spinning="controls.loading">
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
      :columns="table.columns"
      :data-source="table.data"
      :loading="controls.loading"
      :pagination="table.page"
      :scroll="{ x: 1500 }"
      bordered
      :row-key="record => record[table.rowKey]"
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
                  <a-menu-item key="1" style="background-color: dodgerblue;color:white" @click="doEdit(record)">
                    <template #icon>
                      <edit-outlined/>
                    </template>
                    编辑
                  </a-menu-item>
                  <a-menu-item key="2" style="background-color: orangered;color: white" @click="doDelete(record)">
                    <template #icon>
                      <delete-outlined/>
                    </template>
                    删除
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>

            <a-col>

            </a-col>
            <a-col>

            </a-col>
          </a-row>
        </template>
      </template>
    </a-table>

    <a-modal
      v-model:visible="dialogs.add.show"
      :confirm-loading="dialogs.add.confirmLoading"
      :title="dialogs.add.title"
      @ok="handleAddOk"
    >

    </a-modal>
  </div>
</template>
<script>

export default {
  name: 'TaskList',
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      controls: {
        loading: false,
      },
      rules: {
        username: [{required: true, message: '请输入用户名!'}],
        password: [{required: true, message: '请输入密码!'}],
      },
      dialogs: {
        add: {
          title: '新增',
          show: false,
          confirmLoading: false,
        }
      },
      table: {
        data: [{
          name: '20'
        }],
        page: {
          current: 1,
          pageSize: 20,
          total: 2000,
          defaultPageSize: 20,
          showQuickJumper: true,
          showSizeChanger: true,
          // showTotal: true,
          pageSizeOptions: [10, 20, 50, 100, 300]
        },
        rowKey: 'id',
        columns: [
          {
            title: '名称',
            dataIndex: 'name',
          },
          {
            title: '内容',
            dataIndex: 'content',
          },
          {
            title: '等级',
            dataIndex: 'level',
          },
          {
            title: '价值',
            dataIndex: 'value',
          },
          {
            title: '进度',
            dataIndex: 'progress',
          },
          {
            title: '处理',
            dataIndex: 'process',
          },
          {
            title: '截止日期',
            dataIndex: 'deadline',
          },
          {
            title: '状态',
            dataIndex: 'statusDesc',
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
    }
  },
  mounted() {
    this.doSearch()
  },
  methods: {
    doSearch() {
      this.getData(true)
    },
    doReset() {
      this.$refs.form.resetFields()
    },
    getData(reset) {
      if (reset) {
        this.table.page.current = 1
      }
      this.controls.loading = true
      this.$axios({
        url: '/api/biz/taskList/page/' + this.table.page.pageSize + "/" + (this.table.page.current - 1),
        method: 'get',
        params: this.form
      }).then(({data}) => {
        this.table.data = data.list
        this.table.page.current = data.index + 1
        this.table.page.pageSize = data.size
        this.table.page.total = data.total
      }).finally(() => {
        this.controls.loading = false
      })
    },
    handleTableChange(pagination, filters, sorter, {currentDataSource}) {
      this.table.page = pagination
    },
    doAdd() {
      this.dialogs.add.show = true
      this.dialogs.add.confirmLoading = false

    },
    handleAddOk() {
      let _this = this
      this.dialogs.add.confirmLoading = true
      setTimeout(() => {
        _this.dialogs.add.confirmLoading = false
        _this.dialogs.add.show = false
      }, 1000)
    },
    doImport() {

    },
    doExport() {

    },
    doView(record) {

    },
    doEdit(record) {

    },
    doDelete(record) {

    }
  }
}
</script>
<style>

</style>
