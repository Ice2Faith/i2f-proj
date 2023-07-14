package com.i2f.biz.noteBook.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.biz.noteBook.data.vo.BizNoteBookVo;
import com.i2f.biz.noteBook.mapper.BizNoteBookMapper;
import com.i2f.biz.noteBook.service.IBizNoteBookService;
import com.i2f.framework.security.SecurityUtils;
import i2f.core.check.Checker;
import i2f.core.std.api.ApiPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:57:13
 * @desc biz_note_book 笔记本
 */
@Slf4j
@Service
public class BizNoteBookServiceImpl implements IBizNoteBookService {


    @Resource
    private BizNoteBookMapper baseMapper;

    @Override
    public ApiPage<BizNoteBookVo> page(BizNoteBookVo webVo,
                                       ApiPage<BizNoteBookVo> page) {
        webVo.setUserId(SecurityUtils.currentUserId());

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<BizNoteBookVo> list = baseMapper.page(webVo);
        PageInfo<BizNoteBookVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<BizNoteBookVo> list(BizNoteBookVo webVo) {
        webVo.setUserId(SecurityUtils.currentUserId());

        List<BizNoteBookVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public List<BizNoteBookVo> children(BizNoteBookVo webVo) {
        webVo.setUserId(SecurityUtils.currentUserId());

        List<BizNoteBookVo> list = baseMapper.children(webVo);

        return list;
    }

    @Override
    public BizNoteBookVo find(Long id) {

        BizNoteBookVo ret = baseMapper.findByPk(id);
        return ret;
    }

    public void prepare(BizNoteBookVo webVo) {
        Date now = new Date();
        Long userId = SecurityUtils.currentUserId();
        if (webVo.getId() == null) {
            webVo.setUserId(userId);
            webVo.setCreateTime(now);
        } else {
            webVo.setUserId(null);
            webVo.setUpdateTime(now);
        }
    }

    public void assertUserAccess(Long id) {
        BizNoteBookVo exInfo = find(id);
        Long currentUserId = SecurityUtils.currentUserId();
        Checker.begin(true)
                .isExTrueMsg("您无法访问该资源", (long) exInfo.getUserId() != currentUserId);
    }


    @Override
    public void add(BizNoteBookVo webVo) {
        Checker.begin(true)
                .isEmptyStrMsg(webVo.getTitle(), "名称不能为空");
        if(webVo.getParentId()==null){
            webVo.setParentId(0L);
        }
        prepare(webVo);
        baseMapper.insertSelective(webVo);
    }

    @Override
    public void update(BizNoteBookVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(), "ID必填参数");

        assertUserAccess(webVo.getId());
        prepare(webVo);
        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {
        assertUserAccess(id);
        baseMapper.deleteByPk(id);
    }
}