<template>
  <div>
    <a-row>
      <a-col :span="hasTimeline()?16:24">
        <a-form
          ref="form"
          :label-col="{ span: 4 }"
          :model="form"
          :wrapper-col="{ span: 20 }"
          autocomplete="off"
          name="basic"
        >
          <a-form-item
            :rules="rules.name"
            label="名称"
            name="name"
          >
            <a-input v-model:value="form.name"/>
          </a-form-item>

          <a-form-item
            :rules="rules.level"
            label="等级"
            name="level"
          >
            <a-rate v-model:value="form.level" :count="10" />
          </a-form-item>

          <a-form-item
            :rules="rules.value"
            label="价值"
            name="value"
          >
            <a-rate v-model:value="form.value" :count="10" />
          </a-form-item>

          <a-form-item
            :rules="rules.progress"
            label="进度"
            name="progress"
          >
            <a-slider v-model:value="form.progress" :min="0" :max="100"  />
          </a-form-item>

          <a-form-item
            :rules="rules.process"
            label="处理"
            name="process"
          >
            <a-input v-model:value="form.process"/>
          </a-form-item>

          <a-form-item
            :rules="rules.deadline"
            label="截止日期"
            name="deadline"
          >
            <a-date-picker v-model:value="form.deadline"
                           show-time
                           valueFormat="YYYY-MM-DD HH:mm:ss"
                           format="YYYY-MM-DD HH:mm:ss"
                           placeholder="请选择"   />

          </a-form-item>

          <a-form-item
            :rules="rules.status"
            label="状态"
            name="status"
          >
            <a-select
              v-model:value="form.status"
              show-search
              placeholder="请选择"
              :options="metas.statusList"
              :filter-option="filterOption"
            >
            </a-select>
          </a-form-item>

          <a-form-item
            :rules="rules.remark"
            label="备注"
            name="remark"
          >
            <a-input v-model:value="form.remark"/>
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
            :rules="rules.createTime"
            label="创建日期"
            name="createTime"
          >
            <a-input  disabled v-model:value="form.createTime"/>
          </a-form-item>

          <a-form-item
            :rules="rules.content"
            label="内容"
            name="content"
          >
            <a-textarea v-model:value="form.content"
                        :auto-size="{ minRows: 3, maxRows: 10 }"/>
          </a-form-item>

        </a-form>
      </a-col>
      <a-divider type="vertical"></a-divider>
      <a-col v-if="hasTimeline()" :span="7" style="max-height: 70vh;overflow-y: auto;padding-top: 12px;">
        <a-timeline>
          <a-timeline-item v-for="(item,index) in historyList"
                           :color="getRecordStatusColor(item)">
            <h4>
              <span style="font-weight: bold">{{ item.progress }}%</span> {{ item.process }}
            </h4>
            <div style="zoom: 0.6">
              <a-rate :value="item.level" :count="10"/>
              <a-rate :value="item.value" :count="10"/>
              <a-slider :value="item.progress" :min="0" :max="100"/>
            </div>
            <p>
              <a-popover title="内容" trigger="click">
                <template #content>
                    备注：{{item.remark}}<br/>
                    截止时间：{{item.deadline}}<br/>
                </template>
                  <a-button type="link">{{ item.createTime }}</a-button>
                </a-popover>
            </p>
          </a-timeline-item>
        </a-timeline>
      </a-col>
    </a-row>
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
      moduleBaseUrl: '/api/biz/taskList',

      form: {
        name: '',
        content: '',
        level: 5,
        value: 5,
        progress: 0,
        process: '',
        deadline: '',
        status: 0,
        remark: '',
        updateTime: '',
        createTime: ''
      },
      rules: {
        name: [{ required: true, message: '请输入名称!' }]
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
      historyList: []
    }
  },
  methods: {
    hookAfterMounted () {
      this.loadDetailHistory()
    },
    hasTimeline () {
      return this.mode != FormDetailMode.ADD()
    },
    getRecordStatusColor (record) {
      for (const key in this.metas.statusList) {
        const item = this.metas.statusList[key]
        if (item.value == record.status) {
          return item.color
        }
      }
      return 'black'
    },
    loadDetailHistory () {
      if (!this.hasTimeline()) {
        return
      }
      this.$axios({
        url: `${this.moduleBaseUrl}/history/${this.record.id}`,
        method: 'get'
      }).then(({ data }) => {
        this.historyList = data
      })
    }
  }
}
</script>
<style>

</style>
