package com.i2f.framework.security;

import com.i2f.sys.data.vo.SysUserVo;
import i2f.spring.security.SecurityUtil;
import i2f.springboot.security.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Ice2Faith
 * @date 2023/7/6 9:55
 * @desc
 */
public class AuthUtils {
    public static SecurityUser currentSecurityUser(){
        Authentication auth = SecurityUtil.getAuthentication();
        if(auth==null){
            return null;
        }
        return (SecurityUser)auth.getPrincipal();
    }
    public static String currentUsername(){
        SecurityUser securityUser = currentSecurityUser();
        if(securityUser==null){
            return null;
        }
        return securityUser.getUsername();
    }
    public static Collection<? extends GrantedAuthority> getAuthorities() {
        Authentication auth = SecurityUtil.getAuthentication();
        if(auth==null){
            return null;
        }
        return auth.getAuthorities();
    }
    public static SysUserVo currentUser(){
        SecurityUser securityUser = currentSecurityUser();
        if(securityUser==null){
            return null;
        }
        return (SysUserVo)securityUser.getTag();
    }
    public static Long currentUserId(){
        SysUserVo user=currentUser();
        if(user==null){
            return null;
        }
        return user.getId();
    }
    public static String currentUserIdStr(){
        SysUserVo user=currentUser();
        if(user==null){
            return null;
        }
        return String.valueOf(user.getId());
    }

    public static String currentRealname(){
        SysUserVo user=currentUser();
        if(user==null){
            return null;
        }
        return user.getRealname();
    }
}
