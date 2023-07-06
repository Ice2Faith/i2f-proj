package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-06 16:46:18
 * @desc sys_config
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysConfigDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 配置KEY
     */
    protected String configKey;

    /**
     * 名称
     */
    protected String name;

    /**
     * 描述
     */
    protected String remark;

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


    public <T extends SysConfigDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}