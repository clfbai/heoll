package com.boyu.erp.platform.usercenter.controller.price;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname PpnController
 * @Description TODO
 * @Date 2019/8/26 9:39
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/price/ppn")
public class PpnController extends BaseController {
    private static final String ppnCon = "采购价格单";

    @Autowired
    private PpnService ppnService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private PsaService psaService;

    /**
     * 采购价格单查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult ppnList(PpnVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            vo.setUnitId(sysUser.getDomain().getUnitId());

            PageInfo<PpnVo> resultList = ppnService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[PpnController][ppnList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][ppnList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_R, ppnCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加采购价格单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加采购价格单")
    @RequestMapping(value = "/addPpn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPpn(HttpServletRequest re, @RequestBody PpnVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, ppnService.insertPpn(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[PpnController][addPpn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][addPpn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_C, ppnCon+CommonFainl.ADDFIANL, "");
        }
    }


    /**
     * 修改采购价格单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改采购价格单")
    @RequestMapping(value = "/updatePpn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePpn(HttpServletRequest re, @RequestBody PpnVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, ppnService.updatePpn(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[PpnController][updatePpn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][updatePpn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_U, ppnCon+CommonFainl.UPDATEFANS, "");
        }
    }


    /**
     * 删除采购价格单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除采购价格单")
    @RequestMapping(value = "/deletePpn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePpn(HttpServletRequest re, @RequestBody PpnVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, ppnService.deletePpn(vo, sysUser));


        } catch (ServiceException ex) {
            log.error("[PpnController][deletePpn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][deletePpn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_D, ppnCon+CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 切换定价范围的时候删除定价范围
     */
    @SystemLog(name = "切换定价范围的时候删除定价范围")
    @RequestMapping(value = "/deletePpnScp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePpnScp(HttpServletRequest re, @RequestBody PpnVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, ppnService.deletePpnScp(vo));


        } catch (ServiceException ex) {
            log.error("[PpnController][deletePpnScp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][deletePpnScp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPN_D, ppnCon+CommonFainl.DELETEFANS, "");
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
            map.put("prcRsn", sysCodeDtlService.optionGet("PRC_RSN"));//定价原因
            map.put("prcPlcy", sysCodeDtlService.optionGet("PRC_PLCY"));//定价策略
            map.put("specOfr", prodClsUtils.getList());//是否特价

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[PpnController][option] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 判断是否可以添加该供应商
     *
     * @return
     */
    @SystemLog(name = "判断是否可以添加该供应商")
    @RequestMapping(value = "/addVender", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addVender(HttpServletRequest re,@RequestBody String venderId) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //去购销协议中判断是否有数据，参照者是否为空，否则抛异常
            psaService.judge(venderId,"",sysUser);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, "");
        } catch (ServiceException ex) {
            log.error("[PpnController][isUpdateOrDelete] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][isUpdateOrDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ppnCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 判断是否可以删除/修改
     *
     * @param ppn
     * @return
     */
    @SystemLog(name = "判断是否可以删除/修改")
    @RequestMapping(value = "/isUpdateOrDelete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult isUpdateOrDelete(@RequestBody PpnVo ppn) {
        try {

            Map<String, Object> map = new HashMap<>();
            int delete = 0;
            int update = 0;
            //状态要求：PROGRESS IN (PENDING, CONFIRMED) && SUSPENDED = FALSE && CANCELLED = FALSE
            if ((ppn.getProgress().equals("PG") || ppn.getProgress().equals("C")) &&
                    ppn.getSuspended().equals("F") &&
                    ppn.getCancelled().equals("F") ) {
                delete = 1;
            }
            //要求状态 PROGRESS = PENDING && SUSPENDED = FALSE && CANCELLED = FALSE 才能修改 否则不行
            if (ppn.getProgress().equals("PG") && ppn.getSuspended().equals("F") && ppn.getCancelled().equals("F")) {
                update = 1;
            }
            map.put("update",update);
            map.put("delete",delete);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[PpnController][isUpdateOrDelete] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][isUpdateOrDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, ppnCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 采购价格单中确认单据
     *
     * @return
     */
    @RequestMapping(value = "/confirm", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult confirm(@RequestBody PpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, ppnService.confirm(vo));
        } catch (ServiceException ex) {
            log.error("[PpnController][confirm] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][confirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ppnCon+CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 采购价格单中重做单据
     *
     * @return
     */
    @RequestMapping(value = "/redo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult redo(@RequestBody PpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, ppnService.redo(vo));
        } catch (ServiceException ex) {
            log.error("[PpnController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ppnCon+CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 采购价格单中审核单据
     *
     * @return
     */
    @RequestMapping(value = "/examine", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult examine(@RequestBody PpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, ppnService.examine(vo,sysUser));
        } catch (ServiceException ex) {
            log.error("[PpnController][examine] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][examine] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ppnCon+CommonFainl.BILLEXAMINEFIANL, "");
        }
    }

    /**
     * 采购价格单中重审单据
     *
     * @return
     */
    @RequestMapping(value = "/reiterate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult reiterate(@RequestBody PpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, ppnService.reiterate(vo));
        } catch (ServiceException ex) {
            log.error("[PpnController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ppnCon+CommonFainl.BILLREITERATEFIANL, "");
        }
    }

    /**
     * 采购价格单中挂起单据
     *
     * @return
     */
        @RequestMapping(value = "/hangUp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult hangUp(@RequestBody PpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, ppnService.hangUp(vo));
        } catch (ServiceException ex) {
            log.error("[PpnController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ppnCon+CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 采购价格单中恢复单据
     *
     * @return
     */
    @RequestMapping(value = "/recovery", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult recovery(@RequestBody PpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, ppnService.recovery(vo));
        } catch (ServiceException ex) {
            log.error("[PpnController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ppnCon+CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 采购价格单中作废单据
     *
     * @return
     */
    @RequestMapping(value = "/toVoid", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult toVoid(@RequestBody PpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, ppnService.toVoid(vo));
        } catch (ServiceException ex) {
            log.error("[PpnController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ppnCon+CommonFainl.BILLTOVOIDFIANL, "");
        }
    }

    /**
     * 采购价格单中执行单据
     *
     * @return
     */
    @RequestMapping(value = "/implement", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult implement(@RequestBody PpnVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLIMPLEMENT, ppnService.implement(vo,sysUser));
        } catch (ServiceException ex) {
            log.error("[PpnController][implement] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPLEMENT, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnController][implement] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPLEMENT, ppnCon+CommonFainl.BILLIMPLEMENTFIANL, "");
        }
    }



}
