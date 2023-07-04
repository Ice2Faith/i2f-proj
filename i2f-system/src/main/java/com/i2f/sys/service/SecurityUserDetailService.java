package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysUserVo;
import com.i2f.sys.mapper.SysUserMapper;
import i2f.springboot.security.model.SecurityGrantedAuthority;
import i2f.springboot.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author Ice2Faith
 * @date 2023/7/4 9:48
 * @desc
 */
@Component
public class SecurityUserDetailService implements UserDetailsService {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUserVo userVo = sysUserMapper.findByUsername(username);
        if (userVo == null) {
            throw new UsernameNotFoundException("user[" + username + "] not found in system");
        }
        if (StringUtils.isEmpty(userVo.getPassword())
                || "-".equals(userVo.getPassword())) {
            String encPass = this.passwordEncoder.encode("123456");
            SysUserVo updInfo = new SysUserVo();
            updInfo.setId(userVo.getId());
            updInfo.setPassword(encPass);
            sysUserMapper.updateSelectiveByPk(updInfo);
            userVo = sysUserMapper.findByPk(userVo.getId());
        }

        SecurityUser ret = new SecurityUser(userVo.getUsername(),
                userVo.getPassword(),
                Arrays.asList(
                        new SecurityGrantedAuthority("ROLE_admin"),
                        new SecurityGrantedAuthority("index:home"),
                        new SecurityGrantedAuthority("/home")
                )
        );
        return ret;
    }
}
