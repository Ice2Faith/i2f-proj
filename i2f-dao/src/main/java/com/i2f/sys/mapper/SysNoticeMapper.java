package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysNoticeDo;
import com.i2f.sys.data.vo.SysNoticeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:43:00
 * @desc sys_notice 通知表
 */
@Mapper
public interface SysNoticeMapper {

    List<SysNoticeVo> page(@Param("post") SysNoticeVo post);

    List<SysNoticeVo> list(@Param("post") SysNoticeVo post);

    SysNoticeVo findByPk(@Param("id") Long id);

    <T extends SysNoticeDo> int insertSelective(@Param("post") T post);

    <T extends SysNoticeDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysNoticeDo> int insert(@Param("post") T post);

    <T extends SysNoticeDo> int updateByPk(@Param("post") T post);

    <T extends SysNoticeDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysNoticeDo> list);
}