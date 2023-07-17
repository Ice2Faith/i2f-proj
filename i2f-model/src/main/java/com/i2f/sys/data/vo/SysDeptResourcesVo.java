package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysDeptResourcesDo;
import i2f.core.convert.ITreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:39:03
 * @desc sys_dept_resources 部门资源表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptResourcesVo extends SysDeptResourcesDo implements ITreeNode<SysDeptResourcesVo> {

    public SysDeptResourcesDo parent() {
        return (SysDeptResourcesDo) this;
    }

    /**
     * 状态：0 禁用，1 启用，99 删除 desc
     */
    protected String statusDesc;
    /**
     * 排序字段 desc
     */
    protected String orderIndexDesc;


    /**
     * 子节点列表
     */
    private List<SysDeptResourcesVo> children;

    @Override
    public void asMyChild(SysDeptResourcesVo val) {
        if (children == null) {
            children = new LinkedList<>();
        }
        children.add(val);
    }

    @Override
    public boolean isMyChild(SysDeptResourcesVo val) {
        if (this.id == null) {
            return false;
        }
        if (val.parentId == null) {
            return false;
        }
        return (long) this.id == val.parentId;
    }

    @Override
    public boolean isMyParent(SysDeptResourcesVo val) {
        if (this.parentId == null) {
            return false;
        }
        if (val.id == null) {
            return false;
        }
        return (long) this.parentId == val.id;
    }
}