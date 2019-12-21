package com.boyu.erp.platform.usercenter.utils.common;

import com.boyu.erp.platform.usercenter.entity.system.ExceptionRequestCwms;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.system.ExceptionRequestCwmsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InterfaceLogUtils {
    @Autowired
    private ExceptionRequestCwmsMapper cwmsMapper;

    /**
     * 验证参数
     */
    public void isVerify(String UUID, String exchange, String routingKey) throws ServiceException {
        if (StringUtils.isBlank(UUID)) {
            throw new ServiceException("403", "UUID不能为空");
        }
        if (StringUtils.isBlank(exchange)) {
            throw new ServiceException("403", "exchange交换机不能为空");
        }
        if (StringUtils.isBlank(routingKey)) {
            throw new ServiceException("403", "routingKey路由键不能为空");
        }
    }

    /**
     * 验证是否能请求
     *
     * @return true 是
     */
    public Boolean isSend(String uuid) throws ServiceException {
        ExceptionRequestCwms exc = cwmsMapper.selectExceptionRequestCwms(uuid);
        if (exc == null) {
            //uuid不存在行的请求
            return true;
        }
        if (exc != null) {
            if (StringUtils.isNotBlank(exc.getRate())) {
                //处理定成功或等待回执的不用再次请求
                if (exc.getRate().equalsIgnoreCase("sd") || exc.getRate().equalsIgnoreCase("de")) {
                    return false;
                }
                //处理失败或等待发送失败可以再次请求
                if (exc.getRate().equalsIgnoreCase("ng") || exc.getRate().equalsIgnoreCase("nd")) {
                    return true;
                }
            } else {
                throw new ServiceException("403", "请求状态不能为空");
            }
        }
        return false;
    }


}
