<template>
  <div>
    <a-form
      ref="form"
      :label-col="{ span: 4 }"
      :model="form"
      :wrapper-col="{ span: 20 }"
      autocomplete="off"
      layout="inline"
      name="basic"
      @keyup.enter="doSearch"
    >
      <a-form-item
        label="登录用户名"
        name="username"
      >
        <a-input v-model:value="form.username" allow-clear/>
      </a-form-item>

      <a-form-item
        label="用户名"
        name="realname"
      >
        <a-input v-model:value="form.realname" allow-clear/>
      </a-form-item>

      <a-form-item
        label="电话号码"
        name="phone"
      >
        <a-input v-model:value="form.phone" allow-clear/>
      </a-form-item>

      <a-form-item
        label="电子邮箱"
        name="email"
      >
        <a-input v-model:value="form.email" allow-clear/>
      </a-form-item>

      <a-form-item
        label="状态"
        name="status"
      >
        <a-select
          v-model:value="form.status"
          show-search
          allow-clear
          placeholder="请选择"
          :options="metas.statusList"
          :filter-option="filterOption"
        >
        </a-select>
      </a-form-item>

      <a-form-item
        label="是否可删除"
        name="delFlag"
      >
        <a-select
          v-model:value="form.delFlag"
          show-search
          allow-clear
          placeholder="请选择"
          :options="metas.boolList"
          :filter-option="filterOption"
        >
        </a-select>
      </a-form-item>

      <a-form-item
        label="是否系统"
        name="sysFlag"
      >
        <a-select
          v-model:value="form.sysFlag"
          show-search
          allow-clear
          placeholder="请选择"
          :options="metas.boolList"
          :filter-option="filterOption"
        >
        </a-select>
      </a-form-item>

      <a-form-item :wrapper-col="{ offset: 0, span: 24 }">
        <a-row :gutter="20" justify="center" type="flex">
          <a-col>
            <a-spin :spinning="queryLoading">
              <a-button type="primary" @click="doSearch">
                <template #icon>
                  <SearchOutlined/>
                </template>
                搜索
              </a-button>
            </a-spin>
          </a-col>
          <a-col>
            <a-button @click="doReset">
              <template #icon>
                <clear-outlined/>
              </template>
              重置
            </a-button>
          </a-col>
        </a-row>
      </a-form-item>
    </a-form>

    <a-row style="margin: 10px;">
      <a-col :span="12">
        <a-row :gutter="20">
          <a-col>
            <a-button type="primary" @click="doImport">
              <template #icon>
                <import-outlined/>
              </template>
              导入
            </a-button>
          </a-col>
          <a-col>
            <a-button type="primary" @click="doExport">
              <template #icon>
                <export-outlined/>
              </template>
              导出
            </a-button>
          </a-col>
        </a-row>
      </a-col>
      <a-col :span="12">
        <a-row :gutter="20" justify="end">
          <a-col>
            <a-button type="primary" @click="doAdd">
              <template #icon>
                <plus-outlined/>
              </template>
              新增
            </a-button>
          </a-col>
        </a-row>
      </a-col>
    </a-row>
    <a-table
      :columns="tableColumns"
      :data-source="tableData"
      :loading="queryLoading"
      :pagination="tablePage"
      :scroll="{ x: 1500 }"
      bordered
      :row-key="record => record[tableRowKey]"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a-row :gutter="5">
            <a-col>
              <a-button @click="doView(record)">
                <template #icon>
                  <eye-outlined/>
                </template>
                详情
              </a-button>
            </a-col>

            <a-dropdown>
              <a-button>
                更多
                <more-outlined/>
              </a-button>
              <template #overlay>
                <a-menu>
                  <a-menu-item style="background-color: dodgerblue;color:white" @click="doEdit(record)">
                    <template #icon>
                      <edit-outlined/>
                    </template>
                    编辑
                  </a-menu-item>
                  <a-menu-divider/>
                  <a-menu-item style="background-color: lightseagreen;color: white" @click="doAuthUser(record)">
                    <template #icon>
                      <safety-certificate-outlined />
                    </template>
                    授权
                  </a-menu-item>
                  <a-menu-divider/>
                  <a-menu-item style="background-color: orangered;color: white" @click="doDelete(record)">
                    <template #icon>
                      <delete-outlined/>
                    </template>
                    删除
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </a-row>
        </template>
      </template>
    </a-table>

    <a-modal
      v-if="dialogDetail.show"
      v-model:visible="dialogDetail.show"
      :title="dialogDetail.title"
      :footer="null"
      width="1200px"
    >
      <Detail :mode="dialogDetail.mode"
              :record="dialogDetail.record"
              @cancel="handleDetailCancel"
              @submit="handleDetailOk"></Detail>
    </a-modal>


    <a-drawer
      v-model:visible="dialogs.auth.show"
      :title="dialogs.auth.title"
      width="30%"
      placement="right"
    >
      <template #extra>
        <a-button style="margin-right: 8px" @click="onAuthCancel">取消</a-button>
        <a-button type="primary" @click="onAuthSubmit">提交</a-button>
      </template>

      <a-form>
        <a-form-item label="担任角色">
          <a-select
            v-model:value="dialogs.auth.roleKeys"
            show-search
            mode="multiple"
            allow-clear
            placeholder="请选择"
            :options="metas.roleList"
            :filter-option="filterOption"
          >
          </a-select>
        </a-form-item>
        <a-form-item label="归属部门">
          <a-tree
            v-model:checkedKeys="dialogs.auth.deptKeys"
            :fieldNames="{children:'children', title:'name', key:'id' }"
            checkable
            :tree-data="metas.deptTreeData"
          >
            <template #title="{id,name}">
              <div style="border-bottom: solid 1px #aaa">
                <span>
                  {{name}}
                </span>
                <span style="margin-left: 20px" v-if="dialogs.auth.deptKeys.indexOf(id) >= 0">
                  <a-button type="link" @click="doAuthDeptRoles(id)">
                    <template #icon>
                      <carry-out-outlined />
                    </template>
                    担任角色
                  </a-button>
                </span>
              </div>
            </template>
          </a-tree>
        </a-form-item>
      </a-form>

      <a-modal
        v-if="dialogs.deptRole.show"
        v-model:visible="dialogs.deptRole.show"
        :title="dialogs.deptRole.title"
        :footer="null"
        width="800px"
      >
        <a-form>
          <a-form-item label="担任角色">
            <a-select
              v-model:value="dialogs.deptRole.roleKeys"
              show-search
              mode="multiple"
              allow-clear
              placeholder="请选择"
              :options="dialogs.deptRole.roleList"
              :filter-option="filterOption"
            >
            </a-select>
          </a-form-item>
          <a-form-item>
            <a-row justify="center" type="flex">
              <a-button size="large" type="primary" @click="submitAuthDeptRole">提交</a-button>
            </a-row>
          </a-form-item>
        </a-form>
      </a-modal>
    </a-drawer>
  </div>
</template>
<script>

import Detail from "./components/Detail";

import ListManageMixin from "@/mixins/ListManageMixin";

export default {
  components: {
    Detail
  },
  mixins: [ListManageMixin],
  data() {
    return {
      moduleBaseUrl: '/api/sys/user',

      form: {
        username: '',
        realname: '',
        phone: '',
        email: '',
        delFlag: '',
        sysFlag: '',
        status: ''
      },
      rules: {},
      dialogs: {
        auth:{
          title: '用户授权',
          show: false,
          record:{},
          roleKeys: [],
          deptKeys:[],
        },
        deptRole:{
          title: '部门角色授权',
          show: false,
          deptId: null,
          roleKeys:[],
          roleList:[]
        }
      },
      metas: {
        statusList:[{
          value: 0,
          label: '禁用',
        }, {
          value: 1,
          label: '启用',
        }, {
          value: 99,
          label: '删除',
        }],
        boolList:[{
          value: 0,
          label: '否',
        }, {
          value: 1,
          label: '是',
        }],
        roleList:[],
        deptTreeData:[]
      },
      tableColumns: [
        {
          title: '登录用户名',
          dataIndex: 'username',
        },
        {
          title: '用户名',
          dataIndex: 'realname',
        },
        {
          title: '电话号码',
          dataIndex: 'phone',
        },
        {
          title: '电子邮箱',
          dataIndex: 'email',
        },
        {
          title: '注册时间',
          dataIndex: 'regDate',
        },
        {
          title: '状态',
          dataIndex: 'statusDesc',
        },
        {
          title: '是否可删除',
          dataIndex: 'delFlagDesc',
        },
        {
          title: '是否系统',
          dataIndex: 'sysFlagDesc',
        },
        {
          title: '更新日期',
          dataIndex: 'updateTime',
        },
        {
          title: '更新人',
          dataIndex: 'updateUser',
        },
        {
          title: '创建日期',
          dataIndex: 'createTime',
        },
        {
          title: '创建人',
          dataIndex: 'createUser',
        },
        {
          title: '操作',
          key: 'action',
          fixed: 'right',
          width: '200px',
          align: 'center'
        },
      ]
    }
  },

  methods: {
    hookAfterMounted(){
      this.loadDeptTreeData()
      this.loadRoleListData()
    },
    loadDeptTreeData(){
      this.$axios({
        url: `/api/sys/dept/tree`,
        method: 'get'
      }).then(({data})=>{
        this.metas.deptTreeData=data
      })
    },
    loadRoleListData(){
      this.$axios({
        url: `/api/sys/role/list`,
        method: 'get'
      }).then(({data})=>{
        data.map(item=>{
          item.value=item.id
          item.label=item.roleName
        })
        this.metas.roleList=data
      })
    },
    doImport() {

    },
    doExport() {

    },
    onAuthCancel(){
      this.dialogs.auth.show=false

    },
    onAuthSubmit(){
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
        }),
      ]).then(arr=>{
        this.dialogs.auth.show=false
      })
    },
    doAuthUser(record){
      this.dialogs.auth.record=record
      Promise.all([
          this.$axios({
          url: `${this.moduleBaseUrl}/role/ids/${this.dialogs.auth.record.id}`,
          method: 'get',
        }),
        this.$axios({
          url: `${this.moduleBaseUrl}/dept/ids/${this.dialogs.auth.record.id}`,
          method: 'get',
        }),
        ]
      ).then((arr)=>{
        this.dialogs.auth.roleKeys=arr[0].data
        this.dialogs.auth.deptKeys=arr[1].data
        this.dialogs.auth.show=true
      })
    },
    doAuthDeptRoles(deptId){
      this.dialogs.deptRole.deptId=deptId
      Promise.all([
          this.$axios({
            url: `/api/sys/dept/role/list`,
            method: 'get',
            params:{
              deptId: deptId
            }
          }),
          this.$axios({
            url: `${this.moduleBaseUrl}/dept/role/ids/${this.dialogs.auth.record.id}/${this.dialogs.deptRole.deptId}`,
            method: 'get',
          }),
        ]
      ).then((arr)=>{
        let roleList=arr[0].data;
        roleList.map(item=>{
          item.value=item.id
          item.label=item.roleName
        })
        this.dialogs.deptRole.roleList=roleList
        this.dialogs.deptRole.roleKeys=arr[1].data
        this.dialogs.deptRole.show=true
      })

    },
    submitAuthDeptRole(){
      this.$axios({
        url: `${this.moduleBaseUrl}/dept/role/update/${this.dialogs.auth.record.id}/${this.dialogs.deptRole.deptId}`,
        method: 'put',
        data: this.dialogs.deptRole.roleKeys
      }).then(({data})=>{
        this.dialogs.deptRole.show=false
      })
    }
  }
}
</script>
<style>

</style>
