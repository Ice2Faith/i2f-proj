package com.i2f.biz.passwordBook.service;

import com.i2f.biz.passwordBook.data.vo.BizPasswordBookVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:55:59
 * @desc biz_password_book 密码本
 */
public interface IBizPasswordBookService {

    ApiPage<BizPasswordBookVo> page(BizPasswordBookVo webVo,
                                    ApiPage<BizPasswordBookVo> page);

    List<BizPasswordBookVo> list(BizPasswordBookVo webVo);

    BizPasswordBookVo find(Long id);

    void add(BizPasswordBookVo webVo);

    void update(BizPasswordBookVo webVo);

    void delete(Long id);
}