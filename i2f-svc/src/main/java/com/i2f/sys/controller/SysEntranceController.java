package com.i2f.sys.controller;

import com.i2f.sys.data.vo.SysUserVo;
import com.i2f.sys.service.ISysUserService;
import i2f.core.std.api.ApiResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ice2Faith
 * @date 2023/7/4 10:04
 * @desc
 */
@RequestMapping("/sys/entrance")
@RestController
public class SysEntranceController {

    @Autowired
    private ISysUserService baseService;

    @PostMapping("registry")
    public ApiResp<?> registry(@RequestBody SysUserVo webVo) {
        baseService.registry(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "passReset")
    public ApiResp<?> passReset(@RequestBody SysUserVo webVo) {
        baseService.passReset(webVo);
        return ApiResp.success("ok");
    }
}
