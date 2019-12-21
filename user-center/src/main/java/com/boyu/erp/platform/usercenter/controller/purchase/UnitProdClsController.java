package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.purchase.PscDtlModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnScpService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.UnitProdClsService;
import com.boyu.erp.platform.usercenter.service.salesservice.SpnScpService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.BillDtlProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.OptionByPsaVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 组织商品
 * @author: wz
 * @create: 2019-7-10 14:21
 */
@Slf4j
@RestController
@RequestMapping("/purchase/unitProdCls")
public class UnitProdClsController extends BaseController {
    private static final String unitProdClsCon = "组织商品";
    @Autowired
    private UnitProdClsService unitProdClsService;

    @Autowired
    private PpnScpService ppnScpService;
    @Autowired
    private SpnScpService spnScpService;
    /**
     * 商品弹窗查询
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult unitProdClsList(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "1000") Integer size,
                              HttpServletRequest re, DtlProdVo p){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            PageInfo<DtlProdVo> resultList = unitProdClsService.selectByDtl(p,page,size,sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex)
        {
            log.error("[UnitProdClsController][unitProdClsList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[UnitProdClsController][unitProdClsList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, unitProdClsCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 商品弹窗查询
     * @return
     */
    @RequestMapping(value = "/listByProdCode", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult listByProdCode(HttpServletRequest re,@RequestBody  DtlProdVo p){
        try
        {

            DtlProdVo vo = unitProdClsService.selectByProdCode(p);
            if(vo == null){
                return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, CommonFainl.SELECTFIANL, "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, vo);
        } catch (ServiceException ex)
        {
            log.error("[UnitProdClsController][listByProdCode] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[UnitProdClsController][listByProdCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, unitProdClsCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 采购销售订单中商品弹窗
     * @param page
     * @param size
     * @param re
     * @param vo
     * @return
     */
    @RequestMapping(value = "/billProdlist", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult billProdlist(@RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "1000") Integer size,
                                      HttpServletRequest re, BillDtlProdVo vo){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            PageInfo<DtlProdVo> resultList = unitProdClsService.selectByBillDtl(vo,page,size,sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex)
        {
            log.error("[UnitProdClsController][billProdlist] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[UnitProdClsController][billProdlist] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, unitProdClsCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 采购销售订单中输入商品代码查询商品信息
     * @param re
     * @param vo
     * @return
     */
    @RequestMapping(value = "/billByProdCode", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult billByProdCode(HttpServletRequest re,@RequestBody  BillDtlProdVo vo){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            if(StringUtils.NullEmpty(vo.getVendeeId()+"")){
                vo.setVendeeId(vo.getsUnitId());
            }
            if(StringUtils.NullEmpty(vo.getVenderId()+"")){
                vo.setVenderId(vo.getsUnitId());
            }
            DtlProdVo prod = unitProdClsService.findByBillProdCode(vo,sysUser);
            if(prod == null){
                return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, CommonFainl.SELECTFIANL, new DtlProdVo());
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prod);
        } catch (ServiceException ex)
        {
            log.error("[UnitProdClsController][billByProdCode] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[UnitProdClsController][billByProdCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_UNIT_PROD_CLS_R, unitProdClsCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 通过合同中的供应商id，采购商id和商品id 去采购价格单的表中去查询
     */
    @RequestMapping(value = "/updatePscList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePscList(@RequestBody PscDtlModel vo){
        try
        {
            List<DtlProdVo> voList = ppnScpService.updateList(vo);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, voList);
        } catch (ServiceException ex)
        {
            log.error("[UnitProdClsController][updatePscList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[UnitProdClsController][updatePscList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, unitProdClsCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 通过合同中的供应商id，采购商id和商品id 去销售价格单的表中去查询
     */
    @RequestMapping(value = "/updateSalesPscList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateSalesPscList(@RequestBody PscDtlModel vo){
        try
        {
            List<DtlProdVo> voList = spnScpService.updateList(vo);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, voList);
        } catch (ServiceException ex)
        {
            log.error("[UnitProdClsController][updateSalesPscList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[UnitProdClsController][updateSalesPscList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, unitProdClsCon+CommonFainl.UPDATEFANS, "");
        }
    }
}



