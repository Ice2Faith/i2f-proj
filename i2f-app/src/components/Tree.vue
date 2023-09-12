<template>
  <div>
    <van-row v-if="multiple && toolbox"
             :gutter="5"
             style="margin-bottom: 7px;padding-bottom:7px;border-bottom: solid 1px #1989fa;">
      <van-col>
        <van-button
          icon="passed"
          type="primary"
          size="mini"
          plain
          @click="onCheckAll"
        >
          全选
        </van-button>
      </van-col>
      <van-col>
        <van-button
          icon="warning-o"
          type="primary"
          size="mini"
          plain
          @click="onToggleCheckAll"
        >
          反选
        </van-button>
      </van-col>
      <van-col>
        <van-button
          icon="close"
          type="primary"
          size="mini"
          plain
          @click="cleanAllChecked"
        >
          清空
        </van-button>
      </van-col>
      <van-col>
        <van-button
          icon="close"
          type="primary"
          size="mini"
          plain
          @click="expandFirst"
        >
          展开首层
        </van-button>
      </van-col>
      <van-col>
        <van-button
          icon="close"
          type="primary"
          size="mini"
          plain
          @click="expandAll"
        >
          展开全部
        </van-button>
      </van-col>
      <van-col>
        <van-button
          icon="close"
          type="primary"
          size="mini"
          plain
          @click="collapseAll"
        >
          折叠全部
        </van-button>
      </van-col>
    </van-row>
    <sub-tree :tree="treeData"
              :checkable="checkable"
              :multiple="multiple"
              :retract-size="retraceSize"
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
</template>

<script>
import SubTree from './SubTree'
export default {
  name: 'Tree',
  components: {
    SubTree
  },
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
    retraceSize: {
      type: [String, Number],
      default: () => '20px'
    },
    strict: {
      type: Boolean,
      default: () => false
    },
    checkedKeys: {
      type: Array,
      default: () => []
    },
    fields: {
      type: Object,
      default: () => {
        return {
          key: 'key',
          text: 'text',
          children: 'children'
        }
      }
    },
    toolbox: {
      type: Boolean,
      default: () => false
    },
    accordion: {
      type: Boolean,
      default: () => true
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
        this.wrapTree()
      }
    }
  },
  methods: {
    wrapTree () {
      this.treeData = []
      this.wrapTreeNext(1, this.tree, this.treeData, null)
    },
    wrapTreeNext (level, srcTree, dstTree, parent) {
      if (!srcTree || srcTree.length == 0) {
        return
      }
      for (let i = 0; i < srcTree.length; i++) {
        const srcItem = srcTree[i]
        const dstItem = {
          level: level,
          parent: parent,
          checked: this.checkedKeys.indexOf(srcItem[this.fields.key]) >= 0,
          indeterminate: false,
          expand: false,
          key: srcItem[this.fields.key],
          text: srcItem[this.fields.text],
          children: [],
          src: srcItem
        }
        dstTree.push(dstItem)
        this.wrapTreeNext(level + 1, srcItem[this.fields.children], dstItem.children, dstItem)
      }
    },
    onCheckedChange (item) {
      this.updateChecked(item)
      this.emitCheckedKeys()
      this.$emit('checked-change', item)
    },
    onItemClicked (item) {
      this.$emit('item-click', item)
    },
    updateChecked (item) {
      if (this.multiple) {
        this.updateTreeParentChecked(item.parent, item)
        this.updateTreeChildrenChecked(item.children, item)
      } else {
        const checked = item.checked
        this.cleanAllChecked()
        item.checked = checked
      }
    },
    emitCheckedKeys () {
      const currentCheckedKeys = []
      this.findAllCheckedKeys(this.treeData, currentCheckedKeys)
      this.$emit('update:checkedKeys', currentCheckedKeys)
    },
    findAllCheckedKeys (node, keys) {
      if (!node) {
        return
      }
      for (let i = 0; i < node.length; i++) {
        const item = node[i]
        if (item.checked) {
          keys.push(item.key)
        }
        this.findAllCheckedKeys(item.children, keys)
      }
    },
    updateTreeParentChecked (node, child) {
      if (!node) {
        return
      }
      if (!node.children) {
        return
      }
      if (this.strict) {
        return
      }
      let selectCnt = 0
      for (let i = 0; i < node.children.length; i++) {
        const item = node.children[i]
        if (item.checked) {
          selectCnt++
        }
      }
      if (selectCnt == node.children.length) {
        node.checked = true
        node.indeterminate = false
      } else if (selectCnt > 0) {
        node.indeterminate = true
        node.checked = true
      } else {
        node.indeterminate = false
        node.checked = false
      }

      this.updateTreeParentChecked(node.parent, node)
    },
    updateTreeChildrenChecked (node, parent) {
      if (!node) {
        return
      }
      if (node.length == 0) {
        return
      }
      if (this.strict) {
        return
      }
      for (let i = 0; i < node.length; i++) {
        const item = node[i]
        item.checked = parent.checked
        this.updateTreeChildrenChecked(node[i].children, item)
      }
    },
    onCheckAll () {
      this.checkAllNext(this.treeData)
      this.emitCheckedKeys()
    },
    checkAllNext (node) {
      if (!node) {
        return
      }
      for (let i = 0; i < node.length; i++) {
        node[i].checked = true
        this.checkAllNext(node[i].children)
      }
    },
    onToggleCheckAll () {
      this.toggleCheckAllNext(this.treeData)
      this.emitCheckedKeys()
    },
    toggleCheckAllNext (node) {
      if (!node) {
        return
      }
      for (let i = 0; i < node.length; i++) {
        node[i].checked = !node[i].checked
        this.toggleCheckAllNext(node[i].children)
      }
    },
    cleanAllChecked () {
      this.cleanAllCheckedNext(this.treeData)
    },
    cleanAllCheckedNext (node) {
      if (!node) {
        return
      }
      for (let i = 0; i < node.length; i++) {
        node[i].checked = false
        this.cleanAllCheckedNext(node[i].children)
      }
    },
    onExpandClicked (node) {
      if (!this.accordion) {
        return
      }
      const parent = node.parent
      let nodes = null
      if (!parent) {
        nodes = this.treeData
      } else {
        nodes = parent.children
      }
      const expand = node.expand
      for (let i = 0; i < nodes.length; i++) {
        nodes[i].expand = false
      }
      node.expand = expand
    },
    expandFirst () {
      for (let i = 0; i < this.treeData.length; i++) {
        this.treeData[i].expand = true
      }
    },
    expandAll () {
      this.expandAllNext(this.treeData)
    },
    expandAllNext (node) {
      for (let i = 0; i < node.length; i++) {
        node[i].expand = true
        this.expandAllNext(node[i].children)
      }
    },
    collapseAll () {
      this.collapseAllNext(this.treeData)
    },
    collapseAllNext (node) {
      for (let i = 0; i < node.length; i++) {
        node[i].expand = false
        this.collapseAllNext(node[i].children)
      }
    }
  }
}
</script>

<style scoped>

</style>
