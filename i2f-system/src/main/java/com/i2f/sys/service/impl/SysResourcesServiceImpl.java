package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.framework.security.SecurityUtils;
import com.i2f.sys.data.vo.SysResourcesVo;
import com.i2f.sys.mapper.SysResourcesMapper;
import com.i2f.sys.mapper.SysRoleResourcesMapper;
import com.i2f.sys.service.ISysResourcesService;
import i2f.core.check.CheckUtil;
import i2f.core.check.Checker;
import i2f.core.convert.TreeConvertUtil;
import i2f.core.std.api.ApiPage;
import i2f.springboot.redisson.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:32:40
 * @desc sys_resources 资源表
 */
@Slf4j
@Service
public class SysResourcesServiceImpl implements ISysResourcesService {
    public static final String UNIQUE_KEY="unique_key";

    @Resource
    private SysResourcesMapper baseMapper;

    @Resource
    private SysRoleResourcesMapper sysRoleResourcesMapper;

    @Override
    public ApiPage<SysResourcesVo> page(SysResourcesVo webVo,
                                        ApiPage<SysResourcesVo> page) {

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<SysResourcesVo> list = baseMapper.page(webVo);
        PageInfo<SysResourcesVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<SysResourcesVo> list(SysResourcesVo webVo) {

        List<SysResourcesVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public List<SysResourcesVo> children(SysResourcesVo webVo) {
        List<SysResourcesVo> list = baseMapper.children(webVo);

        return list;
    }

    @Override
    public List<SysResourcesVo> tree(SysResourcesVo webVo) {
        List<SysResourcesVo> list = baseMapper.list(webVo);
        List<SysResourcesVo> tree = TreeConvertUtil.list2Tree(list);
        return tree;
    }

    @Override
    public SysResourcesVo find(Long id) {

        SysResourcesVo ret = baseMapper.findByPk(id);
        return ret;
    }

    public void prepare(SysResourcesVo webVo) {
        Date now = new Date();
        String currentUserId = SecurityUtils.currentUserIdStr();
        if(webVo.getId()==null){
            webVo.setCreateTime(now);
            webVo.setCreateUser(currentUserId);
        }else{
            webVo.setUpdateTime(now);
            webVo.setUpdateUser(currentUserId);
        }
    }

    public void uniqueCheck(SysResourcesVo webVo){
        Collection<Object> excludesIds=null;
        if(webVo.getId()!=null){
            excludesIds= Arrays.asList(webVo.getId());
        }
        if(!CheckUtil.isEmptyStr(webVo.getMenuKey())){
            int cnt=baseMapper.countOfMenuKey(webVo.getMenuKey(),excludesIds);

            Checker.begin(true)
                    .isExTrueMsg("menuKey已存在",cnt>0);
        }

        if(!CheckUtil.isEmptyStr(webVo.getPermKey())){
            int cnt=baseMapper.countOfPermKey(webVo.getPermKey(),excludesIds);

            Checker.begin(true)
                    .isExTrueMsg("permKey已存在",cnt>0);
        }
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void add(SysResourcesVo webVo) {
        prepare(webVo);
        if(webVo.getStatus()==null){
            webVo.setStatus(1);
        }
        if(webVo.getType()==null){
            webVo.setType(0);
        }
        if(webVo.getOrderIndex()==null){
            webVo.setOrderIndex(0);
        }
        if(webVo.getParentId()==null){
            webVo.setParentId(0L);
        }
        Checker.begin(true)
                .notInMsg("不正确的类型标志位",webVo.getType(),0,1)
                .notInMsg("不正确的状态标志位",webVo.getStatus(),0,1);
        uniqueCheck(webVo);
        baseMapper.insertSelective(webVo);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void update(SysResourcesVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(),"ID必填参数");
        prepare(webVo);
        webVo.setStatus(null);
        if(webVo.getType()!=null){
            Checker.begin(true)
                    .notInMsg("不正确的类型标志位",webVo.getType(),0,1);
        }
        uniqueCheck(webVo);
        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID必填参数");
        SysResourcesVo updInfo=new SysResourcesVo();
        updInfo.setId(id);
        updInfo.setStatus(99);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);

        sysRoleResourcesMapper.deleteByResId(id);
    }

    @Override
    public void enable(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID必填参数");
        SysResourcesVo updInfo=new SysResourcesVo();
        updInfo.setId(id);
        updInfo.setStatus(1);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }

    @Override
    public void disable(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID必填参数");
        SysResourcesVo updInfo=new SysResourcesVo();
        updInfo.setId(id);
        updInfo.setStatus(0);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }



}