package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.components.GoodsBrandPrivAuthority;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.brand.Brand;
import com.boyu.erp.platform.usercenter.entity.brand.UnitBrand;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.brand.UnitBrandMapper;
import com.boyu.erp.platform.usercenter.model.UserBrandModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.BrandService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.BrandVo;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.system.UserBrandVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 类描述:  品牌控制层
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/22 10:14
 */
@Slf4j
@RestController
@RequestMapping(value = "/product/brand")
public class ProductBrandController extends BaseController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private UnitBrandMapper unitBrandMapper;

    @Autowired
    private UsersService usersService;

    /**
     * 查询品牌
     *
     * @param page
     * @param size
     * @param vo
     * @return
     */

    @RequestMapping(value = "/selectAll", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult selectBrandAll(HttpServletRequest request,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "15") Integer size,
                                     Brand vo) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            PageInfo<BrandVo> brandVoPageInfo = brandService.selectAll(page, size, vo, user);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", brandVoPageInfo);
        } catch (Exception ex) {
            log.error("[ProductBrandController][selectBrandAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 修改品牌
     *
     * @param brand
     * @return
     */
    @SystemLog(name = "修改品牌")
    @GoodsBrandPrivAuthority(name = "updateBrand")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateBrand(@RequestBody Brand brand,
                                  HttpServletRequest request) {
        try {
            SysUser sessionSysUser = this.getSessionSysUser(request);
            brand.setOprId(sessionSysUser.getUserId());
            brand.setUpdTime(new Date());
            int i = brandService.updateByPrimaryKeySelective(brand);
            return new JsonResult(JsonResultCode.SUCCESS, "修改品牌成功", i);
        } catch (Exception ex) {
            log.error("[ProductBrandController][updateBrand] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改品牌失败", "");
        }
    }

    /**
     * 添加品牌
     *
     * @param brand
     * @return
     */
    @SystemLog(name = "添加品牌")
    @GoodsBrandPrivAuthority(name = "addBrand")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addBrand(@RequestBody Brand brand,
                               HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            brand.setOprId(user.getUserId());
            brand.setUpdTime(new Date());
            brand.setHoldUnitId(user.getOwnerId());
            brand.setBrandStatus(CommonFainl.USER_STATUS);
            return new JsonResult(JsonResultCode.SUCCESS, "添加品牌成功",  brandService.insertSelective(brand,user));
        } catch (Exception ex) {
            log.error("[ProductBrandController][addBrand] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "添加品牌失败", "");
        }
    }

    /**
     * 删除品牌
     *
     * @param brand
     * @return
     */
    @SystemLog(name = "删除品牌")
    @GoodsBrandPrivAuthority(name = "deleteBrand")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteBrand(@RequestBody Brand brand,
                                  HttpServletRequest request) {
        try {
            SysUser sessionSysUser = this.getSessionSysUser(request);
            brand.setOprId(sessionSysUser.getUserId());
            brand.setUpdTime(new Date());
            return new JsonResult(JsonResultCode.SUCCESS, "删除品牌成功",  brandService.deleteBrand(brand));
        } catch (Exception ex) {
            log.error("[ProductBrandController][deleteBrand] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除品牌失败", "");
        }
    }

    /**
     * 查看某个用户已有品牌
     */
    @RequestMapping(value = "/user/brand", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult userBbrand(UserBrandModel brand) {
        try {
            Map<String, Object> map = new HashMap<>();
            List<UserBrandVo> list = brandService.getUserBrand(brand);
            map.put("size", list == null ? 0 : list.size());
            map.put("list", list);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (ServiceException ex) {
            log.error("[ProductBrandController][getUserBrand] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductBrandController][ userBbrand] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 查看某个组织已有品牌
     */
    @RequestMapping(value = "/unit/brand", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult unitBrand(UserBrandModel brand) {
        try {
            Map<String, Object> map = new HashMap<>();
            List<UserBrandVo> list = unitBrandMapper.unitBrandTable(brand);
            map.put("size", list == null ? 0 : list.size());
            map.put("list", list);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (Exception ex) {
            log.error("[ProductBrandController][unitBrand] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 用户授予、回收品牌
     */
    @SystemLog(name = "用户授予、回收品牌")
    @GoodsBrandPrivAuthority(name = "userUpdateBrand")
    @RequestMapping(value = "/user/update/brand", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateUserBrand(HttpServletRequest request, @RequestBody UserBrandModel brand) {
        try {
            SysUser user = new SysUser();
            user.setUserId(brand.getUserId());
            user.setOwnerId(brand.getUnitId());
            UnitBrand unit = new UnitBrand();
            unit.setUnitId(brand.getUnitId());

            int bo = 0;
            if (usersService.getAdmin(user) == null) {
                //不是管理员进行组织是否拥有品牌判断
                if (unitBrandMapper.unitListbrand(unit).size() <= 0) {
                    return new JsonResult(JsonResultCode.FAILURE, "请先将该品牌授予用户所在组织", "");
                }
                for (UnitBrand unitBrand : unitBrandMapper.unitListbrand(unit)) {
                    log.info("    " + unit.getBrandId());
                    if (brand.getAddBrand().size() > 0) {
                        for (Brand b : brand.getAddBrand()) {
                            if (unitBrand.getBrandId().equals(b.getBrandId())) {
                                bo++;
                            }
                        }
                    }
                }
                if (bo < brand.getAddBrand().size()) {
                    //添加的品牌内由组织尚未拥有的品牌 不能对用户直接进行操作进行操作
                    return new JsonResult(JsonResultCode.FAILURE, "请先将该品牌授予用户所在组织", "");
                }
            }
            brandService.updateUserBrand(brand, this.getSessionSysUser(request));
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", "");
        } catch (Exception ex) {
            log.error("[ProductBrandController][updateUserBrand] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }

    /**
     * 组织授予、回收品牌
     */
    @SystemLog(name = "组织授予、回收品牌")
    @GoodsBrandPrivAuthority(name = "unitUpdateBrand")
    @RequestMapping(value = "/unit/update/brand", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateUnitBrand(HttpServletRequest request, @RequestBody UserBrandModel brand) {
        try {
            brandService.updateUnitBrand(brand, this.getSessionSysUser(request));
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", "");
        } catch (Exception ex) {
            log.error("[ProductBrandController][updateUnitBrand] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改失败", "");
        }
    }


    /**
     * 用户查看品牌下拉
     */
    @RequestMapping(value = "/userOptions", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult userOptionsBrand(HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);

            List<OptionVo> brand = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(brandService.selectAll(1, 10000, new Brand(), user).getList())) {
                for (BrandVo vo : brandService.selectAll(1, 10000, new Brand(), user).getList()) {
                    OptionVo v = new OptionVo(vo.getBrandName(), vo.getBrandId() + "");
                    brand.add(v);
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("brand", brand);
            return new JsonResult(JsonResultCode.SUCCESS, "查询品牌下拉成功", map);
        } catch (Exception ex) {
            log.error("[ProductBrandController][userOptionsBrand] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询品牌下拉失败:ProductBrandController.userOptionsBrand()", "");
        }
    }
}