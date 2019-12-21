package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.TPOS.service.WmsErpNumService;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.GrnDtlModel;
import com.boyu.erp.platform.usercenter.model.wareh.GrnModel;
import com.boyu.erp.platform.usercenter.model.wareh.GrnStbDtlModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.warehouse.*;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.FiledUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.common.RestulMap;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.GrnUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * @program: boyu-platform_text
 * @description: 入库单控制层
 * @author: ck
 * @create: 2019-07-01 10:26
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/grn")
public class GrnController extends BaseController {
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private WmsErpNumService wmsErpNumService;
    @Autowired
    private GrnService grnService;
    @Autowired
    private StbService stbService;
    @Autowired
    private WarehRcvTaskSerivce warehRcvTaskSerivce;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private WarehRmodeService warehRmodeService;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    private StbDtlService stbDtlService;
    @Autowired
    private SysUnitService unitService;
    @Autowired
    private SysParameterService parameterService;
    @Autowired
    private FiledUtils filedUtils;
    @Autowired
    private GrnUtils grnUtils;


    /**
     * 查询入库单列表
     *
     * @param grnModel
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult grnTypeAll(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 GrnModel grnModel, HttpServletRequest request) {
        try {
            SysUser user = this.isNullUser(request);
            this.setMode(grnModel, user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, grnService.getGrnList(page, size, grnModel));
        } catch (ServiceException ex) {
            log.error("[TfnTypeService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_GRN, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnTypeController][grnTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GRN, "入库单查询异常", "");
        }
    }


    /**
     * 查询当前选中记录商品明细
     */
    @RequestMapping(value = "/getGrnGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getGrnGoods(@RequestBody Grn grn, HttpServletRequest request) {
        try {
            SysUser user = this.isNullUser(request);
            grn.setUnitId(user.getUnit().getUnitId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, RestulMap.getResut(grnService.getStbGoodsDtl(grn)));
        } catch (ServiceException ex) {
            log.error("[GrnService][getGrnByPk] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][getGrnByPk] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GRN, "入库单查询异常", "");
        }
    }

    /**
     * 查询当前选中入库单基本控制备注
     */
    @Deprecated
    @RequestMapping(value = "/getGrnDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getGrnDtl(@RequestBody Grn grn, HttpServletRequest request) {
        try {
            SysUser user = this.isNullUser(request);
            grn.setUnitId(user.getUnit().getUnitId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, grnService.getStbBaseDtl(grn));
        } catch (ServiceException ex) {
            log.error("[GrnService][getGrnByPk] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][getGrnByPk] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GRN, "入库单查询异常", "");
        }
    }


    /**
     * 新增入库单(创建入库单完善)
     *
     * @param model
     * @param re
     * @return
     */
    @SystemLog(name = "新增入库单")
    @RequestMapping(value = "/addGrn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addGrn(@RequestBody GrnDtlModel model, HttpServletRequest re) {
        try {
            BigDecimal qs = BigDecimal.valueOf(0.00);
            SysUser user = (SysUser) this.isNullUser(re);
            //model.getGrn().setUnitId(user.getOwnerId());
           /* if (model.getStbDtl().size() > 0) {
                for (StbDtl stbDtl : model.getStbDtl()) {
                    // BigDecimal 两个数字比较大小函数:compareTo 大于返回 1 等于为0 小于-1
                    if (stbDtl.getQty().compareTo(qs) <= 0) {
                        return new JsonResult(JsonResultCode.FAILURE_GRN_1, "商品明细数量不能小于0", "");
                    }
                }
            }*/
            //  stbUtil.setStbNum(model, user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, grnService.addGrnDtl(model, user));
        } catch (ServiceException ex) {
            log.error("[GrnService][createGrn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_GRN_1, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnService][createGrn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GRN_1, "入库单增加异常", "");
        }
    }

    /**
     * 更新入库单
     *
     * @param grnVo
     * @param re
     * @return
     */
    @SystemLog(name = "更新入库单")
    @RequestMapping(value = "/updateGrn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateGrn(@RequestBody GrnVo grnVo, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null) {
                grnVo.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", grnService.update(grnVo));
        } catch (ServiceException ex) {
            log.error("[GrnService][updateGrn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnService][updateGrn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GRN_2, "入库单修改异常", "");
        }
    }


    /**
     * 删除入库单
     * 1. 判断入库单是否对应入库任务 有改变入库任务状态
     * 2. 删除相关的 数据
     *
     * @param
     * @param re
     * @return
     */
    @SystemLog(name = "删除入库单")
    @RequestMapping(value = "/deleteGrn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult delete(@RequestBody @Valid Grn grn, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            PrivIdExamine examine = privUtils.isUnitPriv("deleteGrn", "删除入库单", grn.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            //删除单据状态要求：progress IN (C,PG) && suspended = F && cancelled = F &&总库存单编号 ldgStbNum == NULL。
            Grn deleteGrn = grnService.getGrnId(grn);
            if (grnService.isUpdateGrn(deleteGrn)) {
                Stb stb = new Stb();
                stb.setUnitId(grn.getUnitId());
                stb.setStbNum(grn.getGrnNum());
                stb = stbService.getStbByPk(stb);
                WarehRcvTask warehRcvTask = new WarehRcvTask();
                warehRcvTask.setTaskDocNum(stb.getSrcDocNum());
                warehRcvTask.setUnitId(grn.getUnitId());
                warehRcvTask.setRcvMode(grn.getRcvMode());
                //查询是否存在入库任务
                WarehRcvTask task = warehRcvTaskSerivce.selectWarehRcvTask(warehRcvTask);
                if (task != null) {
                    task.setIsStb(CommonFainl.FALSE);
                    //判断并推送取消
                    wmsErpNumService.deleteCwmsGrn(task, "取消入库", user);
                    //还原入库任务
                    warehRcvTaskSerivce.updateWarehRcvTask(task);
                }
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, grnService.delete(grn));
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "入库单不能删除", "");
            }
        } catch (ServiceException ex) {
            log.error("[GrnService][updateGrn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_GRN_3, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnService][updateGrn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_GRN_3, "删除入库单异常", "");
        }
    }

    /**
     * 根据所选收货仓，加载入库方式option
     */
    @RequestMapping(value = "/getWarehOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getGrnModeOption(@RequestBody Wareh wareh, HttpServletRequest re) {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            /**
             * 获取送货方式option
             */
            List<PurKeyValue> rcvMode = new ArrayList<>();
            WarehRmode wr = new WarehRmode();
            wr.setWarehId(wareh.getWarehId());
            List<WarehRmode> wrList = warehRmodeService.selectWarehRomde(wr);
            for (PurKeyValue v : sysCodeDtlService.optionGet("RCV_MODE")) {
                for (WarehRmode _wr : wrList) {
                    if (_wr.getRcvMode().equals(v.getOptionValue())) {
                        rcvMode.add(v);
                    }
                }
            }
            map.put("rcvMode", rcvMode);
            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (ServiceException ex) {
            log.error("[GrnService][createGrn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnService][createGrn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }


    /**
     * 查询入库是否可以修改删除
     */
    @RequestMapping(value = "/getGrnType", method = {RequestMethod.POST})
    public JsonResult getGrnType(@RequestBody Grn model) {
        try {
            Grn type = grnService.getGrnId(model);
            Map<String, Integer> map = new HashMap<>();
            map.put("UPDATE", 1);
            map.put("DELETE", 1);
            if (type.getProgress().equalsIgnoreCase("C")) {
                map.put("UPDATE", 0);
                map.put("DELETE", 0);
            }
            if (type.getProgress().equalsIgnoreCase("PG") || type.getProgress().equalsIgnoreCase("RD")) {
                map.put("UPDATE", 0);
                map.put("DELETE", 1);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[GrnService][getGrnId] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][getGrnType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询商品能否审核异常:ProductClsController.getAuditType()", "");
        }
    }

    /**
     * 查询入库单可修改字段
     */
    @RequestMapping(value = "/updateGrnFiled", method = {RequestMethod.POST})
    public JsonResult updateGrnFiled(@RequestBody GrnModel model) {
        try {

            List<String> list = new ArrayList<>();
            if (model.getBaseInst() != null && model.getBaseInst() == 1) {
                String str = "bxi_enabled,box_reqd,";
                WarehRmode warehRmode = warehRmodeService.selectWarehRmode(model.getWarehId(), model.getRcvMode());
                if (warehRmode != null) {
                    String s = "T".equalsIgnoreCase(warehRmode.getFixedUnitPrice()) == true ? "unit_price," : "";
                    String s2 = "T".equalsIgnoreCase(warehRmode.getMnlRwd()) == true ? "rwd," : "";
                    str += s + s2;
                }
                String[] spis;
                SysParameter parameter = new SysParameter("updateGrnFile", "入库单可修改字段", str);
                SysParameter s = parameterService.findByParameter(parameter.getParmId());
                if (s == null) {
                    parameterService.insertParameter(parameter);
                    spis = str.split(",");
                } else {
                    if (!str.equalsIgnoreCase(s.getParmVal())) {
                        parameter.setParmVal(str);
                        parameterService.updateParameter(parameter);
                        s.setParmVal(str);
                    }
                    if (s.getParmVal().indexOf(",") < 1) {
                        return new JsonResult(JsonResultCode.FAILURE, "入库单可修改字段参数值设置有误，请以:表字段名+,+分割", 0);
                    }
                    spis = s.getParmVal().split(",");
                }
                Arrays.stream(spis).forEach(st -> list.add(filedUtils.getConvert(st)));
            }
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", list);
        } catch (ServiceException ex) {
            log.error("[GrnService][initButtons] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][updateGrnFiled] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询入库单可编辑字段异常", "");
        }
    }

    /**
     * 查询入库单按钮状态
     */
    @RequestMapping(value = "/grnAuditType", method = {RequestMethod.POST})
    public JsonResult grnAuditType(@RequestBody Grn model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, grnService.initButtons(model));
        } catch (ServiceException ex) {
            log.error("[GrnService][initButtons] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][grnAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询商品能否审核异常:ProductClsController.getAuditType()", "");
        }
    }


    /**
     * 入库单确认
     */
    @RequestMapping(value = "/confirm", method = {RequestMethod.POST})
    public JsonResult grnConfirm(@RequestBody Grn model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            //确认单据。状态要求：PROGRESS = PENDING && SUSPENDED = FALSE && CANCELLED = FALSE && LDG_STB_NUM IS NULL。新状态：PROGRESS = CONFIRMED。
            if (!grnService.isUpdateGrn(model)) {
                return new JsonResult(JsonResultCode.FAILURE, "单据状态不能确认", 0);
            }
            model.setProgress("C");
            return new JsonResult(JsonResultCode.SUCCESS, "确认成功", grnService.changeStatuss(model));
        } catch (ServiceException ex) {
            log.error("[GrnService][initButtons] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][grnConfirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询商品能否审核异常:ProductClsController.getAuditType()", "");
        }
    }

    /**
     * 入库单挂起
     */
    @RequestMapping(value = "/suspend", method = {RequestMethod.POST})
    public JsonResult grnSuspend(@RequestBody Grn model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            Grn grn = grnService.selectGrn(new Grn(model.getUnitId(), model.getGrnNum()));
            Stb stb = stbService.getStbByPk(new Stb(model.getUnitId(), model.getGrnNum()));
            if (grn == null || stb == null) {
                return new JsonResult(JsonResultCode.FAILURE, "单据状态不能挂起", 0);
            }
            //((ACPT_REQD = FALSE && PA_REQD = FALSE) && && PROGRESS <> RECEIVED) ||
            // ((ACPT_REQD = TRUE || PA_REQD = TRUE) && PROGRESS <> STORED))
            // && SUSPENDED = FALSE && CANCELLED = FALSE
            if ((grn.getAcptReqd().equalsIgnoreCase("F") &&
                    grn.getPaReqd().equalsIgnoreCase("F") &&
                    "F".equalsIgnoreCase(stb.getReversed())) &&
                    "F".equalsIgnoreCase(stb.getSuspended()) &&
                    "F".equalsIgnoreCase(stb.getCancelled())) {
                stb.setSuspended("T");
                return new JsonResult(JsonResultCode.SUCCESS, "挂起成功", stbService.update(stb));
            }
            return new JsonResult(JsonResultCode.FAILURE, "单据状态不能挂起", 0);
        } catch (ServiceException ex) {
            log.error("[GrnService][update] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][grnSuspend] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "挂起入库单异常", "");
        }
    }

    /**
     * 入库单恢复
     */
    @RequestMapping(value = "/resume", method = {RequestMethod.POST})
    public JsonResult grnResume(@RequestBody Grn model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            Grn grn = grnService.selectGrn(new Grn(model.getUnitId(), model.getGrnNum()));
            Stb stb = stbService.getStbByPk(new Stb(model.getUnitId(), model.getGrnNum()));
            if (grn == null || stb == null) {
                return new JsonResult(JsonResultCode.FAILURE, "单据状态不能恢复", 0);
            }
            if ("T".equalsIgnoreCase(stb.getSuspended())) {
                stb.setSuspended("F");
                return new JsonResult(JsonResultCode.SUCCESS, "恢复成功", stbService.update(stb));
            }
            return new JsonResult(JsonResultCode.FAILURE, "单据状态不能恢复", 0);
        } catch (ServiceException ex) {
            log.error("[GrnService][update] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][grnResume] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "恢复入库单异常", "");
        }
    }

    /**
     * 入库单重做
     * 重做单据。状态要求：PROGRESS = CONFIRMED && SUSPENDED = FALSE && CANCELLED = FALSE && LDG_STB_NUM IS NULL。新状态：PROGRESS = PENDING。
     * 1）如果LGC_WAREH_ID为UNKNOWN_ID，则清空为NULL。
     * 2）如果TTL_RWD IS NOT NULL && TTL_RWD <> 0，则调用deallocateDetailReward，取消明细分配。
     * 3）如果STB_DOC非空，则：
     * 循环STB_DOC，调用srcDocStopReceive。
     * 删除STB_DTL、STB_UID，并将单头TTL_QTY、TTL_VAL、TTL_TAX清零，TTL_MKV、TTL_VAL清空。
     */
    @RequestMapping(value = "/redo", method = {RequestMethod.POST})
    public JsonResult grnRedo(@RequestBody Grn model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            Grn grn = grnService.selectGrn(new Grn(model.getUnitId(), model.getGrnNum()));
            Stb stb = stbService.getStbByPk(new Stb(model.getUnitId(), model.getGrnNum()));
            if (grn == null || stb == null) {
                return new JsonResult(JsonResultCode.FAILURE, "单据状态不能恢重做", 0);
            }
            if ("C".equalsIgnoreCase(grn.getProgress()) && "F".equalsIgnoreCase(stb.getSuspended()) && stb.getLdgStbNum() == null) {
                grn.setProgress("PG");
                return new JsonResult(JsonResultCode.SUCCESS, "重做成功", grnService.changeStatuss(grn));
            }
            return new JsonResult(JsonResultCode.FAILURE, "单据状态不能重做", 0);
        } catch (ServiceException ex) {
            log.error("[GrnService][update] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][grnRedo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "重做入库单异常", "");
        }
    }

    /**
     * 作废
     * 状态要求：PROGRESS IN (PENDING, CONFIRMED) && SUSPENDED = FALSE && CANCELLED = FALSE && LDG_STB_NUM IS NULL。新状态：CANCELLED = TRUE。
     * 1）如果是总单，获取子单列表，循环处理：
     * 清除子单STB.LDG_STB_NUM字段值。
     * 调用子单GoodsReceiptNoteHome.abolish方法。
     * 恢复子单STB.LDG_STB_NUM字段值。
     */
    @RequestMapping(value = "/abolish", method = {RequestMethod.POST})
    public JsonResult grnAbolish(@RequestBody Grn model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            Grn grn = grnService.selectGrn(new Grn(model.getUnitId(), model.getGrnNum()));
            Stb stb = stbService.getStbByPk(new Stb(model.getUnitId(), model.getGrnNum()));
            if (grn == null || stb == null) {
                return new JsonResult(JsonResultCode.FAILURE, "单据状态不能作废", 0);
            }
            if (("C".equalsIgnoreCase(grn.getProgress()) ||
                    "PG".equalsIgnoreCase(grn.getProgress()))
                    && "F".equalsIgnoreCase(stb.getSuspended())
                    && "F".equalsIgnoreCase(stb.getCancelled())
                    && stb.getLdgStbNum() == null) {
                stb.setCancelled("T");
                if (model.getRcvMode().equalsIgnoreCase("RTRT") || model.getRcvMode().equalsIgnoreCase("SLRT")
                        || model.getRcvMode().equalsIgnoreCase("PURC") || model.getRcvMode().equalsIgnoreCase("DIPU")) {
                    //todo 总单据恢复子单的 LDG_STB_NUM 值

                    //推送取消收货
                    purchaseService.publicMethon("reverseReceive", stb.getSrcDocType(), stb.getSrcDocUnitId(), stb.getSrcDocNum(), stb.getUnitId(), stb.getStbNum(), user);
                }
                return new JsonResult(JsonResultCode.SUCCESS, "作废成功", stbService.update(stb));
            }
            return new JsonResult(JsonResultCode.FAILURE, "单据状态不能作废", 0);
        } catch (ServiceException ex) {
            log.error("[GrnService][update] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][grnAbolish] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "作废入库单异常", "");
        }
    }

    //remove：删除单据。状态要求：PROGRESS IN (PENDING, CONFIRMED) && SUSPENDED = FALSE && CANCELLED = FALSE && LDG_STB_NUM IS NULL。
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    public JsonResult grnRemove(@RequestBody Grn model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            Grn grn = grnService.selectGrn(new Grn(model.getUnitId(), model.getGrnNum()));
            Stb stb = stbService.getStbByPk(new Stb(model.getUnitId(), model.getGrnNum()));
            if (grn == null || stb == null) {
                return new JsonResult(JsonResultCode.FAILURE, "单据状态不能删除", 0);
            }
            if (("C".equalsIgnoreCase(grn.getProgress()) || "PG".equalsIgnoreCase(grn.getProgress()))
                    && "F".equalsIgnoreCase(stb.getSuspended()) && "F".equalsIgnoreCase(stb.getCancelled())) {
                stb.setCancelled("T");
                //推送取消收货 删除关联所有单据同时判断是否推送C-WMS取消
                if (model.getRcvMode().equalsIgnoreCase("RTRT") || model.getRcvMode().equalsIgnoreCase("SLRT")) {
                    //删除关联单据
                    grnService.delete(grn);
                }
                return new JsonResult(JsonResultCode.SUCCESS, "删除成功", stbService.update(stb));
            }
            return new JsonResult(JsonResultCode.FAILURE, "单据状态不能删除", 0);
        } catch (ServiceException ex) {
            log.error("[GrnService][update] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][grnRemove] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除入库单异常", "");
        }
    }

    /**
     * 功能描述: 入库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 17:53
     */
    @RequestMapping(value = "/receive", method = {RequestMethod.POST})
    public JsonResult grnReceive(@RequestBody Grn model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            //单据入库  ：
            // (PROGRESS = TAKEN_OVER) || (PROGRESS = CONFIRMED && ACPT_REQD = FALSE && (BOX_REQD = FALSE || BOX_SCHD = TRUE)) && SUSPENDED = FALSE && CANCELLED = FALSE
            Stb stb = stbService.getStbByPk(new Stb(model.getUnitId(), model.getGrnNum()));
            Grn grn = grnService.selectGrn(model);
            if (stb == null || grn == null) {
                return new JsonResult(JsonResultCode.SUCCESS, "入库失败入库单信息为空", 0);
            }
            if (CommonFainl.FALSE.equalsIgnoreCase(stb.getSuspended()) &&
                    "C".equalsIgnoreCase((grn.getProgress())) || "PG".equalsIgnoreCase(grn.getProgress())) {
                grnService.warehouSing(model.getUnitId(), model.getGrnNum(), user);
                model.setProgress("RD");
                return new JsonResult(JsonResultCode.SUCCESS, "入库成功", grnService.changeStatuss(model));
            } else {
                return new JsonResult(JsonResultCode.SUCCESS, "单据状态不能入库", 0);
            }

        } catch (ServiceException ex) {
            log.error("[GrnService][initButtons] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][grnConfirm] exception", ex);

            return new JsonResult(JsonResultCode.FAILURE, "入库异常:GrnController.grnReceive()", "");
        }
    }


    /**
     * 功能描述: 取消入库
     * 1.RCV_MODE NOT IN (RTRT, ADJS , ASMB, DSAM)
     * 2.原单已生效则以对应 负数 stbDtl 在进行一次入库操作
     * 3.取消入库前判断成本 即取消入库成本和当前成本是否一致不一致 不能取消入库避免造成成本账不平
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 17:53
     */
    @RequestMapping(value = "/reverseReceive", method = {RequestMethod.POST})
    public JsonResult grnReverseReceive(@RequestBody Grn model, HttpServletRequest request) {
        try {

            SysUser user = this.getSessionSysUser(request);
            //单据入库  ：rvc_mode RETAIL_RETURN, ADJUST, ASSEMBLE, DISASSEMBLE
            //状态要求：PROGRESS = RECEIVED && SUSPENDED = FALSE && CANCELLED = FALSE && LDG_STB_NUM IS NULL && RCV_MODE NOT IN (RTRT, ADJS , ASMB, DSAM)。
            // 新状态：PROGRESS = (IF (ACPT_REQD = TRUE || (BOX_REQD = TRUE && BOX_SCHD = FALSE)) THEN TAKEN_OVER ELSE CONFIRMED)，EFFECTIVE = FALSE。
            //取消入库后 状态改为 C
            Stb stb = stbService.getStbByPk(new Stb(model.getUnitId(), model.getGrnNum()));
            model = grnService.selectGrn(model);
            if (model.getRcvMode().equalsIgnoreCase("RTRT") || model.getRcvMode().equalsIgnoreCase("SLRT")) {
                return new JsonResult(JsonResultCode.FAILURE, "成本已改变不能取消入库", 0);
            }
            if (stb == null || model == null) {
                return new JsonResult(JsonResultCode.FAILURE, "取消入库失败入库单信息为空", 0);
            }
            if (isReverseReceive(model, stb) && stb.getEffective().equalsIgnoreCase(CommonFainl.TRUE)) {
                //取消入库 单据已生效
                //核算成本、改变库存、恢复批次信息
                grnService.warehouReverse(model, user);
                model.setProgress("C");
                stb.setEffective("F");
                stbService.update(stb);
                grnService.changeStatuss(model);
                return new JsonResult(JsonResultCode.SUCCESS, "取消入库成功", 0);
            } else {
                return new JsonResult(JsonResultCode.SUCCESS, "单据状态不能取消入库", 0);
            }
        } catch (
                ServiceException ex) {
            log.error("[GrnService][initButtons] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (
                Exception ex) {
            log.error("[GrnController][grnReverseReceive] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "取消出库异常:ProductClsController.grnReverseReceive()", "");
        }

    }

    /**
     * 功能描述: 入库单弹窗
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/25 16:19
     */
    @GetMapping(value = "/getWindows")
    public JsonResult warehRcvTasWindows(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                         @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                         UnitOptionVo vo, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (StringUtils.isNotEmpty(vo.getsUnitId() + "")) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, unitService.selectByOption(vo, page, size));
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, new PageInfo<>());
        } catch (ServiceException ex) {
            log.error("[SysunitService]selectByOption] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_OPTION_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseController][warehRcvTasWindows] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_OPTION_R, CommonFainl.SELECTFIANL, "");
        }
    }

    //取消入库时状态要求
    public Boolean isReverseReceive(Grn model, Stb stb) {
        //PROGRESS = RECEIVED && SUSPENDED = FALSE && CANCELLED = FALSE && LDG_STB_NUM IS NULL &&
        String[] rvcs = {"RTRT", "ADJS", "ASMB", "DSAM"};
        if (model.getProgress().equalsIgnoreCase("RD") && stb.getSuspended().equalsIgnoreCase(CommonFainl.FALSE)) {
            for (String s : rvcs) {
                if (model.getRcvMode().equalsIgnoreCase(s)) {
                    return false;
                }
            }
            if ("F".equalsIgnoreCase(stb.getSuspended()) && "F".equalsIgnoreCase(stb.getCancelled())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 入库新增库存单明细
     *
     * @param model
     * @param re
     * @return
     */
    @SystemLog(name = "入库新增库存单明细")
    @RequestMapping(value = "/createStbDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult createStbDtl(@RequestBody GrnStbDtlModel model, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            if (StringUtils.NullEmpty(model.getQty() + "")) {
                return new JsonResult(JsonResultCode.FAILURE, "请添加数量", "");
            }
            PrivIdExamine examine = privUtils.isUnitPriv("addStbDtl", "增加入库单明细", model.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            Grn grn = grnService.selectGrn(new Grn(model.getUnitId(), model.getGrnNum()));
            Stb stb = stbService.getStbByPk(new Stb(model.getUnitId(), model.getGrnNum()));
            if (grnService.isUpdateGrn(grn)) {
                if (model.getRcvMode().equalsIgnoreCase("PURC") || model.getRcvMode().equalsIgnoreCase("RTRT") ||
                        model.getRcvMode().equalsIgnoreCase("TAR") || model.getRcvMode().equalsIgnoreCase("SLRT")) {
                    model.setStbNum(stb.getStbNum());
                    stbDtlService.insert(model);
                }
                return new JsonResult(JsonResultCode.SUCCESS, "入库单增加明细成功", 0);
            }
            return new JsonResult(JsonResultCode.FAILURE, "单据状态不能修被改明细", 0);
        } catch (ServiceException ex) {
            log.error("[StbDtlService][createStdDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[StbDtlService][createStdDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 修改库存单明细
     *
     * @param stbDtl
     * @param re
     * @return
     */
    @SystemLog(name = "修改库存单明细")
    @RequestMapping(value = "/updateStbDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateStbDtl(@RequestBody GrnStbDtlModel stbDtl, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            PrivIdExamine examine = privUtils.isUnitPriv("updateStbDtl", "修改库存单明细", stbDtl.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            Grn grn = new Grn(stbDtl.getUnitId(), stbDtl.getStbNum());
            Stb stb = stbService.getStbByPk(new Stb(stbDtl.getUnitId(), stbDtl.getGrnNum()));
            if (grnService.isUpdateGrn(grn)) {
                if (stbDtl.getRcvMode().equalsIgnoreCase("PURC") || stbDtl.getRcvMode().equalsIgnoreCase("RTRT") ||
                        stbDtl.getRcvMode().equalsIgnoreCase("TAR") || stbDtl.getRcvMode().equalsIgnoreCase("SLRT")) {
                    //isStbDtl(grn, stb, stbDtl, "update");
                    stbDtl.setStbNum(stb.getStbNum());
                    stbDtlService.update(stbDtl);
                }
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", stbDtlService.update(stbDtl));
            }
            return new JsonResult(JsonResultCode.FAILURE, "库存单不能修改明细", 0);
        } catch (ServiceException ex) {
            log.error("[StbDtlService][updateStbDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[StbDtlService][updateStbDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 修改库存单明细
     * 修改增加商品判断
     *
     * @param stbDtl
     * @param re
     * @return
     */
    @RequestMapping(value = "/videfUpdateStbDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult videfUpdateStbDtl(@RequestBody GrnStbDtlModel stbDtl, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            grnUtils.isStbDtlTows(stbDtl);
            return new JsonResult(JsonResultCode.SUCCESS, "验证成功", stbDtl);
        } catch (ServiceException ex) {
            log.error("[StbDtlService][updateStbDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[StbDtlService][videfUpdateStbDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "验证失败", "");
        }
    }

    /**
     * 入库单所需下拉框option
     *
     * @return
     */
    @RequestMapping(value = "/getCreateOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getCreateOption() {
        try {
            List<String> lists = prodClsUtils.getListString();
            /**
             * 获取约定会计日期option
             */
            lists.add("fsclDateAptd");
            /**
             * 获取即时结算option
             */
            lists.add("instStl");
            /**
             * 获取启用配码option
             */
            lists.add("bixEnabled");
            /**
             * 获取启用装箱option
             */
            lists.add("boxReqd");

            /**
             * 获取启用验收option
             */
            lists.add("acptReqd");
            /**
             * 获取启用分储option
             */
            lists.add("paReqd");

            /**
             * 获取预定装箱option
             */
            lists.add("boxSchd");
            Map<String, Object> map = prodClsUtils.getMapOffOn(lists);

            /**
             * 获取基准radio
             */
            List<OptionVo> baseDate = new ArrayList<>();
            baseDate.add(new OptionVo("业务", "1"));
            baseDate.add(new OptionVo("会计", "2"));
            /**
             * 获取调拨单进度option
             */
            map.put("progress", sysCodeDtlService.optionGet("GRN_PROG"));
            map.put("baseDate", baseDate);
            //入库方式
            map.put("rcvMode", sysCodeDtlService.optionGet("RCV_MODE"));
            //桥接方式
            map.put("brdgMode", sysCodeDtlService.optionGet("BRDG_MODE"));
            map.put("srcDocType", sysCodeDtlService.optionGet("DOC_TYPE"));
            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (Exception ex) {
            log.error("[UserController][getUserOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "获取信息失败", "");
        }
    }

    @GetMapping(value = "/money")
    public JsonResult grnMoney(GrnModel grnModel, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            this.setMode(grnModel, user);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, grnService.selectMoney(grnModel));
        } catch (ServiceException ex) {
            log.error("[GrnService][selectMoney] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[GrnController][grnMoney] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, "入库单金额查询失败", "");
        }
    }


    public void setMode(GrnModel grnModel, SysUser user) {
        if (grnModel.getBaseInst() == 1) {
            grnModel.setFsclDateFrom(null);
            grnModel.setFsclDateTo(null);
        } else {
            grnModel.setFsclDateFrom(grnModel.getDocDateFrom());
            grnModel.setFsclDateTo(grnModel.getDocDateTo());
            grnModel.setDocDateFrom(null);
            grnModel.setDocDateTo(null);
        }
        if (grnModel.getUnitId() == null) {
            grnModel.setUnitId(user.getOwnerId());
        }
    }


}
