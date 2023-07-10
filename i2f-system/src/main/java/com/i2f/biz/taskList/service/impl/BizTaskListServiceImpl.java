package com.i2f.biz.taskList.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.biz.taskList.data.vo.BizTaskListHistoryVo;
import com.i2f.biz.taskList.data.vo.BizTaskListVo;
import com.i2f.biz.taskList.mapper.BizTaskListHistoryMapper;
import com.i2f.biz.taskList.mapper.BizTaskListMapper;
import com.i2f.biz.taskList.service.IBizTaskListService;
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
 * @date 2023-07-07 13:53:41
 * @desc biz_task_list 任务列表
 */
@Slf4j
@Service
public class BizTaskListServiceImpl implements IBizTaskListService {


    @Resource
    private BizTaskListMapper baseMapper;

    @Resource
    private BizTaskListHistoryMapper bizTaskListHistoryMapper;

    @Override
    public ApiPage<BizTaskListVo> page(BizTaskListVo webVo,
                                       ApiPage<BizTaskListVo> page) {
        webVo.setUserId(SecurityUtils.currentUserId());

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<BizTaskListVo> list = baseMapper.page(webVo);
        PageInfo<BizTaskListVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<BizTaskListVo> list(BizTaskListVo webVo) {
        webVo.setUserId(SecurityUtils.currentUserId());

        List<BizTaskListVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public BizTaskListVo find(Long id) {

        BizTaskListVo ret = baseMapper.findByPk(id);
        return ret;
    }

    public void prepare(BizTaskListVo webVo) {
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
        BizTaskListVo exInfo = find(id);
        Long currentUserId = SecurityUtils.currentUserId();
        Checker.begin(true)
                .isExTrueMsg("您无法访问该资源", (long) exInfo.getUserId() != currentUserId);
    }

    public void backupHistory(Long taskId) {
        baseMapper.backupHistory(taskId);
    }

    @Override
    public void add(BizTaskListVo webVo) {
        if (webVo.getLevel() == null) {
            webVo.setLevel(5);
        }
        if (webVo.getValue() == null) {
            webVo.setValue(5);
        }
        if (webVo.getProgress() == null) {
            webVo.setProgress(0.0);
        }
        if (webVo.getStatus() == null) {
            webVo.setStatus(0);
        }
        Checker.begin(true)
                .isEmptyStrMsg(webVo.getName(), "名称不能为空");
        prepare(webVo);
        baseMapper.insertSelective(webVo);
        backupHistory(webVo.getId());
    }

    @Override
    public void update(BizTaskListVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(), "ID必填参数");
        assertUserAccess(webVo.getId());
        prepare(webVo);
        baseMapper.updateSelectiveByPk(webVo);
        backupHistory(webVo.getId());
    }

    @Override
    public void delete(Long id) {
        assertUserAccess(id);
        baseMapper.deleteByPk(id);
        bizTaskListHistoryMapper.deleteByTaskId(id);
    }

    @Override
    public void suspend(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID必填参数");
        assertUserAccess(id);
        BizTaskListVo updInfo = new BizTaskListVo();
        updInfo.setId(id);
        updInfo.setStatus(2);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
        backupHistory(updInfo.getId());
    }

    @Override
    public void run(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID必填参数");
        assertUserAccess(id);
        BizTaskListVo updInfo = new BizTaskListVo();
        updInfo.setId(id);
        updInfo.setStatus(0);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
        backupHistory(updInfo.getId());
    }

    @Override
    public void finish(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID必填参数");
        assertUserAccess(id);
        BizTaskListVo updInfo = new BizTaskListVo();
        updInfo.setId(id);
        updInfo.setStatus(1);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
        backupHistory(updInfo.getId());
    }

    @Override
    public List<BizTaskListHistoryVo> history(Long id) {
        assertUserAccess(id);
        List<BizTaskListHistoryVo> list = bizTaskListHistoryMapper.historyList(id);
        return list;
    }
}