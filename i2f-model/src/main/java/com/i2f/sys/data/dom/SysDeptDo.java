package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:30:12
 * @desc sys_dept 部门表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 名称
     */
    protected String name;

    /**
     * 备注
     */
    protected String remark;

    /**
     * 父资源ID，见：sys_dept.id
     */
    protected Long parentId;

    /**
     * 层级
     */
    protected Integer level;

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


    public <T extends SysDeptDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}