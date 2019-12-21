package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.vo.warehouse.WarehInfoVo;
import org.springframework.stereotype.Service;

/**
 * @Classname WarehPopupSerivce
 * @Description TODO
 * @Date 2019/10/28 19:29
 * @Created by wz
 */
public interface WarehPopupSerivce {

    //出入库弹窗查询明细
    public Object getWarehInfo(WarehInfoVo vo);

}
