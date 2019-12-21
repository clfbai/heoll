package com.boyu.erp.platform.usercenter.controller.purchase;

import com.alibaba.fastjson.JSONObject;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.purchase.VdrSupplyProd;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.purchase.VdrSupplyProdModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.GoodsPopupService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.VdrSupplyProdService;
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
 * @description: 供应商供货信息
 * @author: wz
 * @create: 2019-8-5 11:01
 */
@Slf4j
@RestController
@RequestMapping("/purchase/vdrSupplyProd")
public class PurchaseVdrSupplyProdController extends BaseController {

    private static final String vdrCon = "供应商供货信息";
    //判断权限操作字段
    private final String tableName = "vdr_supply_prod|";
    private static final String Operations = "PurchaseVdrSupplyProdController|";

    @Autowired
    private VenderService venderService;
    @Autowired
    private VdrSupplyProdService vdrSupplyProdService;
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private GoodsPopupService goodsPopupService;


    /**
     * 供应商查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public JsonResult vdrSupplyList(
            VdrSupplyVo vo, @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size,
            HttpServletRequest re
    ) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            vo.setVendeeId(vo.getsUnitId());

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, venderService.selectAll(page, size, vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[PurchaseVdrSupplyProdController][vdrSupplyList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVdrSupplyProdController][vdrSupplyList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_R, vdrCon + CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 供应商供货信息查询
     *
     * @return
     */
    @RequestMapping(value = "/vdrList", method = {RequestMethod.POST})
    public JsonResult vdrSupplyProdList(@RequestBody VdrSupplyProdVo vo, @RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "15") Integer size,
                                        HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, vdrSupplyProdService.selectAllByVdr(page, size, vo));

        } catch (ServiceException ex) {
            log.error("[PurchaseVdrSupplyProdController][vdrSupplyProdList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VDR_SUPPLY_PROD_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVdrSupplyProdController][vdrSupplyProdList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VDR_SUPPLY_PROD_R, vdrCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加/删除明细
     *
     * @return
     */
    @RequestMapping(value = "/addOrDelete", method = {RequestMethod.POST})
    public JsonResult addOrDelete(HttpServletRequest re, @RequestBody VdrSupplyProdModel vo) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            /*if(!vo.getAdd().isEmpty()){
                //判断是否有权限操作
                PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.ADD, vo.getOwnerId(), user);
                if (!examine.getPrivBoolean()) {
                    return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                }
            }
            if(!vo.getDelete().isEmpty()){
                //判断是否有权限操作
                PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.DELETE, vo.getOwnerId(), user);
                if (!examine.getPrivBoolean()) {
                    return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
                }
            }*/

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS, vdrSupplyProdService.addOrDelete(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchaseVdrSupplyProdController][addOrDelete] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VDR_SUPPLY_PROD_C_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVdrSupplyProdController][addOrDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VDR_SUPPLY_PROD_C_D, vdrCon + CommonFainl.FUNCTIONOPFANS, "");
        }
    }

    /**
     * 商品弹窗查询
     * @return
     */
    @RequestMapping(value = "/prodClsList", method = {RequestMethod.GET})
    public JsonResult prodClsList(@RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "1000") Integer size,
                                      HttpServletRequest re, DtlProdVo vo){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            PageInfo<DtlProdVo> resultList = goodsPopupService.selectByVdrSu(vo,page,size,sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex)
        {
            log.error("[PurchaseVdrSupplyProdController][prodClsList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchaseVdrSupplyProdController][prodClsList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, vdrCon+"商品弹窗"+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 输入商品代码判断商品是否存在
     * @return
     */
    @RequestMapping(value = "/getProdCode", method = {RequestMethod.GET})
    public JsonResult getProdCode(@RequestBody  DtlProdVo vo){
        try
        {

            DtlProdVo dtl = goodsPopupService.getProdCode(vo);
            if(dtl == null){
                return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, CommonFainl.SELECTFIANL, "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, dtl);
        } catch (ServiceException ex)
        {
            log.error("[PurchaseVdrSupplyProdController][getProdCode] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchaseVdrSupplyProdController][getProdCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, vdrCon+"商品弹窗"+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 判断该供应商是否供货
     *
     * @param re
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/verification", method = {RequestMethod.POST})
    public JsonResult verification(HttpServletRequest re, @RequestBody JSONObject jsonObject) {
        try {
            String venderId = jsonObject.get("venderId").toString();
            String vendeeId = jsonObject.get("vendeeId").toString();
            String prodId = jsonObject.get("prodId").toString();

            SysUser sysUser = (SysUser) this.isNullUser(re);

            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            VdrSupplyProd prod = vdrSupplyProdService.selectByVer(venderId, vendeeId, prodId);

            if (prod != null) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.VDRSUPPLYPRODVER, "1");
            }

            return new JsonResult(JsonResultCode.FAILURE_VDR_SUPPLY_PROD_VERIFICATION, CommonFainl.VDRSUPPLYPRODVERFIAIL, "0");

        } catch (ServiceException ex) {
            log.error("[PurchaseVdrSupplyProdController][addOrDelete] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VDR_SUPPLY_PROD_VERIFICATION, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVdrSupplyProdController][addOrDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VDR_SUPPLY_PROD_VERIFICATION, vdrCon + CommonFainl.SELECTFIANL, "");
        }
    }

}



