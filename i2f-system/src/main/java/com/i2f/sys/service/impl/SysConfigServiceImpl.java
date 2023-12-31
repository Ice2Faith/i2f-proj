package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.framework.security.AuthUtils;
import com.i2f.sys.data.vo.SysConfigItemVo;
import com.i2f.sys.data.vo.SysConfigVo;
import com.i2f.sys.mapper.SysConfigItemMapper;
import com.i2f.sys.mapper.SysConfigMapper;
import com.i2f.sys.service.ISysConfigItemService;
import com.i2f.sys.service.ISysConfigService;
import i2f.core.check.CheckUtil;
import i2f.core.check.Checker;
import i2f.core.std.api.ApiPage;
import i2f.springboot.redisson.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysConfigServiceImpl implements ISysConfigService {
    public static final String UNIQUE_KEY = "unique_key";

    @Resource
    private SysConfigMapper baseMapper;

    @Resource
    private SysConfigItemMapper sysConfigItemMapper;

    @Autowired
    private ISysConfigItemService sysConfigItemService;

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


    public void prepare(SysConfigVo webVo) {
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

    public void uniqueCheck(SysConfigVo webVo) {
        Collection<Object> excludesIds = null;
        if (webVo.getId() != null) {
            excludesIds = Arrays.asList(webVo.getId());
        }
        int cnt = baseMapper.countOfKey(webVo.getConfigKey(), excludesIds);
        Checker.begin(true)
                .isExTrueMsg("configKey已存在", cnt > 0);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void add(SysConfigVo webVo) {
        if (webVo.getModFlag() == null) {
            webVo.setModFlag(1);
        }
        if (webVo.getDelFlag() == null) {
            webVo.setDelFlag(1);
        }
        if (webVo.getSysFlag() == null) {
            webVo.setSysFlag(0);
        }
        if (webVo.getSysFlag() == 1) {
            webVo.setDelFlag(0);
        }
        Checker.begin(true)
                .isEmptyStrMsg(webVo.getConfigKey(), "configKey必填参数")
                .isEmptyStrMsg(webVo.getName(), "name必填参数")
                .notInMsg("不正确的修改标志位", webVo.getModFlag(), 0, 1)
                .notInMsg("不正确的删除标志位", webVo.getDelFlag(), 0, 1)
                .notInMsg("不正确的系统标志位", webVo.getSysFlag(), 0, 1);
        prepare(webVo);
        uniqueCheck(webVo);
        baseMapper.insertSelective(webVo);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void update(SysConfigVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(), "ID必填参数");
        prepare(webVo);
        uniqueCheck(webVo);
        SysConfigVo exInfo = find(webVo.getId());

        Checker.begin(true)
                .isExTrueMsg("配置不允许更新", exInfo.getModFlag() == 0);

        if (exInfo.getSysFlag() == 1) {
            webVo.setConfigKey(null);
            webVo.setSysFlag(null);
            webVo.setDelFlag(null);
        }

        baseMapper.updateSelectiveByPk(webVo);
    }

    public void prepareItem(SysConfigItemVo webVo) {
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

    public void uniqueCheckItem(SysConfigItemVo webVo) {
        Collection<Object> excludesIds = null;
        if (webVo.getId() != null) {
            excludesIds = Arrays.asList(webVo.getId());
        }
        if (webVo.getEntryId() != null) {
            int cnt = sysConfigItemMapper.countOfEntryId(webVo.getEntryId(), webVo.getConfigId(), excludesIds);
            Checker.begin(true)
                    .isExTrueMsg("entryId已存在", cnt > 0);
        }
        if (!CheckUtil.isEmptyStr(webVo.getEntryKey())) {
            int cnt = sysConfigItemMapper.countOfEntryKey(webVo.getEntryKey(), webVo.getConfigId(), excludesIds);
            Checker.begin(true)
                    .isExTrueMsg("entryKey已存在", cnt > 0);
        }
    }

    @Override
    public void delete(Long id) {
        SysConfigVo exInfo = find(id);
        Checker.begin(true)
                .isExTrueMsg("配置不允许删除", exInfo.getDelFlag() == 0)
                .isExTrueMsg("系统配置不允许删除", exInfo.getSysFlag() == 1);

        int cnt = baseMapper.countOfNotDeleteableItems(id);
        Checker.begin(true)
                .isExTrueMsg("配置存在不允许删除的项", cnt > 0);

        baseMapper.deleteByPk(id);

        sysConfigItemService.deleteItems(id);
    }

}