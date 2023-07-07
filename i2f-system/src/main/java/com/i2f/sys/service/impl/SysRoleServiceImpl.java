package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.framework.security.SecurityUtils;
import com.i2f.sys.data.vo.SysResourcesVo;
import com.i2f.sys.data.vo.SysRoleResourcesVo;
import com.i2f.sys.data.vo.SysRoleVo;
import com.i2f.sys.mapper.SysResourcesMapper;
import com.i2f.sys.mapper.SysRoleMapper;
import com.i2f.sys.mapper.SysRoleResourcesMapper;
import com.i2f.sys.service.ISysRoleService;
import i2f.core.check.Checker;
import i2f.core.convert.TreeConvertUtil;
import i2f.core.std.api.ApiPage;
import i2f.springboot.redisson.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:28:51
 * @desc sys_role 角色表
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    public static final String UNIQUE_KEY="unique_key";

    @Resource
    private SysRoleMapper baseMapper;

    @Resource
    private SysRoleResourcesMapper sysRoleResourcesMapper;

    @Resource
    private SysResourcesMapper sysResourcesMapper;


    @Override
    public ApiPage<SysRoleVo> page(SysRoleVo webVo,
                                   ApiPage<SysRoleVo> page) {

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<SysRoleVo> list = baseMapper.page(webVo);
        PageInfo<SysRoleVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<SysRoleVo> list(SysRoleVo webVo) {

        List<SysRoleVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public SysRoleVo find(Long id) {

        SysRoleVo ret = baseMapper.findByPk(id);
        return ret;
    }

    public void prepare(SysRoleVo webVo){
        Date now=new Date();
        String currentUserId = SecurityUtils.currentUserIdStr();
        if(webVo.getId()==null){
            webVo.setCreateTime(now);
            webVo.setCreateUser(currentUserId);
        }else{
            webVo.setUpdateTime(now);
            webVo.setUpdateUser(currentUserId);
        }
    }

    public void uniqueCheck(SysRoleVo webVo){
        Collection<Object> excludesIds=null;
        if(webVo.getId()!=null){
            excludesIds= Arrays.asList(webVo.getId());
        }
        int cnt=baseMapper.countOfKey(webVo.getRoleKey(),excludesIds);
        Checker.begin(true)
                .isExTrueMsg("roleKey已存在",cnt>0);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void add(SysRoleVo webVo) {
        if(webVo.getStatus()==null){
            webVo.setStatus(1);
        }
        if(webVo.getDelFlag()==null){
            webVo.setDelFlag(1);
        }
        if(webVo.getSysFlag()==null){
            webVo.setSysFlag(0);
        }
        if(webVo.getSysFlag()==1){
            webVo.setStatus(1);
            webVo.setDelFlag(0);
        }
        prepare(webVo);
        Checker.begin(true)
                .isEmptyStrMsg(webVo.getRoleKey(),"roleKey必填参数")
                .isEmptyStrMsg(webVo.getRoleName(),"roleName必填参数")
                .notInMsg("不正确的状态标志位",webVo.getStatus(),0,1)
                .notInMsg("不正确的删除标志位",webVo.getDelFlag(),0,1)
                .notInMsg("不正确的系统标志位",webVo.getSysFlag(),0,1);
        uniqueCheck(webVo);
        baseMapper.insertSelective(webVo);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void update(SysRoleVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(),"ID必填参数");
        webVo.setStatus(null);
        prepare(webVo);
        uniqueCheck(webVo);
        baseMapper.updateSelectiveByPk(webVo);
    }


    @Override
    public void delete(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID不能为空");
        SysRoleVo updInfo=new SysRoleVo();
        updInfo.setId(id);
        prepare(updInfo);
        baseMapper.deleteLogicalByPk(updInfo);
    }

    @Override
    public void enable(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID不能为空");

        SysRoleVo updInfo=new SysRoleVo();
        updInfo.setId(id);
        updInfo.setStatus(1);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }

    @Override
    public void disable(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID不能为空");

        SysRoleVo updInfo=new SysRoleVo();
        updInfo.setId(id);
        prepare(updInfo);
        baseMapper.disableByPk(updInfo);
    }


    @Override
    public List<SysResourcesVo> findRoleResources(Long roleId) {
        return sysResourcesMapper.findRoleResources(roleId);
    }

    @Override
    public List<SysResourcesVo> treeRoleResources(Long roleId) {
        List<SysResourcesVo> list = sysResourcesMapper.findRoleResources(roleId);
        List<SysResourcesVo> tree = TreeConvertUtil.list2Tree(list);
        return tree;
    }

    @Override
    public List<String> findRolePermKeys(Long roleId) {
        return sysResourcesMapper.findRolePermKeys(roleId);
    }


    @Override
    public void deleteRoleResources(Long roleId) {
        Checker.begin(true)
                .isNullMsg(roleId,"roleId必填参数");

        sysRoleResourcesMapper.deleteRoleResources(roleId);
    }

    @Override
    public void updateRoleResources(Long roleId, Collection<Long> resIds) {
        Checker.begin(true)
                .isNullMsg(roleId,"roleId必填参数")
                .isNullMsg(resIds,"resIds必填参数");

        sysRoleResourcesMapper.deleteRoleResources(roleId);

        if(resIds.isEmpty()){
            return;
        }

        Date now=new Date();
        String currentUserId=SecurityUtils.currentUserIdStr();

        Set<Long> ids=new LinkedHashSet<>(resIds);
        List<SysRoleResourcesVo> list=new LinkedList<>();
        for (Long id : ids) {
            SysRoleResourcesVo item=new SysRoleResourcesVo();
            item.setRoleId(roleId);
            item.setResId(id);
            item.setCreateTime(now);
            item.setCreateUser(currentUserId);
            list.add(item);
        }

        sysRoleResourcesMapper.insertBatch(list);
    }

    @Override
    public void grantAllResources(Long roleId) {
        sysRoleResourcesMapper.grantAllResourcesByRoleId(roleId);
    }

    @Override
    public void grantAllResources(String roleKey) {
        sysRoleResourcesMapper.grantAllResourcesByRoleKey(roleKey);
    }

    @Override
    public void grantLikeResources(Long toRoleId, Long fromRoleId) {
        sysRoleResourcesMapper.grantLikeResourcesByRoleId(toRoleId, fromRoleId);
    }

    @Override
    public void grantLikeResources(String toRoleKey, String fromRoleKey) {
        sysRoleResourcesMapper.grantLikeResourcesByRoleKey(toRoleKey, fromRoleKey);
    }
}