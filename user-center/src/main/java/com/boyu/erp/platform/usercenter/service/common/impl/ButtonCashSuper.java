package com.boyu.erp.platform.usercenter.service.common.impl;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.service.common.ButtonStrategy;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述: 上下文按钮类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/29 14:56
 */
@Component
@NoArgsConstructor
public class ButtonCashSuper {
    //持有策略按钮接口
    private ButtonStrategy buttonStrategy;

    //通过策略类子来决定初始化按钮
    public ButtonCashSuper(ButtonStrategy buttonStrategy) {
        this.buttonStrategy = buttonStrategy;
    }

    //初始化按钮
    public List<OperationVo> cateButton(BaseData model) throws ServiceException {
        return buttonStrategy.initButtons(model);
    }

}
