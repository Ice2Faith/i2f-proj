package com.i2f.biz.taskList.controller;


import com.i2f.biz.taskList.data.vo.BizTaskListHistoryVo;
import com.i2f.biz.taskList.data.vo.BizTaskListVo;
import com.i2f.biz.taskList.service.IBizTaskListService;
import i2f.core.std.api.ApiPage;
import i2f.core.std.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:53:41
 * @desc biz_task_list 任务列表
 */
@Slf4j
@RestController
@RequestMapping("/api/biz/taskList")
public class BizTaskListController {


    @Autowired
    private IBizTaskListService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(BizTaskListVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<BizTaskListVo> page = new ApiPage<>(index, size);
        ApiPage<BizTaskListVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(BizTaskListVo webVo) {
        List<BizTaskListVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        BizTaskListVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody BizTaskListVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody BizTaskListVo webVo) {
        webVo.setId(id);
        baseService.update(webVo);
        return ApiResp.success("ok");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResp<?> delete(@PathVariable(value = "id") Long id) {
        baseService.delete(id);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/suspend/{id}")
    public ApiResp<?> suspend(@PathVariable(value = "id") Long id) {
        baseService.suspend(id);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/run/{id}")
    public ApiResp<?> run(@PathVariable(value = "id") Long id) {
        baseService.run(id);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/finish/{id}")
    public ApiResp<?> finish(@PathVariable(value = "id") Long id) {
        baseService.finish(id);
        return ApiResp.success("ok");
    }

    @GetMapping(value = "/history/{id}")
    public ApiResp<?> history(@PathVariable(value = "id") Long id) {
        List<BizTaskListHistoryVo> ret = baseService.history(id);
        return ApiResp.success(ret);
    }
}