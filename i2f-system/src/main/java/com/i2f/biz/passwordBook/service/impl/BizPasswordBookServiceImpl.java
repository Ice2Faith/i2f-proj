package com.i2f.biz.passwordBook.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.biz.passwordBook.data.vo.BizPasswordBookVo;
import com.i2f.biz.passwordBook.mapper.BizPasswordBookMapper;
import com.i2f.biz.passwordBook.service.IBizPasswordBookService;
import com.i2f.framework.security.AuthUtils;
import com.i2f.sys.data.vo.SysUserVo;
import i2f.core.check.CheckUtil;
import i2f.core.check.Checker;
import i2f.core.codec.CodecUtil;
import i2f.core.data.Pair;
import i2f.core.digest.AESUtil;
import i2f.core.digest.CodeUtil;
import i2f.core.security.jce.digest.MessageDigestUtil;
import i2f.core.security.jce.encrypt.CipherUtil;
import i2f.core.security.jce.encrypt.symmetric.aes.AesType;
import i2f.core.std.api.ApiPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

/**
 * @author Ice2Faith
 * @date 2023-07-07 13:55:59
 * @desc biz_password_book 密码本
 */
@Slf4j
@Service
public class BizPasswordBookServiceImpl implements IBizPasswordBookService {

    @Resource
    private BizPasswordBookMapper baseMapper;

    @Override
    public ApiPage<BizPasswordBookVo> page(BizPasswordBookVo webVo,
                                           ApiPage<BizPasswordBookVo> page) {
        webVo.setUserId(AuthUtils.currentUserId());

        PageHelper.startPage(page.getIndex() + 1, page.getSize());
        List<BizPasswordBookVo> list = baseMapper.page(webVo);
        PageInfo<BizPasswordBookVo> pageInfo = new PageInfo<>(list);
        page.data(pageInfo.getTotal(), pageInfo.getList());
        for (BizPasswordBookVo item : page.getList()) {
            decodeDbData(item);
        }
        return page;
    }

    @Override
    public List<BizPasswordBookVo> list(BizPasswordBookVo webVo) {
        webVo.setUserId(AuthUtils.currentUserId());

        List<BizPasswordBookVo> list = baseMapper.list(webVo);
        for (BizPasswordBookVo item : list) {
            decodeDbData(item);
        }
        return list;
    }

    @Override
    public BizPasswordBookVo find(Long id) {

        BizPasswordBookVo ret = baseMapper.findByPk(id);
        decodeDbData(ret);
        return ret;
    }

    public void prepare(BizPasswordBookVo webVo) {
        Date now = new Date();
        Long userId = AuthUtils.currentUserId();
        if (webVo.getId() == null) {
            webVo.setUserId(userId);
            webVo.setCreateTime(now);
        } else {
            webVo.setUserId(null);
            webVo.setUpdateTime(now);
        }
    }

    public void assertUserAccess(Long id) {
        BizPasswordBookVo exInfo = find(id);
        Long currentUserId = AuthUtils.currentUserId();
        Checker.begin(true)
                .isExTrueMsg("您无法访问该资源", (long) exInfo.getUserId() != currentUserId);
    }

    public Pair<String, String> getAesKey(String salt) {
        try {
            SysUserVo user = AuthUtils.currentUser();
            String username = user.getUsername();
            if(CheckUtil.isEmptyStr(salt)){
                salt = CodeUtil.makeCheckCode(16, false);
            }
            String signText = username + ":" + salt;
            String sign = MessageDigestUtil.SHA256.mdsBase64(CodecUtil.toUtf8(signText));
            String originPass = CodecUtil.toBase64(CodecUtil.toUtf8(sign + ":" + signText));
            SecretKey secretKey = CipherUtil.genSecretKey(AesType.ECB_ISO10126PADDING, CodecUtil.toUtf8(originPass), 128);
            String aesKey = CodecUtil.toHexString(secretKey.getEncoded());
            return new Pair<>(aesKey, salt);
        } catch (Exception e) {
            Checker.begin(true)
                    .exceptMsg("内部秘钥生成失败", e);
        }
        return null;
    }

    public void encodeDbData(BizPasswordBookVo webVo) {
        if (webVo == null) {
            return;
        }
        Pair<String, String> key = getAesKey(null);
        String aesKey = key.key;

        if (!CheckUtil.isEmptyStr(webVo.getAccount())) {
            String encAccount = AESUtil.encrypt(CodecUtil.toUtf8(webVo.getAccount()), aesKey);
            webVo.setAccount(encAccount);
        }
        if (!CheckUtil.isEmptyStr(webVo.getPassword())) {
            String encPassword = AESUtil.encrypt(CodecUtil.toUtf8(webVo.getPassword()), aesKey);
            webVo.setPassword(encPassword);
        }
        if (!CheckUtil.isEmptyStr(webVo.getRemark())) {
            String encRemark = AESUtil.encrypt(CodecUtil.toUtf8(webVo.getRemark()), aesKey);
            webVo.setRemark(encRemark);
        }

        webVo.setSalt(key.val);

    }

    public void decodeDbData(BizPasswordBookVo webVo) {
        if (webVo == null) {
            return;
        }
        Pair<String, String> key = getAesKey(webVo.getSalt());
        String aesKey = key.key;

        if (!CheckUtil.isEmptyStr(webVo.getAccount())) {
            String encAccount = CodecUtil.ofUtf8(AESUtil.decrypt(webVo.getAccount(), aesKey));
            webVo.setAccount(encAccount);
        }
        if (!CheckUtil.isEmptyStr(webVo.getPassword())) {
            String encPassword = CodecUtil.ofUtf8(AESUtil.decrypt(webVo.getPassword(), aesKey));
            webVo.setPassword(encPassword);
        }
        if (!CheckUtil.isEmptyStr(webVo.getRemark())) {
            String encRemark = CodecUtil.ofUtf8(AESUtil.decrypt(webVo.getRemark(), aesKey));
            webVo.setRemark(encRemark);
        }

        webVo.setSalt(null);
    }

    @Override
    public void add(BizPasswordBookVo webVo) {
        Checker.begin(true)
                .isEmptyStrMsg(webVo.getName(), "名称不能为空");
        prepare(webVo);
        encodeDbData(webVo);
        baseMapper.insertSelective(webVo);
    }

    @Override
    public void update(BizPasswordBookVo webVo) {
        Checker.begin(true)
                .isNullMsg(webVo.getId(), "ID不能为空");
        assertUserAccess(webVo.getId());
        prepare(webVo);
        encodeDbData(webVo);
        baseMapper.updateSelectiveByPk(webVo);
    }

    @Override
    public void delete(Long id) {
        Checker.begin(true)
                .isNullMsg(id, "ID不能为空");
        assertUserAccess(id);
        baseMapper.deleteByPk(id);
    }
}