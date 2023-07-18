package com.i2f.sys.controller;


import com.i2f.sys.data.vo.SysDeptRoleVo;
import com.i2f.sys.service.ISysDeptRoleService;
import i2f.core.std.api.ApiPage;
import i2f.core.std.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:38:00
 * @desc sys_dept_role 部门角色表
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/dept/role")
public class SysDeptRoleController {


    @Autowired
    private ISysDeptRoleService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(SysDeptRoleVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<SysDeptRoleVo> page = new ApiPage<>(index, size);
        ApiPage<SysDeptRoleVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(SysDeptRoleVo webVo) {
        List<SysDeptRoleVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        SysDeptRoleVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody SysDeptRoleVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody SysDeptRoleVo webVo) {
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

}