package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user/session")
public class SessionController extends BaseController {

    @RequestMapping(value = "/getSessionUser", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getSessionUser(HttpServletRequest request) {
        try {
            SysUser sessionSysUser = this.getSessionSysUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", sessionSysUser);
        } catch (ServiceException ex) {
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SessionController][getSessionUser] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }
}
