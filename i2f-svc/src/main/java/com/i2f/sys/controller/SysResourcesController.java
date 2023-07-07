package com.i2f.sys.controller;


import com.i2f.sys.data.vo.SysResourcesVo;
import com.i2f.sys.service.ISysResourcesService;
import i2f.core.std.api.ApiPage;
import i2f.core.std.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:32:40
 * @desc sys_resources 资源表
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/resources")
public class SysResourcesController {


    @Autowired
    private ISysResourcesService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(SysResourcesVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<SysResourcesVo> page = new ApiPage<>(index, size);
        ApiPage<SysResourcesVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(SysResourcesVo webVo) {
        List<SysResourcesVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/tree")
    public ApiResp<?> tree(SysResourcesVo webVo) {
        List<SysResourcesVo> ret = baseService.tree(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        SysResourcesVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody SysResourcesVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody SysResourcesVo webVo) {
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