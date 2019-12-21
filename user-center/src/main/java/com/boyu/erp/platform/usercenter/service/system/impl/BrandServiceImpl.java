package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.brand.Brand;
import com.boyu.erp.platform.usercenter.entity.brand.UnitBrand;
import com.boyu.erp.platform.usercenter.entity.brand.UserBrand;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.brand.BrandMapper;
import com.boyu.erp.platform.usercenter.mapper.brand.UnitBrandMapper;
import com.boyu.erp.platform.usercenter.mapper.brand.UserBrandMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.model.UserBrandModel;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;
import com.boyu.erp.platform.usercenter.service.system.BrandService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.vo.BrandVo;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.BrandWindowVo;
import com.boyu.erp.platform.usercenter.vo.system.UserBrandVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname BrandServiceImpl
 * @Description TODO
 * @Date 2019/5/7 9:54
 * @Created by js
 */
@Slf4j
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private UserBrandMapper userBrandMapper;

    @Autowired
    private UnitBrandMapper unitBrandMapper;

    @Autowired
    private SysParameterMapper parameterMapper;
    /**
     * 关于品牌操作权限 取 goods_priv_crud 目前暂定只有 系统管理员 、 授权给TJHJSA
     * */

    /**
     * 添加
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(Brand record, SysUser user) {

        record.setBrandId(null);
        int a = brandMapper.insertSelective(record);
        //组织管理员或普通用户增加品牌自动分配给用户组织
        if (usersService.getIsLeader(user)) {
            UnitBrand unitBrand = new UnitBrand();
            unitBrand.setUnitId(user.getOwnerId());
            unitBrand.setBrandId(record.getBrandId());
            unitBrand.setIsDel(CommonFainl.BTYPESTATUS);
            unitBrand.setCreateBy(user.getUserId());
            unitBrand.setCreateTime(new Date());
            unitBrandMapper.insertSelective(unitBrand);
            if (usersService.getIsUser(user)) {
                UserBrand userBrand = new UserBrand();
                userBrand.setBrandId(record.getBrandId());
                userBrand.setUserId(user.getUserId());
                userBrand.setOwnerId(user.getOwnerId());
                userBrand.setIsDel(CommonFainl.BTYPESTATUS);
                userBrand.setCreateBy(user.getUserId());
                userBrand.setCreateTime(new Date());
            }
        }

        return a;
    }

    /**
     * 主键查询
     *
     * @param brandId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Brand selectByPrimaryKey(Long brandId) {
        return brandMapper.selectByPrimaryKey(brandId);
    }

    /**
     * 修改
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(Brand record) {


        return brandMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 查询所有
     *
     * @param page
     * @param size
     * @param brand
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<BrandVo> selectAll(Integer page, Integer size, Brand brand, SysUser user) {
        if (usersService.getAdmin(user) != null) {
            if (usersService.getAdmin(user).getOprId() == CommonFainl.ADMIN) {
                return getPageInfo(page, size, brand);
            }
            /**
             * 组织管理员：
             * 1.取参数表(组织管理员 USER_BRAND_CONTROL_ENABLED = FALSE ) 用户品牌权限不生效
             * 2.判断是否设置组织品牌权限
             * */
            SysParameter parameter = parameterMapper.findById(ParameterString.UNIT_BRAND_PRIV);
            if (parameter != null && CommonFainl.TRUE.equalsIgnoreCase(parameter.getParmVal())) {
                //设置开启组织权限
                brand.setUnitId(user.getOwnerId());
                log.info("领域" + user.getOwnerId());
                return getPageInfo(page, size, brand, CommonFainl.TRUE);
            }
            //不设置组织品牌权限
            return getPageInfo(page, size, brand);
        }
        /**
         * 普通用户
         * 1. 设置组织品牌权限    设置用户品牌权限     则内关联返回USER_BRAND和UNIT_BRAND的交集。
         * 2. 不设置组织品牌权限  不设置用户品牌权限  则返回全局
         * 3. 设置组织品牌权限    不设置用户品牌权限  则返回UNIT_BRAND
         * 4. 不设置组织品牌权限  设置用户品牌权限    则返回USER_BRAND
         * */
        String unitp = parameterMapper.findById(ParameterString.UNIT_BRAND_PRIV).getParmVal() == null || "".equalsIgnoreCase(parameterMapper.findById(ParameterString.UNIT_BRAND_PRIV).getParmVal()) ? CommonFainl.FALSE : parameterMapper.findById(ParameterString.UNIT_BRAND_PRIV).getParmVal();
        String userp = parameterMapper.findById(ParameterString.USER_BRAND_PRIV).getParmVal() == null || "".equalsIgnoreCase(parameterMapper.findById(ParameterString.USER_BRAND_PRIV).getParmVal()) ? CommonFainl.FALSE : parameterMapper.findById(ParameterString.USER_BRAND_PRIV).getParmVal();
        // 2. 不设置组织品牌权限  不设置用户品牌权限
        if (CommonFainl.FALSE.equalsIgnoreCase(unitp) && CommonFainl.FALSE.equalsIgnoreCase(userp)) {
            log.info("2. 不设置组织品牌权限  不设置用户品牌权限 则返回全局 ");
            return getPageInfo(page, size, brand);
        }
        //1. 设置组织品牌权限    设置用户品牌权限  则内关联返回USER_BRAND和UNIT_BRAND的交集。
        if (CommonFainl.TRUE.equalsIgnoreCase(unitp) && CommonFainl.TRUE.equalsIgnoreCase(userp)) {
            brand.setUnitId(user.getOwnerId());
            brand.setUserId(user.getUserId());
            brand.setUnitParam(CommonFainl.TRUE);
            brand.setUserParam(CommonFainl.TRUE);
            log.info("1. 设置组织品牌权限    设置用户品牌权限  则内关联返回USER_BRAND和UNIT_BRAND的交集。 ");
            return getUserPageInfo(page, size, brand);
        }
        //3. 设置组织品牌权限    不设置用户品牌权限
        if (unitp.equalsIgnoreCase(CommonFainl.TRUE) && userp.equalsIgnoreCase(CommonFainl.FALSE)) {
            brand.setUnitId(user.getOwnerId());
            brand.setUserId(user.getUserId());
            brand.setUnitParam(CommonFainl.TRUE);
            log.info("3. 设置组织品牌权限    不设置用户品牌权限 则返回UNIT_BRAND");
            return getUserPageInfo(page, size, brand);
        }
        //4. 不设置组织品牌权限  设置用户品牌权限
        if (userp.equalsIgnoreCase(CommonFainl.TRUE) && unitp.equalsIgnoreCase(CommonFainl.FALSE)) {
            brand.setUnitId(user.getOwnerId());
            brand.setUserId(user.getUserId());
            brand.setUserParam(CommonFainl.TRUE);
            log.info("4. 不设置组织品牌权限  设置用户品牌权限  则返回USER_BRAND");
            return getUserPageInfo(page, size, brand);
        }
        return null;
    }


    /**
     * 功能描述: 查询用户拥有品牌权限(进行参数判断)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 17:22
     */
    @Override
    public List<Brand> userBrandList(SysUser user) {
        List<Brand> brands = new ArrayList<>();
        if (usersService.getAdmin(user) != null) {
            if (usersService.getAdmin(user).getOprId() == CommonFainl.ADMIN) {
                return setBrandVo(brandMapper.selectAll(null));
            }
            /**
             * 组织管理员：
             * 1.取参数表(组织管理员 USER_BRAND_CONTROL_ENABLED = FALSE ) 用户品牌权限不生效
             * 2.判断是否设置组织品牌权限
             * */
            SysParameter parameter = parameterMapper.findById(ParameterString.UNIT_BRAND_PRIV);
            if (parameter != null && CommonFainl.TRUE.equalsIgnoreCase(parameter.getParmVal())) {
                Brand brand = new Brand();
                //设置开启组织权限
                brand.setUnitId(user.getOwnerId());
                System.out.println("开启组织权限");
                return setBrandVo(brandMapper.selectUnitAll(brand));
            }
            //不设置组织品牌权限
            System.out.println("未开启组织权限");
            return setBrandVo(brandMapper.selectAll(null));
        }
        /**
         * 普通用户
         * 1. 设置组织品牌权限    设置用户品牌权限     则内关联返回USER_BRAND和UNIT_BRAND的交集。
         * 2. 不设置组织品牌权限  不设置用户品牌权限  则返回全局
         * 3. 设置组织品牌权限    不设置用户品牌权限  则返回UNIT_BRAND
         * 4. 不设置组织品牌权限  设置用户品牌权限    则返回USER_BRAND
         * */
        String unitp = parameterMapper.findById(ParameterString.UNIT_BRAND_PRIV).getParmVal() == null || "".equalsIgnoreCase(parameterMapper.findById(ParameterString.UNIT_BRAND_PRIV).getParmVal()) ? CommonFainl.FALSE : parameterMapper.findById(ParameterString.UNIT_BRAND_PRIV).getParmVal();
        String userp = parameterMapper.findById(ParameterString.USER_BRAND_PRIV).getParmVal() == null || "".equalsIgnoreCase(parameterMapper.findById(ParameterString.USER_BRAND_PRIV).getParmVal()) ? CommonFainl.FALSE : parameterMapper.findById(ParameterString.USER_BRAND_PRIV).getParmVal();
        // 2. 不设置组织品牌权限  不设置用户品牌权限
        if (CommonFainl.FALSE.equalsIgnoreCase(unitp) && CommonFainl.FALSE.equalsIgnoreCase(userp)) {
            log.info("2.不设置组织品牌权限不设置用户品牌权限 回所有品牌");
            return setBrandVo(brandMapper.selectAll(null));
        }
        //1. 设置组织品牌权限    设置用户品牌权限  则内关联返回USER_BRAND和UNIT_BRAND的交集。
        if (CommonFainl.TRUE.equalsIgnoreCase(unitp) && CommonFainl.TRUE.equalsIgnoreCase(userp)) {
            Brand brand = new Brand();
            brand.setUnitId(user.getOwnerId());
            brand.setUserId(user.getUserId());
            brand.setUnitParam(CommonFainl.TRUE);
            brand.setUserParam(CommonFainl.TRUE);
            log.info("1.设置组织品牌权限，设置用户品牌权限则内关联返回USER_BRAND和UNIT_BRAND的交集。 ");
            return setBrandVo(brandMapper.selectUserAll(brand));
        }
        //3. 设置组织品牌权限    不设置用户品牌权限
        if (unitp.equalsIgnoreCase(CommonFainl.TRUE) && userp.equalsIgnoreCase(CommonFainl.FALSE)) {
            Brand brand = new Brand();
            brand.setUnitId(user.getOwnerId());
            brand.setUserId(user.getUserId());
            brand.setUnitParam(CommonFainl.TRUE);
            log.info("3. 设置组织品牌权限    不设置用户品牌权限 则返回UNIT_BRAND");
            return setBrandVo(brandMapper.selectUserAll(brand));
        }
        //4. 不设置组织品牌权限  设置用户品牌权限
        if (userp.equalsIgnoreCase(CommonFainl.TRUE) && unitp.equalsIgnoreCase(CommonFainl.FALSE)) {
            Brand brand = new Brand();
            brand.setUnitId(user.getOwnerId());
            brand.setUserId(user.getUserId());
            brand.setUserParam(CommonFainl.TRUE);
            log.info("4. 不设置组织品牌权限  设置用户品牌权限  则返回USER_BRAND");
            return setBrandVo(brandMapper.selectUserAll(brand));
        }
        return null;
    }

    /**
     * 功能描述: 查询用户品牌权限对应商品品种集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 19:39
     */
    @Override
    public List<ProdClsModel> userBrandOrProdCls(SysUser user) {
        List<Brand> list = this.userBrandList(user);
        List<ProdClsModel> ps = brandMapper.selectProdCls(list);
        return ps;
    }


    /**
     * 删除打标
     *
     * @param brand
     * @return
     */
    @Override
    public int deleteBrand(Brand brand) {
        brand.setBrandStatus("D");
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserBrandVo> getUserBrand(UserBrandModel brand) {
        return userBrandMapper.userListbrand(brand);
    }

    /**
     * 查询组织持有品牌
     */
    @Override
    @Transactional(readOnly = true)
    public List<Brand> userOperationBrand(SysUser user) {
        if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1L) {
            user.setOwnerId(null);
        }
        return brandMapper.userOperation(user.getOwnerId());
    }

    @Override
    public List<Brand> optionGet() {
        return brandMapper.optionList();
    }

    @Override
    public List<BrandWindowVo> getUserBrandWindow(Brand brand, SysUser user) {
        List<BrandWindowVo> list = brandMapper.getBrandWindow(brand);
        List<Long> brandList = new ArrayList<>();
        List<BrandWindowVo> restList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(this.userOperationBrand(user))) {
            this.userOperationBrand(user).parallelStream().forEach(s -> brandList.add(s.getBrandId()));
        } else {
            UserBrandModel model = new UserBrandModel();
            model.setUserId(user.getUserId());
            model.setUnitId(user.getOwnerId());
            if (CollectionUtils.isNotEmpty(this.getUserBrand(model))) {
                this.getUserBrand(model).forEach(s -> brandList.add(s.getBrandId()));
            }
        }
        if (CollectionUtils.isNotEmpty(brandList)) {
            for (BrandWindowVo vo : list) {
                for (Long s : brandList) {
                    if (vo.getBrandId().equals(s)) {
                        restList.add(vo);
                    }
                }
            }
        }
        return restList;
    }


    private List<Brand> setBrandVo(List<BrandVo> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            List<Brand> brands = new ArrayList<>();
            for (BrandVo v : list) {
                Brand brand = new Brand();
                BeanUtils.copyProperties(v, brand);
                brands.add(brand);
            }
            return brands;
        }
        return null;
    }

    /**
     * 组织授予 回收品牌
     *
     * @param brand
     * @return
     */
    @Override
    public void updateUnitBrand(UserBrandModel brand, SysUser users) {
        SysUser user = ViteAdmin(brand);
        if (user != null) {
            updateBrandPojo(brand, users);
        }
    }


    /**
     * 用户授予 回收品牌
     *
     * @param brand
     * @return
     */
    @Override
    public void updateUserBrand(UserBrandModel brand, SysUser users) {
        SysUser user = ViteAdmin(brand);
        if (user != null) {
            //如果是管理员  如同组织品牌授予、回收
            updateBrandPojo(brand, users);
        } else {
            updateLowUserBrand(brand, users, CommonFainl.ADMIN);
        }
    }


    //普通用户 增加或删除品牌
    private void updateLowUserBrand(UserBrandModel brand, SysUser users, int s) {
        if (StringUtils.isNotEmpty(brand.getAdd())) {
            for (Brand b : brand.getAddBrand()) {
                //新增品牌  （涉及到 已删除组织品牌 或 用户品牌恢复， 以及新增主键冲突问题 待解决
                userBrandMapper.deleteByPrimaryKey(addUserBrand(b, users, brand));
                userBrandMapper.insertSelective(addUserBrand(b, users, brand));
            }
        }
        if (StringUtils.isNotEmpty(brand.getDelete())) {
            //回收品牌
            for (Brand b : brand.getDeleteBrand()) {
                userBrandMapper.updateUserBrand(deleteUserBrand(b, users, brand));
            }
        }
    }

    //组织管理员 增加或删除品牌
    private void updateBrandPojo(UserBrandModel brand, SysUser users) {

        //管理员授品牌和 回收品牌 需要对用户和组织同时操作(组织授予 品牌)
        if (StringUtils.isNotEmpty(brand.getAdd())) {
            //增加品牌
            for (Brand b : brand.getAddBrand()) {
                /**
                 * 新增品牌  涉及到 已删除组织品牌 或 用户品牌恢复， 以及新增主键冲突问题 待解决
                 * 最简单解决方式 先删除在添加
                 * */
                UnitBrand unitBrand = new UnitBrand();
                unitBrand.setUpdateTime(new Date());
                unitBrand.setCreateTime(new Date());
                unitBrand.setCreateBy(users.getUserId());
                unitBrand.setUpdateBy(users.getUserId());
                unitBrand.setUnitId(brand.getUnitId());
                unitBrand.setBrandId(b.getBrandId());
                unitBrand.setIsDel(CommonFainl.BTYPESTATUS);
                unitBrandMapper.deleteByPrimaryKey(unitBrand);
                unitBrandMapper.insertSelective(unitBrand);

                UserBrand userBrand = (addUserBrand(b, users, brand));
                userBrandMapper.deleteByPrimaryKey(userBrand);
                userBrandMapper.insertSelective(userBrand);
            }


        }
        if (StringUtils.isNotEmpty(brand.getDelete())) {
            /**
             * 组织品牌回收
             * 1. 回收 unit_brand 组织品牌
             * 2. 回收 user_brand 对应组织下所有用户品牌
             * */
            for (Brand b : brand.getDeleteBrand()) {
                UnitBrand unitBrand = new UnitBrand();
                unitBrand.setUpdateTime(new Date());
                unitBrand.setUpdateBy(users.getUserId());
                unitBrand.setUnitId(brand.getUnitId());
                unitBrand.setBrandId(b.getBrandId());
                unitBrand.setIsDel(CommonFainl.FAILSTATUS);
                //步骤一
                unitBrandMapper.updateByPrimaryKeySelective(unitBrand);
                UserBrand userBrand = (deleteUserBrand(b, users, brand));
                //步骤二
                userBrandMapper.updateUserBrandList(userBrand);
            }
        }
    }

    // 增加普通用户 品牌对象 CommonFainl.BTYPESTATUS为 1
    private UserBrand addUserBrand(Brand b, SysUser users, UserBrandModel brand) {
        UserBrand userBrand = new UserBrand();
        userBrand.setUserId(brand.getUserId());
        //如果时组织增加品牌 不会传 userId 对应传saId 即组织管理员Id
        if (brand.getSaId() != null) {
            userBrand.setUserId(brand.getSaId());
        }
        userBrand.setBrandId(b.getBrandId());
        userBrand.setOwnerId(brand.getUnitId());
        userBrand.setCreateBy(users.getUserId());
        userBrand.setUpdateBy(users.getUserId());
        userBrand.setCreateTime(new Date());
        userBrand.setUpdateTime(new Date());
        userBrand.setIsDel(CommonFainl.BTYPESTATUS);
        return userBrand;
    }

    //普通用户 品牌删除 对象 CommonFainl.FAILSTATUS为 -1
    private UserBrand deleteUserBrand(Brand b, SysUser users, UserBrandModel brand) {
        UserBrand userBrand = new UserBrand();
        userBrand.setBrandId(b.getBrandId());
        userBrand.setOwnerId(brand.getUnitId());
        userBrand.setUpdateBy(users.getUserId());
        //用户Id
        userBrand.setUserId(brand.getUserId());
        //如果时删除组织品牌组织 不会传 userId 对应传saId 即组织管理员Id
        if (brand.getSaId() != null) {
            userBrand.setUserId(brand.getSaId());
        }
        userBrand.setUpdateTime(new Date());
        userBrand.setIsDel(CommonFainl.FAILSTATUS);
        return userBrand;
    }


    //验证是否是管理员
    private SysUser ViteAdmin(UserBrandModel brand) {
        SysUser u = new SysUser();
        u.setOwnerId(brand.getUnitId());
        if (brand.getUserId() != null) {
            u.setUserId(brand.getUserId());
        }
        if (brand.getSaId() != null) {
            u.setUserId(brand.getSaId());
        }
        return usersService.getAdmin(u);
    }

    //系统管理员查看品牌
    private PageInfo<BrandVo> getPageInfo(Integer page, Integer size, Brand brand) {
        PageHelper.startPage(page, size);
        List<BrandVo> brands = brandMapper.selectAll(brand);
        PageInfo<BrandVo> pageInfo = new PageInfo<>(brands);
        return pageInfo;
    }

    //组织管理员查看品牌
    private PageInfo<BrandVo> getPageInfo(Integer page, Integer size, Brand brand, String parmVal) {
        //开启组织品牌权限
        if (CommonFainl.TRUE.equalsIgnoreCase(parmVal)) {
            PageHelper.startPage(page, size);
            List<BrandVo> brands = brandMapper.selectUnitAll(brand);
            return new PageInfo<>(brands);
        } else {
            //未开启组织品牌权限
            return getPageInfo(page, size, brand);
        }

    }

    //普通用户品牌权限
    private PageInfo<BrandVo> getUserPageInfo(Integer page, Integer size, Brand brand) {
        PageHelper.startPage(page, size);
        List<BrandVo> brands = brandMapper.selectUserAll(brand);
        PageInfo<BrandVo> pageInfo = new PageInfo<>(brands);
        return pageInfo;
    }
}
