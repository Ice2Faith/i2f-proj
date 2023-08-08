<template>
  <div>
    <MenuTree :config="menuConfig" :menus="menus" @click="onClickMenu"></MenuTree>
  </div>
</template>
<script>
import MenuTree from "@/components/MenuTree";

export default {
  name: 'MenuView',
  components: {MenuTree},
  data() {
    return {
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
  mounted() {
    this.loadMenu()
  },
  methods: {
    loadMenu() {
      this.$axios({
        url: '/sys/user/menus',
        method: 'post'
      }).then(data => {
        this.menus = data.data
      })
    },
    onClickMenu(item) {

    }
  }
}
</script>
<style scoped>

</style>
