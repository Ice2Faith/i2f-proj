<template>
  <div>
    <div class="verifycode-box">
      <div>
        <span>问题：</span>
        <span>{{request.question}}</span>
      </div>
      <div class="verifycode-img">
        <img v-if="request.base64 && request.base64!=''" :src="request.base64" @click="refresh" />
        <div v-else style="width: 100%;height: 46px;color: red" @click="refresh">
          验证码加载失败了，点击此处重试
        </div>
      </div>
      <div>
        <span>答案：</span>
        <input type="text" v-model="result" class="verifycode-result" @keydown.enter="submit"/>
        <input type="button" @click="submit" value="提交" class="verifycode-submit"/>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VerifyCode',
  props: {
    request: {
      type: Object,
      default: () => {
        return {
          question: '',
          base64: '',
          code: '',
          width: 320,
          height: 320
        }
      }
    }
  },
  data () {
    return {
      result: ''
    }
  },
  mounted () {
    this.refresh()
  },
  methods: {
    refresh () {
      this.$emit('refresh')
    },
    submit () {
      this.$emit('submit', {
        code: this.request.code,
        result: this.result
      })
    }
  },
  computed: {

  }
}
</script>

<style scoped>
.verifycode-box {
  background: white;
  font-size: 22px;
  padding: 5px;
  width: 100%;
  margin: 5px;
}
.verifycode-img{
  border: solid 1px #aaa;
  margin: 5px 0;
  text-align: center;
}
@media (max-aspect-ratio: 1/1) {
  .verifycode-img img{
    width: 100%;
  }
}
@media (min-aspect-ratio: 1/1) {
  .verifycode-img img{
    max-height: 70vh;
    width: 100%;
  }
}
.verifycode-result{
  width: calc(100% - 140px);
  border: solid 1px #aaa;
  margin: 0 5px;
  border-radius: 5px;
}
.verifycode-result:hover{
  border: solid 1px #aaa;
  border-radius: 5px;
}
.verifycode-submit{
  border: solid 1px limegreen;
  background: limegreen;
  color: white;
  padding: 0 8px;
}
.verifycode-submit:hover{
  transform: scale(0.8);
  background: coral;
  border: solid 1px coral;
}
</style>
