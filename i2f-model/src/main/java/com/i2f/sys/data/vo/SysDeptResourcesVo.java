package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysDeptResourcesDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:39:03
 * @desc sys_dept_resources 部门资源表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptResourcesVo extends SysDeptResourcesDo {


    /**
     * 状态：0 禁用，1 启用，99 删除 desc
     */
    protected String statusDesc;
    /**
     * 排序字段 desc
     */
    protected String orderIndexDesc;

    public SysDeptResourcesDo parent() {
        return (SysDeptResourcesDo) this;
    }


}