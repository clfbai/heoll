package com.boyu.erp.platform.usercenter.mapper.brand;

import com.boyu.erp.platform.usercenter.entity.brand.UserBg;
import com.boyu.erp.platform.usercenter.vo.system.UserBgVo;

import java.util.List;

public interface UserBgMapper {

    public List<UserBgVo> getUserBg(UserBg bgs);

    public UserBg selectByPrimaryKey(UserBg key);

    public int insert(UserBg record);

    public int insertUserBg(UserBg record);

    public int updateUserBg(UserBg record);

    public int updateUserBgList(UserBg bg);
    /**
    * 删除品牌分组时修改用户品牌分组
    * */
    public int updateBgList(UserBg bg);

    public int deleteUserBg(UserBg key);
}