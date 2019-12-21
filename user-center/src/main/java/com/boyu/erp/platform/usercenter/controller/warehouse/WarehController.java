package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.WarehOptinModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehPopUpFilterModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.system.SysUnitUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform_text
 * @description: 仓库控制层
 * @author: clf
 * @create: 2019-06-26 12:31
 */
@Slf4j
@RestController
@RequestMapping(value = "/warehouse")
public class WarehController extends BaseController {
    private static final String psn = "仓库";
    private static final String option = "下拉";
    private static final String table = "wareh_a|";
    private static final String Operations = "WarehController|";
    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private SysUnitUtils unitUtils;
    @Autowired
    private OperationAuthorityUtils operationAuthorityUtils;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private WarehService warehService;


    /**
     * 查询仓库列表
     *
     * @param warehVo
     * @return
     */
    @RequestMapping(value = "/all", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult warehAll(WarehVo warehVo,
                               @RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                               @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                               HttpServletRequest request) {
        try {
            if (StringUtils.NullEmpty(warehVo.getUnitStatus())) {
                warehVo.setUnitStatus(null);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehService.selectWareh(page, size, warehVo, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[warehService][selectWareh] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, psn + CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 查询指定仓库
     *
     * @param wareh
     * @return
     */
    @RequestMapping(value = "/getWarehId", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getWarehId(@RequestBody Wareh wareh) {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            map.put("Wareh", warehService.selectByWarehId(wareh.getWarehId()));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[warehService][selectWareh] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, psn + CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 增加仓库
     *
     * @param warehVo
     * @return
     */
    @SystemLog(name = "增加仓库")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addWarehAll(@RequestBody WarehVo warehVo, HttpServletRequest request) {
        try {
            if (StringUtils.NullEmpty(warehVo.getUnitNum())) {
                return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, "请填写仓库编号", "");
            }
            if (StringUtils.NullEmpty(warehVo.getUnitCode())) {
                return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, "请填写仓库代码", "");
            }
            if (StringUtils.NullEmpty(warehVo.getUnitName())) {
                return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, "请填写仓库名称", "");
            }
            if (StringUtils.NullEmpty(warehVo.getUnitStatus())) {
                return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, "请选择组织状态", "");
            }
            if (StringUtils.NullEmpty(warehVo.getWarehStatus())) {
                return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, "请选择仓库状态", "");
            }
            if (warehVo.getUnitId() == null) {
                if (unitUtils.isCodeAndName(warehVo.getUnitName(), warehVo.getUnitCode(), CommonFainl.ADD)) {
                    return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, psn + CommonFainl.ADDFIANL + "： 组织代码或组织名称已存在", "");
                }
            }
            String s = operationAuthorityUtils.isAuthority(table, Operations + CommonFainl.ADD);
            if (!(operationAuthorityUtils.isEmptAuthority(table, Operations + CommonFainl.ADD, this.getSessionSysUser(request)))) {
                return new JsonResult(JsonResultCode.FAILURE, CommonFainl.TEXT + s, "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,  warehService.instrWareh(warehVo, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[warehService][selectWareh] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_1, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_1, psn + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改仓库信息
     *
     * @param warehVo
     * @return
     */
    @SystemLog(name = "修改仓库信息")
    @RequestMapping(value = "/updateWareh", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateWareh(@RequestBody WarehVo warehVo, HttpServletRequest request) {
        try {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(warehVo.getUpdateUnitCode()) || org.apache.commons.lang3.StringUtils.isNotBlank(warehVo.getUpdateUnitName())) {
                if (unitUtils.isCodeAndName(warehVo.getUpdateUnitName(), warehVo.getUpdateUnitCode(), CommonFainl.ADD)) {
                    return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, psn + CommonFainl.UPDATEFANS + "： 组织代码或组织名称已存在", "");
                }
            }
            String s = operationAuthorityUtils.isAuthority(table, Operations + CommonFainl.UPDATE);
            if (!(operationAuthorityUtils.isNotAuthority(this.getSessionSysUser(request), s))) {
                return new JsonResult(JsonResultCode.FAILURE, CommonFainl.TEXT + s, "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, warehService.udpateWareh(warehVo, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[warehService][udpateWareh] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_2, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][updateWareh] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_2, psn + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除仓库信息
     *
     * @param warehVo
     * @return
     */
    @SystemLog(name = "删除仓库")
    @RequestMapping(value = "/deleteWareh", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteWareh(@RequestBody WarehVo warehVo, HttpServletRequest request) {
        try {
            String s = operationAuthorityUtils.isAuthority(table, Operations + CommonFainl.DELETE);
            if (!(operationAuthorityUtils.isNotAuthority(this.getSessionSysUser(request), s))) {
                return new JsonResult(JsonResultCode.FAILURE, CommonFainl.TEXT + s, "");
            }
            if (warehVo.getWarehId() == null || warehVo.getOwnerId() == null) {
                return new JsonResult(JsonResultCode.FAILURE, CommonFainl.DELETEFANS, "请选择仓库");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, warehService.deleteWareH(warehVo, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[warehService][deleteWareH] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][deleteWareh] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_3, psn + CommonFainl.DELETEFANS, "");
        }
    }


    /**
     * 仓库不能直接修改字段
     *
     * @return
     */
    @RequestMapping(value = "/updateNotFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateNotFile(HttpServletRequest request) {
        try {
            List<TableFile> at = new ArrayList<>();
            for (String li : warehService.getUpdateFile()) {
                TableFile t = new TableFile();
                t.setTableFlie(li);
                at.add(t);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, at);
        } catch (ServiceException ex) {
            log.error("[warehService][getUpdateFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 仓库代码查询(仓库代码弹窗)
     *
     * @return
     */
    @RequestMapping(value = "/getOptinCode", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOptinCode(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                   @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                   WarehOptinModel model, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehService.getOptinCode(page, size, model, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[warehService][ getOptinCode] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][ getOptinCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_OPENT, psn + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 仓库所有下拉初始化
     *
     * @return
     */
    @RequestMapping(value = "/getOptin/all", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOptinAll(HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            List<String> list = prodClsUtils.getListString();
            //库存管理
            list.add("stkAdopted");
            //唯一码管理
            list.add("uidAdopted");
            //配码库存管理
            list.add("astAdopted");
            //装箱库存管理
            list.add("bstAdopted");
            //货位管理
            list.add("locAdopted");
            //人员状态
            list.add("prsnstatus");
            //是共享
            list.add("shared");
            //可征募
            list.add("recruitable");
            //允许负库存
            list.add("negStk");
            //允许负动存
            list.add("negAtk");
            //集货区管理
            list.add("clnAreaAdopted");
            //启用分货复位
            list.add("clnRckReqd");
            //启用唯一码验收
            list.add("acptUidReqd");
            //启用唯一码分储
            list.add("paUidReqd");
            //启用唯一码分捡
            list.add("pickUidReqd");
            //启用唯一码复核
            list.add("rckUidReqd");
            //启用唯一码装箱
            list.add("boxUidReqd");

            Map<String, Object> map = prodClsUtils.getMapOffOn(list);
            //执照类别
            map.put("licType", codeDtlService.optionGet("LIC_TYPE"));


            List<OptionVo> Td = new ArrayList<>();
            // System.out.println("{后} "+map.get("boxUidReqd"));
            Td.add(new OptionVo("活动", "A"));
            Td.add(new OptionVo("非活动", "I"));
            Td.add(new OptionVo("删除", "D"));
            //仓库状态
            map.put("warehStatus", Td);
            //组织状态
            map.put("unitStatus", Td);
            //仓库属性
            map.put("warehProp", codeDtlService.optionGet("WAREH_PROP"));

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);

        } catch (ServiceException ex) {
            log.error("[warehService][ getOptinCode] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_OPENT, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][ getOptinCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_OPENT, psn + option + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 仓库弹窗
     *
     * @author HHe
     * @date 2019/9/15 14:51
     */
    @RequestMapping(value = "/warehpopup", method = RequestMethod.POST)
    public JsonResult warehPopUp(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                 @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                 WarehPopUpFilterModel warehPopUpFilterModel, HttpServletRequest request) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehService.warehPopUp(warehPopUpFilterModel, sysUser));
        } catch (ServiceException e) {
            log.error("[WarehController][warehPopUp] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[WarehController][warehPopUp] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 仓库功能按钮初始化
     */
    @RequestMapping(value = "/warehAuditType", method = {RequestMethod.POST})
    public JsonResult warehAuditType(@RequestBody ProdCls model) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehService.initButtons(model));
        } catch (ServiceException ex) {
            log.error("[WarehService][initButtons] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询商品能否审核异常:ProductClsController.getAuditType()", "");
        }
    }


}
