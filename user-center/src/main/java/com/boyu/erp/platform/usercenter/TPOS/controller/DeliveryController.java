package com.boyu.erp.platform.usercenter.TPOS.controller;

import com.boyu.erp.platform.usercenter.TPOS.model.XmlResult;
import com.boyu.erp.platform.usercenter.TPOS.utils.JaxbUtil;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
/**
 * WMS出库请求
 * @author HHe
 * @date 2019/11/8 19:40
 */
@RestController
@Slf4j
public class DeliveryController {

    @RequestMapping("/stockout.confirm")
    public String stockoutConfirm(HttpServletRequest request, @RequestBody String requestData){
        JaxbUtil jaxbUtil = new JaxbUtil(XmlResult.class);
        try {
            //接收对象转换

            return jaxbUtil.toXml(new XmlResult("success",JsonResultCode.SUCCESS,CommonFainl.WMS_RECEIVE_SUCC),"utf-8");
        } catch (ServiceException e) {
            log.error("[DeliveryController][stockoutConfirm] ServiceException",e);
            return jaxbUtil.toXml(new XmlResult("failure",JsonResultCode.FAILURE,e.getMessage()),"utf-8");
        }catch (Exception e){
            log.error("[DeliveryController][stockoutConfirm] Exception",e);
            return jaxbUtil.toXml(new XmlResult("failure",JsonResultCode.FAILURE,CommonFainl.WMS_RECEIVE_FIANL),"utf-8");
        }
    }
}
