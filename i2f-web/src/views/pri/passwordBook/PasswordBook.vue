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
      v-if="dialogs.detail.show"
      v-model:visible="dialogs.detail.show"
      :title="dialogs.detail.title"
      :footer="null"
      width="800px"
    >
      <Detail :mode="dialogs.detail.mode"
              :record="dialogs.detail.record"
              @cancel="handleDetailCancel"
              @submit="handleDetailOk"></Detail>
    </a-modal>
  </div>
</template>
<script>

import FormDetailMode from "@/framework/consts/FormDetailMode";
import Detail from "./components/Detail";

export default {
  components: {
    Detail
  },
  data() {
    return {
      form: {
        name: '',
        platform: null,
        account: '',
        remark: ''
      },
      controls: {
        loading: false,
      },
      rules: {

      },
      dialogs: {
        detail: {
          title: '新增',
          show: false,
          mode: FormDetailMode.ADD(),
          record: {},
        }
      },
      metas:{
        baseUrl: '/api/biz/passwordBook',
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
    }
  },
  mounted() {
    this.doSearch()
  },
  methods: {
    filterOption(input, option) {
      return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    },
    onClickUrl(url){
      if(url &&url!=''){
        window.open(url,'_blank')
      }
    },
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
        url: `${this.metas.baseUrl}/page/${this.table.page.pageSize}/${(this.table.page.current - 1)}`,
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
    getRecordStatusStyle(record){
      for(let key in this.metas.statusList){
        let item=this.metas.statusList[key]
        if(item.value==record.status){
          return {
            backgroundColor: item.color,
            border:  `solid 1px ${item.color}`,
            color: 'white'
          }
        }
      }
      return {}
    },
    doAdd() {
      this.dialogs.detail.mode=FormDetailMode.ADD()
      this.dialogs.detail.title='新增'
      this.dialogs.detail.show = true
    },
    handleDetailOk() {
      this.dialogs.detail.show = false
      this.doSearch()
    },
    handleDetailCancel() {
      this.dialogs.detail.show = false
    },
    doImport() {

    },
    doExport() {

    },
    doView(record) {
      this.dialogs.detail.mode=FormDetailMode.VIEW()
      this.dialogs.detail.title='详情'
      this.dialogs.detail.record=record
      this.dialogs.detail.show = true
    },
    doRun(record){
      this.$axios({
        url: `${this.metas.baseUrl}/run/${record.id}`,
        method: 'put',
        data: {}
      }).then(()=>{
        this.doSearch()
      })
    },
    doSuspend(record){
      this.$axios({
        url: `${this.metas.baseUrl}/suspend/${record.id}`,
        method: 'put',
        data: {}
      }).then(()=>{
        this.doSearch()
      })
    },
    doFinish(record){
      this.$axios({
        url: `${this.metas.baseUrl}/finish/${record.id}`,
        method: 'put',
        data: {}
      }).then(()=>{
        this.doSearch()
      })
    },
    doEdit(record) {
      this.dialogs.detail.mode=FormDetailMode.EDIT()
      this.dialogs.detail.title='编辑'
      this.dialogs.detail.record=record
      this.dialogs.detail.show = true
    },
    doDelete(record) {
      this.$axios({
        url: `${this.metas.baseUrl}/delete/${record.id}`,
        method: 'delete',
        data: {}
      }).then(()=>{
        this.doSearch()
      })
    }
  }
}
</script>
<style>

</style>
