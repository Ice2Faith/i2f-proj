package com.i2f.biz.passwordBook.data.vo;


import com.i2f.biz.passwordBook.data.dom.BizPasswordBookDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:55:59
 * @desc biz_password_book 密码本
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BizPasswordBookVo extends BizPasswordBookDo {


    public BizPasswordBookDo parent() {
        return (BizPasswordBookDo) this;
    }


}