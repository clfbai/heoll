package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WarehDelivTaskModel implements Serializable {
    /**
     * 组织Id
     */
    private Long sUnitId;
    /**
     * 缺省仓库Id
     */
    private Long defaultWarehId;
    /**
     * 出库任务集合
     */
    private List<WarehDelivTask> warehDelivTaskList;

    public Long getsUnitId() {
        return sUnitId;
    }

    public void setsUnitId(Long sUnitId) {
        this.sUnitId = sUnitId;
    }
}
