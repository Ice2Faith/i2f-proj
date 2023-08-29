package com.i2f.framework.security;

import i2f.core.j2ee.firewall.FirewallFilter;
import i2f.springboot.secure.core.SecureTransferFilter;
import i2f.springboot.security.SecurityConfig;
import i2f.springboot.security.impl.ISecurityConfigListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.stereotype.Component;

/**
 * @author Ice2Faith
 * @date 2023/7/3 18:01
 * @desc
 */
@Component
public class SecureSecurityAdapter implements ISecurityConfigListener {
    @Autowired
    private SecureTransferFilter secureTransferFilter;

    @Autowired
    private FirewallFilter firewallFilter;

    @Override
    public void onAfterHttpConfig(HttpSecurity http, SecurityConfig config) {
        http.addFilterBefore(secureTransferFilter, LogoutFilter.class);
        http.addFilterBefore(firewallFilter,SecureTransferFilter.class);
    }
}
