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
      v-model:show="actionRecordMoreShow"
      :actions="actionRecordMoreActions"
      cancel-text="取消"
      close-on-click-action
      safe-area-inset-bottom
      @cancel="actionRecordMoreShow=false"
    />

    <van-popup v-model:show="actionSearchShow"
               position="top"
               round
               :style="{  }" >
      <Form ref="form"
            :fields="fields"
            v-model:form="form"
            :enter-submit="true"
            @submit="doSearch"
            :metas="metas"
            :rules="rules">
      </Form>

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
    </van-popup>

    <van-popup
      v-model:show="dialogDetail.show"
      :style="{ width: '100%',height: '100%',paddingTop: 'var(--van-popup-close-icon-margin)' }"
      position="right"
    >
      <van-nav-bar
        :title="dialogDetail.title"
        left-text="取消"
        left-arrow
        @click-left="dialogDetail.show=false"
      />
      <Detail :mode="dialogDetail.mode"
              :record="dialogDetail.record"
              v-if="dialogDetail.show"
              @cancel="handleDetailCancel"
              @submit="handleDetailOk"></Detail>
    </van-popup>
  </div>
</template>
<script>
import ListManageMixin from '@/mixins/ListManageMixin'
import Detail from './components/Detail'
import Form from '@/components/Form'
import Tree from '@/components/Tree'

export default {
  components: {
    Detail,
    Tree,
    Form
  },
  mixins: [ListManageMixin],
  data () {
    return {
      actionMoreActions: [
        {
          name: '新增',
          callback: this.doAdd
        }
      ],

      actionRecordMoreActions: [
        {
          name: '添加子节点',
          callback: this.doAddSub
        }
      ],

      moduleBaseUrl: '/api/sys/resources',

      form: {
        name: '',
        menuKey: '',
        type: null,
        typeDesc: '',
        status: null,
        statusDesc: '',
        url: '',
        permKey: '',
        remark: '',
        icon: ''
      },
      controls: {

      },
      rules: {
        name: [{ required: true, message: '请填写名称' }]

      },
      dialogs: {

      },
      metas: {
        statusList: [{
          value: 0,
          label: '禁用'
        }, {
          value: 1,
          label: '启用'
        }, {
          value: 99,
          label: '删除'
        }],
        menuTypeList: [{
          value: 0,
          label: '菜单'
        }, {
          value: 1,
          label: '权限'
        }],
        metasFields: {
          text: 'label',
          value: 'value',
          children: 'children'
        }
      },
      fields: [
        {
          prop: 'name',
          label: '名称'
        },
        {
          prop: 'menuKey',
          label: '菜单键'
        },
        {
          prop: 'type',
          label: '类型',
          type: 'select',
          options: 'menuTypeList',
          optionsFields: 'metasFields'
        },
        {
          prop: 'status',
          label: '状态',
          type: 'select',
          options: 'statusList',
          optionsFields: 'metasFields'
        },
        {
          prop: 'url',
          label: 'URL'
        },
        {
          prop: 'permKey',
          label: '权限键'
        },
        {
          prop: 'remark',
          label: '备注'
        },
        {
          prop: 'icon',
          label: 'ICON'
        }
      ]
    }
  },
  created () {
    this.$emit('nav', {
      enable: true,
      title: '资源管理',
      rightText: null,
      rightIcon: 'search',
      rightClick: this.expandSearchPanel
    })
    this.$emit('dock', {
      enable: true,
      icon: 'plus',
      click: this.expandMorePanel
    })
  },
  mounted () {

  },
  methods: {
    doReset () {
      this.form = {
        name: '',
        menuKey: '',
        type: null,
        typeDesc: '',
        status: null,
        statusDesc: '',
        url: '',
        permKey: '',
        remark: '',
        icon: ''
      }
    },
    doAddSub (record) {

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
