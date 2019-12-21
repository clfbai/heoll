package com.boyu.erp.platform.usercenter.controller.ca;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.caservice.CaService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname CaController
 *  往来账户控制层
 * @Description TODO
 * @Date 2019/9/28 11:02
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/ca/ca")
public class CaController extends BaseController {

    private static final String caCon = "往来账户";

    @Autowired
    private CaService caService;
    @Autowired
    private SysUnitService sysUnitService;

    /**
     * 往来账户查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult caList(CaVo vo, @RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "15") Integer size,
                             HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            PageInfo<CaVo> resultList = caService.selectAll(vo, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[CaController][caList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CaController][caList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, caCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加往来账户
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加往来账户")
    @RequestMapping(value = "/addCa", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addCa(HttpServletRequest re, @RequestBody CaVo vo) {
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

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, caService.insertCa(vo, user));

        } catch (ServiceException ex) {
            log.error("[CaController][addCa] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CaController][addCa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_C, caCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改往来账户
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改往来账户")
    @RequestMapping(value = "/updateCa", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateCa(HttpServletRequest re, @RequestBody CaVo vo) {
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

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, caService.updateCa(vo, user));

        } catch (ServiceException ex) {
            log.error("[CaController][updateCa] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CaController][updateCa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_U, caCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除往来账户
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除往来账户")
    @RequestMapping(value = "/deleteCa", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteCa(HttpServletRequest re, @RequestBody CaVo vo) {
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

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, caService.deleteCa(vo, user));

        } catch (ServiceException ex) {
            log.error("[CaController][deleteCa] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CaController][deleteCa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_D, caCon+CommonFainl.DELETEFANS, "");
        }
    }

    @SystemLog(name = "判断是否可以删除/修改")
    @RequestMapping(value = "/isUpdateOrDelete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult isUpdateOrDelete(@RequestBody CaVo vo) {
        try {

            Map<String, Object> map = new HashMap<>();
            int delete = 0;
            BigDecimal big = new BigDecimal(0);
            if (vo.getDbBal().compareTo(big)==0&&vo.getCrBal().compareTo(big)==0&&vo.getDbMemoBal().compareTo(big)==0&&
                vo.getCrMemoBal().compareTo(big)==0&&vo.getDbAccBal().compareTo(big)==0&&vo.getCrAccBal().compareTo(big)==0&&
                vo.getDbFrzBal().compareTo(big)==0&&vo.getCrFrzBal().compareTo(big)==0&&vo.getRvaBal().compareTo(big)==0&&
                vo.getPyaBal().compareTo(big)==0&&vo.getDbInBase().compareTo(big)==0&&vo.getCrInBal().compareTo(big)==0) {
                delete = 1;
            }
            map.put("delete",delete);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[CaController][isUpdateOrDelete] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CaController][isUpdateOrDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, caCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 往来账户组织弹窗
     * @param vo
     * @param page
     * @param size
     * @param re
     * @return
     */
    @RequestMapping(value = "/unitOption", method = {RequestMethod.GET})
    public JsonResult unitOption(UnitOptionVo vo, @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, sysUnitService.selectByCaOption(vo, page, size, sysUser));
        } catch (ServiceException ex) {
            log.error("[CaController][unitOption] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_UNIT_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CaController][unitOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_UNIT_R, caCon+CommonFainl.SELECTFIANL, "");
        }
    }


}
