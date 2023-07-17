package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysNoticeDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:43:00
 * @desc sys_notice 通知表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysNoticeVo extends SysNoticeDo {


    /**
     * 类型：0 仅通知，1 留言，2 待办事项 desc
     */
    protected String typeDesc;
    /**
     * 业务的类型 desc
     */
    protected String businessTypeDesc;

    public SysNoticeDo parent() {
        return (SysNoticeDo) this;
    }


}