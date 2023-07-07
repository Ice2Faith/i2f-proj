package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysResourcesDo;
import i2f.core.convert.ITreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:32:40
 * @desc sys_resources 资源表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysResourcesVo extends SysResourcesDo implements ITreeNode<SysResourcesVo> {

    /**
     * 子节点列表
     */
    protected List<SysResourcesVo> children;

    /**
     * 类型：0 菜单，1 接口，2 按钮，3 权限 desc
     */
    protected String typeDesc;
    /**
     * 状态：0 禁用，1 启用，99 删除 desc
     */
    protected String statusDesc;

    public SysResourcesDo parent() {
        return (SysResourcesDo) this;
    }

    @Override
    public void asMyChild(SysResourcesVo val) {
        if (children == null) {
            children = new LinkedList<>();
        }
        children.add(val);
    }

    @Override
    public boolean isMyChild(SysResourcesVo val) {
        if (this.id == null) {
            return false;
        }
        if (val.parentId == null) {
            return false;
        }
        return (long) this.id == val.parentId;
    }

    @Override
    public boolean isMyParent(SysResourcesVo val) {
        if (this.parentId == null) {
            return false;
        }
        if (val.id == null) {
            return false;
        }
        return (long) this.parentId == val.id;
    }
}