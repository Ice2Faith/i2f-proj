package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysDeptVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:30:12
 * @desc sys_dept 部门表
 */
public interface ISysDeptService {

    ApiPage<SysDeptVo> page(SysDeptVo webVo,
                            ApiPage<SysDeptVo> page);

    List<SysDeptVo> list(SysDeptVo webVo);

    SysDeptVo find(Long id);

    void add(SysDeptVo webVo);

    void update(SysDeptVo webVo);

    void delete(Long id);
}