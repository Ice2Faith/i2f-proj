<template>
  <div>
    <a-card>
      <a-row>
        <a-col :span="12">
          <a-statistic title="部门键" :value="dept.deptKey"
                       style="margin-right: 50px" />
        </a-col>
        <a-col :span="12">
          <a-statistic title="部门名称" :value="dept.name" />
        </a-col>
      </a-row>
    </a-card>
    <a-tabs v-model:activeKey="activeKey">
      <a-tab-pane key="role">
        <template #tab>
        <span>
          <audit-outlined />
          角色管理
        </span>
        </template>
        <SysDeptRole :dept="dept"/>
      </a-tab-pane>
      <a-tab-pane key="resources">
        <template #tab>
        <span>
          <appstore-outlined />
          资源管理
        </span>
        </template>
        <SysDeptResources :dept="dept"/>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>
<script>

import SysDeptRole from '@/views/sys/dept/components/role/SysDeptRole'
import SysDeptResources from '@/views/sys/dept/components/resources/SysDeptResources'
export default {
  components: {
    SysDeptResources,
    SysDeptRole
  },
  props: {
    dept: {
      type: Object,
      default: {}
    }
  },
  data () {
    return {
      activeKey: 'role'
    }
  },

  methods: {

  }
}
</script>
<style>

</style>
