package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysUserDeptDo;
import com.i2f.sys.data.vo.SysUserDeptVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:34:56
 * @desc sys_user_dept 用户-部门表
 */
@Mapper
public interface SysUserDeptMapper {

    List<SysUserDeptVo> page(@Param("post") SysUserDeptVo post);

    List<SysUserDeptVo> list(@Param("post") SysUserDeptVo post);

    SysUserDeptVo findByPk(@Param("id") Long id);

    <T extends SysUserDeptDo> int insertSelective(@Param("post") T post);

    <T extends SysUserDeptDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysUserDeptDo> int insert(@Param("post") T post);

    <T extends SysUserDeptDo> int updateByPk(@Param("post") T post);

    <T extends SysUserDeptDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysUserDeptDo> list);
}