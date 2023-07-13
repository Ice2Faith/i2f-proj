import FormDetailMode from "@/framework/consts/FormDetailMode";
import ListDetailMixinData from "@/mixins/ListDetailMixinData";

const ListDetailMixin={
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
    return ListDetailMixinData()
  },
  mounted() {
    this.hookBeforeMounted()
    if (this.record) {
      this.form = Object.assign({}, this.form, this.record)
    }
    if (this.mode == FormDetailMode.ADD()) {
      this.form.id = null
    }
    this.hookAfterMounted()
    if(this.needFindDetail){
      this.doFindDetail()
    }
  },
  methods: {
    hookBeforeMounted(){

    },
    hookAfterMounted(){

    },
    hasSubmitButton() {
      return this.mode == FormDetailMode.ADD() || this.mode == FormDetailMode.EDIT()
    },
    filterOption(input, option) {
      return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    },
    doCancel() {
      this.$emit('cancel')
    },
    doFindDetail(){
      if(this.form[this.formRowKey]==null || this.form[this.formRowKey]==undefined || this.form[this.formRowKey]==''){
        return
      }
      this.$axios({
        url: `${this.moduleBaseUrl}/find/${this.form[this.formRowKey]}`,
        method: 'get'
      }).then(({data})=>{
        this.form=Object.assign({},this.form,data)
      })
    },
    doSubmit() {
      this.$refs.form.validateFields().then(() => {
        let _this = this
        _this.controls.loading = true
        let reqConfig = null
        if (this.mode == FormDetailMode.ADD()) {
          reqConfig = {
            url: `${this.moduleBaseUrl}/add`,
            method: 'post',
            data: this.form
          }
        }
        if (this.mode == FormDetailMode.EDIT()) {
          reqConfig = {
            url: `${this.moduleBaseUrl}/update/${this.form[this.formRowKey]}`,
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

export default ListDetailMixin
