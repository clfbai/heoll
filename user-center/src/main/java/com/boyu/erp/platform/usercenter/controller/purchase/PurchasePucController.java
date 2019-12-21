package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.*;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscAutoVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 采购合同
 * @author: wz
 * @create: 2019-7-4 19:45
 */
@Slf4j
@RestController
@RequestMapping("/purchase/puc")
public class PurchasePucController extends BaseController {
    private static final String pucCon = "采购合同";
    //判断权限操作字段
    private final String tableName = "puc|";
    private static final String Operations = "PurchasePucController|";
    @Autowired
    private PucService pucService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private PucTypeService pucTypeService;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private SlcTypeService slcTypeService;
    @Autowired
    private PscDtlService pscDtlService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private FieldUtils fieldUtils;
    @Autowired
    private OperationAuthorityUtils privUtils;

    /**
     * 采购合同查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult pucList(PscVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            PageInfo<PscVo> resultList = pucService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][pucList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][pucList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, pucCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加采购合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加采购合同")
    @RequestMapping(value = "/addPuc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPuc(HttpServletRequest re, @RequestBody PscVo vo) {
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
            vo.setVendeeId(vo.getsUnitId()+"");
            vo.setUnitId(vo.getsUnitId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, pucService.insertPuc(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchasePucController][addPuc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][addPuc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_C, pucCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改采购合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改采购合同")
    @RequestMapping(value = "/updatePuc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePuc(HttpServletRequest re, @RequestBody PscVo vo) {
        try {

            SysUser user = (SysUser) this.isNullUser(re);
            if (user == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.UPDATE, vo.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, pucService.updatePuc(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchasePucController][updatePuc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][updatePuc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_U, pucCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除采购合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除采购合同")
    @RequestMapping(value = "/deletePuc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePuc(HttpServletRequest re, @RequestBody PscVo vo) {
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

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, pucService.deletePuc(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchasePucController][deletePuc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][deletePuc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_D, pucCon+CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 采购合同/销售合同公用
     * 判断是否可以删除/修改
     * 采购/销售公用
     * @param vo
     * @return
     */
    @SystemLog(name = "判断是否可以删除/修改")
    @RequestMapping(value = "/isUpdateOrDelete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult isUpdateOrDelete(@RequestBody PscVo vo) {
        try {

            Map<String, Object> map = new HashMap<>();
            int update = 0;
            int delete = 0;
            //状态要求：PROGRESS IN (PENDING, CONFIRMED) && SUSPENDED = FALSE && CANCELLED = FALSE &&
            // ITMD_PSC_NUM IS NULL && EXEC_PSC_NUM IS NULL 才能删除
            if ((vo.getProgress().equals("PG") || vo.getProgress().equals("C")) &&
                    vo.getSuspended().equals("F") &&
                    vo.getCancelled().equals("F")
                    &&  StringUtils.NullEmpty(vo.getItmdPscNum())&&
                    StringUtils.NullEmpty(vo.getExecPscNum()) ) {
                delete = 1;
            }
            //要求状态 PROGRESS = PENDING && SUSPENDED = FALSE && CANCELLED = FALSE 才能修改 否则不行
            if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                update = 1;
            }
            map.put("update",update);
            map.put("delete",delete);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][deletePuc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][deletePuc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, pucCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * @return
     * 采购中：选择供应商弹窗  只传 VD
     * 只做供应商编号弹窗
     * 销售中：选择采购商弹窗  只传VE
     * 只做采购商编号弹窗
     */
    @RequestMapping(value = "/unitOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult unitOption(UnitOptionVo vo, @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            PageInfo<UnitOptionVo> list = null;
            if (StringUtils.isNotEmpty(vo.getUnitType())) {
                list = sysUnitService.selectByOption(vo, page, size, sysUser);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, list);
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][unitOption] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_UNIT_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][unitOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_UNIT_R, pucCon+CommonFainl.SELECTFIANL, "");
        }
    }



    /**
     *  采购合同/销售合同公用
     * 下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult option() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();

            map.put("pucType", pucTypeService.optionGet());//采购合同
            map.put("slcType", slcTypeService.optionGet());//销售合同
            map.put("acReqd", prodClsUtils.getList());//绑定附加成本
            map.put("afReqd", prodClsUtils.getList());//绑定附加费用
            map.put("prcSite", sysCodeDtlService.optionGet("PRC_SITE"));//定价点
            map.put("isCms", prodClsUtils.getList());//是否代销
            map.put("drDiffJgd", sysCodeDtlService.optionGet("DR_ROLE"));//差异裁定方
            map.put("multiImpl", prodClsUtils.getList());//多次执行
            map.put("implByIns", prodClsUtils.getList());//是否指令执行
            map.put("puBrdgMode", sysCodeDtlService.optionGet("BRDG_MODE"));//采购桥接方式
            map.put("slBrdgMode", sysCodeDtlService.optionGet("BRDG_MODE"));//销售桥接方式
            map.put("isPuItmd", prodClsUtils.getList());//是否居间采购
            map.put("isSpot", prodClsUtils.getList());//是否现货
            map.put("psStlMthd", sysCodeDtlService.optionGet("PS_STL_MTHD"));//结算方式
            map.put("splEnabled", prodClsUtils.getList());//允许增补商品
            map.put("gmEnabled", prodClsUtils.getList());//启用保证金
            map.put("mfzEnabled", prodClsUtils.getList());//启用冻款
            map.put("bxiEnabled", prodClsUtils.getList());//启用配码
            map.put("rqdCtrl", sysCodeDtlService.optionGet("RQD_CTRL"));//货期控制
            map.put("psMfzSite", sysCodeDtlService.optionGet("PS_MFZ_SITE"));//冻款点
            map.put("planCtrl", prodClsUtils.getList());//计划控制
            map.put("ordType", sysCodeDtlService.optionGet("ORD_TYPE"));//订单类别
            map.put("delivMthd", sysCodeDtlService.optionGet("DELIV_MTHD"));//送货方式
            map.put("progress", sysCodeDtlService.optionGet("PSC_PROG"));//进度
            map.put("psaCtlr", sysCodeDtlService.optionGet("PS_ROLE"));//协议控制方

            map.put("slcAutoGen", prodClsUtils.getList());//销售合同自动生成
            map.put("slcAutoChk", prodClsUtils.getList());//销售合同自动审核
            map.put("pucAutoGen", prodClsUtils.getList());//采购合同自动生成
            map.put("slcGen", prodClsUtils.getList());//销售合同已生成
            map.put("pucAutoChk", prodClsUtils.getList());//采购合同自动审核
            map.put("isSlItmd", prodClsUtils.getList());//是否居间销售
            map.put("vdrInvd", prodClsUtils.getList());//供应商介入
            map.put("vdeInvd", prodClsUtils.getList());//采购商介入

            map.put("effective", prodClsUtils.getList());//已生效
            map.put("suspended", prodClsUtils.getList());//挂起
            map.put("cancelled", prodClsUtils.getList());//撤销
            map.put("renewed", prodClsUtils.getList());//已递延
            map.put("slCtrl", sysCodeDtlService.optionGet("SL_CTRL"));//销售控制PU_CTRL
            map.put("slFwdd", prodClsUtils.getList());//已转销
            map.put("puCtrl", sysCodeDtlService.optionGet("PU_CTRL"));//采购控制
            map.put("puFwdd", prodClsUtils.getList());//已转购

            map.put("prodStatus", sysCodeDtlService.optionGet("PROD_STATUS"));//商品状态


            List<PurKeyValue> list = new ArrayList<>();
            list.add(new PurKeyValue("采购申请","PUA"));
            map.put("srcDocType",list);

            List<PurKeyValue> list1 = new ArrayList<>();
            list1.add(new PurKeyValue("销售申请","SLA"));
            map.put("srcDocType1",list1);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][option] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 类别相关数据查询
     *
     * @return
     */
    @RequestMapping(value = "/pscAutolist", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult pscAutolist(@RequestBody PscAutoVo vo) {
        try {
            PscAutoVo pscAutoVo = pucTypeService.selectByPscAuto(vo.getPucType());

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pscAutoVo);
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][pscAutolist] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_PSC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][pscAutolist] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_PSC_R, pucCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 采购合同/销售合同公用
     * 如果修改了采购合同类别以及供应商 前台弹窗是否清空明细数据  点确定时走的接口
     * 通过单号查询出所有数据，然后调用明细的删除
     * */
    @RequestMapping(value = "/wipeData", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult wipeData(@RequestBody PscDtlVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            List<PscDtlVo> list = pscDtlService.findByPscNum(vo);
            if(list!=null && list.size()>0){
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,pscDtlService.deletePscDtl(list,sysUser));
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,"");

        } catch (ServiceException ex) {
            log.error("[PurchasePucController][wipeData] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][wipeData] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_D, CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 查询采购合同必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getPucFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getPucFile(@RequestBody PucType vo) {
        try {
            if(StringUtils.isNotEmpty(vo.getPucType())){
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("PUC,PSC",vo.getPucType()));
            }else{
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("PUC,PSC","PUR_PUC"));
            }

        } catch (Exception ex) {
            log.error("[PurchasePucController][getPucFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 不能直接修改字段
     *
     * @return
     */
    @RequestMapping(value = "/updateNotFile", method = {RequestMethod.POST})
    public JsonResult updateNotFile(@RequestBody PucType vo) {
        try {

            List<TableFile> at = new ArrayList<>();
            List<String> list = null;
            if(StringUtils.isNotEmpty(vo.getPucType())){
                list = fieldUtils.isNotUpdateWareh(vo.getPucType());
            }else{
                list = fieldUtils.isNotUpdateWareh("PUR_PUC");
            }
            if(list!=null){
                for (String li : list) {
                    TableFile t = new TableFile();
                    t.setTableFlie(li);
                    at.add(t);
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, at);
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][updateNotFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 采购合同中确认单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.CONFIRM, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, pucService.confirm(pucService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][confirm] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][confirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, pucCon+CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 采购合同中重做单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REDO, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, pucService.redo(pucService.selectByOnly(vo)));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, pucCon+CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 采购合同中审核单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.EXAMINE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, pucService.examine(pucService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][examine] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][examine] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, pucCon+CommonFainl.BILLEXAMINEFIANL, "");
        }
    }

    /**
     * 采购合同中重审单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REITERATE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, pucService.reiterate(pucService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, pucCon+CommonFainl.BILLREITERATEFIANL, "");
        }
    }

    /**
     * 采购合同中关闭单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.CLOSE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCLOSE, pucService.close(pucService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][close] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][close] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, pucCon+CommonFainl.BILLCLOSEFIANL, "");
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REUSING, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREUSING, pucService.reusing(pucService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][reusing] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][reusing] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, pucCon+CommonFainl.BILLREUSINGFIANL, "");
        }
    }

    /**
     * 采购合同中挂起单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.HANGUP, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, pucService.hangUp(pucService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, pucCon+CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 采购合同中恢复单据
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
            //判断是否有权限操作
           /* PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.RECOVERY, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, pucService.recovery(pucService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, pucCon+CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 采购合同中作废单据
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
            //判断是否有权限操作
           /* PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.TOVOID, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, pucService.toVoid(pucService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, pucCon+CommonFainl.BILLTOVOIDFIANL, "");
        }
    }

    /**
     * 采购合同中导入原始单据
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
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLIMPORTBILL, pucService.importBill(vo));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][importBill] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTBILL, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][importBill] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTBILL, pucCon+CommonFainl.BILLIMPORTBILLFIANL, "");
        }
    }

    /**
     * 采购合同中导入原始单据 清除更新数据
     *
     * @return
     */
    @RequestMapping(value = "/importUpdate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult importUpdate(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLIMPORTUPDATE, pucService.importUpdate(vo,sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][importBill] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTUPDATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][importBill] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTUPDATE, pucCon+CommonFainl.BILLIMPORTUPDATEFIANL, "");
        }
    }

    /**
     * 查询是否固定单价
     * @param jsonObject
     * @param re
     * @return
     */
    @RequestMapping(value = "/getFixedPrice", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getFixedPrice(@RequestBody JSONObject jsonObject, HttpServletRequest re) {
        try {
            String pscType=jsonObject.get("pscType").toString();
            String pscNum=jsonObject.get("pscNum").toString();
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pucService.getFixedPrice(pscType,pscNum));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][getFixedPrice] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_FIXED, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][getFixedPrice] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_FIXED, pucCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     *
     * 采购单/销售单公用 查询超额比例
     *
     * @return
     */
    @RequestMapping(value = "/getExbl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getExbl(@RequestBody JSONObject jsonObject, HttpServletRequest re) {
        try {
            String pscNum=jsonObject.get("pscNum").toString();
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pucService.getExbl(pscNum));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][getExbl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_EXBL, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][getExbl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_EXBL, pucCon+CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 采购合同可操作状态
     *
     * @return
     */
    @RequestMapping(value = "/pucAuditType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult pucAuditType(@RequestBody PscVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pucService.initButtons(vo));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][pucAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][pucAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, pucCon+CommonFainl.SELECTFIANL, "");
        }
    }
}