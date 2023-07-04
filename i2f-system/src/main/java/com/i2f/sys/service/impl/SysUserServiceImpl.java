package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.sys.data.vo.SysUserVo;
import com.i2f.sys.mapper.SysUserMapper;
import com.i2f.sys.service.ISysUserService;
import i2f.core.std.api.ApiPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 15:47:56
 * @desc sys_user 用户表
 */
@Slf4j
@Service
public class SysUserServiceImpl implements ISysUserService {


    @Resource
    private SysUserMapper baseMapper;

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

    @Override
    public void add(SysUserVo webVo) {

        baseMapper.insertSelective(webVo);
    }

    @Override
    public void update(SysUserVo webVo) {

        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {

        baseMapper.deleteByPk(id);
    }
}