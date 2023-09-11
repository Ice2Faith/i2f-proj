import FormDetailMode from '@/framework/consts/FormDetailMode'

function ListManageMixinData () {
  return {
    queryRefreshing: false,
    queryLoading: false,
    queryFinished: true,

    actionMoreShow: false,
    actionMoreRecord: {},
    actionMoreActions: [],

    actionSearchShow: false,


    dialogDetail: {
      title: '新增',
      show: false,
      mode: FormDetailMode.ADD(),
      record: {}
    },
    moduleBaseUrl: '/api/',

    tablePage: {
      current: 1,
      pageSize: 20,
      total: 0
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
}

export default ListManageMixinData
