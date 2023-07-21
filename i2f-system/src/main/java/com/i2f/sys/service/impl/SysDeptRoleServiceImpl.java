package com.i2f.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.framework.security.AuthUtils;
import com.i2f.sys.data.vo.SysDeptRoleResourcesVo;
import com.i2f.sys.data.vo.SysDeptRoleVo;
import com.i2f.sys.mapper.SysDeptRoleMapper;
import com.i2f.sys.mapper.SysDeptRoleResourcesMapper;
import com.i2f.sys.service.ISysDeptRoleService;
import i2f.core.check.Checker;
import i2f.core.std.api.ApiPage;
import i2f.springboot.redisson.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Ice2Faith
 * @date 2023-07-17 08:38:00
 * @desc sys_dept_role 部门角色表
 */
@Slf4j
@Service
public class SysDeptRoleServiceImpl implements ISysDeptRoleService {

    public static final String UNIQUE_KEY = "unique_key";

    @Resource
    private SysDeptRoleMapper baseMapper;

    @Resource
    private SysDeptRoleResourcesMapper sysDeptRoleResourcesMapper;

    @Override
    public ApiPage<SysDeptRoleVo> page(SysDeptRoleVo webVo,
                                       ApiPage<SysDeptRoleVo> page) {

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<SysDeptRoleVo> list = baseMapper.page(webVo);
        PageInfo<SysDeptRoleVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        return page;
    }

    @Override
    public List<SysDeptRoleVo> list(SysDeptRoleVo webVo) {

        List<SysDeptRoleVo> list = baseMapper.list(webVo);

        return list;
    }

    @Override
    public SysDeptRoleVo find(Long id) {

        SysDeptRoleVo ret = baseMapper.findByPk(id);
        return ret;
    }

    public void prepare(SysDeptRoleVo webVo) {
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

    public void uniqueCheck(SysDeptRoleVo webVo) {
        Collection<Object> excludesIds = null;
        if (webVo.getId() != null) {
            excludesIds = Arrays.asList(webVo.getId());
        }
        int cnt = baseMapper.countOfKey(webVo.getDeptId(),webVo.getRoleKey(), excludesIds);
        Checker.begin(true)
                .isExTrueMsg("roleKey已存在", cnt > 0);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void add(SysDeptRoleVo webVo) {
        if (webVo.getStatus() == null) {
            webVo.setStatus(1);
        }
        prepare(webVo);
        Checker.begin(true)
                .isNullMsg(webVo.getDeptId(),"deptId必填参数")
                .isEmptyStrMsg(webVo.getRoleKey(), "roleKey必填参数")
                .isEmptyStrMsg(webVo.getRoleName(), "roleName必填参数")
                .notInMsg("不正确的状态标志位", webVo.getStatus(), 0, 1);
        uniqueCheck(webVo);
        baseMapper.insertSelective(webVo);
    }

    @RedisLock(UNIQUE_KEY)
    @Override
    public void update(SysDeptRoleVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(), "ID必填参数");
        webVo.setStatus(null);
        prepare(webVo);
        uniqueCheck(webVo);
        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID不能为空");
        SysDeptRoleVo updInfo = new SysDeptRoleVo();
        updInfo.setId(id);
        updInfo.setStatus(99);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }


    @Override
    public void enable(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID不能为空");

        SysDeptRoleVo updInfo = new SysDeptRoleVo();
        updInfo.setId(id);
        updInfo.setStatus(1);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }

    @Override
    public void disable(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID不能为空");

        SysDeptRoleVo updInfo = new SysDeptRoleVo();
        updInfo.setId(id);
        updInfo.setStatus(0);
        prepare(updInfo);
        baseMapper.updateSelectiveByPk(updInfo);
    }


    @Override
    public List<Long> findRoleResourcesIds(Long roleId) {
        Checker.begin(true)
                .isNullMsg(roleId, "roleId必填参数");

        return sysDeptRoleResourcesMapper.findRoleResourcesIds(roleId);
    }


    @Override
    public void updateRoleResources(Long roleId, Collection<Long> resIds) {
        Checker.begin(true)
                .isNullMsg(roleId, "roleId必填参数")
                .isNullMsg(resIds, "resIds必填参数");

        sysDeptRoleResourcesMapper.deleteRoleResources(roleId);

        if (resIds.isEmpty()) {
            return;
        }

        Date now = new Date();
        String currentUserId = AuthUtils.currentUserIdStr();

        Set<Long> ids = new LinkedHashSet<>(resIds);
        List<SysDeptRoleResourcesVo> list = new LinkedList<>();
        for (Long id : ids) {
            SysDeptRoleResourcesVo item = new SysDeptRoleResourcesVo();
            item.setDeptRoleId(roleId);
            item.setDeptResId(id);
            item.setCreateTime(now);
            item.setCreateUser(currentUserId);
            list.add(item);
        }

        sysDeptRoleResourcesMapper.insertBatch(list);
    }
}