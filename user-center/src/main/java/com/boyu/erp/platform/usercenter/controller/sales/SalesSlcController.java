package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.sales.SlcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcTypeService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PscAutoVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 销售合同控制层
 * @author: ck
 * @create: 2019-06-21 11:43
 */
@Slf4j
@RestController
@RequestMapping(value = "/sales/slc")
public class SalesSlcController extends BaseController {
    private static final String slcCon = "销售合同";

    @Autowired
    private SlcService slcService;
    @Autowired
    private SlcTypeService slcTypeService;
    @Autowired
    private FieldUtils fieldUtils;

    /**采购商弹窗  调用供应商弹窗  传unitType =  VE
     * 供应商仓库  	WH	当前组织id
     * 采购商仓库     WH  采购商id
     * 始发方         VD  当前组织id
     * 始发方仓库      WH   始发方id
     * 转送方          VE   当前组织id
     * 转送方仓库      WH  转送方id
     * 中转方          VE   当前组织id
     */

    /**
     * 销售合同查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult slcList(PscVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            PageInfo<PscVo> resultList = slcService.selectAll(vo, page, size, sysUser);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][slcList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][slcList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_R, slcCon + CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 添加销售合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加销售合同")
    @RequestMapping(value = "/addSlc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addSlc(HttpServletRequest re, @RequestBody PscVo vo) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            vo.setVenderId(vo.getsUnitId()+"");
            vo.setUnitId(vo.getsUnitId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, slcService.insertSlc(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][addSlc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][addSlc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_C, slcCon + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改销售合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改销售合同")
    @RequestMapping(value = "/updateSlc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateSlc(HttpServletRequest re, @RequestBody PscVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, slcService.updateSlc(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesSlcController][updateSlc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][updateSlc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_U, slcCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除销售合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除销售合同")
    @RequestMapping(value = "/deleteSlc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSlc(HttpServletRequest re, @RequestBody PscVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, slcService.deleteSlc(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesSlcController][deleteSlc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][deleteSlc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_D, slcCon + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 类别相关数据查询
     *
     * @return
     */
    @RequestMapping(value = "/slcAutolist", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult slcAutolist(@RequestBody PscAutoVo vo) {
        try {
            PscAutoVo pscAutoVo = slcTypeService.selectByPscAuto(vo.getSlcType());

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pscAutoVo);
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][slcAutolist] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_PSC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][slcAutolist] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_PSC_R, slcCon + CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 查询采购合同必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getSLcFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getSLcFile(@RequestBody SlcType vo) {
        try {
            if (StringUtils.isNotEmpty(vo.getSlcType())) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("SLC,PSC", vo.getSlcType()));
            } else {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("SLC,PSC", "SALE_SLC"));
            }

        } catch (Exception ex) {
            log.error("[SalesSlcController][getSLcFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 不能直接修改字段
     *
     * @return
     */
    @RequestMapping(value = "/updateNotFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateNotFile(@RequestBody SlcType vo) {
        try {

            List<TableFile> at = new ArrayList<>();
            List<String> list = null;
            if (vo.getSlcType() != null) {
                list = fieldUtils.isNotUpdateWareh(vo.getSlcType());
            } else {
                list = fieldUtils.isNotUpdateWareh("SLC");
            }
            if (list != null) {
                for (String li : list) {
                    TableFile t = new TableFile();
                    t.setTableFlie(li);
                    at.add(t);
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, at);
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][updateNotFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 销售合同中确认单据
     *
     * @return
     */
    @RequestMapping(value = "/confirm", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult confirm(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, slcService.confirm(slcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][confirm] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][confirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, slcCon + CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 销售合同中重做单据
     *
     * @return
     */
    @RequestMapping(value = "/redo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult redo(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, slcService.redo(slcService.selectByOnly(vo)));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, slcCon + CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 销售合同中审核单据
     *
     * @return
     */
    @RequestMapping(value = "/examine", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult examine(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //订单来源
            int orderType = 1;
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, slcService.examine(slcService.selectByOnly(vo), sysUser, orderType));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][examine] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][examine] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, slcCon + CommonFainl.BILLEXAMINEFIANL, "");
        }
    }

    /**
     * 销售合同中重审单据
     *
     * @return
     */
    @RequestMapping(value = "/reiterate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult reiterate(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, slcService.reiterate(slcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, slcCon + CommonFainl.BILLREITERATEFIANL, "");
        }
    }

    /**
     * 销售合同中关闭单据
     *
     * @return
     */
    @RequestMapping(value = "/close", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult close(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCLOSE, slcService.close(slcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][close] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][close] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, slcCon + CommonFainl.BILLCLOSEFIANL, "");
        }
    }

    /**
     * 采购合同中重用单据
     *
     * @return
     */
    @RequestMapping(value = "/reusing", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult reusing(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREUSING, slcService.reusing(slcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][reusing] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][reusing] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, slcCon + CommonFainl.BILLREUSINGFIANL, "");
        }
    }

    /**
     * 销售合同中挂起单据
     *
     * @return
     */
    @RequestMapping(value = "/hangUp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult hangUp(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, slcService.hangUp(slcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, slcCon + CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 销售合同中恢复单据
     *
     * @return
     */
    @RequestMapping(value = "/recovery", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult recovery(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, slcService.recovery(slcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, slcCon + CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 销售合同中作废单据
     *
     * @return
     */
    @RequestMapping(value = "/toVoid", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult toVoid(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, slcService.toVoid(slcService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, slcCon + CommonFainl.BILLTOVOIDFIANL, "");
        }
    }

    /**
     * 销售合同中导入原始单据
     *
     * @return
     */
    @RequestMapping(value = "/importBill", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult importBill(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLIMPORTBILL, slcService.importBill(vo));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][importBill] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTBILL, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][importBill] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTBILL, slcCon+CommonFainl.BILLIMPORTBILLFIANL, "");
        }
    }

    /**
     * 销售合同可操作状态
     *
     * @return
     */
    @RequestMapping(value = "/slcAuditType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult slcAuditType(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, slcService.initButtons(vo));
        } catch (ServiceException ex) {
            log.error("[SalesSlcController][slcAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlcController][slcAuditType ] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, slcCon + CommonFainl.SELECTFIANL, "");
        }
    }

}
