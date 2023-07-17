package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.framework.security.SecurityUtils;
import com.i2f.sys.data.vo.SysConfigItemVo;
import com.i2f.sys.mapper.SysConfigItemMapper;
import com.i2f.sys.service.ISysConfigItemService;
import i2f.core.check.CheckUtil;
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
 * @date 2023-07-06 16:46:18
 * @desc sys_config
 */
@Slf4j
@Service
public class SysConfigItemServiceImpl implements ISysConfigItemService {
    public static final String UNIQUE_KEY = "unique_key";

    @Resource
    private SysConfigItemMapper baseMapper;

    @Override
    public ApiPage<SysConfigItemVo> page(SysConfigItemVo webVo,
                                         ApiPage<SysConfigItemVo> page) {

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<SysConfigItemVo> list = baseMapper.page(webVo);
        PageInfo<SysConfigItemVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<SysConfigItemVo> list(SysConfigItemVo webVo) {

        List<SysConfigItemVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public SysConfigItemVo find(Long id) {

        SysConfigItemVo ret = baseMapper.findByPk(id);
        return ret;
    }


    public void prepare(SysConfigItemVo webVo) {
        Date now = new Date();
        String currentUserId = SecurityUtils.currentUserIdStr();
        if (webVo.getId() == null) {
            webVo.setCreateTime(now);
            webVo.setCreateUser(currentUserId);
        } else {
            webVo.setUpdateTime(now);
            webVo.setUpdateUser(currentUserId);
        }
    }

    public void uniqueCheck(SysConfigItemVo webVo) {
        Collection<Object> excludesIds = null;
        if (webVo.getId() != null) {
            excludesIds = Arrays.asList(webVo.getId());
        }
        if (webVo.getEntryId() != null) {
            int cnt = baseMapper.countOfEntryId(webVo.getEntryId(), webVo.getConfigId(), excludesIds);
            Checker.begin(true)
                    .isExTrueMsg("entryId已存在", cnt > 0);
        }
        if (!CheckUtil.isEmptyStr(webVo.getEntryKey())) {
            int cnt = baseMapper.countOfEntryKey(webVo.getEntryKey(), webVo.getConfigId(), excludesIds);
            Checker.begin(true)
                    .isExTrueMsg("entryKey已存在", cnt > 0);
        }
    }


    @RedisLock(UNIQUE_KEY)
    @Override
    public void add(SysConfigItemVo webVo) {
        if (webVo.getStatus() == null) {
            webVo.setStatus(1);
        }
        if (webVo.getEntryOrder() == null) {
            webVo.setEntryOrder(0);
        }
        if (webVo.getModFlag() == null) {
            webVo.setModFlag(1);
        }
        if (webVo.getDelFlag() == null) {
            webVo.setDelFlag(1);
        }
        if (webVo.getSysFlag() == null) {
            webVo.setSysFlag(0);
        }
        if (webVo.getParentEntryId() == null) {
            webVo.setParentEntryId(0L);
        }
        prepare(webVo);

        Checker.begin(true)
                .isNullMsg(webVo.getConfigId(), "configId必填参数")
                .isNullMsg(webVo.getEntryId(), "entryId必填参数")
                .isEmptyStrMsg(webVo.getEntryName(), "entryName必填参数")
                .notInMsg("不正确的状态标志位", webVo.getStatus(), 0, 1)
                .notInMsg("不正确的修改标志位", webVo.getModFlag(), 0, 1)
                .notInMsg("不正确的删除标志位", webVo.getDelFlag(), 0, 1)
                .notInMsg("不正确的系统标志位", webVo.getSysFlag(), 0, 1);
        uniqueCheck(webVo);

        baseMapper.insertSelective(webVo);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void update(SysConfigItemVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(), "ID必填参数");
        SysConfigItemVo exInfo = find(webVo.getId());
        Checker.begin(true)
                .isNullMsg(exInfo, "找不到配置项")
                .isExTrueMsg("配置项不允许修改", exInfo.getModFlag() == 0)
                .isExTrueMsg("配置项已删除", exInfo.getStatus() == 99);
        exInfo.setConfigId(null);
        prepare(webVo);
        uniqueCheck(webVo);

        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID必填参数");
        SysConfigItemVo exInfo = find(id);
        Checker.begin(true)
                .isNullMsg(exInfo, "找不到配置项")
                .isExTrueMsg("配置项不允许删除", exInfo.getDelFlag() == 0)
                .isExTrueMsg("系统配置项不允许删除", exInfo.getSysFlag() == 1)
                .isExTrueMsg("配置项已删除", exInfo.getStatus() == 99);

        SysConfigItemVo updInfo = new SysConfigItemVo();
        updInfo.setId(id);
        prepare(updInfo);
        baseMapper.deleteLogicalByPk(updInfo);
    }

    @Override
    public List<SysConfigItemVo> items(String configKey) {
        return baseMapper.findConfigItemsByConfigKey(configKey);
    }

    @Override
    public List<SysConfigItemVo> items(Long configId) {
        return baseMapper.findConfigItemsByConfigId(configId);
    }

    @Override
    public List<SysConfigItemVo> tree(Long configId) {
        List<SysConfigItemVo> list = baseMapper.findConfigItemsByConfigId(configId);
        List<SysConfigItemVo> tree = TreeConvertUtil.list2Tree(list);
        return tree;
    }

    @Override
    public List<SysConfigItemVo> tree(String configKey) {
        List<SysConfigItemVo> list = baseMapper.findConfigItemsByConfigKey(configKey);
        List<SysConfigItemVo> tree = TreeConvertUtil.list2Tree(list);
        return tree;
    }


    @Override
    public void updateItems(Long configId, List<SysConfigItemVo> items) {
        deleteItems(configId);
        for (SysConfigItemVo item : items) {
            item.setConfigId(configId);
            if (item.getId() != null) {
                update(item);
            } else {
                add(item);
            }
        }
    }

    @Override
    public void deleteItems(Long configId) {
        SysConfigItemVo updInfo = new SysConfigItemVo();
        updInfo.setConfigId(configId);
        prepare(updInfo);
        baseMapper.deleteItemsLogical(updInfo);
    }

    @Override
    public List<SysConfigItemVo> children(Long configId, SysConfigItemVo webVo) {
        webVo.setConfigId(configId);

        List<SysConfigItemVo> list = baseMapper.children(webVo);

        return list;
    }
}