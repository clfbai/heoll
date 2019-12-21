package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@RestController
@RequestMapping("/commondeliv")
public class CommonDelivController extends BaseController {
    @Autowired
    private SysUnitService sysUnitService;

    /**
     * 仓库编号弹窗
     * @author HHe
     */
    @Transactional(readOnly = true)
    @RequestMapping(value = "/warehcodepopup",method = RequestMethod.GET)
    public JsonResult warehCodePopUp(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                     @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                     UnitOptionVo vo, HttpServletRequest request) {
        try {
            //查看是否登录
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            vo.setOwnerId(sysUser.getOwnerId());
            vo.setUnitType("WH");
            PageInfo<UnitOptionVo> list = sysUnitService.selectByOption(vo, page, size);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, list);
        } catch (ServiceException e) {
            log.error("[WarehDelivTaskController][warehcodepopup] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE_DELIVTASKT_PENDING_Q, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[WarehDelivTaskController][warehcodepopup] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE_DELIVTASKT_PENDING_Q, "出库任务仓库弹框" + CommonFainl.SELECTFIANL, null);
        }
    }

    /**
     * 收货方编号弹框
     * @author HHe
     */
    @Transactional(readOnly = true)
        @RequestMapping(value = "/rcvpopup", method = RequestMethod.GET)
    public JsonResult rcvPopUp(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                               @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                               UnitOptionVo vo, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            vo.setOwnerId(this.getSessionSysUser(re).getDomain().getUnitId());
            vo.setUnitId(this.getSessionSysUser(re).getDomain().getUnitId());
            vo.setUnitType("VD");
            PageInfo<UnitOptionVo> list;
            if(StringUtils.isNotEmpty(vo.getUnitType())){
                list = sysUnitService.selectByOption(vo,page,size,sysUser);
            }else{
                list = new PageInfo<>();
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, list);
        } catch (ServiceException ex) {
            log.error("[WarehDelivTaskController][rcvPopUp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_UNIT_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehDelivTaskController][rcvPopUp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_UNIT_R, "收货方编号" + CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 根据Id查询用户的仓库
     * @author HHe
     */
    @Transactional(readOnly = true)
    @RequestMapping(value = "/queryrcvwarehbyrcvunitid",method = RequestMethod.GET)
    public JsonResult queryRcvWarehByRcvUnitId(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                               @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                               UnitOptionVo vo){
        try {
            vo.setUnitType("WH");
            PageInfo<UnitOptionVo> list = sysUnitService.selectByOption(vo, page, size);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, list);
        } catch (ServiceException ex) {
            log.error("[WarehDelivTaskController][queryRcvWarehByRcvUnitId] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_UNIT_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehDelivTaskController][queryRcvWarehByRcvUnitId] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_UNIT_R, "查询用户的仓库" + CommonFainl.SELECTFIANL, "");
        }
    }

}
