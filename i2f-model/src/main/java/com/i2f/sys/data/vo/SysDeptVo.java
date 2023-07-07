package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysDeptDo;
import i2f.core.convert.ITreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:30:12
 * @desc sys_dept 部门表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptVo extends SysDeptDo implements ITreeNode<SysDeptVo> {

    /**
     * 子节点列表
     */
    protected List<SysDeptVo> children;

    /**
     * 层级 desc
     */
    protected String levelDesc;
    /**
     * 状态：0 禁用，1 启用，99 删除 desc
     */
    protected String statusDesc;

    public SysDeptDo parent() {
        return (SysDeptDo) this;
    }

    @Override
    public void asMyChild(SysDeptVo val) {
        if (children == null) {
            children = new LinkedList<>();
        }
        children.add(val);
    }

    @Override
    public boolean isMyChild(SysDeptVo val) {
        if (this.id == null) {
            return false;
        }
        if (val.parentId == null) {
            return false;
        }
        return (long) this.id == val.parentId;
    }

    @Override
    public boolean isMyParent(SysDeptVo val) {
        if (this.parentId == null) {
            return false;
        }
        if (val.id == null) {
            return false;
        }
        return (long) this.parentId == val.id;
    }
}