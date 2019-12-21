package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PrcType;
import com.boyu.erp.platform.usercenter.entity.sales.SrcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcService;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcTypeService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcAutoVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 退销合同控制层
 * @author: ck
 * @create: 2019-8-13 16:07
 */
@Slf4j
@RestController
@RequestMapping(value = "/sales/src")
public class SalesSrcController extends BaseController {
    private static final String srcCon = "退销合同";

    @Autowired
    private SrcService srcService;
    @Autowired
    private SrcTypeService srcTypeService;
    @Autowired
    private FieldUtils fieldUtils;

    /**
     * 退销合同查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult srcList(PrcVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            PageInfo<PrcVo> resultList = srcService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][srcList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SRC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][srcList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SRC_R, srcCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 退销合同添加
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "退销合同添加")
    @RequestMapping(value = "/addSrc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addSrc(HttpServletRequest re, @RequestBody PrcVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            vo.setVenderId(vo.getsUnitId());
            vo.setUnitId(vo.getsUnitId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, srcService.insertSrc(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesSrcController][addSrc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SRC_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][addSrc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SRC_C, srcCon + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改退销合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改退销合同")
    @RequestMapping(value = "/updateSrc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateSrc(HttpServletRequest re, @RequestBody PrcVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, srcService.updateSrc(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesSrcController][updateSrc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SRC_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][updateSrc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SRC_U, srcCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除退销合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除退销合同")
    @RequestMapping(value = "/deleteSrc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSrc(HttpServletRequest re, @RequestBody PrcVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, srcService.deleteSrc(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesSrcController][deleteSrc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SRC_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][deleteSrc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SRC_D, srcCon + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 类别相关数据查询
     *
     * @return
     */
    @RequestMapping(value = "/srcAutolist", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult srcAutolist(@RequestBody RtcAutoVo vo) {
        try {
            RtcAutoVo prcAutoVo = srcTypeService.selectByRtcAuto(vo.getSrcType());

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prcAutoVo);
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][srcAutolist] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SRC_RTC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][srcAutolist] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SRC_RTC_R, srcCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询退销合同必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getSrcFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getSrcFile(@RequestBody SrcType vo) {
        try {
            if (StringUtils.isNotEmpty(vo.getSrcType())) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("SRC,RTC", vo.getSrcType()));
            } else {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("SRC,RTC", "SALE_SRC"));
            }

        } catch (Exception ex) {
            log.error("[SalesSrcController][getSrcFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 不能直接修改字段
     *
     * @return
     */
    @RequestMapping(value = "/updateNotFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateNotFile(@RequestBody SrcType vo) {
        try {

            List<TableFile> at = new ArrayList<>();
            List<String> list = null;
            if (StringUtils.isNotEmpty(vo.getSrcType())) {
                list = fieldUtils.isNotUpdateWareh(vo.getSrcType());
            } else {
                list = fieldUtils.isNotUpdateWareh("SALE_SRC");
            }
            for (String li : list) {
                TableFile t = new TableFile();
                t.setTableFlie(li);
                at.add(t);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, at);
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][updateNotFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 退销合同中确认单据
     *
     * @return
     */
    @RequestMapping(value = "/confirm", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult confirm(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, srcService.confirm(srcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][confirm] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][confirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, srcCon + CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 退销合同中重做单据
     *
     * @return
     */
    @RequestMapping(value = "/redo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult redo(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, srcService.redo(srcService.selectByOnly(vo)));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, srcCon + CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 退销合同中审核单据
     *
     * @return
     */
    @RequestMapping(value = "/examine", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult examine(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, srcService.examine(srcService.selectByOnly(vo), sysUser, 1));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][examine] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][examine] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, srcCon + CommonFainl.BILLEXAMINEFIANL, "");
        }
    }

    /**
     * 退销合同中重审单据
     *
     * @return
     */
    @RequestMapping(value = "/reiterate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult reiterate(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, srcService.reiterate(srcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, srcCon + CommonFainl.BILLREITERATEFIANL, "");
        }
    }

    /**
     * 退销合同中挂起单据
     *
     * @return
     */
    @RequestMapping(value = "/hangUp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult hangUp(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, srcService.hangUp(srcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, srcCon + CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 退销合同中恢复单据
     *
     * @return
     */
    @RequestMapping(value = "/recovery", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult recovery(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, srcService.recovery(srcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, srcCon + CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 退销合同中关闭单据
     *
     * @return
     */
    @RequestMapping(value = "/close", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult close(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCLOSE, srcService.close(srcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][close] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][close] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, srcCon + CommonFainl.BILLCLOSEFIANL, "");
        }
    }

    /**
     * 退销合同中重用单据
     *
     * @return
     */
    @RequestMapping(value = "/reusing", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult reusing(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREUSING, srcService.reusing(srcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][reusing] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][reusing] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, srcCon + CommonFainl.BILLREUSINGFIANL, "");
        }
    }

    /**
     * 退销申请中作废单据
     *
     * @return
     */
    @RequestMapping(value = "/toVoid", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult toVoid(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, srcService.toVoid(srcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, srcCon + CommonFainl.BILLTOVOIDFIANL, "");
        }
    }

    /**
     * 退销合同中导入原始单据
     *
     * @return
     */
    @RequestMapping(value = "/importBill", method = {RequestMethod.POST})
    public JsonResult importBill(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLIMPORTBILL, srcService.importBill(vo));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][importBill] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][importBill] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, srcCon+CommonFainl.BILLIMPORTBILLFIANL, "");
        }
    }

    /**
     * 采购单中导入更新
     *
     * @return
     */
    @RequestMapping(value = "/importUpdate", method = {RequestMethod.POST})
    public JsonResult importUpdate(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLIMPORTBILL, srcService.importUpdate(vo));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][importUpdate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTUPDATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][importUpdate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTUPDATE, srcCon+CommonFainl.BILLIMPORTUPDATEFIANL, "");
        }
    }

    /**
     * 退销合同可操作状态
     *
     * @return
     */
    @RequestMapping(value = "/srcAuditType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult srcAuditType(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, srcService.initButtons(vo));
        } catch (ServiceException ex) {
            log.error("[SalesSrcController][srcAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSrcController][srcAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, srcCon + CommonFainl.SELECTFIANL, "");
        }
    }


}
