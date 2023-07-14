package com.i2f.sys.controller;


import com.i2f.sys.data.vo.SysDeptVo;
import com.i2f.sys.data.vo.SysResourcesVo;
import com.i2f.sys.data.vo.SysRoleVo;
import com.i2f.sys.data.vo.SysUserVo;
import com.i2f.sys.service.ISysUserService;
import i2f.core.std.api.ApiPage;
import i2f.core.std.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 15:47:56
 * @desc sys_user 用户表
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/user")
public class SysUserController {

    @Autowired
    private ISysUserService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(SysUserVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<SysUserVo> page = new ApiPage<>(index, size);
        ApiPage<SysUserVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(SysUserVo webVo) {
        List<SysUserVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        SysUserVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody SysUserVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody SysUserVo webVo) {
        webVo.setId(id);
        baseService.update(webVo);
        return ApiResp.success("ok");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResp<?> delete(@PathVariable(value = "id") Long id) {
        baseService.delete(id);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/password/change/id/{id}")
    public ApiResp<?> changePassword(@PathVariable(value = "id") Long id,
                                     @RequestBody String password) {
        baseService.changePassword(id, password);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/password/change/username/{username}")
    public ApiResp<?> changePassword(@PathVariable(value = "username") String username,
                                     @RequestBody String password) {
        baseService.changePassword(username, password);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/enable/{id}")
    public ApiResp<?> enable(@PathVariable(value = "id") Long id) {
        baseService.enable(id);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/disable/{id}")
    public ApiResp<?> disable(@PathVariable(value = "id") Long id) {
        baseService.disable(id);
        return ApiResp.success("ok");
    }

    @GetMapping(value = "/role/list/{userId}")
    public ApiResp<?> findUserRoles(@PathVariable(value = "userId") Long userId) {
        List<SysRoleVo> ret = baseService.findUserRoles(userId);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/role/ids/{userId}")
    public ApiResp<?> findUserRoleIds(@PathVariable(value = "userId") Long userId) {
        List<Long> ret = baseService.findUserRoleIds(userId);
        return ApiResp.success(ret);
    }

    @PutMapping(value = "/role/update/{userId}")
    public ApiResp<?> updateUserRoleIds(@PathVariable(value = "userId") Long userId,
                                      @RequestBody List<Long> ids) {
        baseService.updateUserRoleIds(userId,ids);
        return ApiResp.success("ok");
    }

    @GetMapping(value = "/role/keys/{userId}")
    public ApiResp<?> findUserRoleKeys(@PathVariable(value = "userId") Long userId) {
        List<String> ret = baseService.findUserRoleKeys(userId);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/resources/list/{userId}")
    public ApiResp<?> findUserResources(@PathVariable(value = "userId") Long userId) {
        List<SysResourcesVo> ret = baseService.findUserResources(userId);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/resources/tree/{userId}")
    public ApiResp<?> treeUserResources(@PathVariable(value = "userId") Long userId) {
        List<SysResourcesVo> ret = baseService.treeUserResources(userId);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/resources/menu/list/{userId}")
    public ApiResp<?> findUserResourcesMenu(@PathVariable(value = "userId") Long userId) {
        List<SysResourcesVo> ret = baseService.findUserResourcesMenu(userId);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/resources/menu/tree/{userId}")
    public ApiResp<?> treeUserResourcesMenu(@PathVariable(value = "userId") Long userId) {
        List<SysResourcesVo> ret = baseService.treeUserResourcesMenu(userId);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/resources/perm/keys/{userId}")
    public ApiResp<?> findUserPermKeys(@PathVariable(value = "userId") Long userId) {
        List<String> ret = baseService.findUserPermKeys(userId);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/dept/list/{userId}")
    public ApiResp<?> findUserDepts(@PathVariable(value = "userId") Long userId) {
        List<SysDeptVo> ret = baseService.findUserDepts(userId);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/dept/ids/{userId}")
    public ApiResp<?> findUserDeptIds(@PathVariable(value = "userId") Long userId) {
        List<Long> ret = baseService.findUserDeptIds(userId);
        return ApiResp.success(ret);
    }

    @PutMapping(value = "/dept/update/{userId}")
    public ApiResp<?> updateUserDeptIds(@PathVariable(value = "userId") Long userId,
                                        @RequestBody List<Long> ids) {
        baseService.updateUserDeptIds(userId,ids);
        return ApiResp.success("ok");
    }

    @GetMapping(value = "/dept/keys/{userId}")
    public ApiResp<?> findUserDeptKeys(@PathVariable(value = "userId") Long userId) {
        List<String> ret = baseService.findUserDeptKeys(userId);
        return ApiResp.success(ret);
    }


}