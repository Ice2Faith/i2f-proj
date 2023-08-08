<template>
  <nav>
    <button @click="testSvc">Svc</button>
    <router-link to="/home">Home</router-link>
    |
    <router-link to="/about">About</router-link>
    <button @click="doLogout">Logout</button>
  </nav>
</template>
<script>
export default {
  name: 'Hello',
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
    },
    doLogout() {
      this.$axios({
        url: '/logout',
        method: 'get'
      }).then(data => {
        this.$router.replace({path: '/'})
        this.$message.noticeInfo(data)
      })
    }
  }
}
</script>
<style>

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
