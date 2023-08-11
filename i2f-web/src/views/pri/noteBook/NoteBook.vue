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
        label="标题"
        name="title"
      >
        <a-input v-model:value="form.title" allow-clear/>
      </a-form-item>

      <a-form-item
        label="关键字"
        name="keywords"
      >
        <a-input v-model:value="form.keywords" allow-clear/>
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
                  <a-menu-item style="background-color: lightseagreen;color:white" @click="doPreview(record)">
                    <template #icon>
                      <eye-outlined/>
                    </template>
                    阅览
                  </a-menu-item>
                  <a-menu-item style="background-color: dodgerblue;color:white" @click="doEdit(record)">
                    <template #icon>
                      <edit-outlined/>
                    </template>
                    编辑
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

    <a-modal
      v-if="dialogs.preview.show"
      v-model:visible="dialogs.preview.show"
      :title="dialogs.preview.title"
      @ok="dialogs.preview.show=false"
      width="100%"
      wrap-class-name="full-modal"
    >
      <markdown-editor :text="dialogs.preview.record.content"
                       :fullscreen="false"
                       :height="'auto'"
                       :edit="false">
      </markdown-editor>
    </a-modal>
  </div>
</template>
<script>

import Detail from './components/Detail'
import MarkdownEditor from '@/components/MarkdownEditor'

import ListManageMixin from '@/mixins/ListManageMixin'

export default {
  components: {
    MarkdownEditor,
    Detail
  },
  mixins: [ListManageMixin],
  data () {
    return {
      moduleBaseUrl: '/api/biz/noteBook',

      form: {
        title: '',
        keywords: '',
        remark: ''
      },
      rules: {},
      dialogs: {
        preview: {
          title: '阅览',
          show: false,
          record: {}
        }
      },
      metas: {},
      tableColumns: [
        {
          title: '标题',
          dataIndex: 'title'
        },
        {
          title: '关键字',
          dataIndex: 'keywords'
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
          title: '创建日期',
          dataIndex: 'createTime'
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
    doImport () {

    },
    doExport () {

    },
    doPreview (record) {
      this.dialogs.preview.title = '阅览'
      this.dialogs.preview.record = record
      this.dialogs.preview.show = true
    },
    doRun (record) {
      this.$axios({
        url: `${this.moduleBaseUrl}/run/${record.id}`,
        method: 'put',
        data: {}
      }).then(() => {
        this.doSearch()
      })
    },
    doSuspend (record) {
      this.$axios({
        url: `${this.moduleBaseUrl}/suspend/${record.id}`,
        method: 'put',
        data: {}
      }).then(() => {
        this.doSearch()
      })
    },
    doFinish (record) {
      this.$axios({
        url: `${this.moduleBaseUrl}/finish/${record.id}`,
        method: 'put',
        data: {}
      }).then(() => {
        this.doSearch()
      })
    }
  }
}
</script>
<style>

</style>
