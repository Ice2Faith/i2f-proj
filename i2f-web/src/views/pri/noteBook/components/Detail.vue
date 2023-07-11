<template>
  <div>

    <a-form
      ref="form"
      :label-col="{ span: 6 }"
      :model="form"
      :wrapper-col="{ span: 18 }"
      autocomplete="off"
      name="basic"
    >
      <a-form-item
        :rules="rules.title"
        label="标题"
        name="title"
      >
        <a-input v-model:value="form.title"/>
      </a-form-item>

      <a-form-item
        :rules="rules.keywords"
        label="关键字"
        name="keywords"
      >
        <a-input v-model:value="form.keywords"/>
      </a-form-item>

      <a-form-item
        :rules="rules.parentId"
        label="父笔记"
        name="parentId"
      >
        <a-select
          v-model:value="form.parentId"
          show-search
          placeholder="请选择"
          :options="metas.parentList"
          :filter-option="filterOption"
        >
        </a-select>
      </a-form-item>

      <a-form-item
        :rules="rules.remark"
        label="备注"
        name="remark"
      >
        <a-input v-model:value="form.remark"/>
      </a-form-item>
      <a-form-item
        :rules="rules.updateTime"
        disabled
        label="更新日期"
        name="updateTime"
      >
        <a-input disabled v-model:value="form.updateTime"/>
      </a-form-item>

      <a-form-item
        :rules="rules.createTime"
        label="创建日期"
        name="createTime"
      >
        <a-input disabled v-model:value="form.createTime"/>
      </a-form-item>

      <a-form-item
        :rules="rules.content"
        label="内容"
        name="content"
      >
        <a-textarea v-model:value="form.content"
                    :auto-size="{ minRows: 3, maxRows: 10 }"/>
      </a-form-item>

    </a-form>

    <a-row :gutter="20" justify="end" type="flex" style="margin-top:12px">
      <a-col>
        <a-button @click="doCancel">取消</a-button>
      </a-col>
      <a-col v-if="hasSubmitButton()">
        <a-spin :spinning="controls.loading">
          <a-button type="primary" @click="doSubmit">提交</a-button>
        </a-spin>
      </a-col>
    </a-row>
  </div>
</template>
<script>

import FormDetailMode from "@/framework/consts/FormDetailMode";

export default {
  props: {
    mode: {
      type: String,
      default: FormDetailMode.ADD()
    },
    record: {
      type: Object,
      default: {}
    }
  },
  data() {
    return {
      form: {
        title: '',
        keywords: '',
        content: '',
        parentId: null,
        remark: '',
        updateTime: '',
        createTime: '',
      },
      controls: {
        loading: false,
      },
      rules: {
        title: [{required: true, message: '请输入标题!'}],
      },
      metas: {
        baseUrl: '/api/biz/noteBook',
        parentList: []
      },
    }
  },
  mounted() {
    if (this.record) {
      this.form = Object.assign({}, this.form, this.record)
    }
    if (this.mode == FormDetailMode.ADD()) {
      this.form.id = null
    }
  },
  methods: {
    hasSubmitButton() {
      return this.mode == FormDetailMode.ADD() || this.mode == FormDetailMode.EDIT()
    },
    filterOption(input, option) {
      return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    },
    doCancel() {
      this.$emit('cancel')
    },
    doSubmit() {
      this.$refs.form.validateFields().then(() => {
        let _this = this
        _this.controls.loading = true
        let reqConfig = null
        if (this.mode == FormDetailMode.ADD()) {
          reqConfig = {
            url: `${this.metas.baseUrl}/add`,
            method: 'post',
            data: this.form
          }
        }
        if (this.mode == FormDetailMode.EDIT()) {
          reqConfig = {
            url: `${this.metas.baseUrl}/update/${this.form.id}`,
            method: 'put',
            data: this.form
          }
        }
        if (reqConfig) {
          this.$axios(reqConfig).then(resp => {
          })
            .finally(() => {
              _this.controls.loading = false
              _this.$emit('submit')
            })
        }
      })

    }
  }
}
</script>
<style>

</style>
