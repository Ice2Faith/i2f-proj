package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysUserRoleDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:35:50
 * @desc sys_user_role 用户-角色表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysUserRoleVo extends SysUserRoleDo {


    public SysUserRoleDo parent() {
        return (SysUserRoleDo) this;
    }


}