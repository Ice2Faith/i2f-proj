package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysResourcesDo;
import com.i2f.sys.data.vo.SysResourcesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:32:40
 * @desc sys_resources 资源表
 */
@Mapper
public interface SysResourcesMapper {

    List<SysResourcesVo> page(@Param("post") SysResourcesVo post);

    List<SysResourcesVo> list(@Param("post") SysResourcesVo post);

    List<SysResourcesVo> children(@Param("post") SysResourcesVo post);

    SysResourcesVo findByPk(@Param("id") Long id);

    <T extends SysResourcesDo> int insertSelective(@Param("post") T post);

    <T extends SysResourcesDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysResourcesDo> int insert(@Param("post") T post);

    <T extends SysResourcesDo> int updateByPk(@Param("post") T post);

    <T extends SysResourcesDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysResourcesDo> list);

    List<SysResourcesVo> findRoleResources(@Param("roleId") Long roleId);

    List<String> findRolePermKeys(@Param("roleId") Long roleId);

    List<SysResourcesVo> findUserResources(@Param("userId") Long userId,
                                           @Param("type") String type);

    List<String> findUserPermKeys(@Param("userId") Long userId);


    int countOfMenuKey(@Param("key") String key,
                       @Param("excludesIds") Collection<Object> excludesIds);

    int countOfPermKey(@Param("key") String key,
                       @Param("excludesIds") Collection<Object> excludesIds);
}