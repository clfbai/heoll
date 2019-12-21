package com.boyu.erp.platform.usercenter.TPOS.utils;

import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CwmsUrlParamModelUtils {
    @Value("${app_key}")
    public String appKey;
    @Value("${customer_id}")
    public String customerId;
    @Value("${customer_id}")
    public String secret;

    public CwmsUrlParamModel cretaeComm(String api, String method) throws ServiceException {
        vifes(api, method);
        CwmsUrlParamModel cwmsUrlParamModel = new CwmsUrlParamModel();
        setCwms(cwmsUrlParamModel);
        log.info("头部信息" + cwmsUrlParamModel.getAppKey());
        //这个请求地址后面成正式地址
        cwmsUrlParamModel.setRequestMapping(api);
        //请求方法换正式方法
        cwmsUrlParamModel.setMethod(method);
        return cwmsUrlParamModel;
    }

    public CwmsUrlParamModel cretaeComm(String method) {
        vifes(method);
        CwmsUrlParamModel cwmsUrlParamModel = new CwmsUrlParamModel();
        setCwms(cwmsUrlParamModel);
        log.info("头部信息" + cwmsUrlParamModel.getAppKey());
        //这个请求地址后面成正式地址
        cwmsUrlParamModel.setRequestMapping("/api/qm2");
        //请求方法换正式方法
        cwmsUrlParamModel.setMethod(method);
        return cwmsUrlParamModel;
    }

    public void setCwms(CwmsUrlParamModel cwmsUrlParamModel) {
        cwmsUrlParamModel.setAppKey(appKey);
        cwmsUrlParamModel.setSecret(secret);
        cwmsUrlParamModel.setCustomerid(customerId);
    }


    public void vifes(String... strings) {
        for (String s : strings) {
            if (StringUtils.isBlank(s)) {
                throw new ServiceException("403", "方法名或api为空");
            }
        }
    }


}
