package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.TArea;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.TAreaServcie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/tarea")
public class TAreaController extends BaseController {
    @Autowired
    private TAreaServcie tAreaServcie;

    /**
     * 查询省市区
     *
     * @param tArea
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getTAreaList(@RequestBody TArea tArea, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", tAreaServcie.getTArea(tArea));
        } catch (ServiceException ex) {
            log.error("[TAreaServcie][getTArea] exception", ex);
            return new JsonResult("600800", ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TAreaController][getTAreaList] exception", ex);
            return new JsonResult("600800", "查询省市数据异常", "");
        }
    }

}
