package com.boyu.erp.platform.usercenter.controller.collarUse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.collarUse.ArqType;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.collarUseservice.ArqService;
import com.boyu.erp.platform.usercenter.service.collarUseservice.ArqTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.collarUse.ArqVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: CollarUseArqController
 * @description: 领用单控制层
 * @author: wz
 * @create: 2019-8-23 12:11
 */
@Slf4j
@RestController
@RequestMapping("/collarUse/arq")
public class CollarUseArqController extends BaseController {
    private static final String arqCon = "领用单";

    @Autowired
    private ArqService arqService;

    @Autowired
    private ProdClsUtils prodClsUtils;

    @Autowired
    private SysCodeDtlService sysCodeDtlService;

    @Autowired
    private ArqTypeService arqTypeService;

    @Autowired
    private FieldUtils fieldUtils;


    /**
     * 弹窗
     * 部门：DP   当前组织id
     * 发货仓：WH  当前组织id
     */

    /**
     * 领用单查询
     * @param vo
     * @param page
     * @param size
     * @param re
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult arqList(ArqVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            vo.setUnitId(sysUser.getDomain().getUnitId());

            PageInfo<ArqVo> resultList = arqService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[CollarUseArqController][arqList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CollarUseArqController][arqList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, arqCon+CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 添加领用单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加领用单")
    @RequestMapping(value = "/addArq", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addArq(HttpServletRequest re, @RequestBody ArqVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, arqService.insertArq(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[CollarUseArqController][addArq] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CollarUseArqController][addArq] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_C, arqCon+CommonFainl.ADDFIANL, "");
        }
    }


    /**
     * 修改领用单
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改领用单")
    @RequestMapping(value = "/updateArq", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateArq(HttpServletRequest re, @RequestBody ArqVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }


            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, arqService.updateArq(vo, sysUser));


        } catch (ServiceException ex) {
            log.error("[CollarUseArqController][updateArq] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CollarUseArqController][updateArq] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_U, arqCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除采购合同
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除领用单")
    @RequestMapping(value = "/deleteArq", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteArq(HttpServletRequest re, @RequestBody ArqVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, arqService.deleteArq(vo));


        } catch (ServiceException ex) {
            log.error("[CollarUseArqController][deleteArq] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CollarUseArqController][deleteArq] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_D, arqCon+CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 判断是否可以删除/修改
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "判断是否可以删除/修改")
    @RequestMapping(value = "/isUpdateOrDelete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult isUpdateOrDelete(@RequestBody ArqVo vo) {
        try {
            Map<String, Object> map = new HashMap<>();
            int update = 0;
            int delete = 0;

            //要求状态 PROGRESS = PENDING && SUSPENDED = FALSE && CANCELLED = FALSE 才能修改 否则不行
            if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                update = 1;
            }

            //状态要求：PROGRESS IN (PENDING, CONFIRMED) && SUSPENDED = FALSE && CANCELLED = FALSE
            if ((vo.getProgress().equals("PG") || vo.getProgress().equals("C")) &&
                    vo.getSuspended().equals("F") &&
                    vo.getCancelled().equals("F")) {
                delete = 1;
            }

            map.put("update",update);
            map.put("delete",delete);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[PurchasePucController][deletePuc] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucController][deletePuc] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, arqCon+CommonFainl.SELECTFIANL, "");
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

            map.put("arqType", arqTypeService.optionGet());//领用单类别
            map.put("prcSite", sysCodeDtlService.optionGet("ARQ_RSN"));//领用原因
            map.put("bxiEnabled", prodClsUtils.getList());//启用配码
            map.put("progress", sysCodeDtlService.optionGet("PSC_PROG"));//进度
            map.put("effective", prodClsUtils.getList());//已生效
            map.put("suspended", prodClsUtils.getList());//挂起
            map.put("cancelled", prodClsUtils.getList());//撤销

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
     * 查询领用单必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getArqFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getArqFile(@RequestBody ArqType vo) {
        try {
            if(StringUtils.isNotEmpty(vo.getArqType())){
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("ARQ",vo.getArqType()));
            }else{
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("ARQ","ARQ"));
            }

        } catch (Exception ex) {
            log.error("[PurchasePucController][getArqFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

}



