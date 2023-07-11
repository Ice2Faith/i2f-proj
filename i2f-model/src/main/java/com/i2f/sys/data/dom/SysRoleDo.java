package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:28:51
 * @desc sys_role 角色表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysRoleDo {


    /**
     * ID
     */
    protected Long id;

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
     * 是否可删除：0 不可，1 可以
     */
    protected Integer delFlag;

    /**
     * 是否系统：0 否，1 是
     */
    protected Integer sysFlag;

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
     * 更新人
     */
    protected String updateUser;


    public <T extends SysRoleDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}