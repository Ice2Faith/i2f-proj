<template>
  <div class="about">
    <h1 v-for="index in 30">{{index}} : This is an about page</h1>
  </div>
</template>
<script>
export default {
  created() {
    this.$emit('nav',{
      enable: true,
      title: '关于',
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
