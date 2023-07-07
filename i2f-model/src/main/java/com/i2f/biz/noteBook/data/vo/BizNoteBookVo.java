package com.i2f.biz.noteBook.data.vo;


import com.i2f.biz.noteBook.data.dom.BizNoteBookDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:57:13
 * @desc biz_note_book 笔记本
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BizNoteBookVo extends BizNoteBookDo {


    public BizNoteBookDo parent() {
        return (BizNoteBookDo) this;
    }


}