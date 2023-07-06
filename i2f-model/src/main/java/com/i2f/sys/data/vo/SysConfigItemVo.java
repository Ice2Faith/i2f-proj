package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysConfigItemDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-06 16:47:19
 * @desc sys_config_item 配置项表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysConfigItemVo extends SysConfigItemDo {


    public SysConfigItemDo parent() {
        return (SysConfigItemDo) this;
    }


    /**
     * 项排序 desc
     */
    protected String entryOrderDesc;
    /**
     * 状态：0 禁用，1 启用，99 删除 desc
     */
    protected String statusDesc;
    /**
     * 层级 desc
     */
    protected String levelDesc;
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