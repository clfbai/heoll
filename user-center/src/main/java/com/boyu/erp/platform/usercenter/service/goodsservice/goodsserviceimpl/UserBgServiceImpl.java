package com.boyu.erp.platform.usercenter.service.goodsservice.goodsserviceimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.brand.UnitBg;
import com.boyu.erp.platform.usercenter.entity.brand.UserBg;
import com.boyu.erp.platform.usercenter.mapper.brand.UnitBgMapper;
import com.boyu.erp.platform.usercenter.mapper.brand.UserBgMapper;
import com.boyu.erp.platform.usercenter.model.UserBgModel;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.goodsservice.UserBgService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.UserBgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 用户品牌分组服务
 * @author: clf
 * @create: 2019-05-24 14:11
 */
@Slf4j
@Service
@Transactional
public class UserBgServiceImpl implements UserBgService {
    @Autowired
    private UserBgMapper userBgMapper;
    @Autowired
    private UnitBgMapper unitBgMapper;
    @Autowired
    private UsersService usersService;

    @Override
    @Transactional(readOnly = true)
    public List<UserBgVo> getUserBg(UserBgModel model) {
        UserBg bg = new UserBg();
        bg.setOwnerId(model.getOwnerId());
        bg.setUserId(model.getUserId());
        return userBgMapper.getUserBg(bg);
    }

    @Override
    public void updateUserbg(SysUser user, UserBgModel model) {
        SysUser sysUser = usersService.getAdmin(user);

        //管理员
        if (model.getAdd().equalsIgnoreCase("ADD")) {
            for (UserBg b : model.getAddBg()) {
                UserBg bg = getUserBgPojo(b, model, user, "ADD");
                if (sysUser != null) {
                    //增加
                    /**
                     * 管理员增加品牌分组
                     * 1. 给自己增加
                     * 2. 组织增加
                     * */
                    userBgMapper.deleteUserBg(bg);
                    userBgMapper.insertUserBg(bg);

                    UnitBg unitBg = new UnitBg();
                    unitBg.setUnitId(model.getOwnerId());
                    unitBg.setBgId(b.getBgId());
                    unitBg.setUpdateTime(new Date());
                    unitBg.setCreateTime(new Date());
                    unitBg.setCreateBy(user.getUserId());
                    unitBg.setUnitId(user.getUserId());
                    unitBg.setIsDel(CommonFainl.BTYPESTATUS);
                    unitBgMapper.deleteUnitBg(unitBg);
                    unitBgMapper.insertSelective(unitBg);
                } else {
                    //普通用户给自己增加
                    userBgMapper.deleteUserBg(bg);
                    userBgMapper.insertUserBg(bg);
                }
            }


        }
        if (model.getDelete().equalsIgnoreCase("DELETE")) {
            for (UserBg b : model.getDeleteBg()) {
                //删除
                /**
                 * 管理员删除 品牌分组
                 * 1. 组织删除
                 * 2. 组织下所有拥有该品牌分组的用户 删除此品牌分组
                 * */
                UserBg bg = getUserBgPojo(b, model, user, "DELETE");
                if (sysUser != null) {

                    //步骤一
                    UnitBg unitBg = new UnitBg();
                    unitBg.setUnitId(model.getOwnerId());
                    unitBg.setBgId(b.getBgId());
                    unitBg.setUpdateTime(new Date());
                    unitBg.setUnitId(user.getUserId());
                    unitBg.setIsDel(CommonFainl.FAILSTATUS);
                    unitBgMapper.updateUnitBg(unitBg);
                    //步骤二
                    userBgMapper.updateUserBgList(bg);
                } else {
                    //普通用户给自己删除
                    userBgMapper.updateUserBg(bg);
                }
            }
        }

    }

    private UserBg getUserBgPojo(UserBg b, UserBgModel model, SysUser user, String s) {
        UserBg bg = new UserBg();
        if (s.equalsIgnoreCase("ADD")) {
            bg.setUserId(model.getUserId());
            bg.setOwnerId(model.getUnitId());
            bg.setBgId(b.getBgId());
            bg.setCreateBy(user.getUserId());
            bg.setUpdateBy(user.getUserId());
            bg.setCreateTime(new Date());
            bg.setUpdateTime(new Date());
            bg.setIsDel(CommonFainl.BTYPESTATUS);
            return bg;
        }
        if (s.equalsIgnoreCase("DELETE")) {
            bg.setUserId(model.getUserId());
            bg.setOwnerId(model.getUnitId());
            bg.setBgId(b.getBgId());
            bg.setUpdateBy(user.getUserId());
            bg.setUpdateTime(new Date());
            bg.setIsDel(CommonFainl.FAILSTATUS);
            return bg;
        }
        return null;
    }
}
