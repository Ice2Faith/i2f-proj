<template>
  <div>
    <van-nav-bar
      left-arrow
      left-text="返回"
      title="菜单"
      @click-left="onClickBack"
    />
    <van-grid :gutter="10" square>
      <van-grid-item v-for="(item,index) in currents"
                     :key="item[config.key]"
                     :dot="item[config.children] && item[config.children].length>0"
                     :icon=" 'search' || item[config.icon]"
                     :text="item[config.title]"
                     @click="onClickMenu(item)"
      />
    </van-grid>
  </div>
</template>
<script>
export default {
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
  },
  data() {
    return {
      currents: [],
      parent: []
    }
  },
  mounted() {

  },
  watch: {
    menus: {
      immediate: true,
      deep: true,
      handler: function (val, old) {
        this.currents = this.menus
        this.parent = []
      }
    }
  },
  methods: {
    onClickMenu(item) {
      if (item.children && item.children.length > 0) {
        this.parent.push(this.currents)
        this.currents = item.children
        return
      }
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
    onClickBack() {
      if (this.parent.length <= 0) {
        this.currents = this.menus
        this.parent = []
        return
      }

      this.currents = this.parent.splice(this.parent.length - 1, 1)[0]
    }
  }
}
</script>
<style scoped>

</style>
