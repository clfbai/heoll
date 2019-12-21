package com.boyu.erp.platform.usercenter.controller.base;

import com.alibaba.fastjson.JSONObject;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.common.ResponseOrder;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.TPOS.service.impl.RequestTPOServiceImpl;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.system.ExceptionRequestCwms;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.base.InterfaceLog;
import com.boyu.erp.platform.usercenter.service.base.SendSerivce;
import com.boyu.erp.platform.usercenter.service.system.ExceptionRequestCwmsService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述:
 *
 * @Description: 收发异常再次请求
 * @auther: CLF
 * @date: 2019/11/11 17:19
 */
@Slf4j
@RestController
@RequestMapping("/user/text")
public class ExcpController extends BaseController {
    @Autowired
    private ExceptionRequestCwmsService exceptionRequestCwmsService;
    @Autowired
    RequestTPOServiceImpl requestTPOService;
    @Autowired
    private InterfaceLog interfaceLog;
    @Autowired
    SendSerivce sendSerivce;

    /**
     * 功能描述: 接口交互查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/11 17:59
     */
    @RequestMapping(value = "/excpList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult ExcpList(@RequestBody ExceptionRequestCwms model, HttpServletRequest re) {
        try {
            SysUser sysUser = this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, exceptionRequestCwmsService.selectKey(model));
        } catch (ServiceException ex) {
            log.error("[ExceptionRequestCwmsService][selectKey] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ExcpController][ExcpList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, "发起请求异常", "");
        }
    }

    /**
     * 功能描述: 请求异常的接口再次发起请求
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/11 17:59
     */
    @RequestMapping(value = "/send", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult ExcpSend(@RequestBody ExceptionRequestCwms model, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            if (model.getStatuss().equals(CommonFainl.TRUE)) {
                return new JsonResult(JsonResultCode.SUCCESS, "请求成功的数据不能再执行", 0);
            }
            if (model.getIsMessage().equals(CommonFainl.FALSE)) {
                CwmsUrlParamModel cwms = JSONObject.parseObject(model.getRequestParam(), CwmsUrlParamModel.class);
                ResponseOrder responseOrder = requestTPOService.createCwmsURL(cwms, model.getUuid(), ResponseOrder.class);
                return new JsonResult(JsonResultCode.SUCCESS, "执行成功", interfaceLog.CWMSLog(cwms,responseOrder,model.getRequestMagess(),model.getUuid(),user));
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, 0);
        } catch (ServiceException ex) {
            log.error("[ExceptionRequestCwmsService][updateExcep] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ExcpController][ExcpSend] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, "发起请求异常", "");
        }
    }


    /**
     * 功能描述: 重发消息接口
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/11 17:59
     */
    @RequestMapping(value = "/send/message", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult sendMessage(@RequestBody ExceptionRequestCwms model, HttpServletRequest re) {
        try {
            SysUser user = this.isNullUser(re);
            if (model.getRate().equals("de")) {
                return new JsonResult(JsonResultCode.SUCCESS, "请求成功的消息不能再执行", 0);
            }
            String exchange = model.getExchange();
            String routingKey = model.getRoutingKey();
            if (StringUtils.isBlank(exchange)) {
                return new JsonResult(JsonResultCode.FAILURE_PUC_R, "检查参数:exchange不能为空", "");
            }
            if (StringUtils.isBlank(routingKey)) {
                return new JsonResult(JsonResultCode.FAILURE_PUC_R, "检查参数:routingKey不能为空", "");
            }
            MessageObject body = JSONObject.parseObject(model.getRequestParam(), MessageObject.class);
            if (body == null) {
                return new JsonResult(JsonResultCode.FAILURE_PUC_R, "requestParam为空或不是消息格式类型", "");
            }
            //log.info("[MessageObject]:"+body);
            //重发
            sendSerivce.send(exchange, routingKey, body);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, "消息重发成功");

        } catch (ServiceException ex) {
            log.error("[ExceptionRequestCwmsService][updateExcep] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ExcpController][ExcpSend] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, "发起请求异常", "");
        }
    }
}
