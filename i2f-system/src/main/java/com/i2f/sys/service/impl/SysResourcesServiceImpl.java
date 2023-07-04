package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.sys.data.vo.SysResourcesVo;
import com.i2f.sys.mapper.SysResourcesMapper;
import com.i2f.sys.service.ISysResourcesService;
import i2f.core.std.api.ApiPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:32:40
 * @desc sys_resources 资源表
 */
@Slf4j
@Service
public class SysResourcesServiceImpl implements ISysResourcesService {


    @Resource
    private SysResourcesMapper baseMapper;

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
    public SysResourcesVo find(Long id) {

        SysResourcesVo ret = baseMapper.findByPk(id);
        return ret;
    }

    @Override
    public void add(SysResourcesVo webVo) {

        baseMapper.insertSelective(webVo);
    }

    @Override
    public void update(SysResourcesVo webVo) {

        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {

        baseMapper.deleteByPk(id);
    }
}