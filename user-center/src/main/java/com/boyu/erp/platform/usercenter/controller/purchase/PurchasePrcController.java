package com.boyu.erp.platform.usercenter.controller.purchase;

import com.alibaba.fastjson.JSONObject;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.purchase.Prc;
import com.boyu.erp.platform.usercenter.entity.purchase.PrcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.purchase.PscDtlModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PrcService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PrcTypeService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcDtlService;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.purchase.*;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;
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
 * @description: 退购合同
 * @author: wz
 * @create: 2019-7-25 11:21
 */
@Slf4j
@RestController
@RequestMapping("/purchase/prc")
public class PurchasePrcController extends BaseController {
    private static final String prcCon = "退购合同";
    //判断权限操作字段
    private final String tableName = "prc|";
    private static final String Operations = "PurchasePrcController|";
    @Autowired
    private PrcService prcService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private PrcTypeService prcTypeService;
    @Autowired
    private FieldUtils fieldUtils;
    @Autowired
    private SrcTypeService srcTypeService;
    @Autowired
    private OperationAuthorityUtils privUtils;

    /**
     * 退购合同查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult prcList(PrcVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            PageInfo<PrcVo> resultList = prcService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][prcList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][prcList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRC_R, prcCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加退购合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加退购合同")
    @RequestMapping(value = "/addPrc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPrc(HttpServletRequest re, @RequestBody PrcVo vo) {
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
            vo.setUnitId(vo.getsUnitId());
            vo.setVendeeId(vo.getsUnitId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, prcService.insertPrc(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][addPrc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRC_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][addPrc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRC_C, prcCon + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改退购合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改退销合同")
    @RequestMapping(value = "/updatePrc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePrc(HttpServletRequest re, @RequestBody PrcVo vo) {
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

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, prcService.updatePrc(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][updatePrc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRC_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][updatePrc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRC_U, prcCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除退购合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除退销合同")
    @RequestMapping(value = "/deletePrc", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePrc(HttpServletRequest re, @RequestBody PrcVo vo) {
        try {

            SysUser user = (SysUser) this.isNullUser(re);
            if (user == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            //判断是否有权限操作
//            PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.DELETE, vo.getUnitId(), user);
//            if (!examine.getPrivBoolean()) {
//                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
//            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, prcService.deletePrc(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][deletePrc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRC_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][deletePrc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRC_D, prcCon + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 判断是否可以删除/修改
     * 采购/销售公用
     * @param vo
     * @return
     */
    @SystemLog(name = "判断是否可以删除/修改")
    @RequestMapping(value = "/isUpdateOrDelete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult isUpdateOrDelete(@RequestBody PrcVo vo) {
        try {

            Map<String, Object> map = new HashMap<>();
            int update = 0;
            int delete = 0;
            //状态要求：PROGRESS IN (PENDING, CONFIRMED) && SUSPENDED = FALSE && CANCELLED = FALSE && ITMD_PSC_NUM IS NULL && EXEC_PSC_NUM IS NULL
            // 才能删除
            if ((vo.getProgress().equals("PG") || vo.getProgress().equals("C")) &&
                    vo.getSuspended().equals("F") &&
                    vo.getCancelled().equals("F")
                    && StringUtils.NullEmpty(vo.getItmdRtcNum())&&
                    StringUtils.NullEmpty(vo.getExecRtcNum())) {
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
            log.error("[PurchasePrcController][isUpdateOrDelete] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][isUpdateOrDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, prcCon + CommonFainl.SELECTFIANL, "");
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

            map.put("prcType", prcTypeService.optionGet());//退购合同
            map.put("srcType", srcTypeService.purGet());//退销合同
            map.put("acReqd", prodClsUtils.getList());//绑定附加成本
            map.put("afReqd", prodClsUtils.getList());//绑定附加费用
            map.put("prcSite", sysCodeDtlService.optionGet("PRC_SITE"));//定价点
            map.put("isCms", prodClsUtils.getList());//是否代销
            map.put("drDiffJgd", sysCodeDtlService.optionGet("DR_ROLE"));//差异裁定方
            map.put("rtnaInvd", prodClsUtils.getList());//占用可退额度
            map.put("multiImpl", prodClsUtils.getList());//多次执行
            map.put("accEnabled", prodClsUtils.getList());//启用授信
            map.put("rtnAccSite", sysCodeDtlService.optionGet("RTN_ACC_SITE"));//授信点
            map.put("splEnabled", prodClsUtils.getList());//允许增补商品
            map.put("vdrInvd", prodClsUtils.getList());//供应商介入
            map.put("vdeInvd", prodClsUtils.getList());//采购商介入
            map.put("prBrdgMode", sysCodeDtlService.optionGet("BRDG_MODE"));//退购桥接方式
            map.put("srBrdgMode", sysCodeDtlService.optionGet("BRDG_MODE"));//退销桥接方式
            map.put("isPrItmd", prodClsUtils.getList());//是否居间退购
            map.put("isSrItmd", prodClsUtils.getList());//是否居间退销
            map.put("bxiEnabled", prodClsUtils.getList());//启用配码

            map.put("srcAutoGen", prodClsUtils.getList());//退销合同自动生成
            map.put("srcAutoChk", prodClsUtils.getList());//退销合同自动审核
            map.put("prcAutoGen", prodClsUtils.getList());//退购合同自动生成
            map.put("prcAutoChk", prodClsUtils.getList());//退购合同自动审核
            map.put("pscReqd", prodClsUtils.getList());//关联购销合同
            map.put("rtnType", sysCodeDtlService.optionGet("RTN_TYPE"));//退单类别
            map.put("delivMthd", sysCodeDtlService.optionGet("DELIV_MTHD"));//送货方式
            map.put("srcGen", prodClsUtils.getList());//退销合同已生成
            map.put("prcGen", prodClsUtils.getList());//退购合同已生成
            map.put("acBnd", prodClsUtils.getList());//附加成本已绑定
            map.put("srFwdd", prodClsUtils.getList());//已转退销
            map.put("prodStatus", sysCodeDtlService.optionGet("PROD_STATUS"));//商品状态
//            map.put("brand", brandService.optionGet());//品牌名称
            map.put("effective", prodClsUtils.getList());//已生效
            map.put("suspended", prodClsUtils.getList());//挂起
            map.put("cancelled", prodClsUtils.getList());//撤销
            map.put("progress", sysCodeDtlService.optionGet("PSC_PROG"));//进度

            List<PurKeyValue> list = new ArrayList<>();
            list.add(new PurKeyValue("退销合同","SRC"));
            map.put("srcDoctype", list );

            List<PurKeyValue> list1 = new ArrayList<>();
            list1.add(new PurKeyValue("退购合同","PRC"));
            map.put("srcDoctype1", list1 );

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][option] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 类别相关数据查询
     *
     * @return
     */
    @RequestMapping(value = "/prcAutolist", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult prcAutolist(@RequestBody RtcAutoVo vo) {
        try {
            RtcAutoVo prcAutoVo = prcTypeService.selectByPrcAuto(vo.getPrcType());

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prcAutoVo);
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][prcAutolist] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRC_RTC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][prcAutolist] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRC_RTC_R, prcCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 如果修改了退购合同类别以及供应商 前台弹窗是否清空明细数据  点确定时走的接口
     * 通过退销单号删除明细数据  更新退销数据
     * 采购/销售公用
     */
    @RequestMapping(value = "/wipeData", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult wipeData(@RequestBody Prc vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, prcService.deletePrcDtl(vo.getRtcNum(), sysUser));

        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][wipeData] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][wipeData] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_D, CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 查询退购合同必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getPrcFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getPrcFile(@RequestBody PrcType vo) {
        try {
            if (StringUtils.isNotEmpty(vo.getPrcType())) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("PRC,RTC", vo.getPrcType()));
            } else {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("PRC,RTC", "PUR_PRC"));
            }

        } catch (Exception ex) {
            log.error("[PurchasePrcController][getPrcFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 不能直接修改字段
     *
     * @return
     */
    @RequestMapping(value = "/updateNotFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateNotFile(@RequestBody PrcType vo) {
        try {

            List<TableFile> at = new ArrayList<>();
            if (fieldUtils.isNotUpdateWareh(vo.getPrcType()) != null) {
                for (String li : fieldUtils.isNotUpdateWareh(vo.getPrcType())) {
                    TableFile t = new TableFile();
                    t.setTableFlie(li);
                    at.add(t);
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, at);
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][updateNotFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 退购合同中确认单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.CONFIRM, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = prcService.selectByOnly(vo);
            if(vo==null){
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, prcService.confirm(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][confirm] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][confirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, prcCon + CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 退购合同中重做单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REDO, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = prcService.selectByOnly(vo);
            if(vo==null){
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, prcService.redo(vo));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, prcCon + CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 退购合同中审核单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.EXAMINE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = prcService.selectByOnly(vo);
            if(vo==null){
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, prcService.examine(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][examine] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][examine] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, prcCon + CommonFainl.BILLEXAMINEFIANL, "");
        }
    }

    /**
     * 退购合同中重审单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REITERATE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = prcService.selectByOnly(vo);
            if(vo==null){
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, prcService.reiterate(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, prcCon + CommonFainl.BILLREITERATEFIANL, "");
        }
    }

    /**
     * 退购合同中挂起单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.HANGUP, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = prcService.selectByOnly(vo);
            if(vo==null){
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, prcService.hangUp(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, prcCon + CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 退购合同中恢复单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.RECOVERY, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = prcService.selectByOnly(vo);
            if(vo==null){
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, prcService.recovery(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, prcCon + CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 退购合同中关闭单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.CLOSE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = prcService.selectByOnly(vo);
            if(vo==null){
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCLOSE, prcService.close(vo, sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][close] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][close] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSE, prcCon + CommonFainl.BILLCLOSEFIANL, "");
        }
    }

    /**
     * 退购合同中重用单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REUSING, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = prcService.selectByOnly(vo);
            if(vo==null){
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREUSING, prcService.reusing(vo,sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][reusing] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][reusing] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REUSING, prcCon+CommonFainl.BILLREUSINGFIANL, "");
        }
    }


    /**
     * 退购申请中作废单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.TOVOID, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo = prcService.selectByOnly(vo);
            if(vo==null){
                throw new ServiceException("403", "单据异常！");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, prcService.toVoid(vo,sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, prcCon+CommonFainl.BILLTOVOIDFIANL, "");
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
            String rtcType=jsonObject.get("rtcType").toString();
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prcService.getFixedPrice(rtcType));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][getFixedPrice] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_FIXED, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][getFixedPrice] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_FIXED, prcCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 退购合同可操作状态
     *
     * @return
     */
    @RequestMapping(value = "/prcAuditType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult prcAuditType(@RequestBody PrcVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prcService.initButtons(vo));
        } catch (ServiceException ex) {
            log.error("[PurchasePrcController][prcAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcController][prcAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, prcCon+CommonFainl.SELECTFIANL, "");
        }
    }


}



