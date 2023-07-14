package com.i2f.sys.controller;


import com.i2f.sys.data.vo.SysResourcesVo;
import com.i2f.sys.data.vo.SysRoleVo;
import com.i2f.sys.service.ISysRoleService;
import i2f.core.std.api.ApiPage;
import i2f.core.std.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:28:51
 * @desc sys_role 角色表
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/role")
public class SysRoleController {


    @Autowired
    private ISysRoleService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(SysRoleVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<SysRoleVo> page = new ApiPage<>(index, size);
        ApiPage<SysRoleVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(SysRoleVo webVo) {
        List<SysRoleVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        SysRoleVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody SysRoleVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody SysRoleVo webVo) {
        webVo.setId(id);
        baseService.update(webVo);
        return ApiResp.success("ok");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResp<?> delete(@PathVariable(value = "id") Long id) {
        baseService.delete(id);
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


    @GetMapping(value = "/resources/list/{roleId}")
    public ApiResp<?> findRoleResources(@PathVariable("roleId") Long roleId) {
        List<SysResourcesVo> ret = baseService.findRoleResources(roleId);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/resources/tree/{roleId}")
    public ApiResp<?> treeRoleResources(@PathVariable("roleId") Long roleId) {
        List<SysResourcesVo> ret = baseService.treeRoleResources(roleId);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/perm/keys/{roleId}")
    public ApiResp<?> findRolePermKeys(@PathVariable("roleId") Long roleId) {
        List<String> ret = baseService.findRolePermKeys(roleId);
        return ApiResp.success(ret);
    }

    @DeleteMapping(value = "/resources/delete/{roleId}")
    public ApiResp<?> deleteRoleResources(@PathVariable(value = "roleId") Long roleId) {
        baseService.deleteRoleResources(roleId);
        return ApiResp.success("ok");
    }

    @GetMapping(value = "/resources/ids/{roleId}")
    public ApiResp<?> findRoleResourcesIds(@PathVariable(value = "roleId") Long roleId) {
        List<Long> resIds=baseService.findRoleResourcesIds(roleId);
        return ApiResp.success(resIds);
    }

    @PutMapping(value = "/resources/update/{roleId}")
    public ApiResp<?> updateRoleResources(@PathVariable(value = "roleId") Long roleId,
                                          @RequestBody List<Long> resIds) {
        baseService.updateRoleResources(roleId, resIds);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/resources/grant/all/id/{roleId}")
    public ApiResp<?> grantAllResourcesByRoleId(@PathVariable(value = "roleId") Long roleId) {
        baseService.grantAllResources(roleId);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/resources/grant/all/key/{roleKey}")
    public ApiResp<?> grantAllResourcesByRoleKey(@PathVariable(value = "roleKey") String roleKey) {
        baseService.grantAllResources(roleKey);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/resources/grant/like/id/{fromRoleId}/{toRoleId}")
    public ApiResp<?> grantLikeResourcesByRoleId(@PathVariable(value = "fromRoleId") Long fromRoleId,
                                                 @PathVariable(value = "toRoleId") Long toRoleId) {
        baseService.grantLikeResources(toRoleId, fromRoleId);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/resources/grant/like/key/{fromRoleKey}/{toRoleKey}")
    public ApiResp<?> grantLikeResourcesByRoleKey(@PathVariable(value = "fromRoleKey") String fromRoleKey,
                                                  @PathVariable(value = "toRoleKey") String toRoleKey) {
        baseService.grantLikeResources(toRoleKey, fromRoleKey);
        return ApiResp.success("ok");
    }
}