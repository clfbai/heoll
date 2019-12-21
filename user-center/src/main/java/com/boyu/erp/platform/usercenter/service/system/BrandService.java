package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.brand.Brand;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.UserBrandModel;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;
import com.boyu.erp.platform.usercenter.vo.BrandVo;
import com.boyu.erp.platform.usercenter.vo.system.BrandWindowVo;
import com.boyu.erp.platform.usercenter.vo.system.UserBrandVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Classname BrandService
 * @Description TODO
 * @Date 2019/5/7 9:53
 * @Created by js
 */
public interface BrandService {


    int insertSelective(Brand record, SysUser user);

    /**
     * 品牌单个查询
     */
    Brand selectByPrimaryKey(Long brandId);

    int updateByPrimaryKeySelective(Brand record);

    PageInfo<BrandVo> selectAll(Integer page, Integer size, Brand brand, SysUser user);

    int deleteBrand(Brand brand);

    /**
     * 用户授予、回收品牌
     */
    void updateUserBrand(UserBrandModel brand, SysUser user);

    /**
     * 组织授予、回收品牌
     */
    void updateUnitBrand(UserBrandModel brand, SysUser sessionSysUser);

    /**
     * 查询用户拥有品牌
     */
    List<UserBrandVo> getUserBrand(UserBrandModel brand);

    /**
     * 查询组织持有品牌
     */
    List<Brand> userOperationBrand(SysUser user);

    /**
     * 品牌下拉
     */
    List<Brand> optionGet();

    /**
     * 查询用户(拥有品牌)弹窗
     */
    List<BrandWindowVo> getUserBrandWindow(Brand brand, SysUser user);


    /**
     * 功能描述: 查询用户拥有品牌(进行参数判断)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 17:22
     */
    List<Brand> userBrandList(SysUser user);

    /**
     * 功能描述: 查询用户品牌权限对应商品品种集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 19:39
     */
    List<ProdClsModel> userBrandOrProdCls(SysUser user);
}
