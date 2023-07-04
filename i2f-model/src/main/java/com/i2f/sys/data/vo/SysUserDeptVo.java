package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysUserDeptDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:34:56
 * @desc sys_user_dept 用户-部门表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysUserDeptVo extends SysUserDeptDo {


    public SysUserDeptDo parent() {
        return (SysUserDeptDo) this;
    }


}