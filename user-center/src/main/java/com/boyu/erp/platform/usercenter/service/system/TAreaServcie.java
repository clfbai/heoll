package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.TArea;

import java.util.List;

/**
 * @Description: 地区接口
 * @auther: CLF
 * @date: 2019/7/19 15:25
 */
public interface TAreaServcie {


    List<TArea> getTArea(TArea tArea)throws Exception;
}
