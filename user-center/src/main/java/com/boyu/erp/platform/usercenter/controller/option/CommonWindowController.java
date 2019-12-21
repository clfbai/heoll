package com.boyu.erp.platform.usercenter.controller.option;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.utils.common.CommonWindowUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述:  通用弹窗类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/18 17:31
 */
@Slf4j
@RestController
@RequestMapping("/unit/option/common")
public class CommonWindowController extends BaseController {
    @Autowired
    private CommonWindowUtils commonWindowUtils;

    /**
     * 类描述:  根据属主Id查询组织弹窗
     *
     * @Description:
     * @auther: CLF
     * @date: 2019/8/27 10:04
     */
    @GetMapping(value = "/list")
    public JsonResult getOptionUnit(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                    @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                    SysUnit unit, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, "");
        } catch (Exception ex) {
            log.error("[CommonWindowController][getOptionUnit] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_SYS_PRSNL, "组织弹窗信息查询失败", "");
        }
    }


    /**
     * 类描述:  通用弹窗
     *
     * @Description:
     * @auther: CLF
     * @date: 2019/8/27 10:04
     */
    @PostMapping(value = "/single")
    public JsonResult singleOption(@RequestBody CommonWindowModel model, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            Object object=commonWindowUtils.getSingle(model,user);
            if ( object== null) {
                return new JsonResult(JsonResultCode.FAILURE, "没有您搜索到你要的信息", "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, object);
        } catch (Exception ex) {
            log.error("[CommonWindowController][singleOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "弹窗信息查询异常", "");
        }
    }





}
