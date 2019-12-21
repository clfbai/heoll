package com.boyu.erp.platform.usercenter.service.warehouse;

import java.util.List;
/**
 * 仓库分组明细接口
 * @author HHe
 * @date 2019/10/13 11:34
 */
public interface WarehUgDtlService {
    /**
     * 根据仓库分组集合查询出所有相关的仓库Id
     * @author HHe
     * @date 2019/10/13 11:34
     */
    List<Long> queryWarehIdsByWarehUgIds(List<Long> warehUgIds);
}
