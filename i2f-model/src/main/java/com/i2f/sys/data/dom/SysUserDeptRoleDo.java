package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:40:13
 * @desc sys_user_dept_role 用户-部门角色表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysUserDeptRoleDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 用户ID，见：sys_user.id
     */
    protected Long userId;

    /**
     * 部门角色ID，见：sys_dept_role.id
     */
    protected Long deptRoleId;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 创建人
     */
    protected String createUser;


    public <T extends SysUserDeptRoleDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}