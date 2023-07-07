package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.framework.security.SecurityUtils;
import com.i2f.sys.data.vo.SysDeptVo;
import com.i2f.sys.data.vo.SysResourcesVo;
import com.i2f.sys.data.vo.SysRoleVo;
import com.i2f.sys.data.vo.SysUserVo;
import com.i2f.sys.mapper.SysDeptMapper;
import com.i2f.sys.mapper.SysResourcesMapper;
import com.i2f.sys.mapper.SysRoleMapper;
import com.i2f.sys.mapper.SysUserMapper;
import com.i2f.sys.service.ISysUserService;
import i2f.core.check.CheckUtil;
import i2f.core.check.Checker;
import i2f.core.convert.TreeConvertUtil;
import i2f.core.std.api.ApiPage;
import i2f.springboot.redisson.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 15:47:56
 * @desc sys_user 用户表
 */
@Slf4j
@Service
public class SysUserServiceImpl implements ISysUserService {
    public static final String UNIQUE_KEY="unique_key";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysUserMapper baseMapper;

    @Resource
    private SysResourcesMapper sysResourcesMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public ApiPage<SysUserVo> page(SysUserVo webVo,
                                   ApiPage<SysUserVo> page) {

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<SysUserVo> list = baseMapper.page(webVo);
        PageInfo<SysUserVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<SysUserVo> list(SysUserVo webVo) {

        List<SysUserVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public SysUserVo find(Long id) {

        SysUserVo ret = baseMapper.findByPk(id);
        return ret;
    }

    private void prepare(SysUserVo webVo){
        Date now=new Date();
        String currentUserId = SecurityUtils.currentUserIdStr();
        if(webVo.getId()==null){
            webVo.setCreateUser(currentUserId);
            webVo.setCreateTime(now);
        }else{
            webVo.setUpdateUser(currentUserId);
            webVo.setUpdateTime(now);
        }
    }

    public void uniqueCheck(SysUserVo webVo){
        Collection<Object> excludesIds=null;
        if(webVo.getId()!=null){
            excludesIds= Arrays.asList(webVo.getId());
        }
        int cnt=baseMapper.countOfKey(webVo.getUsername(),excludesIds);
        Checker.begin(true)
                .isExTrueMsg("username已存在",cnt>0);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void add(SysUserVo webVo) {
        Date now=new Date();
        if(CheckUtil.isEmptyStr(webVo.getRealname())){
            webVo.setRealname("未命名");
        }
        webVo.setRegDate(now);
        if(webVo.getDelFlag()==null){
            webVo.setDelFlag(1);
        }
        if(webVo.getSysFlag()==null){
            webVo.setSysFlag(0);
        }
        if(webVo.getStatus()==null){
            webVo.setStatus(1);
        }
        if(webVo.getSysFlag()==1){
            webVo.setStatus(1);
            webVo.setDelFlag(0);
        }
        prepare(webVo);

        Checker.begin(true)
                .isEmptyStrMsg(webVo.getUsername(),"用户名不能为空")
                .isEmptyStrMsg(webVo.getPassword(),"密码不能为空")
                .notInMsg("不正确的删除标志位",webVo.getDelFlag(),0,1)
                .notInMsg("不正确的系统标志位",webVo.getSysFlag(),0,1)
                .notIn("不正确的状态标志位",webVo.getStatus(),0,1);

        uniqueCheck(webVo);
        webVo.setPassword(passwordEncoder.encode(webVo.getPassword()));
        baseMapper.insertSelective(webVo);
    }

    @Override
    public void update(SysUserVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(),"ID不能为空");
        prepare(webVo);
        webVo.setUsername(null);
        webVo.setPassword(null);
        webVo.setRegDate(null);
        webVo.setStatus(null);
        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID不能为空");
        SysUserVo updInfo=new SysUserVo();
        updInfo.setId(id);
        prepare(updInfo);
        baseMapper.deleteLogicalByPk(updInfo);
    }

    @Override
    public void changePassword(Long id, String password) {
        Checker.begin(true)
                .isNullMsg(id,"ID不能为空")
                .isEmptyStrMsg(password,"密码不能为空");

        SysUserVo updInfo=new SysUserVo();
        updInfo.setId(id);
        updInfo.setPassword(passwordEncoder.encode(password));
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }

    @Override
    public void changePassword(String username, String password) {
        Checker.begin(true)
                .isEmptyStrMsg(username,"用户名不能为空")
                .isEmptyStrMsg(password,"密码不能为空");

        SysUserVo user = findByUsername(username);
        Checker.begin(true)
                .isNullMsg(user,"找不到用户");

        changePassword(user.getId(),password);
    }

    @Override
    public void enable(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID不能为空");

        SysUserVo updInfo=new SysUserVo();
        updInfo.setId(id);
        updInfo.setStatus(1);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }

    @Override
    public void disable(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID不能为空");

        SysUserVo updInfo=new SysUserVo();
        updInfo.setId(id);
        prepare(updInfo);
        baseMapper.disableByPk(updInfo);
    }

    @Override
    public List<SysRoleVo> findUserRoles(Long userId) {
        return sysRoleMapper.findUserRoles(userId);
    }

    @Override
    public List<String> findUserRoleKeys(Long userId) {
        return sysRoleMapper.findUserRoleKeys(userId);
    }

    @Override
    public List<SysResourcesVo> findUserResources(Long userId) {
        return sysResourcesMapper.findUserResources(userId, null);
    }

    @Override
    public List<SysResourcesVo> treeUserResources(Long userId) {
        List<SysResourcesVo> list = sysResourcesMapper.findUserResources(userId, null);
        List<SysResourcesVo> tree = TreeConvertUtil.list2Tree(list);
        return tree;
    }

    @Override
    public List<SysResourcesVo> findUserResourcesMenu(Long userId) {
        return sysResourcesMapper.findUserResources(userId, "0");
    }

    @Override
    public List<SysResourcesVo> treeUserResourcesMenu(Long userId) {
        List<SysResourcesVo> list = sysResourcesMapper.findUserResources(userId, "0");
        List<SysResourcesVo> tree = TreeConvertUtil.list2Tree(list);
        return tree;
    }

    @Override
    public List<String> findUserPermKeys(Long userId) {
        return sysResourcesMapper.findUserPermKeys(userId);
    }

    @Override
    public List<SysDeptVo> findUserDepts(Long userId) {
        return sysDeptMapper.findUserDepts(userId);
    }

    @Override
    public List<String> findUserDeptKeys(Long userId) {
        return sysDeptMapper.findUserDeptKeys(userId);
    }

    @Override
    public SysUserVo findByUsername(String username) {
        return baseMapper.findByUsername(username);
    }
}