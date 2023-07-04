package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.sys.data.vo.SysRoleVo;
import com.i2f.sys.mapper.SysRoleMapper;
import com.i2f.sys.service.ISysRoleService;
import i2f.core.std.api.ApiPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-04 17:28:51
 * @desc sys_role 角色表
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements ISysRoleService {


    @Resource
    private SysRoleMapper baseMapper;

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

    @Override
    public void add(SysRoleVo webVo) {

        baseMapper.insertSelective(webVo);
    }

    @Override
    public void update(SysRoleVo webVo) {

        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {

        baseMapper.deleteByPk(id);
    }
}