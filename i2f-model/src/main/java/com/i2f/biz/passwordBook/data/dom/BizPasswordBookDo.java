package com.i2f.biz.passwordBook.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:55:59
 * @desc biz_password_book 密码本
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BizPasswordBookDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 用户ID，见：sys_user.id
     */
    protected Long userId;

    /**
     * 名称
     */
    protected String name;

    /**
     * 平台
     */
    protected String platform;

    /**
     * 链接
     */
    protected String url;

    /**
     * 账号
     */
    protected String account;

    /**
     * 密码
     */
    protected String password;

    /**
     * 盐值
     */
    protected String salt;

    /**
     * 备注
     */
    protected String remark;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 更新时间
     */
    protected Date updateTime;


    public <T extends BizPasswordBookDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}