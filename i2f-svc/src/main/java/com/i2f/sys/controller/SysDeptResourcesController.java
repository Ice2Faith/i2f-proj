package com.i2f.sys.controller;


import com.i2f.sys.data.vo.SysDeptResourcesVo;
import com.i2f.sys.service.ISysDeptResourcesService;
import i2f.core.std.api.ApiPage;
import i2f.core.std.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:39:03
 * @desc sys_dept_resources 部门资源表
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/dept/resources")
public class SysDeptResourcesController {


    @Autowired
    private ISysDeptResourcesService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(SysDeptResourcesVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<SysDeptResourcesVo> page = new ApiPage<>(index, size);
        ApiPage<SysDeptResourcesVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(SysDeptResourcesVo webVo) {
        List<SysDeptResourcesVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        SysDeptResourcesVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/tree")
    public ApiResp<?> tree(SysDeptResourcesVo webVo) {
        List<SysDeptResourcesVo> ret = baseService.tree(webVo);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody SysDeptResourcesVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody SysDeptResourcesVo webVo) {
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

}