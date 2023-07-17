package com.i2f.sys.controller;


import com.i2f.sys.data.vo.SysNoticeVo;
import com.i2f.sys.service.ISysNoticeService;
import i2f.core.std.api.ApiPage;
import i2f.core.std.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:43:00
 * @desc sys_notice 通知表
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/notice")
public class SysNoticeController {


    @Autowired
    private ISysNoticeService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(SysNoticeVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<SysNoticeVo> page = new ApiPage<>(index, size);
        ApiPage<SysNoticeVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(SysNoticeVo webVo) {
        List<SysNoticeVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        SysNoticeVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody SysNoticeVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody SysNoticeVo webVo) {
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