package com.boyu.erp.platform.usercenter.model.batch;

import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehBatchModel implements Serializable {

    //由于目前销售时采购商都是介入的 不需要考虑采购商不介入

    /**
     * 采购商
     */
    private Long vendeeId;
    /**
     * 供应商
     */
    private Long venderId;
    /**
     * 供应商是否介入  1 等于true 0 等于 false
     */
    private Boolean intervene;
    /**
     * 出库入库类型 R 入库  D 出库
     */
    private String type;
    /**
     * 商品明细 主要是商品Id 数量 和价格
     */
    private List<StbDtl> stbDtls = new ArrayList<>();


}
