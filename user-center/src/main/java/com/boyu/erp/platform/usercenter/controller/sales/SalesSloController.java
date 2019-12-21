package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscDtlService;
import com.boyu.erp.platform.usercenter.service.salesservice.SloService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 销售单控制层
 * @author: ck
 * @create: 2019-06-21 11:43
 */
@Slf4j
@RestController
@RequestMapping(value = "/sales/slo")
public class SalesSloController extends BaseController {
    private static final String sloCon = "销售单";

    @Autowired
    private SloService sloService;
    @Autowired
    private PscDtlService pscDtlService;

    /**
     * 销售单查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult sloList(PsoVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            PageInfo<PsoVo> resultList = sloService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[SalesSloController][sloList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLO_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][sloList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLO_R, sloCon + CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 添加销售单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加销售单")
    @RequestMapping(value = "/addSlo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addSlo(HttpServletRequest re, @RequestBody PsoVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            vo.setVenderId(vo.getsUnitId());
            vo.setUnitId(vo.getsUnitId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, sloService.insertSlo(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSloController][addSlo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLO_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][addSlo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLO_C, sloCon + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改销售单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改销售单")
    @RequestMapping(value = "/updateSlo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateSlo(HttpServletRequest re, @RequestBody PsoVo vo) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            //要求状态 PROGRESS = PENDING && SUSPENDED = FALSE && CANCELLED = FALSE 才能修改 否则不行
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, sloService.updateSlo(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesSloController][updateSlo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLO_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][updateSlo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLO_U, sloCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除采购单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除采购单")
    @RequestMapping(value = "/deleteSlo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSlo(@RequestBody PsoVo vo) {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, sloService.deleteSlo(vo));

        } catch (ServiceException ex) {
            log.error("[SalesSloController][deleteSlo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLO_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][deleteSlo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLO_D, sloCon + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 判断是否存在商品明细 返回响应数据
     * @return
     */
    @RequestMapping(value = "/getJudgeDtlBySlo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getJudgeDtlBySlo(@RequestBody PscDtlVo vo){
        try
        {
            Map<String,Object> resultMap=new HashMap<String,Object>();

            List<PscDtlVo> list = pscDtlService.getJudgeDtlBySlo(vo);
            if(list.isEmpty()){
                return new JsonResult(JsonResultCode.FAILURE_PUC_PROD_R, CommonFainl.PRODBYSLOFAIL, resultMap);
            }
            resultMap.put("list",list);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex)
        {
            log.error("[SalesSloController][getJudgeDtlBySlo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[SalesSloController][getJudgeDtlBySlo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_R, sloCon+"商品明细"+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 销售单中确认单据
     *
     * @return
     */
    @RequestMapping(value = "/confirm", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult confirm(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, sloService.confirm(sloService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSloController][confirm] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][confirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, sloCon + CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 销售单中重做单据
     *
     * @return
     */
    @RequestMapping(value = "/redo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult redo(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, sloService.redo(sloService.selectByOnly(vo)));
        } catch (ServiceException ex) {
            log.error("[SalesSloController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, sloCon + CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 销售单中审核单据
     *
     * @return
     */
    @RequestMapping(value = "/examine", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult examine(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, sloService.examine(sloService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSloController][examine] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][examine] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, sloCon + CommonFainl.BILLEXAMINEFIANL, "");
        }
    }

    /**
     * 销售中重审单据
     *
     * @return
     */
    @RequestMapping(value = "/reiterate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult reiterate(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, sloService.reiterate(sloService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSloController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, sloCon + CommonFainl.BILLREITERATEFIANL, "");
        }
    }

    /**
     * 销售单中挂起单据
     *
     * @return
     */
    @RequestMapping(value = "/hangUp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult hangUp(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, sloService.hangUp(sloService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSloController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, sloCon + CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 销售单中恢复单据
     *
     * @return
     */
    @RequestMapping(value = "/recovery", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult recovery(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, sloService.recovery(sloService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSloController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, sloCon + CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 销售单中作废单据
     *
     * @return
     */
    @RequestMapping(value = "/toVoid", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult toVoid(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, sloService.toVoid(sloService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSloController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, sloCon + CommonFainl.BILLTOVOIDFIANL, "");
        }
    }

    /**
     * 销售单中导入销售合同
     *
     * @return
     */
    @RequestMapping(value = "/importBill", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult importBill(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLIMPORTBILL, sloService.importBill(vo));
        } catch (ServiceException ex) {
            log.error("[SalesSloController][importBill] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][importBill] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, sloCon + CommonFainl.BILLIMPORTBILLFIANL, "");
        }
    }

    /**
     * 销售单可操作状态
     *
     * @return
     */
    @RequestMapping(value = "/sloAuditType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult sloAuditType(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, sloService.initButtons(vo));
        } catch (ServiceException ex) {
            log.error("[SalesSloController][sloAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSloController][sloAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, sloCon + CommonFainl.SELECTFIANL, "");
        }
    }

}
