package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysDeptDo;
import com.i2f.sys.data.vo.SysDeptVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:30:12
 * @desc sys_dept 部门表
 */
@Mapper
public interface SysDeptMapper {

    List<SysDeptVo> page(@Param("post") SysDeptVo post);

    List<SysDeptVo> list(@Param("post") SysDeptVo post);

    List<SysDeptVo> children(@Param("post") SysDeptVo post);

    SysDeptVo findByPk(@Param("id") Long id);

    <T extends SysDeptDo> int insertSelective(@Param("post") T post);

    <T extends SysDeptDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysDeptDo> int insert(@Param("post") T post);

    <T extends SysDeptDo> int updateByPk(@Param("post") T post);

    <T extends SysDeptDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysDeptDo> list);

    List<SysDeptVo> findUserDepts(@Param("userId")Long userId);

    List<String> findUserDeptKeys(@Param("userId")Long userId);

    int countOfKey(@Param("key")String key,
                   @Param("excludesIds")Collection<Object> excludesIds);

    int deleteLogicalByPk(@Param("post") SysDeptVo post);

    int disableByPk(@Param("post") SysDeptVo post);;

    List<Long> findUserDeptIds(Long userId);
}