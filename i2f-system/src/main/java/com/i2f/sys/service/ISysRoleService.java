package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysResourcesVo;
import com.i2f.sys.data.vo.SysRoleVo;
import i2f.core.std.api.ApiPage;

import java.util.Collection;
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

    void enable(Long id);

    void disable(Long id);

    List<SysResourcesVo> findRoleResources(Long roleId);

    List<SysResourcesVo> treeRoleResources(Long roleId);

    List<String> findRolePermKeys(Long roleId);

    void deleteRoleResources(Long roleId);

    void updateRoleResources(Long roleId, Collection<Long> resIds);

    void grantAllResources(Long roleId);

    void grantAllResources(String roleKey);

    void grantLikeResources(Long toRoleId, Long fromRoleId);

    void grantLikeResources(String toRoleKey, String fromRoleKey);

}