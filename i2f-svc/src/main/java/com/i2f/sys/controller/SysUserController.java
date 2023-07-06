package com.i2f.sys.controller;


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


}