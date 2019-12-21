package com.boyu.erp.platform.usercenter.mapper.brand;

import com.boyu.erp.platform.usercenter.entity.brand.UserBrand;
import com.boyu.erp.platform.usercenter.model.UserBrandModel;
import com.boyu.erp.platform.usercenter.vo.system.UserBrandVo;

import java.util.List;

public interface UserBrandMapper {

    public List<UserBrandVo> userListbrand(UserBrandModel model);

    public UserBrand selectByPrimaryKey(UserBrand key);

    public int insert(UserBrand record);

    public int insertSelective(UserBrand record);

    public int updateUserBrand(UserBrand record);

    public int updateUserBrandList(UserBrand record);

    public int deleteByPrimaryKey(UserBrand key);
}