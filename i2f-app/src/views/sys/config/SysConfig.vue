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
            v-model="form.configKey"
            clearable
            name="configKey"
            label="配置键"
            placeholder="配置键"
          />
          <van-field
            v-model="form.name"
            clearable
            name="name"
            label="名称"
            placeholder="名称"
          />
          <van-field
            v-model="form.modFlagDesc"
            clearable
            is-link
            readonly
            name="type"
            label="是否可修改"
            placeholder="点击选择"
            @click="dialogs.modFlag.show = true"
          />
          <van-popup v-model:show="dialogs.modFlag.show" position="bottom">
            <van-picker
              :columns-field-names="{ text: 'label', value: 'value', children: 'children' }"
              :columns="metas.boolList"
              @confirm="({ selectedOptions }) =>{dialogs.modFlag.show=false;form.modFlag=selectedOptions[0].value;form.modFlagDesc=selectedOptions[0].label}"
              @cancel="dialogs.modFlag.show = false"
            />
          </van-popup>
          <van-field
            v-model="form.delFlagDesc"
            clearable
            is-link
            readonly
            name="status"
            label="是否可删除"
            placeholder="点击选择"
            @click="dialogs.delFlag.show = true"
          />
          <van-popup v-model:show="dialogs.delFlag.show" position="bottom">
            <van-picker
              :columns-field-names="{ text: 'label', value: 'value', children: 'children' }"
              :columns="metas.boolList"
              @confirm="({ selectedOptions }) =>{dialogs.delFlag.show=false;form.delFlag=selectedOptions[0].value;form.delFlagDesc=selectedOptions[0].label}"
              @cancel="dialogs.delFlag.show = false"
            />
          </van-popup>
          <van-field
            v-model="form.sysFlagDesc"
            clearable
            is-link
            readonly
            name="status"
            label="是否系统"
            placeholder="点击选择"
            @click="dialogs.sysFlag.show = true"
          />
          <van-popup v-model:show="dialogs.sysFlag.show" position="bottom">
            <van-picker
              :columns-field-names="{ text: 'label', value: 'value', children: 'children' }"
              :columns="metas.boolList"
              @confirm="({ selectedOptions }) =>{dialogs.sysFlag.show=false;form.sysFlag=selectedOptions[0].value;form.sysFlagDesc=selectedOptions[0].label}"
              @cancel="dialogs.sysFlag.show = false"
            />
          </van-popup>
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
        modFlag:{
          show:false,
        },
        delFlag:{
          show:false,
        },
        sysFlag:{
          show:false,
        },
      },
      metas: {
        boolList: [{
          value: 0,
          label: '否'
        }, {
          value: 1,
          label: '是'
        }]
      },
    }
  },
  created() {
    this.$emit('nav',{
      enable: true,
      title: '配置管理',
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
