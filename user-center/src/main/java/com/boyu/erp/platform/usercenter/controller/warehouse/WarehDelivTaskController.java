package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.WarehDelivTaskFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehDelivTaskModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehDelivTaskService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehUgService;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.utils.warehouse.DeliveryDefineUtil;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GdnModelVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehDelivTaskVO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 出库任务服务
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/delivtask")
public class WarehDelivTaskController extends BaseController {
    private static final String TABLE = "wareh_deliv_task|";
    private static final String OPERATIONS = "WarehDelivTaskController|";
    @Autowired
    private WarehDelivTaskService warehDelivTaskService;
    @Autowired
    private WarehUgService warehUgService;
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private DelivUtil delivUtil;



    /**
     * 出库任务下拉
     *
     * @return
     */
    @RequestMapping(value = "/delivtaskpulldown", method = RequestMethod.POST)
    public JsonResult delivTaskPullDown(@RequestBody WarehDelivTaskFilterModel warehDelivTaskFilterModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            Map<String, Object> map = warehDelivTaskService.delivTaskPullDown(warehDelivTaskFilterModel.getSUnitId(), sysUser);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException e) {
            log.error("[WarehDelivTaskController][delivTaskPullDown] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[WarehDelivTaskController][delivTaskPullDown] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 根据组织分组加载仓库分组
     *
     * @author HHe
     * @date 2019/9/5 10:32
     */
    @RequestMapping(value = "/loadwarehug", method = RequestMethod.GET)
    public JsonResult loadWarehUg(Long[] sysUgIds) {
        Map<String, Object> map = warehUgService.loadWarehUg(Arrays.asList(sysUgIds));
        return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
    }

    /**
     * 查询任务列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JsonResult getDelivTasklist(WarehDelivTaskFilterModel warehDelivTaskFilterModel,@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size, HttpServletRequest request) {
        try {
            //查看是否登录
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            //判断权限
//            PrivIdExamine examine = privUtils.isUnitPriv(TABLE,OPERATIONS,"QUERYALL",warehDelivTaskFilterModel.getUnitId(),sysUser);
//            if(!examine.getPrivBoolean()){
//                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
//            }

            PageInfo<WarehDelivTaskVO> taskVOs = warehDelivTaskService.selectAll(warehDelivTaskFilterModel, page, size, sysUser);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, taskVOs);
        } catch (ServiceException e) {
            log.error("[WarehDelivTaskController][getPendinglist] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE_DELIVTASKT_PENDING_Q, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[WarehDelivTaskController][getPendinglist] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE_DELIVTASKT_PENDING_Q, "出库任务" + CommonFainl.SELECTFIANL, null);
        }
    }

    /**
     * 查询列表总金额、总数量
     *
     * @author HHe
     * @date 2019/11/18 17:10
     */
    @RequestMapping(value = "/getlisttotal", method = RequestMethod.POST)
    public JsonResult getListTotal(@RequestBody WarehDelivTaskFilterModel warehDelivTaskFilterModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehDelivTaskService.getListTotal(warehDelivTaskFilterModel));
        } catch (ServiceException e) {
            log.error("[WarehDelivTaskController][getListTotal] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[WarehDelivTaskController][getListTotal] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }
//    /**
//     * 查询出库任务列表（PLAN2）
//     * @author HHe
//     * @date 2019/9/4 15:31
//     */
//    @RequestMapping(value = "/listgoods", method = RequestMethod.POST)
//    public JsonResult getDelivTasklist(@RequestBody WarehDelivTaskFilterModel warehDelivTaskFilterModel, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size, HttpServletRequest request) {
//        try {
//            //查看是否登录
//            SysUser sysUser = (SysUser) this.isNullUser(request);
//            if (sysUser == null) {
//                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
//            }
////            delivTaskVO.setUnitId(sysUser.getOwnerId());
//            PageInfo<WarehDelivTaskVO> taskVOs = warehDelivTaskService.selectListByFilter(warehDelivTaskFilterModel, page, size, sysUser);
//            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, taskVOs);
//        } catch (ServiceException e) {
//            log.error("[WarehDelivTaskController][getPendinglist] ServiceException", e);
//            return new JsonResult(JsonResultCode.FAILURE_DELIVTASKT_PENDING_Q, e.getMessage(), "");
//        } catch (Exception e) {
//            log.error("[WarehDelivTaskController][getPendinglist] Exception", e);
//            return new JsonResult(JsonResultCode.FAILURE_DELIVTASKT_PENDING_Q, "出库任务" + CommonFainl.SELECTFIANL, null);
//        }
//    }
    /**
     * 添加出库任务
     *
     * @param delivTask
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResult addDelivTask(@RequestBody WarehDelivTask delivTask, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
//            PrivIdExamine examine = privUtils.isUnitPriv(TABLE,OPERATIONS,CommonFainl.ADD,delivTask.getUnitId(),sysUser);
//            if(!examine.getPrivBoolean()){
//                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
//            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, warehDelivTaskService.addDelivTask(delivTask, sysUser));
        } catch (ServiceException e) {
            log.error("[WarehDelivTaskController][addDelivTask] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE_DELIVTASKT_I, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[WarehDelivTaskController][addDelivTask] Exception", e);
            return new JsonResult(JsonResultCode.FAILURE_DELIVTASKT_I, "出库任务" + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 生成出库单（可多条任务生成出库单）
     *
     * @return<WarehDelivTaskVO> delivTaskVOs
     */
    @RequestMapping(value = "/creategdn", method = RequestMethod.POST)
    public JsonResult addGdn4Task(@RequestBody WarehDelivTaskModel warehDelivTaskModel, HttpServletRequest request) {
        try {
            SysUser sysUser = this.isNullUser(request);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
//            PrivIdExamine examine = privUtils.isUnitPriv(TABLE,OPERATIONS,"ADDGDN",warehDelivTaskModel.getUnitId(),sysUser);
//            if(!examine.getPrivBoolean()){
//                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
//            }
            if (warehDelivTaskModel.getWarehDelivTaskList().size() > 0) {
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.GENERATE_SUCC, warehDelivTaskService.addGdn4Task1(warehDelivTaskModel, sysUser));
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "请选择出库任务", "");
            }
        } catch (ServiceException e) {
            log.error("[WarehDelivTaskController][addgdn] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, e.getMessage(), "");
        } catch (Exception e) {
            log.error("[WarehDelivTaskController][addgdn] ServiceException", e);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.GENERATE_FIANL, "");
        }
    }

    /**
     * 出库任务页面组合显示列表数据功能
     *
     * @author HHe
     * @date 2019/11/25 15:13
     */
    @RequestMapping(value = "/pageshowgroup", method = RequestMethod.GET)
    public JsonResult pageShowGroup(String type) {
        List<OptionVo> optionVoList = new ArrayList<>();
        if (DeliveryDefineUtil.GROUP_RCVUNIT.equals(type)) {
            //收货方
            optionVoList.add(new OptionVo(null, "taskDocNum"));
            optionVoList.add(new OptionVo(null, "taskDocTypeCp"));
            optionVoList.add(new OptionVo(null, "taskDocDate"));
            optionVoList.add(new OptionVo(null, "joinTime"));
            optionVoList.add(new OptionVo(null, "rcvWarehNum"));
            optionVoList.add(new OptionVo(null, "rcvWarehName"));
            optionVoList.add(new OptionVo(null, "multiImplCp"));
            optionVoList.add(new OptionVo(null, "implTimes"));
            optionVoList.add(new OptionVo(null, "suspendedCp"));
            optionVoList.add(new OptionVo(null, "remarks"));
        } else if (DeliveryDefineUtil.GROUP_RCVWAREH.equals(type)) {
            //收货仓库
            optionVoList.add(new OptionVo(null, "taskDocNum"));
            optionVoList.add(new OptionVo(null, "taskDocTypeCp"));
            optionVoList.add(new OptionVo(null, "taskDocDate"));
            optionVoList.add(new OptionVo(null, "joinTime"));
            optionVoList.add(new OptionVo(null, "multiImplCp"));
            optionVoList.add(new OptionVo(null, "implTimes"));
            optionVoList.add(new OptionVo(null, "suspendedCp"));
            optionVoList.add(new OptionVo(null, "remarks"));
        }
        return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, optionVoList);
    }

    @RequestMapping("/getarr")
    public String getarr(Long[] ids) throws Exception {
//        String a = "";
        String attr = delivUtil.Attr(new GdnModelVO());
//        for (Long id : ids) {
//            a += id;
//        }
        return attr;
//        return delivUtil.Attr(new GdnModelVO());
    }
}
