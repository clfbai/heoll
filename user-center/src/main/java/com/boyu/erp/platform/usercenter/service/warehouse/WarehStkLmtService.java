package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStkLmt;

import java.util.List;

public interface WarehStkLmtService {
    /**
     * 功能描述: 批量添加仓库库存策略
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/20 20:55
     */
    void addwarehStkLmtList(List<WarehStkLmt> warehStkLmts);


    int addwarehStkLmt(WarehStkLmt warehStkLmt);


}
