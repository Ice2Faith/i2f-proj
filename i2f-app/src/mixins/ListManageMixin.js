/**
 * 混入的本质是对vue中的各个部分进行合并或替换
 * 可以简单理解为对data/methods/mounted/props等各部分对象进行Object.assign(mixinPart,componentPart)
 * 因此，如果在组件中同一个部分重复定义了相同的名称的对象，则直接覆盖
 * 举个例子来说，如果methods中，同时存在同名函数，则以component中的为准
 * 这个是好理解的
 * 但是，针对data中的部分，没有进行深度合并，而是简单的第一层合并
 * 所以，如果data中的定义有user.age,则如果在component中也存在user，
 * 此时，user就需要在component中重写age属性，因为这不是第一层
 * 没有进行深层合并
 */
import FormDetailMode from '@/framework/consts/FormDetailMode'
import ListManageMixinData from '@/mixins/ListManageMixinData'

const ListManageMixin = {
  data () {
    return ListManageMixinData()
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

    expandSearchPanel () {
      this.actionSearchShow = true
    },
    expandMorePanel () {
      this.actionMoreShow = true
    },
    onClickUrl (url) {
      if (url && url != '') {
        window.open(url, '_blank')
      }
    },
    doSearch () {
      this.getData(true)
    },
    doLoad () {
      this.getData(false)
    },
    doReset () {

    },
    getData (reset) {
      this.actionSearchShow = false
      this.actionMoreShow = false
      if (reset) {
        this.tablePage.current = 1
        this.tablePage.total = 0
        this.tableData = []
      }
      if (reset) {
        this.queryRefreshing = true
        this.queryFinished = false
      }
      this.queryLoading = true

      this.hookBeforeGetData()
      this.$axios({
        url: `${this.moduleBaseUrl}/page/${this.tablePage.pageSize}/${(this.tablePage.current - 1)}`,
        method: 'get',
        params: this.form
      }).then(({ data }) => {
        const list = data.list
        if (!list || list.length < this.tablePage.pageSize) {
          this.queryFinished = true
        }
        this.tableData = [...this.tableData, ...list]
        this.tablePage.current = (data.index + 1) + 1
        this.tablePage.pageSize = data.size
        this.tablePage.total = data.total
      }).catch(err => {
        this.queryFinished = true
      }).finally(() => {
        if (reset) {
          this.queryRefreshing = false
        }
        this.queryLoading = false
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
    doMore (record) {
      this.actionRecordMoreData = record
      this.actionRecordMoreShow = true
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
      }).then(() => {
        _this.$axios({
          url: `${_this.moduleBaseUrl}/delete/${record.id}`,
          method: 'delete',
          data: {}
        }).then(() => {
          _this.doSearch()
        })
      }).catch(() => {
        _this.$message.noticeInfo('操作已取消')
      })
    }
  }
}

export default ListManageMixin
