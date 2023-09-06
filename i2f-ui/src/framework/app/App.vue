<template>
  <router-view/>
</template>

<script>

import SecureTransfer from '@/framework/secure/core/SecureTransfer'
import '../resizer/DebounceResizeObserver'
import SecureCallback from '@/framework/secure/core/SecureCallback'
import SecureConfig from '@/framework/secure/SecureConfig'

export default {
  name: 'App',
  created () {
    if (SecureConfig.enableSwapAsymKey) {
      this.swapAsymKey()
      SecureCallback.callSwapKey = this.swapAsymKey
      const _this = this
      window.rsaTimer = setInterval(function () {
        _this.swapAsymKey()
      }, 5 * 60 * 1000)
    } else {
      this.initAsymOthPubKey()
      this.initAsymSlfPriKey()
      SecureCallback.callPubKey = this.initAsymOthPubKey
      SecureCallback.callPriKey = this.initAsymSlfPriKey
      const _this = this
      window.rsaTimer = setInterval(function () {
        _this.initAsymPubKey()
      }, 5 * 60 * 1000)
    }
  },
  unmounted () {
    clearInterval(window.rsaTimer)
  },
  methods: {
    swapAsymKey () {
      this.$axios({
        url: 'secure/swapKey',
        method: 'post',
        data: {
          key: SecureTransfer.loadWebAsymSlfPubKey()
        }
      }).then(({ data }) => {
        console.log('SECURE_KEY', data)
        SecureTransfer.saveAsymOthPubKey(data)
      })
    },
    initAsymOthPubKey () {
      this.$axios({
        url: 'secure/key',
        method: 'post'
      }).then(({ data }) => {
        console.log('SECURE_KEY', data)
        SecureTransfer.saveAsymOthPubKey(data)
      })
    },
    initAsymSlfPriKey () {
      this.$axios({
        url: 'secure/clientKey',
        method: 'post'
      }).then(({ data }) => {
        console.log('SECURE_KEY', data)
        SecureTransfer.saveAsymSlfPriKey(data)
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
