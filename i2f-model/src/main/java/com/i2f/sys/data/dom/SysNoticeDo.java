package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:43:00
 * @desc sys_notice 通知表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysNoticeDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 类型：0 仅通知，1 留言，2 待办事项
     */
    protected Integer type;

    /**
     * 接受用户ID，为空时全体用户
     */
    protected Long recvUserId;

    /**
     * 标题
     */
    protected String title;

    /**
     * 正文
     */
    protected String content;

    /**
     * 发送者名称，不为空时显示此项
     */
    protected String sender;

    /**
     * 发送者用户ID，如果是用户发送则允许填写此项
     */
    protected Long sendUserId;

    /**
     * 通知时间，默认当前时间，可修改支持预约
     */
    protected Date noticeTime;

    /**
     * 支持跳转的连接
     */
    protected String url;

    /**
     * 业务的类型
     */
    protected Integer businessType;

    /**
     * 支持关联的业务键
     */
    protected String businessKey;

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


    public <T extends SysNoticeDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}