package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysUserDo;
import com.i2f.sys.data.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 15:47:56
 * @desc sys_user 用户表
 */
@Mapper
public interface SysUserMapper {

    List<SysUserVo> page(@Param("post") SysUserVo post);

    List<SysUserVo> list(@Param("post") SysUserVo post);

    SysUserVo findByPk(@Param("id") Long id);

    SysUserVo findByUsername(@Param("username") String username);

    <T extends SysUserDo> int insertSelective(@Param("post") T post);

    <T extends SysUserDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysUserDo> int insert(@Param("post") T post);

    <T extends SysUserDo> int updateByPk(@Param("post") T post);

    <T extends SysUserDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysUserDo> list);

    int deleteLogicalByPk(@Param("post") SysUserVo post);

    int disableByPk(@Param("post") SysUserVo post);

    int countOfKey(@Param("key")String key,
                   @Param("excludesIds")Collection<Object> excludesIds);

    String findUserPassword(@Param("id")Long id);
}