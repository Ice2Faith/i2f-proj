package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysUserRoleDo;
import com.i2f.sys.data.vo.SysUserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:35:50
 * @desc sys_user_role 用户-角色表
 */
@Mapper
public interface SysUserRoleMapper {

    List<SysUserRoleVo> page(@Param("post") SysUserRoleVo post);

    List<SysUserRoleVo> list(@Param("post") SysUserRoleVo post);

    SysUserRoleVo findByPk(@Param("id") Long id);

    <T extends SysUserRoleDo> int insertSelective(@Param("post") T post);

    <T extends SysUserRoleDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysUserRoleDo> int insert(@Param("post") T post);

    <T extends SysUserRoleDo> int updateByPk(@Param("post") T post);

    <T extends SysUserRoleDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysUserRoleDo> list);
}