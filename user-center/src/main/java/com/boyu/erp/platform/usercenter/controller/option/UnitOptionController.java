package com.boyu.erp.platform.usercenter.controller.option;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述:  组织弹窗下拉公用
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/27 9:49
 */
@Slf4j
@RestController
@RequestMapping("/unit/option")
public class UnitOptionController extends BaseController {
    @Autowired
    private SysUnitService unitService;

    /**
     * 类描述:  根据属主Id查询组织弹窗
     *
     * @Description:
     * @auther: CLF
     * @date: 2019/8/27 10:04
     */
    @GetMapping(value = "/getOwnerId")
    public JsonResult getOptionUnit(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                    @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                     SysUnit unit, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, unitService.selectOptionUnit(page, size, unit, this.getSessionSysUser(request)));
        } catch (Exception ex) {
            log.error("[ProductCatController][getOptionUnit] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_PRSNL, "组织弹窗信息查询失败", "");
        }
    }


}
