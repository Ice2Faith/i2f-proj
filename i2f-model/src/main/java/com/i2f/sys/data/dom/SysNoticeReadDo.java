package com.i2f.sys.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:44:03
 * @desc sys_notice_read 通知已读表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysNoticeReadDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 通知ID，见：sys_notice.id
     */
    protected Long noticeId;

    /**
     * 用户ID，见：sys_user.id
     */
    protected Long userId;

    /**
     * 创建时间
     */
    protected Date createTime;


    public <T extends SysNoticeReadDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}