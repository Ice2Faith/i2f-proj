package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-18 16:42:11
 * @desc sys_dept_role_resources 部门角色-部门资源表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptRoleResourcesDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 角色ID，见：sys_dept_role.id
     */
    protected Long deptRoleId;

    /**
     * 资源ID，见：sys_dept_resources.id
     */
    protected Long deptResId;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 创建人
     */
    protected String createUser;


    public <T extends SysDeptRoleResourcesDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}