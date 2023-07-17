package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysDeptRoleDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:38:00
 * @desc sys_dept_role 部门角色表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptRoleVo extends SysDeptRoleDo {


    /**
     * 状态：0 禁用，1 启用，99 删除 desc
     */
    protected String statusDesc;

    public SysDeptRoleDo parent() {
        return (SysDeptRoleDo) this;
    }


}