<template>
  <div>
    <van-nav-bar
      v-if="nav.enable"
      fixed
      placeholder
      :title="nav.title"
      :left-text="nav.leftText"
      :right-text="nav.rightText"
      :left-arrow="nav.leftArrow"
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <template v-if="nav.leftIcon && nav.leftIcon!=''" #left>
        <van-icon :name="nav.leftIcon" size="18" style="margin-right: var(--van-padding-base);"/>
        <span class="van-nav-bar__text">{{nav.leftText}}</span>
      </template>
      <template v-if="nav.rightIcon && nav.rightIcon!=''" #right>
        <span class="van-nav-bar__text">{{nav.rightText}}</span>
        <van-icon :name="nav.rightIcon" size="18" style="margin-left: var(--van-padding-base);"/>
      </template>
    </van-nav-bar>
    <van-floating-bubble
      v-if="dock.enable"
      axis="xy"
      :icon="dock.icon"
      magnetic="x"
      @click="onClickDock"
    />

    <router-view @nav="onConfigNav" @dock="onConfigDock"/>
  </div>
</template>
<script>

export default {
  name: 'PageLayout',
  components: {},
  data () {
    return {
      nav: {
        enable: true,
        title: '标题',
        leftText: '返回',
        leftIcon: '',
        leftArrow: true,
        rightText: '',
        rightIcon: '',
        leftClick: null,
        rightClick: null,
      },
      dock:{
        enable: true,
        icon: 'arrow-up',
        click: null
      }
    }
  },
  methods:{
    onClickLeft(){
      if(this.nav.leftClick){
        this.nav.leftClick()
      }else{
        this.$router.back()
      }
    },
    onClickRight(){
      if(this.nav.rightClick){
        this.nav.rightClick()
      }
    },
    onClickDock(){
      if(this.dock.click){
        this.dock.click()
      }else{
        window.scrollTo(0, 0)
      }
    },
    onConfigNav(config){
      this.nav=Object.assign({},this.nav,config)
    },
    onConfigDock(config){
      this.dock=Object.assign({},this.dock,config)
    }
  }

}
</script>
<style scoped>

</style>
