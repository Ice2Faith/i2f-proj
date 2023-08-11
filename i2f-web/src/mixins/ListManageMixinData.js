import FormDetailMode from '@/framework/consts/FormDetailMode'

function ListManageMixinData () {
  return {
    queryLoading: false,
    dialogDetail: {
      title: '新增',
      show: false,
      mode: FormDetailMode.ADD(),
      record: {}
    },
    moduleBaseUrl: '/api',
    tablePage: {
      current: 1,
      pageSize: 20,
      total: 2000,
      defaultPageSize: 20,
      showQuickJumper: true,
      showSizeChanger: true,
      // showTotal: true,
      pageSizeOptions: [10, 20, 50, 100, 300]
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
    tableColumns: []
  }
}

export default ListManageMixinData
