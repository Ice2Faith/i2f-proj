<template>
  <van-form ref="form"
            :style="formStyle"
            @keydown.enter.native.stop="onKeydownEnter">
    <van-cell-group :style="cellGroupStyle" inset>
      <template v-for="(item,index) in fields">
        <template v-if="item.hasIf?item.hasIf(innerForm[item.prop]):true">
          <slot :descForm="descForm"
                :emitForm="emitForm"
                :field="item"
                :formRef="$refs.form"
                :innerForm="innerForm"
                :name="item.prop"
                :validate="validate">
            <template v-if="item.type=='select'">
              <van-field
                v-show="item.hasShow?item.hasShow(innerForm[item.prop]):true"
                v-model="descForm[item.prop]"
                :clearable="item.clearable==false?false:true"
                :disabled="item.disabled==true?true:false"
                :is-link="item.isLink==false?false:true"
                :label="item.label"
                :name="item.prop"
                :placeholder="item.placeholder?item.placeholder:`请填写${item.label}`"
                :readonly="item.readonly==true?true:false"
                :rules="rules[item.prop]?rules[item.prop]:[]"
                :type="'text'"
                @click="item._show=true"
              />
              <van-popup v-model:show="item._show" position="bottom">
                <van-picker
                  :columns="metas[item.options?item.options:item.prop]"
                  :columns-field-names="metas[item.optionsFields?item.optionsFields:item.prop+'Fields']?metas[item.optionsFields?item.optionsFields:item.prop+'Fields']:defaultListFields"
                  @cancel="item._show = false"
                  @confirm="({ selectedOptions }) =>{debugger;item._show=false;innerForm[item.prop]=selectedOptions[0].value;descForm[item.prop]=selectedOptions[0].label}"
                />
              </van-popup>
            </template>
            <template v-else-if="item.type=='radio'">
              <van-field
                v-show="item.hasShow?item.hasShow(innerForm[item.prop]):true"
                :disabled="item.disabled==true?true:false"
                :label="item.label"
                :name="item.prop"
                :readonly="item.readonly==true?true:false"
                :rules="rules[item.prop]?rules[item.prop]:[]">
                <template #input>
                  <van-radio-group v-model="innerForm[item.prop]" direction="horizontal">
                    <van-radio v-for="(option,index) in metas[item.options?item.options:item.prop]"
                               :key="option[(metas[item.optionsFields?item.optionsFields:item.prop+'Fields']?metas[item.optionsFields?item.optionsFields:item.prop+'Fields']:defaultListFields).value]"
                               :name="option[(metas[item.optionsFields?item.optionsFields:item.prop+'Fields']?metas[item.optionsFields?item.optionsFields:item.prop+'Fields']:defaultListFields).value]">
                      {{
                        option[(metas[item.optionsFields ? item.optionsFields : item.prop + 'Fields'] ? metas[item.optionsFields ? item.optionsFields : item.prop + 'Fields'] : defaultListFields).text]
                      }}
                    </van-radio>
                  </van-radio-group>
                </template>
              </van-field>
            </template>
            <template v-else-if="item.type=='checkbox'">
              <van-field
                v-show="item.hasShow?item.hasShow(innerForm[item.prop]):true"
                :disabled="item.disabled==true?true:false"
                :label="item.label"
                :name="item.prop"
                :readonly="item.readonly==true?true:false"
                :rules="rules[item.prop]?rules[item.prop]:[]">
                <template #input>
                  <van-checkbox v-model="innerForm[item.prop]">
                  </van-checkbox>
                </template>
              </van-field>
            </template>
            <template v-else-if="item.type=='checkbox-group'">
              <van-field
                v-show="item.hasShow?item.hasShow(innerForm[item.prop]):true"
                :disabled="item.disabled==true?true:false"
                :label="item.label"
                :name="item.prop"
                :readonly="item.readonly==true?true:false"
                :rules="rules[item.prop]?rules[item.prop]:[]">
                <template #input>
                  <van-checkbox-group v-model="innerForm[item.prop]" direction="horizontal">
                    <van-checkbox v-for="(option,index) in metas[item.options?item.options:item.prop]"
                                  :key="option[(metas[item.optionsFields?item.optionsFields:item.prop+'Fields']?metas[item.optionsFields?item.optionsFields:item.prop+'Fields']:defaultListFields).value]"
                                  :name="option[(metas[item.optionsFields?item.optionsFields:item.prop+'Fields']?metas[item.optionsFields?item.optionsFields:item.prop+'Fields']:defaultListFields).value]">
                      {{
                        option[(metas[item.optionsFields ? item.optionsFields : item.prop + 'Fields'] ? metas[item.optionsFields ? item.optionsFields : item.prop + 'Fields'] : defaultListFields).text]
                      }}
                    </van-checkbox>
                  </van-checkbox-group>
                </template>
              </van-field>
            </template>
            <template v-else-if="item.type=='tree'">
              <van-field
                v-show="item.hasShow?item.hasShow(innerForm[item.prop]):true"
                v-model="descForm[item.prop]"
                :clearable="item.clearable==false?false:true"
                :disabled="item.disabled==true?true:false"
                :is-link="item.isLink==false?false:true"
                :label="item.label"
                :name="item.prop"
                :placeholder="item.placeholder?item.placeholder:`请填写${item.label}`"
                :readonly="item.readonly==true?true:false"
                :rules="rules[item.prop]?rules[item.prop]:[]"
                :type="'text'"
                @click="item._show=true"
              />
              <van-popup v-model:show="item._show" position="bottom">
                <div style="padding: 24px 5px;">
                  <tree
                    :checkable="true"
                    :multiple="false"
                    :strict="false"
                    v-model:checked-keys="innerForm[item.prop]"
                    @checked-nodes="(nodes)=>{descForm[item.prop]=nodes.map(e=>e.text).join(',')}"
                    :tree="metas[item.options?item.options:item.prop]"
                    :fields="metas[item.optionsFields?item.optionsFields:item.prop+'Fields']?metas[item.optionsFields?item.optionsFields:item.prop+'Fields']:defaultListFields"
                  >
                  </tree>
                </div>
              </van-popup>
            </template>
            <van-field
              v-else
              v-show="item.hasShow?item.hasShow(innerForm[item.prop]):true"
              v-model="innerForm[item.prop]"
              :clearable="item.clearable==false?false:true"
              :disabled="item.disabled==true?true:false"
              :is-link="item.isLink==true?true:false"
              :label="item.label"
              :name="item.prop"
              :placeholder="item.placeholder?item.placeholder:`请填写${item.label}`"
              :readonly="item.readonly==true?true:false"
              :rules="rules[item.prop]?rules[item.prop]:[]"
              :type="item.type?item.type:'text'"
            />
          </slot>
        </template>
      </template>
    </van-cell-group>
  </van-form>
</template>

<script>
import Tree from '@/components/Tree'
export default {
  name: 'Form',
  components: { Tree },
  props: {
    form: {
      type: Object,
      default: () => {
      }
    },
    fields: {
      type: Array,
      default: () => [{
        prop: 'username',
        label: '用户名',
        placeholder: '请填写用户名',
        clearable: true,
        disabled: false,
        readonly: false,
        isLink: false,
        type: 'text', // select,radio,checkbox,checkbox-group,tree,multi-tree,date,datetime,原始input标签支持的type[email,phone,password...]
        format: 'YYYY-MM-DD',
        options: 'username',
        optionsFields: 'usernameFields'
      }]
    },
    enterSubmit: {
      type: Boolean,
      default: () => false
    },
    rules: {
      username: []
    },
    metas: {
      username: [],
      usernameFields: {
        text: 'text',
        value: 'value',
        children: 'children'
      }
    },
    formStyle: {
      type: Object,
      default: () => {
      }
    },
    cellGroupStyle: {
      type: Object,
      default: () => {
      }
    }
  },
  data () {
    return {
      innerForm: {},
      descForm: {},
      defaultListFields: {
        text: 'text',
        value: 'value',
        children: 'children'
      }
    }
  },
  watch: {
    form: {
      immediate: true,
      deep: true,
      handler: function (val, old) {
        const form = Object.assign({}, this.form)
        this.innerForm = Object.assign({}, form)
        this.descForm = Object.assign({}, form)
        for (let i = 0; i < this.fields.length; i++) {
          const item = this.fields[i]
          if (item.type == 'tree') {
            const srcVal = this.innerForm[item.prop]
            this.innerForm[item.prop] = []
            if (srcVal) {
              this.innerForm[item.prop].push(srcVal)
            }
          }
        }
      }
    },
    innerForm: {
      deep: true,
      handler: function (val, old) {
        const wrapForm = Object.assign({}, this.innerForm)
        for (let i = 0; i < this.fields.length; i++) {
          const item = this.fields[i]
          if (item.type == 'tree') {
            const srcVal = wrapForm[item.prop]
            wrapForm[item.prop] = null
            if (srcVal) {
              wrapForm[item.prop] = srcVal[0]
            }
          }
        }
        this.$emit('update:form', wrapForm)
      }
    }
  },
  methods: {
    onKeydownEnter () {
      if (this.enterSubmit) {
        this.emitForm()
      }
    },
    emitForm () {
      this.$refs.form.validate().then(() => {
        this.$emit('submit', this.innerForm)
      })
    },
    validate () {
      return this.$refs.form.validate()
    },
    formRef () {
      return this.$refs.form
    }
  }
}
</script>

<style scoped>

</style>
