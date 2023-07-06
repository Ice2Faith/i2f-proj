package com.i2f.sys.controller;


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


}