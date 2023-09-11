<template>
  <div>
    <a-form
      ref="form"
      :label-col="{ span: 6 }"
      :model="form"
      :wrapper-col="{ span: 18 }"
      autocomplete="off"
      layout="inline"
      name="basic"
      @keyup.enter="doSearch"
    >

      <a-form-item
        label="解码值"
        name="value"
        style="width: 80%"
      >
        <a-input v-model:value="form.value" allow-clear style="width: 100%">
          <template #addonAfter>
            <a-button :href="form.value" style="width: 100%" target="_blank" type="primary">
              跳转
            </a-button>
          </template>
        </a-input>
      </a-form-item>
    </a-form>

    <img :src="form.bs64" style="width: 480px;height: 480px"/>
  </div>
</template>
<script>

import QRCode from 'qrcode'

export default {
  components: {},
  mixins: [],
  data() {
    return {

      form: {
        value: '',
        bs64: ''
      },
      rules: {},
      dialogs: {},
      metas: {},
      tableColumns: []
    }
  },
  watch: {
    'form.value': {
      immediate: true,
      handler: function (val, old) {
        let _this = this
        QRCode.toDataURL(val)
          .then(url => {
            _this.form.bs64 = url
          })
          .catch(err => {
            console.error(err)
          })
      }
    }
  },
  mounted() {
    this.form.value = window.location.href
  },
  computed: {},
  methods: {
    doSearch() {

    }
  }
}
</script>
<style>

</style>
