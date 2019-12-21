package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRmode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WarehRmodeMode extends WarehRmode {
    /**
     * 修改前入库方式
     */
    private String updateRcvMode;
    /**
     * 仓库属主
     */
    private Long ownerId;


}
