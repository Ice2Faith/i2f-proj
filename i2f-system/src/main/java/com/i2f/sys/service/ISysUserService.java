package com.i2f.sys.service;

import com.i2f.sys.data.vo.*;
import i2f.core.std.api.ApiPage;

import java.util.Collection;
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

    void changePassword(Long id, String password);

    void changePassword(String username, String password);

    void enable(Long id);

    void disable(Long id);

    List<SysRoleVo> findUserRoles(Long userId);

    List<String> findUserRoleKeys(Long userId);

    List<SysResourcesVo> findUserResources(Long userId);

    List<SysResourcesVo> treeUserResources(Long userId);

    List<SysResourcesVo> findUserResourcesMenu(Long userId);

    List<SysResourcesVo> treeUserResourcesMenu(Long userId);

    List<String> findUserPermKeys(Long userId);

    List<SysDeptVo> findUserDepts(Long userId);

    List<String> findUserDeptKeys(Long userId);

    SysUserVo findByUsername(String username);

    String findUserPassword(Long id);

    List<Long> findUserRoleIds(Long userId);

    List<Long> findUserDeptIds(Long userId);

    void updateUserRoleIds(Long userId, Collection<Long> roleIds);

    void updateUserDeptIds(Long userId, Collection<Long> deptIds);

    List<Long> findUserDeptRoleIds(Long userId, Long deptId);

    void updateUserDeptRoleIds(Long userId, Long deptId, List<Long> ids);

    List<SysDeptRoleVo> findUserDeptRoles(Long userId, Long deptId);

    List<SysDeptResourcesVo> findUserDeptPerms(Long userId, Long deptId);

    void registry(SysUserVo webVo);

    void passReset(SysUserVo webVo);
}