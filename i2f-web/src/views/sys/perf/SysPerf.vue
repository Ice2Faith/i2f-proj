<template>
  <div>
    <a-input v-model:value="keyword"
              placeholder="过滤（!开头表示不包含）"
              style="position: fixed;background-color: ghostwhite;width:100%;z-index: 200"
              @keyup.enter.native="filterByKeyword">
    </a-input>
    <div id="content" style="width:100%;z-index:10; background-color: white;padding-top: 48px">

    </div>
  </div>
</template>
<script>


export default {
  components: {

  },
  data() {
    return {
      timer: null,
      keyword: '!single',
      baseUrlType: 0,
      echartObjs:{

      }
    }
  },

  mounted() {
    let _this=this
    _this.getData()
    _this.timer=setInterval(function(){
      _this.getData()
    },5000)
  },
  destroyed() {
    clearInterval(this.timer)
  },
  methods: {
    filterByKeyword(){
      this.getData(true)
    },
    getData(fresh) {
      const _this = this
      this.$axios({
        url: '/perf/find',
        method: 'get',
        params: {key: this.keyword}
      }).then(({data:res}) => {
        _this.applyCharts(res, fresh)
      })
    },
    applyCharts(indexesMap,fresh){
      if(fresh){
        this.echartObjs={}
        document.getElementById('content').innerHTML=''
      }
      let _this=this
      let newIds={}
      Object.keys(indexesMap).sort().forEach(function(key){
        _this.applyChart(key,indexesMap[key])
        let domId = 'chart:'+key
        newIds[domId]=true
      })
      let pdom=document.getElementById('content')
      Object.keys(this.echartObjs).forEach(function(key){
        if(!newIds[key]){
          let dom = document.getElementById(key);
          if(dom!=null && dom!=undefined){
            pdom.removeChild(dom)
          }
        }
      })
    },
    // echartsId div的ID ，option：echart的数据 参考https://echarts.apache.org/v4/examples/zh/index.html
    setOption(echartsId, option,callback) {
      // 基于准备好的dom，初始化echarts实例
      let _this=this
      let echarts=this.$echarts
      let renderFunc=function(echartsObj){
        echartsObj.setOption(option)
        if(callback){
          callback(echartsObj)
        }
      }

      let ensureFunc=function(){
        let echartsObj=null
        if(_this.echartObjs[echartsId]){
          echartsObj=_this.echartObjs[echartsId]
        }else {
          let dom = document.getElementById(echartsId);
          if(dom!=null && dom !=undefined){
            echartsObj = echarts.init(dom, 'custom')
          }
        }
        if(echartsObj==null || echartsObj==undefined){
          setTimeout(ensureFunc,30)
        }else{
          _this.echartObjs[echartsId]=echartsObj
          renderFunc(echartsObj)
        }
      }
      ensureFunc()
    },
    applyChart(key,content) {
      let data = content.data
      let domId = 'chart:'+key

      let dom = document.getElementById(domId);
      if(!dom){
        let pdom = document.getElementById('content');
        let ndom = document.createElement('div');
        ndom.id=domId
        ndom.style.width='100%'
        ndom.style.height='360px'
        pdom.appendChild(ndom)
      }

      let names = []
      let dataArr = []


      data.forEach(item => {
        names.push(item.name)
        item.value=parseFloat(item.value).toFixed(2)
        dataArr.push(item)
      })

      let option = {
        title: {
          text: content.title,
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            // Use axis to trigger tooltip
            type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
          }
        },
        yAxis: {
          type: 'value'
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            show: true,
            rotate: -90, // 强制显示所有列名
          },
          data: [...names]
        },
        series: [
          {
            type: 'line',
            data: [...dataArr],
            markPoint: {
              data: [
                {type: 'max', name: 'Max'},
                {type: 'min', name: 'Min'}
              ],
            },
            markLine: {
              data: [{type: 'average', name: 'Avg'}]
            },
          }
        ]
      }

      this.setOption(domId,option)
    },
  }
}
</script>
<style>

</style>
