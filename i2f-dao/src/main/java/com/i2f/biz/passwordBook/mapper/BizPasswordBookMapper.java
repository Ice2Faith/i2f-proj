package com.i2f.biz.passwordBook.mapper;

import com.i2f.biz.passwordBook.data.dom.BizPasswordBookDo;
import com.i2f.biz.passwordBook.data.vo.BizPasswordBookVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:55:59
 * @desc biz_password_book 密码本
 */
@Mapper
public interface BizPasswordBookMapper {

    List<BizPasswordBookVo> page(@Param("post") BizPasswordBookVo post);

    List<BizPasswordBookVo> list(@Param("post") BizPasswordBookVo post);

    BizPasswordBookVo findByPk(@Param("id") Long id);

    <T extends BizPasswordBookDo> int insertSelective(@Param("post") T post);

    <T extends BizPasswordBookDo> int updateSelectiveByPk(@Param("post") T post);

    int deleteByPk(@Param("id") Long id);

    <T extends BizPasswordBookDo> int insert(@Param("post") T post);

    <T extends BizPasswordBookDo> int updateByPk(@Param("post") T post);

    <T extends BizPasswordBookDo> int deleteSelective(@Param("post") T post);

    int insertBatch(@Param("list") Collection<? extends BizPasswordBookDo> list);
}