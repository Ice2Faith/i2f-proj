<template>
  <div>
    <a-checkbox v-if="checkbox" v-model:checked="previewMode">仅预览</a-checkbox>
    <div :style="{border: editor.mode=='preview'?'solid 1px #aaa': 'none'}" @keyup.esc="previewMode=false">
      <v-md-editor v-model="form.content"
                   :mode="editor.mode"
                   :toc-nav-position-right="true"
                   :default-show-toc="true"
                   :left-toolbar="editor.leftToolbar"
                   :toolbar="getToolbar()"
                   :disabled-menus="[]"
                   :include-level="[1,2,3,4]"
                   :default-fullscreen="fullscreen"
                   @upload-image="handleUploadImage"
                   @change="handleChange"
                   @save="handleSave"
                   @copy-code-success="handleCopyCodeSuccess"
                   :height="height"></v-md-editor>
    </div>
    <a-upload v-show="false" :max-count="1" :file-list="form.fileList" :before-upload="beforeUpload">
      <a-button ref="uploadTriggerBtn">
        <upload-outlined></upload-outlined>
        选择文件
      </a-button>
    </a-upload>
    <a-modal
      v-if="dialogs.detail.show"
      v-model:visible="dialogs.detail.show"
      :title="dialogs.detail.title"
      @ok="dialogs.detail.show=false"
      width="1200px"
    >
      <a-textarea :value="form.html"
                  :auto-size="{ minRows: 3, maxRows: 10 }"/>
    </a-modal>
  </div>
</template>
<script>

export default {
  name: 'MarkdownEditor',
  props: {
    text: {
      type: String,
      default: ''
    },
    edit: {
      type: Boolean,
      default: true
    },
    height: {
      type: String,
      default: '400px'
    },
    fullscreen: {
      type: Boolean,
      default: false
    },
    checkbox: {
      type: Boolean,
      default: true
    }
  },
  components: {},
  data () {
    return {
      previewMode: false,
      form: {
        content: '',
        html: '',
        fileList: []
      },
      dialogs: {
        detail: {
          title: '保存 HTML',
          show: false
        }
      },
      editor: {
        instance: null,
        mode: 'editable',
        leftToolbar: 'undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | save | tip emoji more'
      }
    }
  },
  watch: {
    text: {
      immediate: true,
      handler: function (val, old) {
        this.form.content = val
      }
    },
    edit: {
      immediate: true,
      handler: function (val, old) {
        this.editor.mode = val ? 'editable' : 'preview'
        this.previewMode = !val
      }
    },
    previewMode: {
      handler: function (val, old) {
        this.editor.mode = val ? 'preview' : 'editable'
      }
    }
  },
  created () {

  },
  methods: {
    beforeUpload (file) {
      this.form.fileList.value = [...this.form.fileList, file]

      this.$multipart({
        url: '/api/sys/file/upload',
        data: {
          file: file
        }
      }).then(({ data }) => {
        const url = data.url
        const filename = data.filename
        this.editor.instance.insert(function (selected) {
          const prefix = '['
          const suffix = `](${url})`
          const placeholder = '链接'
          const content = selected || filename || placeholder

          return {
            text: `${prefix}${content}${suffix}`,
            selected: content
          }
        })
      })

      return false
    },

    handleUploadImage (event, insertImage, files) {
      this.$multipart({
        url: '/api/sys/file/upload',
        data: {
          file: files[0]
        }
      }).then(({ data }) => {
        const url = data.url
        const filename = data.filename

        // Here is just an example
        insertImage({
          url: url,
          desc: filename
          // width: 'auto',
          // height: 'auto',
        })
      })
    },
    handleChange (text, html) {
      this.$emit('update:text', text)
      this.$emit('change', text, html)
    },
    handleCopyCodeSuccess () {
      this.$message.noticeInfo('复制成功')
    },
    handleSave (text, html) {
      this.form.html = html
      this.dialogs.detail.show = true
    },
    getToolbar () {
      const _this = this
      return {
        more: {
          title: '更多',
          icon: 'v-md-icon-more',
          menus: [
            {
              name: 'katex',
              text: 'katex 公式',
              action (editor) {
                editor.insert(function (selected) {
                  const prefix = '$$'
                  const suffix = '$$'
                  const placeholder = '\\sum_{i=1}^n a_i=0'
                  const content = selected || placeholder

                  return {
                    text: `${prefix}${content}${suffix}`,
                    selected: content
                  }
                })
              }
            },
            {
              name: 'mermaid',
              text: 'mermaid 流程图',
              action (editor) {
                editor.insert(function (selected) {
                  const prefix = '```mermaid\n' +
                    'graph LR\n'
                  const suffix = '\n```'
                  const placeholder = 'A --- B\n' +
                    'B-->C[fa:fa-ban C]\n' +
                    'B-->D(fa:fa-spinner D);'
                  const content = selected || placeholder

                  return {
                    text: `${prefix}${content}${suffix}`,
                    selected: content
                  }
                })
              }
            },
            {
              name: 'task-finish',
              text: 'task 待办任务-已完成',
              action (editor) {
                editor.insert(function (selected) {
                  const prefix = '- [x] '
                  const suffix = ''
                  const placeholder = 'placeholder'
                  const content = selected || placeholder

                  return {
                    text: `${prefix}${content}${suffix}`,
                    selected: content
                  }
                })
              }
            },
            {
              name: 'task-new',
              text: 'task 待办任务-未完成',
              action (editor) {
                editor.insert(function (selected) {
                  const prefix = '- [ ] '
                  const suffix = ''
                  const placeholder = 'placeholder'
                  const content = selected || placeholder

                  return {
                    text: `${prefix}${content}${suffix}`,
                    selected: content
                  }
                })
              }
            },
            {
              name: 'upload-file',
              text: '上传文件',
              action (editor) {
                _this.$refs.uploadTriggerBtn.$el.click()
                _this.editor.instance = editor
              }
            },
            {
              name: 'preview',
              text: '预览',
              action (editor) {
                _this.previewMode = true
              }
            }
          ]
        }

      }
    }
  }
}
</script>
<style>
.v-md-icon-more::before {
  content: '+';
  font-weight: bolder;
}
</style>
<style scoped>

</style>
