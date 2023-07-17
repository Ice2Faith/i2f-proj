package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:39:03
 * @desc sys_dept_resources 部门资源表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptResourcesDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 部门ID，见：sys_dept.id
     */
    protected Long deptId;

    /**
     * 名称
     */
    protected String name;

    /**
     * 权限键
     */
    protected String permKey;

    /**
     * 备注
     */
    protected String remark;

    /**
     * 父资源ID，见：sys_resources.id
     */
    protected Long parentId;

    /**
     * 状态：0 禁用，1 启用，99 删除
     */
    protected Integer status;

    /**
     * 排序字段
     */
    protected Integer orderIndex;

    /**
     * ICON
     */
    protected String icon;

    /**
     * 样式
     */
    protected String style;

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


    public <T extends SysDeptResourcesDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}