package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysResourcesVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:32:40
 * @desc sys_resources 资源表
 */
public interface ISysResourcesService {

    ApiPage<SysResourcesVo> page(SysResourcesVo webVo,
                                 ApiPage<SysResourcesVo> page);

    List<SysResourcesVo> list(SysResourcesVo webVo);

    List<SysResourcesVo> children(SysResourcesVo webVo);

    List<SysResourcesVo> tree(SysResourcesVo webVo);

    SysResourcesVo find(Long id);

    void add(SysResourcesVo webVo);

    void update(SysResourcesVo webVo);

    void delete(Long id);

    void enable(Long id);

    void disable(Long id);

}