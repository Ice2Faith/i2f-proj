package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysConfigItemVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-06 16:46:18
 * @desc sys_config
 */
public interface ISysConfigItemService {

    ApiPage<SysConfigItemVo> page(SysConfigItemVo webVo,
                              ApiPage<SysConfigItemVo> page);

    List<SysConfigItemVo> list(SysConfigItemVo webVo);

    SysConfigItemVo find(Long id);

    void add(SysConfigItemVo webVo);

    void update(SysConfigItemVo webVo);

    void delete(Long id);

    List<SysConfigItemVo> items(String configKey);

    List<SysConfigItemVo> items(Long configId);

    List<SysConfigItemVo> tree(Long configId);

    List<SysConfigItemVo> tree(String configKey);

    void updateItems(Long configId, List<SysConfigItemVo> items);

    void deleteItems(Long configId);

    List<SysConfigItemVo> children(Long configId, SysConfigItemVo webVo);
}