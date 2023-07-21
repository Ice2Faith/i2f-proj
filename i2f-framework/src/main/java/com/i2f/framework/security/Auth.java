package com.i2f.framework.security;

import com.i2f.sys.data.vo.SysUserVo;
import i2f.springboot.security.model.SecurityGrantedAuthority;
import i2f.springboot.security.model.SecurityUser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author Ice2Faith
 * @date 2023/7/21 13:49
 * @desc
 */
@Data
@Slf4j
@Component("v")
public class Auth {
    public boolean hasAuthority(String key) {
        SecurityUser user = AuthUtils.currentSecurityUser();
        if (user == null) {
            return false;
        }
        Set<SecurityGrantedAuthority> set = user.getAuthorities();
        if (set == null) {
            return false;
        }
        for (SecurityGrantedAuthority item : set) {
            if(item.getAuthority().equals(key)){
                return true;
            }
        }
        return false;
    }

    public boolean hasDept(String key) {
        SysUserVo user = AuthUtils.currentUser();
        if (user == null) {
            return false;
        }
        Set<String> arr = user.getDepts();
        if (arr == null) {
            return false;
        }
        return arr.contains(key);
    }

    public boolean hasMenu(String key) {
        SysUserVo user = AuthUtils.currentUser();
        if (user == null) {
            return false;
        }
        Set<String> arr = user.getMenus();
        if (arr == null) {
            return false;
        }
        return arr.contains(key);
    }

    public boolean hasPerm(String key) {
        SysUserVo user = AuthUtils.currentUser();
        if (user == null) {
            return false;
        }
        Set<String> arr = user.getPerms();
        if (arr == null) {
            return false;
        }
        return arr.contains(key);
    }

    public boolean hasRole(String key) {
        SysUserVo user = AuthUtils.currentUser();
        if (user == null) {
            return false;
        }
        Set<String> arr = user.getRoles();
        if (arr == null) {
            return false;
        }
        return arr.contains(key);
    }

    public boolean hasUrl(String key) {
        SysUserVo user = AuthUtils.currentUser();
        if (user == null) {
            return false;
        }
        Set<String> arr = user.getUrls();
        if (arr == null) {
            return false;
        }
        return arr.contains(key);
    }

    public boolean hasDeptRole(String deptKey,String roleKey){
        SysUserVo user = AuthUtils.currentUser();
        if (user == null) {
            return false;
        }
        Map<String, Set<String>> map = user.getDeptRoles();
        if(map==null){
            return false;
        }
        if(!map.containsKey(deptKey)){
            return false;
        }
        Set<String> arr = map.get(deptKey);
        if (arr == null) {
            return false;
        }
        return arr.contains(roleKey);
    }

    public boolean hasDeptPerm(String deptKey,String permKey){
        SysUserVo user = AuthUtils.currentUser();
        if (user == null) {
            return false;
        }
        Map<String, Set<String>> map = user.getDeptPerms();
        if(map==null){
            return false;
        }
        if(!map.containsKey(deptKey)){
            return false;
        }
        Set<String> arr = map.get(deptKey);
        if (arr == null) {
            return false;
        }
        return arr.contains(permKey);
    }
}
