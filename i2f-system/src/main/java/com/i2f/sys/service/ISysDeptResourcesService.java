package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysDeptResourcesVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:39:03
 * @desc sys_dept_resources 部门资源表
 */
public interface ISysDeptResourcesService {

    ApiPage<SysDeptResourcesVo> page(SysDeptResourcesVo webVo,
                                     ApiPage<SysDeptResourcesVo> page);

    List<SysDeptResourcesVo> list(SysDeptResourcesVo webVo);

    SysDeptResourcesVo find(Long id);

    void add(SysDeptResourcesVo webVo);

    void update(SysDeptResourcesVo webVo);

    void delete(Long id);

    void enable(Long id);

    void disable(Long id);
}