package com.i2f.sys.service;

import com.i2f.sys.data.vo.SysDeptVo;
import com.i2f.sys.data.vo.SysResourcesVo;
import com.i2f.sys.data.vo.SysRoleVo;
import com.i2f.sys.data.vo.SysUserVo;
import i2f.core.check.CheckUtil;
import i2f.springboot.security.model.SecurityGrantedAuthority;
import i2f.springboot.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Ice2Faith
 * @date 2023/7/4 9:48
 * @desc
 */
@Component
public class SecurityUserDetailService implements UserDetailsService {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUserVo userVo = sysUserService.findByUsername(username);
        if (userVo == null) {
            throw new UsernameNotFoundException("user[" + username + "] not found in system");
        }
        String pass = sysUserService.findUserPassword(userVo.getId());
        userVo.setPassword(pass);
        if (StringUtils.isEmpty(userVo.getPassword())
                || "-".equals(userVo.getPassword())) {
            sysUserService.changePassword(userVo.getId(), "123456");
            userVo = sysUserService.find(userVo.getId());
        }

        List<SysRoleVo> roles = sysUserService.findUserRoles(userVo.getId());
        List<SysResourcesVo> resources = sysUserService.findUserResources(userVo.getId());
        List<SysDeptVo> depts = sysUserService.findUserDepts(userVo.getId());

        Set<String> urlsSet = new TreeSet<>();
        Set<String> permsSet = new TreeSet<>();
        Set<String> menusSet = new TreeSet<>();
        Set<String> rolesSet = new TreeSet<>();
        Set<String> deptsSet = new TreeSet<>();

        Set<String> resSet = new TreeSet<>();

        for (SysRoleVo role : roles) {
            String roleKey = role.getRoleKey();
            if (!CheckUtil.isEmptyStr(roleKey)) {
                rolesSet.add(roleKey);
                resSet.add("ROLE_" + roleKey);
            }
        }

        for (SysDeptVo dept : depts) {
            String deptKey = dept.getDeptKey();
            if (!CheckUtil.isEmptyStr(deptKey)) {
                deptsSet.add(deptKey);
                resSet.add("DEPT_" + deptKey);
            }
        }

        for (SysResourcesVo resource : resources) {
            String permKey = resource.getPermKey();
            if (!CheckUtil.isEmptyStr(permKey)) {
                permsSet.add(permKey);
                resSet.add(permKey);
            }
            String menuKey = resource.getMenuKey();
            if (!CheckUtil.isEmptyStr(menuKey)) {
                menusSet.add(menuKey);
                resSet.add("MENU_" + menuKey);
            }
            String url = resource.getUrl();
            if (!CheckUtil.isEmptyStr(url)) {
                urlsSet.add(url);
                resSet.add("URL_" + url);
            }
        }

        userVo.setUrls(urlsSet);
        userVo.setPerms(permsSet);
        userVo.setMenus(menusSet);
        userVo.setRoles(rolesSet);
        userVo.setDepts(deptsSet);

        List<GrantedAuthority> authorities = new ArrayList<>(300);
        for (String item : resSet) {
            authorities.add(new SecurityGrantedAuthority(item));
        }


        SecurityUser ret = new SecurityUser(userVo.getUsername(),
                userVo.getPassword(),
                authorities
        );

        userVo.setPassword(null);
        ret.setEnabled(userVo.getStatus() == 1);
        ret.setTag(userVo);

        return ret;
    }
}
