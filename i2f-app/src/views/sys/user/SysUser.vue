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
          <van-cell :title="item.realname">
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
      <h4>
        角色授权
      </h4>
      <tree
        ref="roleTree"
        :checkable="true"
        :multiple="true"
        :toolbox="false"
        v-model:checked-keys="dialogs.auth.roleKeys"
        :tree="metas.roleList"
        :fields="metas.roleTreeFields"
      >
      </tree>

      <h4>
        部门授权
      </h4>
      <tree
        ref="deptTree"
        :checkable="true"
        :multiple="true"
        :toolbox="true"
        v-model:checked-keys="dialogs.auth.deptKeys"
        :tree="metas.deptTreeData"
        :fields="metas.deptTreeFields"
      >
        <template #content="{node}">
          {{node.text}}
          <van-button
            v-if="node.checked"
            plain
            type="primary"
            size="mini"
            @click="doAuthDeptRoles(node.key)">
            授权
          </van-button>
        </template>
      </tree>
    </van-popup>

    <van-popup
      v-model:show="dialogs.deptRole.show"
      :style="{ width: '100%',height: '100%',paddingTop: 'var(--van-popup-close-icon-margin)' }"
      position="right"
    >
      <van-nav-bar
        :title="dialogs.deptRole.title"
        left-text="取消"
        right-text="提交"
        left-arrow
        @click-left="dialogs.deptRole.show=false"
        @click-right="submitAuthDeptRole"
      />

      <tree
        ref="deptRoleTree"
        :checkable="true"
        :multiple="true"
        :toolbox="true"
        v-model:checked-keys="dialogs.deptRole.roleKeys"
        :tree="dialogs.deptRole.roleList"
        :fields="dialogs.deptRole.deptRoleFields"
      >
      </tree>
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
          callback: this.doAuthUser
        }
      ],

      moduleBaseUrl: '/api/sys/user',

      form: {
        username: '',
        realname: '',
        phone: '',
        email: '',
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
          title: '用户授权',
          show: false,
          record: {},
          roleKeys: [],
          deptKeys: []
        },
        deptRole: {
          title: '部门角色授权',
          show: false,
          deptId: null,
          roleKeys: [],
          deptRoleFields: {
            key: 'id',
            text: 'roleName',
            children: 'children'
          },
          roleList: []
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
        roleTreeFields: {
          key: 'id',
          text: 'roleName',
          children: 'children'
        },
        roleList: [],
        deptTreeFields: {
          key: 'id',
          text: 'name',
          children: 'children'
        },
        deptTreeData: [],
        metasFields: {
          text: 'label',
          value: 'value',
          children: 'children'
        }
      },
      fields: [
        {
          prop: 'username',
          label: '登录用户名'
        },
        {
          prop: 'realname',
          label: '用户名'
        },
        {
          prop: 'phone',
          label: '电话号码',
          type: 'phone'
        },
        {
          prop: 'email',
          label: '电子邮箱',
          type: 'email'
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
      title: '用户管理',
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
  methods: {
    hookAfterMounted () {
      this.loadDeptTreeData()
      this.loadRoleListData()
    },
    loadDeptTreeData () {
      this.$axios({
        url: '/api/sys/dept/tree',
        method: 'get'
      }).then(({ data }) => {
        this.metas.deptTreeData = data
      })
    },
    loadRoleListData () {
      this.$axios({
        url: '/api/sys/role/list',
        method: 'get'
      }).then(({ data }) => {
        data.map(item => {
          item.value = item.id
          item.label = item.roleName
        })
        this.metas.roleList = data
      })
    },
    doReset () {
      this.form = {
        username: '',
        realname: '',
        phone: '',
        email: '',
        status: null,
        statusDesc: '',
        delFlag: null,
        delFlagDesc: '',
        sysFlag: null,
        sysFlagDesc: ''
      }
    },
    onAuthCancel () {
      this.dialogs.auth.show = false
    },
    onAuthSubmit () {
      Promise.all([
        this.$axios({
          url: `${this.moduleBaseUrl}/role/update/${this.dialogs.auth.record.id}`,
          method: 'put',
          data: this.dialogs.auth.roleKeys
        }),
        this.$axios({
          url: `${this.moduleBaseUrl}/dept/update/${this.dialogs.auth.record.id}`,
          method: 'put',
          data: this.dialogs.auth.deptKeys
        })
      ]).then(arr => {
        this.dialogs.auth.show = false
      })
    },
    doAuthUser () {
      this.dialogs.auth.record = this.actionRecordMoreData
      Promise.all([
        this.$axios({
          url: `${this.moduleBaseUrl}/role/ids/${this.dialogs.auth.record.id}`,
          method: 'get'
        }),
        this.$axios({
          url: `${this.moduleBaseUrl}/dept/ids/${this.dialogs.auth.record.id}`,
          method: 'get'
        })
      ]
      ).then((arr) => {
        this.dialogs.auth.roleKeys = arr[0].data
        this.dialogs.auth.deptKeys = arr[1].data
        this.dialogs.auth.show = true
      })
    },
    doAuthDeptRoles (deptId) {
      this.dialogs.deptRole.deptId = deptId
      Promise.all([
        this.$axios({
          url: '/api/sys/dept/role/list',
          method: 'get',
          params: {
            deptId: deptId
          }
        }),
        this.$axios({
          url: `${this.moduleBaseUrl}/dept/role/ids/${this.dialogs.auth.record.id}/${this.dialogs.deptRole.deptId}`,
          method: 'get'
        })
      ]
      ).then((arr) => {
        if (!arr[0].data || arr[0].data.length == 0) {
          this.$message.noticeWarning('该该部门无角色配置')
          return
        }
        this.dialogs.deptRole.roleList = arr[0].data
        this.dialogs.deptRole.roleKeys = arr[1].data
        this.dialogs.deptRole.show = true
      })
    },
    submitAuthDeptRole () {
      this.$axios({
        url: `${this.moduleBaseUrl}/dept/role/update/${this.dialogs.auth.record.id}/${this.dialogs.deptRole.deptId}`,
        method: 'put',
        data: this.dialogs.deptRole.roleKeys
      }).then(({ data }) => {
        this.dialogs.deptRole.show = false
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
