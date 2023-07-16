package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysConfigVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-06 16:46:18
 * @desc sys_config
 */
public interface ISysConfigService {

    ApiPage<SysConfigVo> page(SysConfigVo webVo,
                              ApiPage<SysConfigVo> page);

    List<SysConfigVo> list(SysConfigVo webVo);

    SysConfigVo find(Long id);

    void add(SysConfigVo webVo);

    void update(SysConfigVo webVo);

    void delete(Long id);
}