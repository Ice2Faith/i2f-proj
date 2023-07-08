package com.i2f.sys.controller;

import com.i2f.framework.security.SecurityUtils;
import com.i2f.sys.data.vo.SysResourcesVo;
import com.i2f.sys.service.ISysUserService;
import i2f.core.std.api.ApiResp;
import i2f.spring.security.SecurityUtil;
import i2f.springboot.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023/7/4 10:04
 * @desc
 */
@RequestMapping("sys/user")
@RestController
public class SecurityUserController {

    @Autowired
    private ISysUserService baseService;

    @PostMapping("info")
    public SecurityUser getUserInfo() {
        SecurityUser user = (SecurityUser) SecurityUtil.getPrincipal();
        user.setPassword(null);
        return user;
    }

    @PostMapping(value = "menus")
    public ApiResp<?> treeUserResourcesMenu() {
        Long userId = SecurityUtils.currentUserId();
        List<SysResourcesVo> ret = baseService.treeUserResourcesMenu(userId);
        return ApiResp.success(ret);
    }
}
