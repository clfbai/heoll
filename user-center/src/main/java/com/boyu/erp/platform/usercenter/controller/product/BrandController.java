package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.brand.Brand;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.BrandService;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.vo.system.TreeKeyValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 品牌控制层
 * @author: clf
 * @create: 2019-06-10 15:29
 */
@Slf4j
@RestController
@RequestMapping("/product/brand")
public class BrandController extends BaseController {
    @Autowired
    private BrandService brandService;


    /**
     * 功能描述:  用户登录组织拥有品牌查询(获取品牌权限)
     * 判断牌品牌权限参数获取用能看到品牌
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/19 15:37
     */
    @PostMapping(value = "/listUser")
    public JsonResult brandUser(@RequestBody Brand brand, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", RestulMap.getResut(brandService.userBrandList(user)));
        } catch (ServiceException ex) {
            log.error("[BrandService][userBrandList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "查询查询用户拥有品牌服务异常");
        } catch (Exception ex) {
            log.error("[BrandController][brandUser] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询用户拥有品牌失败", "");
        }
    }

    /**
     * 品牌Id对应品牌名称下拉
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult brandOption() {
        try {
            List<Brand> list = brandService.optionGet();
            List<TreeKeyValue> keys = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            if (list.size() > 0) {
                for (Brand m : list) {
                    TreeKeyValue ms = new TreeKeyValue(m.getBrandName(), m.getBrandId());
                    keys.add(ms);
                }
            }
            map.put("list", keys);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (ServiceException ex) {
            log.error("[BrandService][brandService] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "品牌下拉服务异常");
        } catch (Exception ex) {
            log.error("[BrandController][brandOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败失败", "");
        }
    }

    /**
     * 功能描述:  用户查询品牌弹窗(通用)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/19 15:37
     */
    @PostMapping(value = "/Window")
    public JsonResult brandWindow(@RequestBody Brand brand, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", RestulMap.getResut(brandService.getUserBrandWindow(brand, user)));
        } catch (ServiceException ex) {
            log.error("[BrandService][brandService] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "品牌弹窗查询服务异常");
        } catch (Exception ex) {
            log.error("[BrandController][brandOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }
}



