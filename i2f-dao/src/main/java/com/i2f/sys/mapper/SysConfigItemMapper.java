package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysConfigItemDo;
import com.i2f.sys.data.vo.SysConfigItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-06 16:47:19
 * @desc sys_config_item 配置项表
 */
@Mapper
public interface SysConfigItemMapper {

    List<SysConfigItemVo> page(@Param("post") SysConfigItemVo post);

    List<SysConfigItemVo> list(@Param("post") SysConfigItemVo post);

    List<SysConfigItemVo> children(@Param("post") SysConfigItemVo post);

    SysConfigItemVo findByPk(@Param("id") Long id);

    <T extends SysConfigItemDo> int insertSelective(@Param("post") T post);

    <T extends SysConfigItemDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysConfigItemDo> int insert(@Param("post") T post);

    <T extends SysConfigItemDo> int updateByPk(@Param("post") T post);

    <T extends SysConfigItemDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysConfigItemDo> list);

    int deleteItemsLogical(@Param("post") SysConfigItemVo post);

    List<SysConfigItemVo> findConfigItemsByConfigId(@Param("configId")Long configId);

    List<SysConfigItemVo> findConfigItemsByConfigKey(@Param("configKey")String configKey);

    int countOfEntryId(@Param("key")Long key,
                       @Param("configId")Long configId,
                       @Param("excludesIds")Collection<Object> excludesIds);

    int countOfEntryKey(@Param("key")String key,
                        @Param("configId")Long configId,
                        @Param("excludesIds")Collection<Object> excludesIds);

    int deleteLogicalByPk(@Param("post")SysConfigItemVo post);

}