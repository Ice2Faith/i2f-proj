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

    <van-action-sheet
      v-model:show="actionSearchShow"
      title="搜索框"
      safe-area-inset-bottom
    >
      在此处进行搜索
    </van-action-sheet>

  </div>
</template>
<script>
import FormDetailMode from "@/framework/consts/FormDetailMode";

export default {
  data(){
    return {
      queryRefreshing: false,
      queryLoading: false,
      queryFinished: true,

      actionMoreShow: false,
      actionMoreActions:[
        {
          name: '新增',
          callback: this.doAdd
        }
      ],

      actionSearchShow: false,


      dialogDetail: {
        title: '新增',
        show: false,
        mode: FormDetailMode.ADD(),
        record: {}
      },
      moduleBaseUrl: '/api/sys/resources',

      tablePage: {
        current: 1,
        pageSize: 20,
        total: 2000
      },
      tableRowKey: 'id',
      tableData: [],
      form: {

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
      title: '资源管理',
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
    this.hookBeforeMounted()
    this.doSearch()
    this.hookAfterMounted()
  },
  methods: {
    hookBeforeMounted () {

    },
    hookAfterMounted () {

    },
    hookBeforeGetData () {

    },
    expandSearchPanel(){
      this.actionSearchShow=true
    },
    expandMorePanel(){
      this.actionMoreShow=true
    },
    onClickUrl (url) {
      if (url && url != '') {
        window.open(url, '_blank')
      }
    },
    doSearch () {
      this.getData(true)
    },
    doLoad(){
      this.getData(false)
    },
    doReset () {

    },
    getData (reset) {
      if (reset) {
        this.tablePage.current = 1
        this.tablePage.total=0
        this.tableData=[]
      }
      if(reset){
        this.queryRefreshing=true
        this.queryFinished=false
      }
        this.queryLoading=true

      this.hookBeforeGetData()
      this.$axios({
        url: `${this.moduleBaseUrl}/page/${this.tablePage.pageSize}/${(this.tablePage.current - 1)}`,
        method: 'get',
        params: this.form
      }).then(({ data }) => {
        let list=data.list
        if(!list || list.length<this.tablePage.pageSize){
          this.queryFinished=true
        }
        this.tableData = [...this.tableData,...list]
        this.tablePage.current = (data.index + 1)+1
        this.tablePage.pageSize = data.size
        this.tablePage.total = data.total

      }).catch(err=>{
        this.queryFinished=true
      }).finally(() => {
        if(reset){
          this.queryRefreshing=false
        }
          this.queryLoading=false

      })
    },
    doAdd () {
      this.$message.noticeInfo('新增')
      this.dialogDetail.mode = FormDetailMode.ADD()
      this.dialogDetail.title = '新增'
      this.dialogDetail.show = true
    },
    handleDetailOk () {
      this.dialogDetail.show = false
      this.doSearch()
    },
    handleDetailCancel () {
      this.dialogDetail.show = false
    },
    doImport () {

    },
    doExport () {

    },
    doMore(record){

    },
    doView (record) {
      this.$message.noticeInfo('查看')
      this.dialogDetail.mode = FormDetailMode.VIEW()
      this.dialogDetail.title = '详情'
      this.dialogDetail.record = record
      this.dialogDetail.show = true
    },
    doEdit (record) {
      this.$message.noticeInfo('编辑')
      this.dialogDetail.mode = FormDetailMode.EDIT()
      this.dialogDetail.title = '编辑'
      this.dialogDetail.record = record
      this.dialogDetail.show = true
    },
    doDelete (record) {
      const _this = this
      this.$confirm({
        title: '删除确认框',
        message: '您真的要要删除此项吗?'
      }).then(()=>{
        _this.$axios({
          url: `${_this.moduleBaseUrl}/delete/${record.id}`,
          method: 'delete',
          data: {}
        }).then(() => {
          _this.doSearch()
        })
      }).catch(()=>{
        _this.$message.noticeInfo('操作已取消')
      })
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
