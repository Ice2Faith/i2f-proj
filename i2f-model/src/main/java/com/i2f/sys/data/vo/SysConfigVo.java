package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysConfigDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-06 16:46:18
 * @desc sys_config
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysConfigVo extends SysConfigDo {


    public SysConfigDo parent() {
        return (SysConfigDo) this;
    }

    /**
     * 是否可修改：0 不可，1 可以 desc
     */
    protected String modFlagDesc;
    /**
     * 是否可删除：0 不可，1 可以 desc
     */
    protected String delFlagDesc;
    /**
     * 是否系统：0 否，1 是 desc
     */
    protected String sysFlagDesc;

}