package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/user")
public class ValidateCodeController extends BaseController
{
    /**
     * 验证码
     * @param request
     * @param response
     */
    @RequestMapping(value = "/validate/code",method = {RequestMethod.POST, RequestMethod.GET})
    public void validateCode(HttpServletRequest request, HttpServletResponse response){
        try
        {
            //防止有缓存,先清理再重新获取
            request.getSession().removeAttribute(GlobalConstants.SESSION_VALIDATE_CODE);
            ValidateCodeUtils.createImage(request,response);
        }catch (Exception e){
            log.error("[ValidateCodeController][validateCode] exception",e);
        }
    }
}
