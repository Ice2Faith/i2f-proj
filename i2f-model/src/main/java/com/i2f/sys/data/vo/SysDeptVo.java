package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysDeptDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:30:12
 * @desc sys_dept 部门表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptVo extends SysDeptDo {


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


}