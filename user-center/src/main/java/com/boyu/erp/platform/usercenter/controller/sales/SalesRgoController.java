package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.sales.RgoDtl;
import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoDtlService;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoService;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.purchase.*;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
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
 * @description: 调配单控制层
 * @author: wz
 * @create: 2019-10-6 15:32
 */
@Slf4j
@RestController
@RequestMapping(value = "/sales/rgo")
public class SalesRgoController extends BaseController {
    private static final String rgoCon = "调配单";

    @Autowired
    private RgoService rgoService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private RgoTypeService rgoTypeService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private RgoDtlService rgoDtlService;
    @Autowired
    private FieldUtils fieldUtils;

    /**
     * 调配单查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public JsonResult rgoList(RgoVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            PageInfo<RgoVo> resultList = rgoService.selectAll(vo, page, size, sysUser);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][rgoList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][rgoList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_R, rgoCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加调配单
     * @param re
     * @param vo
     * @return
     */
    @SystemLog(name = "添加调配单")
    @RequestMapping(value = "/addRgo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addRgo(HttpServletRequest re, @RequestBody RgoVo vo) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            vo.setUnitId(vo.getsUnitId());
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, rgoService.insertRgo(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesRgoController][addRgo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][addRgo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SLC_C, rgoCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改调配单
     * @param re
     * @param vo
     * @return
     */
    @SystemLog(name = "修改调配单")
    @RequestMapping(value = "/updateRgo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateRgo(HttpServletRequest re, @RequestBody RgoVo vo) {
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

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, rgoService.updateRgo(vo, user));

        } catch (ServiceException ex) {
            log.error("[SalesRgoController][updateRgo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][updateRgo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_U, rgoCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除调配单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除调配单")
    @RequestMapping(value = "/deleteRgo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteRgo(HttpServletRequest re, @RequestBody RgoVo vo) {
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

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, rgoService.deleteRgo(vo, user));

        } catch (ServiceException ex) {
            log.error("[SalesRgoController][deleteRgo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][deleteRgo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_D, rgoCon+CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 判断是否可以删除或修改
     * @param vo
     * @return
     */
    @SystemLog(name = "判断是否可以删除/修改")
    @RequestMapping(value = "/isUpdateOrDelete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult isUpdateOrDelete(@RequestBody RgoVo vo) {
        try {

            Map<String, Object> map = new HashMap<>();
            int update = 0;
            int delete = 0;
            if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                update = 1;
            }
            if ((vo.getProgress().equals("PG") || vo.getProgress().equals("C")) &&
                    vo.getSuspended().equals("F") &&
                    vo.getCancelled().equals("F") ) {
                delete = 1;
            }

            map.put("update",update);
            map.put("delete",delete);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][deletePuc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][deletePuc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, rgoCon+CommonFainl.SELECTFIANL, "");
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

            map.put("rgoType", rgoTypeService.optionGet());//调配单类别
            map.put("bxiEnabled", prodClsUtils.getList());//启用配码
            map.put("drDiffJgd", sysCodeDtlService.optionGet("DR_ROLE"));//差异裁定方
            map.put("prcAutoGen", prodClsUtils.getList());//退购合同自动生成
            map.put("prcAutoChk", prodClsUtils.getList());//退购合同自动审核
            map.put("pucAutoGen", prodClsUtils.getList());//采购合同自动生成
            map.put("pucAutoChk", prodClsUtils.getList());//采购合同自动审核
            map.put("srcUnitInvd", prodClsUtils.getList());//调出方是否介入
            map.put("destUnitInvd", prodClsUtils.getList());//调入方是否介入

            map.put("effective", prodClsUtils.getList());//已生效
            map.put("suspended", prodClsUtils.getList());//挂起
            map.put("cancelled", prodClsUtils.getList());//撤销
            map.put("progress", sysCodeDtlService.optionGet("R_PROG"));//进度

            map.put("srcDocType", new ArrayList<OptionVo>() );//原始单据类别

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][option] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 类别相关数据查询
     *
     * @return
     */
    @RequestMapping(value = "/rgoAutolist", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult rgoAutolist(@RequestBody RgoType vo) {
        try {
            RgoType rgo = rgoTypeService.selectByRgoAuto(vo.getRgoType());

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, rgo);
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][rgoAutolist] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_PSC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][rgoAutolist] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_PSC_R, rgoCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 更换类别，调出方，调入方时删除明细 更新主表数据
     * @param vo
     * @param re
     * @return
     */
    @RequestMapping(value = "/wipeData", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult wipeData(@RequestBody RgoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            List<RgoDtl> list = rgoDtlService.selectByRgo(vo);
            if(list!=null && list.size()>0){
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,rgoDtlService.deleteRgoDtl(list));
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,"");

        } catch (ServiceException ex) {
            log.error("[SalesRgoController][wipeData] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][wipeData] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_D, CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 查询调配单必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getRgoFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getRgoFile(@RequestBody RgoType vo) {
        try {
            if(StringUtils.isNotEmpty(vo.getRgoType())){
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("RGO",vo.getRgoType()));
            }else{
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("RGO","SALE_RGO"));
            }

        } catch (Exception ex) {
            log.error("[SalesRgoController][getRgoFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 不能直接修改字段
     *
     * @return
     */
    @RequestMapping(value = "/updateNotFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateNotFile(@RequestBody RgoType vo) {
        try {

            List<TableFile> at = new ArrayList<>();
            List<String> list = null;
            if(vo.getRgoType()!=null){
                list = fieldUtils.isNotUpdateWareh(vo.getRgoType());
            }else{
                list = fieldUtils.isNotUpdateWareh("SALE_RGO");
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
            log.error("[SalesRgoController][updateNotFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询是否固定单价
     * @param vo
     * @param re
     * @return
     */
    @RequestMapping(value = "/getFixedPrice", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getFixedPrice(@RequestBody RgoVo vo, HttpServletRequest re) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, rgoService.getFixedPrice(vo));
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][getFixedPrice] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_FIXED, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][getFixedPrice] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_FIXED, rgoCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 调配单中确认单据
     *
     * @return
     */
    @RequestMapping(value = "/confirm", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult confirm(@RequestBody RgoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLCONFIRM, rgoService.confirm(rgoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][confirm] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][confirm] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_CONFIRM, rgoCon+CommonFainl.BILLCONFIRMFIANL, "");
        }
    }

    /**
     * 调配单中重做单据
     *
     * @return
     */
    @RequestMapping(value = "/redo", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult redo(@RequestBody RgoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREDO, rgoService.redo(rgoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][redo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][redo] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REDO, rgoCon+CommonFainl.BILLREDOFIANL, "");
        }
    }

    /**
     * 调配单中审核单据
     *
     * @return
     */
    @RequestMapping(value = "/examine", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult examine(@RequestBody RgoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLEXAMINE, rgoService.examine(rgoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][examine] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][examine] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_EXAMINE, rgoCon+CommonFainl.BILLEXAMINEFIANL, "");
        }
    }

    /**
     * 调配单中重审单据
     *
     * @return
     */
    @RequestMapping(value = "/reiterate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult reiterate(@RequestBody RgoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLREITERATE, rgoService.reiterate(rgoService.selectByOnly(vo), sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][reiterate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][reiterate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_REITERATE, rgoCon+CommonFainl.BILLREITERATEFIANL, "");
        }
    }

    /**
     * 调配单中挂起单据
     *
     * @return
     */
    @RequestMapping(value = "/hangUp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult hangUp(@RequestBody RgoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLHANGUP, rgoService.hangUp(rgoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][hangUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][hangUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_HANGUP, rgoCon+CommonFainl.BILLHANGUPFIANL, "");
        }
    }

    /**
     * 调配单中恢复单据
     *
     * @return
     */
    @RequestMapping(value = "/recovery", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult recovery(@RequestBody RgoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLRECOVERY, rgoService.recovery(rgoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][recovery] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][recovery] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_RECOVERY, rgoCon+CommonFainl.BILLRECOVERYFIANL, "");
        }
    }

    /**
     * 调配单中作废单据
     *
     * @return
     */
    @RequestMapping(value = "/toVoid", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult toVoid(@RequestBody RgoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.BILLTOVOID, rgoService.toVoid(rgoService.selectByOnly(vo),sysUser));
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][toVoid] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][toVoid] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_BILL_TOVOID, rgoCon+CommonFainl.BILLTOVOIDFIANL, "");
        }
    }

    /**
     * 调配单可操作状态
     *
     * @return
     */
    @RequestMapping(value = "/rgoAuditType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult rgoAuditType(@RequestBody RgoVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, rgoService.initButtons(vo));
        } catch (ServiceException ex) {
            log.error("[SalesRgoController][rgoAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesRgoController][rgoAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, rgoCon + CommonFainl.SELECTFIANL, "");
        }
    }

}
