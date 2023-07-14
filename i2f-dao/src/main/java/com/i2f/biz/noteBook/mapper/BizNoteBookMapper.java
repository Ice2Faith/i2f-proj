package com.i2f.biz.noteBook.mapper;

import com.i2f.biz.noteBook.data.dom.BizNoteBookDo;
import com.i2f.biz.noteBook.data.vo.BizNoteBookVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:57:13
 * @desc biz_note_book 笔记本
 */
@Mapper
public interface BizNoteBookMapper {

    List<BizNoteBookVo> page(@Param("post") BizNoteBookVo post);

    List<BizNoteBookVo> list(@Param("post") BizNoteBookVo post);

    List<BizNoteBookVo> children(@Param("post") BizNoteBookVo post);

    BizNoteBookVo findByPk(@Param("id") Long id);

    <T extends BizNoteBookDo> int insertSelective(@Param("post") T post);

    <T extends BizNoteBookDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends BizNoteBookDo> int insert(@Param("post") T post);

    <T extends BizNoteBookDo> int updateByPk(@Param("post") T post);

    <T extends BizNoteBookDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends BizNoteBookDo> list);


}