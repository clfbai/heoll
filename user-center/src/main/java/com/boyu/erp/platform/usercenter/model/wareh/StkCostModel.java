package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehCost;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 核算成本参数
 *
 * @author HHe
 * @date 2019/10/24 11:47
 */
@Data
public class StkCostModel implements Serializable {
    /**
     * 是否改变成本
     */
    private Boolean costChanged;
    /**
     * 是否取反
     */
    private Boolean reversed;
    /**
     * 单据类型（D:出库单；R：入库单）
     */
    private String drType;
    /**
     * 会计组织Id
     */
    private Long fsclUnitId;
    /**
     * 出/入库方式
     */
    private String mode;
    /**
     * 仓库Id
     */
    private Long warehId;
    /**
     * 库存表明细
     */
    private List<StbDtl> stbDtls;
    /**
     * 仓库库存成本
     */
    private List<WarehCost> warehCosts;
    /**
     * 成本组Id
     */
    private Long costGrpId;
}
