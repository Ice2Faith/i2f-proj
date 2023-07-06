package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.framework.security.SecurityUtils;
import com.i2f.sys.data.vo.SysDeptVo;
import com.i2f.sys.mapper.SysDeptMapper;
import com.i2f.sys.service.ISysDeptService;
import i2f.core.check.Checker;
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
 * @date 2023-07-04 17:30:12
 * @desc sys_dept 部门表
 */
@Slf4j
@Service
public class SysDeptServiceImpl implements ISysDeptService {
    public static final String UNIQUE_KEY="unique_key";

    @Resource
    private SysDeptMapper baseMapper;

    @Override
    public ApiPage<SysDeptVo> page(SysDeptVo webVo,
                                   ApiPage<SysDeptVo> page) {

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<SysDeptVo> list = baseMapper.page(webVo);
        PageInfo<SysDeptVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<SysDeptVo> list(SysDeptVo webVo) {

        List<SysDeptVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public SysDeptVo find(Long id) {

        SysDeptVo ret = baseMapper.findByPk(id);
        return ret;
    }


    public void prepare(SysDeptVo webVo){
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

    public void uniqueCheck(SysDeptVo webVo){
        Collection<Object> excludesIds=null;
        if(webVo.getId()!=null){
            excludesIds= Arrays.asList(webVo.getId());
        }
        int cnt=baseMapper.countOfKey(webVo.getDeptKey(),excludesIds);
        Checker.begin(true)
                .isExTrueMsg("deptKey已存在",cnt>0);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void add(SysDeptVo webVo) {
        if(webVo.getStatus()==null){
            webVo.setStatus(1);
        }
        if(webVo.getLevel()==null){
            webVo.setLevel(1);
        }
        Checker.begin(true)
                .isEmptyStrMsg(webVo.getDeptKey(),"deptKey参数必填")
                .isEmptyStrMsg(webVo.getName(),"name参数必填")
                .notInMsg("不正确的状态标志位",webVo.getStatus(),0,1);
        prepare(webVo);
        uniqueCheck(webVo);
        baseMapper.insertSelective(webVo);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void update(SysDeptVo webVo) {
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
        SysDeptVo updInfo=new SysDeptVo();
        updInfo.setId(id);
        prepare(updInfo);
        baseMapper.deleteLogicalByPk(updInfo);
    }

    @Override
    public void enable(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID不能为空");

        SysDeptVo updInfo=new SysDeptVo();
        updInfo.setId(id);
        updInfo.setStatus(1);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }

    @Override
    public void disable(Long id) {
        Checker.begin(true)
                .isNullMsg(id,"ID不能为空");

        SysDeptVo updInfo=new SysDeptVo();
        updInfo.setId(id);
        prepare(updInfo);
        baseMapper.disableByPk(updInfo);
    }



}