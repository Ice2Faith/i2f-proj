package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysRoleDo;
import com.i2f.sys.data.vo.SysRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:28:51
 * @desc sys_role 角色表
 */
@Mapper
public interface SysRoleMapper {

    List<SysRoleVo> page(@Param("post") SysRoleVo post);

    List<SysRoleVo> list(@Param("post") SysRoleVo post);

    SysRoleVo findByPk(@Param("id") Long id);

    <T extends SysRoleDo> int insertSelective(@Param("post") T post);

    <T extends SysRoleDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysRoleDo> int insert(@Param("post") T post);

    <T extends SysRoleDo> int updateByPk(@Param("post") T post);

    <T extends SysRoleDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysRoleDo> list);

    List<SysRoleVo> findUserRoles(@Param("userId") Long userId);

    List<String> findUserRoleKeys(@Param("userId")Long userId);

    int countOfKey(@Param("key")String key,
                   @Param("excludesIds")Collection<Object> excludesIds);

    int deleteLogicalByPk(@Param("post") SysRoleVo post);

    int disableByPk(@Param("post") SysRoleVo post);

    List<Long> findUserRoleIds(@Param("userId")Long userId);
}