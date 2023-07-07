package com.i2f.biz.passwordBook.controller;


import com.i2f.biz.passwordBook.data.vo.BizPasswordBookVo;
import com.i2f.biz.passwordBook.service.IBizPasswordBookService;
import i2f.core.std.api.ApiPage;
import i2f.core.std.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:55:59
 * @desc biz_password_book 密码本
 */
@Slf4j
@RestController
@RequestMapping("/api/biz/passwordBook")
public class BizPasswordBookController {


    @Autowired
    private IBizPasswordBookService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(BizPasswordBookVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<BizPasswordBookVo> page = new ApiPage<>(index, size);
        ApiPage<BizPasswordBookVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(BizPasswordBookVo webVo) {
        List<BizPasswordBookVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        BizPasswordBookVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody BizPasswordBookVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody BizPasswordBookVo webVo) {
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