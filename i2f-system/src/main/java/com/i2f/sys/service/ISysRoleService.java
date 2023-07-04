package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysRoleVo;
import i2f.core.std.api.ApiPage;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:28:51
 * @desc sys_role 角色表
 */
public interface ISysRoleService {

    ApiPage<SysRoleVo> page(SysRoleVo webVo,
                            ApiPage<SysRoleVo> page);

    List<SysRoleVo> list(SysRoleVo webVo);

    SysRoleVo find(Long id);

    void add(SysRoleVo webVo);

    void update(SysRoleVo webVo);

    void delete(Long id);
}