package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysDeptResourcesDo;
import com.i2f.sys.data.vo.SysDeptResourcesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:39:03
 * @desc sys_dept_resources 部门资源表
 */
@Mapper
public interface SysDeptResourcesMapper {

    List<SysDeptResourcesVo> page(@Param("post") SysDeptResourcesVo post);

    List<SysDeptResourcesVo> list(@Param("post") SysDeptResourcesVo post);

    SysDeptResourcesVo findByPk(@Param("id") Long id);

    <T extends SysDeptResourcesDo> int insertSelective(@Param("post") T post);

    <T extends SysDeptResourcesDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysDeptResourcesDo> int insert(@Param("post") T post);

    <T extends SysDeptResourcesDo> int updateByPk(@Param("post") T post);

    <T extends SysDeptResourcesDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysDeptResourcesDo> list);

    int countOfKey(@Param("deptId")Long deptId,
                   @Param("key")String key,
                   @Param("excludesIds")Collection<Object> excludesIds);
}