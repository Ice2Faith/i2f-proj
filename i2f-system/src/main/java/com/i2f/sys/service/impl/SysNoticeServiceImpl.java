package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.framework.security.AuthUtils;
import com.i2f.sys.data.vo.SysNoticeVo;
import com.i2f.sys.mapper.SysNoticeMapper;
import com.i2f.sys.service.ISysNoticeService;
import i2f.core.check.Checker;
import i2f.core.std.api.ApiPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:43:00
 * @desc sys_notice 通知表
 */
@Slf4j
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {


    @Resource
    private SysNoticeMapper baseMapper;

    @Override
    public ApiPage<SysNoticeVo> page(SysNoticeVo webVo,
                                     ApiPage<SysNoticeVo> page) {

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<SysNoticeVo> list = baseMapper.page(webVo);
        PageInfo<SysNoticeVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<SysNoticeVo> list(SysNoticeVo webVo) {

        List<SysNoticeVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public SysNoticeVo find(Long id) {

        SysNoticeVo ret = baseMapper.findByPk(id);
        return ret;
    }


    public void prepare(SysNoticeVo webVo) {
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

    @Override
    public void add(SysNoticeVo webVo) {
        Date now=new Date();
        if (webVo.getType() == null) {
            webVo.setType(0);
        }
        if(webVo.getNoticeTime()==null){
            webVo.setNoticeTime(now);
        }
        prepare(webVo);
        Checker.begin(true)
                .isNullMsg(webVo.getType(),"type必填参数")
                .isNullMsg(webVo.getNoticeTime(),"noticeTime必填参数")
                .isEmptyStrMsg(webVo.getTitle(), "roleKey必填参数");
        baseMapper.insertSelective(webVo);
    }

    @Override
    public void update(SysNoticeVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(), "ID必填参数");
        prepare(webVo);
        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID必填参数");
        baseMapper.deleteByPk(id);
    }
}