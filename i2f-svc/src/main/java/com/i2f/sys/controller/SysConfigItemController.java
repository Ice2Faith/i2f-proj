package com.i2f.sys.controller;


import com.i2f.sys.data.vo.SysConfigItemVo;
import com.i2f.sys.service.ISysConfigItemService;
import i2f.core.std.api.ApiPage;
import i2f.core.std.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-06 16:46:18
 * @desc sys_config
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/config/item")
public class SysConfigItemController {

    @Autowired
    private ISysConfigItemService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(SysConfigItemVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<SysConfigItemVo> page = new ApiPage<>(index, size);
        ApiPage<SysConfigItemVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(SysConfigItemVo webVo) {
        List<SysConfigItemVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        SysConfigItemVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody SysConfigItemVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody SysConfigItemVo webVo) {
        webVo.setId(id);
        baseService.update(webVo);
        return ApiResp.success("ok");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResp<?> delete(@PathVariable(value = "id") Long id) {
        baseService.delete(id);
        return ApiResp.success("ok");
    }

    @GetMapping(value = "/items/id/{configId}")
    public ApiResp<?> findItemByConfigId(@PathVariable(value = "configId") Long configId) {
        List<SysConfigItemVo> list = baseService.items(configId);
        return ApiResp.success(list);
    }

    @GetMapping(value = "/items/key/{configKey}")
    public ApiResp<?> findItemByConfigKey(@PathVariable(value = "configKey") String configKey) {
        List<SysConfigItemVo> list = baseService.items(configKey);
        return ApiResp.success(list);
    }

    @GetMapping(value = "/children/{configId}")
    public ApiResp<?> findItemsChildren(@PathVariable(value = "configId") Long configId,SysConfigItemVo webVo) {
        List<SysConfigItemVo> list = baseService.children(configId,webVo);
        return ApiResp.success(list);
    }

    @GetMapping(value = "/tree/id/{configId}")
    public ApiResp<?> findItemTreeByConfigId(@PathVariable(value = "configId") Long configId) {
        List<SysConfigItemVo> list = baseService.tree(configId);
        return ApiResp.success(list);
    }

    @GetMapping(value = "/tree/key/{configKey}")
    public ApiResp<?> findItemTreeByConfigKey(@PathVariable(value = "configKey") String configKey) {
        List<SysConfigItemVo> list = baseService.tree(configKey);
        return ApiResp.success(list);
    }

    @PutMapping(value = "/items/update/{configId}")
    public ApiResp<?> updateConfigItems(@PathVariable(value = "configId") Long configId,
                                        @RequestBody List<SysConfigItemVo> items) {

        baseService.updateItems(configId, items);
        return ApiResp.success("ok");
    }

    @DeleteMapping(value = "/items/delete/{configId}")
    public ApiResp<?> deleteConfigItems(@PathVariable(value = "configId") Long configId) {
        baseService.deleteItems(configId);
        return ApiResp.success("ok");
    }
}