package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysUserDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Set;

/**
 * @author Ice2Faith
 * @date 2023-07-04 15:47:56
 * @desc sys_user 用户表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysUserVo extends SysUserDo {


    public SysUserDo parent() {
        return (SysUserDo) this;
    }


    /**
     * 是否可删除：0 不可，1 可以 desc
     */
    protected String delFlagDesc;
    /**
     * 是否系统：0 否，1 是 desc
     */
    protected String sysFlagDesc;
    /**
     * 状态：0 禁用，1 启用，99 删除 desc
     */
    protected String statusDesc;


    protected Set<String> urls;
    protected Set<String> perms;
    protected Set<String> menus;
    protected Set<String> roles;
    protected Set<String> depts;
    protected Map<String,Set<String>> deptRoles;
    protected Map<String,Set<String>> deptPerms;
}