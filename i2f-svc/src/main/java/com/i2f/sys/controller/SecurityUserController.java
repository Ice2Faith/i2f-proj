package com.i2f.sys.sys.controller;

import i2f.spring.security.SecurityUtil;
import i2f.springboot.security.model.SecurityUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ice2Faith
 * @date 2023/7/4 10:04
 * @desc
 */
@RequestMapping("sys/user")
@RestController
public class SecurityUserController {

    @PostMapping("info")
    public SecurityUser getUserInfo() {
        SecurityUser user = (SecurityUser) SecurityUtil.getPrincipal();
        user.setPassword(null);
        return user;
    }
}
