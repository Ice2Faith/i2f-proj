<template>
  <div>
    <div class="verifycode-box">
      <div>
        <span>问题：</span>
        <span>{{request.question}}</span>
      </div>
      <div class="verifycode-img">
        <img v-if="request.base64 && request.base64!=''"
             :src="request.base64"
             @click="onClickImg($event)" />
        <div v-else style="width: 100%;height: 46px;color: red" @click="refresh">
          验证码加载失败了，点击此处重试
        </div>
      </div>
      <div v-if="request.type==0">
        <span>答案：</span>
        <input type="text" v-model="result" class="verifycode-result" @keydown.enter="submit"/>
        <input type="button" @click="submit" value="提交" class="verifycode-submit"/>
      </div>
      <div v-else style="text-align: end">
        <input type="button" @click="refresh" value="刷新" class="verifycode-submit"/>
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
          height: 320,
          type: 1,
          count: 0
        }
      }
    }
  },
  data () {
    return {
      result: '',
      points:[],
      zoom: 'normal'
    }
  },
  mounted () {
    this.refresh()
    this.zoom=document.body.style.zoom
    document.body.style.zoom='normal'
  },
  unmounted() {
    document.body.style.zoom=this.zoom
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
    },
    onClickImg(event){
      if(this.request.type==0){
        this.refresh()
        return
      }
      let dom=event.currentTarget
      let domWid=dom.clientWidth
      let domHei=dom.clientHeight

      let originWid=dom.width
      let originHei=dom.height

      let rect = dom.getBoundingClientRect();

      let x=event.clientX-dom.offsetLeft
      let y=event.clientY-dom.offsetTop

      let style = window.getComputedStyle(document.body,null)

      this.points.push({
        x: x*100.0/domWid,
        y: y*100.0/domHei
      })
      let count=Math.max(1,this.request.count)
      if(this.points.length>=count){
        let str=''
        for(let i=0;i<count;i++){
          if(this.request.type==1){
            str+=';'+this.points[i].x.toFixed(2)
          }else if(this.request.type==2){
            str+=';'+this.points[i].x.toFixed(2)
          }else if(this.request.type==3){
            str+=';'+this.points[i].x.toFixed(2)+','+this.points[i].y.toFixed(2)
          }else if(this.request.type==4){
            str+=';'+this.points[i].x.toFixed(2)+','+this.points[i].y.toFixed(2)
          }
        }
        if(str!=''){
          str=str.substring(1)
        }
        this.result=str
        this.submit()
      }
    },
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
