package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysDeptRoleDo;
import com.i2f.sys.data.vo.SysDeptRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:38:00
 * @desc sys_dept_role 部门角色表
 */
@Mapper
public interface SysDeptRoleMapper {

    List<SysDeptRoleVo> page(@Param("post") SysDeptRoleVo post);

    List<SysDeptRoleVo> list(@Param("post") SysDeptRoleVo post);

    SysDeptRoleVo findByPk(@Param("id") Long id);

    <T extends SysDeptRoleDo> int insertSelective(@Param("post") T post);

    <T extends SysDeptRoleDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysDeptRoleDo> int insert(@Param("post") T post);

    <T extends SysDeptRoleDo> int updateByPk(@Param("post") T post);

    <T extends SysDeptRoleDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysDeptRoleDo> list);

    int countOfKey(@Param("deptId")Long deptId,
                   @Param("key")String key,
                   @Param("excludesIds")Collection<Object> excludesIds);

    List<SysDeptRoleVo> findUserDeptRoles(@Param("userId")Long userId,
                                          @Param("deptId")Long deptId);
}