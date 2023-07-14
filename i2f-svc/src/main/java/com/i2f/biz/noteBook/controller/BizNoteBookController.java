package com.i2f.biz.noteBook.controller;


import com.i2f.biz.noteBook.data.vo.BizNoteBookVo;
import com.i2f.biz.noteBook.service.IBizNoteBookService;
import i2f.core.std.api.ApiPage;
import i2f.core.std.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:57:13
 * @desc biz_note_book 笔记本
 */
@Slf4j
@RestController
@RequestMapping("/api/biz/noteBook")
public class BizNoteBookController {

    @Autowired
    private IBizNoteBookService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(BizNoteBookVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<BizNoteBookVo> page = new ApiPage<>(index, size);
        ApiPage<BizNoteBookVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(BizNoteBookVo webVo) {
        List<BizNoteBookVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/children")
    public ApiResp<?> children(BizNoteBookVo webVo) {
        List<BizNoteBookVo> ret = baseService.children(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        BizNoteBookVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody BizNoteBookVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody BizNoteBookVo webVo) {
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