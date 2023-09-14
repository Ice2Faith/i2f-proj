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
          <van-cell :title="item.roleName">
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
      v-model:show="dialogs.auth.show"
      :style="{ width: '100%',height: '100%',paddingTop: 'var(--van-popup-close-icon-margin)' }"
      position="right"
    >
      <van-nav-bar
        :title="dialogs.auth.title"
        left-text="取消"
        right-text="提交"
        left-arrow
        @click-left="dialogs.auth.show=false"
        @click-right="onAuthSubmit"
      />

      <tree
        ref="resourcesTree"
        :checkable="true"
        :multiple="true"
        :toolbox="true"
        v-model:checked-keys="dialogs.auth.checkedKeys"
        :tree="metas.resourceTreeData"
        :fields="metas.resourcesTreeFields"
      />
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
import Tree from '@/components/Tree'
import Detail from './components/Detail'
import Form from '@/components/Form'

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
          name: '授权',
          callback: this.doAuthResources
        }
      ],

      moduleBaseUrl: '/api/sys/role',

      form: {
        roleKey: '',
        roleName: '',
        status: null,
        statusDesc: '',
        delFlag: null,
        delFlagDesc: '',
        sysFlag: null,
        sysFlagDesc: ''
      },
      controls: {

      },
      rules: {

      },
      dialogs: {
        auth: {
          title: '角色授权',
          show: false,
          record: {},
          checkedKeys: []
        }
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
        boolList: [{
          value: 0,
          label: '否'
        }, {
          value: 1,
          label: '是'
        }],
        resourcesTreeFields: {
          key: 'id',
          text: 'name',
          children: 'children'
        },
        resourceTreeData: [],
        metasFields: {
          text: 'label',
          value: 'value',
          children: 'children'
        }
      },
      fields: [
        {
          prop: 'roleKey',
          label: '角色键'
        },
        {
          prop: 'roleName',
          label: '角色名称'
        },
        {
          prop: 'status',
          label: '状态',
          type: 'select',
          options: 'statusList',
          optionsFields: 'metasFields'
        },
        {
          prop: 'delFlag',
          label: '是否可删除',
          type: 'select',
          options: 'boolList',
          optionsFields: 'metasFields'
        },
        {
          prop: 'sysFlag',
          label: '是否系统',
          type: 'select',
          options: 'boolList',
          optionsFields: 'metasFields'
        }
      ]
    }
  },
  created () {
    this.$emit('nav', {
      enable: true,
      title: '角色管理',
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
        roleKey: '',
        roleName: '',
        status: null,
        statusDesc: '',
        delFlag: null,
        delFlagDesc: '',
        sysFlag: null,
        sysFlagDesc: ''
      }
    },
    hookAfterMounted () {
      this.loadResourcesTreeData()
    },
    loadResourcesTreeData () {
      this.$axios({
        url: '/api/sys/resources/tree',
        method: 'get'
      }).then(({ data }) => {
        this.metas.resourceTreeData = data
      })
    },
    onAuthSubmit () {
      this.$axios({
        url: `${this.moduleBaseUrl}/resources/update/${this.dialogs.auth.record.id}`,
        method: 'put',
        data: this.dialogs.auth.checkedKeys
      }).then((data) => {
        this.dialogs.auth.show = false
      })
    },
    doAuthResources () {
      this.dialogs.auth.record = this.actionRecordMoreData
      this.$axios({
        url: `${this.moduleBaseUrl}/resources/ids/${this.dialogs.auth.record.id}`,
        method: 'get'
      }).then(({ data }) => {
        this.dialogs.auth.checkedKeys = data
        this.dialogs.auth.show = true
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
