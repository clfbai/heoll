package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.brand.BgDtl;
import com.boyu.erp.platform.usercenter.entity.brand.UnitBg;
import com.boyu.erp.platform.usercenter.entity.brand.UserBg;
import com.boyu.erp.platform.usercenter.entity.system.Bg;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.brand.BgDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.brand.BgMapper;
import com.boyu.erp.platform.usercenter.mapper.brand.UnitBgMapper;
import com.boyu.erp.platform.usercenter.mapper.brand.UserBgMapper;
import com.boyu.erp.platform.usercenter.service.system.BgService;
import com.boyu.erp.platform.usercenter.vo.BgVo;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Classname BgServiceimpl
 * @Description TODO
 * @Date 2019/5/7 10:58
 * @Created by js
 * 品牌分组
 */
@Service
@Transactional
public class BgServiceimpl implements BgService {

    @Autowired
    private BgMapper bgMapper;
    @Autowired
    private BgDtlMapper bgDtlMapper;
    @Autowired
    private UserBgMapper userBgMapper;
    @Autowired
    private UnitBgMapper unitBgMapper;

    @Override
    public int insert(Bg record) {

        return bgMapper.insert(record);
    }

    @Override
    @Transactional(readOnly = true)
    public Bg selectByPrimaryKey(String bgId) {
        return bgMapper.selectByPrimaryKey(bgId);
    }

    @Override
    public int updateByPrimaryKeySelective(Bg record) {
        return bgMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<BgVo> selectAll(Integer page, Integer size, Bg bg) {
        PageHelper.startPage(page, size);
        List<BgVo> bgVos = bgMapper.selectAll(bg);
        PageInfo<BgVo> bgVoPageInfo = new PageInfo<>(bgVos);
        return bgVoPageInfo;
    }

    /**
     * 删除品牌分组
     * 1.   删除品牌分组表数据
     * 2.  删除品牌分组明细表数据
     * 3.  删除用户品牌分组(打标)
     * 4. 删除组织品牌分组(打标)
     *
     * @param bg
     * @param
     * @return
     */
    @Override
    public int deleteBg(Bg bg, SysUser user) {
        BgDtl dtl = new BgDtl();
        dtl.setBgId(bg.getBgId());

        /**
         * 2.  删除品牌分组明细表数据
         */
        int s = bgDtlMapper.deleteBgList(dtl);
        /**
         * 3.  删除用户品牌分组(打标)
         *
         */
        UserBg userBg = new UserBg();
        userBg.setBgId(bg.getBgId());
        userBg.setUpdateTime(new Date());
        userBg.setUpdateBy(user.getUserId());
        userBg.setIsDel(CommonFainl.FAILSTATUS);
        int sk = userBgMapper.updateUserBgList(userBg);
        /**
         * 4. 删除组织品牌分组(打标)
         *
         */
        UnitBg unitBg = new UnitBg();
        unitBg.setIsDel(CommonFainl.FAILSTATUS);
        unitBg.setBgId(bg.getBgId());
        unitBg.setUpdateTime(new Date());
        unitBg.setUpdateBy(user.getUserId());
        int skt = unitBgMapper.updateUnitBgList(unitBg);
        /**
         * 1.   删除品牌分组表数据
         */
        int skt1 = bgMapper.deleteBg(bg);


        return s + skt + sk + skt1;
    }
}
