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
        label="截止时间"
        name="deadline">
        <a-range-picker v-model:value="beforeForm.deadline"
                        valueFormat="YYYY-MM-DD"
                        format="YYYY-MM-DD"
                        @change="onDeadlineRangeChange" />
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
        label="内容"
        name="content"
      >
        <a-input v-model:value="form.content" allow-clear/>
      </a-form-item>

      <a-form-item
        label="处理"
        name="process"
      >
        <a-input v-model:value="form.process" allow-clear/>
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
      <template #expandedRowRender="{ record }">
        内容：
        <p style="margin: 0">
          {{ record.content }}
        </p>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'level'">
          <a-rate style="zoom: 0.7" :value="record.level" :count="10" />
        </template>
        <template v-if="column.key === 'value'">
          <a-rate style="zoom: 0.7" :value="record.value" :count="10" />
        </template>
        <template v-if="column.key === 'progress'">
          <a-slider :value="record.progress" :min="0" :max="100"  />
        </template>
        <template v-if="column.key === 'status'">
          <a-button :style="getRecordStatusStyle(record)">{{record.statusDesc}}</a-button>
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
                  <a-menu-item :style="getRecordStatusStyle({status: 0})" @click="doRun(record)">
                    <template #icon>
                      <tool-outlined />
                    </template>
                    运行
                  </a-menu-item>
                  <a-menu-divider />
                  <a-menu-item :style="getRecordStatusStyle({status: 2})" @click="doSuspend(record)">
                    <template #icon>
                      <tool-outlined />
                    </template>
                    挂起
                  </a-menu-item>
                  <a-menu-divider />
                  <a-menu-item :style="getRecordStatusStyle({status: 1})" @click="doFinish(record)">
                    <template #icon>
                      <tool-outlined />
                    </template>
                    结束
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

import Detail from '@/views/pri/taskList/components/Detail'
import FormDetailMode from '@/framework/consts/FormDetailMode'
import ListManageMixin from '@/mixins/ListManageMixin'

export default {
  components: {
    Detail
  },
  mixins: [ListManageMixin],
  data () {
    return {
      moduleBaseUrl: '/api/biz/taskList',

      beforeForm: {
        deadline: []
      },
      form: {
        name: '',
        status: null,
        content: '',
        process: '',
        remark: '',
        deadlineBegin: '',
        deadlineEnd: ''
      },
      rules: {

      },
      dialogs: {

      },
      metas: {
        statusList: [{
          value: 0,
          label: '运行',
          color: '#00cc44'
        }, {
          value: 1,
          label: '结束',
          color: '#444444'
        }, {
          value: 2,
          label: '挂起',
          color: '#ff7700'
        }]
      },
      tableColumns: [
        {
          title: '名称',
          dataIndex: 'name'
        },
        {
          title: '等级',
          dataIndex: 'level',
          key: 'level'
        },
        {
          title: '价值',
          dataIndex: 'value',
          key: 'value'
        },
        {
          title: '进度',
          dataIndex: 'progress',
          key: 'progress'
        },
        {
          title: '处理',
          dataIndex: 'process'
        },
        {
          title: '截止日期',
          dataIndex: 'deadline'
        },
        {
          title: '状态',
          dataIndex: 'statusDesc',
          key: 'status'
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

    onDeadlineRangeChange () {
      const range = this.beforeForm.deadline
      if (range && range.length == 2) {
        this.form.deadlineBegin = range[0]
        this.form.deadlineEnd = range[1]
      } else {
        this.form.deadlineBegin = ''
        this.form.deadlineEnd = ''
      }
    },
    getRecordStatusStyle (record) {
      for (const key in this.metas.statusList) {
        const item = this.metas.statusList[key]
        if (item.value == record.status) {
          return {
            backgroundColor: item.color,
            border: `solid 1px ${item.color}`,
            color: 'white'
          }
        }
      }
      return {}
    },
    doImport () {

    },
    doExport () {

    },
    doRun (record) {
      this.$axios({
        url: `${this.metas.baseUrl}/run/${record.id}`,
        method: 'put',
        data: {}
      }).then(() => {
        this.doSearch()
      })
    },
    doSuspend (record) {
      this.$axios({
        url: `${this.metas.baseUrl}/suspend/${record.id}`,
        method: 'put',
        data: {}
      }).then(() => {
        this.doSearch()
      })
    },
    doFinish (record) {
      this.$axios({
        url: `${this.metas.baseUrl}/finish/${record.id}`,
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
