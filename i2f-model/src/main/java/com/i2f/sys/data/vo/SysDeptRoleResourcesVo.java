package com.i2f.sys.data.vo;


import com.i2f.sys.data.dom.SysDeptRoleResourcesDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-18 16:42:11
 * @desc sys_dept_role_resources 部门角色-部门资源表
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptRoleResourcesVo extends SysDeptRoleResourcesDo {


    public SysDeptRoleResourcesDo parent() {
        return (SysDeptRoleResourcesDo) this;
    }


}