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
              <a-button type="primary" @click="doSearch">搜索</a-button>
            </a-spin>
          </a-col>
          <a-col>
            <a-button @click="doReset">重置</a-button>
          </a-col>
        </a-row>
      </a-form-item>
    </a-form>

    <a-table
      :columns="table.columns"
      :data-source="table.data"
      :loading="controls.loading"
      :pagination="table.page"
      :row-key="record => record[table.rowKey]"
      @change="handleTableChange"
    >
    </a-table>
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
    }
  }
}
</script>
<style>

</style>
