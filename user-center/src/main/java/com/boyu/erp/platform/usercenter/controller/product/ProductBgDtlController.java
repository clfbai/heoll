package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.components.GoodsBrandPrivAuthority;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.brand.BgDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.BgDtlModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.BgDtlService;
import com.boyu.erp.platform.usercenter.service.system.BrandService;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname BgDtlController
 * @Description TODO
 * @Date 2019/5/7 19:29
 * @Created by js
 * 品牌分组明细
 */

@Slf4j
@RestController
@RequestMapping(value = "/product/bgdtl")
public class ProductBgDtlController extends BaseController {

    @Autowired
    private BgDtlService bgDtlService;
    @Autowired
    private BrandService brandService;

    /**
     * 查询品牌分组明细
     *
     * @param dtl
     * @return
     */
    @RequestMapping(value = "/selectAll", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getbgdtlAll(@RequestBody BgDtl dtl) {
        try {
            List<BgDtl> pageInfo = bgDtlService.selectAll(dtl);
            Map<String, Object> map = new HashMap<>();
            map.put("size", pageInfo == null ? 0 : pageInfo.size());
            map.put("list", pageInfo);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", map);
        } catch (Exception ex) {
            log.error("[BgDtlController][getbgdtlAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


    /**
     * 删除品牌分组明细
     *
     * @param dtl
     * @return
     */
    @SystemLog(name = "删除品牌分组明细")
    @GoodsBrandPrivAuthority(name = "dtl_Bg")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteBgdtl(@RequestBody BgDtl dtl, HttpServletRequest request) {
        try {
            int i = bgDtlService.deleteBgDtl(dtl);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", i);
        } catch (Exception ex) {
            log.error("[BgDtlController][deletebgdtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 添加品牌分组明细
     *
     * @param dtl
     * @return
     */
    @SystemLog(name = "添加品牌分组明细")
    @GoodsBrandPrivAuthority(name = "dtl_Bg")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addBgdtl(@RequestBody BgDtl dtl, HttpServletRequest request) {
        try {
            int i = bgDtlService.insertSelective(dtl);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", i);
        } catch (Exception ex) {
            log.error("[BgDtlController][addbgdtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 修改品牌分组明细
     *
     * @param dtl
     * @return
     */
    @SystemLog(name = "修改品牌分组明细")
    @GoodsBrandPrivAuthority(name = "dtl_Bg")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateBgdtl(@RequestBody BgDtlModel dtl, HttpServletRequest request) {
        try {
            int i = bgDtlService.updateBgAndBgDtl(dtl);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", i);
        } catch (ServiceException ex) {
            log.error("[BgDtlService][updateBgAndBgDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[BgDtlController][addbgdtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 用户修改品牌分组明细下拉选择品牌(用户能操作的品牌)
     *
     * @return
     */

    @RequestMapping(value = "/get/userBrand", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getUserBrand(HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", RestulMap.getResut(brandService.userOperationBrand(this.getSessionSysUser(request))));
        } catch (ServiceException ex) {
            log.error("[ColorController][colorList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[BgDtlController][addbgdtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

}
