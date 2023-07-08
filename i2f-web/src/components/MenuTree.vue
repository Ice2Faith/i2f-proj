<template>
  <div>
    <a-menu
      :default-open-keys="openKeys"
      :default-selected-keys="selectedKeys"
      :inline-collapsed="collapsed"
      :mode="mode"
      :theme="theme"
    >
      <template v-for="item in menus" :key="item[config.key]">
        <template v-if="!item[config.children]">
          <a-menu-item :key="item[config.key]" @click="onClickMenu(item)">
            <template v-if="item[config.icon]" #icon>
              <component :is="item[config.icon]"/>
            </template>
            {{ item[config.title] }}
          </a-menu-item>
        </template>
        <template v-else>
          <sub-menu :key="item[config.key]" :config="config" :menu="item" @click="popClickMenu"/>
        </template>
      </template>
    </a-menu>
  </div>
</template>
<script>
import {defineComponent} from 'vue';

const SubMenu = {
  name: 'SubMenu',
  props: {
    menu: {
      type: Object,
      default: () => ({}),
    },
    config: {
      type: Object,
      default: {
        key: 'key',
        title: 'title',
        icon: 'icon',
        children: 'children',
        url: 'url'
      }
    },
  },
  methods: {
    onClickMenu(item) {
      if (!item[this.config.url] || item[this.config.url] == '') {
        return
      }
      if (item[this.config.url].indexOf('://') >= 0) {
        window.open(item[this.config.url], '_blank')
      } else {
        this.$router.push({path: item[this.config.url]})
      }
      this.$emit('click', {
        key: item[this.config.key],
        title: item[this.config.title],
        icon: item[this.config.icon],
        children: item[this.config.children],
        url: item[this.config.url]
      }, item)
    },
    popClickMenu(item, originItem) {
      this.$emit('click', item, originItem)
    }
  },
  setup() {

  },
  template: `
    <a-sub-menu :key="menu[config.key]">
    <template v-if="menu[config.icon]" #icon>
      <component :is="menu[config.icon]"/>
    </template>
    <template #title>{{ menu[config.title] }}</template>
    <template v-for="item in menu[config.children]" :key="item[config.key]">
      <template v-if="!item[config.children]">
        <a-menu-item :key="item[config.key]" @click="onClickMenu(item)">
          <template v-if="item[config.icon]" #icon>
            <component :is="item[config.icon]"/>
          </template>
          {{ item[config.title] }}
        </a-menu-item>
      </template>
      <template v-else>
        <sub-menu :menu="item" :key="item[config.key]" @click="popClickMenu"/>
      </template>
    </template>
    </a-sub-menu>
  `
};

/**
 menus : [
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
 */
export default defineComponent({
  props: {
    menus: {
      type: Array,
      default: [],
    },
    config: {
      type: Object,
      default: {
        key: 'key',
        title: 'title',
        icon: 'icon',
        children: 'children',
        url: 'url'
      }
    },
    mode: {
      type: String,
      default: 'inline'
    },
    theme: {
      type: String,
      default: 'dark'
    },
    selectedKeys: {
      type: Array,
      default: []
    },
    openKeys: {
      type: Array,
      default: []
    },
    collapsed: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    onClickMenu(item) {
      if (!item[this.config.url] || item[this.config.url] == '') {
        return
      }
      if (item[this.config.url].indexOf('://') >= 0) {
        window.open(item[this.config.url], '_blank')
      } else {
        this.$router.push({path: item[this.config.url]})
      }
      this.$emit('click', {
        key: item[this.config.key],
        title: item[this.config.title],
        icon: item[this.config.icon],
        children: item[this.config.children],
        url: item[this.config.url]
      }, item)
    },
    popClickMenu(item, originItem) {
      this.$emit('click', item, originItem)
    }
  },
  setup() {

  },
  components: {
    'sub-menu': SubMenu
  },
});
</script>
