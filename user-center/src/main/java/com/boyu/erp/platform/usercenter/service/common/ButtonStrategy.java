package com.boyu.erp.platform.usercenter.service.common;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.OperationVo;

import java.util.List;

/**
 * 类描述: 按钮策略类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/29 14:57
 */
public interface ButtonStrategy {

    List<OperationVo> initButtons(BaseData model) throws ServiceException;
}
