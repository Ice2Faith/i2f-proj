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
          <van-cell :title="item.title">
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
            v-model="form.title"
            clearable
            name="title"
            label="标题"
            placeholder="标题"
          />
          <van-field
            v-model="form.keywords"
            clearable
            name="keywords"
            label="关键字"
            placeholder="关键字"
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

      moduleBaseUrl: '/api/biz/noteBook',

      form: {
        title: '',
        keywords: '',
        remark: ''
      },
      controls: {

      },
      rules: {

      },
      dialogs: {

      },
      metas: {

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
          title: '',
          keywords: '',
          remark: ''
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
