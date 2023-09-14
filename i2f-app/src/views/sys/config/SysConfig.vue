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
          name: '添加子节点',
          callback: this.doAuthResources
        }
      ],

      moduleBaseUrl: '/api/sys/config',

      form: {
        configKey: '',
        name: '',
        modFlag: '',
        modFlagDesc: '',
        delFlag: '',
        delFlagDesc: '',
        sysFlag: '',
        sysFlagDesc: '',
        remark: ''
      },
      controls: {

      },
      rules: {

      },
      dialogs: {

      },
      metas: {
        boolList: [{
          value: 0,
          label: '否'
        }, {
          value: 1,
          label: '是'
        }],
        metasFields: {
          text: 'label',
          value: 'value',
          children: 'children'
        }
      },
      fields: [
        {
          prop: 'configKey',
          label: '配置键'
        },
        {
          prop: 'name',
          label: '名称'
        },
        {
          prop: 'modFlag',
          label: '是否可修改',
          type: 'select',
          options: 'boolList',
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
        },
        {
          prop: 'remark',
          label: '备注'
        }
      ]
    }
  },
  created () {
    this.$emit('nav', {
      enable: true,
      title: '配置管理',
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
        configKey: '',
        name: '',
        modFlag: '',
        modFlagDesc: '',
        delFlag: '',
        delFlagDesc: '',
        sysFlag: '',
        sysFlagDesc: '',
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
