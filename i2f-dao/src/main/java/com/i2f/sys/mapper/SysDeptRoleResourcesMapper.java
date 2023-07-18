package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysDeptRoleResourcesDo;
import com.i2f.sys.data.vo.SysDeptRoleResourcesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-18 16:42:11
 * @desc sys_dept_role_resources 部门角色-部门资源表
 */
@Mapper
public interface SysDeptRoleResourcesMapper {

    List<SysDeptRoleResourcesVo> page(@Param("post") SysDeptRoleResourcesVo post);

    List<SysDeptRoleResourcesVo> list(@Param("post") SysDeptRoleResourcesVo post);

    SysDeptRoleResourcesVo findByPk(@Param("id") Long id);

    <T extends SysDeptRoleResourcesDo> int insertSelective(@Param("post") T post);

    <T extends SysDeptRoleResourcesDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysDeptRoleResourcesDo> int insert(@Param("post") T post);

    <T extends SysDeptRoleResourcesDo> int updateByPk(@Param("post") T post);

    <T extends SysDeptRoleResourcesDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysDeptRoleResourcesDo> list);

    int deleteRoleResources(@Param("roleId") Long roleId);

    List<Long> findRoleResourcesIds(@Param("roleId")Long roleId);

}