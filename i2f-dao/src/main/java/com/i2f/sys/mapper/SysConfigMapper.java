package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysConfigDo;
import com.i2f.sys.data.vo.SysConfigVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-06 16:46:18
 * @desc sys_config
 */
@Mapper
public interface SysConfigMapper {

    List<SysConfigVo> page(@Param("post") SysConfigVo post);

    List<SysConfigVo> list(@Param("post") SysConfigVo post);

    SysConfigVo findByPk(@Param("id") Long id);

    <T extends SysConfigDo> int insertSelective(@Param("post") T post);

    <T extends SysConfigDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysConfigDo> int insert(@Param("post") T post);

    <T extends SysConfigDo> int updateByPk(@Param("post") T post);

    <T extends SysConfigDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysConfigDo> list);

    int countOfKey(@Param("key")String key,
                   @Param("excludesIds")Collection<Object> excludesIds);

    int countOfNotDeleteableItems(@Param("id")Long id);


}