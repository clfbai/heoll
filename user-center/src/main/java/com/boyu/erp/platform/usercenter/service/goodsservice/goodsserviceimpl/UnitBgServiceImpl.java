package com.boyu.erp.platform.usercenter.service.goodsservice.goodsserviceimpl;

import com.boyu.erp.platform.usercenter.entity.system.Bg;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.brand.UnitBg;
import com.boyu.erp.platform.usercenter.entity.brand.UserBg;
import com.boyu.erp.platform.usercenter.mapper.brand.BgMapper;
import com.boyu.erp.platform.usercenter.mapper.brand.UnitBgMapper;
import com.boyu.erp.platform.usercenter.mapper.brand.UserBgMapper;
import com.boyu.erp.platform.usercenter.model.UnitBgModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.UnitBgService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 组织品牌分组接口服务
 * @author: clf
 * @create: 2019-05-23 15:39
 */
@Slf4j
@Service
@Transactional
public class UnitBgServiceImpl implements UnitBgService {
    @Autowired
    private BgMapper bgMapper;

    @Autowired
    private UnitBgMapper unitBgMapper;

    @Autowired
    private UserBgMapper userBgMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Bg> unitBgAll(UnitBgModel mode) {
        return bgMapper.getUnitBg(mode);
    }

    @Override
    public void unitBgUpdate(UnitBgModel bgMode, SysUser user) {

        if (StringUtils.isNotEmpty(bgMode.getAdd())) {
            /**
             * 新增  ：
             * 1. 添加组织品牌分组
             * 2. 添加 组织管理员(用户品牌分组)
             * */
            for (UnitBg bg : bgMode.getAddBg()) {
                UnitBg unitbg = getUnitBrand(bgMode, bg, user, bgMode.getAdd());
                UserBg userBg = getUserBg(bgMode, bg, user, bgMode.getAdd());
                unitBgMapper.deleteUnitBg(unitbg);
                unitBgMapper.insertSelective(unitbg);
                userBgMapper.deleteUserBg(userBg);
                userBgMapper.insertUserBg(userBg);
            }
        }
        if (StringUtils.isNotEmpty(bgMode.getDelete())) {
            /**
             * 删除  ：
             * 1. 删除组织品牌分组
             * 2. 删除组织下用户拥有的 品牌分组
             * */
            for (UnitBg bg : bgMode.getDeleteBg()) {
                UnitBg unitbg = getUnitBrand(bgMode, bg, user, bgMode.getDelete());
                UserBg userBg = getUserBg(bgMode, bg, user, bgMode.getDelete());
                //步骤一
                unitBgMapper.updateUnitBg(unitbg);
                //步骤二
                userBgMapper.updateUserBgList(userBg);
            }
        }

    }

    /**
     * 新增用户品牌分组对象
     */
    private UserBg getUserBg(UnitBgModel bgMode, UnitBg bg, SysUser user, String s) {
        UserBg bgs = new UserBg();
        if (s.equalsIgnoreCase("ADD")) {
            bgs.setUserId(bgMode.getSaId());
            bgs.setOwnerId(bgMode.getUnitId());
            bgs.setBgId(bg.getBgId());
            bgs.setCreateBy(user.getUserId());
            bgs.setUpdateBy(user.getUserId());
            bgs.setUpdateTime(new Date());
            bgs.setCreateTime(new Date());
            bgs.setIsDel(CommonFainl.BTYPESTATUS);
            return bgs;
        }
        if (s.equalsIgnoreCase("DELETE")) {
            bgs.setUserId(bgMode.getSaId());
            bgs.setOwnerId(bgMode.getUnitId());
            bgs.setBgId(bg.getBgId());
            bgs.setUpdateBy(user.getUserId());
            bgs.setUpdateTime(new Date());
            bgs.setIsDel(CommonFainl.FAILSTATUS);
            return bgs;
        }
        return null;
    }

    /**
     * 新增组织品牌分组对象
     */
    private UnitBg getUnitBrand(UnitBgModel bgMode, UnitBg bg, SysUser user, String s) {
        UnitBg unitBg = new UnitBg();

        if (s.equalsIgnoreCase("ADD")) {
            //新增对象
            unitBg.setBgId(bg.getBgId());
            unitBg.setUnitId(bgMode.getUnitId());

            unitBg.setCreateBy(user.getOwnerId());
            unitBg.setUpdateBy(user.getOwnerId());
            unitBg.setCreateTime(new Date());
            unitBg.setUpdateTime(new Date());
            unitBg.setIsDel(CommonFainl.BTYPESTATUS);
            log.info("品牌分组Id:  "+bg.getBgId());
            return unitBg;
        }
        if (s.equalsIgnoreCase("DELETE")) {
            //删除对象
            unitBg.setBgId(bg.getBgId());
            unitBg.setUnitId(bgMode.getUnitId());
            unitBg.setUpdateBy(user.getOwnerId());
            unitBg.setUpdateTime(new Date());
            unitBg.setIsDel(CommonFainl.FAILSTATUS);
            log.info("删除品牌分组Id:  "+bg.getBgId());
            return unitBg;
        }
        return null;
    }
}
