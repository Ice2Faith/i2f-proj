package com.i2f.hello.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Ice2Faith
 * @date 2023/3/24 20:54
 * @desc
 */
@Slf4j
@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('MENU_home') && hasRole('admin')")
    @PostMapping(value = "/hello")
    public Object hello(HttpServletRequest request, @RequestBody Map<String, Object> body) {
        body.put("addr", request.getRemoteAddr());
        body.put("port", request.getRemotePort());
        String encode = passwordEncoder.encode("123456");
        System.out.println("encode:" + encode);
        return body;
    }

}