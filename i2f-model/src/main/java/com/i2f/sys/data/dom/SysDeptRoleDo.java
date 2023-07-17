package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:38:00
 * @desc sys_dept_role 部门角色表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptRoleDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 部门ID，见：sys_dept.id
     */
    protected Long deptId;

    /**
     * 角色键
     */
    protected String roleKey;

    /**
     * 角色名称
     */
    protected String roleName;

    /**
     * 状态：0 禁用，1 启用，99 删除
     */
    protected Integer status;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 创建人
     */
    protected String createUser;

    /**
     * 更新时间
     */
    protected Date updateTime;

    /**
     * 更新时间
     */
    protected String updateUser;


    public <T extends SysDeptRoleDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}