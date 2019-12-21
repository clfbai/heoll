package com.boyu.erp.platform.usercenter.controller.purchase;

import com.alibaba.fastjson.JSONObject;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.purchase.Psx;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.Pua;
import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PuaService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PuaTypeService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlaTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 采购申请单
 * @author: wz
 * @create: 2019-7-15 19:32
 */
@Slf4j
@RestController
@RequestMapping("/purchase/pua")
public class PurchasePuaController extends BaseController {
    private static final String puaCon = "采购申请";
    //判断权限操作字段
    private final String tableName = "pua|";
    private static final String Operations = "PurchasePuaController|";
    @Autowired
    private PuaService puaService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private PuaTypeService puaTypeService;
    @Autowired
    private FieldUtils fieldUtils;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SlaTypeService slaTypeService;
    @Autowired
    private OperationAuthorityUtils privUtils;

    /**
     * 采购申请查询(采购合同用到)
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult puaList(PsxVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            PageInfo<PsxVo> resultList = puaService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][puaList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][puaList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_R, puaCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 采购申请添加
     *
     * @return
     */
    @RequestMapping(value = "/addPua", method = {RequestMethod.POST})
    public JsonResult addPua(HttpServletRequest re, @RequestBody PsxVo vo) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.ADD, vo.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo.setVendeeId(vo.getsUnitId());
            vo.setUnitId(vo.getsUnitId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, puaService.insertPua(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][addPua] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][addPua] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_C, puaCon + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 采购申请修改
     *
     * @return
     */
    @RequestMapping(value = "/updatePua", method = {RequestMethod.POST})
    public JsonResult updatePua(HttpServletRequest re, @RequestBody PsxVo vo) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            //判断是否有权限操作
//            PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.UPDATE, vo.getUnitId(), user);
//            if (!examine.getPrivBoolean()) {
//                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
//            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, puaService.updatePua(vo, user));


        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][updatePua] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][updatePua] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_U, puaCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 采购申请删除
     *
     * @return
     */
    @RequestMapping(value = "/deletePua", method = {RequestMethod.POST})
    public JsonResult deletePua(HttpServletRequest re, @RequestBody PsxVo vo) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.DELETE, vo.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, puaService.deletePua(vo));


        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][deletePua] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][deletePua] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUA_D, puaCon + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 判断是否可以删除/修改
     * 采购申请/销售申请
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "判断是否可以删除/修改")
    @RequestMapping(value = "/isUpdateOrDelete", method = {RequestMethod.POST})
    public JsonResult isUpdateOrDelete(@RequestBody PsxVo vo) {
        try {

            Map<String, Object> map = new HashMap<>();
            int update = 0;
            int delete = 0;
            //状态要求：PROGRESS IN (PENDING, CONFIRMED) && SUSPENDED = FALSE && CANCELLED = FALSE
            if ((vo.getProgress().equals("PG") || vo.getProgress().equals("C")) &&
                    vo.getSuspended().equals("F") &&
                    vo.getCancelled().equals("F")) {
                delete = 1;
            }
            //要求状态 PROGRESS = PENDING && SUSPENDED = FALSE && CANCELLED = FALSE 才能修改 否则不行
            if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                update = 1;
            }
            map.put("update", update);
            map.put("delete", delete);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][isUpdateOrDelete] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][isUpdateOrDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, puaCon + CommonFainl.SELECTFIANL, "");
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

            map.put("puaType", puaTypeService.optionGet());//采购申请类别下拉
            map.put("slaType", slaTypeService.optionGet());//销售申请类别下拉
            map.put("slaAutoGen", prodClsUtils.getList());//销售申请自动生成
            map.put("slaGen", prodClsUtils.getList());//销售申请已生成
            map.put("slaAutoChk", prodClsUtils.getList());//销售申请自动审核
            map.put("rqdCtrl", sysCodeDtlService.optionGet("RQD_CTRL"));//货期控制
            map.put("vdrInvd", prodClsUtils.getList());//供应商介入
            map.put("vdeInvd", prodClsUtils.getList());//供应商介入
            map.put("bxiEnabled", prodClsUtils.getList());//启用配码
            map.put("effective", prodClsUtils.getList());//已生效
            map.put("suspended", prodClsUtils.getList());//挂起
            map.put("cancelled", prodClsUtils.getList());//撤销
            map.put("progress", sysCodeDtlService.optionGet("PSX_PROG"));//进度

            map.put("ordType", sysCodeDtlService.optionGet("ORD_TYPE"));//订单类别
            map.put("puaAutoGen", prodClsUtils.getList());//采购申请自动生成
            map.put("puaAutoChk", prodClsUtils.getList());//采购申请自动审核
            map.put("puaGen", prodClsUtils.getList());//采购申请已生成

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][option] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 通过选择的采购申请类别获取购销申请类别的详细信息
     */
    @RequestMapping(value = "/psxTypeList", method = {RequestMethod.POST})
    public JsonResult psxTypeList(@RequestBody PuaType vo) {
        try {
            PsxType type = puaTypeService.selectByPsxType(vo);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, type);
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][psxTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_TYPE_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][psxTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_TYPE_R, puaCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 如果修改了采购申请类别 前台弹窗是否清空明细数据  点确定时走的接口
     * 通过单号查询出所有数据，然后调用明细的删除
     * <p>
     * 采购申请/销售申请公用
     */
    @RequestMapping(value = "/wipeData", method = {RequestMethod.POST})
    public JsonResult wipeData(@RequestBody Psx psx) {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", puaService.deletePsxDtl(psx.getPsxNum()));

        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][wipeData] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_DTL_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][wipeData] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_DTL_D, puaCon + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 采购申请弹窗
     * 供应商编号：公用采购合同中供应商弹窗
     * 收货仓库编号：当前组织id +  WH
     * 部门编号： 当前组织id  +  DP
     * 发货仓库编号：供应商id + WH
     * 始发方编号：供应商id  +  VD
     * 始发方仓库编号：始发方id  +  WH
     * 转送方编号：当前组织id  +  VE
     * 转送方仓库编号：转送方id  +  WH
     */

    /**
     * 查询采购申请必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getPuaFile", method = {RequestMethod.POST})
    public JsonResult getPuaFile(@RequestBody PuaType type) {
        try {
            if (StringUtils.isNotEmpty(type.getPuaType())) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("PUA,PSX", type.getPuaType()));
            } else {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("PUA,PSX", "PUR_PUA"));
            }
        } catch (Exception ex) {
            log.error("[PurchasePuaController][getPuaFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 不能直接修改字段
     *
     * @return
     */
    @RequestMapping(value = "/updateNotFile", method = {RequestMethod.POST})
    public JsonResult updateNotFile(@RequestBody PuaType type) {
        try {

            List<TableFile> at = new ArrayList<>();
            List<String> list = new ArrayList<>();
            if (StringUtils.isNotEmpty(type.getPuaType())) {
                list = fieldUtils.isNotUpdateWareh(type.getPuaType());
            } else {
                list = fieldUtils.isNotUpdateWareh("PUR_PUA");
            }
            if(!list.isEmpty()){
                for (String li : list) {
                    TableFile t = new TableFile();
                    t.setTableFlie(li);
                    at.add(t);
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, at);
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][updateNotFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 采购申请中确认单据
     *
     * @return
     */
    @RequestMapping(value = "/confirm", method = {RequestMethod.POST})
    public JsonResult confirm(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
           /* PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.CONFIRM, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, puaService.confirm(puaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][confirm] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][confirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, puaCon + CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 采购申请中重做单据
     *
     * @return
     */
    @RequestMapping(value = "/redo", method = {RequestMethod.POST})
    public JsonResult redo(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
          /*  PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REDO, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, puaService.redo(puaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, puaCon + CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 采购申请中审核单据
     *
     * @return
     */
    @RequestMapping(value = "/examine", method = {RequestMethod.POST})
    public JsonResult examine(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.EXAMINE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, puaService.examine(puaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][examine] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][examine] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, puaCon + CommonFainl.BILLEXAMINEFIANL, "");
        }
    }


    /**
     * 采购申请中重审单据
     *
     * @return
     */
    @RequestMapping(value = "/reiterate", method = {RequestMethod.POST})
    public JsonResult reiterate(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REITERATE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, puaService.reiterate(puaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, puaCon + CommonFainl.BILLREITERATEFIANL, "");
        }
    }

    /**
     * 采购申请中挂起单据
     *
     * @return
     */
    @RequestMapping(value = "/hangUp", method = {RequestMethod.POST})
    public JsonResult hangUp(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.HANGUP, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, puaService.hangUp(puaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, puaCon + CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 采购申请中恢复单据
     *
     * @return
     */
    @RequestMapping(value = "/recovery", method = {RequestMethod.POST})
    public JsonResult recovery(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.RECOVERY, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, puaService.recovery(puaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, puaCon + CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 采购申请中作废单据
     *
     * @return
     */
    @RequestMapping(value = "/toVoid", method = {RequestMethod.POST})
    public JsonResult toVoid(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.TOVOID, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, puaService.toVoid(puaService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, puaCon + CommonFainl.BILLTOVOIDFIANL, "");
        }
    }

    /**
     * 采购申请中关闭单据
     *
     * @return
     */
    @RequestMapping(value = "/close", method = {RequestMethod.POST})
    public JsonResult close(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
           /* PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.CLOSE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = puaService.selectByOnly(vo);
            if (vo == null) {
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCLOSE, puaService.close(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][close] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][close] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, puaCon + CommonFainl.BILLCLOSEFIANL, "");
        }
    }

    /**
     * 采购申请中重用单据
     *
     * @return
     */
    @RequestMapping(value = "/reusing", method = {RequestMethod.POST})
    public JsonResult reusing(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REUSING, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = puaService.selectByOnly(vo);
            if (vo == null) {
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREUSING, puaService.reusing(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][reusing] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][reusing] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, puaCon + CommonFainl.BILLREUSINGFIANL, "");
        }
    }

    /**
     * 采购申请可操作状态
     *
     * @return
     */
    @RequestMapping(value = "/puaAuditType", method = {RequestMethod.POST})
    public JsonResult puaAuditType(@RequestBody PsxVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, puaService.initButtons(vo));
        } catch (ServiceException ex) {
            log.error("[PurchasePuaController][puaAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaController][puaAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, puaCon + CommonFainl.SELECTFIANL, "");
        }
    }
}




