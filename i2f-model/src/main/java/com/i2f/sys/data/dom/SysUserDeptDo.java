package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:34:56
 * @desc sys_user_dept 用户-部门表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysUserDeptDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 用户ID，见：sys_user.id
     */
    protected Long userId;

    /**
     * 部门ID，见：sys_dept.id
     */
    protected Long deptId;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 创建人
     */
    protected String createUser;


    public <T extends SysUserDeptDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}