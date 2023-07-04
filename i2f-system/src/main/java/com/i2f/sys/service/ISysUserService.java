package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysUserVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 15:47:56
 * @desc sys_user 用户表
 */
public interface ISysUserService {

    ApiPage<SysUserVo> page(SysUserVo webVo,
                            ApiPage<SysUserVo> page);

    List<SysUserVo> list(SysUserVo webVo);

    SysUserVo find(Long id);

    void add(SysUserVo webVo);

    void update(SysUserVo webVo);

    void delete(Long id);
}