package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysDeptRoleVo;
import i2f.core.std.api.ApiPage;

import java.util.Collection;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:38:00
 * @desc sys_dept_role 部门角色表
 */
public interface ISysDeptRoleService {

    ApiPage<SysDeptRoleVo> page(SysDeptRoleVo webVo,
                                ApiPage<SysDeptRoleVo> page);

    List<SysDeptRoleVo> list(SysDeptRoleVo webVo);

    SysDeptRoleVo find(Long id);

    void add(SysDeptRoleVo webVo);

    void update(SysDeptRoleVo webVo);

    void delete(Long id);

    void enable(Long id);

    void disable(Long id);

    List<Long> findRoleResourcesIds(Long roleId);

    void updateRoleResources(Long roleId, Collection<Long> resIds);
}