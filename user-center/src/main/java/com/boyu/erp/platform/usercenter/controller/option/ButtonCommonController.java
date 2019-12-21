package com.boyu.erp.platform.usercenter.controller.option;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述:
 *
 * @Description: 按钮初始化控制层
 * @auther: CLF
 * @date: 2019/9/29 11:07
 */
@Slf4j
@RestController
@RequestMapping("/sys/button")
public class ButtonCommonController extends BaseController {

    @Autowired
    private ButtonCommonService buttonCommonService;


    /**
     * 查询商品品种模块按钮
     *
     * @param
     * @return
     */
    @PostMapping(value = "/prodCls")
    public JsonResult prodClsButton(@RequestBody SysParameter parameter, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, buttonCommonService.initButton(parameter.getParmId()));
        } catch (ServiceException ex) {
            log.error("[ShopServicer][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopController][shopList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询门店信息失败", "");
        }
    }

}
