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
  data () {
    return {
      timer: null,
      keyword: '!single',
      baseUrlType: 0,
      echartObjs: {

      }
    }
  },

  mounted () {
    const _this = this
    _this.getData()
    _this.timer = setInterval(function () {
      _this.getData()
    }, 5000)
  },
  unmounted () {
    clearInterval(this.timer)
  },
  methods: {
    filterByKeyword () {
      this.getData(true)
    },
    getData (fresh) {
      const _this = this
      this.$axios({
        url: '/perf/find',
        method: 'get',
        params: { key: this.keyword }
      }).then(({ data: res }) => {
        _this.applyCharts(res, fresh)
      })
    },
    applyCharts (indexesMap, fresh) {
      if (fresh) {
        this.echartObjs = {}
        document.getElementById('content').innerHTML = ''
      }
      const _this = this
      const newIds = {}
      Object.keys(indexesMap).sort().forEach(function (key) {
        _this.applyChart(key, indexesMap[key])
        const domId = 'chart:' + key
        newIds[domId] = true
      })
      const pdom = document.getElementById('content')
      Object.keys(this.echartObjs).forEach(function (key) {
        if (!newIds[key]) {
          const dom = document.getElementById(key)
          if (dom != null && dom != undefined) {
            pdom.removeChild(dom)
          }
        }
      })
    },
    // echartsId div的ID ，option：echart的数据 参考https://echarts.apache.org/v4/examples/zh/index.html
    setOption (echartsId, option, callback) {
      // 基于准备好的dom，初始化echarts实例
      const _this = this
      const echarts = this.$echarts
      const renderFunc = function (echartsObj) {
        echartsObj.setOption(option)
        if (callback) {
          callback(echartsObj)
        }
      }

      const ensureFunc = function () {
        let echartsObj = null
        if (_this.echartObjs[echartsId]) {
          echartsObj = _this.echartObjs[echartsId]
        } else {
          const dom = document.getElementById(echartsId)
          if (dom != null && dom != undefined) {
            echartsObj = echarts.init(dom, 'custom')
          }
        }
        if (echartsObj == null || echartsObj == undefined) {
          setTimeout(ensureFunc, 30)
        } else {
          _this.echartObjs[echartsId] = echartsObj
          renderFunc(echartsObj)
        }
      }
      ensureFunc()
    },
    applyChart (key, content) {
      const data = content.data
      const domId = 'chart:' + key

      const dom = document.getElementById(domId)
      if (!dom) {
        const pdom = document.getElementById('content')
        const ndom = document.createElement('div')
        ndom.id = domId
        ndom.style.width = '100%'
        ndom.style.height = '360px'
        pdom.appendChild(ndom)
      }

      const names = []
      const dataArr = []

      data.forEach(item => {
        names.push(item.name)
        item.value = parseFloat(item.value).toFixed(2)
        dataArr.push(item)
      })

      const option = {
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
            rotate: -90 // 强制显示所有列名
          },
          data: [...names]
        },
        series: [
          {
            type: 'line',
            data: [...dataArr],
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
            markLine: {
              data: [{ type: 'average', name: 'Avg' }]
            }
          }
        ]
      }

      this.setOption(domId, option)
    }
  }
}
</script>
<style>

</style>
