<template>
  <div>
    home
  </div>
</template>
<script>
export default {
  name: 'HomeView',
  methods: {
    testSvc() {
      this.$axios({
        url: 'api/hello/hello',
        method: 'post',
        data: {
          version: 1.0,
          author: 'i2f'
        }
      }).then(data => {
        this.$message.noticeInfo(data)
      })
    }
  }
}
</script>
<style scoped>

</style>
