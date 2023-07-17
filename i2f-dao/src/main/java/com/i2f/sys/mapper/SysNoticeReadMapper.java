package com.i2f.sys.mapper;

import com.i2f.sys.data.dom.SysNoticeReadDo;
import com.i2f.sys.data.vo.SysNoticeReadVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:44:03
 * @desc sys_notice_read 通知已读表
 */
@Mapper
public interface SysNoticeReadMapper {

    List<SysNoticeReadVo> page(@Param("post") SysNoticeReadVo post);

    List<SysNoticeReadVo> list(@Param("post") SysNoticeReadVo post);

    SysNoticeReadVo findByPk(@Param("id") Long id);

    <T extends SysNoticeReadDo> int insertSelective(@Param("post") T post);

    <T extends SysNoticeReadDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends SysNoticeReadDo> int insert(@Param("post") T post);

    <T extends SysNoticeReadDo> int updateByPk(@Param("post") T post);

    <T extends SysNoticeReadDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends SysNoticeReadDo> list);
}