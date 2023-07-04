package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.sys.data.vo.SysDeptVo;
import com.i2f.sys.mapper.SysDeptMapper;
import com.i2f.sys.service.ISysDeptService;
import i2f.core.std.api.ApiPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:30:12
 * @desc sys_dept 部门表
 */
@Slf4j
@Service
public class SysDeptServiceImpl implements ISysDeptService {


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

    @Override
    public void add(SysDeptVo webVo) {

        baseMapper.insertSelective(webVo);
    }

    @Override
    public void update(SysDeptVo webVo) {

        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {

        baseMapper.deleteByPk(id);
    }
}