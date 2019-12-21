package com.boyu.erp.platform.usercenter.service.goodsservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.UserBgModel;
import com.boyu.erp.platform.usercenter.vo.system.UserBgVo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 用户品牌分组接口
 * @author: liu yan
 * @create: 2019-05-24 14:11
 */
public interface UserBgService {
    
     List<UserBgVo> getUserBg(UserBgModel model);

    void updateUserbg(SysUser user, UserBgModel model);
}
