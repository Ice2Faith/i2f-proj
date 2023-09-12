<template>
  <div>
    <div v-for="(item,index) in treeData" :key="item.key" class="tree-item">
      <div class="tree-icon" :style="{width: retractSize}" >
        <div v-if="item.children && item.children.length>0" @click="()=>{item.expand=!item.expand;onExpandClicked(item)}">
          <slot name="icon" :node="item" >
            <van-icon name="arrow-down" v-if="item.expand"/>
            <van-icon name="arrow" v-else />
          </slot>
        </div>
      </div>
      <div class="tree-checkbox" v-if="checkable" :style="{width: retractSize}" @click="onCheckedChange(item)">
        <slot name="checkbox" :node="item">
          <van-checkbox :shape="multiple?'square':'round'"
                        v-model="item.checked"
                        :checked-color="item.indeterminate?'#fa8919':'#1989fa'">
          </van-checkbox>
        </slot>
      </div>
      <div class="tree-content" :style="contentWidth(item)" @click="onItemClicked(item)">
        <slot name="content" :node="item">
          {{item.text}}
        </slot>
      </div>
      <div class="tree-children"
           :style="{marginLeft: `calc(${retractSize}  * ${item.level+1})`}"
           v-if="item.children && item.children.length>0 && item.expand">
        <sub-tree :tree="item.children"
                  :checkable="checkable"
                  :multiple="multiple"
                  :retract-size="retractSize"
                  :checked-keys="checkedKeys"
                  @checked-change="onCheckedChange"
                  @item-click="onItemClicked"
                  @expand-click="onExpandClicked">
          <template #icon="{node}">
            <slot name="icon" :node="node">
              <van-icon name="arrow-down" v-if="node.expand"/>
              <van-icon name="arrow" v-else />
            </slot>
          </template>
          <template #checkbox="{node}">
            <slot name="checkbox" :node="node">
              <van-checkbox :shape="multiple?'square':'round'"
                            v-model="node.checked"
                            :checked-color="node.indeterminate?'#fa8919':'#1989fa'">
              </van-checkbox>
            </slot>
          </template>
          <template #content="{node}">
            <slot name="content" :node="node">
              {{node.text}}
            </slot>
          </template>
        </sub-tree>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SubTree',
  props: {
    tree: {
      type: Array,
      default: () => []
    },
    checkable: {
      type: Boolean,
      default: () => false
    },
    multiple: {
      type: Boolean,
      default: () => true
    },
    strict: {
      type: Boolean,
      default: () => false
    },
    checkedKeys: {
      type: Array,
      default: () => []
    },
    retractSize: {
      type: [String, Number],
      default: () => '20px'
    }
  },
  data () {
    return {
      treeData: []
    }
  },
  watch: {
    tree: {
      immediate: true,
      deep: true,
      handler: function (val, old) {
        this.treeData = this.tree
      }
    }
  },
  methods: {
    onCheckedChange (item) {
      this.$emit('checked-change', item)
    },
    onItemClicked (item) {
      this.$emit('item-click', item)
    },
    onExpandClicked (item) {
      this.$emit('expand-click', item)
    },
    contentWidth (item) {
      let cnt = 1
      if (this.checkable) {
        cnt++
      }

      if (cnt == 0) {
        return { width: '100%' }
      }
      return { width: `calc(100% - (${cnt + 1} * 5px) - (${cnt} * ${this.retractSize}))` }
    }
  }
}
</script>

<style scoped>
.tree-item{
  font-size: 22px;
  overflow: hidden;
  padding: 2px;
}
.tree-icon{
  display: inline-block;
  margin-left: 5px;
}
.tree-checkbox{
  display: inline-block;
  margin-left: 5px;
}
.tree-content{
  display: inline-block;
  margin-left: 5px;
}
.tree-children{
  border-left: dotted 1px #bbb;
}
</style>
