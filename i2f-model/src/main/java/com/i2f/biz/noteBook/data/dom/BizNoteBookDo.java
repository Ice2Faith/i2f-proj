package com.i2f.biz.noteBook.data.dom;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:57:13
 * @desc biz_note_book 笔记本
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BizNoteBookDo {


    /**
     * ID
     */
    protected Long id;

    /**
     * 用户ID，见：sys_user.id
     */
    protected Long userId;

    /**
     * 标题
     */
    protected String title;

    /**
     * 关键字
     */
    protected String keywords;

    /**
     * 内容
     */
    protected String content;

    /**
     * 父笔记ID，见：biz_note_book.id
     */
    protected Long parentId;

    /**
     * 备注
     */
    protected String remark;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 更新时间
     */
    protected Date updateTime;


    public <T extends BizNoteBookDo> T convert(T dom) {
        BeanUtils.copyProperties(this, dom);
        return dom;
    }

}