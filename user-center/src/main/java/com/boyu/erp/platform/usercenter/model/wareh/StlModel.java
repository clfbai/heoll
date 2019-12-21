package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.Stl;
import com.boyu.erp.platform.usercenter.entity.warehouse.StlDtl;
import lombok.Data;

import java.util.List;
@Data
public class StlModel extends Stl {
    /**
     * 仓库编号
     */
    private String warehNum;
    /**
     * 明细集合
     */
    private List<StlDtl> stlDtlList;
}
