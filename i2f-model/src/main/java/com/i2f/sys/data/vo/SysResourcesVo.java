package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysResourcesDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:32:40
 * @desc sys_resources 资源表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysResourcesVo extends SysResourcesDo {


    /**
     * 类型：0 菜单，1 接口，2 按钮，3 权限 desc
     */
    protected String typeDesc;
    /**
     * 状态：0 禁用，1 启用，99 删除 desc
     */
    protected String statusDesc;

    public SysResourcesDo parent() {
        return (SysResourcesDo) this;
    }


}