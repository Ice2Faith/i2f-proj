package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysNoticeReadDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:44:03
 * @desc sys_notice_read 通知已读表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysNoticeReadVo extends SysNoticeReadDo {


    public SysNoticeReadDo parent() {
        return (SysNoticeReadDo) this;
    }


}