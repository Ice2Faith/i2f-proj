<template>
  <a-config-provider :locale="locale">
    <router-view/>
  </a-config-provider>
</template>

<script>

import SecureTransfer from '@/framework/secure/core/secure-transfer'
import zhCN from 'ant-design-vue/es/locale/zh_CN'
import '../resizer/DebounceResizeObserver'
import SecureCallback from '@/framework/secure/core/secure-callback'

export default {
  name: 'App',
  data () {
    return {
      locale: zhCN
    }
  },
  created () {
    this.initAsymPubKey()
    this.initAsymPriKey()
    SecureCallback.callPubKey = this.initAsymPubKey
    SecureCallback.callPriKey = this.initAsymPriKey
    const _this = this
    window.rsaTimer = setInterval(function () {
      _this.initAsymPubKey()
    }, 5 * 60 * 1000)
  },
  unmounted () {
    clearInterval(window.rsaTimer)
  },
  methods: {
    initAsymPubKey () {
      this.$axios({
        url: 'secure/key',
        method: 'post'
      }).then(({ data }) => {
        SecureTransfer.saveAsymPubKey(data)
      })
    },
    initAsymPriKey () {
      this.$axios({
        url: 'secure/clientKey',
        method: 'post'
      }).then(({ data }) => {
        SecureTransfer.saveAsymPriKey(data)
      })
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;

  color: #2c3e50;
}

</style>
