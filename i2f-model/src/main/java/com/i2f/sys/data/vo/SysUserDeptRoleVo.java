package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysUserDeptRoleDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:40:13
 * @desc sys_user_dept_role 用户-部门角色表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysUserDeptRoleVo extends SysUserDeptRoleDo {


    public SysUserDeptRoleDo parent() {
        return (SysUserDeptRoleDo) this;
    }


}