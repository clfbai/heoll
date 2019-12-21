package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.sales.SlaType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.salesservice.SlaService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlaTypeService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname SalesSlaController
 * @Description TODO
 * @Date 2019/9/4 19:27
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/sales/sla")
public class SalesSlaController extends BaseController {

    private static final String slaCon = "销售申请";

    @Autowired
    private SlaService slaService;
    @Autowired
    private SlaTypeService slaTypeService;
    @Autowired
    private FieldUtils fieldUtils;


    /**
     * 销售申请查询(销售合同用到)
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult slaList(PsxVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            PageInfo<PsxVo> resultList = slaService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][slaList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][slaList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_R, slaCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 销售申请添加
     *
     * @return
     */
    @RequestMapping(value = "/addSla", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addSla(HttpServletRequest re, @RequestBody PsxVo vo) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            vo.setVenderId(vo.getsUnitId());
            vo.setUnitId(vo.getsUnitId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, slaService.insertSla(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesSlaController][addSla] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][addSla] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_C, slaCon + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 销售申请修改
     *
     * @return
     */
    @RequestMapping(value = "/updateSla", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateSla(HttpServletRequest re, @RequestBody PsxVo vo) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, slaService.updateSla(vo, sysUser));


        } catch (ServiceException ex) {
            log.error("[SalesSlaController][updateSla] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][updateSla] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_U, slaCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 销售申请删除
     *
     * @return
     */
    @RequestMapping(value = "/deleteSla", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSla(HttpServletRequest re, @RequestBody PsxVo vo) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, slaService.deleteSla(vo));


        } catch (ServiceException ex) {
            log.error("[SalesSlaController][deleteSla] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][deleteSla] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_D, slaCon + CommonFainl.DELETEFANS, "");
        }
    }


    /**
     * 通过选择的销售申请类别获取购销申请类别的详细信息
     */
    @RequestMapping(value = "/psxTypeList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psxTypeList(@RequestBody SlaType vo) {
        try {
            PsxType type = slaTypeService.selectByPsxType(vo);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, type);
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][psxTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_TYPE_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][psxTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_TYPE_R, slaCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询销售申请必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getSlaFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getSlaFile(@RequestBody SlaType type) {
        try {
            if (StringUtils.isNotEmpty(type.getSlaType())) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("SLA,PSX", type.getSlaType()));
            }else{
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("SLA,PSX", "SALE_SLA"));
            }
        } catch (Exception ex) {
            log.error("[SalesSlaController][getSlaFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 不能直接修改字段
     *
     * @return
     */
    @RequestMapping(value = "/updateNotFile", method = {RequestMethod.POST})
    public JsonResult updateNotFile(@RequestBody SlaType type) {
        try {

            List<TableFile> at = new ArrayList<>();
            List<String> list = null;
            if (StringUtils.isNotEmpty(type.getSlaType())) {
                list = fieldUtils.isNotUpdateWareh(type.getSlaType());
            } else {
                list = fieldUtils.isNotUpdateWareh("SALE_SLA");
            }
            for (String li : list) {
                TableFile t = new TableFile();
                t.setTableFlie(li);
                at.add(t);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, at);
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][updateNotFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 销售申请中确认单据
     *
     * @return
     */
    @RequestMapping(value = "/confirm", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult confirm(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, slaService.confirm(slaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][confirm] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][confirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, slaCon + CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 销售申请中重做单据
     *
     * @return
     */
    @RequestMapping(value = "/redo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult redo(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, slaService.redo(slaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, slaCon + CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 销售申请中审核单据
     *
     * @return
     */
    @RequestMapping(value = "/examine", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult examine(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, slaService.examine(slaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][examine] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][examine] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, slaCon + CommonFainl.BILLEXAMINEFIANL, "");
        }
    }


    /**
     * 销售申请中重审单据
     *
     * @return
     */
    @RequestMapping(value = "/reiterate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult reiterate(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, slaService.reiterate(slaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, slaCon + CommonFainl.BILLREITERATEFIANL, "");
        }
    }

    /**
     * 销售申请中挂起单据
     *
     * @return
     */
    @RequestMapping(value = "/hangUp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult hangUp(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, slaService.hangUp(slaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, slaCon + CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 销售申请中恢复单据
     *
     * @return
     */
    @RequestMapping(value = "/recovery", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult recovery(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, slaService.recovery(slaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, slaCon + CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 销售申请中作废单据
     *
     * @return
     */
    @RequestMapping(value = "/toVoid", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult toVoid(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, slaService.toVoid(slaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, slaCon + CommonFainl.BILLTOVOIDFIANL, "");
        }
    }

    /**
     * 销售申请中关闭单据
     *
     * @return
     */
    @RequestMapping(value = "/close", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult close(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCLOSE, slaService.close(slaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][close] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][close] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, slaCon + CommonFainl.BILLCLOSEFIANL, "");
        }
    }

    /**
     * 销售申请中重用单据
     *
     * @return
     */
    @RequestMapping(value = "/reusing", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult reusing(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            vo = slaService.selectByOnly(vo);
            if (vo == null) {
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREUSING, slaService.reusing(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][reusing] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][reusing] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, slaCon + CommonFainl.BILLREUSINGFIANL, "");
        }
    }

    /**
     * 销售申请可操作状态
     *
     * @return
     */
    @RequestMapping(value = "/slaAuditType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult slaAuditType(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, slaService.initButtons(vo));
        } catch (ServiceException ex) {
            log.error("[SalesSlaController][slaAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaController][slaAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, slaCon + CommonFainl.SELECTFIANL, "");
        }
    }

}
