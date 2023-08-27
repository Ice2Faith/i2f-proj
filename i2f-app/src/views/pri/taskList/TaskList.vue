<template>
  <div>
    <van-pull-refresh v-model="queryRefreshing" @refresh="doSearch">
      <van-list
        v-model:loading="queryLoading"
        :finished="queryFinished"
        finished-text="没有更多了"
        @load="doLoad"
      >
        <van-swipe-cell v-for="(item,index) in tableData"
                        :key="item[tableRowKey]">
          <template #left>
            <van-button square type="primary" text="更多" @click="doMore(item)"/>
          </template>
          <template #right>
            <van-button square type="danger" text="删除" @click="doDelete(item)"/>
            <van-button square type="primary" text="编辑" @click="doEdit(item)"/>
          </template>
          <van-cell :title="item.name">
            <template #right-icon>
              <van-icon name="arrow" class="search-icon" @click="doView(item)" />
            </template>
          </van-cell>
        </van-swipe-cell>
      </van-list>
    </van-pull-refresh>

    <van-action-sheet
      v-model:show="actionMoreShow"
      :actions="actionMoreActions"
      cancel-text="取消"
      close-on-click-action
      safe-area-inset-bottom
      @cancel="actionMoreShow=false"
    />

    <van-popup v-model:show="actionSearchShow"
               position="top"
               round
               :style="{  }" >
      <van-form ref="form" @keydown.enter.native.stop="doSearch">
        <van-cell-group inset style="max-height: 50vh;overflow: auto;margin-right: 0px">
          <van-field
            v-model="formDesc.deadline"
            clearable
            is-link
            readonly
            name="deadline"
            label="截止时间"
            placeholder="截止时间"
            @click="dialogs.deadline.show = true"
          />
          <van-popup v-model:show="dialogs.deadline.show" position="bottom">
            <van-picker-group
              title="查询日期"
              :tabs="['开始日期', '结束日期']"
              @confirm="()=>{form.deadlineBegin=formDesc.deadlineBeginDesc.join('-');form.deadlineEnd=formDesc.deadlineEndDesc.join('-');formDesc.deadline=form.deadlineBegin+' ~ '+form.deadlineEnd;dialogs.deadline.show=false;}"
              @cancel="()=>dialogs.deadline.show=false"
            >
              <van-date-picker
                v-model="formDesc.deadlineBeginDesc"/>
              <van-date-picker
                v-model="formDesc.deadlineEndDesc"/>
            </van-picker-group>
          </van-popup>
          <van-field
            v-model="form.name"
            clearable
            name="name"
            label="名称"
            placeholder="名称"
          />
          <van-field
            v-model="form.statusDesc"
            clearable
            is-link
            readonly
            name="status"
            label="状态"
            placeholder="点击选择"
            @click="dialogs.status.show = true"
          />
          <van-popup v-model:show="dialogs.status.show" position="bottom">
            <van-picker
              :columns-field-names="{ text: 'label', value: 'value', children: 'children' }"
              :columns="metas.statusList"
              @confirm="({ selectedOptions }) =>{dialogs.status.show=false;form.status=selectedOptions[0].value;form.statusDesc=selectedOptions[0].label}"
              @cancel="dialogs.status.show = false"
            />
          </van-popup>
          <van-field
            v-model="form.content"
            clearable
            name="content"
            label="内容"
            placeholder="内容"
          />
          <van-field
            v-model="form.process"
            clearable
            name="process"
            label="处理"
            placeholder="处理"
          />
          <van-field
            v-model="form.remark"
            clearable
            name="remark"
            label="备注"
            placeholder="备注"
          />

        </van-cell-group>
        <van-row style="margin: 16px">
          <van-col span="12">
            <van-button block type="default" size="small" @click="doReset">
              重置
            </van-button>
          </van-col>
          <van-col span="12">
            <van-button block type="primary" size="small" @click="doSearch">
              搜索
            </van-button>
          </van-col>
        </van-row>
      </van-form>
    </van-popup>

  </div>
</template>
<script>
import ListManageMixin from "@/mixins/ListManageMixin";
export default {
  mixins:[ListManageMixin],
  data(){
    return {
      actionMoreActions:[
        {
          name: '新增',
          callback: this.doAdd
        }
      ],

      moduleBaseUrl: '/api/biz/taskList',

      form: {
        name: '',
        status: null,
        statusDesc: '',
        content: '',
        process: '',
        remark: '',
        deadlineBegin: '',
        deadlineEnd: '',
      },
      formDesc:{
        deadline: '',
        deadlineBeginDesc: [],
        deadlineEndDesc:[]
      },
      controls: {

      },
      rules: {

      },
      dialogs: {
        status:{
          show:false
        },
        deadline:{
          show:false
        }
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
    }
  },
  created() {
    this.$emit('nav',{
      enable: true,
      title: '笔记本',
      rightText: null,
      rightIcon: 'search',
      rightClick: this.expandSearchPanel
    })
    this.$emit('dock',{
      enable: true,
      icon: 'plus',
      click: this.expandMorePanel
    })
  },
  mounted () {

  },
  methods: {
    doReset(){
      this.form= {
          name: '',
          status: null,
          statusDesc: '',
          content: '',
          process: '',
          remark: '',
          deadlineBegin: '',
          deadlineEnd: '',
      }
      this.formDesc={
          deadline: '',
          deadlineBeginDesc: [],
          deadlineEndDesc:[]
      }
    }
  }
}
</script>
<style>
.search-icon {
  font-size: 16px;
  line-height: inherit;
}
</style>
