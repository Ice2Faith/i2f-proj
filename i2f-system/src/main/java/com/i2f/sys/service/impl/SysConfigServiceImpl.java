package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.sys.data.vo.SysConfigVo;
import com.i2f.sys.mapper.SysConfigMapper;
import com.i2f.sys.service.ISysConfigService;
import i2f.core.std.api.ApiPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:31:20
 * @desc sys_config 配置表
 */
@Slf4j
@Service
public class SysConfigServiceImpl implements ISysConfigService {


    @Resource
    private SysConfigMapper baseMapper;

    @Override
    public ApiPage<SysConfigVo> page(SysConfigVo webVo,
                                     ApiPage<SysConfigVo> page) {

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<SysConfigVo> list = baseMapper.page(webVo);
        PageInfo<SysConfigVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<SysConfigVo> list(SysConfigVo webVo) {

        List<SysConfigVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public SysConfigVo find(Long id) {

        SysConfigVo ret = baseMapper.findByPk(id);
        return ret;
    }

    @Override
    public void add(SysConfigVo webVo) {

        baseMapper.insertSelective(webVo);
    }

    @Override
    public void update(SysConfigVo webVo) {

        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {

        baseMapper.deleteByPk(id);
    }
}