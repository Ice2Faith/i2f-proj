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
        <markdown-editor v-model:text="form.content"></markdown-editor>
      </a-form-item>

    </a-form>

    <a-row :gutter="20" justify="end" type="flex" style="margin-top:12px">
      <a-col>
        <a-button @click="doCancel">取消</a-button>
      </a-col>
      <a-col v-if="hasSubmitButton()">
        <a-spin :spinning="submitLoading">
          <a-button type="primary" @click="doSubmit">提交</a-button>
        </a-spin>
      </a-col>
    </a-row>
  </div>
</template>
<script>

import MarkdownEditor from "@/components/MarkdownEditor";
import ListDetailMixin from "@/mixins/ListDetailMixin";
export default {
  components: {MarkdownEditor},
  mixins:[ListDetailMixin],
  data() {
    return {
      moduleBaseUrl: '/api/biz/noteBook',
      form: {
        title: '',
        keywords: '',
        content: '',
        parentId: null,
        remark: '',
        updateTime: '',
        createTime: '',
      },
      rules: {
        title: [{required: true, message: '请输入标题!'}],
      },
      metas: {
        parentList: []
      },
    }
  },
  methods: {
    hookAfterMounted(){
      if(this.form.content==null || this.form.content==undefined){
        this.form.content=''
      }
    }
  }
}
</script>
<style>

</style>
