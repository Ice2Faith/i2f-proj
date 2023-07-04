package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:33:51
 * @desc sys_role_resources 角色-资源表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysRoleResourcesDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 角色ID，见：sys_role.id
     */
    protected Long roleId;

    /**
     * 资源ID，见：sys_resources.id
     */
    protected Long resId;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 创建人
     */
    protected String createUser;


    public <T extends SysRoleResourcesDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}