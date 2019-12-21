package com.boyu.erp.platform.usercenter.controller.option;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import com.boyu.erp.platform.usercenter.model.system.SysPrsnlModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysPrsnlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description: 人员弹窗查询控制层
 * @auther: CLF
 * @date: 2019/7/19 14:13
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class PrsnlOptionController extends BaseController {
    private static final String psn = "人员";
    @Autowired
    private SysPrsnlService prsnlService;
    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private ProdClsUtils prodClsUtils;

    /**
     * 功能描述: 查询登录用户所在组织下用户
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/27 15:15
     */
    @RequestMapping(value = "/getOption/prsnl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOptionPrsnl(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                     @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                     SysPrsnl prsnl, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prsnlService.getOpetionPrsin(page, size, prsnl, this.getSessionSysUser(request)));
        } catch (Exception ex) {
            log.error("[ProductCatController][getOptionPrsnl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_PRSNL, psn + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 功能描述: 查询组织下人员(可传筛选组织)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/27 15:15
     */
    @RequestMapping(value = "/getOwner/prsnl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOwnerPrsnl(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                    @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                    SysPrsnlModel prsnl, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, prsnlService.getOwnerPrsnl(page, size, prsnl, this.getSessionSysUser(request)));
        } catch (Exception ex) {
            log.error("[ProductCatController][ getOwnerPrsnl(] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_PRSNL, psn + CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 功能描述: 查询员工下拉
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/27 15:15
     */
    @RequestMapping(value = "/getOwner/EmpOption", method = {RequestMethod.POST})
    public JsonResult getOwnerPrsnlEmpOptions() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            //学历
            map.put("acadDeg", codeDtlService.optionGet("ACAD_DEG"));
            //民族
            map.put("folk", codeDtlService.optionGet("FOLK"));
            //婚姻状况
            map.put("mrtStatus", codeDtlService.optionGet("MRT_STATUS"));
            //政治面貌
            map.put("party", codeDtlService.optionGet("PARTY"));
            //职称
            map.put("techTtl", codeDtlService.optionGet("TECH_TTL"));
            //员工状态
            map.put("emplStatus", codeDtlService.optionGet("PRSNL_STATUS"));
            //奖惩类别
            map.put("rwdPunType", codeDtlService.optionGet("RWD_PUN_TYPE"));
            //家庭关系
            map.put("rwdPunType", codeDtlService.optionGet("FAM_COMP"));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[ProductCatController][ getOwnerPrsnl(] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_PRSNL, psn + CommonFainl.SELECTFIANL, "");
        }
    }

}
