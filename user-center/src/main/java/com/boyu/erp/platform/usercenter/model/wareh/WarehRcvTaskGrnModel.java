package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class WarehRcvTaskGrnModel  implements Serializable {
    /**
     * 缺省仓库编号
     */
    private Long defaultWarehId;

    private List<WarehRcvTask> list = new ArrayList<>();
}
