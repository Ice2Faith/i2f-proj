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
            v-model="form.name"
            clearable
            name="name"
            label="名称"
            placeholder="名称"
          />
          <van-field
            v-model="form.menuKey"
            clearable
            name="menuKey"
            label="菜单键"
            placeholder="菜单键"
          />
          <van-field
            v-model="form.typeDesc"
            clearable
            is-link
            readonly
            name="type"
            label="类型"
            placeholder="点击选择"
            @click="dialogs.type.show = true"
          />
          <van-popup v-model:show="dialogs.type.show" position="bottom">
            <van-picker
              :columns-field-names="{ text: 'label', value: 'value', children: 'children' }"
              :columns="metas.menuTypeList"
              @confirm="({ selectedOptions }) =>{dialogs.type.show=false;form.type=selectedOptions[0].value;form.typeDesc=selectedOptions[0].label}"
              @cancel="dialogs.type.show = false"
            />
          </van-popup>
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
            v-model="form.url"
            clearable
            name="url"
            label="URL"
            placeholder="URL"
          />
          <van-field
            v-model="form.permKey"
            clearable
            name="permKey"
            label="权限键"
            placeholder="权限键"
          />
          <van-field
            v-model="form.remark"
            clearable
            name="remark"
            label="备注"
            placeholder="备注"
          />
          <van-field
            v-model="form.icon"
            clearable
            name="icon"
            label="ICON"
            placeholder="ICON"
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
        name: [{ required: true, message: '请填写名称' }],

      },
      dialogs: {
        type:{
          show: false
        },
        status:{
          show: false
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
        menuTypeList: [{
          value: 0,
          label: '菜单'
        }, {
          value: 1,
          label: '权限'
        }]
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

  },
  methods: {
    doReset(){
      this.form= {
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
