package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.San;
import com.boyu.erp.platform.usercenter.entity.warehouse.SanDtl;
import lombok.Data;

import java.util.List;

/**
 * 库存调整表model
 * @author HHe
 * @date 2019/10/7 11:53
 */
@Data
public class SanModel extends San {
    /**
     * 功能类型
     */
    private String codeType;
    /**
     * 库存调整表明细
     */
    private List<SanDtl> sanDtlList;
}

