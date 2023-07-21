package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.framework.security.AuthUtils;
import com.i2f.sys.data.vo.SysDeptResourcesVo;
import com.i2f.sys.mapper.SysDeptResourcesMapper;
import com.i2f.sys.service.ISysDeptResourcesService;
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
 * @date 2023-07-17 08:39:03
 * @desc sys_dept_resources 部门资源表
 */
@Slf4j
@Service
public class SysDeptResourcesServiceImpl implements ISysDeptResourcesService {

    public static final String UNIQUE_KEY = "unique_key";

    @Resource
    private SysDeptResourcesMapper baseMapper;

    @Override
    public ApiPage<SysDeptResourcesVo> page(SysDeptResourcesVo webVo,
                                            ApiPage<SysDeptResourcesVo> page) {

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<SysDeptResourcesVo> list = baseMapper.page(webVo);
        PageInfo<SysDeptResourcesVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<SysDeptResourcesVo> list(SysDeptResourcesVo webVo) {

        List<SysDeptResourcesVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public SysDeptResourcesVo find(Long id) {

        SysDeptResourcesVo ret = baseMapper.findByPk(id);
        return ret;
    }

    @Override
    public List<SysDeptResourcesVo> tree(SysDeptResourcesVo webVo) {
        List<SysDeptResourcesVo> list = baseMapper.list(webVo);
        List<SysDeptResourcesVo> tree = TreeConvertUtil.list2Tree(list);
        return tree;
    }


    public void prepare(SysDeptResourcesVo webVo) {
        Date now = new Date();
        String currentUserId = AuthUtils.currentUserIdStr();
        if (webVo.getId() == null) {
            webVo.setCreateTime(now);
            webVo.setCreateUser(currentUserId);
        } else {
            webVo.setUpdateTime(now);
            webVo.setUpdateUser(currentUserId);
        }
    }

    public void uniqueCheck(SysDeptResourcesVo webVo) {
        Collection<Object> excludesIds = null;
        if (webVo.getId() != null) {
            excludesIds = Arrays.asList(webVo.getId());
        }
        int cnt = baseMapper.countOfKey(webVo.getDeptId(),webVo.getPermKey(), excludesIds);
        Checker.begin(true)
                .isExTrueMsg("permKey已存在", cnt > 0);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void add(SysDeptResourcesVo webVo) {
        if (webVo.getStatus() == null) {
            webVo.setStatus(1);
        }
        if(webVo.getParentId()==null){
            webVo.setParentId(0L);
        }
        prepare(webVo);
        Checker.begin(true)
                .isNullMsg(webVo.getDeptId(), "deptId必填参数")
                .isEmptyStrMsg(webVo.getPermKey(), "permKey必填参数")
                .isEmptyStrMsg(webVo.getName(), "name必填参数")
                .notInMsg("不正确的状态标志位", webVo.getStatus(), 0, 1);
        uniqueCheck(webVo);
        baseMapper.insertSelective(webVo);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void update(SysDeptResourcesVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(), "ID必填参数");
        webVo.setStatus(null);
        prepare(webVo);
        uniqueCheck(webVo);
        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID不能为空");
        SysDeptResourcesVo updInfo = new SysDeptResourcesVo();
        updInfo.setId(id);
        updInfo.setStatus(99);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }


    @Override
    public void enable(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID不能为空");

        SysDeptResourcesVo updInfo = new SysDeptResourcesVo();
        updInfo.setId(id);
        updInfo.setStatus(1);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }

    @Override
    public void disable(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID不能为空");

        SysDeptResourcesVo updInfo = new SysDeptResourcesVo();
        updInfo.setId(id);
        updInfo.setStatus(0);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }

}