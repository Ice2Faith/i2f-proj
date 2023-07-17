package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysNoticeVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:43:00
 * @desc sys_notice 通知表
 */
public interface ISysNoticeService {

    ApiPage<SysNoticeVo> page(SysNoticeVo webVo,
                              ApiPage<SysNoticeVo> page);

    List<SysNoticeVo> list(SysNoticeVo webVo);

    SysNoticeVo find(Long id);

    void add(SysNoticeVo webVo);

    void update(SysNoticeVo webVo);

    void delete(Long id);
}