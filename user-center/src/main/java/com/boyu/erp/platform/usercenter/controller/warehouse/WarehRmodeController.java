package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRmode;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.WarehRmodeMode;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehRmodeService;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 类描述:
 *
 * @Description: 入库方式控制层
 * @auther: CLF
 * @date: 2019/10/22 14:59
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/rmode")
public class WarehRmodeController extends BaseController {
    private final String tableName = "wareh_rmode";
    private static final String Operations = "WarehRmodeController|";
    @Autowired
    private WarehRmodeService warehRmodeService;

    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService codeDtlService;

    /**
     * 查询仓入库方式
     *
     * @param warehRmode
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult warehRmode(@RequestBody WarehRmode warehRmode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehRmodeService.selectWarehRomde(warehRmode));
        } catch (ServiceException ex) {
            log.error("[WarehRmodeService][selectWarehRomde] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehRmodeController][warehRmode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, CommonFainl.SELECTFIANL, "");
        }
    }

    @SystemLog(name = "增加入库方式")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addWarehRmode(@RequestBody WarehRmodeMode warehRmode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.ADD, warehRmode.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, warehRmodeService.insertWarehRomde(warehRmode));
        } catch (ServiceException ex) {
            log.error("[WarehRmodeService][insertWarehRomde] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehRmodeController][updateWarehRmode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, CommonFainl.ADDFIANL, "");
        }
    }

    @SystemLog(name = "删除入库方式")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteWarehRmode(@RequestBody WarehRmodeMode warehRmode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.DELETE, warehRmode.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, warehRmodeService.deleteWarehRomde(warehRmode));
        } catch (ServiceException ex) {
            log.error("[WarehRmodeService][deleteWarehRomde] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehRmodeController][deleteWarehRmode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, CommonFainl.DELETEFANS, "");
        }
    }

    @SystemLog(name = "修改入库方式")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateWarehRmode(@RequestBody WarehRmodeMode warehRmode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.UPDATE, warehRmode.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, warehRmodeService.updateWarehRomde(warehRmode));
        } catch (ServiceException ex) {
            log.error("[WarehRmodeService][updateWarehRomde] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehRmodeController][updateWarehRmode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 功能描述: 变更操作查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/22 17:02
     */
    @RequestMapping(value = "/changeFunctionList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult changeFunctionList(@RequestBody Wareh warehRmode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehRmodeService.changeFunctionList(warehRmode));
        } catch (ServiceException ex) {
            log.error("[WarehRmodeService][changeFunctionList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehRmodeController][changeFunctionWarehRmode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 功能描述: 更改变更操作
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/22 17:02
     */
    @RequestMapping(value = "/changeFunctionUpdate", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult changeFunctionUpdate(@RequestBody Wareh warehRmode, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = privUtils.isUnitPriv("WarehDefaultProperty", "更改仓库默认值", warehRmode.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, warehRmodeService.changeFunctionUpdate(warehRmode));
        } catch (ServiceException ex) {
            log.error("[WarehRmodeService][changeFunctionUpdate] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehRmodeController][changeFunctionUpdate] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 功能描述: 入库方式下拉初始化
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/22 15:28
     */
    @RequestMapping(value = "/options", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult warehRmodeoptions() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            List<OptionVo> list = prodClsUtils.getList();
            map.put("All", list);
            map.put("rcvMode", codeDtlService.optionGet("RCV_MODE"));
            map.put("diffCtrl", codeDtlService.optionGet("CMP_TYPE"));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, map);
        } catch (ServiceException ex) {
            log.error("[WarehRmodeService][updateWarehRomde] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehRmodeController][warehRmodeoptions] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH, CommonFainl.UPDATEFANS, "");
        }
    }

}
