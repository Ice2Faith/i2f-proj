<template>
  <div >
    perf
  </div>
</template>
<script>
export default {
  created() {
    this.$emit('nav',{
      enable: true,
      title: '性能监控',
      rightText: null,
      rightIcon: 'search',
      rightClick: this.search
    })
  },
  methods: {
    search(){
      this.$message.noticeSuccess('搜索')
    }
  }
}
</script>
<style>


</style>
