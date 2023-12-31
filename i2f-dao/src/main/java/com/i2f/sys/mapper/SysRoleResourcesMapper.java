package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysRoleResourcesDo;
import com.i2f.sys.data.vo.SysRoleResourcesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:33:51
 * @desc sys_role_resources 角色-资源表
 */
@Mapper
public interface SysRoleResourcesMapper {

    List<SysRoleResourcesVo> page(@Param("post") SysRoleResourcesVo post);

    List<SysRoleResourcesVo> list(@Param("post") SysRoleResourcesVo post);

    SysRoleResourcesVo findByPk(@Param("id") Long id);

    <T extends SysRoleResourcesDo> int insertSelective(@Param("post") T post);

    <T extends SysRoleResourcesDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysRoleResourcesDo> int insert(@Param("post") T post);

    <T extends SysRoleResourcesDo> int updateByPk(@Param("post") T post);

    <T extends SysRoleResourcesDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysRoleResourcesDo> list);

    int deleteByResId(@Param("resId") Long resId);

    int deleteRoleResources(@Param("roleId") Long roleId);

    int grantAllResourcesByRoleId(@Param("roleId") Long roleId);

    int grantAllResourcesByRoleKey(@Param("roleKey") String roleKey);

    int grantLikeResourcesByRoleId(@Param("toRoleId") Long toRoleId, @Param("fromRoleId") Long fromRoleId);

    int grantLikeResourcesByRoleKey(@Param("toRoleKey") String toRoleKey, @Param("fromRoleKey") String fromRoleKey);

    List<Long> findRoleResourcesIds(@Param("roleId")Long roleId);
}