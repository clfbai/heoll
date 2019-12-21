package com.boyu.erp.platform.usercenter.TPOS.controller;

import com.boyu.erp.platform.usercenter.TPOS.model.XmlResult;
import com.boyu.erp.platform.usercenter.TPOS.service.RequestTPOService;
import com.boyu.erp.platform.usercenter.TPOS.utils.BoyuErpUtils;
import com.boyu.erp.platform.usercenter.TPOS.utils.BoyuSignUtils;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 类描述:
 *
 * @Description: 入库单请求
 * @auther: CLF
 * @date: 2019/11/21 10:58
 */
@Slf4j
@RestController
public class WarehSingController {
    @Value("${app_key}")
    private String appkey;
    @Autowired
    private BoyuErpUtils boyuErpUtils;
    @Autowired
    RequestTPOService requestTPOService;

    /**
     * 功能描述: C-WMS 同一调用接口
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/21 11:00
     */
    @RequestMapping(value = "/boyu/erp", method = {RequestMethod.POST}, produces = {"text/xml; charset=UTF-8"})
    public Object warehSingConfirm(HttpServletRequest request) throws Exception {

        String response = "";
        try {
            //请求参数
            String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            String requestUrl = request.getRequestURI() + "?" + request.getQueryString();
            log.info("URL：" + requestUrl);
            //获得参数集
            Map<String, String> params = BoyuSignUtils.getParamsFromUrl(requestUrl);
            String method = params.get("method");
            String sign = params.get("sign");
            String getXml = "";
            //截取xml格式String
            if (data.indexOf("<") > 0) {
                getXml = data.substring(data.indexOf("<"));
            } else {
                getXml = data;
            }
            System.out.println("sing:  " + sign);
            String mySign = BoyuSignUtils.sign(params, getXml, appkey);
            System.out.println("mysqing:  " + mySign);
            if (sign.equals(mySign)) {
                boyuErpUtils.boyuErpService(getXml, method);
                response = requestTPOService.createObjXml(new XmlResult("success", JsonResultCode.SUCCESS, CommonFainl.WMS_RECEIVE_SUCC));
            } else {
                response = requestTPOService.createObjXml(new XmlResult("failure", JsonResultCode.FAILURE, "签名有误"));
            }
        } catch (ServiceException e) {
            log.error("[BoyuErpUtils][boyuErpService] ServiceException", e);
            response = requestTPOService.createObjXml(new XmlResult("failure", JsonResultCode.FAILURE, e.getMessage()));
        } catch (Exception e) {
            log.error("[WarehSingController][warehSingConfirm] Exception", e);
            response = requestTPOService.createObjXml(new XmlResult("failure", JsonResultCode.FAILURE, "BoYu_ERP接口处理异常"));
        }
        log.info("返回的xml： " + response);
        return response;
    }


}
