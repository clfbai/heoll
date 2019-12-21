package com.boyu.erp.platform.usercenter.mapper.brand;

import com.boyu.erp.platform.usercenter.entity.brand.UnitBrand;
import com.boyu.erp.platform.usercenter.model.UserBrandModel;
import com.boyu.erp.platform.usercenter.vo.system.UserBrandVo;
import java.util.List;

public interface UnitBrandMapper {
    /**
     * 组织在 表 直接拥有品牌
     * */
    public List<UserBrandVo> unitBrandTable(UserBrandModel model);
   /**
   * 与参数有关 组织拥有品牌
   * */
    public List<UnitBrand> unitListbrand(UnitBrand key);

    public UnitBrand selectByPrimaryKey(UnitBrand key);

    public int updateByPrimaryKeySelective(UnitBrand record);

    public int updateByPrimaryKey(UnitBrand record);

    public int insert(UnitBrand record);

    public int insertSelective(UnitBrand record);

    public int deleteByPrimaryKey(UnitBrand key);

}