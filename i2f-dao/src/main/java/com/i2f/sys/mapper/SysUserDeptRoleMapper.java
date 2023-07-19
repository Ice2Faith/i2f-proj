package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysUserDeptRoleDo;
import com.i2f.sys.data.vo.SysUserDeptRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:40:13
 * @desc sys_user_dept_role 用户-部门角色表
 */
@Mapper
public interface SysUserDeptRoleMapper {

    List<SysUserDeptRoleVo> page(@Param("post") SysUserDeptRoleVo post);

    List<SysUserDeptRoleVo> list(@Param("post") SysUserDeptRoleVo post);

    SysUserDeptRoleVo findByPk(@Param("id") Long id);

    <T extends SysUserDeptRoleDo> int insertSelective(@Param("post") T post);

    <T extends SysUserDeptRoleDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysUserDeptRoleDo> int insert(@Param("post") T post);

    <T extends SysUserDeptRoleDo> int updateByPk(@Param("post") T post);

    <T extends SysUserDeptRoleDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysUserDeptRoleDo> list);

    List<Long> findUserDeptRoleIds(@Param("userId")Long userId,
                                   @Param("deptId")Long deptId);

    int deleteUserDeptRoles(@Param("userId")Long userId,
                            @Param("deptId")Long deptId);
}