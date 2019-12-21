package com.boyu.erp.platform.usercenter.controller.price;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.priceservice.SpnService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitHierService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname SpnController 销售价格单
 * @Description TODO
 * @Date 2019/8/27 11:48
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/price/spn")
public class SpnController extends BaseController {
    private static final String spnCon = "销售价格单";

    @Autowired
    private SpnService spnService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private SysUnitHierService sysUnitHierService;
    @Autowired
    private PsaService psaService;

    /**
     * 销售价格单查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult spnList(SpnVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            vo.setUnitId(sysUser.getDomain().getUnitId());

            PageInfo<SpnVo> resultList = spnService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[SpnController][spnList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][spnList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_R, spnCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加销售价格单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加销售价格单")
    @RequestMapping(value = "/addSpn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addSpn(HttpServletRequest re, @RequestBody SpnVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, spnService.insertSpn(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SpnController][addSpn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][addSpn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_C, spnCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改销售价格单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改销售价格单")
    @RequestMapping(value = "/updateSpn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateSpn(HttpServletRequest re, @RequestBody SpnVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, spnService.updateSpn(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SpnController][updateSpn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][updateSpn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_U, spnCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除销售价格单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除销售价格单")
    @RequestMapping(value = "/deleteSpn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSpn(HttpServletRequest re, @RequestBody SpnVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, spnService.deleteSpn(vo, sysUser));


        } catch (ServiceException ex) {
            log.error("[SpnController][deleteSpn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][deleteSpn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_D, spnCon+CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 切换定价范围时删除范围
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除销售价格单范围")
    @RequestMapping(value = "/deleteSpnScp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSpnScp(HttpServletRequest re, @RequestBody SpnVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, spnService.deleteSpnScp(vo));


        } catch (ServiceException ex) {
            log.error("[SpnController][deleteSpnScp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][deleteSpnScp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_D, spnCon+CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult option() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();

            map.put("prcScp", sysCodeDtlService.optionGet("PRC_SCP"));//定价范围
            map.put("xpType", sysCodeDtlService.optionGet("XP_TYPE"));//价格类别
            map.put("rsvUnit", prodClsUtils.getList());//保留组织底价
            map.put("handOn", prodClsUtils.getList());//向下传递
            map.put("prcRsn", sysCodeDtlService.optionGet("PRC_RSN"));//定价原因
            map.put("prcPlcy", sysCodeDtlService.optionGet("PRC_PLCY"));//定价策略
            map.put("unitHierId", sysUnitHierService.optionGet());//组织层级
            map.put("specOfr", prodClsUtils.getList());//是否特价

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[SpnController][option] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 判断是否可以添加该采购商
     *
     * @return
     */
    @SystemLog(name = "判断是否可以添加该采购商")
    @RequestMapping(value = "/addVendee", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addVendee(HttpServletRequest re,@RequestBody String vendeeId) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //去购销协议中判断是否有数据，参照者是否为空，否则抛异常
            psaService.judge("",vendeeId,sysUser);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, "");
        } catch (ServiceException ex) {
            log.error("[SpnController][addVendee] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][addVendee] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, spnCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 判断是否可以删除/修改
     *
     * @param spn
     * @return
     */
    @SystemLog(name = "判断是否可以删除/修改")
    @RequestMapping(value = "/isUpdateOrDelete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult isUpdateOrDelete(@RequestBody SpnVo spn) {
        try {

            Map<String, Object> map = new HashMap<>();
            int delete = 0;
            int update = 0;
            //状态要求：PROGRESS IN (PENDING, CONFIRMED) && SUSPENDED = FALSE && CANCELLED = FALSE
            if ((spn.getProgress().equals("PG") || spn.getProgress().equals("C")) &&
                    spn.getSuspended().equals("F") &&
                    spn.getCancelled().equals("F") ) {
                delete = 1;
            }
            //要求状态 PROGRESS = PENDING && SUSPENDED = FALSE && CANCELLED = FALSE 才能修改 否则不行
            if (spn.getProgress().equals("PG") && spn.getSuspended().equals("F") && spn.getCancelled().equals("F")) {
                update = 1;
            }
            map.put("update",update);
            map.put("delete",delete);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[SpnController][isUpdateOrDelete] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][isUpdateOrDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, spnCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 销售价格单中确认单据
     *
     * @return
     */
    @RequestMapping(value = "/confirm", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult confirm(@RequestBody SpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, spnService.confirm(vo));
        } catch (ServiceException ex) {
            log.error("[SpnController][confirm] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][confirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, spnCon+CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 销售价格单中重做单据
     *
     * @return
     */
    @RequestMapping(value = "/redo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult redo(@RequestBody SpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, spnService.redo(vo));
        } catch (ServiceException ex) {
            log.error("[SpnController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, spnCon+CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 销售价格单中审核单据
     *
     * @return
     */
    @RequestMapping(value = "/examine", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult examine(@RequestBody SpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, spnService.examine(vo,sysUser));
        } catch (ServiceException ex) {
            log.error("[SpnController][examine] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][examine] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, spnCon+CommonFainl.BILLEXAMINEFIANL, "");
        }
    }

    /**
     * 销售价格单中重审单据
     *
     * @return
     */
    @RequestMapping(value = "/reiterate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult reiterate(@RequestBody SpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, spnService.reiterate(vo));
        } catch (ServiceException ex) {
            log.error("[SpnController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, spnCon+CommonFainl.BILLREITERATEFIANL, "");
        }
    }
    /**
     * 销售价格单中挂起单据
     *
     * @return
     */
    @RequestMapping(value = "/hangUp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult hangUp(@RequestBody SpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, spnService.hangUp(vo));
        } catch (ServiceException ex) {
            log.error("[SpnController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, spnCon+CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 采购价格单中恢复单据
     *
     * @return
     */
    @RequestMapping(value = "/recovery", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult recovery(@RequestBody SpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, spnService.recovery(vo));
        } catch (ServiceException ex) {
            log.error("[SpnController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, spnCon+CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 采购价格单中作废单据
     *
     * @return
     */
    @RequestMapping(value = "/toVoid", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult toVoid(@RequestBody SpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, spnService.toVoid(vo));
        } catch (ServiceException ex) {
            log.error("[SpnController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, spnCon+CommonFainl.BILLTOVOIDFIANL, "");
        }
    }

    /**
     * 采购价格单中执行单据
     *
     * @return
     */
    @RequestMapping(value = "/implement", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult implement(@RequestBody SpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLIMPLEMENT, spnService.implement(vo,sysUser));
        } catch (ServiceException ex) {
            log.error("[SpnController][implement] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPLEMENT, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnController][implement] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPLEMENT, spnCon+CommonFainl.BILLIMPLEMENTFIANL, "");
        }
    }


}
