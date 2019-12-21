package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.FieldAuthority;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.basic.Vender;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.purchase.VdrAttrModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.VdrAttrDefService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.VdrAttrService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.VenderService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.system.SysUnitUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.partner.VenVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VenderVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 采购商控制层
 * @author: wz
 * @create: 2019-06-26 09:19
 */
@Slf4j
@RestController
@RequestMapping("/purchase/vender")
public class PurchaseVenderController extends BaseController {

    private static final String venderCon = "供应商";
    //判断权限操作字段
    private final String tableName = "vender|";
    private final String tableAttrName = "vdr_attr|";
    private static final String Operations = "PurchaseVenderController|";
    @Autowired
    private VenderService venderService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private VdrAttrDefService vdrAttrDefService;
    @Autowired
    private VdrAttrService vdrAttrService;
    @Autowired
    private com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils fieldUtils;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private SysUnitUtils unitUtils;

    /**
     * 供应商查询
     *
     * @return
     */
    @FieldAuthority(name = "VENDER")
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public Object venderList(
            VenderVo vo, @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size,
            HttpServletRequest re) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if(StringUtils.NullEmpty(vo.getVdrStatus())){
                vo.setVdrStatus("A");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, venderService.selectAll(page, size, vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[PurchaseVenderController][venderList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][venderList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_R, venderCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加供应商
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/addVender", method = {RequestMethod.POST})
    public JsonResult addVender(HttpServletRequest re, @RequestBody VenderVo vo) {
        try {
            SysUser user = this.getSessionSysUser(re);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(vo.getUnitCode()) || org.apache.commons.lang3.StringUtils.isNotBlank(vo.getUnitName())) {
                if (unitUtils.isCodeAndName(vo.getUnitName(), vo.getUnitCode(), CommonFainl.ADD)) {
                    return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, venderCon + CommonFainl.ADDFIANL + "： 组织代码或组织名称已存在", "");
                }
            }
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.ADD, venderVo.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            vo.setOwnerId(vo.getsUnitId());
            //通过判断是否有供应商编号走新增或修改
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, venderService.insertVender(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchaseVenderController][addVender] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][addVender] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_C, venderCon + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改供应商
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/updateVender", method = {RequestMethod.POST})
    public JsonResult updateVender(HttpServletRequest re, @RequestBody VenderVo vo) {
        try {
            SysUser user = this.getSessionSysUser(re);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(vo.getUnitCode()) || org.apache.commons.lang3.StringUtils.isNotBlank(vo.getUnitName())) {
                if (unitUtils.isCodeAndName(vo.getUnitName(), vo.getUnitCode(), CommonFainl.UPDATE)) {
                    return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, venderCon + CommonFainl.UPDATEFANS + "： 组织代码或组织名称已存在", "");
                }
            }
            //判断是否有权限操作
           /* PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.UPDATE, venderVo.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, venderService.updateVender(vo, user));

        } catch (ServiceException ex) {
            log.error("[PurchaseVenderController][updateVender] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][updateVender] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_U, venderCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除供应商
     *
     * @param venderVo
     * @return
     */
    @SystemLog(name = "删除供应商")
    @RequestMapping(value = "/deleteVender", method = {RequestMethod.POST})
    public JsonResult deleteVender(HttpServletRequest re, @RequestBody VenderVo venderVo) {
        try {
            SysUser user = this.getSessionSysUser(re);
            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.DELETE, venderVo.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, venderService.deleteVender(venderVo, user));

        } catch (ServiceException ex) {
            log.error("[PurchaseVenderController][deleteVender] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][deleteVender] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_D, venderCon + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 自定义属性查询
     */
    @SystemLog(name = "自定义属性查询")
    @RequestMapping(value = "/vdrAttrList", method = {RequestMethod.POST})
    public JsonResult vdrAttrList(HttpServletRequest re, @RequestBody Vender ven) {
        try {
            SysUser user = this.getSessionSysUser(re);

            List<Map<String, Object>> list = vdrAttrService.vdrAttrList(ven);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, list);
        } catch (ServiceException ex) {
            log.error("[PurchaseVenderController][vdrAttrList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_VDR_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][vdrAttrList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_VDR_R, venderCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * @return 自定义属性
     */
    @RequestMapping(value = "/AttrAndName", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult AttrAndName() {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, vdrAttrDefService.findByAll());
        } catch (ServiceException ex) {
            log.error("[PurchaseVenderController][AttrAndName] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][AttrAndName] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, venderCon + CommonFainl.SELECTFIANL);
        }
    }

    /**
     * 自定义属性修改
     */
    @SystemLog(name = "修改供应商自定义属性")
    @RequestMapping(value = "/updateVdrAttr", method = {RequestMethod.POST})
    public JsonResult updateVdrAttr(HttpServletRequest re, @RequestBody VdrAttrModel vdrAttrModel) {
        try {
            SysUser user = this.getSessionSysUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, vdrAttrService.updateVdrAttr(vdrAttrModel));
        } catch (ServiceException ex) {
            log.error("[PurchaseVenderController][updateVdrAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_VDR_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][updateVdrAttr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_VDR_U, venderCon + CommonFainl.UPDATEFANS, "");
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

            map.put("licType", sysCodeDtlService.optionGet("LIC_TYPE"));//执照类别
            map.put("recruitable", prodClsUtils.getList());//可征募
            map.put("shared", sysCodeDtlService.optionGet("BOOLEA"));//是否共享
            map.put("unitStatus", sysCodeDtlService.optionGet("UNIT_STATUS"));//状态
            map.put("pcrLvl", sysCodeDtlService.optionGet("PCR_LVL"));//信用等级
            map.put("dfltDelivMthd", sysCodeDtlService.optionGet("DELIV_MTHD"));//缺省送货方式
            map.put("ptnrStatus", sysCodeDtlService.optionGet("PTNR_STATUS"));//伙伴状态
            map.put("psaCtlr", sysCodeDtlService.optionGet("PS_ROLE"));//协议控制方
            map.put("vdrStatus", sysCodeDtlService.optionGet("VDR_STATUS"));//供应商状态
            map.put("invoice", prodClsUtils.getList());//是否开票
            map.put("accountPeriod", sysCodeDtlService.optionGet("PAY_DATE"));//账期
            map.put("sdPlcyId", new ArrayList<PurKeyValue>());//供需策略

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, venderCon + CommonFainl.SELECTFIANL);
        }
    }

    /**
     * 根据所选择或者所输入的供应商代码  属主为当前组织id
     */
    @RequestMapping(value = "/venderByCode", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult venderByCode(@RequestBody VenderVo vo) {
        try {
            VenderVo vos = venderService.selectByCode(vo);
            //为空将传过来的对象返回
            if(vos==null){
                vos = vo;
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, vos);
        } catch (ServiceException ex) {
            log.error("[PurchaseVenderController][venderByCode] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_UNIT_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][venderByCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_UNIT_U, venderCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询供应商必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getVenderFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getVenderFile() {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("VENDER,SYS_UNIT,COMPANY,PARTNER,SYS_UNIT_OWNER"));
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][getVenderFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    @RequestMapping(value = "/updateNotFile", method = {RequestMethod.POST})
    public JsonResult updateNotFile(HttpServletRequest re,@RequestBody VenVo vo) {
        try {
            SysUser user = this.getSessionSysUser(re);
            List<TableFile> at = null;
            if(!org.springframework.util.StringUtils.isEmpty(vo.getCtrlUnitId())){
                if(!vo.getCtrlUnitId().equals(user.getUnit().getUnitId()+"")){
                    at = purchaseService.updateNotFile("VENDER",vo.getMethoyType());
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, at);
        } catch (ServiceException ex) {
            log.error("[PurchaseVenderController][updateNotFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVenderController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }
}



