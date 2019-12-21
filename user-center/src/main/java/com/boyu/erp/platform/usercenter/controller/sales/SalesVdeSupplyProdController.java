package com.boyu.erp.platform.usercenter.controller.sales;

import com.alibaba.fastjson.JSONObject;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.VdrSupplyProd;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.purchase.VdrSupplyProdModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.GoodsPopupService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.VdrSupplyProdService;
import com.boyu.erp.platform.usercenter.service.system.VendeeService;
import com.boyu.erp.platform.usercenter.service.system.VenderService;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @program: boyu-platform
 * @description: 采购商购货信息
 * @author: wz
 * @create: 2019-8-5 11:01
 */
@Slf4j
@RestController
@RequestMapping("/sales/vdeSupplyProd")
public class SalesVdeSupplyProdController extends BaseController {

    private static final String vdeCon = "采购商购货信息";
    //判断权限操作字段
    private final String tableName = "vde_supply_prod|";
    private static final String Operations = "SalesVdeSupplyProdController|";

    @Autowired
    private VendeeService vendeeService;
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private GoodsPopupService goodsPopupService;


    /**
     * 采购商查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public JsonResult vdeSupplyList(
            VdrSupplyVo vo, @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size,
            HttpServletRequest re
    ) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            vo.setVenderId(vo.getsUnitId());

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, vendeeService.selectAll(page, size, vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesVdeSupplyProdController][vdeSupplyList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDEE_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVdeSupplyProdController][vdeSupplyList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDEE_R, vdeCon + CommonFainl.SELECTFIANL, "");
        }
    }

}



