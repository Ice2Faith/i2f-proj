<template>
  <div>
    <a-layout style="min-height: 100vh">
      <a-layout-sider v-model:collapsed="collapsed" collapsible>
        <div class="logo">
          开放、安全
        </div>
        <MenuTree :config="menuConfig" :menus="menus" @click="onClickMenu"></MenuTree>
      </a-layout-sider>
      <a-layout>
        <a-layout-header style="background: #fff; padding: 0;">
          <a-row type="flex">
            <a-col flex="50%">
              <MenuTree :config="menuConfig" :menus="menus" mode="horizontal" theme="light"></MenuTree>
            </a-col>
            <a-col flex="auto">
              <div style="float:right">

                <a-menu
                  mode="horizontal"
                >
                  <a-menu-item key="msg" @click="showNotice">
                    <template #icon>
                      <message-outlined/>
                    </template>
                    <a-badge :count="noticeCount">
                      <span style="margin-right: 10px">
                        消息
                      </span>
                    </a-badge>
                  </a-menu-item>
                  <a-sub-menu key="mine">
                    <template #icon>
                      <user-outlined/>
                    </template>
                    <template #title>{{ user.tag.realname }}</template>
                    <a-menu-item key="info" @click="editUserInfo">
                      <template #icon>
                        <form-outlined/>
                      </template>
                      个人信息
                    </a-menu-item>
                    <a-menu-item key="pass" @click="changePassword">
                      <template #icon>
                        <key-outlined/>
                      </template>
                      修改密码
                    </a-menu-item>
                    <a-menu-item key="logout" @click="logout">
                      <template #icon>
                        <logout-outlined/>
                      </template>
                      退出登录
                    </a-menu-item>
                  </a-sub-menu>
                </a-menu>

              </div>
            </a-col>
          </a-row>
        </a-layout-header>
        <a-layout-content style="margin: 0 16px">
          <a-tabs v-model:activeKey="activeTab"
                  hide-add
                  style="margin: 16px 0"
                  type="editable-card"
                  @edit="onTabEdit"
                  @tabClick="onClickTab">
            <a-tab-pane v-for="pane in tabs"
                        :key="pane.key"
                        :closable="true"
                        :tab="pane.title">
            </a-tab-pane>
          </a-tabs>
          <div :style="{ padding: '24px', background: '#fff', minHeight: '75vh' }">
            <router-view></router-view>
          </div>
        </a-layout-content>
        <a-layout-footer style="text-align: center">
          开放、安全 @Copyright 2023 created by Ice2Faith
        </a-layout-footer>
      </a-layout>
    </a-layout>
  </div>
</template>
<script>
import MenuTree from "@/components/MenuTree";
import Auth from "../framework/auth";

export default {
  name: 'HomeLayout',
  components: {
    MenuTree
  },
  data() {
    return {
      user: {},
      noticeCount: 0,
      collapsed: false,
      selectedKeys: ['1'],
      activeTab: '0',
      tabs: [
        {
          key: '0',
          title: '首页',
          icon: 'home-outlined',
          url: '/home'
        },
      ],
      menuConfig: {
        key: 'id',
        title: 'name',
        icon: 'icon',
        children: 'children',
        url: 'url'
      },
      menus: [
        {
          key: '0',
          title: '欢迎',
          icon: 'pie-chart-outlined',
          url: '/hello'
        },
        {
          key: '1',
          title: '关于',
          icon: 'pie-chart-outlined',
          url: '/about'
        },
        {
          key: '2',
          title: '欢迎',
          icon: 'pie-chart-outlined',
          url: '/hello',
          children: [
            {
              key: '2.1',
              title: '关于',
              icon: 'pie-chart-outlined',
              url: '/about',
              children: [
                {
                  key: '2.1.1',
                  title: '欢迎',
                  icon: 'pie-chart-outlined',
                  url: '/hello',
                },
              ],
            },
          ],
        },
      ]
    }
  },
  created() {
    this.user = Auth.getUser();
    this.loadMenu()
  },
  methods: {
    onTabEdit(targetKey, action) {
      let idx = -1
      for (let i = 0; i < this.tabs.length; i++) {
        if (this.tabs[i].key == targetKey) {
          idx = i
          break
        }
      }
      if (idx >= 0) {
        this.tabs.splice(idx, 1)
      }
      if (idx > 0) {
        let menu = this.tabs[idx - 1]
        this.onClickTab(menu.key)
      }
    },
    onClickMenu(menu) {
      if (!menu.key) {
        return
      }
      let exists = false
      this.tabs.forEach(item => {
        if (item.key == menu.key) {
          exists = true
        }
      })
      if (!exists) {
        this.tabs.push(menu)
      }
      this.activeTab = menu.key
    },
    onClickTab(key) {
      this.tabs.forEach(item => {
        if (item.key == key) {
          if (!item.url || item.url == '') {
            return
          }
          if (item.url.indexOf('://') >= 0) {
            window.open(item.url, '_blank')
          } else {
            this.$router.push({path: item.url})
          }
        }
      })
    },
    showNotice() {
      this.$message.noticeInfo('功能还未开发')
    },
    editUserInfo() {
      this.$message.noticeInfo('功能还未开发')
    },
    changePassword() {
      this.$message.noticeInfo('功能还未开发')
    },
    logout() {
      this.$axios({
        url: '/logout',
        method: 'get'
      }).then(data => {
        this.$router.replace({path: '/'})
        this.$message.noticeInfo(data.msg)
      })
    },
    loadMenu() {
      this.$axios({
        url: '/sys/user/menus',
        method: 'post'
      }).then(data => {
        this.menus = data.data
      })
    }
  }
}
</script>
<style scoped>
.logo {
  height: 48px;
  margin: 16px;
  background: rgba(255, 255, 255, 0.3);
  text-align: center;
  line-height: 48px;
  color: lightskyblue;
  font-weight: bold;
  overflow: hidden;
}

.site-layout .site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}
</style>
