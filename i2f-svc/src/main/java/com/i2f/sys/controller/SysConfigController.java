package com.i2f.sys.controller;


import com.i2f.sys.data.vo.SysConfigItemVo;
import com.i2f.sys.data.vo.SysConfigVo;
import com.i2f.sys.service.ISysConfigService;
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
@RequestMapping("/api/sys/config")
public class SysConfigController {


    @Autowired
    private ISysConfigService baseService;

    @GetMapping(value = "/page/{size}/{index}")
    public ApiResp<?> page(SysConfigVo webVo,
                           @PathVariable(value = "index") Integer index,
                           @PathVariable(value = "size") Integer size) {
        ApiPage<SysConfigVo> page = new ApiPage<>(index, size);
        ApiPage<SysConfigVo> ret = baseService.page(webVo, page);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/list")
    public ApiResp<?> page(SysConfigVo webVo) {
        List<SysConfigVo> ret = baseService.list(webVo);
        return ApiResp.success(ret);
    }

    @GetMapping(value = "/find/{id}")
    public ApiResp<?> find(@PathVariable(value = "id") Long id) {
        SysConfigVo ret = baseService.find(id);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/add")
    public ApiResp<?> add(@RequestBody SysConfigVo webVo) {
        baseService.add(webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/update/{id}")
    public ApiResp<?> update(@PathVariable(value = "id") Long id,
                             @RequestBody SysConfigVo webVo) {
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
        List<SysConfigItemVo> list = baseService.findConfigItems(configId);
        return ApiResp.success(list);
    }

    @GetMapping(value = "/items/key/{configKey}")
    public ApiResp<?> findItemByConfigKey(@PathVariable(value = "configKey") String configKey) {
        List<SysConfigItemVo> list = baseService.findConfigItems(configKey);
        return ApiResp.success(list);
    }

    @GetMapping(value = "/items/tree/id/{configId}")
    public ApiResp<?> findItemTreeByConfigId(@PathVariable(value = "configId") Long configId) {
        List<SysConfigItemVo> list = baseService.treeConfigItems(configId);
        return ApiResp.success(list);
    }

    @GetMapping(value = "/items/tree/key/{configKey}")
    public ApiResp<?> findItemTreeByConfigKey(@PathVariable(value = "configKey") String configKey) {
        List<SysConfigItemVo> list = baseService.treeConfigItems(configKey);
        return ApiResp.success(list);
    }

    @GetMapping(value = "/item/find/{configItemId}")
    public ApiResp<?> findConfigItem(@PathVariable(value = "configItemId") Long configItemId) {
        SysConfigItemVo ret = baseService.findConfigItem(configItemId);
        return ApiResp.success(ret);
    }

    @PostMapping(value = "/item/add")
    public ApiResp<?> addConfigItem(@RequestBody SysConfigItemVo webVo) {
        baseService.addConfigItem(webVo.getConfigId(), webVo);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/item/update/{configItemId}")
    public ApiResp<?> updateConfigItem(@PathVariable(value = "configItemId") Long configItemId,
                                       @RequestBody SysConfigItemVo webVo) {
        webVo.setId(configItemId);
        baseService.updateConfigItem(webVo);
        return ApiResp.success("ok");
    }

    @DeleteMapping(value = "/item/delete/{configItemId}")
    public ApiResp<?> deleteConfigItem(@PathVariable(value = "configItemId") Long configItemId) {
        baseService.deleteConfigItem(configItemId);
        return ApiResp.success("ok");
    }

    @PutMapping(value = "/items/update/{configId}")
    public ApiResp<?> updateConfigItems(@PathVariable(value = "configId") Long configId,
                                        @RequestBody List<SysConfigItemVo> items) {

        baseService.updateConfigItems(configId, items);
        return ApiResp.success("ok");
    }

    @DeleteMapping(value = "/items/delete/{configId}")
    public ApiResp<?> deleteConfigItems(@PathVariable(value = "configId") Long configId) {
        baseService.deleteConfigItems(configId);
        return ApiResp.success("ok");
    }
}