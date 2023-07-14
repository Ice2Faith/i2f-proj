package com.i2f.biz.noteBook.service;

import com.i2f.biz.noteBook.data.vo.BizNoteBookVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:57:13
 * @desc biz_note_book 笔记本
 */
public interface IBizNoteBookService {

    ApiPage<BizNoteBookVo> page(BizNoteBookVo webVo,
                                ApiPage<BizNoteBookVo> page);

    List<BizNoteBookVo> list(BizNoteBookVo webVo);

    List<BizNoteBookVo> children(BizNoteBookVo webVo);

    BizNoteBookVo find(Long id);

    void add(BizNoteBookVo webVo);

    void update(BizNoteBookVo webVo);

    void delete(Long id);


}