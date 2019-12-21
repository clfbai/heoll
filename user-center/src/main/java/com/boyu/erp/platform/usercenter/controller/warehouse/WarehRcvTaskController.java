package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.WarehOptinModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehRcvTaskGrnModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehRcvTaskModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehOptionSerivce;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehRcvTaskSerivce;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehUnitOptionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 入库任务控制层
 * @auther: CLF
 * @date: 2019/7/18 16:28
 */
@Slf4j
@RestController
@RequestMapping("/warehouse")
public class WarehRcvTaskController extends BaseController {
    @Autowired
    private OperationAuthorityUtils privUtils;
    @Autowired
    private WarehRcvTaskSerivce warehRcvTaskSerivce;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private WarehOptionSerivce warehOptionSerivce;
    @Autowired
    private WarehService warehService;
    private static final String pnc = "入库任务";
    private static final String pncDtl = "入库任务明细";

    /**
     * 查询入库任务列表
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/rcvtask/all", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult warehAll(WarehRcvTaskModel vo,
                               @RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                               @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                               HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (vo.getUnitId() == null) {
                //默认查询当前用户组织
                vo.setUnitId(user.getOwnerId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehRcvTaskSerivce.getWarehRcvTask(page, size, vo, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[warehService][getWarehRcvTask] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK, pnc + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询入库任务明细
     * 1.获取明细数据写入对应表
     * 2. 查询入库仓库对应控制信息
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/rcvtask/findById", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult warehfindById(WarehRcvTaskModel vo,
                                    @RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                    @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                    HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehRcvTaskSerivce.getWarehRcvTaskDtl(page, size, vo, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[warehService][getWarehRcvTaskDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_DTL, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehfindById] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_DTL, pncDtl + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 增加入库任务
     * 判断哪些商品需要进行库存管理，哪些商品没有库存管理
     *
     * @param vo
     *
     * @return
     */
    @RequestMapping(value = "/rcvtask/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult warehRcvTaskAdd(@RequestBody WarehRcvTask vo, HttpServletRequest request) {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehRcvTaskSerivce.addWarehRcvTask(vo, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[warehService][addWarehRcvTaskDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehRcvTaskAdd] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, pnc + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 入库任务生成入库单
     * 1.推送取消入库单(C_WMS接口)
     *
     * @param model
     * @return
     */
    @SystemLog(name = "入库任务生成入库单")
    @RequestMapping(value = "/rcvtask/add/grn", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult warehRcvTaskAddGrn(@RequestBody WarehRcvTaskGrnModel model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);

            if (CollectionUtils.isNotEmpty(model.getList())) {
                for (WarehRcvTask w : model.getList()) {
                    PrivIdExamine examine = privUtils.isUnitPriv("rcvtaskCreateGrn", "入库任务生成入库单", w.getUnitId(), user);
                    if (!examine.getPrivBoolean()) {
                        return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), w);
                    }
                    if (CommonFainl.TRUE.equalsIgnoreCase(w.getSuspended())) {
                        return new JsonResult(JsonResultCode.FAILURE, "单据已挂起不能生成入库任务", 0);
                    }
                    if (w.getWarehId() == null && model.getDefaultWarehId() == null) {
                        return new JsonResult(JsonResultCode.FAILURE, "请选择缺省仓库", 0);
                    }
                    if (w.getWarehId() == null && model.getDefaultWarehId() != null) {
                        w.setWarehId(model.getDefaultWarehId());
                        //修改入库仓库Id
                        warehRcvTaskSerivce.updateWarehRcvTask(w);
                    }
                    warehRcvTaskSerivce.WarehRcvTaskGrn(w, user);
                }
                return new JsonResult(JsonResultCode.SUCCESS, "生成入库单成功", 0);
            }
            return new JsonResult(JsonResultCode.FAILURE, "请选择入库任务", 0);
        } catch (ServiceException ex) {
            log.error("[warehService][addGrn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehRcvTaskAddGrn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, "生成入库单异常", "");
        }
    }


    /**
     * 组合方式查询(查询屏蔽字段)
     */
    @PostMapping(value = "/rcvtask/shield")
    public JsonResult warehRcvTaskshield(@RequestBody WarehRcvTaskModel vo, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (vo.getGroup() == null) {
                vo.setGroup(0);
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehRcvTaskSerivce.selectWarehRcvTaskShield(vo.getGroup()));
        } catch (ServiceException ex) {
            log.error("[warehService][selectWarehRcvTaskShield] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehRcvTaskshield] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, "入库单组合模式查询失败", "");
        }
    }


    @RequestMapping(value = "/rcvtask/money", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult warehRcvTasMoney(WarehRcvTaskModel vo, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehRcvTaskSerivce.selectWarehRcvTaskMoney(vo));
        } catch (ServiceException ex) {
            log.error("[WarehService][selectWarehRcvTaskMoney] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehRcvTasMoney] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, "入库任务金额查询失败", "");
        }
    }

    /**
     * 功能描述: 入库任务下拉
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/25 16:19
     */
    @RequestMapping(value = "/rcvtask/getOptin", method = {RequestMethod.POST})
    public JsonResult warehRcvTasOptin(HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            Map<String, Object> map = prodClsUtils.getMap();
            //挂起
            map.put("suspended", prodClsUtils.getList());
            //入库方式
            map.put("rcvMode", codeDtlService.optionGet("RCV_MODE"));
            PurKeyValue purs = new PurKeyValue("发货方", "1");
            PurKeyValue purs3 = new PurKeyValue("", "0");
            PurKeyValue purs2 = new PurKeyValue("发仓库", "2");
            List<PurKeyValue> list = new ArrayList<>();
            list.add(purs3);
            list.add(purs);
            list.add(purs2);
            map.put("group", list);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[warehService][selectWarehRcvTaskShield] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehRcvTaskshield] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, "入库单组合模式查询失败", "");
        }
    }

    /**
     * 功能描述: 入库任务弹窗
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/25 16:19
     */
    @GetMapping(value = "/rcvtask/getWindows")
    public JsonResult warehRcvTasWindows(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                         @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                         @Validated WarehOptinModel model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            if (model.getUnitType().equalsIgnoreCase("VD")) {
                WarehUnitOptionVo vo = new WarehUnitOptionVo();
                BeanUtils.copyProperties(model, vo, "inputCode", "unitStatus", "unitNum", "unitName");
                vo.setOwnerId(model.getUnitId());
                return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehOptionSerivce.getDelivOption(page, size, vo, user));
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehService.getOptinCode(page, size, model, user));
        } catch (ServiceException ex) {
            log.error("[warehService][selectWarehRcvTaskShield] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehController][warehRcvTaskshield] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RCVTASK_1, "入库单组合模式查询失败", "");
        }
    }


}
