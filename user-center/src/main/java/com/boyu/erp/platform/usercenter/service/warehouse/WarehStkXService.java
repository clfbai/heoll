package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStkX;

import java.sql.Timestamp;
import java.util.List;
/**
 * 仓库库存快照服务
 * @author HHe
 * @date 2019/9/18 20:17
 */
public interface WarehStkXService {
    /**
     * 存储仓库库存快照
     * @author HHe
     * @date 2019/9/18 20:17
     */
    int addWarehStkXList(List<WarehStkX> warehStkXList, Timestamp nowDate);
    /**
     * 根据盘点时间和仓库Id删除所有快照
     * @author HHe
     * @date 2019/9/19 16:56
     */
    int delWarehStkXByTimeAndWarehId(Timestamp snptTime, Long warehId);
    /**
     * 根据仓库id和快照时间查询仓库快照
     * @author HHe
     * @date 2019/9/26 19:43
     */
    List<WarehStkX> queryWarehStkXList(Long warehId, Timestamp snptTime);
}
