package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysRoleResourcesDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:33:51
 * @desc sys_role_resources 角色-资源表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysRoleResourcesVo extends SysRoleResourcesDo {


    public SysRoleResourcesDo parent() {
        return (SysRoleResourcesDo) this;
    }


}