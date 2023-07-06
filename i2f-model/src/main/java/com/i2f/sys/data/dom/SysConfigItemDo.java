package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-06 16:47:19
 * @desc sys_config_item 配置项表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysConfigItemDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 配置ID：见sys_config.id
     */
    protected Long configId;

    /**
     * 项ID
     */
    protected Long entryId;

    /**
     * 项键
     */
    protected String entryKey;

    /**
     * 项名称
     */
    protected String entryName;

    /**
     * 项描述
     */
    protected String entryDesc;

    /**
     * 项排序
     */
    protected Integer entryOrder;

    /**
     * 父项ID，见：sys_config_item.entry_id
     */
    protected Long parentEntryId;

    /**
     * 参数描述
     */
    protected String paramDesc;

    /**
     * 参数1
     */
    protected String param1;

    /**
     * 参数2
     */
    protected String param2;

    /**
     * 参数3
     */
    protected String param3;

    /**
     * 参数4
     */
    protected String param4;

    /**
     * 参数5
     */
    protected String param5;

    /**
     * 状态：0 禁用，1 启用，99 删除
     */
    protected Integer status;

    /**
     * 层级
     */
    protected Integer level;

    /**
     * 生效时间
     */
    protected Date validTime;

    /**
     * 失效时间
     */
    protected Date invalidTime;

    /**
     * 是否可修改：0 不可，1 可以
     */
    protected Integer modFlag;

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
     * 更新时间
     */
    protected String updateUser;


    public <T extends SysConfigItemDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}