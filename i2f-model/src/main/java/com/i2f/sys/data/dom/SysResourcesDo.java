package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:32:40
 * @desc sys_resources 资源表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysResourcesDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 名称
     */
    protected String name;

    /**
     * 菜单键
     */
    protected String menuKey;

    /**
     * 类型：0 菜单，1 权限
     */
    protected Integer type;

    /**
     * URL
     */
    protected String url;

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


    public <T extends SysResourcesDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}