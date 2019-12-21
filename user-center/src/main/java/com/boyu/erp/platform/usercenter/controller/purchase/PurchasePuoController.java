package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.purchase.Puo;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscDtlService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PuoService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
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
 * @description: 采购单
 * @author: wz
 * @create: 2019-7-17 16:45
 */
@Slf4j
@RestController
@RequestMapping("/purchase/puo")
public class PurchasePuoController extends BaseController {
    private static final String puoCon = "采购单";
    //判断权限操作字段
    private final String tableName = "puo|";
    private static final String Operations = "PurchasePuoController|";
    @Autowired
    private PuoService puoService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private PscDtlService pscDtlService;

    /**
     *采购单弹窗
     * 收货仓库： 采购商id  +   WH
     * 发货仓库： 供应商id  +   WH
     */

    /**
     * 采购单查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult puoList(PsoVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            PageInfo<PsoVo> resultList = puoService.selectAll(vo, page, size, sysUser);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][puoList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUO_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][puoList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUO_R, puoCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加采购单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加采购单")
    @RequestMapping(value = "/addPuo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPuo(HttpServletRequest re, @RequestBody PsoVo vo) {
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
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, puoService.insertPuo(vo, user));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][addPuo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUO_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][addPuo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUO_C, puoCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改采购单
     *先去判断采购合同号是否发生改变，如果没有就修改当前数据
     * 如果发生改变更新旧合同的任务数
     * @param vo
     * @return
     */
    @SystemLog(name = "修改采购单")
    @RequestMapping(value = "/updatePuo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePuo(HttpServletRequest re, @RequestBody PsoVo vo) {
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

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, puoService.updatePuo(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][updatePuo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUO_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][updatePuo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUO_U, puoCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除采购单
     * @param vo
     * @return
     */
    @SystemLog(name = "删除采购单")
    @RequestMapping(value = "/deletePuo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePuo(HttpServletRequest re,@RequestBody PsoVo vo) {
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

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, puoService.deletePuo(vo));

        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][deletePuo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUO_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][deletePuo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUO_D, puoCon+CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 判断是否可以删除/修改
     *
     * 采购/销售公用
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "判断是否可以删除/修改")
    @RequestMapping(value = "/isUpdateOrDelete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult isUpdateOrDelete(@RequestBody PsoVo vo) {
        try {

            Map<String, Object> map = new HashMap<>();
            int update = 0;
            int delete = 0;
            //状态要求：PROGRESS IN (PENDING, CONFIRMED) && SUSPENDED = FALSE && CANCELLED = FALSE &&
            // ITMD_PSO_NUM IS NULL && EXEC_PSO_NUM IS NULL 才能删除
            if ((vo.getProgress().equals("PG") || vo.getProgress().equals("C")) &&
                    vo.getSuspended().equals("F") &&
                    vo.getCancelled().equals("F")
                    && StringUtils.NullEmpty(vo.getItmdPsoNum())&&
                    StringUtils.NullEmpty(vo.getExecPsoNum() )) {
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
            log.error("[PurchasePuoController][deletePuc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][deletePuc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, puoCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 下拉框
     * 采购/销售公用
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult option() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();

            map.put("vdrInvd", prodClsUtils.getList());//供应商介入
            map.put("vdeInvd", prodClsUtils.getList());//采购商介入
            map.put("sloGen", prodClsUtils.getList());//销售单已生成
            map.put("puoGen", prodClsUtils.getList());//采购单已生成
            map.put("planCtrl", prodClsUtils.getList());//计划控制
            map.put("bxiEnabled", prodClsUtils.getList());//启用配码
            map.put("effective", prodClsUtils.getList());//已生效
            map.put("suspended", prodClsUtils.getList());//挂起
            map.put("cancelled", prodClsUtils.getList());//撤销
            map.put("progress", sysCodeDtlService.optionGet("PSX_PROG"));//进度
            map.put("psoKind", sysCodeDtlService.optionGet("PSO_KIND"));//购销单性质
            map.put("delivMthd", sysCodeDtlService.optionGet("DELIV_MTHD"));//送货方式

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][option] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 如果修改了采购合同 前台弹窗是否清空明细数据  点确定时走的接口
     * 通过单号查询出所有数据，然后调用明细的删除
     * 采购/销售公用
     * */
    @RequestMapping(value = "/wipeData", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult wipeData(@RequestBody Puo vo) {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,puoService.deleteByPsoDtl(vo.getPsoNum()));

        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][wipeData] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][wipeData] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, puoCon+CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 判断是否存在商品明细 返回响应数据
     * @return
     */
    @RequestMapping(value = "/getJudgeDtlByPuo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getJudgeDtlByPuo(@RequestBody PscDtlVo vo){
        try
        {
            Map<String,Object> resultMap=new HashMap<String,Object>();

            List<PscDtlVo> list = pscDtlService.getJudgeDtlByPuo(vo);
            if(list.isEmpty()){
                return new JsonResult(JsonResultCode.FAILURE_PUC_PROD_R, CommonFainl.PRODBYPUOFAIL, resultMap);
            }
            resultMap.put("list",list);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePuoController][getJudgeDtlByPuo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePuoController][getJudgeDtlByPuo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_R, puoCon+"商品明细"+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 采购单中确认单据
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
            //判断是否有权限操作
           /* PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.CONFIRM, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, puoService.confirm(puoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][getUpdateFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, puoCon+CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 采购单中重做单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REDO, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, puoService.redo(puoService.selectByOnly(vo)));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, puoCon+CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 采购单中审核单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.EXAMINE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, puoService.examine(puoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][getUpdateFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, puoCon+CommonFainl.BILLEXAMINEFIANL, "");
        }
    }

    /**
     * 采购单中重审单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.REITERATE, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, puoService.reiterate(puoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, puoCon+CommonFainl.BILLREITERATEFIANL, "");
        }
    }

    /**
     * 采购单中挂起单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.HANGUP, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, puoService.hangUp(puoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, puoCon+CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 采购单中恢复单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.RECOVERY, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, puoService.recovery(puoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, puoCon+CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 采购单中作废单据
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
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.TOVOID, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, puoService.toVoid(puoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, puoCon+CommonFainl.BILLTOVOIDFIANL, "");
        }
    }

    /**
     * 采购单中结束入库
     *
     * @return
     */
    @RequestMapping(value = "/closeGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult closeGoods(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.CLOSEGOODS, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCLOSEGOODS, puoService.closeGoods(puoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][closeGoods] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSEGOODS, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][closeGoods] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CLOSEGOODS, puoCon+CommonFainl.BILLCLOSEGOODSFIANL, "");
        }
    }

    /**
     * 采购单中重启入库
     *
     * @return
     */
    @RequestMapping(value = "/restartGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult restartGoods(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.RESTARTGOODS, vo.getUnitId(), sysUser);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRESTARTGOODS, puoService.restartGoods(puoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][restartGoods] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RESTARTGOODS, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][restartGoods] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RESTARTGOODS, puoCon+CommonFainl.BILLRESTARTGOODSFIANL, "");
        }
    }

    /**
     * 采购单中导入单据
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
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLIMPORTBILL, puoService.importBill(vo));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][importBill] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTBILL, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][importBill] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTBILL, puoCon+CommonFainl.BILLIMPORTBILLFIANL, "");
        }
    }

    /**
     * 采购单中导入更新
     *
     * @return
     */
    @RequestMapping(value = "/importUpdate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult importUpdate(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLIMPORTBILL, puoService.importUpdate(vo));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][importUpdate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTUPDATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][importUpdate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_IMPORTUPDATE, puoCon+CommonFainl.BILLIMPORTUPDATEFIANL, "");
        }
    }

    /**
     * 采购单可操作状态
     *
     * @return
     */
    @RequestMapping(value = "/puoAuditType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult puoAuditType(@RequestBody PsoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, puoService.initButtons(vo));
        } catch (ServiceException ex) {
            log.error("[PurchasePuoController][puoAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuoController][puoAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, puoCon+CommonFainl.SELECTFIANL, "");
        }
    }

}



